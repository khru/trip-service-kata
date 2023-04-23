package org.craftedsw.tripservicekata.trip;


import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TripServiceShould {

    public static final User GUEST = null;
    public static final User REGISTER_USER = new User();
    public static final Trip LONDON_TRIP = new Trip();
    public static final Trip BARCELONA_TRIP = new Trip();
    private User loggedInUser;

    private TripService tripService;
    private UserBuilder userBuilder;

    @BeforeEach
    public void setUp() {
        userBuilder = new UserBuilder();
        tripService = new TestableTripService();
    }

    @Test
    public void not_allowed_not_logged_in_users() {
        UserNotLoggedInException expectedException = assertThrows(UserNotLoggedInException.class, () -> {
            tripService.getTripsByUser(loggedInUser, GUEST);
        });
    }

    @Test
    public void return_an_empty_trip_list_when_the_user_has_no_friends() {
        User otherStranger = userBuilder.build();
        User stranger = userBuilder.withFriends(otherStranger).build();

        assertEquals(tripService.getTripsByUser(stranger, REGISTER_USER), Collections.emptyList());
    }

    @Test
    public void return_an_empty_trip_list_when_the_user_has_friends_but_not_trips() {
        User anotherUser = userBuilder
                .withFriends(REGISTER_USER)
                .build();

        assertEquals(tripService.getTripsByUser(anotherUser, REGISTER_USER), Collections.emptyList());
    }

    @Test
    public void return_a_list_of_trips_when_the_user_has_friends_and_trips() {
        User friend = userBuilder
                .withFriends(REGISTER_USER)
                .withTrips(LONDON_TRIP, BARCELONA_TRIP)
                .build();

        assertEquals(tripService.getTripsByUser(friend, REGISTER_USER), List.of(LONDON_TRIP, BARCELONA_TRIP));
    }

    class TestableTripService extends TripService {

        @Override
        public List<Trip> tripsBy(User user) {
            return user.trips();
        }
    }
}


