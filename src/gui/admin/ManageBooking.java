package gui.admin;

import gui.SplashScreen;
import java.awt.Frame;
import java.io.InputStream;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;
import java.util.logging.Level;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import model.MySQL;
import model.OrderDetail;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRTableModelDataSource;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author dulanjaya
 */
public class ManageBooking extends javax.swing.JPanel {

    private AdminDashBoard adminDashBoard;

    private String customerMobile;

    private int selectedOrderId = -1;

    private int activityId;
    private String price;
    private String offer;

    private HashMap<String, Integer> orderIdMap = new HashMap<>();
    private HashMap<String, OrderDetail> orderDetailMap = new HashMap<>();
    private HashMap<String, Integer> orderDetailIdMap = new HashMap<>();

    public void setActivity(String activity) {
        jTextField1.setText(activity);
    }

    public void setQty(int qty) {
        jFormattedTextField1.setText(String.valueOf(qty));
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
    public ManageBooking(AdminDashBoard adminDashBoard) {
        initComponents();
        loadOrders();
        jDateChooser1.setEnabled(false);
        jTextField1.setEditable(false);
        this.adminDashBoard = adminDashBoard;
    }

    private void loadOrders() {
        try {
            DefaultTableModel tableModel = (DefaultTableModel) jTable1.getModel();
            tableModel.setRowCount(0);

            ResultSet orderResultset = MySQL.execute("SELECT * FROM `order` INNER JOIN `customer` ON "
                    + "`order`.`customer_customer_mobile`=`customer`.`customer_mobile` INNER JOIN `customer_type` ON "
                    + "`customer`.`customer_type_customer_type_id`=`customer_type`.`customer_type_id` ORDER BY `arrival_date` DESC");

            int counter = 0;

            while (orderResultset.next()) {
                try {
                    ResultSet orderSettledResultset = MySQL.execute("SELECT `order_settlement_record_id` FROM `order_settlement_record` WHERE `order_order_id`='" + orderResultset.getInt("order_id") + "'");

                    if (!orderSettledResultset.next()) {
                        counter++;

                        Vector<String> vector = new Vector<>();
                        vector.add(String.valueOf(counter));
                        vector.add(orderResultset.getString("first_name") + " " + orderResultset.getString("last_name"));
                        vector.add(orderResultset.getString("customer_mobile"));
                        vector.add(orderResultset.getString("customer_type_name"));
                        vector.add(orderResultset.getString("arrival_date"));

                        orderIdMap.put(orderResultset.getString("customer_mobile") + " " + orderResultset.getString("arrival_date"), orderResultset.getInt("order_id"));

                        tableModel.addRow(vector);
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
        DefaultTableModel tableModel = (DefaultTableModel) jTable2.getModel();
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

        jLabel10.setText(new DecimalFormat("0.00").format(total));
        jLabel12.setText(new DecimalFormat("0.00").format(discount));
        jLabel16.setText(new DecimalFormat("0.00").format(total - discount));
        jFormattedTextField3.setEnabled(true);

        balanceCalculate();

        System.gc();
    }

    private void resetAll() {
        this.customerMobile = null;
        jDateChooser1.setDate(null);

        this.selectedOrderId = -1;
        this.activityId = 0;
        jTextField1.setText("");
        this.price = null;
        this.offer = null;

        jFormattedTextField1.setText("1");
        jFormattedTextField3.setText("0");
        orderDetailMap.clear();
        orderDetailIdMap.clear();
        orderIdMap.clear();

        System.gc();

        loadOrders();
        orderDetailTableConstruct();
    }

    private void balanceCalculate() {

        if (jFormattedTextField3.getText().matches("[\\\\+\\\\-]?[0-9]{0,8}([,|.][0-9]{0,3})?") && !jFormattedTextField3.getText().isEmpty()) {
            double subTotal = Double.parseDouble(jLabel16.getText());
            double payment = 0;

            if (jFormattedTextField3.getText().startsWith("0") && jFormattedTextField3.getText().length() > 1) {
                jFormattedTextField3.setText(jFormattedTextField3.getText().replace("0", ""));
                payment = Double.parseDouble(jFormattedTextField3.getText().replace("0", ""));
            } else {
                payment = Double.parseDouble(jFormattedTextField3.getText());
            }

            if (payment < 0) {
                jFormattedTextField3.setText("0");
            } else if (payment > 50000) {
                jFormattedTextField3.setText("50000");
                JOptionPane.showMessageDialog(this, "Maximum payment amount is Rs. 50,000/=", "Warning", JOptionPane.WARNING_MESSAGE);
            } else {

                if ((payment - subTotal) >= 0) {
                    jLabel19.setForeground(jLabel16.getForeground());
                } else {
                    jLabel19.setForeground(jButton14.getBackground());
                }

                jLabel19.setText(new DecimalFormat("0.00").format(payment - subTotal));
            }
        } else {
            jFormattedTextField3.setText("0");
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

        jSeparator6 = new javax.swing.JSeparator();
        jPanel1 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jPanel6 = new javax.swing.JPanel();
        jButton5 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jPanel15 = new javax.swing.JPanel();
        jPanel19 = new javax.swing.JPanel();
        jPanel20 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jLabel8 = new javax.swing.JLabel();
        jButton7 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jButton8 = new javax.swing.JButton();
        jFormattedTextField1 = new javax.swing.JFormattedTextField();
        jButton9 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel6 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jFormattedTextField3 = new javax.swing.JFormattedTextField();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jButton13 = new javax.swing.JButton();
        jButton14 = new javax.swing.JButton();
        jSeparator4 = new javax.swing.JSeparator();
        jPanel16 = new javax.swing.JPanel();
        jPanel17 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel18 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();

        setLayout(new java.awt.BorderLayout());

        jPanel1.setBackground(new java.awt.Color(204, 204, 255));
        jPanel1.setMinimumSize(new java.awt.Dimension(100, 35));
        jPanel1.setPreferredSize(new java.awt.Dimension(949, 36));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel7.setLayout(new java.awt.BorderLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/iconPack24/calendar_empty_1.png"))); // NOI18N
        jLabel1.setText("Manage Booking");
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

        jPanel15.setPreferredSize(new java.awt.Dimension(200, 530));
        jPanel15.setLayout(new java.awt.BorderLayout());

        jPanel19.setLayout(new java.awt.BorderLayout());

        jPanel20.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 5));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setText("Edit Order");

        jLabel7.setText("Arrival Date : ");

        jDateChooser1.setForeground(new java.awt.Color(255, 255, 255));
        jDateChooser1.setDateFormatString("yyy-MM-dd");
        jDateChooser1.setMaxSelectableDate(new java.util.Date(253370748689000L));
        jDateChooser1.setMinSelectableDate(new java.util.Date(-62128009711000L));

        jLabel8.setText("Activity :");

        jButton7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/iconPack16/add.png"))); // NOI18N
        jButton7.setText("Select a Activity");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jLabel5.setText("Activity Name :");

        jLabel9.setText("Qty :");

        jButton8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton8.setText("-");
        jButton8.setMinimumSize(new java.awt.Dimension(30, 23));
        jButton8.setPreferredSize(new java.awt.Dimension(30, 23));
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
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

        jButton9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton9.setText("+");
        jButton9.setMinimumSize(new java.awt.Dimension(30, 23));
        jButton9.setPreferredSize(new java.awt.Dimension(30, 23));
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        jButton10.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/iconPack16/add_to_database.png"))); // NOI18N
        jButton10.setText("Add");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        jButton12.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/iconPack16/remove_from_database.png"))); // NOI18N
        jButton12.setText("Delete");
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setText("Order Confirmation");
        jLabel6.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 1, 1, 1));

        jPanel4.setPreferredSize(new java.awt.Dimension(97, 135));
        jPanel4.setLayout(new java.awt.GridLayout(5, 2));

        jLabel11.setText("Total (RS) : ");
        jPanel4.add(jLabel11);

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel10.setText("0.00");
        jPanel4.add(jLabel10);

        jLabel14.setText("Discount (RS) : ");
        jPanel4.add(jLabel14);

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel12.setText("0.00");
        jPanel4.add(jLabel12);

        jLabel13.setText("Sub Total (RS) : ");
        jPanel4.add(jLabel13);

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel16.setText("0.00");
        jPanel4.add(jLabel16);

        jLabel17.setText("Payment (RS) :");
        jPanel4.add(jLabel17);

        jPanel5.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 1, 1, 1));
        jPanel5.setLayout(new java.awt.BorderLayout());

        jFormattedTextField3.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        jFormattedTextField3.setText("0");
        jFormattedTextField3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jFormattedTextField3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jFormattedTextField3KeyReleased(evt);
            }
        });
        jPanel5.add(jFormattedTextField3, java.awt.BorderLayout.CENTER);

        jPanel4.add(jPanel5);

        jLabel18.setText("Balance (RS) :");
        jPanel4.add(jLabel18);

        jLabel19.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel19.setText("0.00");
        jLabel19.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 11));
        jPanel4.add(jLabel19);

        jButton13.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/iconPack16/shopping_cart_accept.png"))); // NOI18N
        jButton13.setText("Confirm Order");
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });

        jButton14.setBackground(new java.awt.Color(255, 51, 0));
        jButton14.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/iconPack16/remove_from_shopping_cart.png"))); // NOI18N
        jButton14.setText("Remove Order");
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jButton7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jTextField1)
            .addComponent(jDateChooser1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jSeparator3)
            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jButton13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jButton14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addComponent(jLabel8)
                    .addComponent(jLabel5)
                    .addComponent(jLabel7)
                    .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(jPanel20Layout.createSequentialGroup()
                            .addComponent(jButton10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel20Layout.createSequentialGroup()
                            .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jFormattedTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addComponent(jLabel4)
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
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jFormattedTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton10)
                    .addComponent(jButton12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 4, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton14)
                .addContainerGap())
        );

        jPanel19.add(jPanel20, java.awt.BorderLayout.CENTER);

        jPanel15.add(jPanel19, java.awt.BorderLayout.CENTER);

        jSeparator4.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jPanel15.add(jSeparator4, java.awt.BorderLayout.LINE_END);

        add(jPanel15, java.awt.BorderLayout.LINE_START);

        jPanel16.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 5, 1, 1));
        jPanel16.setLayout(new java.awt.GridLayout(2, 1));

        jPanel17.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jPanel17.setLayout(new java.awt.BorderLayout());

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "#", "Customer Name", "Customer Mobile", "Customer Type", "Arrival Date"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
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

        jPanel17.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        jPanel2.setLayout(new java.awt.BorderLayout());

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setText("Pending Orders");
        jLabel2.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 5, 1));
        jPanel2.add(jLabel2, java.awt.BorderLayout.CENTER);

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/iconPack16/info.png"))); // NOI18N
        jLabel3.setText("You can view order details by clicking pending order");
        jLabel3.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 5, 1));
        jPanel2.add(jLabel3, java.awt.BorderLayout.PAGE_END);

        jPanel17.add(jPanel2, java.awt.BorderLayout.PAGE_START);

        jPanel3.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 1, 1, 1));
        jPanel3.setLayout(new java.awt.BorderLayout());
        jPanel3.add(jSeparator1, java.awt.BorderLayout.CENTER);

        jPanel17.add(jPanel3, java.awt.BorderLayout.PAGE_END);

        jPanel16.add(jPanel17);

        jPanel18.setLayout(new java.awt.BorderLayout());

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel15.setText("Order Details");
        jLabel15.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 5, 1));
        jPanel18.add(jLabel15, java.awt.BorderLayout.PAGE_START);

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "#", "Activicty Name", "Quantity", "Price (RS)", "Offer (%)", "Total (RS)"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable2MouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(jTable2);

        jPanel18.add(jScrollPane3, java.awt.BorderLayout.CENTER);

        jPanel16.add(jPanel18);

        add(jPanel16, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        int userAction = JOptionPane.showConfirmDialog(this, "Are you sure want to reset all?", "Reset All", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);

        if (userAction == JOptionPane.YES_OPTION) {
            resetAll();
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        this.adminDashBoard.setJPanelLoad(new Booking(this.adminDashBoard));
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:

        if (selectedOrderId != -1) {
            SelectNeutralActivity selectNeutralActivity = new SelectNeutralActivity((Frame) this.adminDashBoard, true, (JPanel) this);
            selectNeutralActivity.setCustomerType(String.valueOf(jTable1.getValueAt(selectedOrderId, 3)));
            selectNeutralActivity.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Select a row", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // TODO add your handling code here:
        customerQuantityValidate(Booking.DECREMENT_VALIDATE);
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jFormattedTextField1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jFormattedTextField1KeyReleased
        // TODO add your handling code here:
        customerQuantityValidate(Booking.NORMAL_VALIDATE);
    }//GEN-LAST:event_jFormattedTextField1KeyReleased

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        // TODO add your handling code here:
        customerQuantityValidate(Booking.INCREMENT_VALIDATE);
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        // TODO add your handling code here:

        if (!jFormattedTextField3.getText().isEmpty()
                && jFormattedTextField3.getText() != null
                && !jLabel19.getText().isEmpty()
                && jLabel19.getText() != null
                && !jLabel16.getText().isEmpty()
                && jLabel16.getText() != null
                && this.customerMobile != null
                && this.selectedOrderId != -1) {

            double payedAmount = Double.parseDouble(jFormattedTextField3.getText());
            double subTotal = Double.parseDouble(jLabel16.getText());
            double balance = Double.parseDouble(jLabel19.getText());

            if (balance >= 0 && subTotal > 0) {
                long invoiceId = System.currentTimeMillis();

                try {
                    ResultSet orderResultset = MySQL.execute("SELECT * FROM `invoice` INNER JOIN `order_settlement_record` ON "
                            + "`invoice`.`invoice_id`=`order_settlement_record`.`invoice_invoice_id` INNER JOIN `order` ON "
                            + "`order_settlement_record`.`order_order_id`=`order`.`order_id` WHERE `invoice_id`='" + invoiceId + "' OR (`order_id`='" + orderIdMap.get(this.customerMobile + " " + new SimpleDateFormat("yyy-MM-dd").format(jDateChooser1.getDate())) + "' AND `arrival_date`='" + new SimpleDateFormat("yyy-MM-dd").format(jDateChooser1.getDate()) + "')");

                    if (orderResultset.next()) {
                        JOptionPane.showMessageDialog(this, "Invoice already confirmed", "Warning", JOptionPane.WARNING_MESSAGE);
                    } else {
                        MySQL.execute("INSERT INTO `invoice` VALUES ('" + invoiceId + "','" + this.customerMobile + "','" + payedAmount + "','" + new SimpleDateFormat("yyy-MM-dd").format(new Date()) + "')");

                        for (OrderDetail selectedOrderDetail : orderDetailMap.values()) {
                            try {
                                MySQL.execute("INSERT INTO `invoice_item` (`invoice_invoice_id`,`activity_activity_id`,`qty`) VALUES "
                                        + "('" + invoiceId + "','" + selectedOrderDetail.getActivityId() + "','" + selectedOrderDetail.getQty() + "')");
                            } catch (Exception e) {
                                e.printStackTrace();
                                SplashScreen.logger.log(Level.WARNING, "Warning Message", e);
                            }
                        }

                        try {
                            MySQL.execute("INSERT INTO `order_settlement_record` (`order_order_id`,`invoice_invoice_id`) VALUES ('" + orderIdMap.get(this.customerMobile + " " + new SimpleDateFormat("yyy-MM-dd").format(jDateChooser1.getDate())) + "','" + invoiceId + "')");
                        } catch (Exception e) {
                            e.printStackTrace();
                            SplashScreen.logger.log(Level.WARNING, "Warning Message", e);
                        }

                        JOptionPane.showMessageDialog(this, "Order confirmed", "Success", JOptionPane.INFORMATION_MESSAGE);

                        InputStream reportPath = getClass().getResourceAsStream("/reports/customer_invoice.jasper");

                        HashMap<String, Object> parameters = new HashMap<>();
                        parameters.put("Parameter1", new SimpleDateFormat("yyy-MM-dd").format(new Date()));
                        parameters.put("Parameter2", "RAP-" + String.valueOf(jTable1.getValueAt(selectedOrderId, 3)) + "-" + String.valueOf(invoiceId));
                        parameters.put("Parameter3", String.valueOf(jTable1.getValueAt(this.selectedOrderId, 1)));
                        parameters.put("Parameter4", String.valueOf(this.customerMobile));
                        parameters.put("Parameter5", String.valueOf(jTable1.getValueAt(selectedOrderId, 3)) + " Customer");
                        parameters.put("Parameter6", new SimpleDateFormat("yyy-MM-dd").format(jDateChooser1.getDate()));
                        parameters.put("Parameter7", new DecimalFormat("0.00").format(subTotal));
                        parameters.put("Parameter8", new DecimalFormat("0.00").format(payedAmount));
                        parameters.put("Parameter9", new DecimalFormat("0.00").format(subTotal));
                        parameters.put("Parameter10", new DecimalFormat("0.00").format(payedAmount));
                        parameters.put("Parameter11", new DecimalFormat("0.00").format(balance));

                        JRTableModelDataSource tableModelDataSource = new JRTableModelDataSource(jTable2.getModel());

                        try {
                            JasperPrint jasperPrint = JasperFillManager.fillReport(reportPath, parameters, tableModelDataSource);
                            JasperViewer.viewReport(jasperPrint, false);
                            resetAll();

                        } catch (JRException jre) {
                            jre.printStackTrace();
                            SplashScreen.logger.log(Level.WARNING, "Warning Message", jre);
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    SplashScreen.logger.log(Level.WARNING, "Warning Message", e);
                }
            }
        }
    }//GEN-LAST:event_jButton13ActionPerformed

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
        // TODO add your handling code here:
        
        if (this.selectedOrderId != -1
                && jDateChooser1.getDate() != null
                && this.customerMobile != null) {

            int userDecision = JOptionPane.showConfirmDialog(this, "Are you sure want to revome this order?", "Question", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);

            if (userDecision == JOptionPane.YES_OPTION) {

                int targetOrderId = orderIdMap.get(String.valueOf(jTable1.getValueAt(this.selectedOrderId, 2)) + " " + String.valueOf(jTable1.getValueAt(this.selectedOrderId, 4)));

                for (int selectedOrderItemId : orderDetailIdMap.values()) {
                    try {
                        MySQL.execute("DELETE FROM `order_item` WHERE `order_item_id`='" + selectedOrderItemId + "' AND `order_order_id`='" + targetOrderId + "'");
                    } catch (Exception e) {
                        e.printStackTrace();
                        SplashScreen.logger.log(Level.WARNING, "Warning Message", e);
                    }
                }

                try {
                    ResultSet orderItemCountResultset = MySQL.execute("SELECT `order_item_id` FROM `order_item` WHERE `order_order_id`='" + targetOrderId + "'");

                    if (!orderItemCountResultset.next()) {

                        try {
                            MySQL.execute("DELETE FROM `order` WHERE `order_id`='" + targetOrderId + "'");
                        } catch (Exception e) {
                            e.printStackTrace();
                            SplashScreen.logger.log(Level.WARNING, "Warning Message", e);
                        }

                        resetAll();
                        JOptionPane.showMessageDialog(this, "Order removed", "Success", JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    SplashScreen.logger.log(Level.WARNING, "Warning Message", e);
                }
            }
        }else{
            JOptionPane.showMessageDialog(this, "Select a row", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_jButton14ActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        // TODO add your handling code here:
        int selectedRow = jTable2.getSelectedRow();

        if (this.selectedOrderId != -1 && selectedRow != -1 && this.activityId != 0 && !jTextField1.getText().isEmpty() && !jFormattedTextField1.getText().isEmpty()) {
            int userDecision = JOptionPane.showConfirmDialog(this, "Do you want to remove this activity?", "Question", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);

            if (userDecision == JOptionPane.YES_OPTION) {

                boolean isOrderItemFound = false;

                for (OrderDetail selectedOrderItem : orderDetailMap.values()) {
                    if (selectedOrderItem.getActivityName().equals(String.valueOf(jTable2.getValueAt(selectedRow, 1)))) {
                        isOrderItemFound = true;
                        break;
                    } else {
                        isOrderItemFound = false;
                    }
                }

                if (isOrderItemFound) {

                    try {
                        MySQL.execute("DELETE FROM `order_item` WHERE `order_item_id`='" + orderDetailIdMap.get(String.valueOf(jTable2.getValueAt(selectedRow, 1))) + "'");
                        orderDetailMap.remove(String.valueOf(jTable2.getValueAt(selectedRow, 1)));
                        orderDetailIdMap.remove(String.valueOf(jTable2.getValueAt(selectedRow, 1)));

                        activityId = 0;
                        jTextField1.setText("");
                        jFormattedTextField1.setText("1");

                        orderDetailTableConstruct();
                        JOptionPane.showMessageDialog(this, "Activity removed", "Success", JOptionPane.INFORMATION_MESSAGE);
                    } catch (Exception e) {
                        e.printStackTrace();
                        SplashScreen.logger.log(Level.WARNING, "Warning Message", e);
                    }
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Select a row", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_jButton12ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        // TODO add your handling code here:
        String message = null;

        if (this.selectedOrderId != -1 && this.activityId != 0 && !jTextField1.getText().isEmpty() && !jFormattedTextField1.getText().isEmpty()) {

            int selectedOrderDetailRow = jTable2.getSelectedRow();
            boolean isActivityFound = false;

            for (OrderDetail selectedOrderDetail : orderDetailMap.values()) {
                if (selectedOrderDetail.getActivityName().equals(jTextField1.getText())) {
                    isActivityFound = true;
                    break;
                } else {
                    isActivityFound = false;
                }
            }

            String userToken = String.valueOf(jTable1.getValueAt(this.selectedOrderId, 2) + " " + String.valueOf(jTable1.getValueAt(this.selectedOrderId, 4)));
            String query = null;

            if (isActivityFound) {
                int userDecision = JOptionPane.showConfirmDialog(this, "Activity already available. Do you want to update this activity?", "Question", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);

                if (userDecision == JOptionPane.YES_OPTION) {
                    query = "UPDATE `order_item` SET `qty`='" + (Integer.parseInt(String.valueOf(jTable2.getValueAt(selectedOrderDetailRow, 2))) + Integer.parseInt(jFormattedTextField1.getText())) + "' WHERE `order_item_id`='" + orderDetailIdMap.get(String.valueOf(jTable2.getValueAt(selectedOrderDetailRow, 1))) + "'";
                }
            } else {
                query = "INSERT INTO `order_item` (`order_order_id`,`activity_activity_id`,`qty`) VALUES ('" + orderIdMap.get(userToken) + "','" + this.activityId + "','" + Integer.parseInt(jFormattedTextField1.getText()) + "')";
            }

            if (query != null) {
                try {
                    MySQL.execute(query);

                    if (query.startsWith("UPDATE")) {
                        message = "Activity updated";

                        orderDetailMap.get(jTextField1.getText()).setQty(Integer.parseInt(String.valueOf(jTable2.getValueAt(selectedOrderDetailRow, 2))) + Integer.parseInt(jFormattedTextField1.getText()));
                    } else {
                        message = "Activity added";

                        OrderDetail orderDetail = new OrderDetail();
                        orderDetail.setActivityId(activityId);
                        orderDetail.setActivityName(jTextField1.getText());
                        orderDetail.setPrice(Double.parseDouble(this.price));
                        orderDetail.setOffer(Double.parseDouble(this.offer));
                        orderDetail.setQty(Integer.parseInt(jFormattedTextField1.getText()));

                        orderDetailMap.put(jTextField1.getText(), orderDetail);
                        orderDetailIdMap.put(jTextField1.getText(), this.activityId);
                    }

                    System.gc();
                } catch (Exception e) {
                    e.printStackTrace();
                    SplashScreen.logger.log(Level.WARNING, "Warning Message", e);
                }
            }
        } else {
            message = "Select a row";
        }

        this.activityId = 0;
        this.price = null;
        this.offer = null;
        jTextField1.setText("");
        jFormattedTextField1.setText("1");
        orderDetailTableConstruct();

        if (message != null) {
            JOptionPane.showMessageDialog(this, message, message == "Activity updated" || message == "Activity added" ? "Success" : "Warning", message == "Activity updated" || message == "Activity added" ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 1) {

            this.selectedOrderId = jTable1.getSelectedRow();

            if (selectedOrderId != -1) {

                this.customerMobile = String.valueOf(jTable1.getValueAt(selectedOrderId, 2));

                try {
                    jDateChooser1.setDate(new SimpleDateFormat("yyy-MM-dd").parse(String.valueOf(jTable1.getValueAt(selectedOrderId, 4))));
                } catch (ParseException pe) {
                    pe.printStackTrace();
                    SplashScreen.logger.log(Level.WARNING, "Warning Message", pe);
                }

                activityId = 0;
                jTextField1.setText("");
                jFormattedTextField1.setText("1");

                try {
                    String orderTocken = String.valueOf(jTable1.getValueAt(selectedOrderId, 2) + " " + String.valueOf(jTable1.getValueAt(selectedOrderId, 4)));

                    ResultSet orderResultset = MySQL.execute("SELECT * FROM `order_item` INNER JOIN `activity` ON "
                            + "`order_item`.`activity_activity_id`=`activity`.`activity_id` WHERE `order_order_id`='" + orderIdMap.get(orderTocken) + "'");

                    orderDetailIdMap.clear();
                    orderDetailMap.clear();
                    int counter = 0;

                    while (orderResultset.next()) {
                        counter++;

                        double price = 0;
                        double offer = 0;

                        try {
                            ResultSet priceResultset = MySQL.execute("SELECT * FROM `price_list` WHERE `activity_activity_id`='" + orderResultset.getString("activity_activity_id") + "' ORDER BY `update_date` DESC LIMIT 1");

                            if (priceResultset.next()) {
                                price = priceResultset.getDouble("amount");

                                try {
                                    ResultSet offerResultset = MySQL.execute("SELECT * FROM `activity_has_offer_type` WHERE `activity_activity_id`='" + orderResultset.getInt("activity_activity_id") + "'");

                                    if (offerResultset.next()) {
                                        offer = offerResultset.getDouble("offer_presentage");
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

                        OrderDetail orderDetail = new OrderDetail();
                        orderDetail.setActivityId(orderResultset.getInt("activity_activity_id"));
                        orderDetail.setActivityName(orderResultset.getString("activity_name"));
                        orderDetail.setPrice(price);
                        orderDetail.setQty(orderResultset.getInt("qty"));
                        orderDetail.setOffer(offer);

                        orderDetailIdMap.put(orderResultset.getString("activity_name"), orderResultset.getInt("order_item_id"));
                        orderDetailMap.put(orderResultset.getString("activity_name"), orderDetail);
                    }

                    orderDetailTableConstruct();
                } catch (Exception e) {
                    e.printStackTrace();
                    SplashScreen.logger.log(Level.WARNING, "Warning Message", e);
                }
            }
        }
    }//GEN-LAST:event_jTable1MouseClicked

    private void jFormattedTextField3KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jFormattedTextField3KeyReleased
        // TODO add your handling code here:
        balanceCalculate();
    }//GEN-LAST:event_jFormattedTextField3KeyReleased

    private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 1) {
            int selectedRow = jTable2.getSelectedRow();
            if (selectedRow != -1) {
                this.activityId = orderDetailIdMap.get(String.valueOf(jTable2.getValueAt(selectedRow, 1)));
                jTextField1.setText(String.valueOf(jTable2.getValueAt(selectedRow, 1)));
                jFormattedTextField1.setText(String.valueOf(jTable2.getValueAt(selectedRow, 2)));
            }
        }
    }//GEN-LAST:event_jTable2MouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private javax.swing.JFormattedTextField jFormattedTextField1;
    private javax.swing.JFormattedTextField jFormattedTextField3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
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
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
