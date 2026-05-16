package de.hsbi.lockgame.logic;

import de.hsbi.lockgame.model.*;
import java.util.List;
import java.util.ArrayList;

public final class GameState {

    private Level level;
    private Snake snake;
    private List<Pin> pins;
    private Status status;
    private Direction pendingDirection;


  public GameState(
      Level level, Snake snake, List<Pin> pins, Status status, Direction pendingDirection) {
    // TODO: lege einen neuen GameState mit den übergebenen Informationen an
      this.level = level;
      this.snake = snake;
      this.pins = pins;
      this.status = status;
      this.pendingDirection = pendingDirection;

    //throw new UnsupportedOperationException("method not implemented yet");
  }

  public Level level() {
    // TODO: Getter
      return level;


    //throw new UnsupportedOperationException("method not implemented yet");
  }

  public Snake snake() {
    // TODO: Getter
      return snake;
    //throw new UnsupportedOperationException("method not implemented yet");
  }

  public List<Pin> pins() {
    // TODO: Getter
      return pins;
    //throw new UnsupportedOperationException("method not implemented yet");
  }

  public Status status() {
    // TODO: Getter
      return status;
    //throw new UnsupportedOperationException("method not implemented yet");
  }

  public Direction pendingDirection() {
    // TODO: Getter
      return pendingDirection;
    //throw new UnsupportedOperationException("method not implemented yet");
  }

  public GameState tick() {
    // TODO: diese Methode lässt das Spiel einen Schritt laufen (berechnet den Spielzustand im
    // nächsten Schritt)

    // TODO: early exit: wenn das Spiel nicht läuft oder keine Blickrichtung gesetzt ist: keine
    if (this.status != Status.RUNNING || this.pendingDirection == Direction.NONE) {
        return this;
    }
    // In welche Richtung wandert die Schlange als nächstes
    Position nextHead = this.snake.nextHead(this.pendingDirection);

    // TODO: prüfe die folgenden Bedingungen:
    // (a) Schlange würde das Spielfeld verlassen: Spiel verloren
      if (!level.isInside(nextHead)){
          return new GameState(this.level(),this.snake(),this.pins(), Status.LOST_OUT_OF_BOUNDS,this.pendingDirection());
      }
    // (b) Schlange würde in ein Wandelement gehen: Blockiert (keine Bewegung, Blickrichtung "none")
      if (level.cellAt(nextHead) == CellType.WALL){
          return new GameState(this.level(),this.snake(),this.pins(), this.status(), Direction.NONE);
      }

    // (c) Schlange beisst sich: Spiel verloren

      /*
      Mein erster Ansatz hat hier nicht funkioniert, darueber moechte ich im Praktikum sprechen

      if (snake.occupies(nextHead)){
          return new GameState(this.level(),this.snake(),this.pins(), Status.LOST_SELF_COLLISION, this.pendingDirection);

      }
      */
      for (Position bodyPart : this.snake().body()) {
          if (bodyPart.x() == nextHead.x() && bodyPart.y() == nextHead.y()){
              return new GameState(this.level(), this.snake(), this.pins(), Status.LOST_SELF_COLLISION, Direction.NONE);
          }
      }

    // (d) Schlange würde auf einen Pin gehen (Pin bereits gesetzt oder Schlange kommt nicht in der
    // Aktivierungsrichtung): Blockiert (keine Bewegung, Blickrichtung "none")

    // TODO: aktiviere einen noch nicht gesetzten Pin, wenn die Schlange in der richtigen Richtung
    // auf den Pin gehen würde (die Schlange darf dabei aber nicht auf den Pin gehen)

      // Pin setzen
      Pin targetPin = null;

      // Wenn unsere naechste Position die eines Pins entspricht wird dieser als Ziel gesetzt
      for (Pin pin: this.pins){
          if (pin.position().x() == nextHead.x() && pin.position().y() == nextHead.y()){
              targetPin = pin;
              break;
          }
      }

      // Wenn es ein Ziel gibt...
      if (targetPin != null){
          // pruefen ob er nicht schon bereits aktiviert ist und ob die Blickrichtung passt
          if (targetPin.state() == Pin.State.LOW && this.pendingDirection == targetPin.activationDirection()){
              // Neue Liste fuer den naechsten GameState
              List<Pin> newPins = new ArrayList<>();
              // Flag zum pruefen ob alle Pins gesetzt sind
              boolean allHigh = true;


              for (Pin pin : this.pins()){
                  // targetPin wird aktivert und als neuer Pin in die Leute Liste hinzugefuegt
                  if (pin == targetPin){
                      newPins.add(targetPin.withState(Pin.State.HIGH));

                  }
                  // Alle anderen Pins werden kopiert
                  else {
                      newPins.add(pin);
                  }
              }
              for (Pin pin : newPins){
                  if (pin.state() == Pin.State.LOW){
                      allHigh = false;
                      break;
                  }
              }
              // Naechsten Status von GameState festlegen
              Status nextStatus = allHigh ? Status.WON : Status.RUNNING;
              return new GameState(this.level(), this.snake(), newPins, nextStatus, Direction.NONE);
          }
          // Pin ist bereits gesetzt, oder Blickrichtung passt nicht
          else {
              // Pin blockiert wie eine Wand
              return new GameState(this.level(), this.snake(), this.pins(), this.status, Direction.NONE);
          }
      }

    // TODO: anderenfalls: bewege die Schlange um einen Schritt in Blickrichtung (falls gesetzt)
      // Schlange wachsen lassen in Blickrichtung
      Snake newSnake = this.snake.grow(this.pendingDirection);
      return new GameState(this.level(), newSnake, this.pins(), this.status(), this.pendingDirection());

    //throw new UnsupportedOperationException("method not implemented yet");
  }

  public enum Status {
    RUNNING,
    WON,
    LOST_SELF_COLLISION,
    LOST_OUT_OF_BOUNDS;

    public boolean isRunning() {
      return this == RUNNING;
    }
  }
}
