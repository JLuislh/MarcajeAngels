
package Inicio;

import com.google.gson.Gson;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import marcajeangels.CodigosApi;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 *
 * @author jluis
 */
public final class RelojMarcajeAngels extends javax.swing.JFrame {
     
    int marca;
    int id;
    int codigom;
    String sede;
    String Sucursal = "";
    int codigoapi;
    int idapi;
    /**
     * Creates new form RelojMarcajeAngels
     */
    public RelojMarcajeAngels() {
        this.sede = "";
        
         try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
        }
        initComponents();
        
        setLocationRelativeTo(null);
        sede = System.getProperty("user.name");
    }
    
     Timer timer = new Timer(500, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            semaforo.setBackground(Color.white);
            codigo.setText("");
            Mensaje.setText("");
        }
    });

    Timer timer2 = new Timer(60000, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            marca = 0;
            codigo.setText("");
            //System.out.println("QUITA LA MARCA");

        }
    });
    
    private void buscasucursal(){
        switch (sede) {
            case "it" -> Sucursal = "TRABAJO";
            case "angelsparaiso" -> Sucursal = "PARAISO";
            case "angelssanluis" -> Sucursal = "PUERTA NEGRA";
            case "angelspalencia" -> Sucursal = "PALENCIA";
            case "angelsresidenciales" -> Sucursal = "RESIDENCIALES";
            case "user" -> Sucursal = "SANTA INNES";
            default -> {
            }
        }
    }


    private void marcar() {
        String s = codigo.getText();
        char firstCharacter = s.charAt(0);
        try {

            if (firstCharacter == '%') {
                existe();
                marca = Integer.parseInt(codigo.getText().substring(1, 5));
                timer2.setRepeats(false);
                timer2.start();
                codigo.setText("");

            } else {
                Mensaje.setText("ERROR");
                semaforo.setBackground(Color.red);
                codigo.setText("");
                timer.setRepeats(false);
                timer.start();
                codigo.setText("");
                
            }
        } catch (NumberFormatException e) {
            semaforo.setBackground(Color.red);
            codigo.setText("");
            timer.setRepeats(false);
            timer.start();
            codigo.setText("");
        }

    }
     
     
     public void existe(){
         String apiUrl = "http://140.84.178.126:3000/reloj/"+codigo.getText().substring(1, 5);
         OkHttpClient client = new OkHttpClient();
         Request request = new Request.Builder()
                .url(apiUrl)
                .build();
        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                 String responseBody = response.body().string();
                //System.out.println("Código de estado: " + response.code());
                System.out.println("Respuesta: " + responseBody);
                Gson gson = new Gson();
                CodigosApi persona = gson.fromJson(responseBody, CodigosApi.class);
                codigoapi = persona.getCodigo();
                idapi = persona.getId_reloj();
                System.out.println(codigoapi+" Inicio "+idapi);
            } else {
                System.out.println("Error: " + response.code());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

         if (codigoapi == 0) {
                ingreso();
                semaforo.setBackground(Color.green);
                Mensaje.setText("ENTRADA");codigo.requestFocus();
                }
                else if(marca != Integer.parseInt(codigo.getText().substring(1, 5))){
                salida();
                semaforo.setBackground(Color.yellow);
                Mensaje.setText("SALIDA");
                codigo.requestFocus();
            }else{
                semaforo.setBackground(Color.red);System.out.println("llega a YA MARCADO");
                Mensaje.setText("YA MARCADO");codigo.requestFocus();
                timer.setRepeats(false);
                timer.start();
                }
        
     }
     
     /*public void logear(){
        try {
            BDPool Conn = new BDPool();
            Connection con = Conn.getConexion();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select COUNT(user) as U from USUARIOS where user= 'angels' and pass = 'angels' and estado = 1" );
            rs.next();
            int USUARIO = rs.getInt("U");
            if (USUARIO == 1) {
                 System.out.println("LOGEADO");
                  
            } else {
                 JOptionPane.showMessageDialog(null, "NO TIENE CONEXION A INTERNET");
            }
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR CONTACTE AL ADMINISTRADOR DEL SISTEMA" + e);
        }
    
    }*/
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        semaforo = new javax.swing.JPanel();
        codigo = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        Mensaje = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        semaforo.setBackground(new java.awt.Color(255, 255, 255));
        semaforo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                semaforoMouseClicked(evt);
            }
        });

        codigo.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        codigo.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        codigo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                codigoActionPerformed(evt);
            }
        });
        codigo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                codigoKeyReleased(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("CODIGO");

        Mensaje.setFont(new java.awt.Font("Arial", 1, 40)); // NOI18N
        Mensaje.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Mensaje.setText("-");

        javax.swing.GroupLayout semaforoLayout = new javax.swing.GroupLayout(semaforo);
        semaforo.setLayout(semaforoLayout);
        semaforoLayout.setHorizontalGroup(
            semaforoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(semaforoLayout.createSequentialGroup()
                .addGap(372, 372, 372)
                .addGroup(semaforoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(codigo, javax.swing.GroupLayout.DEFAULT_SIZE, 183, Short.MAX_VALUE)
                    .addComponent(Mensaje, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(398, Short.MAX_VALUE))
        );
        semaforoLayout.setVerticalGroup(
            semaforoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(semaforoLayout.createSequentialGroup()
                .addGap(113, 113, 113)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(codigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Mensaje)
                .addContainerGap(227, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(semaforo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(semaforo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void codigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_codigoActionPerformed
       marcar();
    }//GEN-LAST:event_codigoActionPerformed

    private void codigoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_codigoKeyReleased
        if (codigo.getText().compareTo("") != 0) {
            String a = codigo.getText();
            char first = a.charAt(0);
            if (first != '%') {
                codigo.setText("");
            }
        }
    }//GEN-LAST:event_codigoKeyReleased

    private void semaforoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_semaforoMouseClicked
       if (evt.getClickCount() > 1){
       this.dispose();
    }
    }//GEN-LAST:event_semaforoMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(RelojMarcajeAngels.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RelojMarcajeAngels.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RelojMarcajeAngels.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RelojMarcajeAngels.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new RelojMarcajeAngels().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Mensaje;
    private javax.swing.JTextField codigo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel semaforo;
    // End of variables declaration//GEN-END:variables


public void ingreso(){
        buscasucursal();
        OkHttpClient client = new OkHttpClient();
        String json = "{\"codigo\":\""+codigo.getText().substring(1, 5)+"\",\"sede\":\""+sede+"\"}";
        RequestBody body = RequestBody.create(json, MediaType.parse("application/json"));
        Request request = new Request.Builder()
                .url("http://140.84.178.126:3000/relojingreso/"+codigo.getText().substring(1, 5))
                .put(body)
                .build();

        // Ejecutar la solicitud
        try (Response response = client.newCall(request).execute()) {
            System.out.println("Código de respuesta: " + response.code());
            System.out.println("Respuesta: " + response.body().string());
        } catch (Exception e) {
            e.printStackTrace();
        }
        timer.setRepeats(false);
        timer.start();
}

public void salida(){

        OkHttpClient client = new OkHttpClient();
        String json = "{\"codigo\":\""+codigo.getText().substring(1, 5)+"\"}";
        RequestBody body = RequestBody.create(json, MediaType.parse("application/json"));
        Request request = new Request.Builder()
                .url("http://140.84.178.126:3000/relojsalida/"+codigo.getText().substring(1, 5))
                .put(body)
                .build();
        // Ejecutar la solicitud
        try (Response response = client.newCall(request).execute()) {
            System.out.println("Código de respuesta: " + response.code());
            System.out.println("Respuesta: " + response.body().string());
        } catch (Exception e) {
            e.printStackTrace();
        }
        horas();
        timer.setRepeats(false);
        timer.start();
}
public void horas(){
        OkHttpClient client = new OkHttpClient();
        String json = "{\"idapi\":\""+idapi+"\"}";
        RequestBody body = RequestBody.create(json, MediaType.parse("application/json"));
        Request request = new Request.Builder()
                .url("http://140.84.178.126:3000/relojhoras/"+idapi)
                .put(body)
                .build();
        // Ejecutar la solicitud
        try (Response response = client.newCall(request).execute()) {
            System.out.println("Código de respuesta: " + response.code());
            System.out.println("Respuesta: " + response.body().string());
        } catch (Exception e) {
            e.printStackTrace();
        }
}
}
