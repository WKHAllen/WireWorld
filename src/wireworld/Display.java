package wireworld;

import javafx.scene.Group;

/**
 *
 * @author Will Allen
 */
public class Display {

    private final int width;
    private final int height;
    private final int cellSize;
    private final int padding;
    private final Group root;
    private final Game game;

    public Display(Group root, Game game, int cellSize) {
        this(root, game, cellSize, 1);
    }

    public Display(Group root, Game game, int cellSize, int padding) {
        this.width = game.getWidth() * (cellSize + padding) + padding;
        this.height = game.getHeight() * (cellSize + padding) + padding;
        this.cellSize = cellSize;
        this.padding = padding;
        this.root = root;
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

    public Group getRoot() {
        return this.root;
    }

    public boolean onScreen(int x, int y) {
        return x >= 0 && x < this.width - 1 && y >= 0 && y < this.height - 1;
    }

    public int[] coordsToPos(int x, int y) {
        int[] pos = {x / (this.cellSize + this.padding), y / (this.cellSize + this.padding)};
        return pos;
    }

    public int[] posToCoords(int x, int y) {
        int[] coords = {x * (this.cellSize + this.padding), y * (this.cellSize + this.padding)};
        return coords;
    }

    public void display() {
        // TODO: display the game
    }

}
