/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package gui.supAdmin;

import com.mysql.cj.protocol.Resultset;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.swing.SwingUtilities;
import model.MySQL;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.AxisState;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.DateTick;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.chart.ui.HorizontalAlignment;
import org.jfree.chart.ui.RectangleEdge;
import org.jfree.chart.ui.TextAnchor;
import org.jfree.data.general.Dataset;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;

/**
 *
 * @author kguna
 */
public class EmployeeAttendanceChartFlow extends javax.swing.JPanel {
    
    private ViewEmployeeAttendance1 forViewEmployeeChartFlow;

    /**
     * Creates new form employeeAttendanceChartFlow
     */
    public EmployeeAttendanceChartFlow(ViewEmployeeAttendance1 viewEmployeeAttendance1) {
        initComponents();
        loadEmployeeFlowChart();
    }
    
    public void loadEmployeeFlowChart() {
        ChartPanel chart = new ChartPanel(createChart(createDataset()));
        jPanel1.add(chart, BorderLayout.CENTER);
        SwingUtilities.updateComponentTreeUI(jPanel1);
    }
    
    private static JFreeChart createChart(XYDataset dataset) {
        
        JFreeChart chart = ChartFactory.createTimeSeriesChart(
                "Year Summary", // title
                "Month", // x-axis label
                "Attendance", // y-axis label
                dataset, // data
                false, // create legend?
                true, // generate tooltips?
                false // generate URLs?
        );
        
        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setDomainCrosshairVisible(true);
        plot.setRangeCrosshairVisible(true);
        
        XYItemRenderer r = plot.getRenderer();
        if (r instanceof XYLineAndShapeRenderer) {
            XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) r;
        }
        
        DateAxis axis = (DateAxis) plot.getDomainAxis();
        axis.setDateFormatOverride(new SimpleDateFormat("MMM-yyyy"));
        chart.addSubtitle(new TextTitle((new SimpleDateFormat("YYYY-LLL-dd").format(new Date()))));
        TextTitle source = new TextTitle();
        source.setFont(new Font("Segoe ui", Font.PLAIN, 9));
        source.setBackgroundPaint(Color.BLACK);
        source.setPaint(Color.BLACK);
        source.setPosition(RectangleEdge.BOTTOM);
        source.setHorizontalAlignment(HorizontalAlignment.CENTER.RIGHT);
        chart.addSubtitle(source);
        chart.setBackgroundPaint(Color.GRAY);
        chart.getTitle().setPaint(new Color(31, 51, 34));
        return chart;
        
    }
    
    private static XYDataset createDataset() {
        
        int employeeCount = 0;
        int currentmMonthId;
        int currentYear;
        int totalAttendanceForMonth;
        int datesInSelectedMonth;
        int selectedMonthAttendanceCount = 0;
        
        TimeSeriesCollection dataset = new TimeSeriesCollection();
        
        try {
            
            currentYear = Integer.parseInt(new SimpleDateFormat("YYYY").format(new Date()));
            currentmMonthId = Integer.parseInt(new SimpleDateFormat("MM").format(new Date()));
            
            ResultSet empCountResultset = MySQL.execute("SELECT COUNT(`employee_nic`) FROM `employee`");
            
            if (empCountResultset.next()) {
                employeeCount = empCountResultset.getInt("COUNT(`employee_nic`)");
            }
            
            String monthValue = null;
            int a = 0;
            
            TimeSeries s1 = new TimeSeries("Attendance Summary");
            
            for (int i = 0; i < currentmMonthId; i++) {
                
                a += 1;
                datesInSelectedMonth = YearMonth.of(currentYear, a).lengthOfMonth();
                
                if(a < 10){
                    monthValue = String.valueOf("0" + a);
                }else{
                    monthValue = String.valueOf(a);
                }
                
                ResultSet empAttendanceCountResultset = MySQL.execute("SELECT COUNT(`employee_employee_nic`) FROM `employee_attendance` INNER JOIN `employee_attendance_status` "
                        + "ON `employee_attendance`.`employee_attendance_status_employee_attendance_status_id` = `employee_attendance_status`.`employee_attendance_status_id` "
                        + "INNER JOIN `attendance_date` ON `attendance_date`.`attendance_date_id`=`employee_attendance`.`attendance_date_attendance_date_id` "
                        + "WHERE `employee_attendance_status_name`='Present' AND `attendance_date` LIKE '" + currentYear + "-" + monthValue + "%'");
                
                if (empAttendanceCountResultset.next()) {
                    selectedMonthAttendanceCount = empAttendanceCountResultset.getInt("COUNT(`employee_employee_nic`)");
                }
                
                totalAttendanceForMonth = datesInSelectedMonth * employeeCount;
                float monthlyAttendancePercentage = ((float)selectedMonthAttendanceCount / (float)totalAttendanceForMonth) * 100;
                
                Day m1 = new Day(1, a, currentYear);
                
                s1.addOrUpdate(m1, monthlyAttendancePercentage);
                dataset.addSeries(s1);

                //Day m1 = new Day(1, 12, 2005);
                //s1.add(m1, 3.79);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataset;
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

        setLayout(new java.awt.BorderLayout());

        jPanel1.setPreferredSize(new java.awt.Dimension(290, 171));
        jPanel1.setLayout(new java.awt.BorderLayout());
        add(jPanel1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
