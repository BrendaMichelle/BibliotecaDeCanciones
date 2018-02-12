package application;

import java.io.File;
import java.util.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import java.util.Optional;
import application.Songs.Song;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * 
 * Sets up either a blank List View
 * or populates from text file that is
 * found within the directory that
 * the Song Library is located in
 * 
 * Also is the middle man between the users
 * inputs and the corresponding actions that
 * should be taken
 * 
 */
public class ListViewController {
	// All the buttons and textfields that that app uses
	@FXML Button editButton;
	@FXML Button addButton;
	@FXML Button deleteButton;
	@FXML TextField newSong;
	@FXML TextField newArtist;
	@FXML TextField newAlbum;
	@FXML TextField newYearDate;
	@FXML TextField songDetails;
	@FXML TextField artistDetails;
	@FXML TextField albumDetails;
	@FXML TextField yearDateDetails;
	
	// The ListView object that populates the song library
	@FXML ListView<String> listView;
	
	// Both ArrayList. The first being the list that gets
	// put into the ListView
	// While songArray contains the objects of Song
	// with all their details
	private ObservableList<String> obsList;
	private ArrayList<Song> songArray;
	
	/**
	 * 
	 * @param mainStage
	 * The main scene or window that the user
	 * will mainly be interacting with
	 * @param file
	 * The file that the program will search for to
	 * read from and populate the ListView
	 * 
	 * The setup of the ListView
	 * i.e. either opens a blank ListView
	 * if no text file is given or 
	 * populates the ListView if a valid file is
	 * found
	 */
	public void start(Stage mainStage, File file) {
		songArray = new ArrayList<Song>();
		obsList = FXCollections.observableArrayList();
		newSong.setText(null);
		newArtist.setText(null);
		newAlbum.setText(null);
		newYearDate.setText(null);
		
		
		//*** Still need code to search in the directory that the current code is running 
		//*** for a specified file that we have created in a previous run
		//*** If file is found (either in this class or passed to us in the parameter)
		//*** must then read through file and create the corresponding Song objects
		//*** with correct field values
		//*** NOTE: Song and Artist MUST be present in file to be considered valid
		//***		If we do not encounter an album, we pass null for album parameter
		//***		We MUST encounter a year date for each Song (either as positive int or as negative which typically should be -1)
		
		
		//*** Maybe we can set up our files to read as: Song: "Song Name", Artist: "Artist Name", Album: "" (or) "Album Name", Year: "-1" (or) "0-oo"
		//*** But whatever is easiest
		
		
		
		//***The following is just to test out the app
		//*** And should be deleted once completed with assignment
		//*** In the meantime, use this as a template as to how to
		//*** treat the relation ship between the songArray and
		//*** the observable List
		
		//*** Template begins here
		
		// Creating objects to test inputting into both songArray and ListView
		Song despacito = new Song("Despacito", "Daddy Fonsi", null, -1);
		Song newViolence = new Song("The New Violence", "Emanuel", "Soundtrack to a HeadRush", 2005);
		Song tobeLoved = new Song("To be Loved", "Papa Roach", "The Paramour Sessions", 2006);
		Song ratedR = new Song("Rated R", "The White Noise", "AM/PM", 2017);
		songArray.add(despacito);
		songArray.add(newViolence);
		songArray.add(tobeLoved);
		songArray.add(ratedR);
		
		// Creating the String form from the given songArray to put into the observable LIst 
		for(int i = 0 ; i < songArray.size() ; i++) {
			String enterList = songArray.get(i).name + "    :    " + songArray.get(i).artist;
			obsList.add(enterList);
		}
		
		//Put Strings of songs (if any) into ListView and select the first element
		listView.setItems(obsList);
		listView.getSelectionModel().select(0);
		
		// Display the selected song (should be the first element if already populated)
		// However, must take into account of no text File to read from so Selected Song Detail Fields should all be null
		songDetails.setText(songArray.get(0).name);
		artistDetails.setText(songArray.get(0).artist);
		albumDetails.setText(songArray.get(0).album);
		// Song objects will always have an int for years where >0 indicates a valid year and <0 (usually -1) indicates no year given
		// So we give the text field of detailed year date null to make up for it
		if(songArray.get(listView.getSelectionModel().getSelectedIndex()).year > 0) {
			yearDateDetails.setText("" + songArray.get(0).year);
		}
		else {
			yearDateDetails.setText(null);

		}
		
		//*** Template ends here
	}
	
	/**
	 * 
	 * @param event
	 * The actual event taken place on
	 * the keyboard
	 * 
	 * Optional block of code should the user choose
	 * to navigate the song library via arrow keys
	 * 
	 * Not fully functional!
	 * Is behind by one index if is used and must have
	 * code the prevents the user from activating the LEFT
	 * and RIGHT arrow keys!
	 */
	public void handleArrowButtons(KeyEvent event) {
		if(event.getCode() == KeyCode.UP || event.getCode() == KeyCode.DOWN) {
			//System.out.println("Works, index is: " + listView.getSelectionModel().getSelectedIndex());
			songDetails.setText(songArray.get(listView.getSelectionModel().getSelectedIndex()).name);
			artistDetails.setText(songArray.get(listView.getSelectionModel().getSelectedIndex()).artist);
			albumDetails.setText(songArray.get(listView.getSelectionModel().getSelectedIndex()).album);
			
			if(songArray.get(listView.getSelectionModel().getSelectedIndex()).year > 0) {
				yearDateDetails.setText("" + songArray.get(listView.getSelectionModel().getSelectedIndex()).year);
			}
			else {
				yearDateDetails.setText(null);

			}
		} 
		else if(event.getCode() == KeyCode.LEFT || event.getCode() == KeyCode.RIGHT){
			//event.
		}
	}
	
	/**
	 * 
	 * @param e
	 * The actual even taken place
	 * on a Mouse Click
	 * 
	 * Handles the user clicking on a
	 * specific cell of the LIstView
	 * which then uses the information
	 * from the index of the ListView
	 * to extrapolate information from
	 * the specified songArray's index
	 * and populates the Selected Song Details
	 * text boxes
	 * 
	 */
	public void handleMouseClick(MouseEvent e) {
		// Populates the Selected Song Details portion to the selected element
		songDetails.setText(songArray.get(listView.getSelectionModel().getSelectedIndex()).name);
		artistDetails.setText(songArray.get(listView.getSelectionModel().getSelectedIndex()).artist);
		albumDetails.setText(songArray.get(listView.getSelectionModel().getSelectedIndex()).album);
		
		if(songArray.get(listView.getSelectionModel().getSelectedIndex()).year >= 0) {					// Checks if a proper year is within the selected Song object
			yearDateDetails.setText("" + songArray.get(listView.getSelectionModel().getSelectedIndex()).year);
		}
		else {
			yearDateDetails.setText(null);

		}
	}
	
	/**
	 * 
	 * @param e
	 * The actual event taken place
	 * when a button is pressed on the screen
	 * 
	 * Determines which button was pressed and 
	 * calls the corresponding method to handle 
	 * the button's request
	 * 
	 */
	public void buttonPressed(ActionEvent e) {
		Button b = (Button)e.getSource();
		Stage primaryStage = (Stage)b.getScene().getWindow();
		
		if(b == editButton) {											// Edit Song button was clicked
			editSong(primaryStage);
		}
		else if(b == addButton) {										// Add Song button was clicked
			addSong(primaryStage);
		}
		else if(b == deleteButton) {										// Delete Song button was clicked
			deleteSong(primaryStage);
		}
	}
	
	/**
	 * 
	 * @param primaryStage
	 * Used to be able to give the specific
	 * error message pop up or pop up in general
	 * 
	 * Corresponds to the DeleteButton and takes
	 * the index of the ListView to be able to
	 * remove it from both the observable list
	 * AND songArray
	 * 
	 * Also sets the Selected Song Details text boxes to empty as
	 * a default behavior
	 */
	public void deleteSong(Stage primaryStage) {
		if(obsList.size() == 0) {													//Can't delete from an empty play list
			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(primaryStage);
			alert.setTitle("Empty Playlist");
			alert.setHeaderText("No Songs in Playlist to Delete!");
			alert.showAndWait();
		}
		else {
			Alert alert = new Alert(AlertType.CONFIRMATION); 
			alert.initOwner(primaryStage);
			alert.setTitle("Delete Song");
			alert.setHeaderText("ARE YOU SURE YOU WANT TO DELETE:");
			String content = "Song: " + songArray.get(listView.getSelectionModel().getSelectedIndex()).name + 
					"\nBy: " + songArray.get(listView.getSelectionModel().getSelectedIndex()).artist +
					"\nIndex: " + listView.getSelectionModel().getSelectedIndex();
			alert.setContentText(content);
			Optional<ButtonType> result = alert.showAndWait();
			
			 if (result.isPresent() && result.get() == ButtonType.OK) {				// User confirms to deleting a specified song
				 if(obsList.size() == 1) {
					 obsList.remove(0);
					 songArray.remove(0);
					 songDetails.setText("");
					 artistDetails.setText("");
					 albumDetails.setText("");
					 yearDateDetails.setText("");
				 }
				 else{
					 int index = listView.getSelectionModel().getSelectedIndex();
					 if(index + 1 == obsList.size()) {
						 obsList.remove(index);
						 songArray.remove(index);
						 index--;
						 listView.getSelectionModel().select(index);
						 songDetails.setText("");
						 artistDetails.setText("");
						 albumDetails.setText("");
						 yearDateDetails.setText("");
					 }
					 else {
						 obsList.remove(index);
						 songArray.remove(index);
						 listView.getSelectionModel().select(index);
						 songDetails.setText("");
						 artistDetails.setText("");
						 albumDetails.setText("");
						 yearDateDetails.setText("");
					 }
				}
			 }
		}
	}
	
	/**
	 * 
	 * @param primaryStage
	 * Uses the stage to give any
	 * needed pop up messages
	 * 
	 * The functionality of adding a new 
	 * song to both songArray and the 
	 * observable List in order to update
	 * the ListView
	 */
	public void addSong(Stage primaryStage) {
		String newGivenSong;
		String newGivenArtist;
		String newGivenAlbum;
		int newGivenYearDate = -1;
		
		if(newSong.getText() == null || newArtist.getText() == null || 
				newSong.getText().equals("") || newArtist.getText().equals("") ||
				newSong.getText().charAt(0) == ' ' || newArtist.getText().charAt(0) == ' ') {
			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(primaryStage);
			alert.setTitle("Error");
			alert.setHeaderText("Please Enter Both a Song Name and Artist!");
			String content = "Song name and Artist must be filled and cannot begin with a space.";
			alert.setContentText(content);
			alert.showAndWait();
			newSong.setText(null);
			newArtist.setText(null);
			return;
		}
		
		if(newAlbum.getText() != null && !newAlbum.getText().equals("") && newAlbum.getText().charAt(0) == ' ') {   // Checks for valid album input
			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(primaryStage);
			alert.setTitle("Error");
			alert.setHeaderText("Please Enter a Valid Album Name!");
			String content = "Album name cannot start or only be empty spaces.";
			alert.setContentText(content);
			alert.showAndWait();
			newAlbum.setText(null);
			return;
		}
		
		try {																	// Attempts to parse an int from the New Year text field
			if(newYearDate.getText() != null) {
				newGivenYearDate = Integer.parseInt(newYearDate.getText());
				if(newGivenYearDate < 0) {
					throw new NumberFormatException();
				}
			}
		}
		catch(NumberFormatException exception){									// Error message appears if incorrect format and sets field blank
			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(primaryStage);
			alert.setTitle("Incorrect Input");
			alert.setHeaderText("Please Enter a Valid Year Date!");
			String content = "Empty spaces and letters are not allowed.";
			alert.setContentText(content);
			alert.showAndWait();
			newYearDate.setText(null);
			return;
		}
		
		newGivenSong = newSong.getText();
		newGivenArtist = newArtist.getText();
		newGivenAlbum = newAlbum.getText();
		
		Song songToAdd = new Song(newGivenSong, newGivenArtist, newGivenAlbum, newGivenYearDate);
		
		//*** Here is where we must go through the Song Array List
		//*** And alphabetically insert the new Song object
		//*** We must also save the index that the song went into
		//*** the songArray so that we may also add it into the
		//*** same index of the observableList but in String form:
		//*** "Song	:	Artist"
	}
	
	/**
	 * 
	 * @param primaryStage
	 * Used to give any needed pop up messages
	 * 
	 * Takes all the information from the text fields
	 * of the Selected Song Details and puts them into
	 * both the songArray and observable List in
	 * order to update the ListView
	 */
	public void editSong(Stage primaryStage) {
		String editedName;
		String editedArtist;
		String editedAlbum;
		int editedYearDate = -1;
		
		if(songDetails.getText() == null || artistDetails.getText() == null || 
				songDetails.getText().equals("") || artistDetails.getText().equals("") ||
				songDetails.getText().charAt(0) == ' ' || artistDetails.getText().charAt(0) == ' ') {         // Checks for empty Song or Artist Fields
			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(primaryStage);
			alert.setTitle("Error");
			alert.setHeaderText("Please Enter Both a Song Name and Artist!");
			String content = "Song name and Artist must be filled and cannot begin with a space.";
			alert.setContentText(content);
			alert.showAndWait();
			songDetails.setText(songArray.get(listView.getSelectionModel().getSelectedIndex()).name);
			artistDetails.setText(songArray.get(listView.getSelectionModel().getSelectedIndex()).artist);
			albumDetails.setText(songArray.get(listView.getSelectionModel().getSelectedIndex()).album);
			yearDateDetails.setText("" + songArray.get(listView.getSelectionModel().getSelectedIndex()).year);
			return;
		}
		
		if(albumDetails.getText() != null && !albumDetails.getText().equals("") && albumDetails.getText().charAt(0) == ' ') {   // Checks for valid album input
			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(primaryStage);
			alert.setTitle("Error");
			alert.setHeaderText("Please Enter a Valid Album Name!");
			String content = "Album name cannot start or only be empty spaces.";
			alert.setContentText(content);
			alert.showAndWait();
			songDetails.setText(songArray.get(listView.getSelectionModel().getSelectedIndex()).name);
			artistDetails.setText(songArray.get(listView.getSelectionModel().getSelectedIndex()).artist);
			albumDetails.setText(songArray.get(listView.getSelectionModel().getSelectedIndex()).album);
			if(songArray.get(listView.getSelectionModel().getSelectedIndex()).year >= 0) {
				yearDateDetails.setText("" + songArray.get(listView.getSelectionModel().getSelectedIndex()).year);
			}
			else {
				yearDateDetails.setText(null);
			}
			return;
		}
		
		try {																	// Attempts to parse an int from the Year text field
			if(yearDateDetails.getText() != null) {
				editedYearDate = Integer.parseInt(yearDateDetails.getText());
				if(editedYearDate < 0) {
					throw new NumberFormatException();
				}
			}
		}
		catch(NumberFormatException exception){									// Error message appears if incorrect format and sets field blank
			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(primaryStage);
			alert.setTitle("Incorrect Input");
			alert.setHeaderText("Please Enter a Valid Year Date!");
			String content = "Empty spaces and letters are not allowed.";
			alert.setContentText(content);
			alert.showAndWait();
			if(songArray.get(listView.getSelectionModel().getSelectedIndex()).year >= 0) {
				yearDateDetails.setText("" + songArray.get(listView.getSelectionModel().getSelectedIndex()).year);
			}
			else {
				yearDateDetails.setText(null);
			}
			return;
		}
		
		//*** The bottom logic simply replaces the song object in the same index as the selected index
		//*** in the song Array List and obsList. This is not fully correct.
		//*** Must take into account if user totally changes either the song name
		//*** artist name or BOTH. Must then remove the current Song at the index and 
		//*** then traverse the songArray to put the newly edited song into alphabetical order
		//*** Once alphabetical index is found, must then put the song into the same index as
		//*** the obsList array but as String of: "Song	:	artist" 
		editedName = songDetails.getText();
		editedArtist = artistDetails.getText();
		editedAlbum = albumDetails.getText();
		
		Song editedSong = new Song(editedName, editedArtist, editedAlbum, editedYearDate);
		int index = listView.getSelectionModel().getSelectedIndex();
		songArray.set(index, editedSong);
		obsList.set(index,  songArray.get(index).name + "    :    " + songArray.get(index).artist);
		
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.initOwner(primaryStage);
		alert.setTitle("Song Edited");
		alert.setHeaderText("The Following Track Has Been Succesfully Edited:");
		String content = "Song: " + songArray.get(index).name +
							"\nArtist: " + songArray.get(index).artist;
		alert.setContentText(content);
		alert.showAndWait();
			
	}
}
