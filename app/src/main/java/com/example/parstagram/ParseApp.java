package com.example.parstagram;

import android.app.Application;

import com.example.parstagram.model.Post;
import com.parse.Parse;
import com.parse.ParseObject;

public class ParseApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        ParseObject.registerSubclass(Post.class);

        final Parse.Configuration configuration = new Parse.Configuration.Builder(this)
                .applicationId("parsta_gram")
                .clientKey("davy_reez")
                .server("https://instaparstagram.herokuapp.com/parse")
                .build();

        Parse.initialize(configuration);
    }
}
