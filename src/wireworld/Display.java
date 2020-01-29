package wireworld;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

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

    private static final Color BORDER = Color.GRAY;
    private static final Color EMPTY = Color.BLACK;
    private static final Color CONDUCTOR = Color.YELLOW;
    private static final Color EHEAD = Color.BLUE;
    private static final Color ETAIL = Color.RED;

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

    public void fill(Color color) {
        Rectangle rect = new Rectangle(0, 0, this.width, this.height);
        rect.setFill(color);
        root.getChildren().add(rect);
    }

    public void display() {
        this.root.getChildren().clear();
        this.fill(Display.BORDER);
        for (int i = 0; i < this.game.getHeight(); i++) {
            for (int j = 0; j < this.game.getWidth(); j++) {
                Rectangle rect = new Rectangle(this.padding + j * (this.cellSize + this.padding), this.padding + i * (this.cellSize + this.padding), this.cellSize, this.cellSize);
                switch (this.game.get(j, i)) {
                    case EMPTY:
                        rect.setFill(Display.EMPTY);
                        break;
                    case CONDUCTOR:
                        rect.setFill(Display.CONDUCTOR);
                        break;
                    case EHEAD:
                        rect.setFill(Display.EHEAD);
                        break;
                    case ETAIL:
                        rect.setFill(Display.ETAIL);
                        break;
                }
                this.root.getChildren().add(rect);
            }
        }
    }

}
