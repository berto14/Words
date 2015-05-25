package words;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.StringTokenizer;


public class DataBase {
    private static Connection connection = null;
    private static Statement set = null;
    private static ResultSet rs = null;
    ResultSetMetaData rsmd = null;
        
    String word;
    String noun;
    String verb;
    String adj;
    String adv;

    /*We use the "localhost" because the server will run in our own machine*/    
    private static Driver driver = new org.apache.derby.jdbc.ClientDriver();
    private static String URLDerby = "jdbc:derby://localhost:1527/DBDerby1";
    /*We establish the user add the password that we will use to connect with the database*/    
    private static String user = "userDerby";
    private static String password = "12345";
   
    /*This method allow us to connect with the database*/
    public static Connection getConnection() throws SQLException {
        DriverManager.registerDriver(driver);
        connection = DriverManager.getConnection(URLDerby, user, password);
        return connection;
    }
    
    /*All this set methods will allow us to get all the information separately with different strings*/
    public void setWord(String word) {
        this.word = word;
    }
    
    public void setNoun(String noun) {
        this.noun = noun;
    }
     
   public void setVerb(String verb) {
        this.verb = verb;
    }
      
    public void setAdj(String adj) {
        this.adj = adj;
    }
       
    public void setAdv(String adv) {
        this.adv = adv;
    }
    
    
    /*With this method we can print all the information that we got with the set methods*/
    public void insertWord() throws SQLException {        
        try {
            //Creates a Statement object for sending SQL statements to the database
            set = connection.createStatement();
            set.executeUpdate("INSERT INTO WIKTIONARY " + " (word,noun,verb,adjective,adverb) VALUES ('" + word + "','" + noun + "','" + verb + "','" + adj + "','" + adv + "')");
            set.close();
            
        }catch(Exception e){
        }
    }
    
    
    public void searching(String s) {  
        try {
            set = connection.createStatement();
            rs = set.executeQuery("select *from wiktionary");

            ResultSetMetaData rsmd = rs.getMetaData();
            StringTokenizer tokens;
             
            /*This loop allow us to read the content of the database*/
            while (rs.next()) {
                /*With this loop we will be able to read every single column and taking the word is inside of it*/
                for (int i = 1; i <= rsmd.getColumnCount(); ++i) {

                    if (i!=1) {
                        /*We create the tokenizer and we pass the string like a parameter*/
                        StringTokenizer st = new StringTokenizer(rs.getObject(i).toString()); //con esto creas el tokenizer y le pasas la cadena como parametro

                        /*This cicle check when the processing of the string is over*/
                        while(st.hasMoreTokens()) { 
                            /*With .nextToken() we can read the next word in the string*/
                            String palabra = st.nextToken();

                            /*We check if "s" and "palabra" match. Where "s" is the word which the user introduces
                            through the keyboard and "palabra" is the word we took from the database */
                            if (s.equals(palabra)) {
                                System.out.println("\n" + s);

                                /*With all this jet of "if" sentences we detect if the word has noun, verb, adjective
                                or adverb*/
                                if (rs.getObject(i).equals("")) {
                                    System.out.println(rsmd.getColumnLabel(i) + " --> " + "The word " + s + " has no " + rsmd.getColumnLabel(i).toLowerCase());
                                } else {
                                    System.out.println(rsmd.getColumnLabel(i) + " --> " + rs.getObject(i));
                                }

                                if (rs.getObject(i+1).equals("")) {
                                    System.out.println(rsmd.getColumnLabel(i+1) + " --> " + "The word " + s + " has no " + rsmd.getColumnLabel(i+1).toLowerCase());
                                } else {
                                    System.out.println(rsmd.getColumnLabel(i+1) + " --> " + rs.getObject(i+1));
                                }

                                if (rs.getObject(i+2).equals("")) {
                                    System.out.println(rsmd.getColumnLabel(i+2) + " --> " + "The word " + s + " has no " + rsmd.getColumnLabel(i+2).toLowerCase());
                                } else {
                                    System.out.println(rsmd.getColumnLabel(i+2) + " --> " + rs.getObject(i+2));
                                }

                                if (rs.getObject(i+3).equals("")) {
                                    System.out.println(rsmd.getColumnLabel(i+3) + " --> " + "The word " + s + " has no " + rsmd.getColumnLabel(i+3).toLowerCase());
                                } else {
                                    System.out.println(rsmd.getColumnLabel(i+3) + " --> " + rs.getObject(i+3));
                                }

                            }
                        } 

                    } else {
                        if (s.equals(rs.getObject(i))) {
                            System.out.println("\n" + s);

                            if (rs.getObject(i+1).equals("")) {
                                System.out.println(rsmd.getColumnLabel(i+1) + " --> " + "The word " + s + " has no " + rsmd.getColumnLabel(i+1).toLowerCase());
                            } else {
                                System.out.println(rsmd.getColumnLabel(i+1) + " --> " + rs.getObject(i+1));
                            }

                            if (rs.getObject(i+2).equals("")) {
                                System.out.println(rsmd.getColumnLabel(i+2) + " --> " + "The word " + s + " has no " + rsmd.getColumnLabel(i+2).toLowerCase());
                            } else {
                                System.out.println(rsmd.getColumnLabel(i+2) + " --> " + rs.getObject(i+2));
                            }

                            if (rs.getObject(i+3).equals("")) {
                                System.out.println(rsmd.getColumnLabel(i+3) + " --> " + "The word " + s + " has no " + rsmd.getColumnLabel(i+3).toLowerCase());
                            } else {
                                System.out.println(rsmd.getColumnLabel(i+3) + " --> " + rs.getObject(i+3));
                            }

                            if (rs.getObject(i+4).equals("")) {
                                System.out.println(rsmd.getColumnLabel(i+4) + " --> " + "The word " + s + " has no " + rsmd.getColumnLabel(i+4).toLowerCase());
                            } else {
                                System.out.println(rsmd.getColumnLabel(i+4) + " --> " + rs.getObject(i+4));
                            }

                        }
                    }
                }
            }
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("Not searching on the table");
        }    
    }    
    
    
    /*We delete the table*/
    public void deleteTable() {
        try {
            set = connection.createStatement();
            set.executeUpdate("TRUNCATE TABLE wiktionary");
            set.close();
        }catch(Exception e){
            System.out.println("Not delete the table");
        }
            

    }
    
    /*We close the the connection with the database*/
    public void closeConnection() {
        try {
            connection.close();
        } catch (Exception e){}
    }
}

