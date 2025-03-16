package com.parkour.game;

import java.awt.Color;
import java.awt.Graphics;

public class Obstacle extends Entity {
    public Obstacle(double x, double y, int width, int height) {
        super(x, y, width, height);
    }

    @Override
    public void update(double dt) {
        // Obstacolele sunt statice.
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.DARK_GRAY);
        g.fillRect((int)x, (int)y, width, height);
    }
}
