package words;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;


public class Conexion {
    private Connection conn = null;
    
    String word;
    String noun;
    String verb;
    String adj;
    String adv;
    
    public Connection CrearBD(){
        try{
             //obtenemos el driver de para mysql
             Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
             //obtenemos la conexión
             conn = DriverManager.getConnection("jdbc:derby:.\\DB\\Derby.DB;create=true");
         
            Statement st = conn.createStatement();

            if (conn!=null){     
                st.execute("DELETE FROM wiktionary");

                JOptionPane.showMessageDialog(null,"OK base de datos listo");
                String creartabla="create table Wiktionary(word varchar(100), noun varchar(100), verb varchar(100), adjective varchar(100), adverb varchar(100))";
                String desc="disconnect;";
            
                try {
                    PreparedStatement pstm = conn.prepareStatement(creartabla);
                    pstm.execute();
                    pstm.close();
                
                    PreparedStatement pstm2 = conn.prepareStatement(desc);
                    pstm2.execute();
                 pstm2.close();
            
                    JOptionPane.showMessageDialog(null,"BD Creada correctamente");
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, ex.getLocalizedMessage());
                }
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null,e.getMessage(),"Error" ,  JOptionPane.ERROR_MESSAGE);
        }catch(ClassNotFoundException e){
            JOptionPane.showMessageDialog(null,e.getMessage(),"Error" ,  JOptionPane.ERROR_MESSAGE);
        }
       
        return conn;
  }
    
  public Connection AccederBD(){
       try{
            //obtenemos el driver de para mysql
             Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
             //obtenemos la conexión
             conn = DriverManager.getConnection("jdbc:derby:.\\DB\\Derby.DB");
            
            if (conn!=null){
                JOptionPane.showMessageDialog(null,"OK base de datos listo");
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null,e.getMessage(),"Error" ,  JOptionPane.ERROR_MESSAGE);
        }catch(ClassNotFoundException e){
            JOptionPane.showMessageDialog(null,e.getMessage(),"Error" ,  JOptionPane.ERROR_MESSAGE);
        }
       return conn;
  }
  
  public void printTable() throws SQLException {
    conn = DriverManager.getConnection("jdbc:derby:.\\DB\\Derby.DB");
    Statement statement = conn.createStatement();
    ResultSet rs = statement.executeQuery("select * from Wiktionary");
    ResultSetMetaData metadata = rs.getMetaData();
    
    //Imprimimos la cabecera de la tabla
    int columnas = metadata.getColumnCount();
    for (int i = 1; i <= columnas; i++) {
        System.out.format("%15s", metadata.getColumnName(i) + " || ");
    }
 
    while (rs.next()) {
        //Imprimimos cada una de las filas de la tabla
        System.out.println("");
        for (int j = 1; j <= columnas; j++) {
            System.out.format("%15s", rs.getString(j) + " || ");
        }
    }
  }
  
  
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
    
    
    public void insertWord() throws SQLException {
        conn = DriverManager.getConnection("jdbc:derby:.\\DB\\Derby.DB;create=true");
           
        //se ejecuta la consulta
        try {
            PreparedStatement pstm = conn.prepareStatement("INSERT INTO WIKTIONARY " + " (word,oun,verb,adjective,adverb) VALUES ('" + word + "','" + noun + "','" + verb + "','" + adj + "','" + adv + "')");
            pstm.execute();
            pstm.close();
            
//            JOptionPane.showMessageDialog(null,"Insertado correctamente");
        } catch (SQLException ex) {
//            JOptionPane.showMessageDialog(null, ex.getLocalizedMessage());
        }
        
//            Statement statement = conn.createStatement();
//            statement.executeUpdate("INSERT INTO WIKTIONARY " + " (word,noun, adjetive, adverb) VALUES ('" + word + "','" + noun + "')");
//            statement.close();
//            conn.close();
//            
//        }catch(Exception e){
//            System.out.println("No inserta en la tabla");
//        }
                    
    }
 
      public void cerracon (){
        try {
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
   }
     
}
