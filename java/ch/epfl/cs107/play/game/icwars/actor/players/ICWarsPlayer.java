package ch.epfl.cs107.play.game.icwars.actor.players;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.icwars.actor.ICWarsActor;
import ch.epfl.cs107.play.game.icwars.actor.Unit;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

abstract public class ICWarsPlayer extends ICWarsActor implements Interactable {

    protected List<Unit> unitsList;
    protected List<Area> areasList = new ArrayList<>();

    /**
     * Default ICWarsActor constructor
     *
     * @param area     (Area): Owner area. Not null
     * @param position (Coordinate): Initial position of the entity. Not null
     */
    public ICWarsPlayer(Area area, DiscreteCoordinates position, Faction faction, Unit... units) {
        super(area, position, faction);
        for (Unit unit : units) {
            area.registerActor(unit);
            unitsList = new ArrayList<>(List.of(units));
        }

    }

    @Override
    public List<DiscreteCoordinates> getCurrentCells() {
        return Collections.singletonList(getCurrentMainCellCoordinates());
    }

    @Override
    public void update(float deltaTime) {
        if (unitsList != null) {
            for (Unit unit : unitsList) {
                if (unit.getCurrentHp() <= 0) {
                    getOwnerArea().unregisterActor(unit);
                    areasList.remove(unit);
                }
            }

        }
        super.update(deltaTime);
    }


}
