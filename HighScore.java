import java.util.*;
import java.io.*;

public class HighScore{
	public String[] record = {"", ""};
	int MAX = 10;
	public LinkedList<String[]> theList = new LinkedList<>();	
	
	public LinkedList<String[]> getHighScoreList(){
		return this.theList;
	}	
		
	public void addToList(String[] currentRecord){
		theList.add(currentRecord);
	}
	
	public boolean doesItBelong(String score){
		int userScore = Integer.parseInt(score);
		if (theList.size() < MAX){	
			return true;
		}
		else{
			if (userScore >= this.getLowScore()){ 
				theList.removeFirst(); 		// remove lowest to make room for new score
				return true;
			}
			else{ 
				return false;
			}
		}	
	}
	
	public void assignRecord(String name, String number){
		String[] array = {name, number};
		int indexNumber = findIndex(getRecord());
		insertScore(indexNumber, array);
	}
	
	// finds position for inserting a valid top 10 score in the list
	public int findIndex(String score){						
		int userNumber = Integer.parseInt(score);
		String[] listNumber = {"", ""};
			for (int i = 0 ; i < theList.size(); i++){
				listNumber = theList.get(i);
				int listScore = Integer.parseInt(listNumber[1]);
					if (userNumber < listScore){ 				
						return theList.indexOf(listNumber);
					}
			}
		return theList.size(); // returns the last position
	}
	
	public boolean isItHighScore(String score){
		int userScore = Integer.parseInt(score);
		if (userScore == this.getHighScore()){
			return true;
		}
		else{
			return false;
		}
	}
	
	public String showList(){
	if (theList.isEmpty()){
		return "The List is Empty.";
	}
	String strList = "";
	String[] aRecord = {"", ""};
	String tempLine = "";
	strList = "Score" + "\t\t\t" + "Name\n";
		for (int i = theList.size()-1 ;  i >= 0 ; i--){
			aRecord = theList.get(i);
			strList += aRecord[1] + "\t\t\t\t" + aRecord[0] + "\n";
		}
		return strList;
	}
	
	// recursive method called from assignRecord
	public void insertScore(int index, String[] newRecord){
		theList.add(index, newRecord);
	
	}
		
	// works ... helper method, returns int for immediate comparison
	public int getHighScore(){
		if (!theList.isEmpty()){
			String[] firstScore = theList.peekFirst();
			int first = Integer.parseInt(firstScore[1]);
			
			String[] lastScore = theList.peekLast();
			int last = Integer.parseInt(lastScore[1]);
			
				if (first > last){
					//System.out.println("" + first);
					return first;
				}
				else{
					//System.out.println("" + last);
					return last;
				}
		}
		else{
			return 0;
		}
	}

	// helper method,  returns an int for immediate comparison
	public int getLowScore(){
	
		String[] firstScore = theList.peekFirst();
		int first = Integer.parseInt(firstScore[1]);
		
		String[] lastScore = theList.peekLast();
		int last = Integer.parseInt(lastScore[1]);

			if (first < last){
				return first;
			}
			else{
				return last;
			}
	}

	public void setRecord(String score){
		this.record[1] = score;
	}

	public String getRecord(){	
		return this.record[1];
	}	
	
	public void setName(String name){
		this.record[0] = name;
	}
	
	public String getName(){
		return this.record[0];
	}	
	
	public String printHighScoreMssg(HighScore score){
		int high = -1;
		high = score.getHighScore();
		String hiScoreMssg = "";
		String theHigh = String.valueOf(high);
			if (high == -1){
				return hiScoreMssg = "program error";
			}
			else{
				return hiScoreMssg = "Wow! " + theHigh + " is a new high score!\n";
			}
	}

	public void readFile() throws FileNotFoundException{
		File fileName = new File("Top10Scores.txt");
		if (fileName.exists()){
			Scanner input =  new Scanner(fileName);
		
			while(input.hasNext()){
				record[0] = input.next();
				record[1] = input.next();
				this.assignRecord(record[0], record[1]);
			}
			//this.descendingList();
		}
		else{
		//System.out.println("File does not exist.");
		}
	}
	
	public void descendingList(){
		Collections.reverse(theList);
	}
	
	public void writeFile() throws FileNotFoundException{
		File fileName = new File("Top10Scores.txt");
		String spacing = "";

			PrintWriter output =  new PrintWriter(fileName);
			for (String[] e: theList){
				spacing = getBlankSpaces(e[0].length());
				output.printf("%s%s%s\n", e[0], spacing, e[1]);
			}
			output.close();
	}
	
	public String getBlankSpaces(int nameLength){
		String space = "";
		for(int i = nameLength ; i < 20 ; i++){
			space += " ";
		}
	return space;
	}
	
} // end of HighScore class