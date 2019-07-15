package com.example.parstagram;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.parstagram.model.Post;
import com.parse.ParseFile;

public class DetailActivity extends Activity {

    public TextView tvHandle;
    public ImageView ivImage;
    public TextView tvDescription;
    public ImageView ivProfileImg;

    private String username;
    private String description;
    private String profileImgUrl;
    private String mainImgUrl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        this.ivProfileImg = findViewById(R.id.ivProfileImg2);
        this.tvHandle = findViewById(R.id.tvUsername2);
        this.ivImage = findViewById(R.id.ivImage2);
        this.tvDescription = findViewById(R.id.tvDescription2);

        Post post = getIntent().getParcelableExtra("post");

        username = post.getUser().getUsername();
        description = post.getDescription();



        tvHandle.setText(username);
        tvDescription.setText(description);

        ParseFile image = post.getImage();
        final ParseFile profileImage = post.getProfileImage();

        if (image != null) {
            final String mainImageUrl = image.getUrl();
            Glide.with(this)
                    .load(mainImageUrl)
                    .listener(new RequestListener<String, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                            // log exception
                            Log.e("TAG", "Error loading main image", e);
                            return false; // important to return false so the error placeholder can be placed
                        }

                        @Override
                        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            return false;
                        }
                    })
                    .override(500, 500)
                    .centerCrop() // scale to fill the ImageView and
                    .into(ivImage);
        } else {
            Glide.with(this)
                    .load(R.drawable.instagram_user_outline_24)
                    .into(ivImage);
        }

        if (profileImage != null) {
            final String profileImageUrl = profileImage.getUrl();
            Glide.with(this)
                    .load(profileImageUrl)
                    .listener(new RequestListener<String, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                            // log exception
                            Log.e("TAG", "Error loading profile image", e);
                            return false; // important to return false so the error placeholder can be placed
                        }

                        @Override
                        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            return false;
                        }
                    })
                    .into(ivProfileImg);
        } else {
            Glide.with(this)
                    .load(R.drawable.instagram_user_outline_24)
                    .into(ivProfileImg);
        }
    }
}
