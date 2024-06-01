package gui.admin;

import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import model.MySQL;

/**
 *
 * @author dulanjaya
 */
public class SelectNeutralActivity extends javax.swing.JDialog {

    JPanel requestFrame;
    private String customerType;

    private HashMap<String, String[]> activityDescriptionMap = new HashMap<>();

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
        loadActivity();
    }

    /**
     * Creates new form SelectCustomers
     *
     * @param parent
     */
    public SelectNeutralActivity(java.awt.Frame parent, boolean modal, JPanel requestFrame) {
        super(parent, modal);
        initComponents();
        this.requestFrame = requestFrame;
    }

    private void loadActivity() {
        if (this.customerType != null) {
            try {
                DefaultTableModel tableModel = (DefaultTableModel) jTable1.getModel();
                tableModel.setRowCount(0);

                ResultSet resultset = MySQL.execute("SELECT * FROM `activity` INNER JOIN `customer_type` ON "
                        + "`activity`.`customer_type_customer_type_id`=`customer_type`.`customer_type_id` WHERE `customer_type_name`='" + this.customerType + "' OR `customer_type_name`='Neutral'");

                int tableCounter = 0;

                while (resultset.next()) {
                    tableCounter++;

                    double price = 0;
                    double offer = 0;

                    try {
                        ResultSet activityPriceResultset = MySQL.execute("SELECT `amount` FROM `price_list` WHERE "
                                + "`activity_activity_id`='" + resultset.getInt("activity_id") + "' ORDER BY `update_date` DESC LIMIT 1");

                        if (activityPriceResultset.next()) {
                            price = activityPriceResultset.getDouble("amount");
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    try {
                        ResultSet activityOfferResultset = MySQL.execute("SELECT `offer_presentage`,`offer_type_name` FROM `activity_has_offer_type` INNER JOIN `offer_type` ON "
                                + "`activity_has_offer_type`.`offer_type_offer_type_id`=`offer_type`.`offer_type_id` WHERE "
                                + "`activity_activity_id`='" + resultset.getInt("activity_id") + "'");

                        if (activityOfferResultset.next()) {
                            offer = activityOfferResultset.getDouble("offer_presentage");
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    String[] activityDataArray = new String[4];

                    Vector vector = new Vector();
                    vector.add(String.valueOf(tableCounter));
                    vector.add(resultset.getString("activity_name"));
                    vector.add(new DecimalFormat("0.00").format(price));
                    vector.add(new DecimalFormat("0.00").format(offer));

                    activityDataArray[0] = resultset.getString("activity_id");
                    activityDataArray[1] = resultset.getString("description");
                    activityDataArray[2] = String.valueOf(price);
                    activityDataArray[3] = String.valueOf(offer);

                    activityDescriptionMap.put(resultset.getString("activity_name"), activityDataArray);

                    tableModel.addRow(vector);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void activityDataInitialize(String activityName, int activityId, String price, String offer) {
        if (this.requestFrame != null) {
            if (this.requestFrame.getClass().getSimpleName().equals("ManageBooking")) {
                
                ManageBooking filteredObject = (ManageBooking) this.requestFrame;
                filteredObject.setActivity(activityName);
                filteredObject.setActivityIdAsVar(activityId);
                filteredObject.setPriceAsVar(price);
                filteredObject.setOfferAsVar(offer);
                filteredObject.setQty(1);
            } else if (this.requestFrame.getClass().getSimpleName().equals("CustomerInvoice")) {
                
                CustomerInvoice filteredObject = (CustomerInvoice) this.requestFrame;
                filteredObject.setActivity(activityName);
                filteredObject.setActivityIdAsVar(activityId);
                filteredObject.setPriceAsVar(price);
                filteredObject.setOfferAsVar(offer);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Something went wrong", "Warning", JOptionPane.WARNING_MESSAGE);
        }
        this.dispose();
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
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jPanel5 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        jPanel1.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel2.setLayout(new java.awt.BorderLayout());

        jPanel3.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 5, 1));
        jPanel3.setLayout(new java.awt.BorderLayout());

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/iconPack16/info.png"))); // NOI18N
        jLabel1.setText("You can double click the row or select row and click the select button");
        jLabel1.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 1, 5, 1));
        jPanel3.add(jLabel1, java.awt.BorderLayout.CENTER);
        jPanel3.add(jSeparator1, java.awt.BorderLayout.PAGE_END);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/iconPack24/bowling.png"))); // NOI18N
        jLabel2.setText("Select a Activity");
        jPanel3.add(jLabel2, java.awt.BorderLayout.PAGE_START);

        jPanel2.add(jPanel3, java.awt.BorderLayout.PAGE_START);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "#", "Activity Name", "Price (RS)", "Offer (%)"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jPanel2.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel4.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 1, 1, 1));
        jPanel4.setPreferredSize(new java.awt.Dimension(666, 159));
        jPanel4.setLayout(new java.awt.BorderLayout());

        jLabel3.setText("Acticity Description");
        jLabel3.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 5, 1));
        jPanel4.add(jLabel3, java.awt.BorderLayout.PAGE_START);

        jTextArea1.setColumns(1);
        jTextArea1.setLineWrap(true);
        jTextArea1.setRows(5);
        jScrollPane2.setViewportView(jTextArea1);

        jPanel4.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        jPanel5.setPreferredSize(new java.awt.Dimension(676, 28));

        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton1.setText("Select");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addGap(0, 686, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addGap(0, 5, Short.MAX_VALUE)
                .addComponent(jButton1))
        );

        jPanel4.add(jPanel5, java.awt.BorderLayout.PAGE_END);

        jPanel2.add(jPanel4, java.awt.BorderLayout.PAGE_END);

        jPanel1.add(jPanel2, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() > 0 && evt.getClickCount() < 3) {
            int selectedRow = jTable1.getSelectedRow();
            if (selectedRow != -1) {
                String activityName = String.valueOf(jTable1.getValueAt(selectedRow, 1));
                int activityId = Integer.parseInt(activityDescriptionMap.get(activityName)[0]);
                String activityDescription = activityDescriptionMap.get(activityName)[1];
                String price = activityDescriptionMap.get(activityName)[2];
                String offer = activityDescriptionMap.get(activityName)[3];

                jTextArea1.setText(activityDescription);

                if (evt.getClickCount() == 2) {
                    activityDataInitialize(activityName, activityId, price, offer);
                }
            }
        }
    }//GEN-LAST:event_jTable1MouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        int selectedRow = jTable1.getSelectedRow();

        if (selectedRow != -1) {
            String activityName = String.valueOf(jTable1.getValueAt(selectedRow, 1));
            int activityId = Integer.parseInt(activityDescriptionMap.get(activityName)[0]);
            String price = activityDescriptionMap.get(activityName)[2];
            String offer = activityDescriptionMap.get(activityName)[3];

            activityDataInitialize(activityName, activityId, price, offer);
        } else {
            JOptionPane.showMessageDialog(this, "Select a row", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
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
            java.util.logging.Logger.getLogger(SelectNeutralActivity.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SelectNeutralActivity.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SelectNeutralActivity.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SelectNeutralActivity.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
//                SelectForeignCustomer dialog = new SelectForeignCustomer(new javax.swing.JFrame(), true);
//                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
//                    @Override
//                    public void windowClosing(java.awt.event.WindowEvent e) {
//                        System.exit(0);
//                    }
//                });
//                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextArea jTextArea1;
    // End of variables declaration//GEN-END:variables
}
