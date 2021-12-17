package ch.epfl.cs107.play.game.icwars.actor.players;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.icwars.actor.ICWarsActor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Canvas;

import java.util.List;

public class AIPlayer extends ICWarsPlayer {


    /**
     * Default ICWarsActor constructor
     *
     * @param area     (Area): Owner area. Not null
     * @param position (Coordinate): Initial position of the entity. Not null
     */
    public AIPlayer(Area area, DiscreteCoordinates position, Faction faction) {
        super(area, position, faction);
    }

    @Override
    public void draw(Canvas canvas) {
    }

    @Override
    public List<DiscreteCoordinates> getCurrentCells() {
        return null;
    }

    @Override
    public List<DiscreteCoordinates> getFieldOfViewCells() {
        return null;
    }

    @Override
    public boolean wantsCellInteraction() {
        return false;
    }

    @Override
    public boolean wantsViewInteraction() {
        return false;
    }

    @Override
    public void interactWith(Interactable other) {

    }

    /**
     * verifie avec Urs si besoin de ces quatre methodes
     */
//    @Override
//    public boolean takeCellSpace() {
//        return false;
//    }
//
//    @Override
//    public boolean isCellInteractable() {
//        return false;
//    }
//
//    @Override
//    public boolean isViewInteractable() {
//        return false;
//    }
//
//    @Override
//    public void acceptInteraction(AreaInteractionVisitor v) {
//
//    }
}
