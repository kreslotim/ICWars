package ch.epfl.cs107.play.game.icwars.actor.unit.action;

import ch.epfl.cs107.play.game.actor.Graphics;
import ch.epfl.cs107.play.game.actor.GraphicsEntity;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.icwars.actor.Unit;
import ch.epfl.cs107.play.game.icwars.actor.players.ICWarsPlayer;
import ch.epfl.cs107.play.game.icwars.area.ICWarsArea;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;



public abstract class Action implements Graphics {

    protected Unit unit;
    protected int key;
    private String nameOfAction;

    public Action(Unit unit, ICWarsArea area, String nameOfAction, int key) {
        this.unit = unit;
        this.key = key;
        this.nameOfAction = nameOfAction;
    }

    public int getKey() {
        return key;
    }

    public String getName() {
        return nameOfAction;
    }

    public abstract void doAction(float dt, ICWarsPlayer player, Keyboard keyboard);
}
