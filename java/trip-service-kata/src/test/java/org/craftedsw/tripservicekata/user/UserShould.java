package org.craftedsw.tripservicekata.user;

import org.craftedsw.tripservicekata.trip.UserBuilder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserShould {

    public static final User A_FRIEND = new User();
    public static final User A_STRANGER = new User();

    @Test
    public void inform_when_is_friend_with_another_user() {
        User user = new UserBuilder()
                .withFriends(A_FRIEND)
                .build();

        assertFalse(user.isFriendsWith(A_STRANGER));
        assertTrue(user.isFriendsWith(A_FRIEND));
    }

    @Test
    public void return_false_when_it_has_not_friends() {
        User user = new UserBuilder()
                .build();

        assertFalse(user.isFriendsWith(A_STRANGER));
    }
}
