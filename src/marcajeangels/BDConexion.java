/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package marcajeangels;


import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

/**
 *
 * @author jluis
 */

public class BDConexion {

   public static final String URL = "jdbc:mysql://localhost:3306/marcaje?useTimezone=true&serverTimezone=UTC";//administrador
   public static final String USER =   "MarcajeA";//"SuperElrey";
   public static final String CLAVE =  "Coast@cm";// 
     
    public Connection getConexion(){
        Connection con = null;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = (Connection) DriverManager.getConnection(URL, USER, CLAVE);
        }catch(Exception e){
            System.out.println("Error: " + e.getMessage());
            JOptionPane.showMessageDialog(null,"ERROR GRAVE CONTACTE AL ADMINISTRADOR DEL SISTEMA");
        }
        return con;
    }
    }
    
    
    
    

