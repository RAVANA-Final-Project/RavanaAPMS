package gui.admin;

import gui.SplashScreen;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;
import java.util.logging.Level;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.MySQL;

/**
 *
 * @author karannagoda
 */
public class UpdateActivities extends javax.swing.JPanel {

    private AdminDashBoard adminDashBoard;
    private static HashMap<String, String> customerTypesMap = new HashMap<>();
    private static HashMap<String, Integer> offerTypeMap = new HashMap<>();

    public UpdateActivities(AdminDashBoard adminDashBoard) {
        initComponents();
        this.adminDashBoard = adminDashBoard;
        loadCustomerType();
        loadOfferType();
        loadActivityData();
    }

    private void resetAll() {
        jTextField5.setText("");
        jTextArea1.setText("");
        jComboBox1.setSelectedIndex(0);
        jComboBox2.setSelectedIndex(0);
        jFormattedTextField1.setText("0");
        jFormattedTextField2.setText("0");
    }

    private void loadCustomerType() {
        try {
            ResultSet resultset = MySQL.execute("SELECT * FROM `customer_type`");
            Vector<String> customerType = new Vector<>();
            customerType.add("Select");

            while (resultset.next()) {
                customerType.add(resultset.getString("customer_type_name"));
                customerTypesMap.put(resultset.getString("customer_type_name"), resultset.getString("customer_type_id"));
            }

            DefaultComboBoxModel model = new DefaultComboBoxModel(customerType);
            jComboBox1.setModel(model);
        } catch (Exception e) {
            e.printStackTrace();
            SplashScreen.logger.log(Level.WARNING, "Warning Message", e);
        }
    }

    private void loadOfferType() {
        try {
            ResultSet resultset = MySQL.execute("SELECT * FROM `offer_type`");
            Vector<String> offerType = new Vector<>();
            offerType.add("Select");

            while (resultset.next()) {
                offerType.add(resultset.getString("offer_type_name"));
                offerTypeMap.put(resultset.getString("offer_type_name"), resultset.getInt("offer_type_id"));
            }

            DefaultComboBoxModel model = new DefaultComboBoxModel(offerType);
            jComboBox2.setModel(model);
        } catch (Exception e) {
            e.printStackTrace();
            SplashScreen.logger.log(Level.WARNING, "Warning Message", e);
        }
    }

    private void loadActivityData() {
        try {
            ResultSet activityResultset = MySQL.execute("SELECT * FROM `activity` INNER JOIN `customer_type` ON "
                    + "`activity`.`customer_type_customer_type_id`=`customer_type`.`customer_type_id` ORDER BY `activity_id` ASC");

            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            model.setRowCount(0);

            while (activityResultset.next()) {

                double price = 0;
                double offer = 0;
                String offerType = null;

                try {
                    ResultSet activityPriceResultset = MySQL.execute("SELECT `amount` FROM `price_list` WHERE "
                            + "`activity_activity_id`='" + activityResultset.getInt("activity_id") + "' ORDER BY `update_date` DESC LIMIT 1");

                    if (activityPriceResultset.next()) {
                        price = activityPriceResultset.getDouble("amount");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    ResultSet activityOfferResultset = MySQL.execute("SELECT `offer_presentage`,`offer_type_name` FROM `activity_has_offer_type` INNER JOIN `offer_type` ON "
                            + "`activity_has_offer_type`.`offer_type_offer_type_id`=`offer_type`.`offer_type_id` WHERE "
                            + "`activity_activity_id`='" + activityResultset.getInt("activity_id") + "'");

                    if (activityOfferResultset.next()) {
                        offer = activityOfferResultset.getDouble("offer_presentage");
                        offerType = activityOfferResultset.getString("offer_type_name");
                    } else {
                        offerType = "---";
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

                Vector<String> vector = new Vector<>();
                vector.add(activityResultset.getString("activity_id"));
                vector.add(activityResultset.getString("activity_name"));
                vector.add(activityResultset.getString("description"));
                vector.add(activityResultset.getString("customer_type_name"));
                vector.add(new DecimalFormat("0.00").format(price));
                vector.add(new DecimalFormat("0.00").format(offer));
                vector.add(offerType);

                model.addRow(vector);
            }
        } catch (Exception e) {
            e.printStackTrace();
            SplashScreen.logger.log(Level.WARNING, "Warning Message", e);
        }
    }

    private void paymentInputValidate(JFormattedTextField targetformattedTextfield, int maximumInputValue, int validateType) {
        if (targetformattedTextfield.getText().matches("[\\\\+\\\\-]?[0-9]{0,8}([,|.][0-9]{0,3})?") && !targetformattedTextfield.getText().isEmpty()) {
            double payment = 0;

            if (targetformattedTextfield.getText().startsWith("0") && targetformattedTextfield.getText().length() > 1) {
                targetformattedTextfield.setText(targetformattedTextfield.getText().replace("0", ""));
                payment = Double.parseDouble(targetformattedTextfield.getText().replace("0", ""));
            } else {
                payment = Double.parseDouble(targetformattedTextfield.getText());
            }

            if (payment < 0) {
                targetformattedTextfield.setText("0");
            } else if (payment > maximumInputValue) {
                targetformattedTextfield.setText(String.valueOf(maximumInputValue));
                JOptionPane.showMessageDialog(this, "Maximum activity " + ((validateType == Activities.NORAML_AMOUNT) ? "amount" : (validateType == Activities.PRESENTAGE) ? "offer presentage" : "") + " is " + ((validateType == Activities.NORAML_AMOUNT) ? String.valueOf(maximumInputValue) + "/=" : (validateType == Activities.PRESENTAGE) ? String.valueOf(maximumInputValue) + "%" : ""), "Warning", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            targetformattedTextfield.setText("0");
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
        jButton5 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jTextField5 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel5 = new javax.swing.JLabel();
        jFormattedTextField1 = new javax.swing.JFormattedTextField();
        jLabel6 = new javax.swing.JLabel();
        jFormattedTextField2 = new javax.swing.JFormattedTextField();
        jLabel7 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setLayout(new java.awt.BorderLayout());

        jPanel1.setBackground(new java.awt.Color(204, 204, 255));
        jPanel1.setMinimumSize(new java.awt.Dimension(100, 35));
        jPanel1.setPreferredSize(new java.awt.Dimension(949, 36));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel7.setLayout(new java.awt.BorderLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/iconPack24/activities/globe_process.png"))); // NOI18N
        jLabel1.setText("Update Activities");
        jPanel7.add(jLabel1, java.awt.BorderLayout.LINE_START);
        jPanel7.add(jSeparator2, java.awt.BorderLayout.PAGE_END);

        jPanel6.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 5, 1));
        jPanel6.setLayout(new java.awt.GridLayout(1, 3, 10, 0));

        jButton5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/iconPack24/refresh.png"))); // NOI18N
        jButton5.setText("Reset All");
        jButton5.setToolTipText("Reset All");
        jButton5.setContentAreaFilled(false);
        jButton5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jPanel6.add(jButton5);

        jButton4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/iconPack24/back.png"))); // NOI18N
        jButton4.setText("Go Back");
        jButton4.setToolTipText("Go Back");
        jButton4.setContentAreaFilled(false);
        jButton4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel6.add(jButton4);

        jPanel7.add(jPanel6, java.awt.BorderLayout.LINE_END);

        jPanel1.add(jPanel7, java.awt.BorderLayout.CENTER);

        add(jPanel1, java.awt.BorderLayout.PAGE_START);

        jPanel3.setPreferredSize(new java.awt.Dimension(949, 400));
        jPanel3.setLayout(new java.awt.BorderLayout());

        jPanel4.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 5));
        jPanel4.setMinimumSize(new java.awt.Dimension(170, 100));
        jPanel4.setPreferredSize(new java.awt.Dimension(200, 379));
        jPanel4.setLayout(new java.awt.BorderLayout());

        jPanel11.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 5));

        jLabel2.setText("Activity Name : ");

        jButton1.setText("Update Activity");
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel3.setText("Customer Type : ");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel4.setText("Description : ");

        jTextArea1.setColumns(1);
        jTextArea1.setLineWrap(true);
        jTextArea1.setRows(5);
        jScrollPane2.setViewportView(jTextArea1);

        jLabel5.setText("Price (Rs) : ");

        jFormattedTextField1.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        jFormattedTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jFormattedTextField1KeyReleased(evt);
            }
        });

        jLabel6.setText("Offer (%) : ");

        jFormattedTextField2.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        jFormattedTextField2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jFormattedTextField2KeyReleased(evt);
            }
        });

        jLabel7.setText("Offer Type : ");

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextField5)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE)
                    .addComponent(jFormattedTextField1)
                    .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jFormattedTextField2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jComboBox2, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(0, 0, 0))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jFormattedTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jFormattedTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.add(jPanel11, java.awt.BorderLayout.CENTER);

        jPanel3.add(jPanel4, java.awt.BorderLayout.LINE_START);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "#", "Activity Name", "Description", "Customer Type", "Price", "Offer (%)", "Offer Type"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
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
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setMaxWidth(100);
            jTable1.getColumnModel().getColumn(2).setMinWidth(300);
        }

        jPanel3.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        add(jPanel3, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        int userAction = JOptionPane.showConfirmDialog(this, "Are you sure want to reset all?", "Reset All", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);

        if (userAction == JOptionPane.YES_OPTION) {
            jTable1.clearSelection();
            resetAll();
            loadActivityData();
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        this.adminDashBoard.setJPanelLoad(new Activities(this.adminDashBoard));
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            int row = jTable1.getSelectedRow();

            String Id = String.valueOf(jTable1.getValueAt(row, 0));

            String Name = String.valueOf(jTable1.getValueAt(row, 1));
            jTextField5.setText(Name);

            String Description = String.valueOf(jTable1.getValueAt(row, 2));
            jTextArea1.setText(Description);

            String type = String.valueOf(jTable1.getValueAt(row, 3));
            jComboBox1.setSelectedItem(type);

            jFormattedTextField1.setText(new DecimalFormat("0").format(Double.parseDouble(String.valueOf(jTable1.getValueAt(row, 4)))));

            boolean isPriceAvailabel = false;
            String offerType = "";
            String offer = "";
            
            if(String.valueOf(jTable1.getValueAt(row, 4)).equals("0.00")){
                isPriceAvailabel = true;
            }else{
                isPriceAvailabel = false;
            }

            if (String.valueOf(jTable1.getValueAt(row, 5)).equals("0.00")) {
                offerType = "Select";
                offer = "0.00";
            } else {
                offerType = String.valueOf(jTable1.getValueAt(row, 6));
                offer = String.valueOf(jTable1.getValueAt(row, 5));
            }

            jFormattedTextField1.setEditable(isPriceAvailabel);
            jFormattedTextField2.setText(new DecimalFormat("0").format(Double.parseDouble(offer)));
            jComboBox2.setSelectedItem(offerType);
        }
    }//GEN-LAST:event_jTable1MouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // Update Activity
        int row = jTable1.getSelectedRow();
        String Id = String.valueOf(jTable1.getValueAt(row, 0));
        String Name = jTextField5.getText();
        String Description = jTextArea1.getText();
        String type = String.valueOf(jComboBox1.getSelectedItem());
        String message = null;

        if (Name.isEmpty()) {
            message = "Type activity name";
        } else if (Name.length() > 100) {
            message = "Activity name is too long";
        } else if (Description.isEmpty()) {
            message = "Type description";
        } else if (type.equals("Select")) {
            message = "Select customer type";
        } else if (jFormattedTextField1.getText().equals("0")) {
            message = "Enter price";
        } else if (Double.parseDouble(jFormattedTextField1.getText()) < 0 || Double.parseDouble(jFormattedTextField1.getText()) > 50000) {
            message = "Invlied price";
        } else if (Double.parseDouble(jFormattedTextField2.getText()) < 0 || Double.parseDouble(jFormattedTextField2.getText()) > 100) {
            message = "Invlied offer";
        } else if (Double.parseDouble(jFormattedTextField2.getText()) > 0 && jComboBox2.getSelectedItem().equals("Select")) {
            message = "Select offer type";
        }

        if (message == null) {
            try {
                MySQL.execute("UPDATE `activity` SET `activity_name`='" + Name + "',`description`='" + Description + "',`customer_type_customer_type_id`='" + customerTypesMap.get(type) + "'"
                        + "WHERE `activity_id`='" + Id + "'");

                try {
                    ResultSet priceListResultset = MySQL.execute("SELECT * FROM `price_list` WHERE `activity_activity_id`='" + Id + "' AND `amount`='" + jFormattedTextField1.getText() + "'");

                    if (!priceListResultset.next()) {
                        MySQL.execute("INSERT INTO `price_list` (`activity_activity_id`,`amount`,`update_date`) VALUE ('" + Id + "','" + jFormattedTextField1.getText() + "','" + new SimpleDateFormat("yyy-MM-dd").format(new Date()) + "')");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (Double.parseDouble(jFormattedTextField2.getText()) >= 0 && !jComboBox2.getSelectedItem().equals("Select")) {
                    try {
                        MySQL.execute("INSERT INTO `activity_has_offer_type` (`activity_activity_id`,`offer_type_offer_type_id`,`offer_presentage`) VALUES ('" + Id + "','" + offerTypeMap.get(jComboBox2.getSelectedItem()) + "','" + jFormattedTextField2.getText() + "')");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                message = "Activity updated";
            } catch (Exception e) {
                e.printStackTrace();
                SplashScreen.logger.log(Level.WARNING, "Warning Message", e);
            }

        }

        if (message != null) {
            JOptionPane.showMessageDialog(this, message, message.equals("Activity updated") ? "Success" : "Warning", message.equals("Activity updated") ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.WARNING_MESSAGE);

            if (message.equals("Activity updated")) {
                resetAll();
                loadActivityData();
            }
        }

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jFormattedTextField1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jFormattedTextField1KeyReleased
        // TODO add your handling code here:
        paymentInputValidate(jFormattedTextField1, 50000, Activities.NORAML_AMOUNT);
    }//GEN-LAST:event_jFormattedTextField1KeyReleased

    private void jFormattedTextField2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jFormattedTextField2KeyReleased
        // TODO add your handling code here:
        paymentInputValidate(jFormattedTextField2, 80, Activities.PRESENTAGE);
    }//GEN-LAST:event_jFormattedTextField2KeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JFormattedTextField jFormattedTextField1;
    private javax.swing.JFormattedTextField jFormattedTextField2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField5;
    // End of variables declaration//GEN-END:variables
}
