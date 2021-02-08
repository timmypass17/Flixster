package com.codepath.flixster.adapters;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.codepath.flixster.R;

public class PopularViewHolder extends RecyclerView.ViewHolder {

    private ImageView image1;

    public PopularViewHolder(@NonNull View itemView) {
        super(itemView);
        image1 = (ImageView) itemView.findViewById(R.id.image1);
    }

    public ImageView getImage1() {
        return image1;
    }

    public void setImage1(ImageView image1) {
        this.image1 = image1;
    }
}
