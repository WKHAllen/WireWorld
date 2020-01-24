package wireworld;

/**
 *
 * @author Will Allen
 */
enum GameCell {
    EMPTY, EHEAD, ETAIL, CONDUCTOR;
}

public class Game {

    private final int width;
    private final int height;
    private GameCell[][] board;

    public Game(int width, int height) {
        this.width = width;
        this.height = height;
        board = new GameCell[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                board[i][j] = GameCell.EMPTY;
            }
        }
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    private int numEHeadNeighbors(int x, int y) {
        int total = 0;
        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                if (i >= 0 && i < this.height && j >= 0 && j < this.width && this.board[i][j] == GameCell.EHEAD) {
                    total++;
                }
            }
        }
        return total;
    }

    public void step() {
        GameCell[][] newBoard = new GameCell[this.height][this.width];
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                switch (this.board[i][j]) {
                    case EMPTY:
                        newBoard[i][j] = GameCell.EMPTY;
                        break;
                    case EHEAD:
                        newBoard[i][j] = GameCell.ETAIL;
                        break;
                    case ETAIL:
                        newBoard[i][j] = GameCell.CONDUCTOR;
                        break;
                    case CONDUCTOR:
                        int neighbors = this.numEHeadNeighbors(i, j);
                        if (neighbors == 1 || neighbors == 2) {
                            newBoard[i][j] = GameCell.EHEAD;
                        } else {
                            newBoard[i][j] = GameCell.CONDUCTOR;
                        }
                        break;
                }
            }
        }
        this.board = newBoard;
    }

}
