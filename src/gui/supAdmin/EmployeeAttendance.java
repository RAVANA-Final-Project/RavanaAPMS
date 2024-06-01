package gui.supAdmin;

import gui.admin.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.sql.ResultSet;
import java.text.AttributedString;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.SwingUtilities;
import model.MySQL;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.ui.RectangleInsets;
import org.jfree.chart.util.Rotation;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

/**
 *
 * @author kguna
 */
public class EmployeeAttendance extends javax.swing.JPanel {

    public static final int NORMAL_VALIDATE = 0;
    public static final int INCREMENT_VALIDATE = 1;
    public static final int DECREMENT_VALIDATE = 2;
    

    SuperAdminDashBoard SuperAdminDashBoard;

    /**
     * Creates new form Booking
     */
    public EmployeeAttendance(SuperAdminDashBoard SuperAdminDashBoard) {
        initComponents();
        this.SuperAdminDashBoard = SuperAdminDashBoard;
        loadDailyAttendancePrecentage();
    }

    private void loadDailyAttendancePrecentage() {

        float attendanceCount = 0;
        float totalEmployeeCount = 0;

        try {

            ResultSet employeeAttendanceResultset = MySQL.execute("SELECT COUNT(`employee_employee_nic`) FROM `employee_attendance` INNER JOIN `attendance_date` ON "
                    + "`employee_attendance`.`attendance_date_attendance_date_id`=`attendance_date`.`attendance_date_id` INNER JOIN `employee_attendance_status` ON "
                    + "`employee_attendance`.`employee_attendance_status_employee_attendance_status_id`=`employee_attendance_status`.`employee_attendance_status_id` "
                    + "WHERE `attendance_date` ='" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + "' AND `employee_attendance_status_name`='Present'");

            ResultSet totalActiveEmployeeCount = MySQL.execute("SELECT COUNT(`employee_nic`) From `employee` INNER JOIN `employee_status` ON "
                    + "`employee`.`employee_status_employee_status_id`=`employee_status`.`employee_status_id` "
                    + "WHERE `employee_status_name`='Active'");

            if (employeeAttendanceResultset.next()) {
                attendanceCount = employeeAttendanceResultset.getInt("COUNT(`employee_employee_nic`)");
            }
            if (totalActiveEmployeeCount.next()) {
                totalEmployeeCount = totalActiveEmployeeCount.getInt("COUNT(`employee_nic`)");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        DefaultPieDataset dataset = new DefaultPieDataset();

        String finalPresentValue = null;
        String finalAbsentValue = null;

        if (totalEmployeeCount > 0) {
            finalPresentValue = String.valueOf((attendanceCount / totalEmployeeCount) * 100);
            finalAbsentValue = String.valueOf(((totalEmployeeCount - attendanceCount) / totalEmployeeCount) * 100);
        }
        dataset.setValue("Present", finalPresentValue != null ? Float.parseFloat(finalPresentValue) : 0);
        dataset.setValue("Absent", finalAbsentValue != null ? Float.parseFloat(finalAbsentValue) : 0);

        JFreeChart chart1 = ChartFactory.createPieChart("", dataset);
        
        chart1.setBackgroundPaint(getBackground());//pie-chart background color
        chart1.setPadding(new RectangleInsets(25, 10, 10, 5));
        chart1.setBorderPaint(getBackground());//pie-chart border color

        PiePlot plot = (PiePlot) chart1.getPlot();
        plot.setSectionPaint("Present", new Color(25, 134, 39));//Pie-Chart Section colors
        plot.setSectionPaint("Absent", new Color(255,51,0));//Pie-Chart Section colors
        plot.setDirection(Rotation.CLOCKWISE);
        plot.setNoDataMessage("Still No Attendance Records for Today ");//Display Message When no data Inserted to the chart
        plot.setNoDataMessagePaint(new Color(220, 220, 220));
        plot.setNoDataMessageFont(new Font("segoe ui", 0, 18));
        plot.setExplodePercent("Present", 0.050);//Gap Around Defined Pie
        plot.setOutlinePaint(getBackground());//pie-char rectangle outline

        plot.setLabelFont(new Font("segoe ui", 0, 16));
        plot.setLabelBackgroundPaint(new Color(50, 51, 51));//pie-label background color
        plot.setLabelOutlinePaint(null);
        plot.setSimpleLabels(true);
        plot.setSimpleLabelOffset(new RectangleInsets( 0.09, 0.09, 0.09, 0.09));
        plot.setLabelShadowPaint(null);
        plot.setLabelPaint(new Color(255, 255, 255));//pie-label font color
        plot.setLabelGap(0.1);//to set pie-labels linked to pie-chart fields
        plot.setLabelLinksVisible(false);

        plot.setInteriorGap(0.1);
        plot.setShadowPaint(new Color(36, 38, 36));//shadow around pie-chart
        plot.setIgnoreZeroValues(true);
        plot.setIgnoreNullValues(true);
        plot.setBackgroundPaint(null);//to avoid color around pie-chart

        plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0} : {2}"));

        ChartPanel chartPanel = new ChartPanel(chart1);

        jPanel6.removeAll();
        jPanel6.add(chartPanel, BorderLayout.CENTER);//to add pie-chart to the panel
        SwingUtilities.updateComponentTreeUI(jPanel6);

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
        jLabel3 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        jPanel16 = new javax.swing.JPanel();

        setLayout(new java.awt.BorderLayout());

        jPanel1.setLayout(new java.awt.BorderLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Manage Employee Attendances");
        jPanel1.add(jLabel1, java.awt.BorderLayout.CENTER);

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/iconPack64/image_accept.png"))); // NOI18N
        jLabel3.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 1, 1, 1));
        jPanel1.add(jLabel3, java.awt.BorderLayout.PAGE_START);

        jPanel7.setLayout(new java.awt.BorderLayout());

        jLabel5.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Today Attendance Percentage");
        jLabel5.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jLabel5.setPreferredSize(new java.awt.Dimension(236, 40));
        jPanel7.add(jLabel5, java.awt.BorderLayout.CENTER);

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("To Manage Employee Attendances");
        jLabel6.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jLabel6.setPreferredSize(new java.awt.Dimension(183, 28));
        jPanel7.add(jLabel6, java.awt.BorderLayout.PAGE_START);

        jPanel1.add(jPanel7, java.awt.BorderLayout.PAGE_END);

        add(jPanel1, java.awt.BorderLayout.PAGE_START);

        jPanel2.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 72, 1, 1));
        jPanel2.setMinimumSize(new java.awt.Dimension(200, 368));
        jPanel2.setPreferredSize(new java.awt.Dimension(378, 368));
        jPanel2.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 305, Short.MAX_VALUE)
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        jPanel2.add(jPanel11, java.awt.BorderLayout.PAGE_START);

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 305, Short.MAX_VALUE)
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        jPanel2.add(jPanel12, java.awt.BorderLayout.PAGE_END);

        jPanel13.setPreferredSize(new java.awt.Dimension(255, 80));
        jPanel13.setLayout(new java.awt.GridLayout(2, 1, 0, 20));

        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton1.setText("Mark Attendance");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel13.add(jButton1);

        jButton3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton3.setText("View Attendance");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel13.add(jButton3);

        jPanel2.add(jPanel13, java.awt.BorderLayout.CENTER);

        add(jPanel2, java.awt.BorderLayout.LINE_START);

        jPanel4.setLayout(new java.awt.BorderLayout());

        jPanel6.setLayout(new java.awt.BorderLayout());

        jPanel5.setLayout(new java.awt.BorderLayout());

        jPanel8.setPreferredSize(new java.awt.Dimension(630, 20));

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 530, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );

        jPanel5.add(jPanel8, java.awt.BorderLayout.PAGE_START);

        jPanel9.setPreferredSize(new java.awt.Dimension(20, 264));

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 360, Short.MAX_VALUE)
        );

        jPanel5.add(jPanel9, java.awt.BorderLayout.LINE_END);

        jPanel10.setPreferredSize(new java.awt.Dimension(20, 344));

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 360, Short.MAX_VALUE)
        );

        jPanel5.add(jPanel10, java.awt.BorderLayout.LINE_START);

        jPanel15.setPreferredSize(new java.awt.Dimension(630, 20));

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 530, Short.MAX_VALUE)
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );

        jPanel5.add(jPanel15, java.awt.BorderLayout.PAGE_END);

        jPanel16.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(102, 102, 102), 1, true));
        jPanel16.setPreferredSize(new java.awt.Dimension(490, 344));
        jPanel16.setLayout(new java.awt.BorderLayout());
        jPanel5.add(jPanel16, java.awt.BorderLayout.CENTER);

        jPanel6.add(jPanel5, java.awt.BorderLayout.CENTER);

        jPanel4.add(jPanel6, java.awt.BorderLayout.CENTER);

        add(jPanel4, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        if (this.SuperAdminDashBoard != null) {
            SuperAdminDashBoard.setJPanelLoad(new ManageEmployeeAttendance(this.SuperAdminDashBoard));
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        if (this.SuperAdminDashBoard != null) {
            SuperAdminDashBoard.setJPanelLoad(new ViewEmployeeAttendance1(this.SuperAdminDashBoard));
        }
    }//GEN-LAST:event_jButton3ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    // End of variables declaration//GEN-END:variables
}
