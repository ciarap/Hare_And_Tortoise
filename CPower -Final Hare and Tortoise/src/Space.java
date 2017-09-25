/**
 * The Space class sets out a space object, defining its name, number position,
 * and if it is taken by a player. Instances of this object are called in an Arraylist in board, and are used 
 * throughout the game, specifically when players are moving around the board.
 * 
 * @author Ciara Power
 * @version Final
 */


public class Space {
	private String name;
	private boolean taken;
	private int position;

	// constructor for a Space object , takes in name and position as parameter. Taken is always false at the start of the game. 
	public Space(String name, int position) {
		this.name = name;
		taken = false;
		this.position = position;
	}

	// Space accessors
	public boolean getTaken() {
		return taken;
	}

	public String getName() {
		return name;
	}

	public int getPosition() {
		return position;
	}

	// Space mutators
	public void setPosition(int position) {
		this.position = position;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void setTaken(boolean taken) {
		this.taken = taken;
	}
}
