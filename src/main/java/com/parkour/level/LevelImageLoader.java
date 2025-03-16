package com.parkour.level;

import com.parkour.game.Obstacle;
import com.parkour.game.GameObject;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.InputStream;

public class LevelImageLoader {
    public static Level loadLevelFromImage(String resourcePath) {
        Level level = new Level();
        try {
            // Folosim getResourceAsStream pentru a citi fișierul din resources
            InputStream is = LevelImageLoader.class.getResourceAsStream(resourcePath);
            if (is == null) {
                System.err.println("Nu am găsit resursa: " + resourcePath);
                return level;
            }
            BufferedImage img = ImageIO.read(is);
            int imgWidth = img.getWidth();
            int imgHeight = img.getHeight();
            int blockSize = 32; // Fiecare pixel devine un bloc de 32x32 în lumea jocului
            for (int py = 0; py < imgHeight; py++) {
                for (int px = 0; px < imgWidth; px++) {
                    int pixel = img.getRGB(px, py);
                    Color color = new Color(pixel, true);
                    if (isGround(color)) {
                        // Creează un obstacol pentru fiecare pixel negru
                        level.addObstacle(new Obstacle(px * blockSize, py * blockSize, blockSize, blockSize));
                    }
                    // Poți adăuga și alte verificări, ex. pentru monede sau alte obiecte
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Setează poziția de start a jucătorului – poți extinde formatul și pentru asta
        level.setPlayerStartX(100);
        level.setPlayerStartY(300);
        return level;
    }

    private static boolean isGround(Color color) {
        // Considerăm pixelul negru (sau aproape negru) ca pământ
        return color.getRed() < 10 && color.getGreen() < 10 && color.getBlue() < 10;
    }
}
