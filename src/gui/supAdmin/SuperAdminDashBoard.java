package gui.supAdmin;

import gui.LoginJunction;
import gui.WelcomeScreen;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import model.Admin;
import theme.CustomMacDarkLaf;

/**
 *
 * @author dulanjaya
 */
public class SuperAdminDashBoard extends javax.swing.JFrame {
    
    private Admin admin;

    private ArrayList<JButton> buttonList = new ArrayList<>();
    private ArrayList<String> buttonNameList = new ArrayList<>();
    private JPanel currentJPanel;
    
    public void setJPanelLoad(JPanel outerJPanel){
        jPanelLoad(outerJPanel);
    }
    
    public void setAdminUserData(Admin admin){
        this.admin = admin;
    }

    /**
     * Creates new form AdminDashBoard
     */
    public SuperAdminDashBoard() {
        initComponents();
        jPanelLoad(new WelcomeScreen());
        
        try{
            
            ImageIcon imageIcon = new ImageIcon(getClass().getResource("/resource/Ravana.png"));
            this.setIconImage(imageIcon.getImage());
        }catch(Exception e){
            JOptionPane.showMessageDialog(this, "Image Loading Error", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        
        Thread digitalClock = new Thread(() -> {

            while (true) {

                jLabel8.setText(new SimpleDateFormat("yyy/MM/dd hh.mm.ss a").format(new Date()));
                
                try{
                    Thread.sleep(1000);
                }catch(InterruptedException ie){
                    ie.printStackTrace();
                }
            }

        });
        
        digitalClock.start();
    }
    
    private void jPanelLoad(JPanel targetJPanle) {
        if (this.currentJPanel != null)
            jPanel4.remove(this.currentJPanel);

        this.currentJPanel = targetJPanle;
        jPanel4.add(targetJPanle, BorderLayout.CENTER);
        SwingUtilities.updateComponentTreeUI(jPanel4);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jButton8 = new javax.swing.JButton();
        jPanel11 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jButton9 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("RavanaAPMS");
        setMinimumSize(new java.awt.Dimension(900, 710));
        setPreferredSize(new java.awt.Dimension(1024, 710));

        jPanel1.setBackground(new java.awt.Color(31, 51, 34));
        jPanel1.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 1, 10));
        jPanel1.setMinimumSize(new java.awt.Dimension(426, 100));
        jPanel1.setPreferredSize(new java.awt.Dimension(1024, 100));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel5.setOpaque(false);
        jPanel5.setPreferredSize(new java.awt.Dimension(200, 107));
        jPanel5.setLayout(new java.awt.BorderLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel1.setText("Management System");
        jLabel1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 16, 10, 1));
        jPanel5.add(jLabel1, java.awt.BorderLayout.PAGE_END);

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/ravana_logo1.png"))); // NOI18N
        jLabel2.setPreferredSize(new java.awt.Dimension(215, 77));
        jPanel5.add(jLabel2, java.awt.BorderLayout.CENTER);

        jPanel1.add(jPanel5, java.awt.BorderLayout.LINE_START);

        jPanel6.setOpaque(false);
        jPanel6.setLayout(new java.awt.BorderLayout());

        jPanel7.setOpaque(false);

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/iconPack24/clock.png"))); // NOI18N

        jLabel8.setText("2024/05/09   10.00 PM");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap(64, Short.MAX_VALUE)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap(21, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel6.add(jPanel7, java.awt.BorderLayout.CENTER);

        jPanel8.setOpaque(false);
        jPanel8.setLayout(new java.awt.BorderLayout());

        jLabel4.setBackground(new java.awt.Color(51, 51, 51));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/iconPack24/user.png"))); // NOI18N
        jPanel8.add(jLabel4, java.awt.BorderLayout.CENTER);

        jPanel9.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 5, 1, 5));
        jPanel9.setOpaque(false);
        jPanel9.setLayout(new java.awt.BorderLayout());

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setText("Welcome");
        jPanel9.add(jLabel5, java.awt.BorderLayout.CENTER);

        jLabel6.setText("Super Administrator");
        jPanel9.add(jLabel6, java.awt.BorderLayout.PAGE_END);

        jPanel8.add(jPanel9, java.awt.BorderLayout.LINE_END);

        jPanel6.add(jPanel8, java.awt.BorderLayout.PAGE_START);

        jPanel1.add(jPanel6, java.awt.BorderLayout.LINE_END);

        getContentPane().add(jPanel1, java.awt.BorderLayout.PAGE_START);

        jPanel2.setBackground(new java.awt.Color(20, 32, 21));
        jPanel2.setBorder(javax.swing.BorderFactory.createEmptyBorder(15, 10, 10, 10));
        jPanel2.setPreferredSize(new java.awt.Dimension(170, 688));
        jPanel2.setLayout(new java.awt.BorderLayout());

        jPanel10.setOpaque(false);

        jButton8.setBackground(new java.awt.Color(50, 51, 51));
        jButton8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/iconPack24/shut_down.png"))); // NOI18N
        jButton8.setText("Log Out");
        jButton8.setToolTipText("Log Out");
        jButton8.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jButton8, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addContainerGap(52, Short.MAX_VALUE)
                .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel2.add(jPanel10, java.awt.BorderLayout.PAGE_END);

        jPanel11.setOpaque(false);
        jPanel11.setLayout(new java.awt.GridLayout(8, 1, 0, 15));

        jPanel12.setOpaque(false);

        jButton9.setBackground(new java.awt.Color(50, 51, 51));
        jButton9.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/iconPack24/pin.png"))); // NOI18N
        jButton9.setToolTipText("Minify Navigator");
        jButton9.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                .addContainerGap(112, Short.MAX_VALUE)
                .addComponent(jButton9)
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton9)
                .addContainerGap(21, Short.MAX_VALUE))
        );

        jPanel11.add(jPanel12);

        jButton1.setBackground(new java.awt.Color(50, 51, 51));
        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/iconPack24/image_accept.png"))); // NOI18N
        jButton1.setText("Attendance");
        jButton1.setToolTipText("Customer");
        jButton1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel11.add(jButton1);

        jButton3.setBackground(new java.awt.Color(50, 51, 51));
        jButton3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/iconPack24/business_male_female_users.png"))); // NOI18N
        jButton3.setText("Employees");
        jButton3.setToolTipText("Administrators");
        jButton3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel11.add(jButton3);

        jButton7.setBackground(new java.awt.Color(50, 51, 51));
        jButton7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/iconPack24/dollar_currency_sign.png"))); // NOI18N
        jButton7.setText("Invoice");
        jButton7.setToolTipText("Invoice");
        jButton7.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        jPanel11.add(jButton7);

        jButton6.setBackground(new java.awt.Color(50, 51, 51));
        jButton6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/iconPack24/report.png"))); // NOI18N
        jButton6.setText("Reports");
        jButton6.setToolTipText("Reports");
        jButton6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        jPanel11.add(jButton6);

        jPanel2.add(jPanel11, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel2, java.awt.BorderLayout.LINE_START);

        jPanel3.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        jPanel3.setLayout(new java.awt.BorderLayout());

        jPanel4.setLayout(new java.awt.BorderLayout());
        jPanel3.add(jPanel4, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel3, java.awt.BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        // TODO add your handling code here:
        if (buttonList == null && buttonNameList == null) {
            buttonList = new ArrayList<>();
            buttonNameList = new ArrayList<>();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    buttonList.add(jButton1);
                    buttonNameList.add(jButton1.getText());
                    buttonList.add(jButton3);
                    buttonNameList.add(jButton3.getText());
                    buttonList.add(jButton6);
                    buttonNameList.add(jButton6.getText());
                    buttonList.add(jButton7);
                    buttonNameList.add(jButton7.getText());
                    buttonList.add(jButton8);
                    buttonNameList.add(jButton8.getText());

                    jPanel2.setPreferredSize(new Dimension(70, jPanel2.getPreferredSize().height));
                    
                    jButton9.setToolTipText("Expand Navigator");

                    for (int s = 0; s < buttonList.size(); s++) {
                        JButton iterateButton = (JButton) buttonList.get(s);
                        iterateButton.setHorizontalAlignment(SwingConstants.CENTER);
                        iterateButton.setText("");
                    }

                    SwingUtilities.updateComponentTreeUI(jPanel2);
                }
            }).start();
        } else {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    jPanel2.setPreferredSize(new Dimension(170, jPanel2.getPreferredSize().height));

                    jButton9.setToolTipText("Minify Navigator");
                    
                    for (int s = 0; s < buttonList.size(); s++) {
                        JButton iterateButton = (JButton) buttonList.get(s);
                        iterateButton.setHorizontalAlignment(SwingConstants.LEFT);
                        iterateButton.setText(String.valueOf(buttonNameList.get(s)));
                    }

                    buttonList = null;
                    buttonNameList = null;

                    SwingUtilities.updateComponentTreeUI(jPanel2);
                }
            }).start();
        }
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        jPanelLoad(new EmployeeAttendance(this));
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        jPanelLoad(new Admins(this));
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
        jPanelLoad(new Invoice(this));
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        jPanelLoad(new Reports(this));
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // TODO add your handling code here:
        int userDecision = JOptionPane.showConfirmDialog(this, "Are you sure want to log out?", "Question", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
        
        if(userDecision == JOptionPane.YES_OPTION){
            this.admin = null;
            LoginJunction loginJunction = new LoginJunction();
            loginJunction.setVisible(true);
            this.dispose();
        }
    }//GEN-LAST:event_jButton8ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        CustomMacDarkLaf.setup();

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SuperAdminDashBoard().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    // End of variables declaration//GEN-END:variables
}
