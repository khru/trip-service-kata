package org.craftedsw.tripservicekata.trip;

import java.util.ArrayList;
import java.util.List;

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.User;
import org.craftedsw.tripservicekata.user.UserSession;

public class TripService {

	public List<Trip> getTripsByUser(User user) throws UserNotLoggedInException {
		List<Trip> tripList = new ArrayList<Trip>();
		User loggedInUser = this.getLoggedUser();
		boolean isFriend = false;
		if (null == loggedInUser) {
			throw new UserNotLoggedInException();
		}

		for (User friend : user.getFriends()) {
			if (friend.equals(loggedInUser)) {
				isFriend = true;
				break;
			}
		}
		if (isFriend) {
			tripList = tripsBy(user);
		}
		return tripList;
	}

	List<Trip> tripsBy(User user) {
		return TripDAO.findTripsByUser(user);
	}

	public User getLoggedUser() {
		return UserSession.getInstance().getLoggedUser();
	}

}
