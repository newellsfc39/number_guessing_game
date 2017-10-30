import javafx.application.Application;
import javafx.geometry.Pos;	
import javafx.geometry.Insets;	
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.beans.Observable;
import javafx.stage.Stage;
import javafx.stage.Modality; 
import java.awt.event.*;
import javax.swing.JOptionPane;
import java.lang.String;
import java.util.*;
import java.io.*;
import javafx.scene.control.Alert.AlertType;
import mmUtilities.Utilities;

public class MasterMind extends Application { 		// implements Observable implements ActionListener

		String masterMindNumber = "";
	
		Player player1;
		Player player2; 
				
		// global booleans ... to alternate player turns
		boolean plyr_1_turn = false;
		boolean plyr_2_turn = false;
		
		// class instances
		HighScore highScore = new HighScore();
		ScoreKeeper scorer;
		ProcessGuess pGuess = new ProcessGuess();
		int[] guessResult = {0,0};
		
		boolean belongs = false;
		boolean high = false;
		int indexNumber = -9;
		
		// controls for games main window
		// Player 1
		Label p1Wins_Label = new Label("Games Won: ");
		Label p1Score_Label = new Label("Current Score: ");
		TextField p1Guess_TxFld = makeTextField(5);
		Button p1Guess_Btn = makeButton("Guess", 150);
		
		// Player 2
		Label p2Wins_Label = new Label("Games Won: ");
		Label p2Score_Label = new Label("Current Score: ");
		TextField p2Guess_TxFld = makeTextField(5);
		Button p2Guess_Btn = makeButton("Guess", 150);
		
		// Games heading controls
		Label guessCount_Label = new Label("Guess Count: ");
		Label mMindNum_Label = new Label("MasterMind Number: ");
		Label gameTitle_Label = new Label("**** MasterMind!!! ****");
		
		// games feed back window to user
		TextArea infoWindow_TxArea = new TextArea();
		TextField guessCount_TxFld = makeTextField(2);
		private TextField mMindNum_TxFld = makeTextField(10);				
		
		
		// game buttons on main window
		Button instructions_Btn = makeButton("Game Rules", 130);
		Button highScore_Btn = makeButton("Top 10", 130);
		Button playAgain_Btn = makeButton("Play Again", 130);
		Button playGm_Btn = makeButton("Begin Game", 130);
		Button revealNum_Btn = makeButton("Reveal Number", 130);
		Button exit_Btn = makeButton("Exit Game", 130);	

		
		// controls for secondary window
		Label p1NameS2_Label = new Label("Name: ");
		Label p2NameS2_Label = new Label("Name: ");
		Button submitS2_Btn = makeButton("Submit", 130);		
		RadioButton p1S2_RBtn = new RadioButton("Player 1");
		RadioButton p2S2_RBtn = new RadioButton("Player 2");
		ToggleGroup grpS2_TG = new ToggleGroup();
		Label who1stMssgS2_Label = new Label();
		TextField p1NameS2_TxFld = makeTextField(7);
		TextField p2NameS2_TxFld = makeTextField(7);	
		
		// control for 'game rules' window
		Button closeS1_Btn = makeButton("Close Instructions", 150);	
		
		// control for 'Top 10' window
		Button closeS3_Btn = makeButton("Close Top 10", 150);
		
		// stage for window determining player order
		Stage stage2 = new Stage();	
		
		@Override 
		public void start(Stage primaryStage) throws FileNotFoundException{
				
				// sets stage2 modality
				stage2.initModality(Modality.APPLICATION_MODAL);	
				
				// stage for instructions window and sets modality
				Stage stage1 = new Stage();
				stage1.initModality(Modality.APPLICATION_MODAL);	
				
				// stage for top 10 window and sets modality
				Stage stage3 = new Stage();
				stage3.initModality(Modality.APPLICATION_MODAL);	
				
				// load top 10 list if exists
				highScore.readFile();

			
				// ...........build primaryStage
				BorderPane mainPane = new BorderPane();
	
				HBox topHorizPane = getHBox();
				HBox topgameTitle_Label = getHBox();
				topgameTitle_Label.getChildren().add(gameTitle_Label);
				topgameTitle_Label.setStyle("-fx-background-color: orange; -fx-font-size: 35; " +
				"-fx-font-family: times; -fx-font-style: italic; -fx-font-weight: bold;");
				
				p1Wins_Label.setTextFill(Color.web("white"));
				p1Wins_Label.setStyle("-fx-border-color: orange;");
				p1Wins_Label.setPadding(new Insets(10));
				
				p2Wins_Label.setTextFill(Color.web("white"));
				p2Wins_Label.setStyle("-fx-border-color: orange;");
				p2Wins_Label.setPadding(new Insets(10));			
				
				p1Score_Label.setTextFill(Color.web("white"));
				p1Score_Label.setStyle("-fx-border-color: orange;");
				p1Score_Label.setPadding(new Insets(10));
				
				p2Score_Label.setTextFill(Color.web("white"));
				p2Score_Label.setStyle("-fx-border-color: orange;");
				p2Score_Label.setPadding(new Insets(10));
				
				VBox topVertPane = getVBox();
				topVertPane.getChildren().addAll(topgameTitle_Label, topHorizPane);
				
				p1Wins_Label.setMinWidth(150);
				p2Wins_Label.setMinWidth(150);
				p1Score_Label.setMinWidth(150);
				p2Score_Label.setMinWidth(150);
				
				guessCount_Label.setStyle("-fx-font-size: 22; -fx-font-weight: bold;");
				guessCount_Label.setTextFill(Color.web("white"));
				mMindNum_Label.setStyle("-fx-font-size: 22; -fx-font-weight: bold;");
				mMindNum_Label.setTextFill(Color.web("white"));
				mMindNum_TxFld.setEditable(false);
				mMindNum_TxFld.setFocusTraversable(false);				
				guessCount_TxFld.setEditable(false);
				guessCount_TxFld.setFocusTraversable(false);
				
				topHorizPane.getChildren().addAll(mMindNum_Label, mMindNum_TxFld, 
												guessCount_Label, guessCount_TxFld);
				mainPane.setTop(topVertPane);
			
				// set properties for center screen for user feedback
				VBox centerVertPane = getVBox();
				infoWindow_TxArea.setWrapText(true);
				infoWindow_TxArea.setEditable(false);
				infoWindow_TxArea.setPrefColumnCount(20);
				infoWindow_TxArea.setFocusTraversable(false);
				centerVertPane.getChildren().addAll(infoWindow_TxArea);
				centerVertPane.setStyle("-fx-background-color: orange;");
				infoWindow_TxArea.setStyle("-fx-effect:dropshadow(two-pass-box , purple, 10, 0.25 , 4, 5);" +
				"-fx-font-size: 20; -fx-font-family:verdana;");				
				centerVertPane.setPadding(new Insets(15));
				
				// opening statement/message
				infoWindow_TxArea.setText("How many guesses will you need to guess the MasterMind's 'mystery' number?\n\n" +
				"Select 'Begin Game' button to start the game, \n\nor \n\nIf you need instructions, select the 'Game Rules' button.");
				mainPane.setCenter(centerVertPane);

				// Player 1 
				VBox player1Pane = getVBox();
				Label lblP1Header = new Label("Player 1");
				lblP1Header.setStyle("-fx-font-size: 22; -fx-font-weight: bold;");
				lblP1Header.setTextFill(Color.web("white"));
				Label lblSpacer1 = new Label();
				lblSpacer1.setMinWidth(100);				
				player1Pane.getChildren().addAll(lblP1Header, p1Wins_Label, p1Score_Label, 
												p1Guess_TxFld, p1Guess_Btn, lblSpacer1); 
				mainPane.setLeft(player1Pane);
				
				//Player 2
				VBox player2Pane = getVBox();
				player2Pane.setStyle("-fx-background-color: black;");
				Label lblP2Header = new Label("Player 2");
				lblP2Header.setStyle("-fx-font-size: 22; -fx-font-weight: bold;");
				lblP2Header.setTextFill(Color.web("white"));
				Label lblSpacer2 = new Label();
				lblSpacer2.setMinWidth(100);
				player2Pane.getChildren().addAll(lblP2Header, p2Wins_Label, p2Score_Label, 
												p2Guess_TxFld, p2Guess_Btn, lblSpacer2);  
				mainPane.setRight(player2Pane);
				
				
				// controls at application start disabled
				Utilities.setControls(true, p1Guess_Btn, p2Guess_Btn, revealNum_Btn, 
											playAgain_Btn, p1Guess_TxFld, p2Guess_TxFld);
				
				// Game Buttons
				HBox bottomHorizPane = getHBox();
				//instructions_Btn.setMnemonic('G');
				playGm_Btn.setStyle("-fx-background-color: chartreuse;");
				bottomHorizPane.getChildren().addAll(instructions_Btn, highScore_Btn, 
													playGm_Btn, playAgain_Btn, revealNum_Btn, exit_Btn);  
				VBox botVertPane = getVBox();
				botVertPane.getChildren().add(bottomHorizPane);
				mainPane.setBottom(botVertPane);
				// end of building the primaryStage
				
				
				// build game instruction window
				instructions_Btn.setOnAction(e->{
					Pane instrPane = new Pane();
					instrPane.setStyle("-fx-background-color: lightblue; ");
					VBox instr_VBox = getVBox();
					instr_VBox.setAlignment(Pos.CENTER_LEFT);
					instr_VBox.setStyle("-fx-background-color: lightblue; ");
					instrPane.getChildren().add(instr_VBox);
					
					Label[] instructions = {
					new Label("1. Playing the Game:\n" +
					"a. Press 'Begin Game' and you will be directed to enter two competititors names and \n" + 
					"choice of who shall start the game.\n" +
					"b. From this point, players shall alternate turns, first entering a 4 digit number, \n" +
					"then pressing 'Guess' to see if their guess is correct.\n" +
					"c. The game provides feedback on the 'Master Mind Message Board' in the center screen."),
					new Label("2. Scoring Points:\n" +
					"a. You receive 10 pts for each matching number you have in the same position as the\n" +
					"MasterMind number.\n" +
					"b. You receive 5 pts for each matching number that is not in correct position.\n" +
					"c. The player solves the 'MasterMind Mystery Number' receives an additional 40 points!"), 
					new Label("3. How to win:\n" +
					"a. The player which accumulates the most number of points, through guess' \n" +
					"and solving the matermind mystery number shall be the winner.\n" +
					"b. Play several games for added competition!"),
					new Label("4. Playing Again:\n" +
					"Press 'Play Again' and you will be directed to reselect who shall start the next game."),
					new Label("5. Revealing the number:\n" +
					"a. If you wish to end the current round with out exiting the game, \n" +
					"press 'Reveal Number' and the Master Mind Mystery Number will be revealed!\n" +
					"b. Both players will be unable to make another guess until starting a new game."),
					new Label("6. The Top 10:\n" +
					"a. If a score ranks in the top 10 of all players played, it will be added to the list. \n" +
					"b. Press 'Top 10' to see the ten highest scores of MasterMind!\n")
					};
					
					for (Label instruct: instructions){
						instr_VBox.setMargin(instruct, new Insets(0,0,10,0));
						instr_VBox.getChildren().add(instruct);
						
					}
					instr_VBox.getChildren().add(closeS1_Btn);
								
					stage1.setTitle("Game Instructions");
					stage1.setScene(new Scene(instrPane, 650, 675));
					stage1.setResizable(false);
					stage1.showAndWait();
				}); 	// end of instructions_Btn.setOnAction(e->{
				
				
				// build Top 10 Score window
				highScore_Btn.setOnAction(e->{
					String strShowHighScoreList = new String(highScore.showList());
					
					// build top 10 stage
					StackPane top10Pane = new StackPane();
					top10Pane.setStyle("-fx-background-color: lightblue; ");
					
					VBox top10_VBox = getVBox();
					Label top10String_Label = new Label(strShowHighScoreList);
					
					top10_VBox.setAlignment(Pos.CENTER_LEFT);
					top10_VBox.setStyle("-fx-background-color: lightblue; ");
					
					top10_VBox.setMargin(top10String_Label, new Insets(10,10,10,90));
					top10_VBox.setMargin(closeS3_Btn, new Insets(10,10,10,90));
					top10_VBox.getChildren().add(top10String_Label);
					top10_VBox.getChildren().add(closeS3_Btn);
					top10Pane.getChildren().add(top10_VBox);
					
					stage3.setTitle("Top 10");
					stage3.setScene(new Scene(top10Pane, 350, 375));
					stage3.setResizable(false);
					stage3.showAndWait();
				});  		// end of highScore_Btn.setOnAction(e->{
				
				// handlers for radio buttons on user setup window
				p1S2_RBtn.setOnAction(e->{
					if (p1S2_RBtn.isSelected()){
							plyr_1_turn = true;	
							plyr_2_turn = false;
							p1Guess_TxFld.setDisable(false);
							p2Guess_TxFld.setDisable(true);
					}
				});
				p2S2_RBtn.setOnAction(e->{			
					if (p2S2_RBtn.isSelected()){
							plyr_1_turn = false;
							plyr_2_turn = true;
							p1Guess_TxFld.setDisable(true);
							p2Guess_TxFld.setDisable(false);
					}
				});	
				
				
				// user setup window, gets user names and selects who is first
				playGm_Btn.setOnAction(new EventHandler<ActionEvent>(){
						@Override // Override the handle method
						public void handle(ActionEvent e){
							
								playGm_Btn.setDisable(true);
								
								// build Select starter window
								BorderPane basePane = new BorderPane();
								basePane.setStyle("-fx-background-color:purple;");
								
								who1stMssgS2_Label.setText("1. Enter names ...\n" + 
								"2. Select 'Player 1' or 'Player 2' ...\n3. Press 'Submit' ... ");
								who1stMssgS2_Label.setTextFill(Color.web("white"));
								who1stMssgS2_Label.setPadding(new Insets(10));
								
								basePane.setTop(who1stMssgS2_Label);
								basePane.setAlignment(who1stMssgS2_Label, Pos.CENTER); 
								basePane.setPadding(new Insets(15,15,15,15)); 
								
								HBox paneForRadioButton = getHBox();
								p1S2_RBtn.setTextFill(Color.web("white"));
								p2S2_RBtn.setTextFill(Color.web("white"));
								paneForRadioButton.getChildren().addAll(p1S2_RBtn, p2S2_RBtn);
								basePane.setCenter(paneForRadioButton);
								
								VBox paneForName1Button = getVBox();
								p1NameS2_Label.setTextFill(Color.web("white"));
								p2NameS2_Label.setTextFill(Color.web("white"));
								paneForName1Button.setStyle("-fx-background-color: purple;");
								paneForName1Button.getChildren().addAll(p1NameS2_Label, p1NameS2_TxFld);

								basePane.setLeft(paneForName1Button);
								
								VBox paneForName2Button = getVBox();
								paneForName2Button.setStyle("-fx-background-color: purple;");								
								paneForName2Button.getChildren().addAll(p2NameS2_Label, p2NameS2_TxFld);
								basePane.setRight(paneForName2Button);
								
								StackPane paneForSubmitBtn = new StackPane();
								paneForSubmitBtn.setPadding(new Insets(10));
								paneForSubmitBtn.getChildren().add(submitS2_Btn);
								basePane.setBottom(paneForSubmitBtn);
								
								// radio buttons for selecting what player starts
								p1S2_RBtn.setToggleGroup(grpS2_TG);
								p2S2_RBtn.setToggleGroup(grpS2_TG);
								
								stage2.setTitle("Enter Names and Select Starter");
								stage2.setScene(new Scene(basePane, 650, 250));
								
								p1NameS2_Label.requestFocus();
								stage2.setResizable(false);
								stage2.showAndWait();
						}
				});  		// end of playGm_Btn.setOnAction(new EventHandler<ActionEvent>(){
				
				
				
				// handler for play again button
				playAgain_Btn.setOnAction(new EventHandler<ActionEvent>(){
						@Override // Override the handle method
						public void handle(ActionEvent e){
								playAgain_Btn.setDisable(true);
								playGm_Btn.setDisable(false);
								clearFields();
								playGm_Btn.setText("Select Starter");
								infoWindow_TxArea.setText("\t\tPress 'Select Starter' Button");
						}
				});  	// end of playAgain_Btn.setOnAction(new EventHandler<ActionEvent>(){
				
				
				// handler to close top 10 score window
				closeS3_Btn.setOnAction(new EventHandler<ActionEvent>(){
						@Override // Override the handle method
						public void handle(ActionEvent e){
							stage3.close();
							}
				});  	// end of closeS3_Btn.setOnAction
				
				// handler to close instructions window
				closeS1_Btn.setOnAction(new EventHandler<ActionEvent>(){
						@Override // Override the handle method
						public void handle(ActionEvent e){
							stage1.close();
							}
				});  	// end of closeS1_Btn.setOnAction
				
				
				// handler for 'submit' button in user setup window, calls for creation of MMNumber thru getMysteryNumber
				submitS2_Btn.setOnAction(new EventHandler<ActionEvent>(){
						@Override // Override the handle method
						public void handle(ActionEvent e){
							String errorMssg = "\tBEFORE PRESSING SUBMIT:\n\t1. Enter names. \n" +
												"\t2. Select Player 1 or Player 2.";
							boolean emptyName = true;
							boolean allClear = false;
							if (p1NameS2_TxFld.getText().isEmpty() || p2NameS2_TxFld.getText().isEmpty()){
								emptyName = true;
							}
							else{
								emptyName = false;
							}
								if (p1S2_RBtn.isSelected() && !emptyName){
									allClear = true;
									plyr_1_turn = true;
									plyr_2_turn = false;
									p1Guess_TxFld.setDisable(false);
									p2Guess_TxFld.setDisable(true);
									p1Guess_Btn.setStyle("-fx-background-color: chartreuse; -fx-font-size: 16;");
								}
								else if(p2S2_RBtn.isSelected() && !emptyName ){
									allClear = true;
									plyr_1_turn = false;
									plyr_2_turn = true;
									p1Guess_TxFld.setDisable(true);
									p2Guess_TxFld.setDisable(false);
									p2Guess_Btn.setStyle("-fx-background-color: chartreuse; -fx-font-size: 16;");
								}
								else{
									who1stMssgS2_Label.setText(errorMssg);
								}
								if(allClear){
									player1 = new Player(p1NameS2_TxFld.getText());
									player2 = new Player(p2NameS2_TxFld.getText());
									scorer = new ScoreKeeper(player1, player2);
									playGm_Btn.setDisable(true); 
									MasterMind.setNameField (player1.getName(), lblP1Header, scorer, 1);
									MasterMind.setNameField (player2.getName(), lblP2Header, scorer, 2);
									
									getMysteryNumber();
									stage2.close();
								}
								p1S2_RBtn.setSelected(false);
								p2S2_RBtn.setSelected(false);
						}
				});	// end of submit button handler
				
				
				// guess handler class
				GuessHandlerClass guessHandler = new GuessHandlerClass();
				p1Guess_Btn.setOnAction(guessHandler);
				p2Guess_Btn.setOnAction(guessHandler);
			
				// exit handler class
				ExitHandlerClass exitHandler = new ExitHandlerClass();
				exit_Btn.setOnAction(exitHandler);
				
				// reveal MasterMind number handler class
				RevealNumberHandler revealNmbr = new RevealNumberHandler();
				revealNum_Btn.setOnAction(revealNmbr);
				
				
				// set the MasterMind main game window and show it
				Scene masterMind = new Scene(mainPane, 975, 600); 
				primaryStage.setTitle("Master Mind!");
				primaryStage.setResizable(false);
				primaryStage.setScene(masterMind);
				primaryStage.show();
		} 		// end of public void start(Stage primaryStage) throws FileNotFoundException{
		
		
		
		// --------------- custom pane and control methods --------------- //
	private HBox getHBox(){
			HBox horizPane = new HBox(10);
			horizPane.setPadding(new Insets(10));
			horizPane.setAlignment(Pos.CENTER);
			horizPane.setStyle("-fx-background-color: purple; -fx-font-size: 16;");
		return horizPane;	
	}
		private VBox getVBox(){
			VBox vertPane = new VBox(10);
			vertPane.setPadding(new Insets(10));
			vertPane.setAlignment(Pos.CENTER);
			vertPane.setStyle("-fx-background-color: black; -fx-font-size: 16;");
		return vertPane;	
	}	
		
	private TextField makeTextField(int colCount){
			TextField txtFld = new TextField();
			txtFld.setPrefColumnCount(colCount);
			txtFld.setStyle("-fx-background-color: orange; -fx-font-size: 22; -fx-border-color: yellow; " +
							"-fx-font-weight: bold; -fx-font-family: serif;");
			txtFld.setAlignment(Pos.CENTER);
		return txtFld;
	}	
	private Button makeButton(String title, int minWidth){
				Button theButton = new Button(title);
				theButton.setMinWidth(minWidth);			
		return theButton;
	}
	
	
	public static void setNameField (String name, Label lbl, ScoreKeeper sc, int plyrTurn){
		//String name= nm;
		lbl.setText(name);
		//sc.setName(plyrTurn, name);
	 }
	
	
	
	// method called from ... submitS2_Btn.setOnAction(new EventHandler<ActionEvent>()
	public void getMysteryNumber (){
		masterMindNumber = "";
		String ghostNumber = "****";
		for (int i = 0 ; i < 4 ; i++){
			Random rnd = new Random();
			int nmbr = rnd.nextInt( 10 );
			String strmmNmbr = new String(String.valueOf(nmbr));
			masterMindNumber += strmmNmbr;
		}
				
		// initial settings for the selection of who goes first
		if(plyr_1_turn){
			Utilities.setControls(false, p1Guess_Btn, revealNum_Btn);
			p2Guess_Btn.setDisable(true);
			p1Guess_TxFld.requestFocus();
			scorer.setMysteryNumber(masterMindNumber);
			mMindNum_TxFld.setText(ghostNumber);
			welcomePlayersMssg(p1NameS2_TxFld.getText(), p2NameS2_TxFld.getText());
		}
		else if (plyr_2_turn){
			Utilities.setControls(false, p2Guess_Btn, revealNum_Btn);
			p1Guess_Btn.setDisable(true);
			p2Guess_TxFld.requestFocus();
			scorer.setMysteryNumber(masterMindNumber);
			mMindNum_TxFld.setText(ghostNumber);
			welcomePlayersMssg(p2NameS2_TxFld.getText(), p1NameS2_TxFld.getText());
		}
		else{
			
		}
	} 		// end of getMysteryNumber
	
	public void welcomePlayersMssg(String firstPlayer, String secondPlayer){
		infoWindow_TxArea.setText("Welcome " + firstPlayer + " and " + secondPlayer + 
		"!\n\nThe master has made up his mind on a number. " +
		"It will be impossible to guess, I assure you ... but you may try. " +
		"\n\nEnter your 4 digits, then press guess.");
	}
		
	
	public void clearFields(){
		p1Guess_TxFld.setText("");
		p2Guess_TxFld.setText("");
		p1Score_Label.setText("Current Score: 0");
		p2Score_Label.setText("Current Score: 0");
		p1Guess_Btn.setStyle("-fx-background-color: grey; -fx-font-size: 16;");
		p2Guess_Btn.setStyle("-fx-background-color: grey; -fx-font-size: 16;");
		//scorer.setScore(0,0);
		//scorer.setScore(0,1);
		mMindNum_TxFld.setText("----");
		masterMindNumber = "";
		scorer.GUESS_COUNT = 0;
		guessCount_TxFld.setText("0");
	}

//----------------controls the alternating of turns------------------------
	class GuessHandlerClass implements EventHandler<ActionEvent>{
		@Override
		public void handle(ActionEvent e){
				String guess;
				boolean validGuess = false;
				boolean numberSolved = false;
				int matchingDigits = 0;
				int matchingPositions = 0;
				
				scorer.setGuessCount(1);
				guessCount_TxFld.setText("" + scorer.getGuessCount());

				// determine if valid guess was entered by user. 
				// A) get guess 
				if (plyr_1_turn){
					guess = p1Guess_TxFld.getText().trim();
				}
				else{ 
					guess = p2Guess_TxFld.getText().trim(); 
				}
				// B) test validity
				validGuess = Utilities.isCorrectLength(guess.length(), masterMindNumber.length()); 
					if (validGuess){
						// then test if they are digits
						validGuess = Utilities.isDigit(guess);
					}
				
				// C) process a valid guess 
				if (validGuess){
					guessResult[0] = 0;
					guessResult[1] = 0;
					guessResult = pGuess.process(guess, masterMindNumber);
				
					// tally points to current guess
					int points = scorer.tallyPoints(guessResult[0], guessResult[1]);
					
					// D) assign points to appropriate player and display score
					if (plyr_1_turn){
						scorer.assignPoints(points, 1);
						p1Score_Label.setText("Current Score: " + scorer.getScore(1));
					}
					else{
						scorer.assignPoints(points, 2);
						p2Score_Label.setText("Current Score: " + scorer.getScore(2));
					}
					
					// E) is the game over, true if all positions are matched exactly
					scorer.isOver(guessResult[0]);
					
					// if no winner, alternate player turn 
					if (!scorer.gameOver){ 		
						if (plyr_1_turn){
							Utilities.setControls(true, p1Guess_Btn, p1Guess_TxFld);
							Utilities.setControls(false, p2Guess_Btn, p2Guess_TxFld);
							p2Guess_Btn.setStyle("-fx-background-color: chartreuse; -fx-font-size: 16;");
							plyr_1_turn = false;		
							plyr_2_turn = true;  
							p2Guess_TxFld.requestFocus();
						}
						else if (plyr_2_turn){
							Utilities.setControls(false, p1Guess_Btn, p1Guess_TxFld);
							Utilities.setControls(true, p2Guess_Btn, p2Guess_TxFld);
							p1Guess_Btn.setStyle("-fx-background-color: chartreuse; -fx-font-size: 16;");
							plyr_1_turn = true;  
							plyr_2_turn = false;
							p1Guess_TxFld.requestFocus();		
						}	
						else{
							
						}
						// message, continuing game
						String mssg = "";
						mssg = scorer.masterMindMssg(guessResult[0], guessResult[1]);
						infoWindow_TxArea.setText(mssg);
						
					}  	
					else{	// <----------- if there IS a winner
						int winningPlayer;
						
						int newGamesWon = 0;
						winningPlayer = scorer.getGameWinner();
						scorer.gameOver = false;
						String winningMssg = new String(scorer.gameWinnerMssg(winningPlayer));
						
						int winningScore = 0;
						winningScore = scorer.getScore(winningPlayer);
						String theScore = String.valueOf(winningScore);
						
						String winnerName = "";
						if (winningPlayer == 1){
							winnerName = p1NameS2_TxFld.getText();
						}
						else {
							winnerName = p2NameS2_TxFld.getText();
						}
						
						// tests to see if score is in the top 10
						belongs = highScore.doesItBelong(theScore);
						
						// sets game score in top 10
						if (belongs == true){
							highScore.setName(winnerName);
							highScore.setRecord(theScore);
							highScore.assignRecord(highScore.getName(), highScore.getRecord());
						}
		
						boolean scoreResult = false;
						
						// will include a "hiScoreMssg" if scoreResult is 'true' 
						scoreResult = highScore.isItHighScore(highScore.getRecord());
						String hiScoreMssg = "";
						
						// new high score mssg			
						if (scoreResult && winningScore > highScore.getHighScore()){
						hiScoreMssg = highScore.printHighScoreMssg(highScore);
						}
						// ties the high score mssg
						if (scoreResult && winningScore == highScore.getHighScore()){
						hiScoreMssg = "You have managed to tie the high score.\n";
						}
						
						// includes "list of high scores" in the message if "belongs" is true
						String strShowList = "";
						if (belongs == true){
						strShowList = new String(highScore.showList());
						}
						
						String theWinningMssg = new String( winningMssg + "\n" + hiScoreMssg + "\n" + strShowList);
						infoWindow_TxArea.setText(theWinningMssg);
						
						mMindNum_TxFld.setText("");
						scorer.setMysteryNumber("");
						mMindNum_TxFld.setText(masterMindNumber);
						
						scorer.setGamesWon(winningPlayer);
						newGamesWon = scorer.getGamesWon(winningPlayer);

						if (winningPlayer == 1){
							p1Wins_Label.setText("Games Won: " + newGamesWon);
						}
						else {
							p2Wins_Label.setText("Games Won: " + newGamesWon);
						}
						
						Utilities.setControls(true, p1Guess_Btn, p2Guess_Btn, revealNum_Btn, 
												p1Guess_TxFld, p2Guess_TxFld);
						playAgain_Btn.setDisable(false);

						try{
						highScore.writeFile();
						}
						catch(Exception ex){
						ex.printStackTrace();
						}
						
					} // end of else which process' game winner
				} // end of process valid guess if statment
		}	// end of handle(ActionEvent e)
	}	// end of GuessHandlerClass
	
	
	class ExitHandlerClass implements EventHandler<ActionEvent>{
		@Override
		public void handle(ActionEvent e){
			Alert alert = new Alert(AlertType.ERROR, "", ButtonType.YES, ButtonType.NO);
			alert.setTitle("Exit Confirmation");
			alert.setHeaderText("Do you want to exit?");
			alert.setContentText("Select 'Yes' to exit, 'No' to cancel.");
			alert.showAndWait();
			
			if (alert.getResult() == ButtonType.YES){
				System.exit(0);
			}
			else{
			}
		}
	}
	
	class RevealNumberHandler implements EventHandler<ActionEvent>{
		@Override
		public void handle(ActionEvent e){
				mMindNum_TxFld.setText(masterMindNumber);
				infoWindow_TxArea.setText("You gave up! \n\nI knew you could not match the MasterMind's superiority. " + 
				"Maybe you should save yourself any more embarrassment and not play another game.");
				
				mMindNum_TxFld.setEditable(false);
				Utilities.setControls(true, p1Guess_Btn, p2Guess_Btn, revealNum_Btn, p1Guess_TxFld, p2Guess_TxFld);
				Utilities.setControls(false, playAgain_Btn, mMindNum_TxFld);
		}
	}
} // end of MasterMind


