public class ScoreKeeper{
		
	int NUM_LENGTH = 4;
	String masterMindNum = ""; 
	// int player1Score = 0;
	// int player2Score = 0;
	// String player1Name = "";
	// String player2Name = "";
	int player1GamesWon = 0;
	int player2GamesWon = 0;
	int EXACT_PTS = 10;
	int OTHER_MATCH_PTS = 5;
	int PTS_FOR_WINNING = 40;
	boolean gameOver = false;
	String winningMssg  = "...but this must be a lucky guess. I challenge you to play again to prove my point.";
	int GUESS_COUNT = 0;
	Player player1;
	Player player2;
	
	public ScoreKeeper(Player p1, Player p2){
		player1 = p1;
		player2 = p2;
		
	}

	public void setMysteryNumber(String stringNum){
			masterMindNum = stringNum;
	}
	
	
	public String getMysteryNumber(){
			return masterMindNum;
	}

	// called from MasterMind.java GuessHandlerClass
	public int tallyPoints(int positionMatches, int otherMatches){
		int points = 0;
		points = (positionMatches * EXACT_PTS) + (otherMatches * OTHER_MATCH_PTS);
		if ( positionMatches == 4){
			points += PTS_FOR_WINNING;
		}
		return points;
	}
	
	
	public int getScore(int player){
		if (player == 1){
			return player1.getScore();	
		}
		else
			return player2.getScore();
	}
	
	
	private void setScore(int score, int player){
		if (player == 1){
			player1.setScore(score);	
		}
		else
			player2.setScore(score);
	}
	
	public String getName(int player){
		if (player == 1){
			return player1.getName();	
		}
		else
			return player2.getName();
	}
	
	
	// public void setName(int player, String name){
		// if (player == 1){
			// this.player1Name = name;	
		// }
		// else
			// this.player2Name = name;
	// }

	
	public void setGuessCount(int amount){
		GUESS_COUNT = getGuessCount() + amount;
	}
	
	
	public int getGuessCount(){
		return GUESS_COUNT;
	}
	

	// called from MasterMind.java GuessHandlerClass
	public int getGamesWon(int player){
		if (player == 1){
			return this.player1GamesWon;	
		}
		else{
			return this.player2GamesWon;
			}
	}
	
	
	// called from MasterMind.java in GuessHandlerClass
	public void setGamesWon(int player){
	int newWin = 0;
		if (player == 1){
			newWin = this.player1GamesWon + 1;	
			this.player1GamesWon = newWin;
		}
		else{
			newWin = this.player2GamesWon + 1;
			this.player2GamesWon = newWin;
			}
	}
	
	
	// called from MasterMind.java GuessHandlerClass
	public void assignPoints(int thisRound, int player){
		int updatedScore = thisRound + getScore(player);
		setScore(updatedScore, player);
	}
	
	
	// call getGameWinner after gameOver has returned true
	public int getGameWinner(){
			if (player1.getScore() > player2.getScore()){
				return 1;	
			}
			else{
				return 2;	
			}
	}
	
	
	// called within MasterMind.java "GuessHandlerClass" event handler
	public String masterMindMssg(int exactMatches, int outOfOrderMatches){
		String mssg;
		mssg = "Your guess contains " + exactMatches + " exact match(es),\n and " + 
		outOfOrderMatches + " number match(es) that is/are out of order!";
		return mssg;
	} 
	
	
	public String gameWinnerMssg(int player){
			String currentScoreMssg = getName(player) + " won this round tallying " + 
			getScore(player) + " points!\n";
			return currentScoreMssg; // + winningMssg;
	}
	
	// will return boolean to be tested in condition to branch program in appropriate direction
	public boolean gameOver(){
		return gameOver; 
	}
	
	// called from MasterMind.java GuessHandlerClass
	public boolean isOver(int exactMatch){
			if (exactMatch == NUM_LENGTH){
				gameOver = true;
				return true;
			}
			else
				return false;
	}
	
	
} // end of ScoreKeeper class
