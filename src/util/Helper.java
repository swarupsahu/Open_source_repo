package util;

import java.util.Random;
import java.awt.Color;
import java.lang.Math;

public class Helper {

	private static Random rand = null;

	/**
	 * Use the singleton design pattern to store
	 * the random number generator. You'll learn
	 * more about this later.
	 * @return
	 */
	
	private static Random getRand() {
		if(rand == null) {
			rand = new Random();
		}
		return rand;
	}
	/**
	 * Set the seed used by the random number generator.
	 * @param seed
	 */
	public static void setSeed(long seed) {
		rand = new Random(seed);
	}
	/**
	 * Get a random integer r, such that 0<= r < max.
	 */
	public static int nextInt(int max) {
		return getRand().nextInt(max);
	}

	/**
	 * Return a random double between 0 (inclusive) and 1 (exclusive).
	 */
	public static double nextDouble() {
		return getRand().nextDouble();
	}
	
	/**
	 * Return a random color.
	 */
	public static Color newRandColor(){
		return new Color(nextInt(255),nextInt(255),nextInt(255));
	}

	/**
	 * Calculate percent difference between R,G,and B and output true if 
	 * colors are similar and false if they are not.  
	**/

	public static boolean compareColorTo (Color c1, Color c2, double pct){

		int diffRed = Helper.abs(c1.getRed() - c2.getRed());
		int diffGreen = Helper.abs(c1.getGreen() - c2.getGreen());
		int diffBlue = Helper.abs(c1.getBlue() - c2.getBlue());

		float pctDiffRed   = (float)diffRed   / 255;
		float pctDiffGreen = (float)diffGreen / 255;
		float pctDiffBlue   = (float)diffBlue  / 255;

		float pctDiff = (pctDiffRed + pctDiffGreen + pctDiffBlue) / 3 * 100;

		/* They are similar true, else false.*/
	    if (pctDiff <= pct){
	        return true;
	    } else {
	        return false;
	    }
	 }

	/**
	 * Take absolute value.
	 */
	public static int abs(int number) {
		return Math.abs(number);
	}

}
