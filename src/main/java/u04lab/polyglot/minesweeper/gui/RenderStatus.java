package u04lab.polyglot.minesweeper.gui;

public enum RenderStatus {
    BOMB, COUNTER, FLAG, HIDDEN, ERROR;

    private int counter;

    public int getCounter() {
        return this.counter;
    }

    public RenderStatus setCounter(int counter) {
        this.counter = counter;
        return this;
    }
}
