package com.parkour.game;

import java.awt.Graphics;

public interface GameObject {
    void update(double dt);
    void render(Graphics g);
}
