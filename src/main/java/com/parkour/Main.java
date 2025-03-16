package com.parkour;

import javax.swing.JFrame;
import com.parkour.game.Player;
import com.parkour.level.Level;
import com.parkour.level.LevelImageLoader;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Parkour Game");
        GameEngine engine = new GameEngine();

        // Încarcă nivelul din imagine din directorul de resurse
        Level level = LevelImageLoader.loadLevelFromImage("/levels/level1.png");
        // Adaugă obstacolele din nivel în motorul de joc
        for (com.parkour.game.Obstacle obs : level.getObstacles()) {
            engine.addGameObject(obs);
        }

        // Creează player-ul cu poziția de start din nivel
        Player player = new Player(level.getPlayerStartX(), level.getPlayerStartY(), engine.getInputHandler(), engine);
        engine.addGameObject(player);

        frame.add(engine);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);

        engine.requestFocusInWindow();
        engine.start();
    }
}
