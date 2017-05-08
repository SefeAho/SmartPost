



package smartpost_programm;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Severi Ahopelto & Markus Kyläheiko
 */
public class SmartPost_Programm extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        root.getStylesheets().addAll(getClass().getResource("style.css").toExternalFor‌​m());
        Scene scene = new Scene(root);
        
       // scene.getStylesheets().addAll("smartpost_programm/Christmas.css");
        stage.setScene(scene);    
        stage.show();
    }
  
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}