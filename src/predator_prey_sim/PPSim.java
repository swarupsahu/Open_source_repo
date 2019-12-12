package predator_prey_sim;

import util.DotPanel;
import util.Helper;

import java.awt.Color;
import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/*
 * You must add a way to represent humans.  When there is not a zombie apocalypse occurring, humans
 * should follow these simple rules:
 * 		if (1 in 10 chance):
 * 			turn to face a random direction (up/down/left/right)
 * 		Move in the current direction one space if not blocked by a wall
 *
 * We will add additional rules for dealing with sighting or running into zombies later.
 */

public class PPSim extends JFrame {

	private static final long serialVersionUID = -5176170979783243427L;

	/** The Dot Panel object you will draw to */
	protected static DotPanel dp;

	/* Define constants using static final variables */
	public static final int MAX_X = 100;
	public static final int MAX_Y = 100;
	public static final int DOT_SIZE = 6;
	private static final int NUM_PREY = 2;
	private static final int NUM_PREDATORS = 1;

	/*
	 * This fills the frame with a "DotPanel", a type of drawing canvas that
	 * allows you to easily draw squares for predators and circles for prey
	 * to the screen.
	 */
	public PPSim() {

		/* Add mouse click GUI */
		class FrameMouseListener implements MouseListener {

			public void mouseClicked(MouseEvent m) {
				int x=m.getX()/DOT_SIZE;
				int y=m.getY()/DOT_SIZE;
				Prey prey = new Prey(x, y, Helper.newRandColor());
				World.listPreys.add(prey);
			}
			// Empty methods - to complete interface.
			public void mouseEntered(MouseEvent m) {}
			public void mouseExited(MouseEvent m) {}
			public void mousePressed(MouseEvent m) {}
			public void mouseReleased(MouseEvent m) {}
		}

		class FrameKeyBoardListener implements KeyListener {
		    public void keyPressed(KeyEvent key){ 
	         	if(key.getKeyCode()==KeyEvent.VK_SPACE){
					World.canvasColor = Helper.newRandColor();
	         	} else if (key.getKeyCode() == KeyEvent.VK_ENTER) {
	         		//World ppworld = new World(MAX_X, MAX_Y, 0, 0);
					World.listPredators.clear();
					World.listPreys.clear();
					World ppworld = new World(MAX_X, MAX_Y, NUM_PREY, NUM_PREDATORS);
	         	}
    		}

    		// Empty methods - to complete interface.
			public void keyReleased(KeyEvent key) {}
			public void keyTyped(KeyEvent key) {}
    	}

    	JTextField keyText = new JTextField(20);

		this.setSize(MAX_X * DOT_SIZE, MAX_Y * DOT_SIZE);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setTitle("Predator Prey World");

		/* Create and set the size of the panel */
		dp = new DotPanel(MAX_X, MAX_Y, DOT_SIZE);

		/* Add the panel to the frame */
		Container cPane = this.getContentPane();
		cPane.add(dp);

		// Add to frame.
		cPane.addMouseListener(new FrameMouseListener());
		cPane.add(keyText, BorderLayout.NORTH);
		keyText.addKeyListener(new FrameKeyBoardListener());


		/* Initialize the DotPanel canvas:
		 * You CANNOT draw to the panel BEFORE this code is called.
		 * You CANNOT add new widgets to the frame AFTER this is called.
		 */
		this.pack();
		dp.init();
		dp.clear();
		dp.setPenColor(Color.red);
		this.setVisible(true);

		/* Create our city */
		World ppworld = new World(MAX_X, MAX_Y, NUM_PREY, NUM_PREDATORS);

		/* This is the Run Loop (aka "simulation loop" or "game loop")
		 * It will loop forever, first updating the state of the world
		 * (e.g., having humans take a single step) and then it will
		 * draw the newly updated simulation. Since we don't want
		 * the simulation to run too fast for us to see, it will sleep
		 * after repainting the screen. Currently it sleeps for
		 * 33 milliseconds, so the program will update at about 30 frames
		 * per second.
		 */
		while(true)
		{
			// Run update rules for world and everything in it
			ppworld.update();
			// Draw to screen and then refresh
			ppworld.draw();
			dp.repaintAndSleep(60);

		}
	}

	public static void main(String[] args) {
		/* Create a new GUI window  */
		new PPSim();
	}

}

