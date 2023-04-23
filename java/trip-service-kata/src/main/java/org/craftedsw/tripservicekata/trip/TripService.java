package org.craftedsw.tripservicekata.trip;

import java.util.Collections;
import java.util.List;

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.User;

public class TripService {

    private TripDAO tripDAO;

    public TripService(TripDAO tripDAO) {
        this.tripDAO = tripDAO;
    }

    public TripService() {
        this.tripDAO = new TripDAO();
    }

    public List<Trip> getTripsByUser(User user, User loggedInUser) throws UserNotLoggedInException {
        if (null == loggedInUser) {
            throw new UserNotLoggedInException();
        }

        if (!user.isFriendsWith(loggedInUser)) {
            return Collections.emptyList();
        }

        return tripsBy(user);
    }

    List<Trip> tripsBy(User user) {
        return tripDAO.tripsBy(user);
    }
}
