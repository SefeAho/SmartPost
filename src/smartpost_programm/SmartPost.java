



package smartpost_programm;

/**
 *
 * @author Severi Ahopelto & Markus Kyläheiko
 */

public class SmartPost {
    
    private String place; 
    private String city;
    private String address;
    private String availability;
    private String office;
    private String lat;
    private String lng;


    public SmartPost (String p,String c, String a, String av, String o, String lt, String ln) {
        place = p; city = c; address = a; availability = av; office = o;
        lat= lt; lng = ln;
    }

@Override    
    public String toString() {

        return (place + city + address + availability + office + lat + lng);
        
    }
    
    public String getName() {
        return city;
    }
    
    public String getAddress() {
        return address;
    }
    
    public String getCode() {
        return place;
    }
    
    public String getAvailability() {
        return availability;
    }
    public String getLatitude() {
        return lat;
    }
    public String getLongitude() {
        return lng;
    }
}