package com.example.viewpagerdemo.boxoffice;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.viewpagerdemo.R;

import java.util.ArrayList;
import java.util.List;

public class BoxOfficeAdapter extends RecyclerView.Adapter<BoxOfficeViewHolder> {
    List<WeeklyBoxOfficeList> weeklyBoxOfficeListLists = new ArrayList<>();
    Context context;

    public BoxOfficeAdapter(List<WeeklyBoxOfficeList> weeklyBoxOfficeLists, Context context){
        this.weeklyBoxOfficeListLists = weeklyBoxOfficeLists;
        this.context = context;
        Log.d("testtest3",String.valueOf(weeklyBoxOfficeLists));
    }



    @NonNull
    @Override
    public BoxOfficeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View rootView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.boxoffice_item,viewGroup,false);
        BoxOfficeViewHolder boxOfficeViewHolder = new BoxOfficeViewHolder(rootView);
        return boxOfficeViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BoxOfficeViewHolder boxOfficeViewHolder, int position) {
        WeeklyBoxOfficeList weeklyBoxOfficeList = weeklyBoxOfficeListLists.get(position);
        boxOfficeViewHolder.rankTextView.setText(weeklyBoxOfficeList.getRank()+"");
        boxOfficeViewHolder.movieNameTextView.setText(weeklyBoxOfficeList.getMovieNm());
        boxOfficeViewHolder.visitorsPerDay.setText(weeklyBoxOfficeList.getAudiCnt()); //일일 관객수
        boxOfficeViewHolder.cumulativeVisitorsCount.setText(weeklyBoxOfficeList.getAudiAcc()); //누적관객수

        Log.d("testtest4",String.valueOf(weeklyBoxOfficeList.getAudiAcc()));
    }

    @Override
    public int getItemCount() {
        return weeklyBoxOfficeListLists.size();
    }
}
