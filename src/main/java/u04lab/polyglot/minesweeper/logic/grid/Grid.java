package u04lab.polyglot.minesweeper.logic.grid;

import u04lab.polyglot.minesweeper.logic.grid.cell.Cell;
import u04lab.polyglot.minesweeper.logic.grid.cell.CellContent;

import java.util.Set;

/**
 * Grid of cell that can contains bomb or not.
 */
public interface Grid {

    /**
     *
     * @param cell to get the Content.
     * @return the content of the cell.
     */
    CellContent getCellContent(Cell cell);

    /**
     *
     * @return all cells of the grid.
     */
    Set<Cell> getAllCells();

    /**
     *
     * @param cell to check the adjacency.
     * @return the number of bombs adjacent to the cell.
     */
    int countOfAdjacentBombs(Cell cell);
}
