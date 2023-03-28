package u04lab.polyglot.minesweeper.logic.grid;

import u04lab.polyglot.minesweeper.logic.grid.cell.Cell;
import u04lab.polyglot.minesweeper.logic.grid.cell.CellContent;

import java.util.HashSet;
import java.util.Set;

public class OverlapGridImpl implements OverlapGrid {

    private final Grid grid;
    private final Set<Cell> revealedCells = new HashSet<>();
    private final Set<Cell> flaggedCells = new HashSet<>();

    public OverlapGridImpl(Grid grid) {
        this.grid = grid;
    }
    @Override
    public CellContent getCellContent(Cell cell) {
        if (this.revealedCells.contains(cell)) {
            return this.grid.getCellContent(cell);
        }
        if (this.flaggedCells.contains(cell)) {
            return CellContent.FLAG;
        }
        return CellContent.HIDDEN;
    }

    @Override
    public Set<Cell> getAllCells() {
        return this.grid.getAllCells();
    }

    @Override
    public int countOfAdjacentBombs(Cell cell) {
        return this.grid.countOfAdjacentBombs(cell);
    }

    @Override
    public void reveal(Cell cell) {
        this.revealedCells.add(cell);
        if (this.countOfAdjacentBombs(cell) == 0) {
            this.getAllCells().stream()
                    .filter(cell::isAdjacentTo)
                    .filter(c -> !this.revealedCells.contains(c))
                    .forEach(this::reveal);
        }
    }

    @Override
    public void revealAllBombs() {
        this.grid.getAllCells().stream()
                .filter(cell -> this.grid.getCellContent(cell).equals(CellContent.BOMB))
                .forEach(this::reveal);
    }

    @Override
    public void revealAll() {
        this.grid.getAllCells().forEach(this::reveal);
    }

    @Override
    public void changeFlag(Cell cell) {
        if (this.flaggedCells.contains(cell)) {
            this.flaggedCells.remove(cell);
        } else {
            this.flaggedCells.add(cell);
        }
    }

}
