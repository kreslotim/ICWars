package ch.epfl.cs107.play.game.icwars.actor.unit.action;

import ch.epfl.cs107.play.game.actor.Graphics;
import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.icwars.actor.Unit;
import ch.epfl.cs107.play.game.icwars.actor.players.ICWarsPlayer;
import ch.epfl.cs107.play.window.Keyboard;

public abstract class Action implements Graphics {
    private Unit unit;
    private Area area;
    private String name;
    private Keyboard key;

//    @Override
//    public getIntKey() {
//
//    }

    public Action(Unit unit, Area area) {
        this.unit = unit;
        this.area = area;
    }


    /**
     * the main method
     *
     * @param dt
     * @param player
     * @param keyboard
     */
    public abstract void doAction(float dt, ICWarsPlayer player, Keyboard keyboard);

}
