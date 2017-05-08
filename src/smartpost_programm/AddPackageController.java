


package smartpost_programm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.web.WebView;


/**
 * FXML Controller class
 *
 * @author Severi Ahopelto & Markus Kyläheiko
 */
public class AddPackageController implements Initializable {
    
        
    SmartPostMachine spm;
    @FXML
    private TextField nameInput;
    @FXML
    private TextField weightInput;
    @FXML
    private CheckBox fragileButton;
    @FXML
    private CheckBox firstClassButton;
    @FXML
    private CheckBox secondClassButton;
    @FXML
    private CheckBox thirdClassButton;
    @FXML
    private ComboBox<String> sendingCityComboList;
    @FXML
    private ComboBox<String> sendingSpComboList;
    @FXML
    private ComboBox<String> destCityComboList;
    @FXML
    private ComboBox<String> destSpComboList;
    @FXML
    private ComboBox<String> packageComboList;
    @FXML
    private Button createPackage;
    @FXML
    private ComboBox<String> sizeComboList;
    @FXML
    private Button infoButton;
    @FXML
    private Label errorText;
    @FXML
    private WebView web;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        web.getEngine().load(getClass().getResource("index.html").toExternalForm());
        
        try {
            urlGetter("http://smartpost.ee/fi_apt.xml");
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        sizeComboList.getItems().add("Pieni");
        sizeComboList.getItems().add("Keskikokoinen");
        sizeComboList.getItems().add("Suuri");
        packageComboList.getItems().add("Uusi esine");
        packageComboList.getItems().add("Posliininorsu");
        packageComboList.getItems().add("Glitterbomb");
        packageComboList.getItems().add("Risukasa");
        packageComboList.getItems().add("Kokaiinikilo");      
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
        sendingCityComboList.getItems().addAll(spm.getCityList());
        destCityComboList.getItems().addAll(spm.getCityList());  
    }    

    
    @FXML
    private void makeFragile(ActionEvent event) {
        
    }

    @FXML
    private void makeFirstClass(ActionEvent event) {
        secondClassButton.setSelected(false);
        thirdClassButton.setSelected(false);
    }

    @FXML
    private void makeSecondClass(ActionEvent event) {
        firstClassButton.setSelected(false);
        thirdClassButton.setSelected(false);
    }

    @FXML
    private void makeThirdClass(ActionEvent event) {
        firstClassButton.setSelected(false);
        secondClassButton.setSelected(false);
    }

    @FXML
    private void infoAction(ActionEvent event) {
        Label secondLabel = new Label("1. Luokan paketin tiedot:\nKuljetusnopeus:\t Nopea\nPaino:\t\t\t max 20kg\nKoko:\t\t\t Keskikokoinen (19 x 36 x 60 cm) \nLähetysetäisyys:\t alle 150km\nSärkyville:\t\t\t Ei\n"+
                "\n2. Luokan paketin tiedot:\nKuljetusnopeus:\t Normaali\nPaino:\t\t\t max 10kg\nKoko:\t\t\t Pieni (11 x 36 x 60 cm)\nLähetysetäisyys:\t koko Suomi\nSärkyville:\t\t\t Kyllä\n"+
                "\n3. Luokan paketin tiedot:\nKuljetusnopeus:\t Hidas\nPaino:\t\t\t max 50kg\nKoko:\t\t\t Suuri (37 x 36 x 60 cm)\nLähetysetäisyys:\t koko Suomi\nSärkyville:\t\t\t Ei\n");
        StackPane secondaryLayout = new StackPane();
        
        secondaryLayout.getChildren().add(secondLabel);
        Scene secondScene = new Scene(secondaryLayout, 400, 400);
        secondScene.getStylesheets().addAll(getClass().getResource("style.css").toExternalFor‌​m());
        Stage secondStage = new Stage();
        secondStage.setTitle("Info");
        secondStage.setScene(secondScene);
        secondStage.show();
    }

    @FXML
    private void refreshSendingSp(ActionEvent event) {
        sendingSpComboList.getItems().clear();
        for (SmartPost smartpost : spm.getSmartPostsbyCity(sendingCityComboList.getValue())) {
            sendingSpComboList.getItems().addAll(smartpost.getAddress());
        }
    }

    @FXML
    private void refreshDestSp(ActionEvent event) {
        destSpComboList.getItems().clear();
        for (SmartPost smartpost : spm.getSmartPostsbyCity(destCityComboList.getValue())) {
            destSpComboList.getItems().addAll(smartpost.getAddress());
        }
    }

    @FXML
    private void createPackageAction(ActionEvent event) {
        
        errorText.setText("");
        errorText.setStyle("-fx-text-fill: #fff");
        
        Stage stage = (Stage) createPackage.getScene().getWindow();
        //read values for package
        String name = nameInput.getText();
        String weightText = weightInput.getText(); 
        double weight;
        if (weightText.isEmpty()==true) {
            weight = 0;
        } 
        else {
           weight = Double.parseDouble(weightText);
        }
        String size = sizeComboList.getValue();
        Boolean fragile = false;
        if (fragileButton.isSelected())
            fragile = true;

        //read info of destination and send location
          
        String sendCity = sendingCityComboList.getValue();
        String sendAddress = sendingSpComboList.getValue();      
        
        String destCity = destCityComboList.getValue();
        String destAddress = destSpComboList.getValue();
        
        // calculate distance using coordinates (added script to html)
        double distance = 0; 
        
        ArrayList <SmartPost> coordList = new ArrayList();
        
        coordList.addAll(spm.getSmartPostsbyCity(sendCity));
        coordList.addAll(spm.getSmartPostsbyCity(destCity));
        
        float senderLat = Float.valueOf(coordList.get(0).getLatitude());
        float senderLng = Float.valueOf(coordList.get(0).getLongitude());
        
        float receiverLat = Float.valueOf(coordList.get(1).getLatitude());
        float receiverLng = Float.valueOf(coordList.get(1).getLongitude());
        
        ArrayList<Float> arraylist = new ArrayList();
        arraylist.add(senderLat);
        arraylist.add(senderLng); 
        arraylist.add(receiverLat); 
        arraylist.add(receiverLng);
        
        System.out.println(web.getEngine().executeScript("document.pathDist("+arraylist+")"));                
        distance = (Double) web.getEngine().executeScript("document.pathDist("+arraylist+")");
                        
        //error check if info is allowed
        if (sendingSpComboList.getValue()==null || destSpComboList.getValue()==null || destSpComboList.getValue().equals(sendingSpComboList.getValue())){
            errorText.setText("Anna lähetystiedot!");
        }
        else {
            //create new package && check if packageclass fits 
            if ("Uusi esine".equals(packageComboList.getValue())){
                if (firstClassButton.isSelected()){
                    if (weight<=20 && ("Keskikokoinen".equals(size) || "Pieni".equals(size)) && fragile == false && distance<=150){
                        firstClass paketti = new firstClass(weight,size,fragile,name,distance,1,1); 
                        Storage.getInstance().addPackage(paketti);
                        //Add info to log
                        String logText = "Lisätty paketti!\nEsine: "+paketti.getItemName()+"\nPaino: "+paketti.getWeight()+"\nKoko: "+paketti.getSize()+"\nPakettiluokka: " 
                                +paketti.getPackageClass()+".\nLähetysosoite: "+sendAddress+", "+sendCity+"\nVastaanottajan osoite: "+destAddress +", "+destCity+"\n\n";
                        Log.getInstance().addText(logText);
                        //Add info of address to storage 
                        Storage.getInstance().senderAddressList.addAll(spm.getSmartPostbyAddress(sendAddress));
                        Storage.getInstance().receiverAddressList.addAll(spm.getSmartPostbyAddress(destAddress));
                        stage.close();
                    }
                    else {
                        errorText.setText("Väärä pakettiluokka!");
                    }
                }     
                else if (secondClassButton.isSelected()){
                    if (weight<=10 && "Pieni".equals(size)){  
                        secondClass paketti = new secondClass(weight,size,fragile,name,2,2);
                        Storage.getInstance().addPackage(paketti);
                        //Add info to log
                        String logText = "Lisätty paketti!\nEsine: "+paketti.getItemName()+"\nPaino: "+paketti.getWeight()+"\nKoko: "+paketti.getSize()+"\nPakettiluokka: " 
                                +paketti.getPackageClass()+".\nLähetysosoite: "+sendAddress+", "+sendCity+"\nVastaanottajan osoite: "+destAddress +", "+destCity+"\n\n";
                        Log.getInstance().addText(logText);
                        //Add info of address to storage 
                        Storage.getInstance().senderAddressList.addAll(spm.getSmartPostbyAddress(sendAddress));
                        Storage.getInstance().receiverAddressList.addAll(spm.getSmartPostbyAddress(destAddress));
                        stage.close();
                    } 
                    else {
                        errorText.setText("Väärä pakettiluokka!");
                    }
                }
                else if (thirdClassButton.isSelected()){
                    if (weight<=50 && fragile == false ){
                        thirdClass paketti = new thirdClass(weight,size,fragile,name,3,3);
                        Storage.getInstance().addPackage(paketti);
                        //Add info to log
                        String logText = "Lisätty paketti!\nEsine: "+paketti.getItemName()+"\nPaino: "+paketti.getWeight()+"\nKoko: "+paketti.getSize()+"\nPakettiluokka: " 
                                +paketti.getPackageClass()+".\nLähetysosoite: "+sendAddress+", "+sendCity+"\nVastaanottajan osoite: "+destAddress +", "+destCity+"\n\n";
                        Log.getInstance().addText(logText);
                        //Add info of address to storage 
                        Storage.getInstance().senderAddressList.addAll(spm.getSmartPostbyAddress(sendAddress));
                        Storage.getInstance().receiverAddressList.addAll(spm.getSmartPostbyAddress(destAddress));
                        stage.close();
                    } 
                    else {
                        errorText.setText("Väärä pakettiluokka!");
                    }
                }
                else{
                   errorText.setText("Valitse pakettiluokka");
                }
            }       

            //Create package from existing packagetypes && check if chosen packageclass fits 
            else if ("Posliininorsu".equals(packageComboList.getValue())){
                Posliininorsu norsu = new Posliininorsu (); 

                if (firstClassButton.isSelected()){               
                    if (norsu.getWeight()<=20 && ("Keskikokoinen".equals(norsu.getSize()) || "Pieni".equals(norsu.getSize())) && norsu.getFragile()==false && distance<=150){
                        firstClass paketti = new firstClass(norsu.getWeight(),norsu.getSize(),norsu.getFragile(),norsu.getItemName(),distance,1,1);
                        Storage.getInstance().addPackage(paketti);
                        //Add info to log
                        String logText = "Lisätty paketti!\nEsine: "+paketti.getItemName()+"\nPaino: "+paketti.getWeight()+"\nKoko: "+paketti.getSize()+"\nPakettiluokka: " 
                                +paketti.getPackageClass()+".\nLähetysosoite: "+sendAddress+", "+sendCity+"\nVastaanottajan osoite: "+destAddress +", "+destCity+"\n\n";
                        Log.getInstance().addText(logText);
                        //Add info of address to storage 
                        Storage.getInstance().senderAddressList.addAll(spm.getSmartPostbyAddress(sendAddress));
                        Storage.getInstance().receiverAddressList.addAll(spm.getSmartPostbyAddress(destAddress));
                        stage.close();
                    } 
                    else {
                        errorText.setText("Väärä pakettiluokka!");
                    }
                }

                else if (secondClassButton.isSelected()){
                    if (norsu.getWeight()<=10 && "Pieni".equals(norsu.getSize())){  
                        secondClass paketti = new secondClass(norsu.getWeight(),norsu.getSize(),norsu.getFragile(),norsu.getItemName(),2,2); 
                        Storage.getInstance().addPackage(paketti);
                        //Add info to log
                        String logText = "Lisätty paketti!\nEsine: "+paketti.getItemName()+"\nPaino: "+paketti.getWeight()+"\nKoko: "+paketti.getSize()+"\nPakettiluokka: " 
                                +paketti.getPackageClass()+".\nLähetysosoite: "+sendAddress+", "+sendCity+"\nVastaanottajan osoite: "+destAddress +", "+destCity+"\n\n";
                        Log.getInstance().addText(logText);
                        //Add info of address to storage 
                        Storage.getInstance().senderAddressList.addAll(spm.getSmartPostbyAddress(sendAddress));
                        Storage.getInstance().receiverAddressList.addAll(spm.getSmartPostbyAddress(destAddress));
                        stage.close();
                    }
                    else {
                        errorText.setText("Väärä pakettiluokka!");
                    }
                }
                else if (thirdClassButton.isSelected()){
                    if (norsu.getWeight()<=50 && norsu.getFragile()==false){
                        thirdClass paketti = new thirdClass(norsu.getWeight(),norsu.getSize(),norsu.getFragile(),norsu.getItemName(),3,3);
                        Storage.getInstance().addPackage(paketti);
                        //Add info to log
                        String logText = "Lisätty paketti!\nEsine: "+paketti.getItemName()+"\nPaino: "+paketti.getWeight()+"\nKoko: "+paketti.getSize()+"\nPakettiluokka: " 
                                +paketti.getPackageClass()+".\nLähetysosoite: "+sendAddress+", "+sendCity+"\nVastaanottajan osoite: "+destAddress +", "+destCity+"\n\n";
                        Log.getInstance().addText(logText);
                        //Add info of address to storage 
                        Storage.getInstance().senderAddressList.addAll(spm.getSmartPostbyAddress(sendAddress));
                        Storage.getInstance().receiverAddressList.addAll(spm.getSmartPostbyAddress(destAddress));
                        stage.close();
                    } 
                    else {
                        errorText.setText("Väärä pakettiluokka!");
                    }
                }

                else{
                   errorText.setText("Valitse pakettiluokka");
                }

            }
            else if ("Glitterbomb".equals(packageComboList.getValue())){
                GlitterBomb glitterbomb = new GlitterBomb (); 

                if (firstClassButton.isSelected()){
                    if (glitterbomb.getWeight()<=20 && ("Keskikokoinen".equals(glitterbomb.getSize()) || "Pieni".equals(glitterbomb.getSize())) && glitterbomb.getFragile()==false && distance<=150){
                        firstClass paketti = new firstClass(glitterbomb.getWeight(),glitterbomb.getSize(),glitterbomb.getFragile(),glitterbomb.getItemName(),distance,1,1); 
                        Storage.getInstance().addPackage(paketti);
                        //Add info to log
                        String logText = "Lisätty paketti!\nEsine: "+paketti.getItemName()+"\nPaino: "+paketti.getWeight()+"\nKoko: "+paketti.getSize()+"\nPakettiluokka: " 
                                +paketti.getPackageClass()+".\nLähetysosoite: "+sendAddress+", "+sendCity+"\nVastaanottajan osoite: "+destAddress +", "+destCity+"\n\n";
                        Log.getInstance().addText(logText);
                        //Add info of address to storage 
                        Storage.getInstance().senderAddressList.addAll(spm.getSmartPostbyAddress(sendAddress));
                        Storage.getInstance().receiverAddressList.addAll(spm.getSmartPostbyAddress(destAddress));
                        stage.close();
                    }  
                    else {
                        errorText.setText("Väärä pakettiluokka!");
                    }
                }

                else if (secondClassButton.isSelected()){
                    if (glitterbomb.getWeight()<=10 && "Pieni".equals(glitterbomb.getSize())){  
                        secondClass paketti = new secondClass(glitterbomb.getWeight(),glitterbomb.getSize(),glitterbomb.getFragile(),glitterbomb.getItemName(),2,2);  
                        Storage.getInstance().addPackage(paketti);
                        //Add info to log
                        String logText = "Lisätty paketti!\nEsine: "+paketti.getItemName()+"\nPaino: "+paketti.getWeight()+"\nKoko: "+paketti.getSize()+"\nPakettiluokka: " 
                                +paketti.getPackageClass()+".\nLähetysosoite: "+sendAddress+", "+sendCity+"\nVastaanottajan osoite: "+destAddress +", "+destCity+"\n\n";
                        Log.getInstance().addText(logText);
                        //Add info of address to storage 
                        Storage.getInstance().senderAddressList.addAll(spm.getSmartPostbyAddress(sendAddress));
                        Storage.getInstance().receiverAddressList.addAll(spm.getSmartPostbyAddress(destAddress));
                        stage.close();
                    } 
                    else {
                        errorText.setText("Väärä pakettiluokka!");
                    }
                }
                else if (thirdClassButton.isSelected()){
                    if (glitterbomb.getWeight()<=50 && glitterbomb.getFragile()==false){
                        thirdClass paketti = new thirdClass(glitterbomb.getWeight(),glitterbomb.getSize(),glitterbomb.getFragile(),glitterbomb.getItemName(),3,3);
                        Storage.getInstance().addPackage(paketti);
                        //Add info to log
                        String logText = "Lisätty paketti!\nEsine: "+paketti.getItemName()+"\nPaino: "+paketti.getWeight()+"\nKoko: "+paketti.getSize()+"\nPakettiluokka: " 
                                +paketti.getPackageClass()+".\nLähetysosoite: "+sendAddress+", "+sendCity+"\nVastaanottajan osoite: "+destAddress +", "+destCity+"\n\n";
                        Log.getInstance().addText(logText);
                        //Add info of address to storage 
                        Storage.getInstance().senderAddressList.addAll(spm.getSmartPostbyAddress(sendAddress));
                        Storage.getInstance().receiverAddressList.addAll(spm.getSmartPostbyAddress(destAddress));
                        stage.close();
                    }
                    else {
                        errorText.setText("Väärä pakettiluokka!");
                    }
                }

                else{
                   errorText.setText("Valitse pakettiluokka");
                }                
            }
            else if ("Risukasa".equals(packageComboList.getValue())){
                Risuja risukasa = new Risuja(); 

                if (firstClassButton.isSelected()){
                    if (risukasa.getWeight()<=20 && ("Keskikokoinen".equals(risukasa.getSize()) || "Pieni".equals(risukasa.getSize())) && risukasa.getFragile()==false && distance<=150){
                        firstClass paketti = new firstClass(risukasa.getWeight(),risukasa.getSize(),risukasa.getFragile(),risukasa.getItemName(),distance,1,1);   
                        Storage.getInstance().addPackage(paketti);
                        //Add info to log
                        String logText = "Lisätty paketti!\nEsine: "+paketti.getItemName()+"\nPaino: "+paketti.getWeight()+"\nKoko: "+paketti.getSize()+"\nPakettiluokka: " 
                                +paketti.getPackageClass()+".\nLähetysosoite: "+sendAddress+", "+sendCity+"\nVastaanottajan osoite: "+destAddress +", "+destCity+"\n\n";
                        Log.getInstance().addText(logText);
                        //Add info of address to storage 
                        Storage.getInstance().senderAddressList.addAll(spm.getSmartPostbyAddress(sendAddress));
                        Storage.getInstance().receiverAddressList.addAll(spm.getSmartPostbyAddress(destAddress));
                        stage.close();
                    }
                    else {
                        errorText.setText("Väärä pakettiluokka!");
                    }
                }

                else if (secondClassButton.isSelected()){
                    if (risukasa.getWeight()<=10 && "Pieni".equals(risukasa.getSize())){
                        secondClass paketti = new secondClass(risukasa.getWeight(),risukasa.getSize(),risukasa.getFragile(),risukasa.getItemName(),2,2); 
                        Storage.getInstance().addPackage(paketti);
                        //Add info to log
                        String logText = "Lisätty paketti!\nEsine: "+paketti.getItemName()+"\nPaino: "+paketti.getWeight()+"\nKoko: "+paketti.getSize()+"\nPakettiluokka: " 
                                +paketti.getPackageClass()+".\nLähetysosoite: "+sendAddress+", "+sendCity+"\nVastaanottajan osoite: "+destAddress +", "+destCity+"\n\n";
                        Log.getInstance().addText(logText);
                        //Add info of address to storage 
                        Storage.getInstance().senderAddressList.addAll(spm.getSmartPostbyAddress(sendAddress));
                        Storage.getInstance().receiverAddressList.addAll(spm.getSmartPostbyAddress(destAddress));
                        stage.close();
                    }
                    else {
                        errorText.setText("Väärä pakettiluokka!");
                    }
                }
                else if (thirdClassButton.isSelected()){
                    if (risukasa.getWeight()<=50 && risukasa.getFragile()==false ){
                        thirdClass paketti = new thirdClass(risukasa.getWeight(),risukasa.getSize(),risukasa.getFragile(),risukasa.getItemName(),3,3);
                        Storage.getInstance().addPackage(paketti);
                        //Add info to log
                        String logText = "Lisätty paketti!\nEsine: "+paketti.getItemName()+"\nPaino: "+paketti.getWeight()+"\nKoko: "+paketti.getSize()+"\nPakettiluokka: " 
                                +paketti.getPackageClass()+".\nLähetysosoite: "+sendAddress+", "+sendCity+"\nVastaanottajan osoite: "+destAddress +", "+destCity+"\n\n";
                        Log.getInstance().addText(name);
                        //Add info of address to storage 
                        Storage.getInstance().senderAddressList.addAll(spm.getSmartPostbyAddress(sendAddress));
                        Storage.getInstance().receiverAddressList.addAll(spm.getSmartPostbyAddress(destAddress));
                        stage.close();
                    }
                    else {
                        errorText.setText("Väärä pakettiluokka!");
                    }
                }

                else{
                   errorText.setText("Valitse pakettiluokka");
                }                
            }
            else if ("Kokaiinikilo".equals(packageComboList.getValue())){
                Kokaiinikilo kokaiinikilo = new Kokaiinikilo (); 

                if (firstClassButton.isSelected()){
                    if (kokaiinikilo.getWeight()<=20 && ("Keskikokoinen".equals(kokaiinikilo.getSize()) || "Pieni".equals(kokaiinikilo.getSize())) && kokaiinikilo.getFragile()==false && distance<=150){
                        firstClass paketti = new firstClass(kokaiinikilo.getWeight(),kokaiinikilo.getSize(),kokaiinikilo.getFragile(),kokaiinikilo.getItemName(),distance,1,1); 
                        Storage.getInstance().addPackage(paketti);
                        //Add info to log
                        String logText = "Lisätty paketti!\nEsine: "+paketti.getItemName()+"\nPaino: "+paketti.getWeight()+"\nKoko: "+paketti.getSize()+"\nPakettiluokka: " 
                                +paketti.getPackageClass()+".\nLähetysosoite: "+sendAddress+", "+sendCity+"\nVastaanottajan osoite: "+destAddress +", "+destCity+"\n\n";
                        Log.getInstance().addText(logText);
                        //Add info of address to storage 
                        Storage.getInstance().senderAddressList.addAll(spm.getSmartPostbyAddress(sendAddress));
                        Storage.getInstance().receiverAddressList.addAll(spm.getSmartPostbyAddress(destAddress));
                        stage.close();
                    } 
                    else {
                        errorText.setText("Väärä pakettiluokka!");
                    }
                }

                else if (secondClassButton.isSelected()){
                    if (kokaiinikilo.getWeight()<=10 && "Pieni".equals(kokaiinikilo.getSize())){
                        secondClass paketti = new secondClass(kokaiinikilo.getWeight(),kokaiinikilo.getSize(),kokaiinikilo.getFragile(),kokaiinikilo.getItemName(),2,2); 
                        Storage.getInstance().addPackage(paketti);
                        //Add info to log
                        String logText = "Lisätty paketti!\nEsine: "+paketti.getItemName()+"\nPaino: "+paketti.getWeight()+"\nKoko: "+paketti.getSize()+"\nPakettiluokka: " 
                                +paketti.getPackageClass()+".\nLähetysosoite: "+sendAddress+", "+sendCity+"\nVastaanottajan osoite: "+destAddress +", "+destCity+"\n\n";
                        Log.getInstance().addText(logText);
                        //Add info of address to storage 
                        Storage.getInstance().senderAddressList.addAll(spm.getSmartPostbyAddress(sendAddress));
                        Storage.getInstance().receiverAddressList.addAll(spm.getSmartPostbyAddress(destAddress));
                        stage.close();
                    }
                    else {
                        errorText.setText("Väärä pakettiluokka!");
                    }
                }
                else if (thirdClassButton.isSelected()){
                    if (kokaiinikilo.getWeight()<=50 && kokaiinikilo.getFragile()==false){
                        thirdClass paketti = new thirdClass(kokaiinikilo.getWeight(),kokaiinikilo.getSize(),kokaiinikilo.getFragile(),kokaiinikilo.getItemName(),3,3); 
                        Storage.getInstance().addPackage(paketti);
                        //Add info to log
                        String logText = "Lisätty paketti!\nEsine: "+paketti.getItemName()+"\nPaino: "+paketti.getWeight()+"\nKoko: "+paketti.getSize()+"\nPakettiluokka: " 
                                +paketti.getPackageClass()+".\nLähetysosoite: "+sendAddress+", "+sendCity+"\nVastaanottajan osoite: "+destAddress +", "+destCity+"\n\n";
                        Log.getInstance().addText(logText);
                        //Add info of address to storage 
                        Storage.getInstance().senderAddressList.addAll(spm.getSmartPostbyAddress(sendAddress));
                        Storage.getInstance().receiverAddressList.addAll(spm.getSmartPostbyAddress(destAddress));
                        stage.close();
                    } 
                    else {
                        errorText.setText("Väärä pakettiluokka!");
                    }
                }
                else{
                   errorText.setText("Valitse pakettiluokka");
                }                    
           }
       }
    }
        

   
}
