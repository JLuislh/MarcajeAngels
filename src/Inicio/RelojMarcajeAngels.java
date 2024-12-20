
package Inicio;

import java.awt.Color;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import marcajeangels.BDConexion;
import marcajeangels.BDPool;
import marcajeangels.BD_RELOJ;
import marcajeangels.ClassReloj;
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
        logear();
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
    
    public void AgregarHoras() {

        try {
            BDPool conecta = new BDPool();
            Connection con = conecta.getConexion();
            Statement ps = con.createStatement();
            ps.executeUpdate("call actualizarHoras("+id+")");
            con.close();
            ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error" + e);
        }
    }
    
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

    
     private void guardarINGRESO() {
        buscasucursal();
        try {
            ClassReloj g = new ClassReloj();
            g.setCodigo(Integer.parseInt(codigo.getText().substring(1, 5)));
            g.setSede(sede);
            BD_RELOJ.IngresoDatosRelojin(g);
            timer.setRepeats(false);
            timer.start();
        } catch (NumberFormatException | SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR DE INSERTAR" + e);
        }
    }
     
    private void guardarSALIDA() {

        try {

            ClassReloj g = new ClassReloj();
            g.setCodigo(Integer.parseInt(codigo.getText().substring(1, 5)));
            BD_RELOJ.IngresoDatosRelojout(g);
            AgregarHoras();
            timer.setRepeats(false);
            timer.start();
        } catch (NumberFormatException | SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR DE INSERTAR" + e);
        }
    }
    
    private void marcar() {
        String s = codigo.getText();
        char firstCharacter = s.charAt(0);
        try {

            if (firstCharacter == '%') {
                validarExistencia();
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
    
     private void validarExistencia() {

          try {
                BDPool conecta = new BDPool();
                Connection cn = conecta.getConexion();
                java.sql.Statement stmt = cn.createStatement();
                //ResultSet rs = stmt.executeQuery("select COUNT(codigo) as codigo, sum(id_reloj) as id_reloj from reloj where codigo= " + codigo.getText().substring(1, 5) + " and estado = 1 and date_format(fecha,'dd/mm/yy') = date_format(current_date,'dd/mm/yy')");
                ResultSet rs = stmt.executeQuery("call verificar(" + codigo.getText().substring(1, 5) + ")");
                while (rs.next()) {
                      codigom = rs.getInt(1);
                      id = rs.getInt(2);
                }
                rs.close();
                stmt.close();
                cn.close();
            } catch (SQLException error) {
                System.out.print(error);
            }
                System.out.println("Shit = "+ codigom);
                System.out.println("Shit = "+id);
         
            if (codigom == 0) {
                guardarINGRESO();
                semaforo.setBackground(Color.green);
                Mensaje.setText("ENTRADA");codigo.requestFocus();
                }
                else if(marca != Integer.parseInt(codigo.getText().substring(1, 5))){
                guardarSALIDA();
                semaforo.setBackground(Color.yellow);
                Mensaje.setText("SALIDA");
                codigo.requestFocus();
                //Thread.sleep(4000);
                //semaforo.setBackground(Color.WHITE);
            }else{
                semaforo.setBackground(Color.red);System.out.println("llega a YA MARCADO");
                Mensaje.setText("YA MARCADO");codigo.requestFocus();
                timer.setRepeats(false);
                timer.start();
                }
     }
     
     public void logear(){
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
    
    }
    
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
}
