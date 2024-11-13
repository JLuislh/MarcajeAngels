/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package marcajeangels;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author jluis
 */
public class BD_RELOJ {
    
    
     public static void IngresoDatosRelojin(ClassReloj c) throws SQLException {
        
        BDConexion conecta = new BDConexion();
        Connection cnn = conecta.getConexion();
        PreparedStatement ps = null;
        ps = cnn.prepareStatement("CALL INGRESO(?,?)");
        ps.setInt(1, c.getCodigo());
        ps.setString(2, c.getSede());
        ps.execute();
        cnn.close();
        ps.close();
    }  
    
    
    public static void IngresoDatosRelojout(ClassReloj c) throws SQLException {
        
        BDConexion conecta = new BDConexion();
        Connection cnn = conecta.getConexion();
        PreparedStatement ps = null;
        ps = cnn.prepareStatement("CALL SALIDA(?)");
        ps.setInt(1, c.getCodigo());
        ps.execute();
        cnn.close();
        ps.close();
        
    }  
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
