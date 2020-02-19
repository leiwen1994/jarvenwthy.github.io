package mediaRentalManager;

public class Album extends Media{
	//declare variables 
	private String artist;
	private String songs;
	//constructor
	public Album(String title, int copiesAvailable, String artist, String songs) {
	super(title, copiesAvailable);
	this.artist = artist;
	this.songs= songs;
	}
	// get artist
	public String getArtist() {
		return artist;
	}
	//get songs
	public String getSongs() {
		return songs;
	}
	//setter for artist
	public void setArtist(String artist) {
		this.artist=artist;
	}
	//setter for songs
	public void setSongs(String songs) {
		this.songs = songs;
	}
	//print string
	public String toString() {
		return ", Artist: "+  artist + ", Songs: " + songs;
		
	}
	//equal method
	@Override
	public boolean equals(Object o) {
		if( o == null) {
			return false;
		}
		if( o == this) {
			return true;
		}
		if(!(o instanceof Album)) {
			return false;
		}
		Album a = (Album) o;
		return this.artist.equals(a.artist) && this.songs.equals(a.songs);
	}
	}
