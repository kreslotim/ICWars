package ch.epfl.cs107.play.game.icwars.gui;

import ch.epfl.cs107.play.game.actor.Graphics;
import ch.epfl.cs107.play.game.icwars.actor.Unit;
import ch.epfl.cs107.play.game.icwars.actor.players.RealPlayer;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Canvas;
import org.w3c.dom.ranges.Range;

// perform  the display tasks
public class ICWarsPlayerGUI implements Graphics {
    private RealPlayer realPlayer;
    private Unit selectedUnit;
    private Range range;

    /**
     * invoking the method
     * drawRangeAndPathTo on the unit selected by the player whose display it is in charge
     * of, so that it draws the unit’s range of action and the path separating the unit from
     * the player
     **/
    @Override
    public void draw(Canvas canvas) {
        if (selectedUnit != null) {
            selectedUnit.drawRangeAndPathTo(new DiscreteCoordinates((int) realPlayer.getPosition().x, (int) realPlayer.getPosition().y), canvas);
        }
    }
}
