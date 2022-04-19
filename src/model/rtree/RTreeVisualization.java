package model.rtree;

import model.rtree.interfaces.RTreeElement;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class RTreeVisualization extends JFrame{

    public RTreeVisualization(RTreeNode rootNode){
        super("RTree Visualization");

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(780, 480);
        setContentPane(new DrawingPanel(rootNode));
        setVisible(true);
    }


    private static class DrawingPanel extends JPanel{

        private final RTreeNode rootNode;
        private static final int TRANSPARENCY = 120;

        private DrawingPanel(RTreeNode rootNode){
            this.rootNode = rootNode;
            setBackground(Color.black);
        }

        @Override
        public void paint(Graphics g) {
            paintNode(rootNode, g);
            super.paint(g);
        }

        private Color getRandomColor(){
            Random r = new Random();
            return new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255), TRANSPARENCY);
        }

        private void paintNode(RTreeNode nodeToPaint, Graphics g){
            if(nodeToPaint.isRectangleNode()){
                ArrayList<Rectangle> rectangles = nodeToPaint.getRectangles();
                //Paint rectangle, and its child rectangles (and points) recursively
                for(Rectangle r: rectangles){
                    int width = (int)(r.getP2().getX() - r.getP1().getX());
                    int height = (int)(r.getP2().getY() - r.getP1().getY());
                    g.setColor(getRandomColor());
                    g.drawRect((int)r.getP2().getY(), (int)r.getP1().getX(), width, height);

                    paintNode(r.getChildNode(), g);
                }
            }
            else{
                //Paint points of the RTreeElement
                ArrayList<RTreeElement> elements = nodeToPaint.getElements();
                for(RTreeElement e: elements){
                    g.setColor(getRandomColor());
                    g.drawOval((int)e.getPoint().getX(), (int)e.getPoint().getY(), 5, 5);
                }
            }
        }
        
    }
}

