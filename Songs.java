package application;

import java.util.Comparator;

public class Songs {
	
	public static class Song implements Comparable<Song> {
		public String name;
		public String artist;
		public String album;
		public int year;

		Song (String name, String artist, String album, int year){
			this.name = name;
			this.artist = artist;
			this.album = album;
			this.year = year;
		}
		Song (String name, String artist){
			this.name = name;
			this.artist = artist;
			this.album = null;
			this.year = 0;	
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
		public String getSongname() {
			return name;
		}
		
		//@Override
		public int compareTo(Song s) {
			return Comparators.NAME.compare(this, s);
		}
		public static class Comparators{
			public static final Comparator<Song> NAME = (Song s1, Song s2) -> s1.name.compareToIgnoreCase(s2.name);
			public static final Comparator<Song> ARTIST = (Song s1, Song s2) -> s1.artist.compareToIgnoreCase(s2.artist);
			public static final Comparator<Song> NAMEANDARTIST = (Song s1, Song s2) -> NAME.thenComparing(ARTIST).compare(s1, s2);
		}
		
	} 
}
	
	
	   
	
	






