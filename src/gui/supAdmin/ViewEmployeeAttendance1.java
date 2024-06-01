package gui.supAdmin;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;
import java.util.concurrent.TimeUnit;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import model.MySQL;

/**
 *
 * @author kguna
 */
public class ViewEmployeeAttendance1 extends javax.swing.JPanel {

    private SuperAdminDashBoard SuperAdminDashBoard;
    private EmployeeAttendanceChartFlow employeeAttendanceChartFlow;

    /**
     * Creates new form ForeignerBooking
     */
    public ViewEmployeeAttendance1(SuperAdminDashBoard SuperAdminDashBoard) {
        initComponents();
        jYearChooser1.setYear(Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date())));
        jMonthChooser1.setMonth(Integer.parseInt(new SimpleDateFormat("MM").format(new Date())) - 1);
        addTotalEmployeeDotChart();
        this.SuperAdminDashBoard = SuperAdminDashBoard;
    }

    public void addTotalEmployeeDotChart() {
        employeeAttendanceChartFlow = new EmployeeAttendanceChartFlow(this);
        jPanel14.add(employeeAttendanceChartFlow, BorderLayout.CENTER);
        SwingUtilities.updateComponentTreeUI(jPanel14);
    }

    public void resetAll() {
        jTextField1.setText("");
        jTextField2.setText("");
        jTextField3.setText("");
        jTextField4.setText("");
        jLabel2.setText("");
        jLabel10.setText("");
        jLabel12.setText("");
        jLabel13.setText("");
        jYearChooser1.setYear(Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date())));
        jMonthChooser1.setMonth(Integer.parseInt(new SimpleDateFormat("MM").format(new Date())) - 1);
        DefaultTableModel defaultTableModel = (DefaultTableModel) jTable1.getModel();
        defaultTableModel.setRowCount(0);
    }

    private void loadViewEmployeeAttendance() {
        String employeeNic = null;
        if (jTextField2.getText().length() > 12) {
            textFieldcomponentRearrange(jTextField2);
        }
        if ((!jTextField2.getText().matches("\\d+")) && jTextField2.getText().length() != 10) {
            textFieldcomponentRearrange(jTextField2);
        }
        if (jTextField2.getText().length() >= 10 && jTextField2.getText().length() < 20) {
            if ((jTextField2.getText().length() == 10 && jTextField2.getText().endsWith("v") || (jTextField2.getText().length() == 10 && jTextField2.getText().endsWith("V")))
                    || (jTextField2.getText().length() == 12 && jTextField2.getText().matches("[0-9]+"))) {
                employeeNic = jTextField2.getText();
                // ------------->>>>>
                try {
                    ResultSet loadEmployeeDetailsResultset = MySQL.execute("SELECT * FROM `employee` INNER JOIN `position` ON "
                            + "`employee`.`position_position_id`=`position`.`position_id` "
                            + "WHERE `employee_nic`='" + employeeNic + "'");

                    if (loadEmployeeDetailsResultset.next()) {
                        String employeeFirstName = loadEmployeeDetailsResultset.getString("first_name");
                        String employeeLastName = loadEmployeeDetailsResultset.getString("last_name");
                        String employeeMobile = loadEmployeeDetailsResultset.getString("employee_mobile");
                        String employeePosition = loadEmployeeDetailsResultset.getString("position_name");
                        String joinedDate = loadEmployeeDetailsResultset.getString("join_date");
                        String email = loadEmployeeDetailsResultset.getString("email");

                        jTextField1.setText("Mr. " + employeeFirstName + " " + employeeLastName);
                        jLabel2.setText(employeeFirstName + " " + employeeLastName);
                        jTextField3.setText(employeeMobile);
                        jTextField4.setText(employeePosition);
                        jLabel10.setText("Is Currently " + employeePosition + " at the Adventure Park");
                        jLabel12.setText("And Joined with Us in " + joinedDate);
                        jLabel13.setText("Contact : " + email + "- " + employeeMobile);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }

        String query = "SELECT * FROM `employee_attendance` INNER JOIN `attendance_date` ON "
                + "`employee_attendance`.`attendance_date_attendance_date_id`=`attendance_date`.`attendance_date_id` INNER JOIN `employee_attendance_status` ON "
                + "`employee_attendance`.`employee_attendance_status_employee_attendance_status_id`=`employee_attendance_status`.`employee_attendance_status_id`";
        if (employeeNic != null) {
            query += " WHERE `employee_employee_nic` LIKE '" + jTextField2.getText() + "%'";
        }

        YearMonth yearMonthObject = YearMonth.of(jYearChooser1.getYear(), (jMonthChooser1.getMonth() + 1));
        String daysInMonth = String.valueOf(yearMonthObject.lengthOfMonth());

        String checkingStartDate = String.valueOf(jYearChooser1.getYear()) + "-" + (jMonthChooser1.getMonth() + 1) + "-01";
        String checkingEndDate = String.valueOf(jYearChooser1.getYear()) + "-" + (jMonthChooser1.getMonth() + 1) + "-" + daysInMonth;

        if (query.contains("WHERE")) {
            query += " AND";
        } else {
            query += " WHERE";
        }

        query += " `attendance_date`>='" + checkingStartDate + "' AND `attendance_date`<='" + checkingEndDate + "'";

        try {
            DefaultTableModel tableModel = (DefaultTableModel) jTable1.getModel();
            tableModel.setRowCount(0);

            ResultSet resultset = MySQL.execute(query);

            int tableCounter = 0;

            while (resultset.next()) {
                tableCounter++;

                String inTime = resultset.getString("in_time");
                String outTime = resultset.getString("out_time");

                Vector<String> vector = new Vector<>();
                vector.add(resultset.getString("attendance_date"));
                vector.add(resultset.getString("employee_attendance_status_name"));
                vector.add(new SimpleDateFormat("k:mm:ss a").format(new SimpleDateFormat("HH:mm:ss").parse(inTime)));
                vector.add(new SimpleDateFormat("k:mm:ss a").format(new SimpleDateFormat("HH:mm:ss").parse(outTime)));

                SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");

                Date IncomingTime = formatter.parse(inTime);
                Date LeavingTime = formatter.parse(outTime);
                long difference = LeavingTime.getTime() - IncomingTime.getTime();
                long differenceInHours = TimeUnit.MILLISECONDS.toHours(difference);

                vector.add(String.valueOf(differenceInHours));
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

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel30 = new javax.swing.JPanel();
        jPanel23 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jPanel6 = new javax.swing.JPanel();
        jButton5 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jSeparator3 = new javax.swing.JSeparator();
        jPanel12 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jSeparator4 = new javax.swing.JSeparator();
        jPanel4 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jPanel15 = new javax.swing.JPanel();
        jPanel28 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanel31 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jPanel38 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jPanel39 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jPanel20 = new javax.swing.JPanel();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel19 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jPanel21 = new javax.swing.JPanel();
        jPanel25 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jPanel26 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jPanel27 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jPanel18 = new javax.swing.JPanel();
        jPanel22 = new javax.swing.JPanel();
        jPanel34 = new javax.swing.JPanel();
        jPanel32 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jPanel33 = new javax.swing.JPanel();
        jPanel24 = new javax.swing.JPanel();
        jPanel35 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jYearChooser1 = new com.toedter.calendar.JYearChooser();
        jPanel36 = new javax.swing.JPanel();
        jMonthChooser1 = new com.toedter.calendar.JMonthChooser();
        jPanel37 = new javax.swing.JPanel();
        jPanel29 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jSeparator5 = new javax.swing.JSeparator();
        jPanel16 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jPanel17 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        javax.swing.GroupLayout jPanel30Layout = new javax.swing.GroupLayout(jPanel30);
        jPanel30.setLayout(jPanel30Layout);
        jPanel30Layout.setHorizontalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel30Layout.setVerticalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setLayout(new java.awt.BorderLayout());

        jPanel1.setBackground(new java.awt.Color(204, 204, 255));
        jPanel1.setMinimumSize(new java.awt.Dimension(100, 35));
        jPanel1.setPreferredSize(new java.awt.Dimension(949, 36));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel7.setLayout(new java.awt.BorderLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/iconPack24/calendar_empty_1.png"))); // NOI18N
        jLabel1.setText("View Employee Attendance");
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

        jPanel2.setPreferredSize(new java.awt.Dimension(300, 530));
        jPanel2.setLayout(new java.awt.BorderLayout());

        jSeparator3.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jPanel2.add(jSeparator3, java.awt.BorderLayout.LINE_START);

        jPanel12.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 5, 1, 1));
        jPanel12.setLayout(new java.awt.GridLayout(2, 1));

        jPanel11.setLayout(new java.awt.BorderLayout());

        jPanel14.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        jPanel14.setLayout(new java.awt.BorderLayout());
        jPanel11.add(jPanel14, java.awt.BorderLayout.CENTER);

        jPanel12.add(jPanel11);

        jPanel13.setLayout(new java.awt.BorderLayout());
        jPanel13.add(jSeparator4, java.awt.BorderLayout.PAGE_START);

        jPanel4.setLayout(new java.awt.BorderLayout());

        jLabel3.setFont(new java.awt.Font("segoe ui", 1, 14)); // NOI18N
        jLabel3.setText("Employee Summary");
        jLabel3.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 1, 5, 1));
        jPanel4.add(jLabel3, java.awt.BorderLayout.PAGE_START);

        jPanel15.setLayout(new java.awt.GridLayout(4, 1));

        jPanel28.setLayout(new java.awt.BorderLayout());

        jLabel2.setFont(new java.awt.Font("segoe ui", 0, 14)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel28.add(jLabel2, java.awt.BorderLayout.CENTER);

        jPanel15.add(jPanel28);

        jPanel31.setLayout(new java.awt.BorderLayout());

        jLabel10.setFont(new java.awt.Font("segoe ui", 0, 14)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel31.add(jLabel10, java.awt.BorderLayout.CENTER);

        jPanel15.add(jPanel31);

        jPanel38.setLayout(new java.awt.BorderLayout());

        jLabel12.setFont(new java.awt.Font("segoe ui", 0, 14)); // NOI18N
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel38.add(jLabel12, java.awt.BorderLayout.CENTER);

        jPanel15.add(jPanel38);

        jPanel39.setLayout(new java.awt.BorderLayout());

        jLabel13.setFont(new java.awt.Font("segoe ui", 0, 14)); // NOI18N
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel39.add(jLabel13, java.awt.BorderLayout.CENTER);

        jPanel15.add(jPanel39);

        jPanel4.add(jPanel15, java.awt.BorderLayout.CENTER);

        jPanel13.add(jPanel4, java.awt.BorderLayout.CENTER);

        jPanel12.add(jPanel13);

        jPanel2.add(jPanel12, java.awt.BorderLayout.CENTER);

        jPanel3.add(jPanel2, java.awt.BorderLayout.LINE_END);

        jPanel5.setLayout(new java.awt.BorderLayout());

        jPanel8.setLayout(new java.awt.BorderLayout());

        jPanel20.setPreferredSize(new java.awt.Dimension(649, 50));
        jPanel20.setLayout(new java.awt.BorderLayout());
        jPanel20.add(jSeparator1, java.awt.BorderLayout.PAGE_START);

        jPanel19.setPreferredSize(new java.awt.Dimension(649, 60));
        jPanel19.setLayout(new java.awt.BorderLayout());

        jLabel6.setFont(new java.awt.Font("segoe ui", 1, 14)); // NOI18N
        jLabel6.setText("Employee Details");
        jLabel6.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 5, 5, 1));
        jLabel6.setPreferredSize(new java.awt.Dimension(121, 20));
        jPanel19.add(jLabel6, java.awt.BorderLayout.PAGE_START);

        jPanel21.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 5));
        jPanel21.setPreferredSize(new java.awt.Dimension(468, 100));
        jPanel21.setLayout(new java.awt.GridLayout(1, 3));

        jPanel25.setLayout(new java.awt.BorderLayout());

        jLabel7.setText("Name : ");
        jLabel7.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 4, 1, 4));
        jPanel25.add(jLabel7, java.awt.BorderLayout.LINE_START);

        jTextField1.setEditable(false);
        jTextField1.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jPanel25.add(jTextField1, java.awt.BorderLayout.CENTER);

        jPanel21.add(jPanel25);

        jPanel26.setLayout(new java.awt.BorderLayout());

        jLabel11.setText("Mobile : ");
        jLabel11.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 4, 1, 4));
        jPanel26.add(jLabel11, java.awt.BorderLayout.LINE_START);

        jTextField3.setEditable(false);
        jTextField3.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jPanel26.add(jTextField3, java.awt.BorderLayout.CENTER);

        jPanel21.add(jPanel26);

        jPanel27.setLayout(new java.awt.BorderLayout());

        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel9.setText("Position : ");
        jLabel9.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 4, 1, 4));
        jPanel27.add(jLabel9, java.awt.BorderLayout.LINE_START);

        jTextField4.setEditable(false);
        jTextField4.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jPanel27.add(jTextField4, java.awt.BorderLayout.CENTER);

        jPanel21.add(jPanel27);

        jPanel19.add(jPanel21, java.awt.BorderLayout.CENTER);

        jPanel20.add(jPanel19, java.awt.BorderLayout.CENTER);

        jPanel8.add(jPanel20, java.awt.BorderLayout.PAGE_END);

        jPanel18.setLayout(new java.awt.BorderLayout());

        jPanel22.setPreferredSize(new java.awt.Dimension(46, 64));
        jPanel22.setLayout(new java.awt.GridLayout(2, 1));

        jPanel34.setBorder(javax.swing.BorderFactory.createEmptyBorder(2, 2, 2, 2));
        jPanel34.setLayout(new java.awt.GridLayout(1, 2));

        jPanel32.setBorder(javax.swing.BorderFactory.createEmptyBorder(2, 2, 2, 2));
        jPanel32.setLayout(new java.awt.BorderLayout());

        jLabel5.setFont(new java.awt.Font("segoe ui", 1, 12)); // NOI18N
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/iconPack16/search.png"))); // NOI18N
        jLabel5.setText("Search by NIC :  ");
        jLabel5.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 4, 1, 1));
        jPanel32.add(jLabel5, java.awt.BorderLayout.LINE_START);

        jTextField2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField2FocusLost(evt);
            }
        });
        jTextField2.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                jTextField2InputMethodTextChanged(evt);
            }
        });
        jTextField2.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jTextField2PropertyChange(evt);
            }
        });
        jTextField2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField2KeyReleased(evt);
            }
        });
        jPanel32.add(jTextField2, java.awt.BorderLayout.CENTER);

        jPanel34.add(jPanel32);

        javax.swing.GroupLayout jPanel33Layout = new javax.swing.GroupLayout(jPanel33);
        jPanel33.setLayout(jPanel33Layout);
        jPanel33Layout.setHorizontalGroup(
            jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 306, Short.MAX_VALUE)
        );
        jPanel33Layout.setVerticalGroup(
            jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 28, Short.MAX_VALUE)
        );

        jPanel34.add(jPanel33);

        jPanel22.add(jPanel34);

        jPanel24.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 5, 1, 5));
        jPanel24.setLayout(new java.awt.GridLayout(1, 3));

        jPanel35.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        jPanel35.setLayout(new java.awt.BorderLayout());

        jLabel8.setText("Or");
        jLabel8.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 10, 1, 1));
        jLabel8.setPreferredSize(new java.awt.Dimension(30, 16));
        jPanel35.add(jLabel8, java.awt.BorderLayout.LINE_START);
        jPanel35.add(jYearChooser1, java.awt.BorderLayout.CENTER);

        jPanel24.add(jPanel35);

        jPanel36.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 20, 5, 5));
        jPanel36.setLayout(new java.awt.BorderLayout());
        jPanel36.add(jMonthChooser1, java.awt.BorderLayout.CENTER);

        jPanel24.add(jPanel36);

        jPanel37.setLayout(new java.awt.GridLayout(1, 0));

        jPanel29.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 15, 1, 15));
        jPanel29.setLayout(new java.awt.BorderLayout());

        jButton1.setText("Search by Date");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel29.add(jButton1, java.awt.BorderLayout.CENTER);

        jPanel37.add(jPanel29);

        jPanel24.add(jPanel37);

        jPanel22.add(jPanel24);

        jPanel18.add(jPanel22, java.awt.BorderLayout.CENTER);

        jPanel8.add(jPanel18, java.awt.BorderLayout.CENTER);

        jPanel5.add(jPanel8, java.awt.BorderLayout.PAGE_START);

        jPanel9.setPreferredSize(new java.awt.Dimension(649, 450));
        jPanel9.setLayout(new java.awt.BorderLayout());

        jPanel10.setPreferredSize(new java.awt.Dimension(649, 450));
        jPanel10.setLayout(new java.awt.BorderLayout());
        jPanel10.add(jSeparator5, java.awt.BorderLayout.PAGE_START);

        jPanel16.setPreferredSize(new java.awt.Dimension(649, 450));
        jPanel16.setLayout(new java.awt.BorderLayout());

        jLabel4.setFont(new java.awt.Font("segoe ui", 1, 14)); // NOI18N
        jLabel4.setText("Attendance Details");
        jLabel4.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 5, 5, 1));
        jPanel16.add(jLabel4, java.awt.BorderLayout.PAGE_START);

        jPanel17.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 5));
        jPanel17.setLayout(new java.awt.BorderLayout());

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Date", "Status", "In Time", "Out Time", "Working Hr"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setResizable(false);
            jTable1.getColumnModel().getColumn(0).setPreferredWidth(40);
            jTable1.getColumnModel().getColumn(1).setResizable(false);
            jTable1.getColumnModel().getColumn(1).setPreferredWidth(30);
            jTable1.getColumnModel().getColumn(2).setResizable(false);
            jTable1.getColumnModel().getColumn(2).setPreferredWidth(70);
            jTable1.getColumnModel().getColumn(3).setResizable(false);
            jTable1.getColumnModel().getColumn(3).setPreferredWidth(70);
            jTable1.getColumnModel().getColumn(4).setResizable(false);
            jTable1.getColumnModel().getColumn(4).setPreferredWidth(40);
        }

        jPanel17.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel16.add(jPanel17, java.awt.BorderLayout.CENTER);

        jPanel10.add(jPanel16, java.awt.BorderLayout.CENTER);

        jPanel9.add(jPanel10, java.awt.BorderLayout.CENTER);

        jPanel5.add(jPanel9, java.awt.BorderLayout.CENTER);

        jPanel3.add(jPanel5, java.awt.BorderLayout.CENTER);

        add(jPanel3, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        int resetAllBtn = JOptionPane.showConfirmDialog(this, "Are you sure want to reset all?", "Reset All", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
        if (resetAllBtn == JOptionPane.YES_OPTION) {
            resetAll();
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        this.SuperAdminDashBoard.setJPanelLoad(new EmployeeAttendance(this.SuperAdminDashBoard));
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jTextField2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField2KeyReleased

        loadViewEmployeeAttendance();
    }//GEN-LAST:event_jTextField2KeyReleased

    private void jTextField2InputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_jTextField2InputMethodTextChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2InputMethodTextChanged

    private void jTextField2PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jTextField2PropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2PropertyChange

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        loadViewEmployeeAttendance();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTextField2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField2FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2FocusLost


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private com.toedter.calendar.JMonthChooser jMonthChooser1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel28;
    private javax.swing.JPanel jPanel29;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel30;
    private javax.swing.JPanel jPanel31;
    private javax.swing.JPanel jPanel32;
    private javax.swing.JPanel jPanel33;
    private javax.swing.JPanel jPanel34;
    private javax.swing.JPanel jPanel35;
    private javax.swing.JPanel jPanel36;
    private javax.swing.JPanel jPanel37;
    private javax.swing.JPanel jPanel38;
    private javax.swing.JPanel jPanel39;
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
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private com.toedter.calendar.JYearChooser jYearChooser1;
    // End of variables declaration//GEN-END:variables
}
