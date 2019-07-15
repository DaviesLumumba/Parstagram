package com.example.parstagram.model;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.parstagram.DetailActivity;
import com.example.parstagram.R;
import com.parse.ParseFile;

import java.util.List;

public class PostsAdapter extends RecyclerView.Adapter <PostsAdapter.ViewHolder> {



    private List<Post> mPosts;
    Context context;

    // Pass in the posts array in the constructor
    public PostsAdapter(Context context, List<Post> posts) {
        this.context = context;
        mPosts = posts;
    }


//    public PostsAdapter(@NonNull View itemView) {
//        super(itemView);
//        tvHandle = itemView.findViewById(R.id.tvUsername);
//        ivImage = itemView.findViewById(R.id.ivProfileImg);
//        tvDescription = itemView.findViewById(R.id.tvDescription);
//    }

//    private void bind(Post post) {
//        //TODO: bind the view
//
//
//        // bind the values based on the position of the element
//        @Override
//        public void onBindViewHolder(PostsAdapter.ViewHolder viewHolder, int position) {
//            // Get the data model based on
//            Post post = mPosts.get(position);
//
//            // Set item views based on your views and data model
//            viewHolder.tvUsername.setText(post.user.name);
//            viewHolder.tvBody.setText(post.body);
//            viewHolder.tvTimestamp.setText(post.getRelativeTimeAgo());
//
//
//            viewHolder.ivProfileImg.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    animateButton(v);
//                }
//            });
//
//
//
//            viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(context, ComposeActivity.class);
//                    context.startActivity(intent);
//                }
//            });
//
//            Glide.with(context)
//                    .load(post.user.profileImageUrl)
//                    .listener(new RequestListener<String, GlideDrawable>() {
//                        @Override
//                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
//                            // log exception
//                            Log.e("TAG", "Error loading image", e);
//                            return false; // important to return false so the error placeholder can be placed
//                        }
//
//                        @Override
//                        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
//                            return false;
//                        }
//                    })
//                    .into(viewHolder.ivProfileImg);
//
//
//        }
//    }

    @NonNull
    @Override
    // For each row inflate the layout and cache references into viewHolder
    public PostsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View postView = inflater.inflate(R.layout.item_post, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(postView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PostsAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        final Post post = mPosts.get(position);

        // Load Username and Description
        final String username = post.getUser().getUsername();
        viewHolder.tvHandle.setText(username);
        final String description = post.getDescription();
        viewHolder.tvDescription.setText(description);

        ParseFile image = post.getImage();
        final ParseFile profileImage = post.getProfileImage();

        // Load main image

        if (image != null) {
            final String mainImageUrl = image.getUrl();
            Glide.with(context)
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
                    .into(viewHolder.ivImage);
        }

        // Load profile picture

        if (profileImage != null) {
            final String profileImageUrl = profileImage.getUrl();
            Glide.with(context)
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
                    .into(viewHolder.ivProfileImg);
        }
        else{
            Glide.with(context)
                    .load(R.drawable.instagram_user_outline_24)
                    .into(viewHolder.ivProfileImg);
        }


        viewHolder.ivProfileImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animateButton(v);
            }

        });

         //TODO: Detail view
        viewHolder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailActivity.class);
//                intent.putExtra("username",username);
//                intent.putExtra("description",description);
//                intent.putExtra("profileImageUrl",profileImage.getUrl());
                intent.putExtra("post", post);
                //intent.putExtra("mainImageUrl",mainImageUrl);
                context.startActivity(intent);
            }
        });




    }

    // create the viewHolder class
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvHandle;
        public ImageView ivImage;
        public TextView tvDescription;
        public ImageView ivProfileImg;
        public View container;

        public ViewHolder(View itemView) {
            super(itemView);

            // Perform find view by id lookups
            this.ivProfileImg = itemView.findViewById(R.id.ivProfileImg);
            this.tvHandle = itemView.findViewById(R.id.tvUsername);
            this.ivImage = itemView.findViewById(R.id.ivImage);
            this.tvDescription = itemView.findViewById(R.id.tvDescription);
            this.container = itemView.findViewById(R.id.clContainer);
        }
    }

    @Override
    public int getItemCount() {
        return mPosts.size();
    }

    // Clean all elements of the recycler
    public void clear() {
        mPosts.clear();
        notifyDataSetChanged();
    }


    // Add a list of items -- change to type used
    public void addAll(List<Post> list) {
        mPosts.addAll(list);
        notifyDataSetChanged();
    }

    private void animateButton(View button) {
        final Animation myAnim = AnimationUtils.loadAnimation(context, R.anim.bounce);
        // Use bounce interpolator with amplitude 0.2 and frequency 20
        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.2, 20);
        myAnim.setInterpolator(interpolator);
        button.startAnimation(myAnim);

    }

}
