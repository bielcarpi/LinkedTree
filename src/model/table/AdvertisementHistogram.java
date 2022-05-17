package model.table;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class AdvertisementHistogram extends JFrame {

    private static final int WIDTH = 1080, HEIGHT = 720;
    private static AdvertisementHistogram visualization;
    private HashMap<String, Advertisement> hashMap;

    private AdvertisementHistogram(HashMap<String, Advertisement> hashMap) {
        super("Advertisement Histogram");
        this.hashMap = hashMap;

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addWindowListener(new WindowListener() {
            @Override
            public void windowClosed(WindowEvent e) {
                visualization = null;
            }

            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowIconified(WindowEvent e) {
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
            }

            @Override
            public void windowActivated(WindowEvent e) {
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
            }
        });
        setSize(WIDTH, HEIGHT);


        DefaultCategoryDataset dataset = createDataset();
        JFreeChart chart = createChart(dataset);
        ChartPanel chartPanel = new ChartPanel(chart, false);
        chartPanel.setFillZoomRectangle(true);
        chartPanel.setMouseWheelEnabled(true);
        chartPanel.setPreferredSize(new Dimension(500, 270));
        setContentPane(chartPanel);

        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    public static boolean start(HashMap<String, Advertisement> hashMap) {
        if (visualization == null) {
            SwingUtilities.invokeLater(() -> {
                visualization = new AdvertisementHistogram(hashMap);
            });
            return true;
        }

        return false;
    }


    private DefaultCategoryDataset createDataset() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        ArrayList<Advertisement> values = hashMap.getAllValues();
        int monday = 0, tuesday = 0, wednesday = 0, thursday = 0, friday = 0, saturday = 0, sunday = 0;
        for(Advertisement a: values){
            switch (a.getDate()){
                case "Monday" -> monday++;
                case "Tuesday" -> tuesday++;
                case "Wednesday" -> wednesday++;
                case "Thursday" -> thursday++;
                case "Friday" -> friday++;
                case "Saturday" -> saturday++;
                case "Sunday" -> sunday++;
            }
        }

        dataset.setValue(monday, "Day of the Week", "Monday");
        dataset.setValue(tuesday, "Day of the Week", "Tuesday");
        dataset.setValue(wednesday, "Day of the Week", "Wednesday");
        dataset.setValue(thursday, "Day of the Week", "Thursday");
        dataset.setValue(friday, "Day of the Week", "Friday");
        dataset.setValue(saturday, "Day of the Week", "Saturday");
        dataset.setValue(sunday, "Day of the Week", "Sunday");
        return dataset;
    }

    private JFreeChart createChart(DefaultCategoryDataset dataset) {
        //Create the chart
        JFreeChart chart = ChartFactory.createBarChart(
                "Companies interested in Publishing Ads by Day", "Day of the Week", "Num of Companies",
                dataset, PlotOrientation.VERTICAL, true, true, false);

        chart.addSubtitle(new TextTitle("Pie histogram created using data in the HashMap"));
        chart.setBorderVisible(true);
        chart.setElementHinting(true);
        chart.removeLegend();

        return chart;
    }
}