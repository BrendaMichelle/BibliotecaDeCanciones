import java.util.*;
import java.io.PrintWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class songs {
	
	public static class Song{
		public String name;
		public String artist;
		public String album;
		public int year;
		
		//constructor
		Song (String name, String artist, String album, int year){
			this.name = name;
			this.artist = artist;
			this.album = album;
			this.year = year;
		}
		//methods to update attributes of a song 
		public void editName(String new_name){
			name = new_name;
		}
		public void editArtist(String new_artist){
			artist = new_artist;
		}
		public void editAlbum(String new_album){
			album = new_album;
		}
		public void editYear(int new_year){
			year = new_year;
		}
	}
	
	public static void main(String args[] ) throws FileNotFoundException{
		ArrayList<Song> songlist = new ArrayList<Song>();
		
		songlist.add(new Song("Hello", "Oasis", "Morning Glory", 1995)); //testing
		System.out.println(songlist.get(0).artist); //testing
		
		/*Fills ArrayList called songlist if songlist.txt exists && is not empty
		 * songlist should be in alphabetical order upon filling.*/
		File file = new File("C:/Users/Belle/Desktop/songlibrary.txt");
		if(file.exists() && file.isFile()){
			Scanner sc = new Scanner(file);
			while(sc.hasNextLine()){
				String ln = sc.nextLine();
				String arr [] = ln.split("~");
				songlist.add(new Song(arr[0], arr[1], arr[2], Integer.parseInt(arr[3])));
			}
			sc.close();
		}
		
		else{ 
			System.out.println("File doesn't exist. New file being created...");
			file.getParentFile().mkdirs();
		}

		/*Input songlist into file called songlist.txt. 
		NOTE: This just overwrites a file with the same name if it already exists*/
		
		
				/* sort it in alphabetical order before inserting into txt document
				 *  https://docs.oracle.com/javase/tutorial/collections/interfaces/order.html*/
		
		PrintWriter pw = new PrintWriter(file);
		if(songlist.size() > 0){
		for( int i = 0; i < songlist.size(); i ++){
		pw.println(songlist.get(i).name + "~" + songlist.get(i).artist + "~" + songlist.get(i).album + "~" + songlist.get(i).year);
		}
		}
		pw.close();
	}
}
