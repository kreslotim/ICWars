package ch.epfl.cs107.play.game.icwars.actor.unit.action;

import ch.epfl.cs107.play.game.icwars.actor.Unit;
import ch.epfl.cs107.play.game.icwars.actor.players.ICWarsPlayer;
import ch.epfl.cs107.play.game.icwars.actor.players.RealPlayer;
import ch.epfl.cs107.play.game.icwars.area.ICWarsArea;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;

public class Wait extends Action {
    private Unit myUnit;

    /**
     * Default Wait constructor
     * @param unit
     * @param area
     */
    public Wait(Unit unit, ICWarsArea area) {
        super(unit, area, "(W)ait", Keyboard.W);
        myUnit = unit;
    }

    /**
     * Does nothing particular, keeps the unit (set as used) waiting for next round.
     * @param dt
     * @param player
     * @param keyboard
     */
    @Override
    public void doAction(float dt, ICWarsPlayer player, Keyboard keyboard) {
        System.out.println("Waiting");
        myUnit.setIsUsedUnit(true);
        player.setPlayerState(RealPlayer.PlayerStates.NORMAL);
    }

    @Override
    public void draw(Canvas canvas) {
    }
}
