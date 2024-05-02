

##  Product: Connect Four Game (6 * 7 Grid)

### About/Overview
This is a project of Connect Four game. It has a well tested model and controller, as well as a user-friendly minimal GUI.
When users are playing, the player with red pieces will go first.


### List of features

#### Make a move
* The current user need to click the row they want to drop the next piece
* The player with red pieces will always move first
* the players's turns are automatically taken care of, it will show the player's turn at the top of the GUI

#### Restart the game
* When the game is over, or when the player decide to start fresh, they can click the Restart button and start
  from an empty board.


### How To Run
1. Open your terminal, navigate to the folder that contains the JAR file. 
For example, ```cd res```

2.  Use command ```java -jar ConnectFourGUI.jar``` to run the JAR file.



### How to Use the Program
* Aftern running the JAR file, the GUI will be displayed.
* We will see the instruction "Click a row when it is your turn", and "Player RED's turn" on top of the GUI.
* Below is a grid of 6 * 7. When the user click any column of the grid, a round circle (the piece) with color red or yellow will be displayed.
* The color of the circle will match with the current player (RED or YELLOW).
* The users will keep playing until the game ends.
* There are three possible outcome when the game ends: RED wins, Yellow wins, and a draw. Those outcomes will be displayed on top of the GUI.



### Assumptions
* I am using the classic 6 * 7 board. If users need a customized board, they can simply change the parameters in my main class.

### Limitations
* When testing my controller of the connect four game, I have to use a mock model and a mock view. However, it is hard to test my GUI entensively.
* I ran my JAR files several times and tried different playing strategies. Also, I invite my classmates to test this file.
* In the future, I will learn how to automatically test a GUI, hence make my program more reliable.

### Citations
	Documentation of Package javax.swing
	https://docs.oracle.com/javase%2F7%2Fdocs%2Fapi%2F%2F/javax/swing/package-summary.html


