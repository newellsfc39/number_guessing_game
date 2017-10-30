import javax.swing.JOptionPane;

public class ProcessGuess{

char[] mmFlag = {'n','n','n','n'}; 		//  used to mark if a pos. has already been matched, initiated to 'n' 
char[] guessFlag = {'n','n','n','n'}; 	//  used to mark if a pos. has already been matched, initiated to 'n' 
int [] guessResults = {0,0}; 			// index 0 holds # of exact pos. matches, index 1 holds # of non pos. matches

	public int[] process(String guess, String mmNum){
	mmFlag[0] = 'n';
	mmFlag[1] = 'n';
	mmFlag[2] = 'n';
	mmFlag[3] = 'n';
	guessFlag[0] = 'n';
	guessFlag[1] = 'n';
	guessFlag[2] = 'n';
	guessFlag[3] = 'n';
	
	guessResults[0] = 0;
	guessResults[1] = 0;	

	
		// determine 'exact' positional matches, flags are set to 'y' if a match
		for (int i = 0 ; i < mmNum.length() ; i++){
			if (guess.charAt(i) == mmNum.charAt(i)){
				guessResults[0] = guessResults[0] + 1;  // update # in index 0
				mmFlag[i] = 'y';
				guessFlag[i] = 'y';
			}
		}
	
		// if all are exact matches then no need to go further, just return guessResults array 
		if (guessResults[0] == mmNum.length()){
			return guessResults;
		}
	
		// determine digit matches not in 'exact' positions
		for ( int i = 0 ; i < guess.length() ; i++ ){
			for (int j = 0; j < mmNum.length() ; j++ ){
				// this if statement determines if the index position has already been accounted for
				if ((mmFlag[i] == 'y') || (mmFlag[i] == 'z') || (guessFlag[j] == 'y') || (guessFlag[j] == 'z') ){
					// if true, do nothing and increment for loops
				}
				else{  // flags set to 'z' if this if statement is true
					if (mmNum.charAt(i) == guess.charAt(j)){	
						guessResults[1] = guessResults[1] + 1; // update # in index 1
						mmFlag[i] = 'z';	
						guessFlag[j] = 'z';
					} 
					else{
						// no else statement needed here technically
					}
				}
			} // end of nested for loop
		} // end outer for loop
		
		return guessResults;
	} // end of 'process'

} // end of ProcessGuess class