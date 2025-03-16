package com.parkour.level;

import java.util.ArrayList;
import java.util.List;

public class LevelManager {
    private List<Level> levels;
    private int currentLevelIndex;

    public LevelManager() {
        levels = new ArrayList<>();
        currentLevelIndex = 0;
    }

    public void addLevel(Level level) {
        levels.add(level);
    }

    public Level getCurrentLevel() {
        if(currentLevelIndex < levels.size()) {
            return levels.get(currentLevelIndex);
        }
        return null;
    }

    public void nextLevel() {
        currentLevelIndex++;
        if(currentLevelIndex >= levels.size()) {
            currentLevelIndex = 0; // sau oprește jocul, sau repornește de la primul nivel
        }
    }
}
