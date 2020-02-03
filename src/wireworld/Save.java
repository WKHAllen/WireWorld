package wireworld;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author Will Allen
 */
public class Save {

    private static final String COMMENT_CHAR = "# ";
    private static final String PROP_DELIM = ": ";
    private static final String BOARD_NEWLINE = ",";
    private static final char CELL_EMPTY = '0';
    private static final char CELL_EHEAD = '1';
    private static final char CELL_ETAIL = '2';
    private static final char CELL_CONDUCTOR = '3';

    public static void save(String filename, Game game, Settings settings) throws IOException {
        FileWriter gameFile = new FileWriter(filename);
        writeComment(gameFile, "WireWorld Save file");
        writeProperty(gameFile, "width", settings.get("width"));
        writeProperty(gameFile, "height", settings.get("height"));
        writeProperty(gameFile, "cellSize", settings.get("cellSize"));
        writeProperty(gameFile, "padding", settings.get("padding"));
        writeProperty(gameFile, "speed", settings.get("speed"));
        writeProperty(gameFile, "running", settings.get("running"));
        writeBoard(gameFile, "board", game);
        gameFile.close();
    }

    public static Game load(String filename, Settings settings) throws IOException {
        Scanner gameFile = new Scanner(new File(filename));
        String board = "";
        while (gameFile.hasNextLine()) {
            String line = gameFile.nextLine();
            if (!line.startsWith(COMMENT_CHAR)) {
                String[] prop = line.split(PROP_DELIM);
                switch (prop[0]) {
                    case "board":
                        board = prop[1];
                        break;
                    default:
                        settings.set(prop[0], prop[1]);
                        break;
                }
            }
        }
        gameFile.close();
        int width = Integer.parseInt(settings.get("width"));
        int height = Integer.parseInt(settings.get("height"));
        Game game = new Game(width, height);
        String[] boardSplit = board.split(BOARD_NEWLINE);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                switch (boardSplit[i].charAt(j)) {
                    case CELL_EMPTY:
                        game.set(j, i, GameCell.EMPTY);
                        break;
                    case CELL_EHEAD:
                        game.set(j, i, GameCell.EHEAD);
                        break;
                    case CELL_ETAIL:
                        game.set(j, i, GameCell.ETAIL);
                        break;
                    case CELL_CONDUCTOR:
                        game.set(j, i, GameCell.CONDUCTOR);
                        break;
                }
            }
        }
        return game;
    }

    private static void writeProperty(FileWriter gameFile, String propName, String propValue) throws IOException {
        gameFile.write(propName + PROP_DELIM + propValue + "\n");
    }

    private static void writeBoard(FileWriter gameFile, String propName, Game game) throws IOException {
        gameFile.write(propName + PROP_DELIM);
        for (int i = 0; i < game.getHeight(); i++) {
            for (int j = 0; j < game.getWidth(); j++) {
                switch (game.get(j, i)) {
                    case EMPTY:
                        gameFile.write(CELL_EMPTY);
                        break;
                    case EHEAD:
                        gameFile.write(CELL_EHEAD);
                        break;
                    case ETAIL:
                        gameFile.write(CELL_ETAIL);
                        break;
                    case CONDUCTOR:
                        gameFile.write(CELL_CONDUCTOR);
                        break;
                }
            }
            if (i < game.getHeight() - 1) {
                gameFile.write(BOARD_NEWLINE);
            }
        }
        gameFile.write("\n");
    }

    private static void writeComment(FileWriter gameFile, String comment) throws IOException {
        gameFile.write(COMMENT_CHAR + comment + "\n");
    }

}
