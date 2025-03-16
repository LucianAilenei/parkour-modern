package com.parkour;

import com.parkour.game.Player;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {

    @Test
    void testPlayerMovement() {
        Player player = new Player(100, 300, null, null);
        player.setX(200);
        assertEquals(200, player.getX());
    }
}
