package wireworld;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

/**
 *
 * @author Will Allen
 */
public class WireWorld extends Application {

    @Override
    public void start(Stage primaryStage) {
        // get settings
        Settings settings = null;
        try {
            settings = new Settings("game.properties", "WireWorld game settings");
            settings.setDefault("width", "40");
            settings.setDefault("height", "30");
            settings.setDefault("cellSize", "10");
            settings.setDefault("padding", "1");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // initialize the game
        int width = Integer.parseInt(settings.get("width"));
        int height = Integer.parseInt(settings.get("height"));
        Game game = new Game(width, height);

        // initialize the display
        int cellSize = Integer.parseInt(settings.get("cellSize"));
        int padding = Integer.parseInt(settings.get("padding"));
        Display display = new Display(game, cellSize, padding);

        StackPane root = new StackPane();

        Circle cir = new Circle(200, 200, 100);
        cir.setFill(Color.INDIGO);
        root.getChildren().add(cir);

        Scene scene = new Scene(root, display.getWidth(), display.getHeight());

        primaryStage.setTitle("WireWorld");
        primaryStage.setScene(scene);
        primaryStage.show();

        gameLoop(root, game, display, settings);
    }

    private static void gameLoop(StackPane root, Game game, Display display, Settings settings) {
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
