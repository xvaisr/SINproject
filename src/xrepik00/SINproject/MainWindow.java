package xrepik00.SINproject;

import xrepik00.SINproject.GUIobjects.Plan;

import javax.swing.*;
import java.awt.*;

/**
 * Created by trepik on 5.12.2016.
 */
public class MainWindow extends JFrame {

    private JPanel GUI;

    MainWindow(String n) {
        setTheFrameUp(n);
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                MainWindow win = new MainWindow("SINproject");
                win.setVisible(true);
            }
        });
    }

    private void setTheFrameUp(String n) {
        add(new Plan());
        setTitle(n);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setExtendedState(Frame.MAXIMIZED_BOTH);
    }
}
