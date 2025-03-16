package com.parkour.game;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Entity implements GameObject {
    // Pozițiile se stochează ca double pentru precizie.
    protected double x, y;
    protected int width, height;
    protected double baseY;  // Nivelul solului (sau al platformei de bază)

    public Entity(double x, double y, int width, int height) {
        this.x = x;
        this.y = y;
        this.baseY = y;  // presupunem că poziția inițială este solul
        this.width = width;
        this.height = height;
    }

    public boolean intersects(Entity other) {
        return this.x < other.x + other.width &&
                this.x + this.width > other.x &&
                this.y < other.y + other.height &&
                this.y + this.height > other.y;
    }
}
