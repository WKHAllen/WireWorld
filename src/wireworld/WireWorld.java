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
        GameProperties settings = null;
        try {
            settings = new GameProperties("game.properties", "WireWorld game settings");
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        try {
            settings.set("width", "800");
            settings.set("height", "600");
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        System.out.println(settings.get("width"));
        System.out.println(settings.get("height"));
        
        StackPane root = new StackPane();
        
        Circle cir = new Circle(200, 200, 100);
        cir.setFill(Color.INDIGO);
        root.getChildren().add(cir);
        
        Scene scene = new Scene(root, 400, 400);
        
        primaryStage.setTitle("WireWorld");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
