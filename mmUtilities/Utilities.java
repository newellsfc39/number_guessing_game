package mmUtilities;
import javax.swing.JOptionPane;
import javafx.scene.control.Control;
import javafx.scene.control.Dialog;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;


public class Utilities {

	// called in MasterMind.java GuessHandlerClass
	public static boolean isCorrectLength(int userLen, int masterMindLen){
		if (userLen == masterMindLen){
			return true;
		}
		else{
			
			Alert alert = new Alert(AlertType.ERROR, "", ButtonType.OK);
				alert.setTitle("Invalid Guess");
				alert.setHeaderText("Guess contains too few or too many digits.");
				alert.setContentText("A valid guess is FOUR digits long., ex. 1234");
				alert.showAndWait();

			return false;
		}
	} // end isCorrectLength


	// called from MasterMind.java GuessHandlerClass
	public static boolean isDigit (String guess){
		for (int i = 0 ; i < guess.length() ; i++){
			if (! ((guess.charAt(i) >= '0') && (guess.charAt(i) <= '9'))){
				
				Alert alert = new Alert(AlertType.ERROR, "", ButtonType.OK);
				alert.setTitle("Invalid Guess");
				alert.setHeaderText("Non-digit character found in guess.");
				alert.setContentText("Valid guess must contain all digits, ie., NUMBERS!");
				alert.showAndWait();
				return false;
			}
		}
		return true;
	} // end isLetter
	

	// generic method with variable length argument
	public static <E extends Control> void setControls(boolean value, E... control){
		for (E item : control){
			item.setDisable(value);
		}
	}
	

}// end of class Utilities