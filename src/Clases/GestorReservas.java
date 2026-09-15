package Clases;

import com.mysql.jdbc.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author Administrator
 */
public class GestorReservas {
    public void iniciarGestorReservas() {
        // Ejecutar la verificación de reservas caducadas cada cierto tiempo
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            public void run() {
                verificarReservasCaducadas();
            }
        };
        // Programar la tarea para que se ejecute cada 30 minutos (cambiar según tu necesidad)
        timer.schedule(task, 0, 30 * 60 * 1000); // 30 minutos en milisegundos
    }
    private void verificarReservasCaducadas() {
        // Conectar con la base de datos
        CONECTAR miConexion = new CONECTAR();
        Connection con = miConexion.conexion();


        try {
            // Obtener todas las reservas activas
            String query = "SELECT Id_reserva, Hora_fin_reserva FROM Reservas WHERE Estado_reserva = 'Activa'";
            Statement stmt = (Statement) con.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                int idReserva = rs.getInt("Id_reserva");
                String horaFinReserva = rs.getString("Hora_fin_reserva");

                // Verificar si la reserva ha caducado
                if (verificarCaducidad(horaFinReserva)) {
                    // Si ha caducado, actualizar el estado a 'Caducada'
                    String updateQuery = "UPDATE Reservas SET Estado_reserva = 'Caducada' WHERE Id_reserva = ?";
                    PreparedStatement pstmt = con.prepareStatement(updateQuery);
                    pstmt.setInt(1, idReserva);
                    pstmt.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
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
    
}
