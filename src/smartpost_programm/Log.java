

package smartpost_programm;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Severi Ahopelto & Markus Kyläheiko
 */
public class Log {
    
    private String text= "Lokitiedot: \n\n"; 
    private String filename = "loki.txt";
    
    static private Log pa = null;
     
    public Log (){
        
    }
    
   static public Log getInstance() {
        if (pa == null) {
            pa = new Log();
        }
        return pa;   
    }
    
    public void addText (String a) {
        text = text + "\n" + a; 
    }
    
    public String getText(){
        return text; 
    }
    
    public void writeToLogFile () throws IOException{
         BufferedWriter out = new BufferedWriter (new FileWriter(filename));
         out.write(text);
         out.close();
        
    }
    
}
