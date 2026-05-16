package de.hsbi.lockgame.logic;

import de.hsbi.lockgame.model.Direction;
import de.hsbi.lockgame.model.Level;
import de.hsbi.lockgame.model.Snake;
import de.hsbi.lockgame.ui.GamePanel;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

// TODO: Die GameEngine verwaltet den GameState.

// TODO: Die GameEngine wird durch den Timer im main() getriggert ("tick") und lässt den GameState
// daraufhin einen Schritt ausführen. Dann müssen alle für den GameState registrierten Observer
// benachrichtigt werden, damit das Spielfeld neu gezeichnet werden kann o.ä.

// TODO: Die GameEngine beobachtet die Tastatureingaben (gesetzt in GamePanel.setupKeyBindings()),
// die in Direction übersetzt und an GameEngine.update() übergeben werden. Wenn es eine neue Eingabe
// gibt, wird die "update"-Methode von GameEngine aufgerufen, und die GameEngine muss die
// Blickrichtung der Schlange aktualisieren und diese GameState-Änderung den für den GameState
// registrierten Observer mitteilen.

// TODO: Die GameEngine ist ein Observer für Direction: GameEngine.update(Direction)
// TODO: Die GameEngine ist ein Observable für GameState: GamePanel.update(GameState)
public final class GameEngine {
    private Level level;
    //private GamePanel panel;
    private final List<GameStateObserver> observers = new ArrayList<>();
    private GameState state;

  public GameEngine(Level level) {
    // TODO: lege eine neue GameEngine mit den übergebenen Informationen an
      this.level = level;
      this.state = new GameState(level, new Snake(List.of(level.snakeStart())), level.pins(), GameState.Status.RUNNING, Direction.NONE);
    //throw new UnsupportedOperationException("method not implemented yet");
  }

  public GameState state() {
    // TODO: gebe den aktuellen Spielzustand zurück
      return this.state;
    //throw new UnsupportedOperationException("method not implemented yet");
  }
/*

  public void setGamePanel(GamePanel panel) {
    // TODO: Setter
      this.panel = panel;
    //throw new UnsupportedOperationException("method not implemented yet");
  }

 */
    public void addObserver(GameStateObserver observer) {
        observers.add(observer);
    }


    public void update(Direction d) {
    // TODO: aktualisiere den Blickwinkel der Schlange (GameState)
      this.state = new GameState(
          state.level(),
          state.snake(),
          state.pins(),
          state.status(),
          d
      );
    // TODO: benachrichtige alle Observer und gibt den neuen Spielzustand mit (Neuzeichnen der
    // Spielfläche)
      observers.forEach(observer -> observer.onStateChanged(this.state()));
    //throw new UnsupportedOperationException("method not implemented yet");
  }


  public void tick() {
    // TODO: lass das Spiel (den GameState) einen Schritt ("tick") machen
      this.state = this.state.tick();
    // TODO: benachrichtige alle Observer und gibt den neuen Spielzustand mit (Neuzeichnen der

      observers.forEach(observer -> observer.onStateChanged(this.state()));
    // Spielfläche)
    //throw new UnsupportedOperationException("method not implemented yet");
  }
}
