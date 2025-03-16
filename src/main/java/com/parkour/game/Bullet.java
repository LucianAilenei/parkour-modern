package com.parkour.game;

import java.awt.Color;
import java.awt.Graphics;

public class Bullet extends Entity {
    private final double vx;
    private final double vy;

    public Bullet(int x, int y, double vx, double vy) {
        super(x, y, 10, 5);
        this.vx = vx;
        this.vy = vy;
    }

    @Override
    public void update(double dt) {
        x += vx * dt;
        y += vy * dt;
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect((int)x, (int)y, width, height);
    }
}
