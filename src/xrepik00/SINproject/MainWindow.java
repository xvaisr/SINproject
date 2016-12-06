package xrepik00.SINproject;

import xrepik00.SINproject.GUIobjects.Plan;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

/**
 * Created by trepik on 5.12.2016.
 */
public class MainWindow extends JFrame {

    private final static int delay = 3;
    private JPanel GUI;
    private Plan plan;
    private List<String[]> events;

    MainWindow(String n) {
        setTheFrameUp(n);
        initPlayer();
        plan.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    System.out.println("playing");
                    try {
                        play();
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
    }

    private void initPlayer() {
        this.events = new LinkedList<>();
        createEvents();
    }

    private void createEvents() {
        this.events.add(new String[]{"move", "A", "1 2"});
        this.events.add(new String[]{"move", "A", "2 4"});
        this.events.add(new String[]{"clean", "A", "4"});
    }

    private void play() throws InterruptedException {
        while (!this.events.isEmpty()) {
            //wait some time delay
            TimeUnit.SECONDS.sleep(delay);
            String[] e = this.events.get(0);
            if (e[0].equals("move")) {
                System.out.println("moving roomba");
                plan.roombaMove(e[1], e[2]);
            } else if (e[0].equals("clean")){
                System.out.println("cleaning");
                plan.roombaClean(e[1], e[2]);
            }
            plan.repaint(delay*1000/2);
            this.events.remove(0);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                final MainWindow win = new MainWindow("SINproject");
                win.showit();
            }

        });
    }

    private void showit() {
        this.setVisible(true);
    }

    private void setTheFrameUp(String n) {
        setTitle(n);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        this.plan = (Plan) add(new Plan());
        this.plan.setFocusable(true);
        setExtendedState(Frame.MAXIMIZED_BOTH);
    }
}
