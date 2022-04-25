package model.rtree;

import model.rtree.interfaces.RTreeElement;

import javax.swing.*;
import javax.swing.plaf.basic.BasicSliderUI;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.Random;

/**
 * The class RTreeVisualization is a {@link JFrame} that, given the root RTreeNode of a RTree,
 * it will paint its elements (Rectangles & Points) on the frame's panel.
 *
 * @see RTree
 * @see RTreeNode
 * @see Rectangle
 * @see Point
 */
public class RTreeVisualization extends JFrame {

    private static final int WIDTH = 780, HEIGHT = 480;
    private static RTreeVisualization visualization;
    private final DrawingPanel drawingPanel;
    private JSlider xAxisScale, yAxisScale;
    private Component xAxisLeftBox;

    private RTreeVisualization(RTree rTree){
        super("RTree Visualization");

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

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(Color.BLACK);

        drawingPanel = new DrawingPanel(rTree, this);
        JButton refreshButton = new JButton("Refresh Visualization");
        refreshButton.addActionListener(e -> {
            drawingPanel.repaint(); //Repaint the drawing panel if user clicks the refresh button (axis will be updated too)
        });

        JPanel flowPanel = new JPanel(new FlowLayout());
        flowPanel.setBackground(Color.BLACK);
        flowPanel.add(refreshButton);

        xAxisScale = new JSlider(0, 500, 0);
        xAxisScale.setPaintTrack(false);
        xAxisScale.setPaintTicks(true);
        xAxisScale.setPaintLabels(true);
        xAxisScale.setMajorTickSpacing(50);
        xAxisScale.setMinorTickSpacing(5);
        xAxisScale.setUI(new CustomSliderUI());
        xAxisScale.setBackground(Color.BLACK);
        xAxisScale.setForeground(Color.GRAY);
        xAxisScale.setFont(new Font("Sans-Serif", Font.PLAIN, 12));

        yAxisScale = new JSlider(SwingConstants.VERTICAL, 0, 500, 0);
        yAxisScale.setPaintTrack(false);
        yAxisScale.setPaintTicks(true);
        yAxisScale.setPaintLabels(true);
        yAxisScale.setMajorTickSpacing(50);
        yAxisScale.setMinorTickSpacing(5);
        yAxisScale.setInverted(true);
        yAxisScale.setUI(new CustomSliderUI());
        yAxisScale.setBackground(Color.BLACK);
        yAxisScale.setForeground(Color.GRAY);
        yAxisScale.setFont(new Font("Sans-Serif", Font.PLAIN, 12));

        //X Axis Scale will be contained within a BoxLayout horizontal, as it needs spacing not to occupy the west zone
        JPanel northPanel = new JPanel();
        northPanel.setLayout(new BoxLayout(northPanel, BoxLayout.X_AXIS));
        xAxisLeftBox = Box.createRigidArea(new Dimension(50, 0));
        xAxisLeftBox.setBackground(Color.BLACK);
        northPanel.setBackground(Color.BLACK);
        northPanel.add(xAxisLeftBox);
        northPanel.add(xAxisScale);

        centerPanel.add(northPanel, BorderLayout.NORTH);
        centerPanel.add(yAxisScale, BorderLayout.WEST);
        centerPanel.add(drawingPanel, BorderLayout.CENTER);

        getContentPane().setBackground(Color.BLACK);
        getContentPane().add(flowPanel, BorderLayout.NORTH);
        getContentPane().add(centerPanel, BorderLayout.CENTER);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    private void updateAxis(){
        xAxisScale.setMaximum(drawingPanel.currentTotalWidth);
        yAxisScale.setMaximum(drawingPanel.currentTotalHeight);
        xAxisLeftBox.setSize(new Dimension(yAxisScale.getWidth(), 0));
        xAxisScale.repaint();
        yAxisScale.repaint();
        xAxisLeftBox.repaint();
    }


    /**
     * Starts the visualization
     * @param rTree The rTree to visualize
     * @return Whether the visualization has started or not. If not, it can mean that it is already
     * being displayed, or there is some problem with the OS
     */
    public static boolean start(RTree rTree){
        if(visualization == null){
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    visualization = new RTreeVisualization(rTree);
                }
            });
            return true;
        }

        return false;
    }

    private static class CustomSliderUI extends BasicSliderUI {
        @Override
        public void paintThumb(Graphics g) {
        }
    }


    private static class DrawingPanel extends JPanel{

        private static final int TRANSPARENCY = 120;
        private final RTree rTree;
        private final RTreeVisualization rTreeVisualization;
        private int currentTotalWidth, currentTotalHeight;

        //We need to have the boundaries of the root node's rectangles in order to scale everything properly
        private float[] limitBoundaries; //0 minX, 1 maxX, 2 minY, 3 maxY

        private DrawingPanel(RTree rTree, RTreeVisualization visualization){
            this.rTree = rTree;
            this.rTreeVisualization = visualization;
            setBackground(Color.black);
        }

        @Override
        public void paintComponent(Graphics gr) {
            super.paintComponent(gr);
            Graphics2D g = (Graphics2D) gr;

            RTreeNode rootNode = rTree.getRootNode();
            limitBoundaries = rootNode.getLimitBoundaries();

            final int WIDTH_UNIT = (int)(getSize().width/(limitBoundaries[1] - limitBoundaries[0] + 1));
            final int HEIGHT_UNIT = (int)(getSize().height/(limitBoundaries[3] - limitBoundaries[2] + 1));
            currentTotalWidth = (int)(WIDTH_UNIT * (limitBoundaries[1] - limitBoundaries[0]));
            currentTotalHeight = (int)(HEIGHT_UNIT * (limitBoundaries[3] - limitBoundaries[2]));
            rTreeVisualization.updateAxis(); //Update axis on redraw the drawing panel

            //Before painting, scale the canvas with the desired panel boundaries
            // (gotten from the rootNode.getLimitBoundaries()).
            AffineTransform transform = AffineTransform.getScaleInstance(WIDTH_UNIT, HEIGHT_UNIT);

            final float xTranslation = -limitBoundaries[0];
            final float yTranslation = -limitBoundaries[2];
            transform.concatenate(AffineTransform.getTranslateInstance((int)xTranslation, (int)yTranslation));

            //Apply scaling and translation to the canvas
            g.transform(transform);

            //Pain all the nodes
            paintNode(rTree.getRootNode(), g);
        }


        private void paintNode(RTreeNode nodeToPaint, Graphics2D g){
            if(nodeToPaint.isRectangleNode()){
                ArrayList<Rectangle> rectangles = nodeToPaint.getRectangles();
                //Paint rectangle, and its child rectangles (and points) recursively
                for(Rectangle r: rectangles){
                    int width = (int)(r.getP2().getX() - r.getP1().getX());
                    int height = (int)(r.getP2().getY() - r.getP1().getY());
                    g.setColor(getRandomColor());
                    g.fillRect((int)r.getP2().getY(), (int)r.getP1().getX(), width, height);

                    System.out.println("Painting a rectangle -> " + width + "width  "+ height + "height  " + r.getP1().getX() +"x  "+ r.getP2().getY());
                    paintNode(r.getChildNode(), g);
                }
            }
            else{
                //Paint points of the RTreeElement
                ArrayList<RTreeElement> elements = nodeToPaint.getElements();
                for(RTreeElement e: elements){
                    g.setColor(getRandomColor());
                    g.fillOval((int)e.getPoint().getX(), (int)e.getPoint().getY(), 1, 1);
                    System.out.println("Painting an oval -> " + e.getPoint().getX() +"x  "+ e.getPoint().getY());
                }
            }
        }

        private Color getRandomColor(){
            Random r = new Random();
            return new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255), TRANSPARENCY);
        }
    }
}

