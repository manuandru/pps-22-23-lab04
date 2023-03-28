package u04lab.polyglot.minesweeper.logic;

import u04lab.polyglot.minesweeper.gui.RenderStatus;

public interface Logics {

    /**
     *
     * @param row of the cell to check.
     * @param column of the cell to check.
     * @return true if a bomb is present.
     */
    boolean checkIfContainsBomb(int row, int column);

    /**
     *
     * @param row of the cell to get Content.
     * @param column of the cell get Content.
     * @return
     */
    RenderStatus getStatus(int row, int column);

    /**
     * Reveal all bombs.
     */
    void revealAllBombs();

    /**
     * Reveal all cells.
     */
    void revealAll();

    /**
     *
     * @param row of the cell to change flag status.
     * @param column of the cell to change flag status.
     */
    void changeFlag(int row, int column);

    /**
     *
     * @return true if won.
     */
    boolean won();
}
