package words;

import static java.awt.SystemColor.text;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.StringTokenizer;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;

public class Main {

    private static Converter conv = new Converter();
    private static DataBase db = new DataBase();
    private static Checking check = new Checking();
    
    static String text = null;
    static StringTokenizer tokens;
    
    public static void main(String[] args) throws ServletException, SQLException {

        try {
            /*We need to open the connection iwth the database*/
            db.getConnection();
            
            /*We delete the table directly, trough code*/
            db.deleteTable();

            //We read "finalContent.txt" to insert this informtion our database
            conv.readingFle();
      
            /*This method will prove if there is some match between the 
            sentence which the user typed nad the words are contained in the
            database*/ 
            check.sentence();
           
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}