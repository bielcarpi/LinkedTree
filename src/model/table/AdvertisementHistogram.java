package model.table;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class AdvertisementHistogram extends JFrame {

    private static final int WIDTH = 1080, HEIGHT = 720;
    private static AdvertisementHistogram visualization;

    private AdvertisementHistogram(){
        super("Advertisement Histogram");

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addWindowListener(new WindowListener() {
            @Override
            public void windowClosed(WindowEvent e) {
                visualization = null;
            }
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowIconified(WindowEvent e) {}
            @Override
            public void windowDeiconified(WindowEvent e) {}
            @Override
            public void windowActivated(WindowEvent e) {}
            @Override
            public void windowDeactivated(WindowEvent e) {}
        });
        setSize(WIDTH, HEIGHT);

        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    public static boolean start(HashMap<String, Advertisement> hashMap){
        if(visualization == null){
            SwingUtilities.invokeLater(() -> {
                visualization = new AdvertisementHistogram();
            });
            return true;
        }

        return false;
    }
}
