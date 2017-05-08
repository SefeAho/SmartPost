



package smartpost_programm;

/**
 *
 * @author Severi Ahopelto & Markus Kyläheiko
 */
public class Package {
    
    protected String itemName;
    protected double weight; 
    protected String size;
    protected boolean fragile;
    
    public Package (double a, String  b, boolean c, String d){

        weight = a; 
        size  = b; 
        fragile = c;  
        itemName = d;
    }
    
    @Override
    public String toString() {
        return itemName + weight + size + fragile;
    }
    
    public double getWeight(){
        return weight; 
    }
    public String  getSize(){
        return size; 
    }
    public boolean getFragile(){                
        return fragile; 
    }
    public String getItemName() {
        return itemName; 
    }
   
}

class firstClass extends Package {

    double distance; 
    int speed;
    int packageClass; 
    
    public firstClass(double a, String  b, boolean c, String d, double e, int i, int g) {
        super(a, b, c, d);
        distance = e; 
        speed = i; 
         packageClass=g;
    }            
    
    public void info() {
        System.out.println("1. Luokan paketin tiedot");
        System.out.println("Kuljetusnopeus: Nopea");
        System.out.println("Paino max 20kg\nKoko: Pieni (11 x 36 x 60 cm)");
        System.out.println("Säekyville: Ei");
        System.out.println("Lähetysetäisyys alle 150km");       
    }
    
    public int getSpeed (){
        return speed;
    }
    public int getPackageClass(){
        return  packageClass;
    }
}
            
class secondClass extends Package{

    int speed; 
    int packageClass; 
    
    public secondClass(double a, String  b, boolean c, String d, int i, int g) {
        super(a, b, c,d);
        speed = i; 
        packageClass = g;
    }
         
    public void info() {
    System.out.println("2. Luokan paketin tiedot");
    System.out.println("Kuljetusnopeus: Normaali");
    System.out.println("Paino max 10kg\nKoko: Keskikokoinen (19 x 36 x 60 cm)");
    System.out.println("Särkyville: Kyllä");
    System.out.println("Lähetysetäisyys koko Suomi");       
    }
    public int getSpeed (){
        return speed;
    }
    public int getPackageClass(){
        return  packageClass;
    }
}

class thirdClass extends Package {

    int speed; 
    int packageClass; 
    
    public thirdClass(double a, String  b, boolean c, String d, int i, int g) {
        super(a, b, c, d);
        speed = i;
        packageClass = g;
    }
    public void info() {
        System.out.println("3. Luokan paketin tiedot");
        System.out.println("Kuljetussnopeus: Hidas");
        System.out.println("Paino max 50kg\nKoko: Suuri (37 x 36 x 60 cm)");
        System.out.println("Särkyville: Ei");
        System.out.println("Lähetysetäisyys koko Suomi");       
    }   
    public int getSpeed (){
        return speed;
    }
    public int getPackageClass(){
        return  packageClass;
    }
    
}
