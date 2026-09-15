package sistemaparqueadero;

import Clases.CONECTAR;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Time;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JOptionPane;

public class Ticket extends javax.swing.JFrame {
    
    private boolean clienteIngresado = false;

     public Ticket() {
        initComponents();
        this.setLocationRelativeTo(null);
        setFechaActual();
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                setDefaultValues("");
            }
        });
    }
     private Integer obtenerIdIncidente(String incidente, Connection conexion) throws SQLException {
    // Buscar el Id_incidente correspondiente al incidente dado
    Integer idIncidente = null; // Valor por defecto si no se encuentra

    String query = "SELECT Id_incidente FROM Incidentes_Problemas WHERE Descripcion = ?";
    try (PreparedStatement pstmt = conexion.prepareStatement(query)) {
        pstmt.setString(1, incidente);
        ResultSet rs = pstmt.executeQuery();

        if (rs.next()) {
            idIncidente = rs.getInt("Id_incidente");
        }
    }

    return idIncidente;
}
   
     public void setDefaultValues(String placa) {
         
         int siguienteCodigo = obtenerSiguienteCodigoFactura();
         // Inicializar txt_codigo desde 1
         if (siguienteCodigo == 0) {
             siguienteCodigo = 1;
         }
         txt_codigo.setText(String.valueOf(siguienteCodigo));

    mostrarDatosDesdePlaca(placa);

    // Llenar los campos de txt_cliente
    try {
        CONECTAR con = new CONECTAR();
        Connection conexion = con.conexion();

        String query = "SELECT Propietario FROM Vehiculo WHERE Placa = ?";
        PreparedStatement pstmt = conexion.prepareStatement(query);
        pstmt.setString(1, placa);
        ResultSet rs = pstmt.executeQuery();

        if (rs.next()) {
            String propietario = rs.getString("Propietario");
            txt_cliente.setText(propietario);
        }

        conexion.close();
    } catch (SQLException e) {
        e.printStackTrace();
    } 

    // Llenar los campos de txt_tipo y txt_placa
    try {
        CONECTAR con = new CONECTAR();
        Connection conexion = con.conexion();

        String query = "SELECT Tipo_vehiculo FROM Vehiculo WHERE Placa = ?";
        PreparedStatement pstmt = conexion.prepareStatement(query);
        pstmt.setString(1, placa);
        ResultSet rs = pstmt.executeQuery();

        if (rs.next()) {
            String tipoVehiculo = rs.getString("Tipo_vehiculo");
            txt_vehiculo.setText(tipoVehiculo);
            txt_placa.setText(placa);
        }

        conexion.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }
    // Llamar al método para calcular e imprimir el importe
    calcularEImprimirImporte(placa);
}
     private void mostrarDatosDesdePlaca(String placa) {
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
            txt_entrada.setText(fechaEntrada + " " + horaEntrada);
        }

        String querySalida = "SELECT Fecha, Hora_salida FROM Registro_Salida " +
                             "WHERE Id_vehiculo = (SELECT Id_vehiculo FROM Vehiculo WHERE Placa = ?)";

        PreparedStatement pstmtSalida = conexion.prepareStatement(querySalida);
        pstmtSalida.setString(1, placa);
        ResultSet rsSalida = pstmtSalida.executeQuery();

        if (rsSalida.next()) {
            String fechaSalida = rsSalida.getString("Fecha");
            String horaSalida = rsSalida.getString("Hora_salida");
            txt_salida.setText(fechaSalida + " " + horaSalida);
        }

        // Obtener datos de incidentes
        String queryNovedades = "SELECT Descripcion FROM Incidentes_Problemas " +
                                "WHERE Id_vehiculo = (SELECT Id_vehiculo FROM Vehiculo WHERE Placa = ?)";

        PreparedStatement pstmtNovedades = conexion.prepareStatement(queryNovedades);
        pstmtNovedades.setString(1, placa);
        ResultSet rsNovedades = pstmtNovedades.executeQuery();

        if (rsNovedades.next()) {
            String descripcion = rsNovedades.getString("Descripcion");
            txt_novedades.setText(descripcion);
        } 
        

        conexion.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }
    }
    
    private void mostrarCodigoFactura() {
        int siguienteCodigo = obtenerSiguienteCodigoFactura();
        txt_codigo.setText(String.valueOf(siguienteCodigo));
    }  

    public int obtenerSiguienteCodigoFactura() {
    try {
        CONECTAR con = new CONECTAR();
        Connection conexion = con.conexion();

        String query = "SELECT MAX(Cod_Factura) AS UltimoCodigo FROM Ticket";
        PreparedStatement pstmt = conexion.prepareStatement(query);
        ResultSet rs = pstmt.executeQuery();

        int siguienteCodigo = 0;
        if (rs.next()) {
            siguienteCodigo = rs.getInt("UltimoCodigo") + 1;
        }

        conexion.close();
        return siguienteCodigo;
    } catch (SQLException e) {
        e.printStackTrace();
        return 0; 
    }
}
   

    private void limpiarCampos() {
        // Código para limpiar los campos txt_placa, txt_entrada, txt_salida, txt_novedades, txt_importe, etc.
        txt_placa.setText("");
        txt_entrada.setText("");
        txt_salida.setText("");
        txt_novedades.setText("");
        txt_importe.setText("");
        // ... otros campos que se deban limpiar
    }

    private void calcularEImprimirImporte(String placa) {
    try {
        CONECTAR con = new CONECTAR();
        Connection conexion = con.conexion();

        String entradaQuery = "SELECT Fecha, Hora_entrada FROM Registro_Entrada " +
                              "WHERE Id_vehiculo = (SELECT Id_vehiculo FROM Vehiculo WHERE Placa = ?)";
        PreparedStatement pstmtEntrada = conexion.prepareStatement(entradaQuery);
        pstmtEntrada.setString(1, placa);
        ResultSet rsEntrada = pstmtEntrada.executeQuery();

        String salidaQuery = "SELECT Fecha, Hora_salida FROM Registro_Salida " +
                             "WHERE Id_vehiculo = (SELECT Id_vehiculo FROM Vehiculo WHERE Placa = ?)";
        PreparedStatement pstmtSalida = conexion.prepareStatement(salidaQuery);
        pstmtSalida.setString(1, placa);
        ResultSet rsSalida = pstmtSalida.executeQuery();

        if (rsEntrada.next() && rsSalida.next()) {
            Timestamp fechaEntrada = rsEntrada.getTimestamp("Fecha");
            Time horaEntrada = rsEntrada.getTime("Hora_entrada");
            Timestamp fechaSalida = rsSalida.getTimestamp("Fecha");
            Time horaSalida = rsSalida.getTime("Hora_salida");

            double importe = calcularImporte(fechaEntrada, horaEntrada, fechaSalida, horaSalida);
            txt_importe.setText(String.valueOf(importe));
        }

        conexion.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
    
    public void setFechaActual() {
        Date fecha = new Date();        
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        lb_fecha.setText(formato.format(fecha));
    }
    
    private double calcularImporte(Timestamp fechaEntrada, Time horaEntrada, Timestamp fechaSalida, Time horaSalida) {
        long entrada = fechaEntrada.getTime() + horaEntrada.getTime();
        long salida = fechaSalida.getTime() + horaSalida.getTime();

        long diferenciaMillis = salida - entrada;
        long horas = diferenciaMillis / (60 * 60 * 1000); // Convertir diferencia a horas

        int horasACobrar = (int) horas;
        double importePorHora = 2.0;
        double importePorDia = 8.0;

        double importeTotal = horasACobrar * importePorHora;
        if (horasACobrar >= 24) {
            importeTotal = (horasACobrar / 24) * importePorDia;
            horasACobrar = horasACobrar % 24;
            importeTotal += horasACobrar * importePorHora;
        }

        return importeTotal;
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btn_imprimir = new javax.swing.JButton();
        btn_regresar = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lb_fecha = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txt_codigo = new javax.swing.JTextField();
        txt_cliente = new javax.swing.JTextField();
        txt_importe = new javax.swing.JTextField();
        txt_placa = new javax.swing.JTextField();
        txt_entrada = new javax.swing.JTextField();
        txt_salida = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txt_vehiculo = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        txt_novedades = new javax.swing.JTextPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        btn_imprimir.setBackground(new java.awt.Color(0, 0, 0));
        btn_imprimir.setForeground(new java.awt.Color(255, 255, 255));
        btn_imprimir.setText("Imprimir");
        btn_imprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_imprimirActionPerformed(evt);
            }
        });

        btn_regresar.setBackground(new java.awt.Color(0, 0, 0));
        btn_regresar.setForeground(new java.awt.Color(255, 255, 255));
        btn_regresar.setText("Regresar");
        btn_regresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_regresarActionPerformed(evt);
            }
        });

        jPanel1.setBackground(java.awt.SystemColor.activeCaptionBorder);
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel1.setText("Codigo de Factura");

        lb_fecha.setText("Fecha actual");

        jLabel3.setText("Fecha de Entrada");

        jLabel4.setText("Fecha de Salida");

        jLabel5.setText("Importe a Pagar");

        jLabel6.setText("Tipo de Vehiculo");

        jLabel7.setText("Placa de Vehiculo");

        jLabel9.setText("Estudiante");

        txt_codigo.setBackground(new java.awt.Color(204, 204, 204));
        txt_codigo.setBorder(null);

        txt_cliente.setBackground(new java.awt.Color(204, 204, 204));
        txt_cliente.setBorder(null);

        txt_importe.setBackground(new java.awt.Color(204, 204, 204));
        txt_importe.setBorder(null);

        txt_placa.setBackground(new java.awt.Color(204, 204, 204));
        txt_placa.setBorder(null);

        txt_entrada.setBackground(new java.awt.Color(204, 204, 204));
        txt_entrada.setBorder(null);

        txt_salida.setBackground(new java.awt.Color(204, 204, 204));
        txt_salida.setBorder(null);

        jLabel8.setFont(new java.awt.Font("Urdu Typesetting", 2, 24)); // NOI18N
        jLabel8.setText("El HANGAR ");

        txt_vehiculo.setBackground(new java.awt.Color(204, 204, 204));
        txt_vehiculo.setBorder(null);

        txt_novedades.setBackground(new java.awt.Color(204, 204, 204));
        jScrollPane1.setViewportView(txt_novedades);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(126, Short.MAX_VALUE)
                .addComponent(jLabel8)
                .addGap(64, 64, 64)
                .addComponent(lb_fecha)
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel5)
                                .addComponent(jLabel3)
                                .addComponent(jLabel4)
                                .addComponent(jLabel7))
                            .addGap(41, 41, 41)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txt_placa)
                                .addComponent(txt_salida)
                                .addComponent(txt_entrada)
                                .addComponent(txt_importe, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel1)
                                .addComponent(jLabel6)
                                .addComponent(jLabel9))
                            .addGap(37, 37, 37)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(txt_cliente, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txt_codigo)
                                .addComponent(txt_vehiculo, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lb_fecha)
                    .addComponent(jLabel8))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txt_codigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txt_cliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txt_vehiculo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txt_placa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txt_entrada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txt_salida, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txt_importe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(22, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(66, 66, 66)
                        .addComponent(btn_imprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(31, 31, 31)
                        .addComponent(btn_regresar, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btn_imprimir, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
                    .addComponent(btn_regresar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_imprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_imprimirActionPerformed
        imprimirTicket();
        this.dispose();
    }//GEN-LAST:event_btn_imprimirActionPerformed

    private void btn_regresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_regresarActionPerformed
        this.dispose();
    }//GEN-LAST:event_btn_regresarActionPerformed
    private void imprimirTicket() {
    if (txt_placa.getText().isEmpty() || txt_entrada.getText().isEmpty() || txt_salida.getText().isEmpty() || txt_importe.getText().isEmpty() || txt_novedades.getText().isEmpty()) {
        JOptionPane.showMessageDialog(this, "Complete todos los campos antes de imprimir el ticket.", "Campos Vacíos", JOptionPane.WARNING_MESSAGE);
        return; // Sale del método si algún campo está vacío
    }

    try {
        CONECTAR con = new CONECTAR();
        Connection conexion = con.conexion();

        // Obtener los datos a insertar
        int codigoFactura = obtenerSiguienteCodigoFactura();
        String fecha = lb_fecha.getText();
        String entrada = txt_entrada.getText();
        String salida = txt_salida.getText();
        double importe = Double.parseDouble(txt_importe.getText());

        // Obtener el Id_incidente relacionado con el campo txt_novedades
        String incidente = txt_novedades.getText();
        Integer idIncidente = obtenerIdIncidente(incidente, conexion);

        // Insertar datos en la tabla Ticket
        String insertQuery = "INSERT INTO Ticket (Cod_Factura, Fecha, Fecha_entrada, Fecha_salida, Importe, Id_vehiculo, Id_incidente) " +
                             "VALUES (?, ?, ?, ?, ?, (SELECT Id_vehiculo FROM Vehiculo WHERE Placa = ?), ?)";

        try (PreparedStatement pstmtInsert = conexion.prepareStatement(insertQuery)) {
            pstmtInsert.setInt(1, codigoFactura);
            pstmtInsert.setString(2, fecha);
            pstmtInsert.setString(3, entrada);
            pstmtInsert.setString(4, salida);
            pstmtInsert.setDouble(5, importe);
            pstmtInsert.setString(6, txt_placa.getText()); // asumiendo que txt_placa contiene la placa

            // Si idIncidente es null, no establecer el valor para Id_incidente
            if (idIncidente != null) {
                pstmtInsert.setInt(7, idIncidente.intValue());
            } else {
                pstmtInsert.setNull(7, java.sql.Types.INTEGER);
            }

            pstmtInsert.executeUpdate();
        }

        // Cerrar la conexión
        conexion.close();

        // Mensaje para indicar que se está imprimiendo el ticket
        JOptionPane.showMessageDialog(this, "Su Ticket se está imprimiendo");
    } catch (SQLException | NumberFormatException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error al imprimir el ticket");
    }
}

        
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
            java.util.logging.Logger.getLogger(Ticket.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Ticket.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Ticket.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Ticket.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Ticket().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_imprimir;
    private javax.swing.JButton btn_regresar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lb_fecha;
    private javax.swing.JTextField txt_cliente;
    private javax.swing.JTextField txt_codigo;
    private javax.swing.JTextField txt_entrada;
    private javax.swing.JTextField txt_importe;
    private javax.swing.JTextPane txt_novedades;
    private javax.swing.JTextField txt_placa;
    private javax.swing.JTextField txt_salida;
    private javax.swing.JTextField txt_vehiculo;
    // End of variables declaration//GEN-END:variables
}
