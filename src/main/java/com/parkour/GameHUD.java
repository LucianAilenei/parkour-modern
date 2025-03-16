package com.parkour;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class GameHUD {
    private int score;
    private int lives;

    public GameHUD() {
        score = 0;
        lives = 3;
    }

    public void addScore(int points) {
        score += points;
    }

    public void loseLife() {
        lives--;
    }

    public void render(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 18));
        g.drawString("Score: " + score, 10, 20);
        g.drawString("Lives: " + lives, 10, 40);
    }
}
