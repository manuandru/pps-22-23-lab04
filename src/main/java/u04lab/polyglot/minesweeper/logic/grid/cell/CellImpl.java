package u04lab.polyglot.minesweeper.logic.grid.cell;

import java.util.Objects;

public class CellImpl implements Cell {
    private final int row;
    private final int column;

    public CellImpl(int row, int column) {
        this.row = row;
        this.column = column;
    }

    @Override
    public int getRow() {
        return this.row;
    }

    @Override
    public int getColumn() {
        return this.column;
    }

    @Override
    public boolean isAdjacentTo(Cell otherCell) {
        int x = this.row - otherCell.getRow();
        int y = this.column - otherCell.getColumn();
        return Math.abs(x) <= 1 && Math.abs(y) <= 1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CellImpl cell = (CellImpl) o;
        return row == cell.row && column == cell.column;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }
}
