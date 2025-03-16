package com.parkour;

import lombok.Getter;
import lombok.Setter;
import javax.swing.JPanel;
import javax.swing.AbstractAction;
import javax.swing.KeyStroke;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import com.parkour.game.GameObject;

@Getter
@Setter
public class GameEngine extends JPanel implements Runnable {
    private volatile boolean running;
    private Thread gameThread;
    private final List<GameObject> gameObjects;
    private final List<GameObject> pendingAdditions;
    private final InputHandler inputHandler;
    private final GameHUD hud;
    private int mouseX, mouseY;

    public GameEngine() {
        gameObjects = new ArrayList<>();
        pendingAdditions = new ArrayList<>();
        inputHandler = new InputHandler();
        hud = new GameHUD();

        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        setupKeyBindings();
        setupMouseListener();
    }

    private void setupKeyBindings() {
        // Tasta W pentru săritură
        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("W"), "jumpPressed");
        getActionMap().put("jumpPressed", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inputHandler.setJump(true);
            }
        });
        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("released W"), "jumpReleased");
        getActionMap().put("jumpReleased", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inputHandler.setJump(false);
            }
        });

        // Tasta A pentru stânga
        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("A"), "leftPressed");
        getActionMap().put("leftPressed", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inputHandler.setLeft(true);
            }
        });
        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("released A"), "leftReleased");
        getActionMap().put("leftReleased", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inputHandler.setLeft(false);
            }
        });

        // Tasta D pentru dreapta
        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("D"), "rightPressed");
        getActionMap().put("rightPressed", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inputHandler.setRight(true);
            }
        });
        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("released D"), "rightReleased");
        getActionMap().put("rightReleased", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inputHandler.setRight(false);
            }
        });

        // Tasta S pentru crouch
        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("S"), "crouchPressed");
        getActionMap().put("crouchPressed", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inputHandler.setCrouch(true);
            }
        });
        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("released S"), "crouchReleased");
        getActionMap().put("crouchReleased", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inputHandler.setCrouch(false);
            }
        });
    }

    private void setupMouseListener() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if(e.getButton() == MouseEvent.BUTTON1) {
                    inputHandler.setFire(true);
                    mouseX = e.getX();
                    mouseY = e.getY();
                }
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                if(e.getButton() == MouseEvent.BUTTON1) {
                    inputHandler.setFire(false);
                }
            }
        });
    }

    public void addGameObject(GameObject obj) {
        if(obj != null) {
            synchronized(pendingAdditions) {
                pendingAdditions.add(obj);
            }
        }
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        while(running) {
            long now = System.nanoTime();
            double dt = (now - lastTime) / 1e9; // dt în secunde
            lastTime = now;

            updateGameObjects(dt);
            repaint();

            try {
                Thread.sleep(2);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private void updateGameObjects(double dt) {
        synchronized(pendingAdditions) {
            if(!pendingAdditions.isEmpty()) {
                gameObjects.addAll(pendingAdditions);
                pendingAdditions.clear();
            }
        }
        for(GameObject obj : gameObjects) {
            obj.update(dt);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for(GameObject obj : gameObjects) {
            obj.render(g);
        }
        hud.render(g);
    }

    public void start() {
        if(running) return;
        running = true;
        gameThread = new Thread(this, "GameEngineThread");
        gameThread.start();
    }

    public void stop() {
        running = false;
        if(gameThread != null) {
            try {
                gameThread.join();
            } catch(InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
