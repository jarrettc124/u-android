package la.unum.unum.utils;

import java.util.ArrayList;

/**
 * Created by franklin-pierce on 06/07/16.
 */
public class MultiUser {
    private static ArrayList<User> multiUsers = new ArrayList<>();
    public static void addUser(String instagramId, String instagramToken, String username, String fullName, int numPosts, int numFollowers, int numFollowing) {
        User user = new User(instagramId, instagramToken, username, fullName, numPosts,numFollowers,numFollowing);
        multiUsers.add(user);
    }

    public static void addUser(User user) {
        multiUsers.add(user);
    }

    public static class User{
        public String str_instagramId;
        public String str_instagramToken;
        public String str_username;
        public String str_fullName;

        public String str_authToken;
        public String str_braintreeCustomerId;
        public String str_userid;
        public Boolean b_liveGridEnabled;

        public int n_numPosts;
        public int n_numFollowers;
        public int n_numFollowing;
        public User(){
            str_instagramId = "";
            str_instagramToken = "";
            str_username = "";
            str_fullName = "";
            n_numPosts = 0;
            n_numFollowers = 0;
            n_numFollowing = 0;
            str_authToken = "";
            b_liveGridEnabled = false;
            str_braintreeCustomerId = "";
            str_userid = "";
        }
        User(String instagramId, String instagramToken, String username, String fullName, int numPosts, int numFollowers, int numFollowing){
            this.str_instagramId = instagramId;
            this.str_instagramToken = instagramToken;
            this.str_username = username;
            this.n_numPosts = numPosts;
            this.n_numFollowing = numFollowing;
            this.n_numFollowers = numFollowers;
        }
    }
}
