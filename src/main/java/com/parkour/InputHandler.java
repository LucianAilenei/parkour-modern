package com.parkour;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InputHandler {
    private boolean jump;    // de exemplu, tasta W
    private boolean left;    // de exemplu, tasta A
    private boolean right;   // de exemplu, tasta D
    private boolean crouch;  // de exemplu, tasta S
    private boolean fire;    // de exemplu, click stânga
}
