package graphics;

import graphics.GUIobjects.Plan;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import simulator.environement.Building;
import simulator.eventHandling.EventListener;
import simulator.injection.impl.Injector;

/**
 * Created by trepik on 5.12.2016.
 */
public class MainWindow extends JFrame implements EventListener {

    private final static int delay = 3;
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
                    System.out.println("playing");
                    try {
                        play();
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
        if (e[0].equals("go")) {
            System.out.println("moving roomba");
            plan.roombaMove(e[1], e[2]);
        } else if (e[0].equals("clean")) {
            System.out.println("cleaning");
            plan.roombaClean(e[1], e[2]);
        } else if (e[0].equals("done")) {
            System.out.println("done cleaning");
            plan.roombaDone(e[1], e[2]);
        } else if (e[0].equals("newhome")) {
            System.out.println("changing home station");
            plan.roombaNewHome(e[1], e[2]);
        } else if (e[0].equals("home")) {
            System.out.println("roomba going home");
            plan.roombaGoHome(e[1]);
        } else if (e[0].equals("mess")) {
            System.out.println("room just got messy");
            plan.roomMakeMess(e[1]);
        } else if (e[0].equals("occupied")) {
            System.out.println("room is occupied");
            plan.roomOccupied(e[1]);
        } else if (e[0].equals("clear")) {
            System.out.println("room is empty");
            plan.roomClear(e[1]);
        }
        plan.repaint();
//        updatePlan();
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
/*
    private void updatePlan() {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                plan.invalidate();
                plan.validate();
                plan.repaint();
            }
        });
    }*/

    private void play() throws InterruptedException {
        while (!this.events.isEmpty()) {
            //wait some time delay
            TimeUnit.SECONDS.sleep(delay);
            step();
        }
    }

    public void showit() {
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

    @Override
    public void perceiveEvent(simulator.eventHandling.Event ev) {
        // xvaisr00 pozn: tohle by melo ukladat event do nejake fronty, kterou si poto muzes zpracovat ve step(); metode
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean addEventFilter(simulator.eventHandling.EventFilter f) {
        // xvaisr00 pozn: neni nutne implementovat pokud ti scheduler/controler preda jen ty eventy, ktere te zajimaji
        return true;
    }
}
