package findshortestpath;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class FindShortestPath extends JPanel {
    //implements MouseListener {

    List<Double> scores;
    private int padding = 20;
    private int labelPadding = 12;
    // components colours 
    private Color lineColor = new Color(255, 255, 254);
    private Color pointColor = new Color(255, 0, 255);
    private Color gridColor = new Color(200, 200, 200, 200);
    private static int pointWidth = 10;
    private int numberYDivisions = 30;

    boolean drawLine = false;
    boolean drawPoint = false;
    boolean drawPoint2 = false;
    boolean p1Draw = false;
    boolean p12Draw = false;
    int pointX, pointY, pointX2, pointY2;
    int Xbegin, Ybegin, Xend, Yend;

    // initiaization constructor
    public FindShortestPath(List<Double> scores) {
        this.scores = scores;
        pointX = 0;
        pointY = 0;
        pointX2 = 0;
        pointY2 = 0;
        Xbegin = 0;
        Ybegin = 0;
        Xend = 0;
        Yend = 0;
        addMouseListener(mouse);
        addMouseMotionListener(mouseMotion);
    }

    // create the gui 
    public static void showGui() {

        List<Double> scores = new ArrayList();
        Random random = new Random();

        int maxDataPoints = 40;

        for (int i = 0; i < maxDataPoints; i++) {

            scores.add((double) i);

        }
        // create the panel 
        FindShortestPath mainPanel = new FindShortestPath(scores);
        // set the panel size 
        mainPanel.setPreferredSize(new Dimension(700, 600));
        // create the frame 
        JFrame jframe = new JFrame("Drawing Graph");
        // close when X
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.getContentPane().add(mainPanel);

        //create menu bar ..
        JMenuBar menuBar = new JMenuBar();
        JMenu menu1 = new JMenu("Drawing options");
        JMenu menu2 = new JMenu("Clear");
        JMenuItem pt, ln, cl;

        pt = new JMenuItem("Point");
        ln = new JMenuItem("Line");
        cl = new JMenuItem("Clear");
        // add items 
        menu1.add(pt);
        menu1.add(ln);
        menu2.add(cl);

        menuBar.add(menu1);
        menuBar.add(menu2);
        menuBar.setBackground(Color.white);
        jframe.setJMenuBar(menuBar);

        jframe.pack();
        jframe.setLocationRelativeTo(null);
        jframe.setVisible(true);

    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(Color.WHITE);

        //fill the rectangle
        g2d.fillRect(padding + labelPadding, padding, getWidth() - (2 * padding)
                - labelPadding, getHeight() - 2 * padding - labelPadding);
// Draw lines on y axis 
        for (int i = 0; i < numberYDivisions + 1; i++) {
            int x0 = padding + labelPadding;
            int x1 = pointWidth + padding + labelPadding;
            int y0 = getHeight() - ((i * (getHeight() - padding * 2
                    - labelPadding)) / numberYDivisions + padding + labelPadding);
            int y1 = y0;
            if (scores.size() > 0) {
                g2d.setColor(gridColor);
                g2d.drawLine(padding + labelPadding + 1 + pointWidth, y0, getWidth() - padding, y1);

            }
            g2d.drawLine(x0, y0, x1, y1);
        }

        // draw lines on X axis 
        for (int i = 0; i < scores.size(); i++) {
            if (scores.size() > 1) {
                int x0 = i * (getWidth() - padding * 2 - labelPadding) / (scores.size() - 1) + padding + labelPadding;
                int x1 = x0;
                int y0 = getHeight() - padding - labelPadding;
                int y1 = y0 - pointWidth;
                g2d.setColor(gridColor);
                g2d.drawLine(x0, getHeight() - padding - labelPadding - 1 - pointWidth, x1, padding);

                g2d.drawLine(x0, y0, x1, y1);
            }
        }

        
// the initial point 
        g2d.fillOval(pointX, pointY, 15, 15);
        g2d.setColor(pointColor);
        // draw the points 
        if (drawPoint == true && p1Draw == false) {

            g2d.fillOval(pointX, pointY, 15, 15);
            System.out.println("p1");

            p1Draw = true;
        } else if (drawPoint2 && p1Draw == true ) {

            g2d.fillOval(pointX2, pointY2, 15, 15);
            System.out.println("p2");
            
        }

        g.drawLine(Xbegin, Ybegin, Xend, Yend);
    }

    public MouseListener mouse = new MouseAdapter() {
        // draw when mouse clicked 
        @Override
        public void mouseClicked(MouseEvent mouseCL) {
            if (!drawPoint) {
                pointX = mouseCL.getX();
                pointY = mouseCL.getY();
                System.out.println(pointX + "  " + pointY);

                drawPoint = true;

                //drawPoint2 = true;
            } else if (!drawPoint2) {
                pointX2 = mouseCL.getX();
                pointY2 = mouseCL.getY();
                System.out.println(pointX2 + "  " + pointY2);

                drawPoint2 = true;
            }

            repaint();

        }

        // draw line 
        @Override
        public void mousePressed(MouseEvent e) {
            Xbegin = Xend = e.getX();
            Ybegin = Yend = e.getY();
            repaint();
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            Xend = e.getX();
            Yend = e.getY();
            repaint();
        }
    };
    public MouseMotionListener mouseMotion = new MouseMotionAdapter() {
        @Override
        public void mouseDragged(MouseEvent e) {
            Xend = e.getX();
            Yend = e.getY();
            repaint();
        }
    };

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {

            public void run() {

                showGui();
            }
        });

    }
}
