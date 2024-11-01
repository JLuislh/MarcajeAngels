/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package marcajeangels;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author jluis
 */
public class BD {
    
    
    
        
    
     public static void IngresoDatosRelojin(ClassReloj c) throws SQLException {
        
        BDConexion conecta = new BDConexion();
        PreparedStatement ps;
         try (Connection con = conecta.getConexion()) {
             ps = null;
             ps = con.prepareStatement("insert into RELOJ(ID_RELOJ,CODIGO,FECHA,INGRESO,ESTADO) VALUES(ID_RELOJ.NEXTVAL,?,SYSDATE,SYSDATE,1)");
             ps.setInt(1, c.getCodigo());
             ps.execute();
         }
        ps.close();
    }  
    
    
    
    public static void IngresoDatosRelojout(ClassReloj c) throws SQLException {
        BDConexion conecta = new BDConexion();
        PreparedStatement ps;
         try (Connection cnn = conecta.getConexion()) {
             ps = null;
             ps = cnn.prepareStatement("UPDATE RELOJ SET SALIDA = SYSDATE,ESTADO = 2 WHERE CODIGO = ? AND ESTADO = 1");
             ps.setInt(1, c.getCodigo());
             ps.execute();
         }
        ps.close();
    }  
   
    
}
