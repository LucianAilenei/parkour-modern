package com.parkour.game;

import com.parkour.GameEngine;
import com.parkour.InputHandler;
import java.awt.Color;
import java.awt.Graphics;

public class Player extends Entity implements GameObject {
    // Parametrii fizici (px/s² și px/s)
    private final double accelForce = 50;       // Forța de accelerare orizontală
    private final double frictionCoeff = 30;      // Fricțiune
    private final double gravity = 400;           // Gravitație
    private final double jumpImpulse = -200;      // Impulsul de săritură

    private double vx = 0;  // Viteza orizontală (px/s)
    private double vy = 0;  // Viteza verticală (px/s)
    private double ax = 0;  // Accelerația orizontală (px/s²)

    private final InputHandler input;
    private final GameEngine engine;
    private int bulletCooldown = 0;
    private static final int COOLDOWN_TIME = 20;

    // Stări pentru săritură și contact
    private boolean isJumping = false;
    private boolean onPlatform = false;

    // Pentru crouch – păstrăm înălțimea originală
    private final int originalHeight;

    // Pragul sub care player-ul "moare"
    private static final double FALL_THRESHOLD = 700;

    public Player(double x, double y, InputHandler input, GameEngine engine) {
        super(x, y, 50, 50);
        this.input = input;
        this.engine = engine;
        this.originalHeight = height;
    }

    @Override
    public void update(double dt) {
        double oldX = x, oldY = y;

        // --- Mișcare orizontală ---
        ax = 0;
        if (input.isLeft()) {
            ax = -accelForce;
        } else if (input.isRight()) {
            ax = accelForce;
        } else {
            // Aplicăm fricțiune
            if (vx > 0) {
                ax = -frictionCoeff;
                if (vx + ax * dt < 0) { vx = 0; ax = 0; }
            } else if (vx < 0) {
                ax = frictionCoeff;
                if (vx + ax * dt > 0) { vx = 0; ax = 0; }
            }
        }
        vx += ax * dt;
        x += vx * dt;

        // Verificare coliziune orizontală
        for (GameObject obj : engine.getGameObjects()) {
            if (obj instanceof Obstacle) {
                Entity obs = (Entity)obj;
                if (this.intersects(obs)) {
                    x = oldX;
                    vx = 0;
                    break;
                }
            }
        }

        // --- Mișcare verticală ---
        // Permitem săritura dacă player-ul nu este deja în aer și este pe sol sau pe platformă
        if (input.isJump() && !isJumping && onPlatform) {
            vy = jumpImpulse;
            isJumping = true;
            onPlatform = false;
        }
        // Aplicăm gravitația
        vy += gravity * dt;
        y += vy * dt;

        boolean landed = false;
        onPlatform = false;
        // Verificăm coliziunea verticală cu obstacolele (platforme)
        for (GameObject obj : engine.getGameObjects()) {
            if (obj instanceof Obstacle) {
                Entity obs = (Entity)obj;
                boolean horizontalOverlap = (x + width > obs.x) && (x < obs.x + obs.width);
                if (horizontalOverlap && vy > 0) {  // player-ul cade
                    if ((oldY + height <= obs.y) && (y + height >= obs.y)) {
                        // Aterizare: player-ul se "prinde" pe platformă
                        y = obs.y - height;
                        vy = 0;
                        isJumping = false;
                        landed = true;
                        onPlatform = true;
                        break;
                    }
                } else if (horizontalOverlap && vy < 0) {  // dacă urcă și lovește tavanul
                    y = oldY;
                    vy = 0;
                    break;
                }
            }
        }
        // Dacă nu a aterizat pe nicio platformă, player-ul continuă să cadă (nu forțăm revenirea la baseY)

        // --- Crouch ---
        // Dacă se apasă crouch și player-ul nu sare, atunci:
        if (input.isCrouch() && !isJumping) {
            height = originalHeight / 2;
            // Dacă e pe sol sau platformă, nu modificăm y (rămâne la poziția de aterizare)
        } else {
            height = originalHeight;
        }

        // --- Moarte (cădere în gol) ---
        if (y > FALL_THRESHOLD) {
            System.out.println("Player died!");
            engine.getHud().loseLife();
            // Respawn la poziția inițială
            x = 100;
            y = baseY;
            vx = 0;
            vy = 0;
            isJumping = false;
            onPlatform = false;
        }

        // --- Tragere (fire) ---
        if (bulletCooldown > 0) bulletCooldown--;
        if (input.isFire() && bulletCooldown <= 0) {
            int bulletStartX = (int)(x + width);
            int bulletStartY = (int)(y + height / 2);
            int targetX = engine.getMouseX();
            int targetY = engine.getMouseY();
            double deltaX = targetX - bulletStartX;
            double deltaY = targetY - bulletStartY;
            double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
            if (distance == 0) distance = 1;
            double bulletSpeed = 10.0;
            double bx = (deltaX / distance) * bulletSpeed;
            double by = (deltaY / distance) * bulletSpeed;
            Bullet bullet = new Bullet(bulletStartX, bulletStartY, bx, by);
            engine.addGameObject(bullet);
            bulletCooldown = COOLDOWN_TIME;
        }
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillRect((int)x, (int)y, width, height);
    }
}
