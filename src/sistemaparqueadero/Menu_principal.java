package sistemaparqueadero;
import javax.swing.*;

public class Menu_principal extends javax.swing.JFrame {
    
    Panel_IngresarVehiculo panelingresarvehiculo;
    Panel_RetirarVehiculo panelretirarvehiculo;
    Panel_ListarVehiculo panelListarVehiculos;
    Panel_Reservas panelReservas;
    Panel_Incidentes panelIncidentes;
    
    public Menu_principal() {
        initComponents();
        this.setLocationRelativeTo(null);
        setSize(778,450);  
        panelingresarvehiculo = new Panel_IngresarVehiculo();
        panelingresarvehiculo.setBounds(272,0,490,400);
        add(panelingresarvehiculo);
        panelretirarvehiculo = new Panel_RetirarVehiculo();
        panelretirarvehiculo.setBounds(300,0,473,400);
        add(panelretirarvehiculo);
        panelretirarvehiculo.setVisible(false);
        panelListarVehiculos = new Panel_ListarVehiculo();
        panelListarVehiculos.setBounds(300,0,1050,400);
        add(panelListarVehiculos);
        panelListarVehiculos.setVisible(false);
        panelReservas = new Panel_Reservas();
        panelReservas.setBounds(300,0,473,400);
        add(panelReservas);
        panelReservas.setVisible(false);  
        panelIncidentes = new Panel_Incidentes();
        panelIncidentes.setBounds(300,0,473,400);
        add(panelIncidentes);
        panelIncidentes.setVisible(false);
    }    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PanelMenu = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        btn_ingresar = new javax.swing.JButton();
        btn_retirar = new javax.swing.JButton();
        btn_listar = new javax.swing.JButton();
        btn_reservas = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        btn_listar1 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        PanelMenu.setBackground(new java.awt.Color(2, 91, 102));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGENES/Escudos.png"))); // NOI18N

        btn_ingresar.setBackground(new java.awt.Color(0, 153, 153));
        btn_ingresar.setFont(new java.awt.Font("Segoe UI Symbol", 0, 12)); // NOI18N
        btn_ingresar.setForeground(new java.awt.Color(255, 255, 255));
        btn_ingresar.setText("Ingresar Vehiculo");
        btn_ingresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ingresarActionPerformed(evt);
            }
        });

        btn_retirar.setBackground(new java.awt.Color(0, 153, 153));
        btn_retirar.setFont(new java.awt.Font("Segoe UI Symbol", 0, 12)); // NOI18N
        btn_retirar.setForeground(new java.awt.Color(255, 255, 255));
        btn_retirar.setText("Retirar Vehiculo");
        btn_retirar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_retirarActionPerformed(evt);
            }
        });

        btn_listar.setBackground(new java.awt.Color(0, 153, 153));
        btn_listar.setFont(new java.awt.Font("Segoe UI Symbol", 0, 12)); // NOI18N
        btn_listar.setForeground(new java.awt.Color(255, 255, 255));
        btn_listar.setText("Listar Vehiculo");
        btn_listar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_listarActionPerformed(evt);
            }
        });

        btn_reservas.setBackground(new java.awt.Color(0, 153, 153));
        btn_reservas.setFont(new java.awt.Font("Segoe UI Symbol", 0, 12)); // NOI18N
        btn_reservas.setForeground(new java.awt.Color(255, 255, 255));
        btn_reservas.setText("Reservas");
        btn_reservas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_reservasActionPerformed(evt);
            }
        });

        jButton6.setBackground(new java.awt.Color(0, 153, 153));
        jButton6.setFont(new java.awt.Font("Segoe UI Symbol", 0, 12)); // NOI18N
        jButton6.setForeground(new java.awt.Color(255, 255, 255));
        jButton6.setText("Salir del Sistema");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Leelawadee", 1, 36)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("EL HANGAR");

        btn_listar1.setBackground(new java.awt.Color(0, 153, 153));
        btn_listar1.setFont(new java.awt.Font("Segoe UI Symbol", 0, 12)); // NOI18N
        btn_listar1.setForeground(new java.awt.Color(255, 255, 255));
        btn_listar1.setText("Registrar Incidente");
        btn_listar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_listar1ActionPerformed(evt);
            }
        });

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGENES/logo parqueadero.png"))); // NOI18N

        javax.swing.GroupLayout PanelMenuLayout = new javax.swing.GroupLayout(PanelMenu);
        PanelMenu.setLayout(PanelMenuLayout);
        PanelMenuLayout.setHorizontalGroup(
            PanelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelMenuLayout.createSequentialGroup()
                .addGroup(PanelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelMenuLayout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(jLabel3))
                    .addGroup(PanelMenuLayout.createSequentialGroup()
                        .addGap(66, 66, 66)
                        .addGroup(PanelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btn_retirar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btn_ingresar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_reservas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btn_listar1, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btn_listar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(46, Short.MAX_VALUE))
            .addGroup(PanelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelMenuLayout.createSequentialGroup()
                    .addContainerGap(61, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(39, 39, 39)))
        );
        PanelMenuLayout.setVerticalGroup(
            PanelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelMenuLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addGap(171, 171, 171)
                .addComponent(btn_ingresar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_retirar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btn_reservas, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btn_listar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btn_listar1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26))
            .addGroup(PanelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(PanelMenuLayout.createSequentialGroup()
                    .addGap(81, 81, 81)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(453, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(PanelMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 396, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(PanelMenu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_ingresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ingresarActionPerformed
        setSize(778,450);
        panelListarVehiculos.setVisible(false);
        panelReservas.setVisible(false);
        panelretirarvehiculo.setVisible(false);
        panelingresarvehiculo.setVisible(true);
        panelingresarvehiculo.limpiarCampos();
        panelIncidentes.setVisible(false);
        /*panelingresarvehiculo.cargarUbicacionesDisponibles();*/
        
        revalidate();        
        repaint();        
        
    }//GEN-LAST:event_btn_ingresarActionPerformed

    private void btn_retirarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_retirarActionPerformed
        // TODO add your handling code here:
        setSize(778,450);
        panelListarVehiculos.setVisible(false);
        panelReservas.setVisible(false);
        panelingresarvehiculo.setVisible(false);
        panelretirarvehiculo.setVisible(true);
        panelIncidentes.setVisible(false);
        panelretirarvehiculo.limpiarCampos();
        
         revalidate();
        repaint();
    }//GEN-LAST:event_btn_retirarActionPerformed

    private void btn_listarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_listarActionPerformed
        // TODO add your handling code here:
        setSize(1055,450);
        add(panelListarVehiculos);
        panelListarVehiculos.setVisible(true);
        panelReservas.setVisible(false);
        panelingresarvehiculo.setVisible(false);
        panelretirarvehiculo.setVisible(false);
        panelIncidentes.setVisible(false);
        panelListarVehiculos.limpiarCampos();
        
        revalidate();
        repaint();
    }//GEN-LAST:event_btn_listarActionPerformed

    private void btn_reservasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_reservasActionPerformed
        setSize(778,450);
        panelListarVehiculos.setVisible(false);
        panelReservas.setVisible(true);
        panelingresarvehiculo.setVisible(false);
        panelretirarvehiculo.setVisible(false);
        panelIncidentes.setVisible(false);
        panelReservas.limpiarCampos();
        
        revalidate();
        repaint();
    }//GEN-LAST:event_btn_reservasActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
         int option = JOptionPane.showConfirmDialog(null, "¿Desea salir del sistema?", "Confirmar Salida", JOptionPane.YES_NO_OPTION);
        
        if (option == JOptionPane.YES_OPTION) {            
            System.exit(0); // Cierra el programa
            }
    }//GEN-LAST:event_jButton6ActionPerformed

    private void btn_listar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_listar1ActionPerformed
        setSize(778,450);
        panelListarVehiculos.setVisible(false);
        panelReservas.setVisible(false);
        panelretirarvehiculo.setVisible(false);
        panelingresarvehiculo.setVisible(false);
        panelIncidentes.setVisible(true);
        panelIncidentes.limpiarCampos();
        
        revalidate();        
        repaint();
    }//GEN-LAST:event_btn_listar1ActionPerformed

    public static void main(String args[]) {        

       
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Menu_principal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanelMenu;
    private javax.swing.JButton btn_ingresar;
    private javax.swing.JButton btn_listar;
    private javax.swing.JButton btn_listar1;
    private javax.swing.JButton btn_reservas;
    private javax.swing.JButton btn_retirar;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    // End of variables declaration//GEN-END:variables
}
