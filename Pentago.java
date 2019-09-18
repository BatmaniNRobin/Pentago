import java.util.Scanner;

/**
 * This is a text-based game to play a two-player game of pentago.
 * 
 * TODO: Fill in the method body code for the following methods:
 * - playChoice, rotateGrid, and getWinner
 *
 * NOTE: You can always add additonal methods to suit your need, but my main method and display methods should not change
 * You can also assume the user enters the text in the correct format (two characters as specified by the prompts)
 *
 * @author Cody Narber & <YOURNAME>
 * @since 2018-01-20
 */
public class Pentago{
	public static void main(String [] args){
		Scanner reader = new Scanner(System.in);
		char[][] grid = new char[6][6];
		for(int i=0; i<6; i++)
			for(int j=0; j<6; j++) grid[i][j]=' ';
		
		char playTurn = 'X';
		char winner = ' ';
		
		while(winner==' '){		
			//Placing marble
			drawBunchOfNL();
			drawGrid(grid,false);	
			System.out.print(playTurn+"'s Turn, chose spot to place[A0-F5]: ");
			String usersChoice = reader.nextLine().toUpperCase();
			placeChoice(grid,usersChoice,playTurn);
		
			//rotating quadrant
			drawBunchOfNL();
			drawGrid(grid,true);	
			System.out.print(playTurn+"'s Turn, chose grid to rotate[A-D][L|R]: ");
			usersChoice = reader.nextLine().toUpperCase();
			rotateGrid(grid,usersChoice);

			//switch player
			if(playTurn=='X') playTurn='O';
			else playTurn='X';
			winner = getWinner(grid);
		}

		System.out.println(winner+" is the winner!");
	}



	//////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////
	//
	// TODO, TODO, TODO, TODO, TODO, TODO, TODO, TODO, TODO, TODO
	//
	// Fill in the details for the following methods:
	//	+ Precondition: What is expected as input
	//	+ Postcondition: What will be the result/effect if no return
	//	+ Implementation: Fill in the code to make it work with my main code, do not modify my main method
	//
	//	Hints are in the method bodies...when you write your code you can delete my internal comments
	//	
	//////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////

	public static boolean checkHori(char[][] grid, int r, int c){
		return grid[r][c]==grid[r][c+1] &&
		grid[r][c]==grid[r][c+2] &&
		grid[r][c]==grid[r][c+3] &&
		grid[r][c]==grid[r][c+4] && grid[r][c]!=' ';
	}
	
	public static boolean checkVert(char[][] grid, int r, int c){
		return grid[r][c]==grid[r+1][c] &&
		grid[r][c]==grid[r+2][c] &&
		grid[r][c]==grid[r+3][c] &&
		grid[r][c]==grid[r+4][c] && grid[r][c]!=' ';
	}
	/**
		Computes the backslash diagonal, i.e. \
	*/
	public static boolean checkDiag1(char[][] grid, int r, int c){
		return grid[r][c]==grid[r+1][c+1] &&
		grid[r][c]==grid[r+2][c+2] &&
		grid[r][c]==grid[r+3][c+3] &&
		grid[r][c]==grid[r+4][c+4] && grid[r][c]!=' '; 
	}
	
	/**
		Computes the forwardslash diagonal, i.e. /
	*/
	public static boolean checkDiag2(char[][] grid, int r, int c){
		return grid[r][c+4]==grid[r+1][c+3] &&
		grid[r][c+4]==grid[r+2][c+2] &&
		grid[r][c+4]==grid[r+3][c+1] &&
		grid[r][c+4]==grid[r+4][c] && grid[r][c+4]!=' ';
	}
	/**
	 * Precondition: TODO
	 * Postcondition: TODO
	 */
	public static char getWinner(char[][] myGrid){
		// Should check for row/column/diagonal of five in a row
		// It may be useful to create separate methods for each type of win condition
		for( int r=0; r<6; r++)
			for( int c=0; c<2; c++)
				if(checkHori(myGrid,r,c)) return myGrid[r][c];
		for( int r=0; r<2; r++)
			for( int c=0; c<6; c++)
				if(checkVert(myGrid,r,c)) return myGrid[r][c];
		for( int r=0; r<2; r++)
			for( int c=0; c<2; c++){
				if(checkDiag1(myGrid,r,c)) return myGrid[r][c];	
				if(checkDiag2(myGrid,r,c)) return myGrid[r][c];	
			}
		
		return ' ';
	}


	/**
	 * Precondition: the grid to be a 2d array that is initialized by two dimensions. user choice to be at least 2 characters where the first character references the row and the second references the column of the grid without going out of bounds. 
	 * Postcondition: sets he grid at the specified location to be the user's symbol
	 */
	public static void placeChoice(char[][] myGrid,String userChoice, char currPlayer){
		int r = userChoice.charAt(0)-65;
		int c = userChoice.charAt(1)-'0';
		
		myGrid[r][c]=currPlayer;
		currPlayer='&';//no change to current player
	}

	public static void rotateLeft(char[][] grid, int r, int c){
		char temp = grid[r][c];
		grid[r][c] = grid[r][c+1];
		grid[r][c+1] = grid[r][c+2];
		grid[r][c+2] = grid[r+1][c+2];
		grid[r+1][c+2] = grid[r+2][c+2]; // bottom-corner
		grid[r+2][c+2] = grid[r+2][c+1]; // bottom
		grid[r+2][c+1] = grid[r+2][c]; // bottom-left
		grid[r+2][c] = grid[r+1][c]; // left
		grid[r+1][c] = temp;
	}
	
	
	public static void rotateRight(char[][] grid, int r, int c){
		char temp = grid[r][c];
		grid[r][c] = grid[r+1][c];
		grid[r+1][c] = grid[r+2][c];
		grid[r+2][c] = grid[r+2][c+1];
		grid[r+2][c+1] = grid[r+2][c+2];
		grid[r+2][c+2] = grid[r+1][c+2];
		grid[r+1][c+2] = grid[r][c+2];
		grid[r][c+2] = grid[r][c+1];
		grid[r][c+1] = temp;
	}

	/**
	 * Precondition: TODO
	 * Postcondition: TODO
	 */
	public static void rotateGrid(char[][] myGrid,String userChoice){
		//HINT: Use charAt() for row and column assume the user enters correctly
		int quad = userChoice.charAt(0)-65; //A-D (0-3)
		char dir = userChoice.charAt(1); // L or R
		
		int r=0, c=0;
		switch(quad){
			case 1: r=3; break;
			case 2: c=3; break;
			case 3: r=3; c=3; break;
		}
		
		if(dir=='R') {rotateRight(myGrid,r,c);rotateRight(myGrid,r,c);}
		if(dir=='L') {rotateLeft(myGrid,r,c);rotateLeft(myGrid,r,c);}
		
		// Think about what goes where (yes it could be hard-coded...may be easier)..note the center of the quadrant does not move
		// User choice should be two characters (made upper case in main method), that specify the quadrant followed by either L to go counter-clockwise or R to go clockwise
	}







	//////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////
	//
	// MY display code...you can take a look if you want...nothing to do below here though
	//
	//////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////

	public static void drawBunchOfNL(){
		for(int i=0; i<20; i++) System.out.println("\n");
	}

	//Assumes 6x6, numbering for rotation or not
	public static void drawGrid(char[][] myGrid, boolean forRot){
		if(!forRot){ 
			System.out.print("    ");
			for(int i=0; i<6; i++){	
				System.out.print(" "+i+"  ");
			}
			System.out.println();
		}
		for(int i=0; i<6; i++){	
			System.out.print("   ");		
			if(i==3) for(int k=0; k<26; k++) System.out.print("=");
			else for(int k=0; k<26; k++) System.out.print("-");
			System.out.println();
			if(forRot){
				 if(i==0) System.out.print("A: |");
				 else if(i==3) System.out.print("B: |");
				 else System.out.print("   |");
			} else System.out.print((char)('A'+i)+": |");
			for(int j=0; j<6; j++){
				System.out.printf(" %c |",myGrid[i][j]);
				if(j==2) System.out.print("|");
			}
			if(forRot){
				 if(i==0) System.out.print(" :C");
				 if(i==3) System.out.print(" :D");
			}
			System.out.println();
		}
		System.out.print("   ");
		for(int k=0; k<25; k++) System.out.print("-");
		System.out.println();
	}

}
