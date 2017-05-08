

package smartpost_programm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

/**
 *
 * @author Severi Ahopelto & Markus Kyläheiko
 */
public class FXMLDocumentController implements Initializable {
    
    //Create warehouse storage 
    
    @FXML
    private WebView web;
    @FXML
    private ComboBox<String> spComboList;
    @FXML
    private Button addSpButton;   
    SmartPostMachine spm;
    @FXML
    private Button deleteSpButton;
    @FXML
    private Button addPackageButton;
    @FXML
    private Button sendPackageButton;
    @FXML
    private ComboBox<String> packageComboList;
    @FXML
    private Button removePathButton;
    @FXML
    private Label headlineLabel;
    @FXML
    private Button exitButton;
    @FXML
    private Button logButton;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        web.getEngine().load(getClass().getResource("index.html").toExternalForm());
        Storage.getInstance();               
                     
        try {
            urlGetter("http://smartpost.ee/fi_apt.xml");
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        //CSS headline
        headlineLabel.setStyle("-fx-background-image: url('smartpost_programm/header.png')");
            
    }

    private void urlGetter(String urlName) throws IOException{
        URL url = new URL(urlName);
        BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
        String content = "";
        String line;
        while((line = br.readLine()) != null) {
            content += line + "\n";
        }
    spm = new SmartPostMachine(content);
    spComboList.getItems().addAll(spm.getCityList());
    }    


    @FXML
    private void deleteSp(ActionEvent event) {
        //refresh map -> all markers dissapear
        web.getEngine().load(getClass().getResource("index.html").toExternalForm());
        
    }

    @FXML
    private void addSp(ActionEvent event) {
        //Show all smartposts in the chosen city 
        for (SmartPost smartpost : spm.getSmartPostsbyCity(spComboList.getValue())) {
            web.getEngine().executeScript("document.goToLocation('"+smartpost.getAddress()+" "+smartpost.getCode()+" "+spComboList.getValue()+"','"+smartpost.getAvailability()+"' , 'red')");
        }
    }


    @FXML
    private void addPackage(ActionEvent event) throws Exception {
        try {
            //Initialize new windows for making a package
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("addPackage.fxml"));
            Parent Root = (Parent) fxmlLoader.load();
            Root.getStylesheets().addAll(getClass().getResource("style.css").toExternalFor‌​m());
            Stage stage = new Stage();
            stage.setTitle("Luo paketti");
            stage.setScene(new Scene(Root));
            stage.show();
        } catch(Exception e) {
            System.out.println("Cant load new window.");
        }

    }

    @FXML
    private void sendPackage(ActionEvent event) {
        
        //do only if there are existing packages in storage and chosen
         if (packageComboList.getValue()!=null){
            int i = 0;
         
         //get the chosen package info from warehouse
            while (i< Storage.getInstance().packageList.size()){
                if (Storage.getInstance().packageList.get(i).itemName.equals(packageComboList.getValue())) {                
                    break;
                }
                else {
                   i++; 
                }            
            }       
            
            //add sender and receiver locationpoints on map
            web.getEngine().executeScript("document.goToLocation('"+Storage.getInstance().senderAddressList.get(i).getAddress()+" "+Storage.getInstance().senderAddressList.get(i).getCode()+" "
                    +Storage.getInstance().senderAddressList.get(i).getName()+"','"+Storage.getInstance().senderAddressList.get(i).getAvailability()+"' , 'red')");
            web.getEngine().executeScript("document.goToLocation('"+Storage.getInstance().receiverAddressList.get(i).getAddress()+" "+Storage.getInstance().receiverAddressList.get(i).getCode()+" "
                    +Storage.getInstance().receiverAddressList.get(i).getName()+"','"+Storage.getInstance().receiverAddressList.get(i).getAvailability()+"' , 'red')");

            //Draw path using coordinates of sender and receiver 
            ArrayList<Float> arraylist = new ArrayList();

            float senderLat = Float.valueOf(Storage.getInstance().senderAddressList.get(i).getLatitude());
            float senderLng = Float.valueOf(Storage.getInstance().senderAddressList.get(i).getLongitude());

            float receiverLat = Float.valueOf(Storage.getInstance().receiverAddressList.get(i).getLatitude());
            float receiverLng = Float.valueOf(Storage.getInstance().receiverAddressList.get(i).getLongitude());

            arraylist.add(senderLat);
            arraylist.add(senderLng); 
            arraylist.add(receiverLat); 
            arraylist.add(receiverLng);

           web.getEngine().executeScript("document.createPath("+arraylist+", 'red', 1)");
           
           //Mark to log
           String logText = "Paketti lähetetty!\nEsine: "+Storage.getInstance().packageList.get(i).getItemName()+"\nPaino: "+Storage.getInstance().packageList.get(i).getWeight()+"\nKoko: "+Storage.getInstance().packageList.get(i).getSize()+"\nLähetysosoite: "+
                   Storage.getInstance().senderAddressList.get(i).getAddress()+", "+Storage.getInstance().senderAddressList.get(i).getName()+"\nVastaanottajan osoite: "+Storage.getInstance().receiverAddressList.get(i).getAddress() +", "+Storage.getInstance().receiverAddressList.get(i).getName()+"\n\n";
           Log.getInstance().addText(logText);
           
            //remove sent package from storage 
            Storage.getInstance().senderAddressList.remove(i);
            Storage.getInstance().receiverAddressList.remove(i);
            Storage.getInstance().packageList.remove(i);
            
            //Make new list of sendable packages to combolistbutton
            packageComboList.getItems().clear();
            for(i = 0; i < Storage.getInstance().packageList.size(); i++) {
                packageComboList.getItems().add(Storage.getInstance().packageList.get(i).getItemName());
            }
           
        }
    }

    @FXML
    private void loadPackages(MouseEvent event) {
        //Show packages from warehouse on combolist
        packageComboList.getItems().clear();
        for(int i = 0; i < Storage.getInstance().packageList.size(); i++) {
            packageComboList.getItems().add(Storage.getInstance().packageList.get(i).getItemName());
        }
    }

    @FXML
    private void deletePaths(ActionEvent event) {
        web.getEngine().executeScript("document.deletePaths()");
    }

    @FXML
    private void exitProgram(ActionEvent event) throws IOException {
        //Create loki.txt file with info of user mader actions and warehouse items 
        String logText = "--------------------------------------------------\nVarasto\n\n";
        for(int i = 0; i < Storage.getInstance().packageList.size(); i++) {
            logText = logText + "Esine: "+Storage.getInstance().packageList.get(i).getItemName()+"\nPaino: "+Storage.getInstance().packageList.get(i).getWeight()+"\nKoko: "+Storage.getInstance().packageList.get(i).getSize()+"\nLähetysosoite: "+
                    Storage.getInstance().senderAddressList.get(i).getAddress()+", "+Storage.getInstance().senderAddressList.get(i).getName()+"\nVastaanottajan osoite: "+Storage.getInstance().receiverAddressList.get(i).getAddress() +", "+Storage.getInstance().receiverAddressList.get(i).getName()+"\n\n"; 
        }
         Log.getInstance().addText(logText);
         Log.getInstance().writeToLogFile();
         Platform.exit();
    }

    @FXML
    private void bringLog(ActionEvent event) {
      try {
          //initialize new log window 
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("logWindow.fxml"));
            Parent Root = (Parent) fxmlLoader.load();
            Root.getStylesheets().addAll(getClass().getResource("style.css").toExternalFor‌​m());
            Stage stage = new Stage();
            stage.setTitle("Lokitiedot");
            stage.setScene(new Scene(Root));
            stage.show();
        } catch(Exception e) {
            System.out.println("Cant load new window.");
        }
    }

    
}
