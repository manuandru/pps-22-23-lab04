package u04lab.polyglot.minesweeper.logic.grid;

import u04lab.polyglot.minesweeper.logic.grid.cell.Cell;
import u04lab.polyglot.minesweeper.logic.grid.cell.CellContent;
import u04lab.polyglot.minesweeper.logic.grid.cell.CellImpl;

import java.util.*;

public class GridImpl implements Grid {

    private final Map<Cell, CellContent> grid = new HashMap<>();
    private final int gridSize;
    public GridImpl(int size, int bombCount) {
        this.gridSize = size;
        Set<Integer> bombsPosition = getRandomBombsPosition(bombCount);
        int bombCounter = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (bombsPosition.contains(bombCounter)) {
                    this.grid.put(new CellImpl(i,j), CellContent.BOMB);
                } else {
                    this.grid.put(new CellImpl(i,j), CellContent.EMPTY);
                }
                bombCounter++;
            }
        }
    }

    private Set<Integer> getRandomBombsPosition(int bombCount) {
        var random = new Random();
        var returnSet = new HashSet<Integer>();
        while (returnSet.size() != bombCount) {
            returnSet.add(random.nextInt(gridSize*gridSize));
        }
        return returnSet;
    }

    @Override
    public CellContent getCellContent(Cell cell) {
        return this.grid.get(cell);
    }

    @Override
    public Set<Cell> getAllCells() {
        return this.grid.keySet();
    }

    @Override
    public int countOfAdjacentBombs(Cell cell) {
        return (int) this.getAllCells().stream()
                .filter(cell::isAdjacentTo)
                .map(grid::get)
                .filter(CellContent.BOMB::equals)
                .count();
    }
}
