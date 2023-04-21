package org.craftedsw.tripservicekata.trip;


import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TripServiceShould {

    public static final User GUEST = null;
    public static final User REGISTER_USER = new User();
    public static final User ANOTHER_USER = new User();
    public static final Trip LONDON_TRIP = new Trip();
    public static final Trip BARCELONA_TRIP = new Trip();
    private User loggedInUser;

    private TripService tripService;

    @BeforeEach
    public void setUp() {
        tripService = new TestableTripService();
    }
	@Test
    public void not_allowed_not_logged_in_users() {
        loggedInUser = GUEST;

        UserNotLoggedInException expectedException = assertThrows(UserNotLoggedInException.class, () -> {
            tripService.getTripsByUser(loggedInUser);
        });
    }

    @Test
    public void return_an_empty_trip_list_when_the_user_has_no_friends() {

        User stranger = ANOTHER_USER;
        User otherStranger = new User();
        stranger.addFriend(otherStranger);

        loggedInUser = REGISTER_USER;

        assertEquals(tripService.getTripsByUser(stranger), Collections.emptyList());
    }

    @Test
    public void return_an_empty_trip_list_when_the_user_has_friends_but_not_trips() {

        loggedInUser = REGISTER_USER;
        User anotherUser = ANOTHER_USER;
        anotherUser.addFriend(loggedInUser);

        assertEquals(tripService.getTripsByUser(anotherUser), Collections.emptyList());
    }

    @Test
    public void return_a_list_of_trips_when_the_user_has_friends_and_trips() {

        loggedInUser = REGISTER_USER;
        User friend = ANOTHER_USER;
        friend.addFriend(loggedInUser);
        friend.addTrip(LONDON_TRIP);
        friend.addTrip(BARCELONA_TRIP);

        assertEquals(tripService.getTripsByUser(friend), List.of(LONDON_TRIP, BARCELONA_TRIP));
    }

    class TestableTripService extends TripService {

        @Override
        public User getLoggedUser() {
            return loggedInUser;
        }
        @Override
        public List<Trip> tripsBy(User user) {
            return user.trips();
        }
    }
}


