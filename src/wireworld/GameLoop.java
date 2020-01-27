package wireworld;

/**
 *
 * @author Will Allen
 */
public class GameLoop implements Runnable {

    private final Game game;
    private final Display display;
    private final Settings settings;

    public GameLoop(Game game, Display display, Settings settings) {
        this.game = game;
        this.display = display;
        this.settings = settings;
    }

    @Override
    public void run() {

    }

}
