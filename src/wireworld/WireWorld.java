package wireworld;

import java.io.IOException;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author Will Allen
 */
public class WireWorld extends Application {

    private boolean running = false;
    private int[] mousePos = {-1, -1};

    @Override
    public void start(Stage primaryStage) {
        // get settings
        Settings settings = null;
        try {
            settings = new Settings("game.properties", "WireWorld game settings");
            settings.setDefault("width", "40");
            settings.setDefault("height", "30");
            settings.setDefault("cellSize", "20");
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
        Group root = new Group();
        Display display = new Display(root, game, cellSize, padding);

        Scene scene = new Scene(root, display.getWidth(), display.getHeight());

        primaryStage.setTitle("WireWorld");
        primaryStage.setScene(scene);
        primaryStage.show();

        this.running = true;

        AnimationTimer gameloop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                display.display();
                if (running) {
                    game.step();
                }
            }
        };

        gameloop.start();
        
        scene.setOnMousePressed((MouseEvent event) -> {
            mousePos = display.coordsToPos((int) event.getSceneX(), (int) event.getSceneY());
            if (event.isPrimaryButtonDown()) {
                switch (game.get(mousePos[0], mousePos[1])) {
                    case EMPTY:
                    case EHEAD:
                    case ETAIL:
                        game.set(mousePos[0], mousePos[1], GameCell.CONDUCTOR);
                        break;
                    case CONDUCTOR:
                        game.set(mousePos[0], mousePos[1], GameCell.EMPTY);
                        break;
                }
            } else if (event.isSecondaryButtonDown()) {
                switch (game.get(mousePos[0], mousePos[1])) {
                    case EMPTY:
                    case CONDUCTOR:
                        game.set(mousePos[0], mousePos[1], GameCell.EHEAD);
                        break;
                    case EHEAD:
                    case ETAIL:
                        game.set(mousePos[0], mousePos[1], GameCell.CONDUCTOR);
                        break;
                }
            }
        });

        scene.setOnMouseDragged((MouseEvent event) -> {
            if (display.onScreen((int) event.getSceneX(), (int) event.getSceneY())) {
                int[] newMousePos = display.coordsToPos((int) event.getSceneX(), (int) event.getSceneY());
                if (mousePos[0] != newMousePos[0] || mousePos[1] != newMousePos[1]) {
                    mousePos[0] = newMousePos[0];
                    mousePos[1] = newMousePos[1];
                    if (event.isPrimaryButtonDown()) {
                        switch (game.get(newMousePos[0], newMousePos[1])) {
                            case EMPTY:
                            case EHEAD:
                            case ETAIL:
                                game.set(newMousePos[0], newMousePos[1], GameCell.CONDUCTOR);
                                break;
                            case CONDUCTOR:
                                game.set(newMousePos[0], newMousePos[1], GameCell.EMPTY);
                                break;
                        }
                    } else if (event.isSecondaryButtonDown()) {
                        switch (game.get(newMousePos[0], newMousePos[1])) {
                            case EMPTY:
                            case CONDUCTOR:
                                game.set(newMousePos[0], newMousePos[1], GameCell.EHEAD);
                                break;
                            case EHEAD:
                            case ETAIL:
                                game.set(newMousePos[0], newMousePos[1], GameCell.CONDUCTOR);
                                break;
                        }
                    }
                }
            }
        });

        scene.setOnKeyTyped((KeyEvent event) -> {
            switch (event.getCharacter()) {
                case " ":
                    if (running) {
                        running = false;
                    } else {
                        running = true;
                    }
                    break;
                case "\n":
                case "\r":
                    game.step();
                    display.display();
                    break;
            }
        });

        primaryStage.setOnCloseRequest((WindowEvent event) -> {
            gameloop.stop();
            running = false;
        });
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
