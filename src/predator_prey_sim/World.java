package predator_prey_sim;

import util.Helper;

import java.awt.*;
import java.util.ArrayList;


public class World {

	private int width, height;
	protected static Color canvasColor;
	protected static ArrayList<Predator> listPredators = new ArrayList<Predator>();
	protected static ArrayList<Prey> listPreys = new ArrayList<Prey>();

	/** 
	 * Generates numPrey prey and numPredator predators 
	 * randomly placed around the world. Subtract by 1 
	 * to avoid starting position being past the edge.
	**/

	private	int randXPosMax = PPSim.MAX_X-2;
	private	int randYPosMax = PPSim.MAX_Y-2;

	/** 
	 * Change nativity and death for simulation. 
	**/

	protected static double predatorReproductionRate = .0005; 
	protected static double predatorDeathRate = .0001; 
	protected static int predatorMaxChildren = 3;

	protected static double preyReproductionRate = .001;
	protected static double preyDeathRate = 0; 
	protected static int preyMaxChildren = 1;

	/**
	 * Create a new City and fill it with buildings and people.
	 * @param w width of city
	 * @param h height of city
	 * @param numPrey number of prey
	 * @param numPredator number of predators
	 */
	public World(int w, int h, int numPrey, int numPredator) {
		width = w;
		height = h;
		canvasColor = Helper.newRandColor();
		
		// Add Prey and Predators to the world.
		populate(numPrey, numPredator);
	}

	/**
	 * Generates numPrey random prey and numPredator random predators 
	 * distributed throughout the world.
	 * Prey must not be placed outside canvas!
	 *
	 * @param numPrey the number of prey to generate
	 * @param numPredator the number of predators to generate
	 */
	private void populate(int numPrey, int numPredators)
	{
		/** 
		 * For loop through the number of predators and prey and  
		 * randomly place them within the frame. 
		**/

		for (int i = 0; i < numPredators; i++) {
			Predator predator = new Predator(Helper.nextInt(randXPosMax), Helper.nextInt(randYPosMax), Color.red);
			listPredators.add(predator);
		}

		for (int i = 0; i < numPrey; i++) {
			Prey prey = new Prey(Helper.nextInt(randXPosMax), Helper.nextInt(randYPosMax), Helper.newRandColor());
			listPreys.add(prey);
		}
	}
	
	/**
	 * Updates the state of the world for a time step.
	 */
	public void update() {

		/**
		 * Check if predators or prey are born.
		 * Size of for loop should be static to
		 * limit reproduction to existing animals.
		**/

		int staticSizePredators = listPredators.size();
		int staticSizePrey = listPreys.size(); 

		for (int i = 0; i < staticSizePredators; i++) {
			Predator currentPredator = listPredators.get(i);
			if (currentPredator.animalReproduction()) {
				Predator predator = new Predator(Helper.nextInt(randXPosMax), Helper.nextInt(randYPosMax), Color.red);
				listPredators.add(predator); 
			}
		}

		for (int i = 0; i < staticSizePrey; i++) {
			Prey currentPrey = listPreys.get(i);
			if (currentPrey.animalReproduction()) {
				Prey prey = new Prey(Helper.nextInt(randXPosMax), Helper.nextInt(randYPosMax), currentPrey.preyMutation());
				listPreys.add(prey); 
			}
		}

		/**
		 * Check if predators or prey are dead 
		 * (for predators this is chance dictated by a
		 * static variable and for prey it is if a 
		 * predator is adjacent). 
		 * Check if prey and predator see each other.
		 * Size of for loop should be dynamic in
		 * case death is at position n or Arraylist.
		 * Otherwise move animal. 
		**/

		for (int i = 0; i < listPredators.size(); i++) { 
			Predator currentPredator = listPredators.get(i);
			if (currentPredator.predatorDeath()){
				listPredators.remove(i); 
				continue; 
			} else {
				for (int j = 0; j < listPreys.size(); j++) { 
					Prey currentPrey = listPreys.get(j); 
;					currentPredator.seePrey(currentPrey, 10.0);
				}
			}
		}	

		/** Color Similarity is equal to 10% difference.  
		**/

		for (int i = 0; i < listPreys.size(); i++) { 
			Prey currentPrey = listPreys.get(i); 
			for (int j = 0; j < listPredators.size(); j++) { 
				Predator currentPredator = listPredators.get(j);
				currentPrey.seePredator(currentPredator, 10.0);
				if (currentPrey.preyDeath(currentPredator.getX(), currentPredator.getY(), 10.0)) {
					listPreys.remove(i); 
				}
			}
		}

		/**
		 * Move the animals if all conditions have been met. 
		**/

		for (int i = 0; i < listPredators.size(); i++) { 
			listPredators.get(i).move();
		}

		for (int i = 0; i < listPreys.size(); i++) {
			listPreys.get(i).move();
		}
	}

	/**
	 * Draw all the predators and prey.
	 */

	public void draw(){

		/* Clear the screen */
		PPSim.dp.clear(canvasColor);

		/* Draw predators and prey */
		for (int i = 0; i < listPredators.size(); i++) {
			Predator currentPredator = listPredators.get(i); 
			PPSim.dp.drawSquare(currentPredator.getX(), currentPredator.getY(), currentPredator.getColor());		
		}

		for (int i = 0; i < listPreys.size(); i++) {
			Prey currentPrey = listPreys.get(i); 
			PPSim.dp.drawCircle(currentPrey.getX(), currentPrey.getY(), currentPrey.getColor());		
		}
	}
}
