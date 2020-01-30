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

    private Stage stage;
    private boolean running = false;
    private int speed;
    private int[] mousePos = {-1, -1};

    @Override
    public void start(Stage primaryStage) {
        this.stage = primaryStage;

        // get settings
        final Settings settings;
        try {
            settings = new Settings("game.properties", "WireWorld game settings");
            settings.setDefault("width", "40");
            settings.setDefault("height", "30");
            settings.setDefault("cellSize", "20");
            settings.setDefault("padding", "1");
            settings.setDefault("speed", "20");
        } catch (IOException e) {
            System.out.println("Failed to read game properties");
            e.printStackTrace();
            this.stage.close();
            return;
        }

        // initialize the game
        int width = Integer.parseInt(settings.get("width"));
        int height = Integer.parseInt(settings.get("height"));
        final Game game = new Game(width, height);

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
        this.speed = Integer.parseInt(settings.get("speed"));

        AnimationTimer gameloop = new AnimationTimer() {

            private long lastFrame = 0;

            @Override
            public void handle(long now) {
                if (now - this.lastFrame < 1000000000 / ((double) speed)) {
                    try {
                        Thread.sleep((long) ((1000000000 / ((double) speed) - (now - this.lastFrame)) / 1000000));
                    } catch (InterruptedException e) {
                        System.out.println("Failed to sleep in gameloop");
                        e.printStackTrace();
                    }
                }
                this.lastFrame = now;
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

        scene.setOnKeyPressed((KeyEvent event) -> {
            switch (event.getCode()) {
                case SPACE:
                    running = !running;
                    break;
                case ENTER:
                    game.step();
                    display.display();
                    break;
                case BACK_SPACE:
                case DELETE:
                    game.clear();
                    break;
                case UP:
                    if (speed < 60) {
                        speed++;
                    }
                    break;
                case DOWN:
                    if (speed > 2) {
                        speed--;
                    }
                    break;
            }
        });

        primaryStage.setOnCloseRequest((WindowEvent event) -> {
            gameloop.stop();
            saveGame(game, settings, -1);
            running = false;
        });
    }

    public void saveGame(Game game, Settings settings, int saveId) {
        String filename;
        String ext = ".wws";
        if (saveId == -1) {
            filename = "current" + ext;
        } else {
            filename = "game-" + Integer.toString(saveId) + ext;
        }

        try {
            Save.save(filename, game, settings);
        } catch (IOException e) {
            System.out.println("Failed to save game");
            e.printStackTrace();
        }
    }

    public void loadGame(Game game) {
        // TODO: implement this
        this.stage.close();
        launch(new String[]{});
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
