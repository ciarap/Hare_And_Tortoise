/**
 * Player class defines what we know about a player of the game, how many carrot
 * and lettuce cards they have and the removal of carrot cards per spaces moved,
 * this class also sets whether a player has missed a go or quit.
 * 
 * @author Denise Doyle
 * @version Final
 */
public class Player {
	private double carrotCards;
	private int lettuceCards;
	private String playerName;
	private int playerNumber;
	private int playerSpace;
	private int carrotsPaid;
	private boolean missGo;
	private boolean playerQuit;

	// Setting up player constructor

	public Player(String playerName, int playerNumber) {
		this.playerName = playerName;
		this.playerNumber = playerNumber;
		this.carrotCards = 65;
		this.lettuceCards = 3;
		this.playerSpace = 0;
		this.carrotsPaid = 0;
		this.missGo = false;
		this.playerQuit = false;
	}

	public Player() {
	}

	// Player accessors

	public String getPlayerName() {
		return playerName;
	}

	public int getPlayerNumber() {
		return playerNumber;
	}

	public boolean getMissGo() {
		return missGo;
	}

	public boolean getPlayerQuit() {
		return playerQuit;
	}

	// Player mutators

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public void setPlayerNumber(int playerNumber) {
		this.playerNumber = playerNumber;
	}

	public void setMissGo(boolean missGo) {
		this.missGo = missGo;
	}

	public void setPlayerQuit(boolean playerQuit) {
		this.playerQuit = playerQuit;
	}

	// Player space(starts at 0) and toString

	public String toString() {
		return "Player " + playerNumber + ": " + playerName;
	}

	public int getPlayerSpace() {
		return playerSpace;
	}

	public void setPlayerSpace(int space) {
		this.playerSpace = space;
	}

	// Return player to start space and reset carrot cards to original holding
	public void resetPlayer() {
		this.carrotCards = 65;
		this.playerSpace = 0;

	}

	// Carrot and Lettuce cards

	public double getCarrotCards() {
		return carrotCards;
	}

	public double getCarrotsPaid() {
		return carrotsPaid;
	}

	public int getLettuceCards() {
		return lettuceCards;
	}

	public void setLettuceCards(int lettuceCards) {
		this.lettuceCards = lettuceCards;
	}

	// Using double to calculate removal of carrot cards as some calculations
	// result in decimal points, converted back to int when being shown to the
	// player
	public void setCarrotCards(double carrotCards) {
		this.carrotCards = carrotCards;
	}

	public void addCarrotCards(int carrots) {
		carrotCards += carrots;
	}

	// Removing carrot cards from player for spaces moved
	public void carrotPatch(double doubleMoveBy) {
		carrotsPaid = (int) (((doubleMoveBy + 1) / 2) * doubleMoveBy);
		this.carrotCards = carrotCards - carrotsPaid;
	}
}
