package com.example.parstagram.model;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

@ParseClassName("Post")
public class Post extends ParseObject {

    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_IMAGE = "image";
    private static final String KEY_USER = "user";
    private static final String KEY_PROFILE_IMAGE = "profileImage";

    public String getDescription() {
        return getString(KEY_DESCRIPTION);
    }

    public void setDescription(String description) {
        put(KEY_DESCRIPTION, description);
    }

    public ParseFile getImage() {
        return getParseFile(KEY_IMAGE);
    }

    public ParseFile getProfileImage() {
        return getUser().getParseFile(KEY_PROFILE_IMAGE);
    }

    public void setImage(ParseFile image) {
        put(KEY_IMAGE,image);
    }

    public ParseUser getUser() {
        return getParseUser(KEY_USER);
    }

    public void setUser(ParseUser user) {
        put(KEY_USER,user);
    }

    // Extend the parseQuery class for Post and add method for requesting user info.
    public static class Query extends ParseQuery<Post>{
        public Query() {
            super(Post.class);
        }

        public Query getTop() { // function to get only the first 20
            setLimit(50);
            return this;
        }

        public Query withUser() {
            include("user");
            return this;
        }
    }

}
