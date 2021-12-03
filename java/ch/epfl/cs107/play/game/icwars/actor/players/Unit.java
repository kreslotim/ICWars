package ch.epfl.cs107.play.game.icwars.actor.players;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.icwars.actor.ICWarsActor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Button;
import ch.epfl.cs107.play.window.Keyboard;

public class Unit extends ICWarsActor {
    private Faction Faction;

    private String name;
    private int Hp;

    @Override
    public void update(float deltaTime) {

        if (Hp < 0) Hp = 0;
        super.update(deltaTime);

    }

    /**
     * Default MovableAreaEntity constructor
     *
     * @param area        (Area): Owner area. Not null
     * @param orientation (Orientation): Initial orientation of the entity. Not null
     * @param position    (Coordinate): Initial position of the entity. Not null
     */

    Unit(Area area, Orientation orientation, DiscreteCoordinates position) {
        super(area, orientation, position);
    }
}
