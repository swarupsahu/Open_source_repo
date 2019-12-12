package predator_prey_sim;
import util.Helper;

import java.awt.Color;
import java.awt.Container;

import javax.swing.JFrame;


public abstract class Animal { 

	// Data
	protected int xPos;
	protected int yPos; 
	protected Color animalColor; 
	protected int numChildren = 0; 

	// Set direction to be random initially. Change once 
	// the move method is called. 
	protected int animalDirection = Helper.nextInt(4); 

	// Constructor
	public Animal(int xPos, int yPos, Color animalColor) { 

		this.xPos = xPos; 
		this.yPos = yPos;
		this.animalColor = animalColor;

	}

	public abstract void move ();
	// public abstract boolean animalDeath (); 
	public abstract boolean animalReproduction ();
	//public abstract boolean seeAnimal (Animal animal, double pct);

	// Methods to get positions.
	public int getX() {return(xPos);}
	public int getY() {return(yPos);}
	public Color getColor() {return(animalColor);}
	public String toString() { return("X position: " + xPos + " Y position: " + yPos + " Direction: " + animalDirection);}
}
