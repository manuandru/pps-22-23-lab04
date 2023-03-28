package u04lab.polyglot.minesweeper

import u04lab.code.List
import u04lab.code.List.*
import u04lab.polyglot.Pair
import u04lab.polyglot.minesweeper.gui.RenderStatus

import scala.util.Random

class LogicsImpl2(size: Int, bombCount: Int) extends logic.Logics:

  private val grid: OverlapGrid = OverlapGrid(Grid(size, bombCount))

  override def checkIfContainsBomb(row: Int, column: Int): Boolean = ???

  override def getStatus(row: Int, column: Int): RenderStatus =
    val cell = Cell(row, column)
    grid.contentOf(cell) match
      case CellContent.Bomb => RenderStatus.BOMB
      case CellContent.Empty => RenderStatus.COUNTER.setCounter(grid.countAdjacentBombs(cell))
      case CellContent.Flag => RenderStatus.FLAG
      case CellContent.Hidden => RenderStatus.HIDDEN

  override def revealAllBombs(): Unit = ???

  override def revealAll(): Unit = ???

  override def changeFlag(row: Int, column: Int): Unit = ???

  override def won(): Boolean = ???

trait Cell:
  def row: Int
  def column: Int
  def adjacentTo(other: Cell): Boolean

object Cell:
  def apply(row: Int, column: Int): Cell = CellImpl(row, column)
  private case class CellImpl(row: Int, column: Int) extends Cell:
    override def adjacentTo(other: Cell): Boolean =
      Math.abs(this.row - other.row) <= 1 && Math.abs(this.column - other.column) <= 1

enum CellContent:
  case Bomb
  case Empty
  case Flag
  case Hidden

trait Grid:
  def contentOf(cell: Cell): CellContent
  def allCells: List[Cell]
  def countAdjacentBombs(cell: Cell): Int

object Grid:

  def apply(size: Int, bombCount: Int): Grid = GridImpl(size, bombCount)

  private class GridImpl(size: Int, bombCount: Int)  extends Grid:

    private var cellsToContent: List[Pair[Cell, CellContent]] = Nil()

    init()

    override def contentOf(cell: Cell): CellContent = ???

    override def allCells: List[Cell] = ???

    override def countAdjacentBombs(cell: Cell): Int = ???

    private def init() =
      val bombsPosition = randomPositions(size)(Nil())
      for i <- 0 until size; j <- 0 until size
      do
        val cell = Cell(i, j)
        cellsToContent = append(
          cellsToContent,
          cons(
            Pair(cell, if contains(bombsPosition, cell) then CellContent.Bomb else CellContent.Empty),
            Nil())
        )

    private def randomPositions(count: Int)(acc: List[Cell]): List[Cell] = count match
      case 0 => acc
      case _ =>
        val randomCell = Cell(Random.nextInt(size), Random.nextInt(size))
        if contains(acc, randomCell) then
          randomPositions(count)(acc)
        else
          randomPositions(count - 1)(append(acc, cons(randomCell, Nil())))

trait OverlapGrid extends Grid:
  def reveal(cell: Cell): Unit
  def revealAll: Unit
  def revealAllBombs(): Unit
  def changeFlag(cell: Cell): Unit

object OverlapGrid:

  def apply(grid: Grid): OverlapGrid = OverlapGridImpl(grid)

  private class OverlapGridImpl(grid: Grid) extends OverlapGrid:

    private var revealedCells: List[Cell] = Nil()
    private var flaggedCells: List[Cell] = Nil()

    override def contentOf(cell: Cell): CellContent =
      if contains(revealedCells, cell) then
        grid.contentOf(cell)
      else if contains(flaggedCells, cell) then
        CellContent.Flag
      else
        CellContent.Hidden

    override def allCells: List[Cell] = ???
    override def countAdjacentBombs(cell: Cell): Int = ???
    override def reveal(cell: Cell): Unit = ???
    override def revealAll: Unit = ???
    override def revealAllBombs(): Unit = ???
    override def changeFlag(cell: Cell): Unit = ???
