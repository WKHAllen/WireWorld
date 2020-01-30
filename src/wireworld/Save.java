package wireworld;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Will Allen
 */
public class Save {

    private static final String COMMENT_CHAR = "# ";
    private static final String PROP_DELIM = ": ";
    private static final String BOARD_NEWLINE = "|";
    private static final String CELL_EMPTY = "0";
    private static final String CELL_EHEAD = "1";
    private static final String CELL_ETAIL = "2";
    private static final String CELL_CONDUCTOR = "3";

    public static void save(String filename, Game game, Settings settings) throws IOException {
        FileWriter gameFile = new FileWriter(filename);
        writeComment(gameFile, "WireWorld Save file");
        writeProperty(gameFile, "width", settings.get("width"));
        writeProperty(gameFile, "height", settings.get("height"));
        writeProperty(gameFile, "cellSize", settings.get("cellSize"));
        writeProperty(gameFile, "padding", settings.get("padding"));
        writeProperty(gameFile, "speed", settings.get("speed"));
        writeBoard(gameFile, "board", game);
        gameFile.close();
    }

    public static void load(String filename) {
        // TODO: implement this
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
            gameFile.write(BOARD_NEWLINE);
        }
        gameFile.write("\n");
    }

    private static void writeComment(FileWriter gameFile, String comment) throws IOException {
        gameFile.write(COMMENT_CHAR + comment + "\n");
    }

}
