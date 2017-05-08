


package smartpost_programm;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;

/**
 * FXML Controller class
 *
 * @author Severi Ahopelto & Markus Kyläheiko
 */
public class LogWindowController implements Initializable {

    @FXML
    private TextArea logInfoText;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        logInfoText.setText(Log.getInstance().getText());
        logInfoText.setStyle("-fx-text-fill: #000000");
        
    }    
    
}
