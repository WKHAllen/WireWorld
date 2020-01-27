package wireworld;

/**
 *
 * @author Will Allen
 */
public class Display {

    private final int width;
    private final int height;
    private final int cellSize;
    private final int padding;
    private final Game game;

    public Display(Game game, int cellSize) {
        this(game, cellSize, 1);
    }

    public Display(Game game, int cellSize, int padding) {
        this.width = game.getWidth() * (cellSize + padding) + padding;
        this.height = game.getHeight() * (cellSize + padding) + padding;
        this.cellSize = cellSize;
        this.padding = padding;
        this.game = game;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public int getCellSize() {
        return this.cellSize;
    }

    public int getPadding() {
        return this.padding;
    }

    public Game getGame() {
        return this.game;
    }

    public void display() {
        // TODO: display the game
    }

}
