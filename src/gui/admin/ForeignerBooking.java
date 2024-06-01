package gui.admin;

import gui.SplashScreen;
import java.awt.Frame;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;
import java.util.logging.Level;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.MySQL;
import model.OrderDetail;

/**
 *
 * @author dulanjaya
 */
public class ForeignerBooking extends javax.swing.JPanel {

    private AdminDashBoard adminDashBoard;
    
    private String customerMobile;
    private int activityId;
    private String price;
    private String offer;

    private HashMap<String, OrderDetail> orderDetailMap = new HashMap<>();

    public void setCustomerName(String customerName) {
        jTextField2.setText(customerName);
    }

    public void setActivity(String activity) {
        jTextField1.setText(activity);
    }

    public void setCustomerMobileAsVar(String customerMobile) {
        this.customerMobile = customerMobile;
    }

    public void setActivityIdAsVar(int activityId) {
        this.activityId = activityId;
    }

    public void setPriceAsVar(String price) {
        this.price = price;
    }

    public void setOfferAsVar(String offer) {
        this.offer = offer;
    }

    /**
     * Creates new form ForeignerBooking
     */
    public ForeignerBooking(AdminDashBoard adminDashBoard) {
        initComponents();
        jTextField1.setEditable(false);
        jTextField2.setEditable(false);
        this.adminDashBoard = adminDashBoard;
    }

    private void customerQuantityValidate(int validateType) {

        if (jFormattedTextField1.getText().matches("^-?\\d+$")) {
            int qty = Integer.parseInt(jFormattedTextField1.getText());

            if (qty < 1) {
                jFormattedTextField1.setText("1");
            } else if (qty > 20) {
                jFormattedTextField1.setText("20");
                JOptionPane.showMessageDialog(this, "Maximum quantity is 20", "Warning", JOptionPane.WARNING_MESSAGE);
            } else {
                if (validateType != Booking.NORMAL_VALIDATE) {
                    if (validateType == Booking.INCREMENT_VALIDATE) {
                        if ((qty + 1) <= 20) {
                            jFormattedTextField1.setText(String.valueOf(qty + 1));
                        } else {
                            JOptionPane.showMessageDialog(this, "Maximum quantity is 20", "Warning", JOptionPane.WARNING_MESSAGE);
                        }
                    } else if (validateType == Booking.DECREMENT_VALIDATE) {
                        if ((qty - 1) > 0) {
                            jFormattedTextField1.setText(String.valueOf(qty - 1));
                        } else {
                            JOptionPane.showMessageDialog(this, "Minimum quantity is 1", "Warning", JOptionPane.WARNING_MESSAGE);
                        }
                    }
                }
            }
        } else {
            jFormattedTextField1.setText("1");
        }
    }

    private void orderDetailTableConstruct() {
        DefaultTableModel tableModel = (DefaultTableModel) jTable1.getModel();
        tableModel.setRowCount(0);

        double total = 0;
        double discount = 0;
        int counter = 0;

        for (OrderDetail selectedOrderDetail : orderDetailMap.values()) {
            counter++;

            double activityVisePrice = (selectedOrderDetail.getPrice() * selectedOrderDetail.getQty()) * ((100 - selectedOrderDetail.getOffer()) / 100);

            total += selectedOrderDetail.getPrice() * selectedOrderDetail.getQty();
            discount += (selectedOrderDetail.getPrice() * selectedOrderDetail.getQty()) * (selectedOrderDetail.getOffer() / 100);

            Vector<String> vector = new Vector<>();
            vector.add(String.valueOf(counter));
            vector.add(selectedOrderDetail.getActivityName());
            vector.add(String.valueOf(selectedOrderDetail.getQty()));
            vector.add(new DecimalFormat("0.00").format(selectedOrderDetail.getPrice()));
            vector.add(new DecimalFormat("0.00").format(selectedOrderDetail.getOffer()));
            vector.add(new DecimalFormat("0.00").format(activityVisePrice));

            tableModel.addRow(vector);
        }

        jLabel12.setText(new DecimalFormat("0.00").format(total));
        jLabel11.setText(new DecimalFormat("0.00").format(discount));
        jLabel13.setText(new DecimalFormat("0.00").format(total - discount));
        
        System.gc();
    }

    private void resetAll() {
        this.customerMobile = null;
        jTextField2.setText("");
        jDateChooser1.setDate(null);

        this.activityId = 0;
        jTextField1.setText("");
        this.price = null;
        this.offer = null;

        jFormattedTextField1.setText("1");
        orderDetailMap.clear();
        orderDetailTableConstruct();
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
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jFormattedTextField1 = new javax.swing.JFormattedTextField();
        jButton10 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel5 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jSeparator3 = new javax.swing.JSeparator();
        jPanel13 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

        jPanel1.setBackground(new java.awt.Color(204, 204, 255));
        jPanel1.setMinimumSize(new java.awt.Dimension(100, 35));
        jPanel1.setPreferredSize(new java.awt.Dimension(949, 36));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel7.setLayout(new java.awt.BorderLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/iconPack24/calendar_empty_1.png"))); // NOI18N
        jLabel1.setText("Foreigner Booking");
        jPanel7.add(jLabel1, java.awt.BorderLayout.LINE_START);
        jPanel7.add(jSeparator2, java.awt.BorderLayout.PAGE_END);

        jPanel6.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 5, 1));
        jPanel6.setLayout(new java.awt.GridLayout(1, 3, 10, 0));

        jButton5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/iconPack24/refresh.png"))); // NOI18N
        jButton5.setText("Reset All");
        jButton5.setToolTipText("Reset All");
        jButton5.setContentAreaFilled(false);
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

        jLabel3.setText("Customer Name : ");

        jLabel5.setText("Activity Name :");

        jLabel7.setText("Arrival Date : ");

        jDateChooser1.setForeground(new java.awt.Color(255, 255, 255));
        jDateChooser1.setDateFormatString("yyy-MM-dd");
        jDateChooser1.setMaxSelectableDate(new java.util.Date(253370748689000L));
        jDateChooser1.setMinSelectableDate(new java.util.Date(-62128009711000L));

        jLabel8.setText("Activity :");

        jLabel9.setText("Qty :");

        jLabel16.setText("Customer :");

        jButton6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/iconPack16/add.png"))); // NOI18N
        jButton6.setText("Select a Customer");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/iconPack16/add.png"))); // NOI18N
        jButton7.setText("Select a Activity");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jButton8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton8.setText("-");
        jButton8.setMinimumSize(new java.awt.Dimension(30, 23));
        jButton8.setPreferredSize(new java.awt.Dimension(30, 23));
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jButton9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton9.setText("+");
        jButton9.setMinimumSize(new java.awt.Dimension(30, 23));
        jButton9.setPreferredSize(new java.awt.Dimension(30, 23));
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        jFormattedTextField1.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        jFormattedTextField1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jFormattedTextField1.setText("1");
        jFormattedTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jFormattedTextField1KeyReleased(evt);
            }
        });

        jButton10.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/iconPack16/add_to_database.png"))); // NOI18N
        jButton10.setText("Add to Order");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        jButton11.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/iconPack16/remove_from_database.png"))); // NOI18N
        jButton11.setText("Remove from Order");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTextField2)
            .addComponent(jButton6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jButton10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jButton11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jButton7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jTextField1)
            .addComponent(jDateChooser1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel16)
                    .addComponent(jLabel3)
                    .addComponent(jLabel9)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jFormattedTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel8)
                    .addComponent(jLabel5)
                    .addComponent(jLabel7))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton6)
                .addGap(8, 8, 8)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8)
                .addGap(8, 8, 8)
                .addComponent(jButton7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jFormattedTextField1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton11)
                .addContainerGap(37, Short.MAX_VALUE))
        );

        jPanel4.add(jPanel11, java.awt.BorderLayout.CENTER);

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jPanel4.add(jSeparator1, java.awt.BorderLayout.LINE_END);

        jPanel3.add(jPanel4, java.awt.BorderLayout.LINE_START);

        jPanel5.setLayout(new java.awt.BorderLayout());

        jPanel8.setPreferredSize(new java.awt.Dimension(749, 30));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setText("Order Details");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addContainerGap(654, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel6)
                .addContainerGap())
        );

        jPanel5.add(jPanel8, java.awt.BorderLayout.PAGE_START);

        jPanel10.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jPanel10.setLayout(new java.awt.BorderLayout());

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "#", "Activity Name", "Quantity", "Price (RS)", "Offer (%)", "Total (Rs)"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jPanel10.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel5.add(jPanel10, java.awt.BorderLayout.CENTER);

        jPanel3.add(jPanel5, java.awt.BorderLayout.CENTER);

        add(jPanel3, java.awt.BorderLayout.CENTER);

        jPanel2.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 1, 1, 1));
        jPanel2.setPreferredSize(new java.awt.Dimension(949, 130));
        jPanel2.setLayout(new java.awt.BorderLayout());

        jSeparator3.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 1, 1, 1));
        jPanel2.add(jSeparator3, java.awt.BorderLayout.PAGE_START);

        jPanel13.setLayout(new java.awt.BorderLayout());

        jPanel12.setLayout(new java.awt.BorderLayout());

        jPanel14.setLayout(new java.awt.BorderLayout());
        jPanel12.add(jPanel14, java.awt.BorderLayout.PAGE_END);

        jPanel13.add(jPanel12, java.awt.BorderLayout.CENTER);

        jPanel2.add(jPanel13, java.awt.BorderLayout.PAGE_END);

        jPanel9.setMinimumSize(new java.awt.Dimension(230, 92));
        jPanel9.setPreferredSize(new java.awt.Dimension(290, 237));
        jPanel9.setLayout(new java.awt.GridLayout(4, 2, 15, 0));

        jLabel2.setText("Total (RS) : ");
        jPanel9.add(jLabel2);

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel12.setText("0.00");
        jPanel9.add(jLabel12);

        jLabel4.setText("Discount (RS) : ");
        jPanel9.add(jLabel4);

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel11.setText("0.00");
        jPanel9.add(jLabel11);

        jLabel10.setText("Sub Total (RS) : ");
        jPanel9.add(jLabel10);

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel13.setText("0.00");
        jPanel9.add(jLabel13);

        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/iconPack16/accept_database.png"))); // NOI18N
        jButton1.setText("Confirm Order");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel9.add(jButton1);

        jButton3.setBackground(new java.awt.Color(255, 51, 0));
        jButton3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton3.setText("Cancle");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel9.add(jButton3);

        jPanel2.add(jPanel9, java.awt.BorderLayout.LINE_END);

        add(jPanel2, java.awt.BorderLayout.PAGE_END);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        resetAll();
        SelectForeignCustomer selectCustomer = new SelectForeignCustomer((Frame) this.adminDashBoard, true, this);
        selectCustomer.setVisible(true);
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
        SelectForeignerActivity selectActivity = new SelectForeignerActivity((Frame) this.adminDashBoard, true, this);
        selectActivity.setVisible(true);
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        // TODO add your handling code here:
        customerQuantityValidate(Booking.INCREMENT_VALIDATE);
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // TODO add your handling code here:
        customerQuantityValidate(Booking.DECREMENT_VALIDATE);
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jFormattedTextField1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jFormattedTextField1KeyReleased
        // TODO add your handling code here:
        customerQuantityValidate(Booking.NORMAL_VALIDATE);
    }//GEN-LAST:event_jFormattedTextField1KeyReleased

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        // TODO add your handling code here:
        Date selectedDate = jDateChooser1.getDate();
        String activityName = jTextField1.getText();
        String qty = jFormattedTextField1.getText();

        String message;

        if (this.customerMobile == null) {
            message = "Select a customer";
        } else if (selectedDate == null) {
            message = "Select arrival date";
        } else if (selectedDate.before(new Date())
                && !(new SimpleDateFormat("yyy").format(selectedDate).equals(new SimpleDateFormat("yyy").format(new Date()))
                && new SimpleDateFormat("MM").format(selectedDate).equals(new SimpleDateFormat("MM").format(new Date()))
                && new SimpleDateFormat("dd").format(selectedDate).equals(new SimpleDateFormat("dd").format(new Date())))) {
            message = "Invalied arrival date";
        } else if (this.activityId == 0) {
            message = "Select a activity";
        } else if (activityName.isEmpty()) {
            message = "Select a activity again";
        } else if (this.price == null) {
            message = "Select a activity again";
        } else if (this.offer == null) {
            message = "Select a activity again";
        } else if (jFormattedTextField1.getText().isEmpty()) {
            message = "Enter quantity";
        } else if (Integer.parseInt(qty) < 1 || Integer.parseInt(qty) > 20) {
            message = "Invalied quantity";
        } else {
            message = null;
        }

        if (message != null) {
            JOptionPane.showMessageDialog(this, message, "Warning", JOptionPane.WARNING_MESSAGE);
        } else {

            boolean isAlreadyHave = false;
            int userAction = 0;

            for (OrderDetail selectedOrder : orderDetailMap.values()) {
                if (selectedOrder.getActivityName().equals(activityName)) {
                    isAlreadyHave = true;
                    userAction = JOptionPane.showConfirmDialog(this, "This activity already added. Do you want to update quantity?", "Update Quantity", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);

                    if (userAction == JOptionPane.OK_OPTION) {
                        selectedOrder.setQty(selectedOrder.getQty() + Integer.parseInt(qty));
                    }
                    break;
                } else {
                    isAlreadyHave = false;
                }
            }

            String actionMessage = "";

            if (!isAlreadyHave) {
                actionMessage = "activity added";

                OrderDetail orderDetail = new OrderDetail();

                orderDetail.setActivityId(this.activityId);
                orderDetail.setActivityName(activityName);
                orderDetail.setPrice(Double.parseDouble(this.price));
                orderDetail.setQty(Integer.parseInt(qty));
                orderDetail.setOffer(Double.parseDouble(this.offer));

                orderDetailMap.put(activityName, orderDetail);
            } else {
                actionMessage = "activity updated";
            }

            System.gc();
            orderDetailTableConstruct();

            jTextField1.setText("");
            this.price = null;
            this.offer = null;
            jFormattedTextField1.setText("1");
            jButton7.requestFocus();

            if (userAction == JOptionPane.OK_OPTION) {
                JOptionPane.showMessageDialog(this, actionMessage, "Message", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        // TODO add your handling code here:
        int selectedRow = jTable1.getSelectedRow();

        if (selectedRow != -1) {
            int userAction = JOptionPane.showConfirmDialog(this, "Are you sure want to remove activity?", "Remove ctivity", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);

            if (userAction == JOptionPane.YES_OPTION) {
                orderDetailMap.remove(jTable1.getValueAt(selectedRow, 1));
                orderDetailTableConstruct();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecte a row", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        int userAction = JOptionPane.showConfirmDialog(this, "Are you sure want to reset all?", "Reset All", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);

        if (userAction == JOptionPane.YES_OPTION) {
            resetAll();
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        String message = null;

        if (this.customerMobile == null
                || jTextField2.getText().isEmpty()
                || this.jDateChooser1.getDate() == null) {
            message = "Enter order details properly";
        } else if (this.orderDetailMap.size() < 1) {
            message = "Add activities";
        } else {
            try {
                ResultSet orderResultset = MySQL.execute("SELECT * FROM `order` WHERE `customer_customer_mobile`='" + this.customerMobile + "' AND `arrival_date`='" + new SimpleDateFormat("yyy-MM-dd").format(jDateChooser1.getDate()) + "'");

                if (orderResultset.next()) {
                    message = "This customer's order already assign this date";
                } else {
                    try {
                        MySQL.execute("INSERT INTO `order` (`customer_customer_mobile`,`arrival_date`) VALUES ('" + this.customerMobile + "','" + new SimpleDateFormat("yyy-MM-dd").format(jDateChooser1.getDate()) + "')");

                        try {
                            ResultSet newOrderResultset = MySQL.execute("SELECT `order_id` FROM `order` WHERE `customer_customer_mobile`='" + this.customerMobile + "' AND `arrival_date`='" + new SimpleDateFormat("yyy-MM-dd").format(jDateChooser1.getDate()) + "'");

                            if (newOrderResultset.next()) {
                                for (OrderDetail selectedOrderDetail : this.orderDetailMap.values()) {
                                    try {
                                        MySQL.execute("INSERT INTO `order_item` (`order_order_id`,`activity_activity_id`,`qty`) VALUES ('" + newOrderResultset.getInt("order_id") + "','" + selectedOrderDetail.getActivityId() + "','" + selectedOrderDetail.getQty() + "')");
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        SplashScreen.logger.log(Level.WARNING, "Warning Message", e);
                                    }
                                }

                                message = "Order confirmed";
                                
                                resetAll();
                            } else {
                                message = "Something went wrong";
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            SplashScreen.logger.log(Level.WARNING, "Warning Message", e);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        SplashScreen.logger.log(Level.WARNING, "Warning Message", e);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                SplashScreen.logger.log(Level.WARNING, "Warning Message", e);
            }
        }

        if (message != null) {
            JOptionPane.showMessageDialog(this, message, message.equals("Order confirmed") ? "Success" : "Warning", message.equals("Order confirmed") ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        this.adminDashBoard.setJPanelLoad(new Booking(this.adminDashBoard));
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        this.adminDashBoard.setJPanelLoad(new Booking(this.adminDashBoard));
    }//GEN-LAST:event_jButton3ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private javax.swing.JFormattedTextField jFormattedTextField1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    // End of variables declaration//GEN-END:variables
}
