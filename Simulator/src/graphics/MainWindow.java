package graphics;

import graphics.GUIobjects.Plan;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by trepik on 5.12.2016.
 */
public class MainWindow extends JFrame {

    private JPanel GUI;
    private Plan plan;
    private List<String[]> events;

    public MainWindow(String n) {
        setTheFrameUp(n);
        initPlayer();
        plan.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    try {
                        runAll();
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                }
                if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    step();
                }
            }
        });
    }

    private void step() {
        if (this.events.isEmpty()) {
            return;
        }
        String[] e = this.events.get(0);
        switch (e[0]) {
            case "go":
                System.out.println("moving roomba");
                plan.roombaMove(e[1], e[2]);
                break;
            case "clean":
                System.out.println("cleaning");
                plan.roombaClean(e[1], e[2]);
                break;
            case "done":
                System.out.println("done cleaning");
                plan.roombaDone(e[1], e[2]);
                break;
            case "newhome":
                System.out.println("changing home station");
                plan.roombaNewHome(e[1], e[2]);
                break;
            case "home":
                System.out.println("roomba going home");
                plan.roombaGoHome(e[1]);
                break;
            case "mess":
                System.out.println("room just got messy");
                plan.roomMakeMess(e[1]);
                break;
            case "occupied":
                System.out.println("room is occupied");
                plan.roomOccupied(e[1]);
                break;
            case "clear":
                System.out.println("room is empty");
                plan.roomClear(e[1]);
                break;
        }
        plan.repaint();
        this.events.remove(0);
    }

    private void initPlayer() {
        this.events = new LinkedList<>();
        createEvents();
    }

    private void createEvents() {
        this.events.add(new String[]{"mess", "4"});
        this.events.add(new String[]{"go", "A", "1 2"});
        this.events.add(new String[]{"go", "A", "2 4"});
        this.events.add(new String[]{"clean", "A", "4"});
        this.events.add(new String[]{"occupied", "2"});
        this.events.add(new String[]{"done", "A", "4"});
        this.events.add(new String[]{"newhome", "A", "Y"});
        this.events.add(new String[]{"go", "A", "4 5"});
        this.events.add(new String[]{"home", "A"});
        this.events.add(new String[]{"clear", "2"});
        this.events.add(new String[]{"occupied", "1"});
    }

    private void runAll() throws InterruptedException {
        while (!this.events.isEmpty()) {
            step();
        }
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
