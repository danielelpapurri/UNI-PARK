package sistemaparqueadero;
import Clases.CONECTAR;
import com.mysql.jdbc.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import java.sql.ResultSet;


public class Panel_IngresarVehiculo extends javax.swing.JPanel {

    private Object dateChooser;    
    

    public Panel_IngresarVehiculo() {
        initComponents();
        setHoraActual(); // Llama al método para establecer la hora actual
        cargarUbicacionesDisponibles();
        cb_ubicacion.setSelectedIndex(-1);   
        cb_tipo.setSelectedIndex(-1);
     
}
    public void cargarUbicacionesDisponibles() {
        try {
        CONECTAR con = new CONECTAR();
        Connection conexion = (Connection) con.conexion();

        if (conexion != null) {
            String query = "SELECT Nombre_plaza FROM Espacios_estacionamiento WHERE Estado = 'Libre'";
            java.sql.PreparedStatement pstmt = conexion.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                cb_ubicacion.addItem(rs.getString("Nombre_plaza"));
            }

            conexion.close();
        } else {
            JOptionPane.showMessageDialog(null, "No se pudo establecer la conexión");
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    }

    // Este método establece la hora actual
    public void setHoraActual() {
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        String horaActual = timeFormat.format(new Date());
        txt_horaentrada.setText(horaActual);
    }
    public void limpiarCampos() {
        txt_vehiculo.setText("");
        txt_propietario.setText("");
        dc_fecha.setDate(null);
        setHoraActual();
        txt_condicionVehiculo.setText("");
        cb_tipo.setSelectedIndex(-1);    
        cb_ubicacion.setSelectedIndex(-1);
        txt_vehiculo.requestFocus();
}
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btn_registrar = new java.awt.Button();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txt_condicionVehiculo = new javax.swing.JTextPane();
        dc_fecha = new com.toedter.calendar.JDateChooser();
        txt_vehiculo = new javax.swing.JTextField();
        txt_propietario = new javax.swing.JTextField();
        txt_horaentrada = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        cb_tipo = new javax.swing.JComboBox<>();
        cb_ubicacion = new javax.swing.JComboBox<>();

        setPreferredSize(new java.awt.Dimension(466, 405));

        btn_registrar.setBackground(new java.awt.Color(255, 51, 0));
        btn_registrar.setForeground(new java.awt.Color(255, 255, 255));
        btn_registrar.setLabel("Registrar");
        btn_registrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_registrarActionPerformed(evt);
            }
        });

        jLabel2.setText("Placa del Vehiculo");

        jLabel3.setText("Nombre del propietario");

        jLabel5.setText("Fecha de Ingreso");

        jPanel5.setBackground(new java.awt.Color(0, 51, 51));

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Segoe UI Symbol", 1, 16)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Modulo de Ingreso de vehiculos al parqueadero");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(66, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(50, 50, 50))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(15, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(14, 14, 14))
        );

        jLabel6.setText("Hora de Entrada");

        jLabel7.setText("Ubicacion / Nom. Plaza");

        jLabel8.setText("Condicón del Vehiculo");

        jScrollPane1.setViewportView(txt_condicionVehiculo);

        jLabel9.setText("Tipo de Vehiculo");

        cb_tipo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Motocicleta", "Automovil" }));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(91, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btn_registrar, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(108, 108, 108))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel7))
                                .addGap(27, 27, 27))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel9)
                                    .addComponent(jLabel8))
                                .addGap(31, 31, 31)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cb_tipo, 0, 170, Short.MAX_VALUE)
                            .addComponent(jScrollPane1)
                            .addComponent(dc_fecha, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)
                            .addComponent(txt_vehiculo)
                            .addComponent(txt_propietario)
                            .addComponent(txt_horaentrada)
                            .addComponent(cb_ubicacion, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(54, 54, 54))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txt_vehiculo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_propietario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(dc_fecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txt_horaentrada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(cb_ubicacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(cb_tipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_registrar, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btn_registrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_registrarActionPerformed
        String placa = txt_vehiculo.getText();
        String propietario = txt_propietario.getText();
        String ubicacion = cb_ubicacion.getSelectedItem().toString();
        String condicion = txt_condicionVehiculo.getText();
        String fecha = new SimpleDateFormat("yyyy-MM-dd").format(dc_fecha.getDate());
        String hora = txt_horaentrada.getText(); // Obtener la hora ingresada

    try {
        CONECTAR con = new CONECTAR();
        Connection conexion = (Connection) con.conexion();

        if (conexion != null) {
            String tipoVehiculo = (String) cb_tipo.getSelectedItem(); // Obtener el tipo de vehículo seleccionado

            String queryVehiculo = "INSERT INTO Vehiculo (Placa, Propietario, Condicion, Tipo_vehiculo) VALUES (?, ?, ?, ?)";
            java.sql.PreparedStatement pstmtVehiculo = conexion.prepareStatement(queryVehiculo);
            pstmtVehiculo.setString(1, placa);
            pstmtVehiculo.setString(2, propietario);
            pstmtVehiculo.setString(3, condicion);
            pstmtVehiculo.setString(4, tipoVehiculo);

            int filasInsertadasVehiculo = pstmtVehiculo.executeUpdate();

            if (filasInsertadasVehiculo > 0) {
                // Obtener la hora actual y establecerla en el formato necesario
                SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
                String horaActual = timeFormat.format(new Date());

                String queryRegistro = "INSERT INTO Registro_Entrada (Id_vehiculo, Fecha, Hora_entrada, Ubicacion) VALUES (LAST_INSERT_ID(), ?, ?, ?)";
                java.sql.PreparedStatement pstmtRegistro = conexion.prepareStatement(queryRegistro);
                pstmtRegistro.setString(1, fecha);
                pstmtRegistro.setString(2, hora); // Insertar solo la hora en Hora_entrada
                pstmtRegistro.setString(3, ubicacion);

                int filasInsertadasRegistro = pstmtRegistro.executeUpdate();

                if (filasInsertadasRegistro > 0) {
                    String queryActualizarEspacio = "UPDATE Espacios_estacionamiento SET Estado = 'Ocupado' WHERE Nombre_plaza = ?";
                    java.sql.PreparedStatement pstmtActualizarEspacio = conexion.prepareStatement(queryActualizarEspacio);
                    pstmtActualizarEspacio.setString(1, ubicacion);
                    pstmtActualizarEspacio.executeUpdate();
                    JOptionPane.showMessageDialog(null,"Vehiculo registrado con exito");
                    txt_vehiculo.setText("");
                    txt_propietario.setText("");
                    dc_fecha.setDate(null); 
                    setHoraActual();
                    txt_condicionVehiculo.setText("");
                    cb_tipo.setSelectedIndex(-1);
                    // Remover el elemento del combo box de ubicación
                    cb_ubicacion.removeItem(ubicacion);
                    cb_ubicacion.setSelectedIndex(-1);
                    txt_vehiculo.requestFocus();
                } else {
                    JOptionPane.showMessageDialog(null, "No se logró guardar los datos en Registro_Entrada");
                }
            } else {
                JOptionPane.showMessageDialog(null, "No se logró guardar los datos en Vehiculo");
            }

            conexion.close();
        } else {
            JOptionPane.showMessageDialog(null, "No se pudo establecer la conexión");
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
        
    
        
    }//GEN-LAST:event_btn_registrarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private java.awt.Button btn_registrar;
    private javax.swing.JComboBox<String> cb_tipo;
    private javax.swing.JComboBox<String> cb_ubicacion;
    private com.toedter.calendar.JDateChooser dc_fecha;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextPane txt_condicionVehiculo;
    private javax.swing.JTextField txt_horaentrada;
    private javax.swing.JTextField txt_propietario;
    private javax.swing.JTextField txt_vehiculo;
    // End of variables declaration//GEN-END:variables
}
