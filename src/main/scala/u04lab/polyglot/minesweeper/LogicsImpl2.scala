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