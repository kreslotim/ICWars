package ch.epfl.cs107.play.game.icwars.actor.unit.action;

import ch.epfl.cs107.play.game.actor.Graphics;
import ch.epfl.cs107.play.game.actor.GraphicsEntity;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.icwars.actor.Unit;
import ch.epfl.cs107.play.game.icwars.actor.players.ICWarsPlayer;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;

import java.awt.*;

public class Action implements Graphics {

    private Unit unit;
    private String nameOfAction;

    public Action(Unit unit, Area area) {
        this.unit = unit;
        //unit.setParent(this);

    }

    public void doAction(float dt, ICWarsPlayer player, Keyboard keyboard) {

    }

    @Override
    public void draw(Canvas canvas) {
    }

    class Wait {
        //unit.setIsUsed;
    }


}
