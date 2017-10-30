public class Player{
	
	private final String name;
	int score = 0;				// move to scoreKeeper
	String currentGuess = ""; 	// move to scoreKeeper
	int totalGamesWon = 0;
	
	public Player(String name){
		this.name = name;
		
	}
	
	public String getName(){
		return this.name;
	}
	
	public int getScore(){
		return this.score;
	}
	
	public void setScore(int score){
		this.score = score;
	}
}