Feature: Player Movement

  Scenario: Move the player to the right
    Given the player is at position 0
    When the player moves right by 5 units
    Then the player should be at position 5

  Scenario: Move the player to the left
    Given the player is at position 10
    When the player moves left by 3 units
    Then the player should be at position 7
