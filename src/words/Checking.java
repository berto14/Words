package words;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.StringTokenizer;

public class Checking {
    String text = null;
    StringTokenizer tokens;
    
    private static DataBase db = new DataBase();
    
    /*With this method the user introduces the sentences through the keyboard and we take all words
    for searching in the database*/
    public void sentence() throws SQLException {
        
        try {
            InputStreamReader leer = new InputStreamReader(System.in);
            BufferedReader buff = new BufferedReader(leer);
            System.out.print("Write the sentence: ");
            text = buff.readLine();
            
            tokens = new StringTokenizer(text);
            /*WE search every word of the sentence inside of the database*/
            while(tokens.hasMoreTokens()) {
                db.searching(tokens.nextToken());
            }
        } catch(java.io.IOException ioex) {
        
        }
    }
    
}
