package u04lab.polyglot.minesweeper

import u04lab.code.List.*
import u04lab.code.Option.*
import u04lab.code.{List, Option}
import u04lab.polyglot.minesweeper.gui.RenderStatus

import scala.annotation.tailrec
import scala.util.Random

class LogicsImpl2(size: Int, bombCount: Int) extends logic.Logics:

  private val grid: Grid = Grid(size, bombCount)

  override def checkIfContainsBomb(row: Int, column: Int): Boolean =
    val target = Position(row, column)
    grid.revealAllNear(target)
    grid.findBy(target) match
      case Some(Cell.IsBomb()) => true
      case _ => false

  override def getStatus(row: Int, column: Int): RenderStatus =
    val position = Position(row, column)
    grid.findBy(position) match
      case Some(Cell.IsBomb()) => RenderStatus.BOMB
      case Some(Cell.IsRevealed()) => RenderStatus.COUNTER.setCounter(grid.countAdjacentBombs(position))
      case Some(Cell.IsFlag()) => RenderStatus.FLAG
      case Some(Cell.IsHidden()) => RenderStatus.HIDDEN
      case _ => RenderStatus.ERROR

  override def revealAllBombs(): Unit = grid.perform(_.reveal())(_.bomb)

  override def changeFlag(row: Int, column: Int): Unit = grid.perform(c => c.flag = !c.flag)(_.position == Position(row, column))

  override def won(): Boolean = grid.countOfRevealed == size*size - bombCount

trait Position:
  def row: Int
  def column: Int
  def adjacentTo(other: Position): Boolean

object Position:
  def apply(row: Int, column: Int): Position = PositionImpl(row: Int, column: Int)

  private case class PositionImpl(row: Int, column: Int) extends Position:
    override def adjacentTo(other: Position): Boolean =
      Math.abs(this.row - other.row) <= 1 && Math.abs(this.column - other.column) <= 1

trait Cell:
  def position: Position
  def bomb: Boolean
  def flag: Boolean
  def flag_=(flag: Boolean): Unit
  def revealed: Boolean
  def reveal(): Unit

object Cell:
  def apply(position: Position, bomb: Boolean): Cell = CellImpl(position, bomb)
  private class CellImpl(val position: Position, val bomb: Boolean, var flag: Boolean = false) extends Cell:
    private var _revealed = false
    override def revealed: Boolean = _revealed
    override def reveal(): Unit = _revealed = true

  object IsBomb:
    def unapply(cell: Cell): Boolean = cell.revealed && cell.bomb
  object IsRevealed:
    def unapply(cell: Cell): Boolean = cell.revealed && !cell.bomb
  object IsFlag:
    def unapply(cell: Cell): Boolean = !cell.revealed && cell.flag
  object IsHidden:
    def unapply(cell: Cell): Boolean = !cell.revealed && !cell.flag

trait Grid:
  def findBy(position: Position): Option[Cell]
  def countAdjacentBombs(position: Position): Int
  def countOfRevealed: Int
  def perform(op: Cell => Unit)(predicate: Cell => Boolean): Unit
  def revealAllNear(position: Position): Unit

object Grid:

  def apply(size: Int, bombCount: Int): Grid = GridImpl(size, bombCount)

  private class GridImpl(size: Int, bombCount: Int) extends Grid:

    private val bombsPosition = randomPositions(bombCount)(Nil())
    private var cells: List[Cell] = Nil()
    for i <- 0 until size; j <- 0 until size
    do
      val position = Position(i, j)
      val isBomb = contains(bombsPosition, position)
      cells = append(cells, cons(Cell(position, isBomb), Nil()))

    override def findBy(position: Position): Option[Cell] = find(cells)(_.position == position)

    override def countAdjacentBombs(position: Position): Int = length(filter(filter(cells)(_.position.adjacentTo(position)))(_.bomb))

    override def countOfRevealed: Int = length(filter(cells)(_.revealed))

    def perform(op: Cell => Unit)(predicate: Cell => Boolean): Unit = perform(cells)(op)(predicate)

    @tailrec
    private def perform(list: List[Cell])(op: Cell => Unit)(predicate: Cell => Boolean): Unit = list match
      case Cons(h, t) => if predicate(h) then op(h); perform(t)(op)(predicate)
      case _ =>

    def revealAllNear(position: Position): Unit =
      perform(_.reveal())(_.position == position)
      if countAdjacentBombs(position) == 0 then
        val cellsToReveal = filter(filter(cells)(_.position.adjacentTo(position)))(!_.revealed)
        map(cellsToReveal)(c => {revealAllNear(c.position); c}) // aka foreach

    @tailrec
    private def randomPositions(count: Int)(acc: List[Position]): List[Position] = count match
      case 0 => acc
      case _ =>
        val randomPos = Position(Random.nextInt(size), Random.nextInt(size))
        if contains(acc, randomPos) then
          randomPositions(count)(acc)
        else
          randomPositions(count - 1)(append(acc, cons(randomPos, Nil())))
