package wireworld;

import java.io.IOException;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author Will Allen
 */
public class WireWorld extends Application {

    private boolean running = false;

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
        Group root = new Group();
        Display display = new Display(root, game, cellSize, padding);

        Circle cir = new Circle(0, 0, 100);
        cir.setFill(Color.INDIGO);
        root.getChildren().add(cir);

        Circle cir2 = new Circle(200, 200, 80);
        cir2.setFill(Color.LIGHTGREEN);
        root.getChildren().add(cir2);

        Scene scene = new Scene(root, display.getWidth(), display.getHeight());

        primaryStage.setTitle("WireWorld");
        primaryStage.setScene(scene);
        primaryStage.show();

        this.running = true;

        AnimationTimer gameloop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                display.display();
                game.step();
            }
        };

        gameloop.start();

        int[] mousePos = {-1, -1};

        scene.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (display.onScreen((int) event.getSceneX(), (int) event.getSceneY())) {
                    int[] newMousePos = display.coordsToPos((int) event.getSceneX(), (int) event.getSceneY());
                    if (mousePos[0] != newMousePos[0] || mousePos[1] != newMousePos[1]) {
                        mousePos[0] = newMousePos[0];
                        mousePos[1] = newMousePos[1];
                        if (event.isPrimaryButtonDown()) {
                            switch (game.get(newMousePos[0], newMousePos[1])) {
                                case EMPTY:
                                    game.set(newMousePos[0], newMousePos[1], GameCell.CONDUCTOR);
                                    break;
                                case CONDUCTOR:
                                    game.set(newMousePos[0], newMousePos[1], GameCell.EMPTY);
                                    break;
                            }
                        } else if (event.isSecondaryButtonDown()) {
                            switch (game.get(newMousePos[0], newMousePos[1])) {
                                case EMPTY:
                                    game.set(newMousePos[0], newMousePos[1], GameCell.EHEAD);
                                    break;
                                case EHEAD:
                                    game.set(newMousePos[0], newMousePos[1], GameCell.EMPTY);
                                    break;
                            }
                        }
                    }
                }
            }
        });

        scene.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCharacter()) {
                    case " ":
                        if (running) {
                            gameloop.stop();
                            running = false;
                        } else {
                            gameloop.start();
                            running = true;
                        }
                        break;
                }
            }
        });

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                gameloop.stop();
                running = false;
            }
        });
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
