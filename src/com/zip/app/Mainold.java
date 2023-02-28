package com.zip.app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

public class Mainold extends JFrame implements KeyListener {
    double scalex = -1.941461280229202;
    double scaley = 4.4829384678787484E-4;
    double scale = 0.10;
    int max_iteration = 250;
    int count = 0;
    @Override
    public void paint(Graphics g) {
        Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
        Graphics2D g2d = (Graphics2D) g;
        BufferedImage out = new BufferedImage(this.getWidth(),this.getHeight(), BufferedImage.TYPE_INT_ARGB);
        int w = this.getWidth();
        int h = this.getHeight();
        int ratio = w / h;

        //fractal
        for (int py = 0; py < h; py++) {
            for (int px = 0 ; px < w; px++) {
                double x0 = (((((0.125/scale) * px) / (double)w) - ((0.125/scale)/2)) * ratio)+scalex;
                double y0 = (((((0.125/scale) * py) / (double)h) - ((0.125/scale)/2)))+scaley;
                double x = 0.0;
                double y = 0.0;

                int iteration = 0;
                while ((x*x + y*y < 4) && (iteration < max_iteration)) {
                    double xtemp = x*x - y*y + x0;
                    y = 2*x*y + y0;
                    x = xtemp;
                    iteration++;
                }
                out.setRGB(px,py,new Color((int)(iteration*((4228250625L)/max_iteration))).getRGB());
            }
        }
        /*
        File outputfile = new File("images/"+String.format("%03d", count)+".png");
        try {
            ImageIO.write(out, "png", outputfile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        */
        count++;
        //end fractal
        g2d.drawImage(out, 0,0,null);
        repaint();
        scale *= 1.01;
        if (scale > 6400) {
            scale = 0.1;
            System.exit(0);
        }
    }
    public Mainold() {
        //this.setPreferredSize(new Dimension(400, 400));
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setUndecorated(true);
        this.pack();
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        addKeyListener(this);
    }
    public static void main(String[] args) {
        new Mainold();
    }
    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_PAGE_UP) {
            scale *= 1.01;
        }
        if (e.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
            scale /= 1.01;
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            scalex -= 0.01 * (0.127/scale);
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            scalex += 0.01 * (0.127/scale);
        }
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            scaley -= 0.01 * (0.127/scale);
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            scaley += 0.01 * (0.127/scale);
        }
    }
    @Override
    public void keyReleased(KeyEvent e) {}
}