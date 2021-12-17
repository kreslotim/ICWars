package ch.epfl.cs107.play.game.icwars.actor.unit.action;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.icwars.actor.Unit;
import ch.epfl.cs107.play.game.icwars.actor.players.ICWarsPlayer;
import ch.epfl.cs107.play.game.icwars.area.ICWarsArea;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;

public class Wait extends Action {

    public Wait(Unit unit, ICWarsArea area) {
        super(unit, area, "(W)ait", Keyboard.W);
    }


    @Override
    public void doAction(float dt, ICWarsPlayer player, Keyboard keyboard) {
            unit.setIsUsedUnit(true);
            player.setPlayerState(ICWarsPlayer.PlayerStates.NORMAL);
    }

    @Override
    public void draw(Canvas canvas) {
    }
}
