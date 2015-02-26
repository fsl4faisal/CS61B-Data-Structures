package creatures;
import huglife.Creature;
import huglife.Direction;
import huglife.Action;
import huglife.Occupant;
import huglife.HugLifeUtils;
import java.awt.Color;
import java.util.Map;
import java.util.List;

/** 
 *  @author Hubert Pham
 */

public class Clorus extends Creature {
	
	/** red color. */
    private int r;
    /** green color. */
    private int g;
    /** blue color. */
    private int b;

	public Clorus(double e) {
		super("clorus");
		r = 0;
		g = 0;
		b = 0;
		energy = e;
	}

	public Clorus() {
        this(1);
    }


	public Color color() {
        r = 34;
        g = 0;
        b = 231;
        return color(r, g, b);
    }

    public void attack(Creature c) {
    	energy += c.energy();
    }

   
    public void move() {
        energy -= 0.03;
    }


    public void stay() {
        energy -= 0.01;
    }

    
    public Clorus replicate() {
        energy /= 2;
        return new Clorus(energy);
}
	public Action chooseAction(Map<Direction, Occupant> neighbors) {
        List nb = getNeighborsOfType(neighbors, "empty");
        List plips = getNeighborsOfType(neighbors, "plip");
        if (nb.size() == 0) {
            return new Action(Action.ActionType.STAY);
        }
        else if (plips.size() != 0) {
            return new Action(Action.ActionType.ATTACK, HugLifeUtils.randomEntry(plips));
        }
        else if (energy >= 1) {
        	return new Action(Action.ActionType.REPLICATE, HugLifeUtils.randomEntry(nb));
        }
        return new Action(Action.ActionType.MOVE, HugLifeUtils.randomEntry(nb));
    }
}

	