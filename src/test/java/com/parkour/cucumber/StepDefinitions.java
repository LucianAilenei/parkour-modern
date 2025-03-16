package com.parkour.cucumber;

import com.parkour.game.Player;
import io.cucumber.java.en.*;

import static org.junit.jupiter.api.Assertions.*;

public class StepDefinitions {
    private Player player;

    @Given("the player is at position {int}")
    public void thePlayerIsAtPosition(int startX) {
        player = new Player(startX, 300, null, null); // Inițializează player-ul la startX
    }

    @When("the player moves right by {int} units")
    public void thePlayerMovesRight(int units) {
        player.setX(player.getX() + units);
    }

    @When("the player moves left by {int} units")
    public void thePlayerMovesLeft(int units) {
        player.setX(player.getX() - units);
    }

    @Then("the player should be at position {int}")
    public void thePlayerShouldBeAtPosition(int expectedX) {
        assertEquals(expectedX, player.getX());
    }
}
