package predator_prey_sim;
import util.Helper;
import java.util.ArrayList;

import java.awt.Color;
import java.awt.Container;

import javax.swing.JFrame;

public class Prey extends Animal { 
	protected static int preyCount;
	private int moveSteps = 1;

	public Prey(int xPos, int yPos, Color animalColor) { 
		// Set random position.
		super(xPos, yPos, animalColor);
		preyCount++;
	}

	public Color setPreyColor(Color color) { 
		return (color);
	}

	public void seePredator(Animal predator, double pct) { 

		/** 0 indicates north, 1 indicates east, 2 indicates south, 3 indicates west 
		Check each of these directions to see if there is a predator. The prey cannot 
		see camoflauged animals; if the colors are similar than exit out of this method. 
		The first time you see a predator, move two steps, then one.**/
		
		if (Helper.compareColorTo(World.canvasColor, predator.getColor(), pct)) {
			return;
		} else if ((yPos - predator.getY()) <= 10 & (yPos - predator.getY()) > 0 & predator.getX() == xPos) {
			animalDirection = 2;
			moveSteps = 2;
		} else if ((predator.getX() - xPos) <= 10 & (predator.getX() - xPos) > 0 & predator.getY() == yPos) { 
			animalDirection = 3; 
			moveSteps = 2;
		} else if ((predator.getY() - yPos) <= 10 & (predator.getY() - yPos) > 0 & predator.getX() == xPos) {
			animalDirection = 0;
			moveSteps = 2;
		} else if ((xPos - predator.getX()) <= 10 & (xPos - predator.getX()) > 0 & predator.getY() == yPos) {
			animalDirection = 1; 
			moveSteps = 2;
		} 
		// 5 percent chance of changing direction. If you can't see prey. 
		else if (Helper.nextDouble() <= .1) {
			animalDirection = Helper.nextInt(4);
		} else {
			;
		}
	}

	public int getPreyCount() { 
		return(preyCount);
	}

	public void move () { 

		ArrayList<Integer> direction = new ArrayList<Integer>();
		for (int i = 0; i < 4; i++) {
			direction.add(i);
		}
		// Handling case of running away from predator without going past bounds
		if ((xPos + moveSteps) > PPSim.MAX_X - 2 || (yPos + moveSteps) > PPSim.MAX_X - 2 || (xPos - moveSteps) < 0 || (yPos - moveSteps) < 0) {
			moveSteps = 1;
		}
		/** 0 indicates north, 1 indicates east, 2 indicates south, 3 indicates west 
		If the animal reaches the edge then turn in the opposite direction and keep going **/		
		if (animalDirection == 0 && yPos > 0) {			
			yPos -= moveSteps; 
		} else if (animalDirection == 1 && xPos < PPSim.MAX_X - 2) {
			xPos += moveSteps; 
		} else if (animalDirection == 2 && yPos < PPSim.MAX_Y - 2) {
			yPos += moveSteps;
		} else if (animalDirection == 3 && xPos > 0) {
			xPos -= moveSteps;
		} else if (yPos == 0) { 
			yPos += moveSteps; 
			direction.remove(0);
			animalDirection = direction.get(Helper.nextInt(3));
		} else if (yPos == PPSim.MAX_Y - 2) { 
			yPos -= moveSteps; 
			direction.remove(2);
			animalDirection = direction.get(Helper.nextInt(3));
		} else if (xPos == 0) { 
			xPos += moveSteps;
			direction.remove(3);
			animalDirection = direction.get(Helper.nextInt(3));
		} else if (xPos == PPSim.MAX_X - 2) { 
			xPos -= moveSteps;
			direction.remove(1);
			animalDirection = direction.get(Helper.nextInt(3));
		} 
		moveSteps = 1;
	}

	/**
	 * In the base case of a Prey's death. It will always 
	 * die if next to a Predator, but not if it is camoflauged.
	**/
	public boolean preyDeath (int predatorXPos, int predatorYPos, double pct) {

		/* If they are similar colors than outputs true. Then the prey should not die, 
		hence false. */
		if (Helper.compareColorTo(World.canvasColor, this.getColor(), pct)) {
			return(false);
		} else if (predatorYPos == yPos & Helper.abs(predatorXPos - xPos) <= 2) { 
			return(true);
		} else if (predatorXPos == xPos & Helper.abs(predatorYPos - yPos) <= 2) { 
			return(true); 
		} else { 
			return(false);
		}
	}

	public boolean animalReproduction () { 			
		if ((Helper.nextDouble() <= World.preyReproductionRate) & (numChildren <= World.preyMaxChildren)) {
			numChildren ++;
			return(true); 
		} else { 
			return(false);
		}
	}

	public Color preyMutation () { 
		if(Helper.nextDouble() <= .1) { 
			return(Helper.newRandColor());
		} else { 
			return(this.animalColor);
		}
	}
}

// Must add mutation 