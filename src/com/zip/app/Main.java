package com.zip.app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class Main {
    private JPanel panel1;
    private JSlider ComplexitySlider;
    private JPanel SlidePanel;
    private JPanel drawPanel;
    private JSlider ThreshHoldSlider;
    private JSlider SliderTest1;
    private JSlider XSlider;
    private JSlider YSlider;
    private JButton resetComplexityButton;
    private JButton ResetThreshHoldButton;
    private JButton ResetTest1Button;
    private JButton ResetXButton;
    private JButton ResetYButton;

    public Main() {
        resetComplexityButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ComplexitySlider.setValue(100);
            }
        });
        ResetThreshHoldButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ThreshHoldSlider.setValue(400);
            }
        });
        ResetTest1Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SliderTest1.setValue(200);
            }
        });
        ResetXButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                XSlider.setValue(0);
            }
        });
        ResetYButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                YSlider.setValue(0);
            }
        });
        //Graphics2D g2d = (Graphics2D) drawPanel.getGraphics();
        //g2d.setColor(Color.RED);
        //g2d.fillRect(0,0,100,100);
    }
    public static void main(String[] args) {
        JFrame frame = new JFrame("Main");
        frame.setContentPane(new Main().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    double scalex = -1.941461280229202;
    double scaley = 4.4829384678787484E-4;
    double scale = 0.10;
    int max_iteration = 250;
    int count = 0;
    private void createUIComponents() {
        drawPanel = new JPanel() {
            @Override
            public void paint(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                int w = drawPanel.getWidth();
                int h = drawPanel.getHeight();
                BufferedImage out = new BufferedImage(w,h, BufferedImage.TYPE_INT_ARGB);
                int ratio = w / h;
                //fractal
                for (int py = 0; py < h; py++) {
                    for (int px = 0 ; px < w; px++) {
                        double x0 = (((((0.125/scale) * px) / (double)w) - ((0.125/scale)/2)) * ratio)+scalex;
                        double y0 = (((((0.125/scale) * py) / (double)h) - ((0.125/scale)/2)))+scaley;
                        double x = (double)XSlider.getValue()/100;
                        double y = (double)YSlider.getValue()/100;

                        int iteration = 0;
                        while ((x*x + y*y < (double)ThreshHoldSlider.getValue()/100) && (iteration < ComplexitySlider.getValue())) {
                            double xtemp = x*x - y*y + x0;
                            y = ((double)SliderTest1.getValue()/100)*x*y + y0;
                            x = xtemp;
                            iteration++;
                        }
                        out.setRGB(px,py,new Color((int)(iteration*((4228250625L)/ComplexitySlider.getValue()))).getRGB());
                    }
                }
                //end fractal
                count++;
                //end fractal
                g2d.drawImage(out, 0,0,null);
                repaint();
                //scale *= 1.01;
                if (scale > 6400) {
                    scale = 0.1;
                    System.exit(0);
                }
            }
        };
        panel1 = new JPanel() {
            @Override
            public void paint(Graphics g) {
                super.paint(g);
                drawPanel.repaint();
                repaint();
            }
        };
    }
}
