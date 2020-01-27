package wireworld;

import java.io.IOException;
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
        StackPane root = new StackPane();
        Display display = new Display(root, game, cellSize, padding);

        Circle cir = new Circle(200, 200, 100);
        cir.setFill(Color.INDIGO);
        root.getChildren().add(cir);

        Scene scene = new Scene(root, display.getWidth(), display.getHeight());

        primaryStage.setTitle("WireWorld");
        primaryStage.setScene(scene);
        primaryStage.show();

        Runnable gameloop = new GameLoop(game, display, settings);
        Thread thread = new Thread(gameloop);
        thread.setDaemon(true);
        thread.start();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
