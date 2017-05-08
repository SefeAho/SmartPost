



package smartpost_programm;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 *
 * @author Severi Ahopelto & Markus Kyläheiko
 */
public class Storage {
    
    ArrayList<Package> packageList = new ArrayList();
    ArrayList<SmartPost> senderAddressList = new ArrayList();
    ArrayList<SmartPost> receiverAddressList = new ArrayList();
    
    static private Storage pa = null;
     
    public Storage (){
    }
     
    static public Storage getInstance() {
        if (pa == null) {
            pa = new Storage();
        }
        return pa;   
    }
     
    public void addPackage(Package Package){
           packageList.add(Package);
    }
    public void addSenderAddress(SmartPost SmartPost){
        senderAddressList.add(SmartPost);
    }
    public void addReceiverAddress(SmartPost SmartPost){
        receiverAddressList.add(SmartPost);
    }
        
    public void printItems (){
        for (int i=0; i < packageList.size(); i++) {
            System.out.println(packageList.get(i).getItemName() + " " +packageList.get(i).getWeight()+ " " +packageList.get(i).getSize()+ " " +packageList.get(i).getFragile() );
        }
    }
     
    List<String> strings = packageList.stream()
    .map(object -> Objects.toString(object, null))
    .collect(Collectors.toList());
    
    public ArrayList<Package> getPackageList() {
        
        return packageList;
    }
     
}
