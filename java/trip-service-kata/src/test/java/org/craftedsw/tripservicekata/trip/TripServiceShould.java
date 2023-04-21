package org.craftedsw.tripservicekata.trip;

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class TripServiceShould {
	@Test
    public void not_allowed_not_logged_in_users() {
        TestableTripService tripService = new TestableTripService();


        // Assert
        UserNotLoggedInException expectedException = assertThrows(UserNotLoggedInException.class, () -> {
            tripService.getTripsByUser(null);
        });
    }

}

class TestableTripService extends TripService {

    public User getLoggedUser() {
        return null;
    }
}
