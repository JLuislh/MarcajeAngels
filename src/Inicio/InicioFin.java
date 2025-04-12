/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Inicio;
import java.io.File;
import java.io.IOException;


/**
 *
 * @author it
 */
public class InicioFin {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       File lockFile = new File("app.lock");
        
         if (lockFile.exists()) {
            System.out.println("La aplicación ya está en ejecución.");
            System.exit(0);  // Salir si ya está en ejecución
        }

        // Crear el archivo de bloqueo
        try {
            if (lockFile.createNewFile()) {
                System.out.println("Aplicación iniciada.");
            } else {
                System.out.println("Error al crear archivo de bloqueo.");
                System.exit(1);
            }

            RelojMarcajeAngels F = new RelojMarcajeAngels();
            F.setVisible(true);
            
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        // Eliminar archivo de bloqueo al cerrar la aplicación
        lockFile.deleteOnExit();
    
    }
    
}
