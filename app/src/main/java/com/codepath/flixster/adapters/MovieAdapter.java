package com.codepath.flixster.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.codepath.flixster.DetailActivity;
import com.codepath.flixster.MainActivity;
import com.codepath.flixster.R;
import com.codepath.flixster.models.Movie;

import org.parceler.Parcels;

import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
// Recall: use Toast to print thing on phone screen (bread pops up from toaster)
// 1. Define ViewHolder
// 2. Define Adapters

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    Context context;
    List<Movie> movies; //movies data

    // Movie Constructor
    public MovieAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    // Usually involves inflating a layout from XML and returning the holder
    @NonNull
    @Override
    // Called right when adapter is created
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("MovieAdapter", "onCreateViewHolder");
        // item_movie.xml, represents one view
        View movieView = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        return new ViewHolder(movieView);
    }

    // Involves populating data into the item/view through holder
    // Recall: onCreateViewHolder creates a couple few views, onBindViewHolder constantly(when user scrolls)
    // to "refresh" the page with new data
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d("MovieAdapter", "onBindViewHolder" + position);
        // Get the movie at the passed in position
        Movie movie = movies.get(position);
        // Bind the movie data into the ViewHolder
        holder.bind(movie, position);
    }

    // Returns total count of items in the list
    @Override
    public int getItemCount() {
        return movies.size();
    }

    // ViewHolder = represents one row on the xml( movie img, title, desc)
    public class ViewHolder extends RecyclerView.ViewHolder {

        //We gotta get reference to the Viewholder stuff
        RelativeLayout container;
        TextView tvTitle;
        TextView tvOverview;
        ImageView ivPoster;
        ImageView ivPlay;

        // ViewHolder Constructor
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Grabbing references from ViewHolder
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvOverview = itemView.findViewById(R.id.tvOverview);
            ivPoster = itemView.findViewById(R.id.ivPoster);
            container = itemView.findViewById(R.id.container);
            ivPlay = itemView.findViewById(R.id.ivPlay);
        }

        // Bind (Adding) in actual data to show to screen. combines data & viewHolders
        public void bind(Movie movie, int position) {
            Log.d("Movie popularity: ", String.valueOf(movie.getRating()) + ' ' + movie.getTitle() + " Position: " + position);

            tvTitle.setText(movie.getTitle());
            tvOverview.setText(movie.getOverview());
            String imageUrl;

            // Play Icon
            if (!movie.isPopular()) {
                // Hide play icon
                ivPlay.setVisibility(View.GONE);
            } else {
                // Reveal play icon
                ivPlay.setVisibility(View.VISIBLE);
            }

            // Movie posters
            int radius = 10; // corner radius, higher value = more rounded
            int margin = 10; // crop margin, set to 0 for corners with no crop

            // if phone is in landscape
            if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                // Use Backdrop image
                imageUrl = movie.getBackdropPath();
                // binding image
                Glide.with(context)
                        .load(imageUrl)
                        .transform(new RoundedCornersTransformation(radius, margin))
                        .placeholder(R.drawable.movie_placeholder)
                        .into(ivPoster);
            } else {
                // Use poster image
                imageUrl = movie.getPosterPath();
                // binding image
                Glide.with(ivPoster)
                        .load(imageUrl)
                        .transform(new RoundedCornersTransformation(radius, margin))
                        .placeholder(R.drawable.movie_placeholder)
                        .error(R.drawable.movie_placeholder)
                        .into(ivPoster);

            }

            // NOTE: Fixed placeholder size by hardcoding ImageView (height='wrapcontent' ---> 180dp).
            //       The placeholder image was small because wrapcontent shrunk to image's original size

            // 1. Register click listener on the whole row
            container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast toast = Toast.makeText(context, Double.toString(movie.getRating()), Toast.LENGTH_SHORT);
                    toast.show();
                    // 2. Navigate to a new activity on tap (context ---> detail page)
                    Intent i = new Intent(context, DetailActivity.class);
                    // Parcels - passes data between screens
                    i.putExtra("movie", Parcels.wrap(movie)); // pass in movie object, so we dont have to pass in every indivual thing
                    // (optional) Transition - context -->
                    ActivityOptionsCompat options = ActivityOptionsCompat.
                            makeSceneTransitionAnimation((Activity) context, (ImageView) ivPoster, "profile");
                    context.startActivity(i, options.toBundle());
                }
            });
        }
    }
}
