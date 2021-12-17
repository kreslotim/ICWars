package ch.epfl.cs107.play.game.icwars.handler;

import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.icwars.actor.Unit;
import ch.epfl.cs107.play.game.icwars.actor.players.ICWarsPlayer;

public interface ICWarsInteractionVisitor extends AreaInteractionVisitor {
    default void interactWith(Unit unit) {

    }

    default void interactWith(ICWarsPlayer unit) {
        System.out.println("There is a CURSOR");
    }
}
