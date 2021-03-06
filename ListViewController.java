package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import application.Songs.Song;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
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
	@FXML ListView<String> listView; // The ListView object that populates the song library
	private ObservableList<String> obsList; //list that's put into ListView
	private ArrayList<Song> songArray; //Contains Song objects with their deets
	
	public void start(Stage mainStage, File file, ArrayList<Song> songArray) throws FileNotFoundException {
		//songArray = new ArrayList<Song>();
		obsList = FXCollections.observableArrayList();
		newSong.setText(null);
		newArtist.setText(null);
		newAlbum.setText(null);
		newYearDate.setText(null);
		this.songArray = songArray;
		

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
		if(songArray.size() > 0) {
		songDetails.setText(songArray.get(0).name);
		artistDetails.setText(songArray.get(0).artist);
		albumDetails.setText(songArray.get(0).album);
		}
		// Song objects will always have an int for years where >0 indicates a valid year and <0 (usually -1) indicates no year given
		// So we give the text field of detailed year date null to make up for it
		if(songArray.size() > 0 && songArray.get(listView.getSelectionModel().getSelectedIndex()).year > 0) {
			yearDateDetails.setText("" + songArray.get(0).year);

		}
		else {
			yearDateDetails.setText(null);
		}
		
		mainStage.setOnHidden(event -> {try {
			saveFile(file, songArray);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}} );
		
		
	}
	
	public void saveFile(File file,  ArrayList<Song> updatedList ) throws FileNotFoundException {
		PrintWriter pw = new PrintWriter(file);
		if(updatedList.size() > 0){
		for( int i = 0; i < updatedList.size(); i ++){
		pw.println(updatedList.get(i).name + "~" + updatedList.get(i).artist + "~" + updatedList.get(i).album + "~" + updatedList.get(i).year);
		}
		}
		pw.close();
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
	 * The actual event taken place
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
	 * Corresponds to the DeleteButton and takes
	 * the index of the ListView to be able to
	 * remove it from both the observable list
	 * AND songArray 
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
					"\nArtist: " + songArray.get(listView.getSelectionModel().getSelectedIndex()).artist +
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
			alert.setHeaderText("Please Enter Both a Song Name and an Artist!");
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
			alert.setHeaderText("Please Enter a Valid Year Date! Format: ####");
			String content = "Empty spaces and letters are not allowed.";
			alert.setContentText(content);
			alert.showAndWait();
			newYearDate.setText(null);
			return;
		}
		
		newGivenSong = newSong.getText();
		newGivenArtist = newArtist.getText();
		newGivenAlbum = newAlbum.getText();
		
		//else add and display song into list alphabetically
		Song songToAdd = new Song(newGivenSong, newGivenArtist, newGivenAlbum, newGivenYearDate);
		songArray.add(songToAdd);
		Collections.sort(songArray, Song.Comparators.NAMEANDARTIST);
		obsList.clear();
		for(int i = 0 ; i < songArray.size() ; i++) {
			String enterList = songArray.get(i).name + "    :    " + songArray.get(i).artist;
			obsList.add(enterList);
		}
		listView.setItems(obsList);

	}
	

	
	/**
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
			alert.setHeaderText("Please Enter a Valid Year Date! Format: ####");
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
		

		editedName = songDetails.getText();
		editedArtist = artistDetails.getText();
		editedAlbum = albumDetails.getText();
		
		Song editedSong = new Song(editedName, editedArtist, editedAlbum, editedYearDate);
		int index = listView.getSelectionModel().getSelectedIndex();
		
		//delete from list and add edited version
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
		songArray.add(editedSong);
		//alphabetize
		Collections.sort(songArray, Song.Comparators.NAMEANDARTIST);
		obsList.clear();
		for(int i = 0 ; i < songArray.size() ; i++) {
			String enterList = songArray.get(i).name + "    :    " + songArray.get(i).artist;
			obsList.add(enterList);
		}
		listView.setItems(obsList);
		

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