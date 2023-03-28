package u04lab.polyglot.minesweeper
import u04lab.polyglot.minesweeper.gui.RenderStatus

class LogicsImpl2(size: Int, bombCount: Int) extends logic.Logics:

  private val grid: OverlapGrid = OverlapGrid(Grid(size, bombCount))

  override def checkIfContainsBomb(row: Int, column: Int): Boolean = ???

  override def getStatus(row: Int, column: Int): RenderStatus = ???

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
  private class GridImpl(size: Int, bombCount: Int) extends Grid:
    override def contentOf(cell: Cell): CellContent = ???
    override def allCells: List[Cell] = ???
    override def countAdjacentBombs(cell: Cell): Int = ???

trait OverlapGrid:
  def reveal(cell: Cell): Unit
  def revealAll: Unit
  def revealAllBombs(): Unit
  def changeFlag(cell: Cell): Unit

object OverlapGrid:
  def apply(grid: Grid): OverlapGrid = OverlapGridImpl(grid)
  private class OverlapGridImpl(grid: Grid) extends OverlapGrid:
    override def reveal(cell: Cell): Unit = ???
    override def revealAll: Unit = ???
    override def revealAllBombs(): Unit = ???
    override def changeFlag(cell: Cell): Unit = ???
