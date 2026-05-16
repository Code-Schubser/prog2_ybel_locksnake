package de.hsbi.lockgame.logic;

import de.hsbi.lockgame.model.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class GameStateTest {

    @Test
    void testTickDoesNothingWhenDirectionIsNone() {

        GameState originalState = new GameState(
            null,
            null,
            new java.util.ArrayList<>(),
            GameState.Status.RUNNING,
            Direction.NONE
        );

        GameState resultState = originalState.tick();
        assertEquals(GameState.Status.RUNNING, resultState.status());
        assertEquals(Direction.NONE, resultState.pendingDirection());
    }
    @Test
    void testTickDoesNothingWhenGameIsAlreadyLost() {
        GameState originalState = new GameState(
            null,
            null,
            new java.util.ArrayList<>(),
            GameState.Status.LOST_OUT_OF_BOUNDS,
            Direction.UP
        );

        GameState resultState = originalState.tick();
        assertEquals(GameState.Status.LOST_OUT_OF_BOUNDS, resultState.status());
        assertEquals(Direction.UP, resultState.pendingDirection());
    }

    /*
    Sorry aber irgendwann ist auch mal gut mein Worload ist schon ueber 16h...
     */

}
