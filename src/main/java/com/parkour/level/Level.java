package com.parkour.level;

import com.parkour.game.Obstacle;
import java.util.ArrayList;
import java.util.List;

public class Level {
    private List<Obstacle> obstacles;
    private double playerStartX;
    private double playerStartY;

    public Level() {
        obstacles = new ArrayList<>();
    }

    public void addObstacle(Obstacle obs) {
        obstacles.add(obs);
    }

    public List<Obstacle> getObstacles() {
        return obstacles;
    }

    public double getPlayerStartX() {
        return playerStartX;
    }

    public void setPlayerStartX(double playerStartX) {
        this.playerStartX = playerStartX;
    }

    public double getPlayerStartY() {
        return playerStartY;
    }

    public void setPlayerStartY(double playerStartY) {
        this.playerStartY = playerStartY;
    }
}
