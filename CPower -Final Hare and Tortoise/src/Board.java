
/**
 * The Board class brings together a set of instances of a Space object. The Arraylist created is used for players moving around the board.
 * It also includes methods that relate to the Arraylist of spaces, for example listing all the spaces on the board. 
 * 
 * @author Ciara Power
 * @version Final
 */
import java.util.ArrayList;

public class Board {
	private static ArrayList<Space> spaces;

	// constructor
	public Board() {
		spaces = new ArrayList<Space>();

	}

	// Board accessors ( returns the full arraylist of spaces) 
	public static ArrayList<Space> getSpaces() {
		return spaces;
	}

	// method to return a list of every space on the board, with info such as availability and name
	public static String listSpaces(Player player) {
		if (spaces.size() == 0) {
			return "No Spaces";
		} else {
			String listOfSpaces = "";
			for (int i = 0; i < spaces.size(); i++) {
				listOfSpaces = listOfSpaces + i + ": " + spaces.get(i).getName() + ", is this space taken? :"
						+ spaces.get(i).getTaken() + spaceCostInFront(player, i) + "\n";

			}
			return listOfSpaces;
		}
	}

	// method that tests to see if the space being listed is behind the current player.
	// The cost of moving to a space is only shown for spaces in front of the player, as they cannot move back (unless to Tortoise space, which costs nothing anyway)
	public static String spaceCostInFront(Player player, int i) {
		String spaceCostInFront = "";
		if (spaces.get(i).getPosition() > player.getPlayerSpace()) {
			spaceCostInFront = ("\n  > Cost to move to this space:"
					+ (int) ((((double) (spaces.get(i).getPosition() - player.getPlayerSpace()) + 1) / 2)    // maths formula for cost of move
							* (double) (spaces.get(i).getPosition() - player.getPlayerSpace())));
		}
		return spaceCostInFront;
	}

	// At the start of the game, this method is called from Driver to set up the board. 
	// A new Space object is created and added to the spaces Arraylist each time. 
	// These space names, and their order, are taken directly from the boardgame. 
	public void addSpace() {
		spaces.add(new Space("Start", 0));
		spaces.add(new Space("Hare", 1));
		spaces.add(new Space("Carrots", 2));
		spaces.add(new Space("Hare", 3));
		spaces.add(new Space("3rd", 4));
		spaces.add(new Space("Carrots", 5));
		spaces.add(new Space("Hare", 6));
		spaces.add(new Space("1st,5th,6th", 7));
		spaces.add(new Space("2nd", 8));
		spaces.add(new Space("4th", 9));
		spaces.add(new Space("Lettuce", 10));
		spaces.add(new Space("Tortoise", 11));
		spaces.add(new Space("3rd", 12));
		spaces.add(new Space("Carrots", 13));
		spaces.add(new Space("Hare", 14));
		spaces.add(new Space("Tortoise", 15));
		spaces.add(new Space("1st,5th,6th", 16));
		spaces.add(new Space("2nd", 17));
		spaces.add(new Space("4th", 18));
		spaces.add(new Space("Tortoise", 19));
		spaces.add(new Space("3rd", 20));
		spaces.add(new Space("Carrots", 21));
		spaces.add(new Space("Lettuce", 22));
		spaces.add(new Space("2nd", 23));
		spaces.add(new Space("Tortoise", 24));
		spaces.add(new Space("Hare", 25));
		spaces.add(new Space("Carrots", 26));
		spaces.add(new Space("4th", 27));
		spaces.add(new Space("3rd", 28));
		spaces.add(new Space("2nd", 29));
		spaces.add(new Space("Tortoise", 30));
		spaces.add(new Space("Hare", 31));
		spaces.add(new Space("1st,5th,6th", 32));
		spaces.add(new Space("Carrots", 33));
		spaces.add(new Space("Hare", 34));
		spaces.add(new Space("2nd", 35));
		spaces.add(new Space("3rd", 36));
		spaces.add(new Space("Tortoise", 37));
		spaces.add(new Space("Carrots", 38));
		spaces.add(new Space("Hare", 39));
		spaces.add(new Space("Carrots", 40));
		spaces.add(new Space("2nd", 41));
		spaces.add(new Space("Lettuce", 42));
		spaces.add(new Space("Tortoise", 43));
		spaces.add(new Space("3rd", 44));
		spaces.add(new Space("4th", 45));
		spaces.add(new Space("Hare", 46));
		spaces.add(new Space("2nd", 47));
		spaces.add(new Space("1st,5th,6th", 48));
		spaces.add(new Space("Carrots", 49));
		spaces.add(new Space("Tortoise", 50));
		spaces.add(new Space("Hare", 51));
		spaces.add(new Space("3rd", 52));
		spaces.add(new Space("2nd", 53));
		spaces.add(new Space("4th", 54));
		spaces.add(new Space("Carrots", 55));
		spaces.add(new Space("Tortoise", 56));
		spaces.add(new Space("Lettuce", 57));
		spaces.add(new Space("Hare", 58));
		spaces.add(new Space("Carrots", 59));
		spaces.add(new Space("1st,5th,6th", 60));
		spaces.add(new Space("Carrots", 61));
		spaces.add(new Space("Hare", 62));
		spaces.add(new Space("Carrots", 63));
		spaces.add(new Space("Finish", 64));
	}

}