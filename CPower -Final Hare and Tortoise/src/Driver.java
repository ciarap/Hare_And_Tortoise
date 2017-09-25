
/**
 * The Driver class holds most of the methods used in the running of this game. 
 * It includes the main menu and game menu, all player movements, and all space actions. 
 * 
 * @author Ciara Power, Denise Doyle, Dylan Wall
 * @version Final
 */

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Driver {
	private Scanner input;
	private Board board;
	private Player player;
	
	private int moveBy;     // this variable will be used to store the amount of spaces a player chooses to move each time.
	private boolean taken;  // variable used to hold the boolean value for if a space in question is taken or not.
	private int finishedCounter = 0;  // variable used to count how many players have already reached the finish line.
	private int tempSpaceNumber;  // variable to store the number of a space the player is looking to move to.
	private String tempSpaceName;  //  variable to store the name of a space the player is looking to move to.
	private Space tempSpace;  // variable to store the space object the player is looking to move to.

	private ArrayList<Player> players;

	public Driver() {
		input = new Scanner(System.in); // set up scanner variable
		board = new Board();
		players = new ArrayList<Player>();
		board.addSpace();    // add spaces to the board before the game starts
	}

	public static void main(String args[]) {
		Driver app = new Driver();
		app.runMenu();      // begins the game and starts the main menu

	}

	public void game() throws Exception {      // method called when the 'start game' option is selected in main menu

		for (Player player : players) {        // loop for players arraylist, to give each player a turn
			this.player = player;              // sets the global variable player to the current player from the loop , to use it in other methods throughout driver
			runGameMenu();                    // runs the game menu for the current player

		}
	}

	// Menus and switch statements

	private int mainMenu() {
		System.out.println("Main Menu");
		System.out.println("------------");
		System.out.println("  1) Add a player");
		System.out.println("  2) List the players");
		System.out.println("  3) Start Game");
		System.out.println("  4) Rules of the Game");
		System.out.println("  0) Exit");
		System.out.print("==>> ");

		int option = input.nextInt();
		System.out.println("------------");
		return option;
	}

	private void runMenu() {       // called when the game is run at first ( from main method )
		System.out.println("--------------------------------------------------------");
		System.out.println("\t\tWelcome to Hare and Tortoise");
		System.out.println("--------------------------------------------------------");
		int option = mainMenu();     // calls the mainMenu method to show options and return the players choice
		while (option != 0) {       // when option = 0 ,  the game quits , this condition ensures menu loops until quit game is selected

			switch (option) {
			case 1:
				try {
					addPlayer();     // calls method which adds player
				} catch (Exception e) {
					System.out.println("Error adding player: " + e);     // catches errors on adding players
				}
				break;
			case 2:
				System.out.println(listPlayers());      // calls method which lists players, and prints the string to console 
				break;
			case 3:
				if (players.size() < 2 && finishedCounter == 0) {       // if not enough players are added in at the start an error shows, finishedCounter tested, 
		                                                                //  because only want to give error if not enough players at start of game when no player has finished yet.
					System.out.println(
							"Sorry, you must have at least 2 players to start the game!\nPlease choose another option from the menu");
					runMenu();    // calls menu again to give option to add more players

				} else {
					try {
						game();     // if enough players, game method called to start the game 
					} catch (Exception e) {
						System.out.println("Error starting game: " + e);    // error caught on starting the game
					}
				}
				break;
			case 4:
				rules();     // calls method to print basic rules of the game
				break;

			default:
				System.out.println("Invalid option entered: " + option);      
				runMenu();             // calls menu again if invalid option entered
				break;
			}

			System.out.println("\nPress any key to continue...");    
			input.nextLine();
			input.nextLine();
			if (option != 3) {              // if option is 3(start game) , we want option to stay equal to 3, so menu is looped back to the game option
				option = mainMenu();        // if start game hasn't been chosen yet, we want to get the next choice from player and show main menu again
			}

		}

		System.out.println("Exiting... bye");
		System.exit(0);
	}

	private int gameMenu() {     // called from runGameMenu method which runs for each player after the game has started, to get player choice
		System.out.println("--------------------------------------------------------");
		System.out.println("This is " + player.getPlayerName() + "'s Hare and Tortoise gameplay menu");
		System.out.println("--------------------------------------------------------");
		System.out.println("  1) Move your player");
		System.out.println("  2) List all spaces and their availability");
		System.out.println("  3) Miss a go (only if you are on a carrot space)");
		System.out.println("  4) See your stats");
		System.out.println("  5) View race card");
		System.out.println("  6) Rules of the Game");
		System.out.println("  0) Quit the game");
		System.out.print("==>> ");

		int option = input.nextInt();
		System.out.println("------------");
		return option;

	}

	private void runGameMenu() {        // run for each player after game has started
		String spaceName = Board.getSpaces().get(player.getPlayerSpace()).getName();   // gets the name of the players current space
		if (spaceName == "Lettuce" && player.getMissGo()) {       // if space is lettuce and the player is due to miss a go , the lettuceAction method is called
			lettuceAction();
		} else if (spaceName == "Carrots" && player.getMissGo()) {    // if space is carrots and the player is due to miss a go , the carrotAction method is called
			carrotAction();
		} else if ((spaceName == "1st,5th,6th" || spaceName == "2nd" || spaceName == "3rd" || spaceName == "4th")  && player.getMissGo()) { 
			                                                          // if space is 1st,5th,6th / 2nd / 3rd / 4th and the player is due to miss a go , the numberAction method is called
			numberAction();
		} else if (spaceName == "Hare" && player.getMissGo()) {   // if space is hare and the player is due to miss a go , the player is told they are missing this go 
			System.out.println(player.getPlayerName()
					+ ",as a result of your last move,you miss a turn! Your position has not changed\n");
			player.setMissGo(false);     // players missGo variable is set back to false, as they have missed the go
		} else {
			int option = gameMenu();        //  gets player choice from game menu
			if (option != 0) {

				switch (option) {
				case 1:
					try {
						playersMove();      // move player method 
					} catch (Exception e) {
						System.out.println("Error moving player: " + e);
					}
					break;

				case 2:
					allSpaces();       // prints spaces list 
					System.out.println("\nPress any key to return to the gameplay menu...");
					input.nextLine();
					input.nextLine();
					runGameMenu();
					break;

				case 3:
					missGo();    // gets method to test if the player is allowed miss a go ( only on carrot space)
					break;

				case 4:  // prints players balances 
					System.out.println("Your current carrot balance is: " + (int) player.getCarrotCards());    
					System.out.println("Your current lettuce balance is: " + player.getLettuceCards());
					System.out.println("\nPress any key to return to the gameplay menu...");
					input.nextLine();
					input.nextLine();
					runGameMenu();
					break;

				case 5:
					for (double i = 1; i < 41; i++) {      // shows race card ( which only goes up to moving 40 spaces) 
						System.out.print("Squares moved: " + (int) i + " || Cost in carrots: "
								+ (int) (((i + 1) / 2) * i) + "\n");
					}
					System.out.println("\nPress any key to return to the gameplay menu...");
					input.nextLine();
					input.nextLine();
					runGameMenu();
					break;
				case 6:
					rules();    // prints game rules
					System.out.println("\nPress any key to return to the gameplay menu...");
					input.nextLine();
					input.nextLine();
					runGameMenu();
					break;

				default:
					System.out.println("Invalid option entered: " + option);
					runGameMenu();
					break;
				}

				System.out.println("\nPress any key to continue...");
				input.nextLine();
				input.nextLine();

			} else {

				System.out.println("Exiting... bye");
				System.exit(0);

			}
		}
	}

	// Player methods

	public void addPlayer() throws Exception {
		if (players.size() >= 6) {    // max 6 players allowed
			System.out.println(
					"Sorry, you can't add any more players, 6 players is the maximum\nPlease choose another option from the menu!");
			runMenu();    // if 6 players already added, menu is brought back up 
		} else {    // if less than 6 players , add another one
			System.out.print("Please enter the player name: ");
			String name = input.nextLine();    // 2x input.nextLine() incase last input was an int
			name = input.nextLine();
			System.out.print("Please enter the player number: ");
			int number = input.nextInt();
			players.add(new Player(name, number));    // adds the name and number as parameters to make a new player, and add it to players arraylist
		}
	}

	public String listPlayers() {     // lists players in game
		String listOfPlayers = "";
		int index = 1;       // lists the players in order 1: ,2: ,3:  etc.  
		for (Player player : players) {
			listOfPlayers = listOfPlayers + index + ": " + player.toString() + "\n";
			index++;
		}
		if (listOfPlayers.equals("")) {    // if there are no players in the game
			return "No players";
		} else {
			return listOfPlayers;     // if there are players, return the string constructed above
		}

	}

	// Moving around the board

	public void playersMove() {
		Space space = ((Board.getSpaces()).get(player.getPlayerSpace()));   // variable space which holds the current players current space
		String spaceName = space.getName();  // name of current space
		int spaceNumber = space.getPosition(); // number of current space 
		System.out.println(player.getPlayerName() + ", you are currently on space " + spaceNumber + ": " + spaceName); // prints current position to player
		setUpTempSpace(); // this calls method to get info on space the player wants to move to and validates it ( tempSpaceName, tempSpaceNumber, tempSpace, taken ) 
		space.setTaken(false);     // current space is now not taken 
		player.setPlayerSpace(tempSpaceNumber);   // player space number is updated to new space number
		tempSpace.setTaken(true);    // new space set to taken
		System.out.println(player.getPlayerName() + ", your new space is " + tempSpaceNumber + ":" + tempSpaceName); // prints new space details to player
		player.carrotPatch((double) moveBy);  // calls method in player class to make payment for the move 
		checkSpace(tempSpaceName);   // calls method to do actions associated with the name of the space the player has just landed on 
	}

	public void setUpTempSpace() {       // gets players choice of space to move to and sets it as temp variables
		tempSpaceNumber = getMoveChoice();   // this method called gets the amount of spaces the player wants to move
		tempSpace = ((Board.getSpaces()).get(tempSpaceNumber));   
		tempSpaceName = tempSpace.getName();
		taken = tempSpace.getTaken();
		// if statements below all check to see if move is valid, if not, this method is called again until valid move chosen
		if (moveBy==0){
			System.out.println("You must choose an amount of spaces to move, 0 is an invalid choice");
			setUpTempSpace();
		}
		else if (moveBy< 0 && tempSpaceName!="Tortoise"){
			System.out.println("Sorry, you must choose to move forward, unless you are moving back to a Tortoise space.\nPlease choose again!");
			setUpTempSpace();
		}
		else if (moveBy>0 && tempSpaceName=="Tortoise"){  // test to see if choice of spaces to move is positive ( to move forward on the board) and tortoise space
				System.out.println("Sorry, you cant move forward to a Tortoise space, choose again"); //(error because this is an invalid move)
				setUpTempSpace();
			}
		else if (taken) {    // if space is already taken, we must set retry to true so another space choice is taken when this while loop loops
			System.out.println("Sorry, there is a player already on this space, choose again");
			setUpTempSpace();
		}
		else if (tempSpaceName == "Tortoise") {    // if wanting to move to a tortoise space 
		    boolean notClosestTortoise = false;  // boolean value for if the tortoise space is the closest one to the player currently or not
			for (int i = tempSpaceNumber + 1; i < player.getPlayerSpace(); i++) {  // goes through each space between tortoise space chosen and player current space
				if (((Board.getSpaces().get(i).getName() == "Tortoise"))) {    // if any of the space names is tortoise, the original tortoise space chosen is not the closest to player
					notClosestTortoise = true;     // set to true because there is a closer tortoise space than the one chosen 
				}
			}
		    if (notClosestTortoise) {   // test if negative move number ( to go backwards on board) and if the chosen tortoise space is not the closest ( from boolean in loop above) 
				System.out.println("Sorry, this isn't the closest tortoise space to your position, choose again");
				setUpTempSpace();
			}

		} else if (tempSpaceName == "Lettuce") {    
			if (player.getLettuceCards() < 1) {     // tests if enough lettuce cards to move to lettuce space 
				System.out.println("Sorry, you cant move to a Lettuce space if you have no lettuce, choose again");
				setUpTempSpace();
			}
		}
			
		
	}

	public int getMoveChoice() {    // gets the players choice for amount of spaces they want to move
		System.out.print("How many spaces do you want to move?");
		moveBy = input.nextInt();    // global variable moveBy to store amount of spaces to move
		double doubleMoveBy = (double) moveBy;     // needs to be double for calculations below so set to double doubleMoveBy variable
		if (Board.getSpaces().get(player.getPlayerSpace()+moveBy).getName() == "Tortoise") {   // moving back to tortoise space costs no carrots
			System.out.println("This move to a Tortoise space will cost you no carrots if you move here!");

		} else if (player.getCarrotCards() == 0) {    // no carrot cards left , player must begin again
			System.out.println("Sorry, you have no carrot cards left to make a move, you must go back to the start");
			moveBy = 0;   // moved no spaces
			player.resetPlayer();
		} else if (player.getCarrotCards() < (((doubleMoveBy + 1) / 2) * doubleMoveBy)) {   // formula to check if not enough carrots for move cost
			System.out.println("You do not have enough carrots to make this move!");
			getMoveChoice();   // calls method gets another choice for move
		}
		return player.getPlayerSpace() + moveBy;  // returns new space number
	}

	public int testPositionInRace() {       // tests position ( if third, returns '3' , if 1st , returns '1'  etc.) 
		int playersBehind = 0;     // counter for how many players are behind 
		for (int i = 0; i < player.getPlayerSpace(); i++) {    // for loop to check each space behind the current player
			if (Board.getSpaces().get(i).getTaken()) {    // if space is taken, that is one player that is behind current player
				playersBehind++;     // counter increments to count the player behind you 
			}
		}
		for (Player player : players) {      // for each player in the game 
			if (Board.getSpaces().get(player.getPlayerSpace()).getName() == "Start") {  // checks to see if that player is on start
				playersBehind++;   // increments as if a player is on start they are behind you 
				                    // this player wasn't counted in previous loop as start space is never set to 'taken' as many players start there
			}
		}
		return players.size() - playersBehind;    // returns position 

	}

	public void missGo() {     // called when missGo option is selected  in the game menu
		if (Board.getSpaces().get(player.getPlayerSpace()).getName() != "Carrots") {      // if player isnt on a carrot space they cant miss a go
			System.out.println("Sorry, you must be on a carrot space to choose to miss a go, please choose again");
			System.out.println("\nPress any key to return to the gameplay menu...");
			input.nextLine();
			input.nextLine();
			runGameMenu();    // menu run again to get another player choice in game menu
		} else {    // player is on carrot space , so is allowed miss a go 
			System.out.println("--------------------------");
			System.out.println("CARROT SPACE MISS A GO MENU:");
			System.out.println("--------------------------");
			System.out.println("Please choose one of the following:");   // Options for missing a go while on carrot space
			System.out.println("1) Pay 10 carrots");
			System.out.println("2) Receive 10 carrots");
			System.out.println("--------------------------");
			int option = input.nextInt();
			if (option == 1) {
				player.setCarrotCards(player.getCarrotCards() - 10);
				System.out.println("Your new carrot card balance is: " + (int) player.getCarrotCards());

			} else if (option==2) {
				player.setCarrotCards(player.getCarrotCards() + 10);
				System.out.println("Your new carrot card balance is: " + (int) player.getCarrotCards());
			}
			else {   // if 1 or 2 isn't chosen, carrot space miss go menu is shown again 
				System.out.println("Invalid option chosen, please choose again");
				missGo();
			}
			
		}
	}

	public void checkSpace(String spaceName) {     // method which receives name of the player's new space and does action associated with that space 
		switch (spaceName) {
		case "Start":
			System.out.println("You have restarted to the START space");
			break;
		case "Hare":
			hareCardNumber();
			break;

		case "Carrots":
			System.out.println("Nothing happens on a carrot space until your next go!");
			player.setMissGo(true);     // the player has the option to miss their next go 
			break;

		case "3rd":
			System.out.println("Nothing happens on a number space until your next go!");
			player.setMissGo(true);    // player must miss the next go
			break;

		case "1st,5th,6th":
			System.out.println("Nothing happens on a number space until your next go!");
			player.setMissGo(true);  // player must miss the next go
			break;

		case "2nd":
			System.out.println("Nothing happens on a number space until your next go!");
			player.setMissGo(true); // player must miss the next go
			break;

		case "4th":
			System.out.println("Nothing happens on a number space until your next go!");
			player.setMissGo(true); // player must miss the next go
			break;

		case "Lettuce":
			System.out.println("Nothing happens on a lettuce space until your next go!");
			player.setMissGo(true); // player must miss the next go
			break;

		case "Tortoise":
			tortoiseAction();
			break;

		case "Finish":
			finishAction();
			break;

		default:
			System.out.println("Invalid space name");
			break;
		}
	}

	public void allSpaces() {    // method to print all spaces, calling the method in board which returns a string
		System.out.println(Board.listSpaces(player));

	}

	// Space actions

	public void lettuceAction() {    // when on a lettuce space
		String positionName;   
		player.setLettuceCards(player.getLettuceCards() - 1);    // takes one lettuce card from player
		int position = testPositionInRace();    // gets position in the race 
		player.addCarrotCards(position * 10);   // adds position*10 carrot cards to players carrots , done in a method in player 
		if (position == 1) {  
			positionName = "1st";
		} else if (position == 2) {
			positionName = "2nd";
		} else if (position == 3) {
			positionName = "3rd";
		} else {
			positionName = Integer.toString(position) + "th";    // 4th,5th,6th 
		}
		System.out.println(player.getPlayerName()+ ", you landed on a lettuce space on your last move, now you must miss a go!\nYou are coming "
				+ positionName + ", so you receive " + position * 10 + " carrots, giving you a total of "
				+ (int) player.getCarrotCards() + "!\nOne lettuce was chewed, so you now have "
				+ player.getLettuceCards() + " lettuce");    // prints out info to player about what is happening on this space
		player.setMissGo(false);   // player doesn't miss next go, as they have missed this go 

	}

	public void carrotAction() {
		System.out.println(player.getPlayerName()
				+ ", you are on a carrot space, you can choose to move normally, or to miss a go!\nIf you choose to miss a go, you must decide to either pay or recieve 10 carrot cards");
		player.setMissGo(false); // sets this player's miss go variable back to false
		runGameMenu(); 
	}

	public void numberAction() {  ///if space name and player position in game match then "action"
		int position = testPositionInRace();
		if (((Board.getSpaces().get(player.getPlayerSpace()).getName() == "1st,5th,6th") && (position == 1))) {
			System.out.println(player.getPlayerName()
					+ " , you are on the 1st, 5th and 6th place square and you are in 1st position, collect 10 carrots");
			player.setCarrotCards(player.getCarrotCards() + 10);
			System.out.println("Your new carrot card balance is: " + (int) player.getCarrotCards());

		} else if (((Board.getSpaces().get(player.getPlayerSpace()).getName() == "2nd") && (position == 2))) {
			System.out.println(player.getPlayerName()
					+ " , you are on the 2nd place square and you are in 2nd position, collect 20 carrots");
			player.setCarrotCards(player.getCarrotCards() + 20);
			System.out.println("Your new carrot card balance is: " + (int) player.getCarrotCards());

		} else if (((Board.getSpaces().get(player.getPlayerSpace()).getName() == "3rd") && (position == 3))) {
			System.out.println(player.getPlayerName()
					+ " , you are on the 3rd place square and you are in 3rd position, collect 30 carrots");
			player.setCarrotCards(player.getCarrotCards() + 30);
			System.out.println("Your new carrot card balance is: " + (int) player.getCarrotCards());

		} else if (((Board.getSpaces().get(player.getPlayerSpace()).getName() == "4th") && (position == 4))) {
			System.out.println(player.getPlayerName()
					+ " , you are on the 4th place square and you are in 4th position, collect 40 carrots");
			player.setCarrotCards(player.getCarrotCards() + 40);
			System.out.println("Your new carrot card balance is: " + (int) player.getCarrotCards());

		} else if (((Board.getSpaces().get(player.getPlayerSpace()).getName() == "1st,5th,6th") && (position == 5))) {
			System.out.println(player.getPlayerName()
					+ " , you are on the 1st, 5th and 6th place square and you are in 5th position, collect 50 carrots");
			player.setCarrotCards(player.getCarrotCards() + 50);
			System.out.println("Your new carrot card balance is: " + (int) player.getCarrotCards());

		} else if (((Board.getSpaces().get(player.getPlayerSpace()).getName() == "1st,5th,6th") && (position == 6))) {
			System.out.println(player.getPlayerName()
					+ " , you are on the 1st, 5th and 6th place square and you are in 6th position, collect 60 carrots");
			player.setCarrotCards(player.getCarrotCards() + 60);
			System.out.println("Your new carrot card balance is: " + (int) player.getCarrotCards());
		} else {
			System.out.println(player.getPlayerName()
					+ ", you are not in the correct position for this space, do not collect any carrot cards");
		}
		player.setMissGo(false);
		runGameMenu();
	}

	public void finishAction() {    // method called when player reaches finish space
		finishedCounter++;    // how many players have reached this space increments
		Board.getSpaces().get(player.getPlayerSpace()).setTaken(false);   // finish space shouldnt say taken, so others will be able to land on it later
		int position = finishedCounter;   // if first to reach finish space, you are first, if second to reach it, you came second etc
		if (players.size() > 0) {   // players left in the game
			if ((player.getLettuceCards() == 0)) {
				if ((position == 1) && (player.getCarrotCards() <= 10)) {
					System.out.println("Congratulations! You are the winner!");
					deletePlayer();										//remove the player from the game so they no longer see a menu for their name
				} else if ((position == 2) && (player.getCarrotCards() <= 20)) {
					deletePlayer();
					System.out.println("Congratulations! You came second!");
				} else if ((position == 3) && (player.getCarrotCards() <= 30)) {
					deletePlayer();
					System.out.println("Congratulations! You came third!");
				} else if ((position == 4) && (player.getCarrotCards() <= 40)) {
					deletePlayer();
					System.out.println("Congratulations! You came fourth!");
				} else if ((position == 5) && (player.getCarrotCards() <= 50)) {
					deletePlayer();
					System.out.println("Congratulations! You came fifth!");
				} else if ((position == 6) && (player.getCarrotCards() <= 60)) {
					deletePlayer();
					System.out.println("Congratulations! You came sixth!");
				} else {
					System.out.println(
							"Sorry you have too many carrot or lettuce cards to finish, please come back when you have less");
					finishedCounter--;   // this player didnt actually finish, so decrement the variable that was incremented when they landed on finish
				}
			}
			if (players.size() == 0) {    // no players left to finish , game over
				System.out.println("Game over!");
				System.exit(0);
			}
		}
	}

	public void tortoiseAction() {    
		player.setCarrotCards(player.getCarrotCards() + (moveBy * -10));     // how many spaces moved backwards * 10 ( -10 because spaces moved is negative too, = positive answer)
		System.out.println("You moved " + (moveBy * -1) + " spaces backwards to a tortoise space, so you received "
				+ (moveBy * -10) + " carrots");
	}

	public void deletePlayer() {   
		if (players.size() != 0) {     // arraylist isnt empty 

			players.remove(player);    // remove current player
			if (players.size() != 0) {   // if players still left after current player is removed

				System.out.println("Waiting on others to finish"); 
			}

		}
	}

	//Hare cards 1-7
	public void card1() {   // card to let player move again or miss next go , depending on their position in the race

		if (players.size() % 2 != 0) {											//if amount of players is odd ( means there can be a player in the middle)
			if (testPositionInRace() == players.size() / 2 + 1) {				//if position is equal to half the players(+1) (eg 5/2 + 1=2 + 1 = 3 , which is middle number)
				System.out.println(">> There are equal players in-front and behind of you,\nYou get to move again!");
				playersMove();  //  gets the player to move again
			} else if (players.size() - testPositionInRace() > players.size() / 2) {
				System.out.println(">> There are more players behind you, you will miss your next go!");
				player.setMissGo(true);   // sets the player missGo variable to true, so they miss next go
			} else {
				System.out.println(">> There are less players behind you,\nYou get to move again!");
				playersMove(); //  gets the player to move again
			}

		} else {   // even amount of players ( no chance of a middle positioned player )
			if (players.size() - testPositionInRace() >= players.size() / 2) {
				System.out.println(">> There are more players behind you, you will miss your next go!");
				player.setMissGo(true); // sets the player missGo variable to true, so they miss next go
			} else {
				System.out.println(">> There are less players behind you,\nYou get to move again!");
				playersMove();  //  gets the player to move again
			}

		}
	}

	public void card2() {    // card to miss go if no lettuce, gain 10 carrots for each lettuce owned 
		if (player.getLettuceCards() == 0) {   
			System.out.println(">> You have no lettuce cards, so you must miss your next go!");
			player.setMissGo(true);
		} else {
			for (int i = 0; i < player.getLettuceCards(); i++) {      //for every lettuce card, +10 carrots
				player.setCarrotCards(player.getCarrotCards() + 10);
			}
			System.out.println(">> You have " + player.getLettuceCards() + ", so you now have "
					+ (int) player.getCarrotCards() + " carrots");
		}
	}

	public void card3() {   // card to reinstate carrot balance to 65
		player.setCarrotCards(65);
		System.out.println(">> You now have " + (int) player.getCarrotCards() + " carrots");
	}

	public void card4() {   // card which changes carrot balance, depending if odd or even carrot balance
		if (player.getCarrotCards() % 2 == 0) { 						//if number of carrots is even
			player.setCarrotCards(player.getCarrotCards() / 2);			//divide by two
		} else {
			player.setCarrotCards(player.getCarrotCards() / 2 + 1); 	//else divide by two and add one (to give player "extra odd carrot")
		}
		System.out.println(">> You now have " + (int) player.getCarrotCards() + " carrots");
	}

	public void card5() {   // card to take one carrot off each player and add to current player carrot balance 
		for (Player player : players) {
			player.setCarrotCards(player.getCarrotCards() - 1);			//for all players, remove one carrot card 
		}
		player.setCarrotCards(player.getCarrotCards() + players.size()); //give current player all removed carrot cards
		System.out.println(">> You now have " + (int) player.getCarrotCards() + " carrots");
	}

	public void card6() {  // card to make last move free 
		player.setCarrotCards(player.getCarrotCards() + player.getCarrotsPaid());  //return paid carrot cards
		System.out.println(">> You now have " + (int) player.getCarrotCards() + " carrots"); 
	}

	public void card7() {  // card which displays carrot balance 
		System.out.println(">> You have " + (int) player.getCarrotCards() + " carrots!");
	}

	//Hare cards switch statement (Randomly return any case between 1 and 7)
	public void hareCardNumber() {
		Random randomGenerator = new Random();
		int HareNumber = randomGenerator.nextInt(7) + 1 ;      // This is inclusive of 0 and exclusive of 7 (range 0-6) so we add 1 to this random number to give range 1-7 . 
		switch (HareNumber) {
		case 1:
			System.out.println("----------------");
			System.out.println("CARD DETAILS:");
			System.out.println("----------------");
			System.out.println("If there are more players behind you than in front of you, miss a turn.\n"
					+ "If not,play again.\nIf equal, of course play again");
			System.out.println("----------------");
			card1();
			break;

		case 2:
			System.out.println("----------------");
			System.out.println("CARD DETAILS:");
			System.out.println("----------------");
			System.out.println("Draw 10 carrots for each lettuce you still have, if you have none left, miss a turn");
			System.out.println("----------------");
			card2();
			break;

		case 3:
			System.out.println("----------------");
			System.out.println("CARD DETAILS:");
			System.out.println("----------------");
			System.out.println(
					"Restore you carrot holding to exactly 65 \nIf you have more than 65, pay extras to the carrot patch. If fewer, draw extras from the carrot patch");
			System.out.println("----------------");
			card3();
			break;

		case 4:
			System.out.println("----------------");
			System.out.println("CARD DETAILS:");
			System.out.println("----------------");
			System.out.println("Lose half your carrots! \nIf an odd number, keep the odd one");
			System.out.println("----------------");
			card4();
			break;

		case 5:
			System.out.println("----------------");
			System.out.println("CARD DETAILS:");
			System.out.println("----------------");
			System.out.println("Shuffle the hare cards and receive 1 carrot from each player for doing so");
			System.out.println("----------------");
			card5();
			break;

		case 6:
			System.out.println("----------------");
			System.out.println("CARD DETAILS:");
			System.out.println("----------------");
			System.out.println(
					"Free ride! \nYour last turn costs nothing, retrieve the carrots you paid to reach this square");
			System.out.println("----------------");
			card6();
			break;

		case 7:
			System.out.println("----------------");
			System.out.println("CARD DETAILS:");
			System.out.println("----------------");
			System.out.println(
					"Show us your carrots! \nCount your carrot cards face up so everyone will know how many you have");
			System.out.println("----------------");
			card7();
			break;

		default:
            hareCardNumber();  // if something goes wrong and valid card number option isnt found, method recalled and new random number is generated
			break;

		}
	}
	
	public void rules(){     // prints rules when called
		System.out.println("---------------------------");
		System.out.println("\tGAME RULES");
		System.out.println("---------------------------");
		System.out.println("RULE NO.1: \n\tYou can move forward to any unoccupied square"
				+ " if you have enough carrots.");
		System.out.println("NUMBER SQUARES: \n\tOn your next turn, check if the number you are on is the same\n\t"
				+ "as your position in the race. \n\tIf it is, then multiply your position in the race\n\t"
				+ "by 10 and draw that many Carrot cards. Move on same turn.");
		System.out.println("CARROT SQUARES: \n\tStay as long as you like. Each turn you miss,\n\tcollect OR pay 10 Carrot "
				+ "cards if you want to get rid of some.");
		System.out.println("LETTUCE SQUARES: \n\tYou must hold a Lettuce card to land on this square.\n\t"
				+ "Immediately turn your 'runner' upside down. \n\tNext turn, discard 1x Lettuce card and then "
				+ "multiply your position \n\tin the race by 10 and draw that many Carrot cards. \n\tMove on next "
				+ "turn, and turn 'runner' right side up.");
		System.out.println("TORTOISE SQUARES: \n\tYou can only move backwards to the nearest Tortoise square if empty.\n\t"
				+ "Immediately collect 10 carrots for each square you have moved back. \n\tNext"
				+ " turn move on or backwards again - same rules apply.");
		System.out.println("HARE SQUARES: \n\tDraw the top Hare card and follow the instructions.");
		System.out.println("----------------------");
	}
}