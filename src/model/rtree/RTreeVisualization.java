package model.rtree;

import model.rtree.interfaces.RTreeElement;

import javax.swing.*;
import javax.swing.plaf.basic.BasicSliderUI;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
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

    private static final int WIDTH = 1080, HEIGHT = 720;
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
            drawingPanel.drawTree(); //Repaint the drawing panel if user clicks the refresh button (axis will be updated too)
        });

        JPanel flowPanel = new JPanel(new FlowLayout());
        flowPanel.setBackground(Color.BLACK);
        flowPanel.add(refreshButton);

        xAxisScale = new JSlider(0, 500, 0);
        xAxisScale.setPaintTrack(false);
        xAxisScale.setPaintTicks(true);
        xAxisScale.setPaintLabels(true);
        xAxisScale.setUI(new CustomSliderUI());
        xAxisScale.setBackground(Color.BLACK);
        xAxisScale.setForeground(Color.GRAY);
        xAxisScale.setFont(new Font("Sans-Serif", Font.PLAIN, 12));

        yAxisScale = new JSlider(SwingConstants.VERTICAL, 0, 500, 0);
        yAxisScale.setPaintTrack(false);
        yAxisScale.setPaintTicks(true);
        yAxisScale.setPaintLabels(true);
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

        //Draw the tree
        updateAxis();
        drawingPanel.drawTree();
    }

    private void updateAxis(){
        xAxisScale.setMaximum((int)(drawingPanel.limitBoundaries[1]) + 1);
        xAxisScale.setMinimum((int)(drawingPanel.limitBoundaries[0]));
        xAxisScale.setMajorTickSpacing(5);
        xAxisScale.setMinorTickSpacing(1);

        yAxisScale.setMaximum((int)(drawingPanel.limitBoundaries[3]) + 1);
        yAxisScale.setMinimum((int)(drawingPanel.limitBoundaries[2]));
        yAxisScale.setMajorTickSpacing(5);
        yAxisScale.setMinorTickSpacing(1);
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
        private BufferedImage bufferedImage;

        //We need to have the boundaries of the root node's rectangles in order to scale everything properly
        private float[] limitBoundaries = new float[]{0, 100, 0, 100}; //0 minX, 1 maxX, 2 minY, 3 maxY
        private float WIDTH_UNIT, HEIGHT_UNIT;

        private DrawingPanel(RTree rTree, RTreeVisualization visualization){
            this.rTree = rTree;
            this.rTreeVisualization = visualization;
            setBackground(Color.black);
        }

        public void drawTree(){
            RTreeNode rootNode = rTree.getRootNode();
            limitBoundaries = rootNode.getLimitBoundaries();
            rTreeVisualization.updateAxis(); //Update axis on redraw the drawing panel (now that we have the limit boundaries)

            bufferedImage = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = bufferedImage.createGraphics();


            WIDTH_UNIT = getSize().width/(limitBoundaries[1] - limitBoundaries[0] + 1);
            HEIGHT_UNIT = getSize().height/(limitBoundaries[3] - limitBoundaries[2] + 1);

            //Before painting, translate the canvas
            final float xTranslation = -limitBoundaries[0];
            final float yTranslation = -limitBoundaries[2];
            AffineTransform transform = AffineTransform.getTranslateInstance((int)(xTranslation * WIDTH_UNIT), (int)(yTranslation * HEIGHT_UNIT));

            //Apply translation to the canvas
            g2.transform(transform);

            //Pain all the nodes
            paintNode(rTree.getRootNode(), g2);


            //Once everything is painted, call repaint()
            g2.dispose();
            this.repaint();
        }


        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            if(bufferedImage != null) g.drawImage(bufferedImage, 0, 0, null);
        }


        private void paintNode(RTreeNode nodeToPaint, Graphics2D g){
            if(nodeToPaint.isRectangleNode()){
                ArrayList<Rectangle> rectangles = nodeToPaint.getRectangles();
                //Paint rectangle, and its child rectangles (and points) recursively
                for(Rectangle r: rectangles){
                    float width = r.getP2().getX() - r.getP1().getX();
                    float height = r.getP2().getY() - r.getP1().getY();
                    g.setColor(getRandomColor());
                    if(width == 0 && height == 0)
                        fillRect(r.getP1().getX() - 0.12f, r.getP1().getY() - 0.12f, 0.25f, 0.25f, g);
                    else if(width == 0)
                        fillRect(r.getP1().getX() - 0.12f, r.getP1().getY(), 0.25f, height, g);
                    else if(height == 0)
                        fillRect(r.getP1().getX(), r.getP1().getY() - 0.12f, width, 0.25f, g);
                    else
                        fillRect(r.getP1().getX(), r.getP1().getY(), width, height, g);

                    paintNode(r.getChildNode(), g);
                }
            }
            else{
                //Paint points of the RTreeElement
                ArrayList<RTreeElement> elements = nodeToPaint.getElements();
                for(RTreeElement e: elements){
                    g.setColor(getRandomColor());
                    fillOval(e.getPoint().getX(), e.getPoint().getY(), g);
                }
            }
        }

        private void fillRect(float x, float y, float width, float height, Graphics2D g){
            //Fills a Rectangle with own coordinate system
            g.fillRect((int)(x * WIDTH_UNIT), (int)(y * HEIGHT_UNIT), (int)(width * WIDTH_UNIT), (int)(height * HEIGHT_UNIT));
        }

        private void fillOval(float x, float y, Graphics2D g){
            //Fills an Oval with own coordinate system
            int width;
            if(WIDTH_UNIT > 70) width = 12;
            else if(WIDTH_UNIT > 8) width = 5;
            else width = 3;

            g.fillOval((int)(x*WIDTH_UNIT - width/2), (int)(y*HEIGHT_UNIT - width/2), width, width);
        }

        private Color getRandomColor(){
            Random r = new Random();
            return new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255), TRANSPARENCY);
        }
    }
}

