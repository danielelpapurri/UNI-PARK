package sistemaparqueadero;

import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import Clases.CONECTAR; // Importa la clase que gestiona la conexión a la base de datos

public class Panel_Incidentes extends javax.swing.JPanel {
    
    public Panel_Incidentes() {
        initComponents();
        
        
    }
    public void limpiarCampos() {
        txt_placa.setText("");
        txt_hora.setText("");
        txt_descripcion.setText("");
        dc_fecha.setDate(null);
        txt_placa.requestFocus();
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel5 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txt_hora = new javax.swing.JTextField();
        txt_placa = new javax.swing.JTextField();
        btn_registrar1 = new java.awt.Button();
        dc_fecha = new com.toedter.calendar.JDateChooser();
        jScrollPane1 = new javax.swing.JScrollPane();
        txt_descripcion = new javax.swing.JTextPane();
        jLabel5 = new javax.swing.JLabel();

        jPanel5.setBackground(new java.awt.Color(0, 51, 51));

        jLabel1.setBackground(new java.awt.Color(0, 153, 153));
        jLabel1.setFont(new java.awt.Font("Segoe UI Symbol", 1, 16)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Modulo para Registrar Incidentes");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(117, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(116, 116, 116))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(15, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(14, 14, 14))
        );

        jLabel2.setText("Ingresar Placa del Vehiculo");

        jLabel3.setText("Fecha del Incidente");

        jLabel4.setText("Hora del Incidente");

        btn_registrar1.setBackground(new java.awt.Color(255, 51, 0));
        btn_registrar1.setForeground(new java.awt.Color(255, 255, 255));
        btn_registrar1.setLabel("Registrar");
        btn_registrar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_registrar1ActionPerformed(evt);
            }
        });

        jScrollPane1.setViewportView(txt_descripcion);

        jLabel5.setText("Descripcion del Incidente");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(68, 68, 68)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel5)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 314, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_registrar1, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(37, 37, 37))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel3))
                                .addGap(76, 76, 76)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txt_hora)
                            .addComponent(txt_placa, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(dc_fecha, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(62, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_placa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel4))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(dc_fecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txt_hora, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(29, 29, 29)
                .addComponent(jLabel5)
                .addGap(11, 11, 11)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                .addComponent(btn_registrar1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btn_registrar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_registrar1ActionPerformed
        if (!btn_registrar1.isEnabled()) {
            return; // Si el botón ya está deshabilitado, evita la ejecución adicional
        }

        btn_registrar1.setEnabled(false);

        String placa = txt_placa.getText();
        java.util.Date fecha = dc_fecha.getDate();
        String hora = txt_hora.getText();
        String descripcion = txt_descripcion.getText();

        if (!existePlaca(placa)) {
            btn_registrar1.setEnabled(true);
            JOptionPane.showMessageDialog(this, "La placa no se encuentra registrada en el sistema");
            return;
        }

        try {
            CONECTAR miConexion = new CONECTAR();
            Connection con = miConexion.conexion();

            String query = "INSERT INTO Incidentes_Problemas (Id_vehiculo, Descripcion, Fecha_incidente, Hora_incidente) " +
            "VALUES ((SELECT Id_vehiculo FROM Vehiculo WHERE Placa = ?), ?, ?, ?)";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, placa);
            pstmt.setString(2, descripcion);
            pstmt.setDate(3, new java.sql.Date(fecha.getTime()));

            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            java.util.Date parsed = sdf.parse(hora);
            Time sqlTime = new Time(parsed.getTime());
            pstmt.setTime(4, sqlTime);

            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Incidente registrado exitosamente");
            limpiarCampos();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        btn_registrar1.setEnabled(true);        
    }//GEN-LAST:event_btn_registrar1ActionPerformed
    private boolean existePlaca(String placa) {
    boolean existe = false;
    try {
        CONECTAR miConexion = new CONECTAR();
        Connection con = miConexion.conexion();
        
        String query = "SELECT COUNT(*) AS total FROM Vehiculo WHERE Placa = ?";
        PreparedStatement pstmt = con.prepareStatement(query);
        pstmt.setString(1, placa);
        
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            int total = rs.getInt("total");
            if (total > 0) {
                existe = true;
            }
        }
        con.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
    return existe;
}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private java.awt.Button btn_registrar1;
    private com.toedter.calendar.JDateChooser dc_fecha;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextPane txt_descripcion;
    private javax.swing.JTextField txt_hora;
    private javax.swing.JTextField txt_placa;
    // End of variables declaration//GEN-END:variables
}
