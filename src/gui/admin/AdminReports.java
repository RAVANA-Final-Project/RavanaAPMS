package gui.admin;

import gui.SplashScreen;
import java.awt.Component;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRTableModelDataSource;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author dulanjaya
 */
public class AdminReports extends javax.swing.JPanel {
    
    public static final int VIEW_AND_SAVE_REPORT = 0;
    public static final int SAVE_ONLY_REPORT = 1;
    
    AdminDashBoard adminDashBoard;
    
    /**
     * Creates new form Booking
     */
    public AdminReports(AdminDashBoard adminDashBoard) {
        initComponents();
        this.adminDashBoard = adminDashBoard;
    }
    
    // implemented by Dulanjaya
    public static void reportConstruct(Component parentComponent, InputStream reportPath, ArrayList parameterArray, JTable targetTable, String outputFileName) {

        HashMap<String, Object> parameters = new HashMap<>();

        for (int p = 0; p < parameterArray.size(); p++) {
            parameters.put("Parameter" + (p + 1), parameterArray.get(p));
        }

        JRTableModelDataSource tableModelDataSource = new JRTableModelDataSource(targetTable.getModel());

        try {
            JasperPrint jasperPrint = JasperFillManager.fillReport(reportPath, parameters, tableModelDataSource);

            String message = null;

            if (outputFileName == null || outputFileName.isEmpty()) {
                message = "Report generated";
            } else {
                message = "Report saved";
            }

            if (message != null) {
                JOptionPane.showMessageDialog(parentComponent, message, "Success", JOptionPane.INFORMATION_MESSAGE);
                
                if(message.equals("Report generated")){
                    JasperViewer.viewReport(jasperPrint, false);
                    
                }else if(message.equals("Report saved")){
                    JasperExportManager.exportReportToPdfFile(jasperPrint, (outputFileName + ".pdf"));
                    
                }
            }

        } catch (JRException jre) {
            jre.printStackTrace();
            SplashScreen.logger.log(Level.WARNING, "Warning Message", jre);
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

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

        jPanel1.setLayout(new java.awt.BorderLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Reports");
        jPanel1.add(jLabel1, java.awt.BorderLayout.CENTER);

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("You can select the main reports...");
        jLabel2.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 1, 1, 1));
        jPanel1.add(jLabel2, java.awt.BorderLayout.PAGE_END);

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/iconPack64/report.png"))); // NOI18N
        jLabel3.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 1, 1, 1));
        jPanel1.add(jLabel3, java.awt.BorderLayout.PAGE_START);

        add(jPanel1, java.awt.BorderLayout.PAGE_START);

        jPanel2.setMinimumSize(new java.awt.Dimension(200, 368));
        jPanel2.setPreferredSize(new java.awt.Dimension(200, 368));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 200, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );

        add(jPanel2, java.awt.BorderLayout.LINE_START);

        jPanel3.setMinimumSize(new java.awt.Dimension(200, 368));
        jPanel3.setPreferredSize(new java.awt.Dimension(200, 400));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 200, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );

        add(jPanel3, java.awt.BorderLayout.LINE_END);

        jPanel4.setLayout(new java.awt.BorderLayout());

        jPanel5.setPreferredSize(new java.awt.Dimension(338, 50));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 508, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );

        jPanel4.add(jPanel5, java.awt.BorderLayout.PAGE_START);

        jPanel6.setLayout(new java.awt.GridLayout(6, 1, 0, 15));

        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton1.setText("Daily Income");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel6.add(jButton1);

        jButton2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton2.setText("Monthly Income");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel6.add(jButton2);

        jButton3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton3.setText("Employee Biodata");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel6.add(jButton3);

        jButton4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton4.setText("Customer Biodata");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel6.add(jButton4);

        jPanel4.add(jPanel6, java.awt.BorderLayout.CENTER);

        add(jPanel4, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        if(this.adminDashBoard != null){
            adminDashBoard.setJPanelLoad(new DailyIncomeReport(this.adminDashBoard));
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        if(this.adminDashBoard != null){
            adminDashBoard.setJPanelLoad(new MonthlyIncomeReport(this.adminDashBoard));
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        if(this.adminDashBoard != null){
            adminDashBoard.setJPanelLoad(new EmployeeBioData(this.adminDashBoard));
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        if(this.adminDashBoard != null){
            adminDashBoard.setJPanelLoad(new CustomerBioData(this.adminDashBoard));
        }
    }//GEN-LAST:event_jButton4ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    // End of variables declaration//GEN-END:variables
}
