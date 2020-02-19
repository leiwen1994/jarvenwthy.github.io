package mediaRentalManager;

import java.util.ArrayList;
import java.util.Collections;

public class MediaRentalManager implements MediaRentalManagerInt {
	private int planLimit = 2;
	private ArrayList<Customer> customerList = new ArrayList<Customer>();
	private ArrayList<Media> mediaList = new ArrayList<Media>();

	@Override
	public void addCustomer(String name, String address, String plan) {
		Customer customer = new Customer(name, address, plan);
		customerList.add(customer);

	}

	@Override
	public void addMovie(String title, int copiesAvailable, String rating) {
		Media movie = new Movie(title, copiesAvailable, rating);
		mediaList.add(movie);

	}

	@Override
	public void addAlbum(String title, int copiesAvailable, String artist, String songs) {
		Media album = new Album(title, copiesAvailable, artist, songs);
		mediaList.add(album);

	}

	@Override
	public void setLimitedPlanLimit(int value) {
		planLimit = value;
	}

	@Override
	public String getAllCustomersInfo() {
		Collections.sort(customerList);

		String result = "***** Customers' Information *****" + '\n';
		for (Customer c : customerList) {
			result += c.toString() + '\n';
			result += "Rented: " + c.getRentList() + '\n';
			result += "Queue: " + c.getQueueList() + '\n';
		}
		// System.out.println(result);
		return result;

	}

	@Override
	public String getAllMediaInfo() {
		Collections.sort(mediaList);
		String result = "***** Media Information *****" + '\n';
		for (Media m : mediaList) {
			if (m.getClass() == Album.class) {
				result += "Title: " + m.getTitle() + ", Copies Available: " + m.getCopiesAvailable() + m.toString()
						+ '\n';
			} else if (m.getClass() == Movie.class) {
				result += "Title: " + m.getTitle() + ", Copies Available: " + m.getCopiesAvailable() + m.toString()
						+ '\n';
			}
		}
		// System.out.println(result);
		return result;
	}

	@Override
	public boolean addToQueue(String customerName, String mediaTitle) {
		for (Customer c : customerList) {
			if (customerName.equals(c.getName())) {
				if (!(c.getQueueList().contains(mediaTitle))) {
					c.getQueueList().add(mediaTitle);
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public boolean removeFromQueue(String customerName, String mediaTitle) {
		for (Customer c : customerList) {
			if (customerName.equals(c.getName())) {
				if (c.getQueueList().contains(mediaTitle)) {
					c.getQueueList().remove(mediaTitle);
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public String processRequests() {
		String result = "";
		Collections.sort(customerList);
		for (Customer c1 : customerList) {
			ArrayList<String> rented = c1.getRentList();
			ArrayList<String> queued = c1.getQueueList();

			if (c1.getPlan().equals("UNLIMITED")) {
				for (String q : queued) {
					for (Media m : mediaList) {
						if (q.equals(m.getTitle()) && m.getCopiesAvailable() > 0) {
							if (!(rented.contains(m.getTitle()))) {
								result += "Sending " + m.getTitle() + " to " + c1.getName() + '\n';
								rented.add(m.getTitle());
								m.trackCopies();
							}
						}
					}
				}
			}

			if (c1.getPlan().equals("LIMITED")) {

				for (String q : queued) {
					for (Media m : mediaList) {
						if (q.equals(m.getTitle()) && rented.size() < planLimit && m.getCopiesAvailable() > 0) {
							if (!(rented.contains(m.getTitle()))) {
								result += "Sending " + m.getTitle() + " to " + c1.getName() + '\n';
								rented.add(m.getTitle());
								m.trackCopies();
							}

						}
					}

				}
			}
			for (String s : rented) {
				queued.remove(s);
			}
		}

		return result;
	}

	@Override
	public boolean returnMedia(String customerName, String mediaTitle) {
		for (Customer c : customerList) {
			ArrayList<String> rented = c.getRentList();
			{
				if (c.getName().equals(customerName)) {
					for (Media m : mediaList) {
						if (m.getTitle().equals(mediaTitle)) {
							rented.remove(mediaTitle);
							m.increseCopies();
							return true;
						}
					}
				}

			}
		}
		return false;
	}

	@Override
	public ArrayList<String> searchMedia(String title, String rating, String artist, String songs) {
		ArrayList<String> mediaTitle = new ArrayList<String>();
		Collections.sort(mediaList);
		if (title == null) {
			for (Media m : mediaList) {
				if (m instanceof Movie) {
					if (songs == null && artist == null) {
						if (rating == null) {
							if (!mediaTitle.contains(title)) {
								mediaTitle.add(m.getTitle());
							}
						}
						if (rating != null) {
							Movie movie = (Movie) m;
							if (movie.getRating().equals(rating)) {
								if (!mediaTitle.contains(title)) {
									mediaTitle.add(m.getTitle());
								}
							}
						}
					}
				}
				if (m instanceof Album) {
					if (rating == null) {
						if (artist == null) {
							if (songs == null) {
								if (!mediaTitle.contains(title)) {
									mediaTitle.add(m.getTitle());
								}
							}
							if (songs != null) {
								Album ablum = (Album) m;
								if (ablum.getSongs().contains(songs)) {
									if (!mediaTitle.contains(title)) {
										mediaTitle.add(m.getTitle());
									}
								}
							}
						}
						if (artist != null) {
							Album ablum = (Album) m;
							if (songs == null) {
								if (ablum.getArtist().equals(artist)) {
									if (!mediaTitle.contains(title)) {
										mediaTitle.add(m.getTitle());
									}
								}
							}
							if (songs != null) {
								if (ablum.getSongs().contains(songs) && ablum.getArtist().equals(artist)) {
									if (!mediaTitle.contains(title)) {
										mediaTitle.add(m.getTitle());
									}
								}
							}
						}
					}
				}

			}
		} else if (title != null) {
			for (Media m : mediaList) {
				if (m instanceof Movie) {
					if (songs == null && artist == null) {
						if (rating == null) {
							if (!mediaTitle.contains(title)) {
								if (title.equals(m.getTitle())) {
									mediaTitle.add(m.getTitle());
								}
							}
						}
						if (rating != null) {
							Movie movie = (Movie) m;
							if (movie.getRating().equals(rating)) {
								if (!mediaTitle.contains(title)) {
									if (title.equals(m.getTitle())) {
										mediaTitle.add(m.getTitle());
									}
								}
							}
						}
					}
				}
				if (m instanceof Album) {
					if (rating == null) {
						if (artist == null) {
							if (songs == null) {
								if (!mediaTitle.contains(title)) {
									if (title.equals(m.getTitle())) {
										mediaTitle.add(m.getTitle());
									}
								}
							}
							if (songs != null) {
								Album ablum = (Album) m;
								if (ablum.getSongs().contains(songs)) {
									if (!mediaTitle.contains(title)) {
										if (title.equals(m.getTitle())) {
											mediaTitle.add(m.getTitle());
										}
									}
								}
							}
						}
						if (artist != null) {
							Album ablum = (Album) m;
							if (songs == null) {
								if (ablum.getArtist().equals(artist)) {
									if (!mediaTitle.contains(title)) {
										if (title.equals(m.getTitle())) {
											mediaTitle.add(m.getTitle());
										}
									}
								}
							}
							if (songs != null) {
								if (ablum.getSongs().contains(songs) && ablum.getArtist().equals(artist)) {
									if (!mediaTitle.contains(title)) {
										if (title.equals(m.getTitle())) {
											mediaTitle.add(m.getTitle());
										}
									}
								}
							}
						}
					}
				}

			}

		}
		return mediaTitle;
	}
}