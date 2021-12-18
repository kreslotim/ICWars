package ch.epfl.cs107.play.game.icwars.area;

import ch.epfl.cs107.play.game.areagame.AreaBehavior;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.actor.Interactor;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.icwars.handler.ICWarsInteractionVisitor;
import ch.epfl.cs107.play.window.Window;

public class ICWarsBehavior extends AreaBehavior {
    /**
     * Default AreaBehavior Constructor
     *
     * @param window (Window): graphic context, not null
     * @param name   (String): name of the behavior image, not null
     */
    public ICWarsBehavior(Window window, String name) {
        super(window, name);
        int height = getHeight();
        int width = getWidth();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                ICWarsCellType color = ICWarsCellType.toType(getRGB(height - 1 - y, x));
                setCell(x, y, new ICWarsCell(x, y, color));
            }
        }
    }

    @Override
    public void cellInteractionOf(Interactor interactor) {
        super.cellInteractionOf(interactor);
    }

    public enum ICWarsCellType {
        NONE(0, 0, "None"),
        ROAD(16777216, 0, "Road"),
        PLAIN(-14112955, 1, "Plain"),
        WOOD(-65536, 3, "Wood"),
        RIVER(-16776961, 0, "River"),
        MOUNTAIN(-256, 4, "Mountain"),
        CITY(-1, 2, "City");

        final int type;
        final int defenceStars;
        final String nameOfCell;



        public int getDefenceStars() {
            return defenceStars;
        }

        public String typeToString() {
            return nameOfCell;
        }


        ICWarsCellType(int type, int defenseStars, String nameOfCell) {
            this.type = type;
            this.defenceStars = defenseStars;
            this.nameOfCell = nameOfCell;
        }


        public static ICWarsCellType toType(int type) {
            for (ICWarsCellType ict : ICWarsCellType.values()) {
                if (ict.type == type)
                    return ict;
            }
            return NONE;
        }


    }

    /**
     * Cell adapted to the ICWars game
     */
    public class ICWarsCell extends AreaBehavior.Cell {
        /// Type of the cell following the enum
        private final ICWarsCellType typeOfCell;

        public int getDefenceStars() {
            return typeOfCell.defenceStars;
        }

        public ICWarsCellType getCellType() {
            return typeOfCell;
        }





        /**
         * Default Cell constructor
         *
         * @param x    (int): x-coordinate of this cell
         * @param y    (int): y-coordinate of this cell
         * @param typeOfCell (EnigmeCellType), not null
         */
        public ICWarsCell(int x, int y, ICWarsCellType typeOfCell) {
            super(x, y);
            this.typeOfCell = typeOfCell;
        }

        @Override
        protected boolean canLeave(Interactable entity) {
            return true;
        }


        @Override
        protected boolean canEnter(Interactable entity) {


            boolean containsNotTraversable = false;
            for (Interactable val : entities) {
                if (val.takeCellSpace()) {
                    containsNotTraversable = true;
                }

            }
            return !(containsNotTraversable && entity.takeCellSpace());
        }


        @Override
        public boolean isCellInteractable() {
            return true;
        }

        @Override
        public boolean isViewInteractable() {
            return false;
        }

        @Override
        public void acceptInteraction(AreaInteractionVisitor v) {
            ((ICWarsInteractionVisitor) v).interactWith(this);
        }

    }
}
