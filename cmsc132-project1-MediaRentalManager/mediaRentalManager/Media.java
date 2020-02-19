package mediaRentalManager;

public class Media implements Comparable<Media>{
	//declare variables
	private String title;
	private int copiesAvailable;  
	//constructor
	public Media (String title, int copiesAvailable) {
		this.title = title;
		this.copiesAvailable = copiesAvailable;
	}
	// get title
	public String getTitle() {
		return title;
	}
	//get copiesAvaliable
	public int getCopiesAvailable() {
		return copiesAvailable;
	}
	//track copies when someone rented copies -1
	public void trackCopies() {
		copiesAvailable --;
	}
	//track copies when someone returned copies +1
	public void increseCopies() {
		copiesAvailable ++;
	}
	//setter for title
	public void setTitle(String title) {
		this.title = title;
	}
	//setter for copiesAvailable
	public void setCopiesAvaliable(int copiesAvailable) {
		this.copiesAvailable = copiesAvailable;
	}
	//compare sort
	@Override
	public int compareTo(Media o) {
		return title.compareTo(o.title);
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
		if(!(o instanceof Media)) {
			return false;
		}
		Media me = (Media) o;
		return this.title.equals(me.title) && this.copiesAvailable == me.copiesAvailable;

	}

}
