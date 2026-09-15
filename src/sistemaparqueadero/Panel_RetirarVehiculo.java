package sistemaparqueadero;
import java.text.SimpleDateFormat;
import java.util.Date;
import Clases.CONECTAR;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;


public class Panel_RetirarVehiculo extends javax.swing.JPanel {
    
    
    public Panel_RetirarVehiculo() {
        initComponents();
        setHoraActual();
        setFechaActual();
        
        txt_placa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_placaKeyReleased(evt);
            }
        });}
    
    
    public void limpiarCampos() {
    //FALTA PONER LOS CONTROLES PARA LIMPIAR
    txt_placa.setText("");
    txt_Fentrada.setText("");
    setFechaActual();
    txt_Hentrada.setText("");
    setHoraActual();
    txt_novedades.setText("");
}
    public void setFechaActual() {
        Date fecha = new Date();        
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        txt_Fsalida.setText(formato.format(fecha));
    }
    private void txt_placaKeyReleased(java.awt.event.KeyEvent evt) {                                            
        String placa = txt_placa.getText();

        try {
            CONECTAR con = new CONECTAR();
            Connection conexion = con.conexion();

            String queryEntrada = "SELECT Fecha, Hora_entrada FROM Registro_Entrada " +
                                  "WHERE Id_vehiculo = (SELECT Id_vehiculo FROM Vehiculo WHERE Placa = ?)";

            PreparedStatement pstmtEntrada = conexion.prepareStatement(queryEntrada);
            pstmtEntrada.setString(1, placa);

            ResultSet rsEntrada = pstmtEntrada.executeQuery();

            if (rsEntrada.next()) {
                String fechaEntrada = rsEntrada.getString("Fecha");
                String horaEntrada = rsEntrada.getString("Hora_entrada");

                txt_Fentrada.setText(fechaEntrada);
                txt_Hentrada.setText(horaEntrada);
            }            

            String queryDescripcion = "SELECT Descripcion FROM Incidentes_Problemas " +
                                      "WHERE Id_vehiculo = (SELECT Id_vehiculo FROM Vehiculo WHERE Placa = ?)";

            PreparedStatement pstmtDescripcion = conexion.prepareStatement(queryDescripcion);
            pstmtDescripcion.setString(1, placa);

            ResultSet rsDescripcion = pstmtDescripcion.executeQuery();

            if (rsDescripcion.next()) {
                String descripcion = rsDescripcion.getString("Descripcion");
                txt_novedades.setText(descripcion);
            }
            else {
            // No hay incidente, asignar "SIN NOVEDAD" al campo txt_novedades
            txt_novedades.setText("SIN NOVEDAD");}
            conexion.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        setFechaActual();
        setHoraActual();
    }

    private void setHoraActual() {
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        String horaActual = timeFormat.format(new Date());
        txt_Hsalida.setText(horaActual);
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        btn_retirar = new java.awt.Button();
        jScrollPane1 = new javax.swing.JScrollPane();
        txt_novedades = new javax.swing.JTextPane();
        jLabel6 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txt_placa = new javax.swing.JTextField();
        txt_Hsalida = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txt_Fentrada = new javax.swing.JTextField();
        txt_Fsalida = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txt_Hentrada = new javax.swing.JTextField();

        jLabel2.setText("Placa Vehiculo");

        btn_retirar.setBackground(new java.awt.Color(255, 51, 0));
        btn_retirar.setForeground(new java.awt.Color(255, 255, 255));
        btn_retirar.setLabel("Retirar");
        btn_retirar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_retirarActionPerformed(evt);
            }
        });

        txt_novedades.setBorder(javax.swing.BorderFactory.createTitledBorder("Novedades"));
        jScrollPane1.setViewportView(txt_novedades);

        jLabel6.setText("Hora de Salida");

        jLabel9.setText("Fecha Entrada");

        jPanel5.setBackground(new java.awt.Color(0, 51, 51));

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Segoe UI Symbol", 1, 16)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Modulo de salida de vehiculos del parqueadero");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(79, 79, 79)
                .addComponent(jLabel1)
                .addContainerGap(84, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel1)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        txt_Hsalida.setEnabled(false);

        jLabel10.setText("Fecha Salida");

        txt_Fentrada.setEnabled(false);

        txt_Fsalida.setEnabled(false);

        jLabel7.setText("Hora de Entrada");

        txt_Hentrada.setEnabled(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(82, 82, 82)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel2)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_retirar, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_placa, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(jLabel9)
                            .addComponent(txt_Fentrada, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_Hentrada, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(66, 66, 66)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txt_Fsalida, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txt_Hsalida, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(30, 30, 30)
                                        .addComponent(jLabel6))))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(94, 94, 94)
                                .addComponent(jLabel10)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_placa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_Fentrada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_Fsalida, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_Hentrada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_Hsalida, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21)
                .addComponent(btn_retirar, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btn_retirarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_retirarActionPerformed
        String placa = txt_placa.getText();
    
    // Verifica que los campos necesarios no estén vacíos
    if (placa.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Ingrese la placa antes de realizar la acción.", "Campos Vacíos", JOptionPane.WARNING_MESSAGE);
        return; // Sale del método si algún campo está vacío
    }

    try {
        CONECTAR con = new CONECTAR();
        Connection conexion = con.conexion();

        // Obteniendo la fecha y hora actual para el registro de salida
        Date fechaActual = new Date();
        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
        String fechaActualString = formatoFecha.format(fechaActual);
        SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm:ss");
        String horaActual = formatoHora.format(fechaActual);
        String queryEntrada = "SELECT Fecha, Hora_entrada FROM Registro_Entrada " +
                              "WHERE Id_vehiculo = (SELECT Id_vehiculo FROM Vehiculo WHERE Placa = ?)";

        PreparedStatement pstmtEntrada = conexion.prepareStatement(queryEntrada);
        pstmtEntrada.setString(1, placa);

        

ResultSet rsEntrada = pstmtEntrada.executeQuery();

        if (rsEntrada.next()) {
            String fechaEntrada = rsEntrada.getString("Fecha");
            String horaEntrada = rsEntrada.getString("Hora_entrada");

            // Mostrar en los campos correspondientes (si se desea)
            txt_Fentrada.setText(fechaEntrada);
            txt_Hentrada.setText(horaEntrada);
        }

        // Actualizando los campos con la fecha y hora actuales
        txt_Fsalida.setText(fechaActualString);
        txt_Hsalida.setText(horaActual);

        // Consulta para obtener la descripción si existe un incidente/problema
        String queryDescripcion = "SELECT Descripcion FROM Incidentes_Problemas " +
                                  "WHERE Id_vehiculo = (SELECT Id_vehiculo FROM Vehiculo WHERE Placa = ?)";

        PreparedStatement pstmtDescripcion = conexion.prepareStatement(queryDescripcion);
        pstmtDescripcion.setString(1, placa);

        ResultSet rsDescripcion = pstmtDescripcion.executeQuery();

        if (rsDescripcion.next()) {
            String descripcion = rsDescripcion.getString("Descripcion");
            txt_novedades.setText(descripcion);
        } else {
            // No hay incidente, asignar "SIN NOVEDAD" al campo txt_novedades
            txt_novedades.setText("SIN NOVEDAD");

            // Llamar al método en la instancia de Ticket para que también tenga "SIN NOVEDAD"
            Ticket ticket = new Ticket();
            
        }

        // Actualizando el estado del estacionamiento a 'Libre'
        String updateQuery = "UPDATE Espacios_estacionamiento SET Estado = 'Libre' " +
                             "WHERE Nombre_plaza = (SELECT Nombre_plaza FROM Vehiculo WHERE Placa = ?)";

        PreparedStatement pstmtUpdate = conexion.prepareStatement(updateQuery);
        pstmtUpdate.setString(1, placa);
        pstmtUpdate.executeUpdate();

        // Insertando el registro de salida
        String insertQuery = "INSERT INTO Registro_Salida (Id_vehiculo, Fecha, Hora_salida) " +
                            "VALUES ((SELECT Id_vehiculo FROM Vehiculo WHERE Placa = ?), ?, ?)";

        PreparedStatement pstmtInsert = conexion.prepareStatement(insertQuery);
        pstmtInsert.setString(1, placa);
        pstmtInsert.setString(2, fechaActualString);
        pstmtInsert.setString(3, horaActual);
        pstmtInsert.executeUpdate();

        conexion.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }
    Ticket ticket = new Ticket();
    ticket.setDefaultValues(placa);
    ticket.setVisible(true);
                
    }//GEN-LAST:event_btn_retirarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private java.awt.Button btn_retirar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField txt_Fentrada;
    private javax.swing.JTextField txt_Fsalida;
    private javax.swing.JTextField txt_Hentrada;
    private javax.swing.JTextField txt_Hsalida;
    private javax.swing.JTextPane txt_novedades;
    private javax.swing.JTextField txt_placa;
    // End of variables declaration//GEN-END:variables
}
