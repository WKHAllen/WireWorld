package wireworld;

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
