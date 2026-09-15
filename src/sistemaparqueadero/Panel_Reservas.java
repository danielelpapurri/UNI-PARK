package sistemaparqueadero;
import Clases.CONECTAR;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.Date;
import Clases.GestorReservas;
import javax.swing.DefaultComboBoxModel;

public class Panel_Reservas extends javax.swing.JPanel {  
    
    /*Visualizar_reservas panelVisualizar;
    Panel_Reservas panelReservas;*/
    public Panel_Reservas() {
        initComponents(); 
        cb_ubicacion.setSelectedIndex(-1);
        cargarUbicacionesLibres();
        GestorReservas gestorReservas = new GestorReservas();
        gestorReservas.iniciarGestorReservas();
        txt_propietario.requestFocus();         
    }    
    public void limpiarCampos() {
        txt_propietario.setText("");
        dc_fecha.setDate(null);
        txt_inicio.setText("");
        txt_fin.setText("");
        cb_ubicacion.setSelectedIndex(-1);
        txt_propietario.requestFocus();
    }
    private void cargarUbicacionesLibres() {
        Connection con = null;
        try {
            con = new CONECTAR().conexion();
            String query = "SELECT Nombre_plaza FROM Espacios_estacionamiento WHERE Estado = 'Libre'";
            PreparedStatement pstmt = con.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();

            DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();

            while (rs.next()) {
                String ubicacion = rs.getString("Nombre_plaza");
                model.addElement(ubicacion);
            }

            cb_ubicacion.setModel(model);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        btn_reservar = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        btn_visualizarReservas = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txt_inicio = new javax.swing.JTextField();
        txt_fin = new javax.swing.JTextField();
        dc_fecha = new com.toedter.calendar.JDateChooser();
        cb_ubicacion = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        txt_propietario = new javax.swing.JTextField();

        jLabel6.setText("Hora de Inicio de Reserva");

        jLabel7.setText("Hora Fin de Reserva");

        jLabel5.setText("Fecha de Reserva");

        btn_reservar.setBackground(new java.awt.Color(255, 0, 0));
        btn_reservar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_reservar.setForeground(new java.awt.Color(255, 255, 255));
        btn_reservar.setText("Reservar");
        btn_reservar.setBorder(new javax.swing.border.MatteBorder(null));
        btn_reservar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_reservarActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(255, 0, 0));
        jButton2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Regresar");
        jButton2.setBorder(new javax.swing.border.MatteBorder(null));

        btn_visualizarReservas.setBackground(new java.awt.Color(255, 0, 0));
        btn_visualizarReservas.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_visualizarReservas.setForeground(new java.awt.Color(255, 255, 255));
        btn_visualizarReservas.setText("Visualizar Reservas");
        btn_visualizarReservas.setBorder(new javax.swing.border.MatteBorder(null));
        btn_visualizarReservas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_visualizarReservasActionPerformed(evt);
            }
        });

        jLabel3.setText("Ubicación/Nom.Plaza");

        jPanel5.setBackground(new java.awt.Color(0, 51, 51));

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Segoe UI Symbol", 1, 16)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Módulo de Reservación para Parqueadero");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(61, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(74, 74, 74))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel1)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        jLabel4.setText("Nombre Propietario");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btn_visualizarReservas, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_reservar, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(117, 117, 117))
            .addGroup(layout.createSequentialGroup()
                .addGap(64, 64, 64)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(jLabel3)
                    .addComponent(jLabel7)
                    .addComponent(jLabel4))
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(dc_fecha, javax.swing.GroupLayout.DEFAULT_SIZE, 156, Short.MAX_VALUE)
                    .addComponent(txt_inicio)
                    .addComponent(txt_fin)
                    .addComponent(cb_ubicacion, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txt_propietario))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txt_propietario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(cb_ubicacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dc_fecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_inicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_fin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                .addComponent(btn_reservar, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(btn_visualizarReservas, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btn_visualizarReservasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_visualizarReservasActionPerformed
        VisualizarReservas menu = new VisualizarReservas();
        menu.setVisible(true);
    }//GEN-LAST:event_btn_visualizarReservasActionPerformed

    private void btn_reservarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_reservarActionPerformed
        String propietario = txt_propietario.getText();
        String ubicacion = cb_ubicacion.getSelectedItem().toString();

        String fechaReserva = new SimpleDateFormat("yyyy-MM-dd").format(dc_fecha.getDate());
        String horaInicio = txt_inicio.getText();
        String horaFin = txt_fin.getText();

        Connection con = null;
    try {
        con = new CONECTAR().conexion();
        String estado = "Activa";
        if (verificarCaducidad(horaFin)) {
            estado = "Caducada";
        }

        String insertQuery = "INSERT INTO Reservas (Id_plaza_estacionamiento, Nombre_reservista, Fecha_reserva, Hora_inicio_reserva, Hora_fin_reserva, Estado_reserva) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = con.prepareStatement(insertQuery);

        pstmt.setInt(1, getIdPlaza(ubicacion));
            pstmt.setString(2, propietario);
            pstmt.setString(3, fechaReserva);
            pstmt.setString(4, horaInicio);
            pstmt.setString(5, horaFin);
            pstmt.setString(6, estado);

        pstmt.executeUpdate();

        // Actualizar el estado del espacio de estacionamiento a 'Ocupado' en la base de datos
        if (estado.equals("Activa")) {
            actualizarEstadoEspacioEstacionamiento(ubicacion, "Ocupado");
        }
        if (estado.equals("Caducada")) {
        actualizarEstadoEspacioEstacionamiento(ubicacion, "Libre");
    }    
        JOptionPane.showMessageDialog(this, "Reserva realizada con éxito.");
        
        txt_propietario.setText("");
        dc_fecha.setDate(null); 
        txt_inicio.setText("");
        txt_fin.setText(""); 
        cb_ubicacion.removeItem(ubicacion);
        cb_ubicacion.setSelectedIndex(-1);
        txt_propietario.requestFocus();        

    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        try {
            if (con != null) {
                con.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }   
    }//GEN-LAST:event_btn_reservarActionPerformed
    private void actualizarEstadoEspacioEstacionamiento(String ubicacion, String estado) {
        Connection con = null;
        try {
            con = new CONECTAR().conexion();
            String updateQuery = "UPDATE Espacios_estacionamiento SET Estado = ? WHERE Nombre_plaza = ?";
            PreparedStatement pstmt = con.prepareStatement(updateQuery);
            pstmt.setString(1, estado);
            pstmt.setString(2, ubicacion);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }   
    private boolean verificarCaducidad(String horaFin) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            Date horaFinDate = sdf.parse(horaFin);
            Date horaActual = new Date();

            // Crear una instancia de Calendar para comparar las horas
            Calendar calFin = Calendar.getInstance();
            Calendar calActual = Calendar.getInstance();
            calFin.setTime(horaFinDate);
            calActual.setTime(horaActual);

            // Establecer la fecha para ambas instancias de Calendar para comparar solo la hora
            calFin.set(1, 1, 1);
            calActual.set(1, 1, 1);

            // Verificar si la hora actual es posterior a la hora de finalización
            return calActual.after(calFin);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }        
    private int getIdPlaza(String ubicacion) {
        int idPlazaEstacionamiento = 0; // Valor por defecto
        Connection con = null;
        try {
        con = new CONECTAR().conexion();
        String query = "SELECT Id_plaza FROM Espacios_estacionamiento WHERE Nombre_plaza = ?";
        PreparedStatement pstmt = con.prepareStatement(query);
        pstmt.setString(1, ubicacion);

        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            idPlazaEstacionamiento = rs.getInt("Id_plaza");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        try {
            if (con != null) {
                con.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    return idPlazaEstacionamiento;
}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_reservar;
    private javax.swing.JButton btn_visualizarReservas;
    private javax.swing.JComboBox<String> cb_ubicacion;
    private com.toedter.calendar.JDateChooser dc_fecha;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JTextField txt_fin;
    private javax.swing.JTextField txt_inicio;
    private javax.swing.JTextField txt_propietario;
    // End of variables declaration//GEN-END:variables
}
