package gui.admin;

import gui.SplashScreen;
import java.awt.Component;
import java.io.InputStream;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;
import java.util.logging.Level;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.MySQL;

/**
 *
 * @author dulanjaya
 */
public class DailyIncomeReport extends javax.swing.JPanel {

    private AdminDashBoard adminDashBoard;
    
//    public static void setReportDataInitialize(){
//        
//    }

    /**
     * Creates new form ForeignerBooking
     */
    public DailyIncomeReport(AdminDashBoard adminDashBoard) {
        initComponents();
        this.adminDashBoard = adminDashBoard;
        loadReportData();
    }

    private void loadReportData() {
        int invoiceCount = 0;
        int foreignCustomerCount = 0;
        int localCustomerCount = 0;
        double subTotal = 0;

        try {

            ResultSet todayInvoiceResultset = MySQL.execute("SELECT * FROM `invoice` INNER JOIN `customer` ON "
                    + "`invoice`.`customer_customer_mobile`=`customer`.`customer_mobile` INNER JOIN `customer_type` ON "
                    + "`customer`.`customer_type_customer_type_id`=`customer_type`.`customer_type_id` WHERE "
                    + "`purchased_date` LIKE '" + new SimpleDateFormat("yyy-MM-dd").format(new Date()) + "' ORDER BY `invoice_id` DESC");

            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            model.setRowCount(0);

            int counter = 0;

            while (todayInvoiceResultset.next()) {
                counter++;
                invoiceCount++;

                if (todayInvoiceResultset.getString("customer_type_name").equals("Foreign")) {
                    foreignCustomerCount++;
                } else if (todayInvoiceResultset.getString("customer_type_name").equals("Local")) {
                    localCustomerCount++;
                }

                try {
                    int invoiceItemCount = 0;
                    double invoicePotion = 0;

                    ResultSet invoiceItemResultset = MySQL.execute("SELECT * FROM `invoice_item` WHERE `invoice_invoice_id`='" + todayInvoiceResultset.getString("invoice_id") + "'");

                    while (invoiceItemResultset.next()) {
                        invoiceItemCount++;

                        double price = 0;
                        double offer = 0;

                        try {
                            ResultSet priceResultset = MySQL.execute("SELECT * FROM `price_list` WHERE "
                                    + "`activity_activity_id`='" + invoiceItemResultset.getInt("activity_activity_id") + "' AND "
                                    + "`update_date`<='" + todayInvoiceResultset.getString("purchased_date") + "' ORDER BY `update_date` DESC LIMIT 1");

                            if (priceResultset.next()) {
                                price = priceResultset.getDouble("amount");
                            }

                            ResultSet offerResultset = MySQL.execute("SELECT * FROM `activity_has_offer_type` WHERE `activity_activity_id`='" + invoiceItemResultset.getInt("activity_activity_id") + "'");

                            if (offerResultset.next()) {
                                offer = offerResultset.getDouble("offer_presentage");
                            }

                            invoicePotion += (price * invoiceItemResultset.getInt("qty")) * ((100 - offer) / 100);

                        } catch (Exception e) {
                            e.printStackTrace();
                            SplashScreen.logger.log(Level.WARNING, "Warning Message", e);
                        }
                    }

                    subTotal += invoicePotion;

                    Vector<String> vector = new Vector<>();
                    vector.add(String.valueOf(counter));
                    vector.add(todayInvoiceResultset.getString("invoice_id"));
                    vector.add(todayInvoiceResultset.getString("first_name") + " " + todayInvoiceResultset.getString("last_name"));
                    vector.add(todayInvoiceResultset.getString("customer_type_name"));
                    vector.add(String.valueOf(invoiceItemCount));
                    vector.add(new DecimalFormat("0.00").format(invoicePotion));

                    model.addRow(vector);
                } catch (Exception e) {
                    e.printStackTrace();
                    SplashScreen.logger.log(Level.WARNING, "Warning Message", e);
                }
            }

            jLabel5.setText(String.valueOf(invoiceCount));
            jLabel7.setText(String.valueOf(foreignCustomerCount));
            jLabel9.setText(String.valueOf(localCustomerCount));
            jLabel11.setText(new DecimalFormat("0.00").format(subTotal));
        } catch (Exception e) {
            e.printStackTrace();
            SplashScreen.logger.log(Level.WARNING, "Warning Message", e);
        }
    }

    // implemented by Dulanjaya
    private void reportDataInitialize(int outputType) {
        if (jTable1.getRowCount() > 0) {
            int userDecidion = JOptionPane.showConfirmDialog(this, "Are you sure want to " + (outputType == AdminReports.VIEW_AND_SAVE_REPORT ? "generate" : outputType == AdminReports.SAVE_ONLY_REPORT ? "save" : "") + " this report?", "Question", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);

            if (userDecidion == JOptionPane.YES_OPTION) {
                
                // Code customizing area => start
                
                // give your report path
                InputStream reportPath = getClass().getResourceAsStream("/reports/daily_income.jasper");
                
                // give your output PDF file name & please don't give ".pdf" file extention 
                // if you don't want to save as a PDF, then give "null" 
                String outputfileName = "Admin/Reports/Income_Reports/Daily_Income_Reports/Daily_income_report_" + new SimpleDateFormat("yyy-MM-dd_hh-mm-ss-a").format(new Date()) + "_Ravana_APMS";

                // you can add your parameters to this ArrayList 
                // Remeber; The parameters in your jasper report should be entered into this ArrayList in the same order.
                ArrayList parameterArray = new ArrayList();
                
                // give your parameters to right order
                parameterArray.add(String.valueOf(System.currentTimeMillis()) + " - Rep No.");
                parameterArray.add(new SimpleDateFormat("yyy-MM-dd hh:mm:ss a").format(new Date()));
                parameterArray.add(jLabel5.getText());
                parameterArray.add(jLabel7.getText());
                parameterArray.add(jLabel9.getText());
                parameterArray.add(jLabel11.getText());
                parameterArray.add(jLabel11.getText());
                
                // Code customizing area => end

                if (outputType == AdminReports.VIEW_AND_SAVE_REPORT) {
                    AdminReports.reportConstruct(this, reportPath, parameterArray, jTable1, null);
                } else if (outputType == AdminReports.SAVE_ONLY_REPORT) {
                    AdminReports.reportConstruct(this, reportPath, parameterArray, jTable1, outputfileName);
                }
            }
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
        jPanel7 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jPanel6 = new javax.swing.JPanel();
        jButton4 = new javax.swing.JButton();
        jPanel15 = new javax.swing.JPanel();
        jPanel17 = new javax.swing.JPanel();
        jSeparator3 = new javax.swing.JSeparator();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setLayout(new java.awt.BorderLayout());

        jPanel1.setBackground(new java.awt.Color(204, 204, 255));
        jPanel1.setMinimumSize(new java.awt.Dimension(100, 35));
        jPanel1.setPreferredSize(new java.awt.Dimension(949, 36));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel7.setLayout(new java.awt.BorderLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/iconPack24/folder_full.png"))); // NOI18N
        jLabel1.setText("Daily Income Report");
        jPanel7.add(jLabel1, java.awt.BorderLayout.LINE_START);
        jPanel7.add(jSeparator2, java.awt.BorderLayout.PAGE_END);

        jPanel6.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 5, 1));
        jPanel6.setLayout(new java.awt.GridLayout(1, 3, 10, 0));

        jButton4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/iconPack24/back.png"))); // NOI18N
        jButton4.setText("Go Back");
        jButton4.setToolTipText("Go Back");
        jButton4.setContentAreaFilled(false);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel6.add(jButton4);

        jPanel7.add(jPanel6, java.awt.BorderLayout.LINE_END);

        jPanel1.add(jPanel7, java.awt.BorderLayout.CENTER);

        add(jPanel1, java.awt.BorderLayout.PAGE_START);

        jPanel15.setLayout(new java.awt.BorderLayout());

        jPanel17.setPreferredSize(new java.awt.Dimension(310, 581));
        jPanel17.setLayout(new java.awt.BorderLayout());

        jSeparator3.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jPanel17.add(jSeparator3, java.awt.BorderLayout.LINE_START);

        jPanel3.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 5, 1, 1));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setText("Report Summary");

        jPanel8.setLayout(new java.awt.GridLayout(5, 2, 10, 0));

        jLabel4.setText("Total Invoice :");
        jPanel8.add(jLabel4);

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("0");
        jPanel8.add(jLabel5);

        jLabel8.setText("Total Foreign Customers : ");
        jPanel8.add(jLabel8);

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("0");
        jPanel8.add(jLabel7);

        jLabel6.setText("Total Local Customers :");
        jPanel8.add(jLabel6);

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel9.setText("0");
        jPanel8.add(jLabel9);

        jLabel10.setText("Total Income (Rs) :");
        jPanel8.add(jLabel10);

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel11.setText("0");
        jPanel8.add(jLabel11);

        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/iconPack16/search_printer.png"))); // NOI18N
        jButton1.setText("View & Print");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel8.add(jButton1);

        jButton2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/iconPack16/printer.png"))); // NOI18N
        jButton2.setText("Print Only");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel8.add(jButton2);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(377, Short.MAX_VALUE))
        );

        jPanel17.add(jPanel3, java.awt.BorderLayout.CENTER);

        jPanel15.add(jPanel17, java.awt.BorderLayout.LINE_END);

        jPanel2.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 20, 20, 20));
        jPanel2.setPreferredSize(new java.awt.Dimension(131, 581));
        jPanel2.setLayout(new java.awt.BorderLayout());

        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(64, 66, 67)));
        jPanel4.setLayout(new java.awt.BorderLayout());

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Daily Income Report");
        jLabel3.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 1, 10, 1));
        jPanel4.add(jLabel3, java.awt.BorderLayout.PAGE_START);

        jPanel5.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 5, 5, 5));
        jPanel5.setLayout(new java.awt.BorderLayout());

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "#", "Invoice No.", "Customer Name", "Customer Type", "Item Count", "Total Portion (Rs)"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setMaxWidth(9009090);
        }

        jPanel5.add(jScrollPane1, java.awt.BorderLayout.PAGE_START);

        jPanel4.add(jPanel5, java.awt.BorderLayout.CENTER);

        jPanel2.add(jPanel4, java.awt.BorderLayout.CENTER);

        jPanel15.add(jPanel2, java.awt.BorderLayout.CENTER);

        add(jPanel15, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        this.adminDashBoard.setJPanelLoad(new AdminReports(this.adminDashBoard));
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        reportDataInitialize(AdminReports.VIEW_AND_SAVE_REPORT);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        reportDataInitialize(AdminReports.SAVE_ONLY_REPORT);
    }//GEN-LAST:event_jButton2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
