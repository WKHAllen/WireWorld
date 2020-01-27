package wireworld;

/**
 *
 * @author Will Allen
 */
public class GameLoop implements Runnable {

    private final Game game;
    private final Display display;
    private final Settings settings;
    public boolean running;

    public GameLoop(Game game, Display display, Settings settings) {
        this.game = game;
        this.display = display;
        this.settings = settings;
        this.running = false;
    }

    @Override
    public void run() {
        this.running = true;
        while (this.running) {
            display.display();
            game.step();
        }
    }

    public void stop() {
        this.running = false;
    }

    public void togglePause() {
        this.running = !this.running;
    }

}
