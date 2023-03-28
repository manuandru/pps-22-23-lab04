package u04lab.polyglot.minesweeper.logic.grid;

import u04lab.polyglot.minesweeper.logic.grid.cell.Cell;

/**
 * Decorator Interface for grid,
 * that introduce hidden and flagged cells.
 */
public interface OverlapGrid extends Grid {

    /**
     * Reveal a cell and all adjacent with 0 bombs.
     * @param cell to be revealed.
     */
    void reveal(Cell cell);

    /**
     * Reveal all cells.
     */
    void revealAll();

    /**
     * Reveal all bombs.
     */
    void revealAllBombs();

    /**
     * Flag a cell.
     * @param cell to be flagged.
     */
    void changeFlag(Cell cell);
}
