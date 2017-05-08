




package smartpost_programm;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 *
 * @author Severi Ahopelto & Markus Kyläheiko
 */
public class SmartPostMachine {

    private ArrayList<SmartPost> sp_list = new ArrayList();
    private ArrayList<String> city_list = new ArrayList();
    private ArrayList<String> allCity_list = new ArrayList();
    private ArrayList<String> address_list = new ArrayList();
    private ArrayList<String> code_list = new ArrayList();
    private ArrayList<String> av_list = new ArrayList();
    private Document doc;
            
    public SmartPostMachine (String content) {
        
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = dbFactory.newDocumentBuilder();
            doc = documentBuilder.parse(new InputSource(new StringReader(content)));
            doc.getDocumentElement().normalize();

            NodeList listOfPlaces = doc.getElementsByTagName("place");
            int totalPlaces = listOfPlaces.getLength();
            //Number of SmartPosts

            for (int i = 0; i < listOfPlaces.getLength(); i++) {
                Node firstPlaceNode = listOfPlaces.item(i);
                if(firstPlaceNode.getNodeType() == Node.ELEMENT_NODE){

                    Element firstPlaceElement = (Element)firstPlaceNode;

                    NodeList codesList = firstPlaceElement.getElementsByTagName("code");
                    Element codeElement = (Element)codesList.item(0);
                    NodeList textCodeList = codeElement.getChildNodes();
                    String placeListItem = ((Node)textCodeList.item(0)).getNodeValue().trim();
                    //Get code of each SmartPost

                    NodeList citiesList = firstPlaceElement.getElementsByTagName("city");
                    Element cityElement = (Element)citiesList.item(0);
                    NodeList textCityList = cityElement.getChildNodes();
                    String cityListItem = ((Node)textCityList.item(0)).getNodeValue().trim().toUpperCase();
                    //Get city of each SmartPost

                    NodeList addressesList = firstPlaceElement.getElementsByTagName("address");
                    Element addressElement = (Element)addressesList.item(0);
                    NodeList textAddressList = addressElement.getChildNodes();
                    String addressListItem = ((Node)textAddressList.item(0)).getNodeValue().trim();
                    //Get address of each SmartPost

                    NodeList avaList = firstPlaceElement.getElementsByTagName("availability");
                    Element avaElement = (Element)avaList.item(0);
                    NodeList textAvaList = avaElement.getChildNodes();
                    String avaListItem = ((Node)textAvaList.item(0)).getNodeValue().trim();
                    //Get availability of each SmartPost

                    NodeList postList = firstPlaceElement.getElementsByTagName("postoffice");
                    Element postElement = (Element)postList.item(0);
                    NodeList textPostList = postElement.getChildNodes();
                    String postListItem = ((Node)textPostList.item(0)).getNodeValue().trim();
                    //Get postoffice of each SmartPost

                    NodeList latList = firstPlaceElement.getElementsByTagName("lat");
                    Element latElement = (Element)latList.item(0);
                    NodeList textLatList = latElement.getChildNodes();
                    String latListItem = ((Node)textLatList.item(0)).getNodeValue().trim();
                    //Get latitude of each SmartPost

                    NodeList lngList = firstPlaceElement.getElementsByTagName("lng");
                    Element lngElement = (Element)lngList.item(0);
                    NodeList textLngList = lngElement.getChildNodes();
                    String lngListItem = ((Node)textLngList.item(0)).getNodeValue().trim();
                    //Get longitude of each SmartPost
                    
                    SmartPost sp = new SmartPost(placeListItem, cityListItem, addressListItem, avaListItem, postListItem, latListItem, lngListItem);
                    if (!city_list.contains(cityListItem)) {
                        city_list.add(cityListItem);
                    }
                    address_list.add(addressListItem);
                    code_list.add(placeListItem);
                    av_list.add(avaListItem);
                    sp_list.add(sp);
                    allCity_list.add(cityListItem);
                    // Add items to lists
                }
            }
            Collections.sort(city_list);
            Collections.sort(allCity_list);
        } 
        catch (ParserConfigurationException | SAXException | IOException e) {
        }    
        
    }
    public ArrayList<String> getCityList() {
        return city_list;
    }
    
    public ArrayList<String> getAddressList() {
        return address_list;
    }
    
    public ArrayList<String> getCodeList() {
        return code_list;
    }

    public ArrayList<String> getAvList() {
        return av_list;
    }
    
    public ArrayList<String> getAllCityList() {
        return allCity_list;
    }
    
    public ArrayList<SmartPost> getSmartPostsbyCity(String s) {
        ArrayList<SmartPost> SmartPostList = new ArrayList();
        int i;
        for (i=0; i < sp_list.size(); i++) {
            if (sp_list.get(i).getName().equals(s)) {
                SmartPostList.add(sp_list.get(i));
            }
        }
        return SmartPostList;
    }
    // All SmartPosts from one city
    
        public ArrayList<SmartPost> getSmartPostbyAddress(String s) {
        ArrayList<SmartPost> SmartPostList = new ArrayList();
        int i;
        for (i=0; i < sp_list.size(); i++) {
            if (sp_list.get(i).getAddress().equals(s)) {
                SmartPostList.add(sp_list.get(i));
            }
        }
        return SmartPostList;
    }
    //All SmartPost adresses from one city
}