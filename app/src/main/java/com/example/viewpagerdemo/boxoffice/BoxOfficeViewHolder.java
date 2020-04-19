package com.example.viewpagerdemo.boxoffice;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.viewpagerdemo.R;


public class BoxOfficeViewHolder extends RecyclerView.ViewHolder {


    TextView rankTextView;
    TextView movieNameTextView;
    TextView visitorsPerDay;
    TextView cumulativeVisitorsCount;

    public BoxOfficeViewHolder(@NonNull View itemView) {
        super(itemView);
        rankTextView = itemView.findViewById(R.id.rank);
        movieNameTextView = itemView.findViewById(R.id.movie_name);
        visitorsPerDay = itemView.findViewById(R.id.visitors_PerDay);
        cumulativeVisitorsCount = itemView.findViewById(R.id.cumulative_Visitors_Count);
    }


}
