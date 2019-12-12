package predator_prey_sim;
import util.Helper;

import java.awt.Color;
import java.awt.Container;

import javax.swing.JFrame;

public class Predator extends Animal { 
	static int predatorCount;

	public Predator(int xPos, int yPos, Color animalColor) { 
		// Set random position.
		super(xPos, yPos, animalColor);
		predatorCount++;
	}

	public Color setPredatorColor(Color color) { 
		return (color);
	}

	public void seePrey(Animal prey, double pct) { 

		/** 0 indicates north, 1 indicates east, 2 indicates south, 3 indicates west 
		Check each of these directions to see if there is prey. The predator cannot 
		see camoflauged animals; if the colors are similar than exit out of this method. **/

		if (Helper.compareColorTo(World.canvasColor, prey.getColor(), pct)) {
			return;
		} else if ((yPos - prey.getY()) <= 15 & (yPos - prey.getY()) > 0 & prey.getX() == xPos) {
			animalDirection = 0; 
		} else if ((prey.getX() - xPos) <= 15 & (prey.getX() - xPos) > 0 & prey.getY() == yPos) { 
			animalDirection = 1; 
		} else if ((prey.getY() - yPos) <= 15 & (prey.getY() - yPos) > 0 & prey.getX() == xPos) {
			animalDirection = 2; 
		} else if ((xPos - prey.getX()) <= 15 & (xPos - prey.getX()) > 0 & prey.getY() == yPos) {
			animalDirection = 3; 
		} 
		// 5 percent chance of changing direction. If you can't see prey. 
		else if (Helper.nextDouble() <= .05) {
			animalDirection = Helper.nextInt(4);
		} else {
			;
		}
	}

	public int getPredatorCount() { 
		return(predatorCount);
	}

	// Move method to update position of Predator.
	public void move () { 
		/** 0 indicates north, 1 indicates east, 2 indicates south, 3 indicates west 
		If the animal reaches the edge then turn in the opposite direction and keep going **/		
		if (animalDirection == 0 && yPos > 0) {			
			yPos -= 1; 
		} else if (animalDirection == 1 && xPos < PPSim.MAX_X - 2) {
			xPos += 1; 
		} else if (animalDirection == 2 && yPos < PPSim.MAX_Y - 2) {
			yPos += 1;
		} else if (animalDirection == 3 && xPos > 0) {
			xPos -= 1;
		} else if (yPos == 0) { 
			yPos += 1; 
			animalDirection = 2;
		} else if (yPos == PPSim.MAX_Y - 2) { 
			yPos -= 1; 
			animalDirection = 0;
		} else if (xPos == 0) { 
			xPos += 1;
			animalDirection = 1;
		} else if (xPos == PPSim.MAX_X - 2) { 
			xPos -= 1;
			animalDirection = 3;
		} 
	}

	public boolean predatorDeath () { 								
		if (Helper.nextDouble() <= World.predatorDeathRate) {
			return(true); 
		} else { 
			return(false);
		}
	}

	public boolean animalReproduction () { 
			
		if ((Helper.nextDouble() <= World.predatorReproductionRate) & (numChildren <= World.predatorMaxChildren)) {
			numChildren ++;
			return(true); 
		} else { 
			return(false);
		}
	}
}