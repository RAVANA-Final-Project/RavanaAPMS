package gui.admin;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Vector;
import javax.swing.ButtonModel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import model.MySQL;

/**
 *
 * @author dulanjaya
 */
public class SelectNeutralCustomer extends javax.swing.JDialog {

    JPanel requestFrame;
    
    HashMap<String, String> customerTypeMap = new HashMap<>();

    /**
     * Creates new form SelectCustomers
     */
    public SelectNeutralCustomer(java.awt.Frame parent, boolean modal, JPanel requestFrame) {
        super(parent, modal);
        initComponents();
        this.requestFrame = requestFrame;
        loadCustomer();
    }

    private void loadCustomer() {

        if (!jTextField1.getText().matches("^[ A-Za-z]+$")) {
            textFieldcomponentRearrange(jTextField1);
        } else if (jTextField1.getText().length() > 20) {
            textFieldcomponentRearrange(jTextField1);
        }

        if (!jTextField2.getText().matches("[0-9]+")) {
            textFieldcomponentRearrange(jTextField2);
        } else if (jTextField2.getText().length() > 10) {
            textFieldcomponentRearrange(jTextField2);
        }

        String query = "SELECT * FROM `customer` INNER JOIN `customer_type` ON `customer`.`customer_type_customer_type_id`=`customer_type`.`customer_type_id`";

        if (!jTextField1.getText().isEmpty() && jTextField2.getText().isEmpty() && buttonGroup1.getSelection() == null) {
            query += " WHERE `first_name` LIKE '" + jTextField1.getText() + "%' OR `last_name` LIKE '" + jTextField1.getText() + "%'";
        } else if (jTextField1.getText().isEmpty() && !jTextField2.getText().isEmpty() && buttonGroup1.getSelection() == null) {
            query += " WHERE `customer_mobile` LIKE '" + jTextField2.getText() + "%'";
        } else if (!jTextField1.getText().isEmpty() && !jTextField2.getText().isEmpty() && buttonGroup1.getSelection() == null) {
            query += " WHERE (`first_name` LIKE '" + jTextField1.getText() + "%' OR `last_name` LIKE '" + jTextField1.getText() + "%') AND `customer_mobile` LIKE '" + jTextField2.getText() + "%'";
        } else if (jTextField1.getText().isEmpty() && jTextField2.getText().isEmpty() && buttonGroup1.getSelection() != null) {
            query += " WHERE `customer_type_name`=";

            if (jCheckBox1.isSelected()) {
                query += "'Foreign'";
            } else if (jCheckBox2.isSelected()) {
                query += "'Local'";
            }
        } else if (!jTextField1.getText().isEmpty() && jTextField2.getText().isEmpty() && buttonGroup1.getSelection() != null) {
            query += " WHERE (`first_name` LIKE '" + jTextField1.getText() + "%' OR `last_name` LIKE '" + jTextField1.getText() + "%') AND `customer_type_name`=";

            if (jCheckBox1.isSelected()) {
                query += "'Foreign'";
            } else if (jCheckBox2.isSelected()) {
                query += "'Local'";
            }
        } else if (jTextField1.getText().isEmpty() && !jTextField2.getText().isEmpty() && buttonGroup1.getSelection() != null) {
            query += " WHERE `customer_mobile` LIKE '" + jTextField2.getText() + "%' AND `customer_type_name`=";

            if (jCheckBox1.isSelected()) {
                query += "'Foreign'";
            } else if (jCheckBox2.isSelected()) {
                query += "'Local'";
            }
        } else if (!jTextField1.getText().isEmpty() && !jTextField2.getText().isEmpty() && buttonGroup1.getSelection() != null) {
            query += " WHERE (`first_name` LIKE '" + jTextField1.getText() + "%' OR `last_name` LIKE '" + jTextField1.getText() + "%') AND `customer_mobile` LIKE '" + jTextField2.getText() + "%' AND `customer_type_name`=";

            if (jCheckBox1.isSelected()) {
                query += "'Foreign'";
            } else if (jCheckBox2.isSelected()) {
                query += "'Local'";
            }
        }

        try {
            DefaultTableModel tableModel = (DefaultTableModel) jTable1.getModel();
            tableModel.setRowCount(0);

            ResultSet resultset = MySQL.execute(query);

            int tableCounter = 0;

            while (resultset.next()) {
                tableCounter++;

                Vector<String> vector = new Vector<>();
                vector.add(String.valueOf(tableCounter));
                vector.add(resultset.getString("customer_mobile"));
                vector.add(resultset.getString("first_name") + " " + resultset.getString("last_name"));

                customerTypeMap.put(resultset.getString("customer_mobile"), resultset.getString("customer_type_name"));
                
                tableModel.addRow(vector);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void textFieldcomponentRearrange(JTextField targetComponent) {
        ArrayList<String> characterArray = new ArrayList<>(Arrays.asList(targetComponent.getText().split("")));
        characterArray.remove(characterArray.size() - 1);
        targetComponent.setText(String.join("", characterArray));
    }

    private void customerDataInitialize(int selectedRow) {
        String customerMobile = String.valueOf(jTable1.getValueAt(selectedRow, 1));
        String customerName = String.valueOf(jTable1.getValueAt(selectedRow, 2));

        if (this.requestFrame != null) {
            if (this.requestFrame.getClass().getSimpleName().equals("CustomerInvoice")) {
                CustomerInvoice filteredObject = (CustomerInvoice) this.requestFrame;
                filteredObject.setCustomerName(customerName);
                filteredObject.setCustomerMobileAsVar(customerMobile);
                filteredObject.setCustomerTypeAsVar(this.customerTypeMap.get(customerMobile));
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel2 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jCheckBox1 = new javax.swing.JCheckBox();
        jCheckBox2 = new javax.swing.JCheckBox();
        jPanel8 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jPanel9 = new javax.swing.JPanel();
        jSeparator2 = new javax.swing.JSeparator();
        jPanel10 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

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
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/iconPack24/male_female_users.png"))); // NOI18N
        jLabel2.setText("Select a Customer");
        jPanel3.add(jLabel2, java.awt.BorderLayout.PAGE_START);

        jPanel2.add(jPanel3, java.awt.BorderLayout.PAGE_START);

        jPanel4.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 1, 1, 1));

        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton1.setText("Select");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(538, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jButton1))
        );

        jPanel2.add(jPanel4, java.awt.BorderLayout.PAGE_END);

        jPanel5.setLayout(new java.awt.BorderLayout());

        jPanel6.setPreferredSize(new java.awt.Dimension(612, 31));
        jPanel6.setLayout(new java.awt.BorderLayout());

        jPanel7.setPreferredSize(new java.awt.Dimension(150, 200));
        jPanel7.setLayout(new java.awt.GridLayout(1, 2));

        buttonGroup1.add(jCheckBox1);
        jCheckBox1.setText("Foreign");
        jCheckBox1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jCheckBox1ItemStateChanged(evt);
            }
        });
        jPanel7.add(jCheckBox1);

        buttonGroup1.add(jCheckBox2);
        jCheckBox2.setText("Local");
        jCheckBox2.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jCheckBox2ItemStateChanged(evt);
            }
        });
        jPanel7.add(jCheckBox2);

        jPanel6.add(jPanel7, java.awt.BorderLayout.LINE_END);

        jPanel8.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 15, 1));

        jLabel3.setText("or Mobile");

        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField1KeyReleased(evt);
            }
        });

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/iconPack16/search.png"))); // NOI18N
        jLabel4.setText("Search By Name");

        jTextField2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField2KeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel6.add(jPanel8, java.awt.BorderLayout.CENTER);

        jPanel5.add(jPanel6, java.awt.BorderLayout.PAGE_START);

        jPanel9.setLayout(new java.awt.BorderLayout());
        jPanel9.add(jSeparator2, java.awt.BorderLayout.PAGE_START);

        jPanel10.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 1, 1, 1));
        jPanel10.setLayout(new java.awt.BorderLayout());

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "#", "Customer Mobile", "Customer Name"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
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
        jScrollPane2.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setMaxWidth(200);
        }

        jPanel10.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        jPanel9.add(jPanel10, java.awt.BorderLayout.CENTER);

        jPanel5.add(jPanel9, java.awt.BorderLayout.CENTER);

        jPanel2.add(jPanel5, java.awt.BorderLayout.CENTER);

        jPanel1.add(jPanel2, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        int selectedRow = jTable1.getSelectedRow();
        if (selectedRow != -1) {
            customerDataInitialize(selectedRow);
        } else {
            JOptionPane.showMessageDialog(this, "Select a row", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTextField1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyReleased
        // TODO add your handling code here:
        loadCustomer();
    }//GEN-LAST:event_jTextField1KeyReleased

    private void jTextField2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField2KeyReleased
        // TODO add your handling code here:
        loadCustomer();
    }//GEN-LAST:event_jTextField2KeyReleased

    private void jCheckBox1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jCheckBox1ItemStateChanged
        // TODO add your handling code here:
        if (jCheckBox1.isSelected()) {
            loadCustomer();
        }
    }//GEN-LAST:event_jCheckBox1ItemStateChanged

    private void jCheckBox2ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jCheckBox2ItemStateChanged
        // TODO add your handling code here:
        if (jCheckBox2.isSelected()) {
            loadCustomer();
        }
    }//GEN-LAST:event_jCheckBox2ItemStateChanged

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            int selectedRow = jTable1.getSelectedRow();
            if (selectedRow != -1) {
                customerDataInitialize(selectedRow);
            }
        }
    }//GEN-LAST:event_jTable1MouseClicked

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
            java.util.logging.Logger.getLogger(SelectNeutralCustomer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SelectNeutralCustomer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SelectNeutralCustomer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SelectNeutralCustomer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
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
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton1;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    // End of variables declaration//GEN-END:variables
}
