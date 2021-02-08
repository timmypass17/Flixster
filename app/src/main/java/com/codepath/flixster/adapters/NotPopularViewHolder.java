package com.codepath.flixster.adapters;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.codepath.flixster.R;

public class NotPopularViewHolder extends RecyclerView.ViewHolder {
    private ImageView image3;

    public NotPopularViewHolder(@NonNull View itemView) {
        super(itemView);
        image3 = (ImageView) itemView.findViewById(R.id.image3);
    }

    public ImageView getImage3() {
        return image3;
    }

    public void setImage3(ImageView image3) {
        this.image3 = image3;
    }
}
