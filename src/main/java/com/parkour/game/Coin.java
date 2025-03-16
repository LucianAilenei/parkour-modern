package com.parkour.game;

import java.awt.Color;
import java.awt.Graphics;

public class Coin extends Entity {
    public Coin(double x, double y, int width, int height) {
        super(x, y, width, height);
    }

    @Override
    public void update(double dt) {
        // Monedele sunt statice; poți adăuga animații aici dacă dorești.
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.YELLOW);
        g.fillOval((int)x, (int)y, width, height);
    }
}
