package sistemaparqueadero;

import Clases.CONECTAR;
import com.mysql.jdbc.Connection;
import javax.swing.table.DefaultTableModel;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;


public class Panel_ListarVehiculo extends javax.swing.JPanel {

        public Panel_ListarVehiculo() {
        initComponents();
        cargarDatosUbicacion();
        cb_ubicacion.setSelectedIndex(-1);
        cb_vehiculo.setSelectedIndex(-1);
        actualizarTabla();
    }
    private void cargarDatosUbicacion() {
    try {
        CONECTAR con = new CONECTAR();
        Connection conexion = (Connection) con.conexion();

        String query = "SELECT Nombre_plaza FROM Espacios_estacionamiento";
        PreparedStatement pstmt = conexion.prepareStatement(query);
        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            String nombrePlaza = rs.getString("Nombre_plaza");
            cb_ubicacion.addItem(nombrePlaza);
        }

        conexion.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
}
    
    
    
    public void limpiarCampos() {
    txt_placa.setText("");
    txt_propietario.setText("");
    cb_vehiculo.setSelectedIndex(-1);
    cb_ubicacion.setSelectedIndex(-1);
    txt_placa.requestFocus();
    actualizarTabla();
}

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel5 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tb_datos = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        txt_placa = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txt_propietario = new javax.swing.JTextField();
        btn_buscar = new javax.swing.JButton();
        btnNuevo = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        df_fecha = new com.toedter.calendar.JDateChooser();
        cb_vehiculo = new javax.swing.JComboBox<>();
        cb_ubicacion = new javax.swing.JComboBox<>();

        setPreferredSize(new java.awt.Dimension(689, 411));

        jPanel5.setBackground(new java.awt.Color(0, 51, 51));

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Segoe UI Symbol", 1, 16)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Módulo para Buscar Vehiculos");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(253, 253, 253)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel1)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        tb_datos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Placa", "Propietario", "Tipo de Vehiculo", "Hora Entrada", "Hora Salida", "Pago"
            }
        ));
        jScrollPane1.setViewportView(tb_datos);

        jLabel5.setText("Tipo Vehiculo");

        txt_placa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_placaActionPerformed(evt);
            }
        });

        jLabel6.setText("Plaza / Ubicacion");

        btn_buscar.setBackground(new java.awt.Color(255, 0, 0));
        btn_buscar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_buscar.setForeground(new java.awt.Color(255, 255, 255));
        btn_buscar.setText("Buscar");
        btn_buscar.setBorder(new javax.swing.border.MatteBorder(null));
        btn_buscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_buscarActionPerformed(evt);
            }
        });

        btnNuevo.setBackground(new java.awt.Color(255, 0, 0));
        btnNuevo.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnNuevo.setForeground(new java.awt.Color(255, 255, 255));
        btnNuevo.setText("Nuevo");
        btnNuevo.setBorder(new javax.swing.border.MatteBorder(null));
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });

        jLabel2.setText("Placa");

        jLabel3.setText("Propietario");

        jLabel4.setText("Fecha");

        cb_vehiculo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Motocicleta", "Automovil" }));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(87, 87, 87)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(103, 103, 103)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(cb_vehiculo, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addGap(94, 94, 94)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(cb_ubicacion, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6))
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(jLabel2)
                            .addComponent(txt_placa, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(69, 69, 69)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(txt_propietario, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(69, 69, 69)
                                .addComponent(df_fecha, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel4)
                                .addGap(161, 161, 161))))))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(155, 155, 155)
                        .addComponent(btn_buscar, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 677, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(df_fecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txt_placa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel4))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txt_propietario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cb_ubicacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cb_vehiculo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(12, 12, 12)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_buscar, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        limpiarCampos();
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void btn_buscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_buscarActionPerformed
        actualizarTabla();
    }//GEN-LAST:event_btn_buscarActionPerformed

    private void txt_placaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_placaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_placaActionPerformed
    private void actualizarTabla() {
    try {
        CONECTAR con = new CONECTAR();
        Connection conexion = (Connection) con.conexion();
        PreparedStatement pstmt;
        ResultSet rs;

        String query = "SELECT V.Id_vehiculo, V.Placa, V.Propietario, V.Tipo_vehiculo, " +
                "CONCAT(RE.Fecha, ' ', RE.Hora_entrada) AS Hora_entrada, " +
                "CONCAT(RS.Fecha, ' ', RS.Hora_salida) AS Hora_salida, T.Importe " +
                "FROM Vehiculo V " +
                "LEFT JOIN Registro_Entrada RE ON V.Id_vehiculo = RE.Id_vehiculo " +
                "LEFT JOIN Registro_Salida RS ON V.Id_vehiculo = RS.Id_vehiculo " +
                "LEFT JOIN Ticket T ON V.Id_vehiculo = T.Id_vehiculo " +  // Agrega la relación con la tabla Ticket
                "WHERE 1 = 1";

        if (!txt_placa.getText().isEmpty()) {
            query += " AND V.Placa = ?";
        }
        if (!txt_propietario.getText().isEmpty()) {
            query += " AND V.Propietario = ?";
        }
        if (df_fecha.getDate() != null) {
            query += " AND RE.Fecha = ?";
        }
        if (cb_vehiculo.getSelectedItem() != null) {
            query += " AND V.Tipo_vehiculo = ?";
        }
        if (cb_ubicacion.getSelectedItem() != null) {
            query += " AND RE.Ubicacion = ?";
        }

        pstmt = conexion.prepareStatement(query);

        int parameterIndex = 1;
        if (!txt_placa.getText().isEmpty()) {
            pstmt.setString(parameterIndex, txt_placa.getText());
            parameterIndex++;
        }
        if (!txt_propietario.getText().isEmpty()) {
            pstmt.setString(parameterIndex, txt_propietario.getText());
            parameterIndex++;
        }
        if (df_fecha.getDate() != null) {
            pstmt.setDate(parameterIndex, new java.sql.Date(df_fecha.getDate().getTime()));
            parameterIndex++;
        }
        if (cb_vehiculo.getSelectedItem() != null) {
            pstmt.setString(parameterIndex, cb_vehiculo.getSelectedItem().toString());
            parameterIndex++;
        }
        if (cb_ubicacion.getSelectedItem() != null) {
            pstmt.setString(parameterIndex, cb_ubicacion.getSelectedItem().toString());
        }

        rs = pstmt.executeQuery();

        DefaultTableModel modelo = (DefaultTableModel) tb_datos.getModel();
        modelo.setRowCount(0);

        while (rs.next()) {
            Object[] fila = new Object[7];

            fila[0] = rs.getInt("Id_vehiculo");
            fila[1] = rs.getString("Placa");
            fila[2] = rs.getString("Propietario");
            fila[3] = rs.getString("Tipo_vehiculo");
            fila[4] = rs.getString("Hora_entrada");  // La columna Hora_entrada ahora es una cadena que combina Fecha y Hora_entrada
            fila[5] = rs.getString("Hora_salida");   // La columna Hora_salida ahora es una cadena que combina Fecha y Hora_salida
            fila[6] = rs.getBigDecimal("Importe");

            modelo.addRow(fila);
        }

        // Ajusta el ancho de las columnas
        ajustarAnchoColumnas();

        rs.close();
        pstmt.close();
        conexion.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
}
    private void ajustarAnchoColumnas() {
    // Ajusta el ancho de las columnas ID, Placa, Propietario y Tipo de Vehiculo
    tb_datos.getColumnModel().getColumn(0).setPreferredWidth(2);  // ID
    tb_datos.getColumnModel().getColumn(1).setPreferredWidth(50);  // Placa
    tb_datos.getColumnModel().getColumn(2).setPreferredWidth(70); // Propietario
    tb_datos.getColumnModel().getColumn(3).setPreferredWidth(60); // Tipo de Vehiculo
    tb_datos.getColumnModel().getColumn(4).setPreferredWidth(85);
    tb_datos.getColumnModel().getColumn(5).setPreferredWidth(85);
    tb_datos.getColumnModel().getColumn(6).setPreferredWidth(20);
}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnNuevo;
    private javax.swing.JButton btn_buscar;
    private javax.swing.JComboBox<String> cb_ubicacion;
    private javax.swing.JComboBox<String> cb_vehiculo;
    private com.toedter.calendar.JDateChooser df_fecha;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tb_datos;
    private javax.swing.JTextField txt_placa;
    private javax.swing.JTextField txt_propietario;
    // End of variables declaration//GEN-END:variables
}
