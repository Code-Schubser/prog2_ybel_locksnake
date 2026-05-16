package de.hsbi.lockgame.logic;

import de.hsbi.lockgame.model.Direction;

@FunctionalInterface
public interface DirectionObserver {
    void onDirectionChanged(Direction direction);
}
