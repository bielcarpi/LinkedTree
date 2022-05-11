package model.table;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.title.TextTitle;
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


        DefaultPieDataset dataset = createDataset();
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


    private DefaultPieDataset createDataset() {
        DefaultPieDataset dataset = new DefaultPieDataset();

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

        dataset.setValue("Monday", monday);
        dataset.setValue("Tuesday", tuesday);
        dataset.setValue("Wednesday", wednesday);
        dataset.setValue("Thursday", thursday);
        dataset.setValue("Friday", friday);
        dataset.setValue("Saturday", saturday);
        dataset.setValue("Sunday", sunday);
        return dataset;
    }

    private JFreeChart createChart(DefaultPieDataset dataset) {
        JFreeChart chart = ChartFactory.createPieChart3D(
                "Companies interested in Publishing Ads by Day", dataset, true, true, false);

        chart.addSubtitle(new TextTitle("Pie histogram created using data in the HashMap"));
        PiePlot3D plot = (PiePlot3D) chart.getPlot();
        plot.setForegroundAlpha(0.8f);
        plot.setInteriorGap(0.1f);

        PieSectionLabelGenerator gen = new StandardPieSectionLabelGenerator(
                "{0}: {1} ({2})", new DecimalFormat("0"), new DecimalFormat("0%"));
        plot.setLabelGenerator(gen);

        return chart;
    }
}