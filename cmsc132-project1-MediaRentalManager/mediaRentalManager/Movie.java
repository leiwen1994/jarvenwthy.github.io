package mediaRentalManager;


public class Movie extends Media {
	//declare variable
	private String rating;
	//constructor
	public Movie (String title, int copiesAvailable,String rating) {
		super(title, copiesAvailable);
		this.rating = rating;
	
	}
	// get rating
	public String getRating() {
		return rating;
	}
	// setter for rating
	public void setRating(String rating) {
		this.rating = rating;
	}
	//print string
	public String toString() {
		return ", Rating: " + rating;
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
		if(!(o instanceof Movie)) {
			return false;
		}
		Movie m = (Movie) o;
		return this.rating.equals(m.rating);
		
	}
}

