package u04lab.polyglot.minesweeper.logic.grid.cell;

public interface Cell {

    /**
     *
     * @return the row of the cell.
     */
    int getRow();

    /**
     *
     * @return the column of the cell
     */
    int getColumn();

    /**
     *
     * @param otherCell to check adjacency.
     * @return true if this cell is adjacent to otherCell.
     */
    boolean isAdjacentTo(Cell otherCell);
}
