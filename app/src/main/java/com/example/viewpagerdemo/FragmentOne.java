package com.example.viewpagerdemo;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.viewpagerdemo.boxoffice.BoxOfficeAdapter;
import com.example.viewpagerdemo.boxoffice.BoxOfficeResult;
import com.example.viewpagerdemo.boxoffice.BoxOfficeService;
import com.example.viewpagerdemo.boxoffice.Const;
import com.example.viewpagerdemo.boxoffice.RestClient;
import com.example.viewpagerdemo.boxoffice.Result;
import com.example.viewpagerdemo.boxoffice.WeeklyBoxOfficeList;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentOne extends Fragment {
    private String TAG = FragmentOne.class.getSimpleName();

    private BoxOfficeService boxOfficeService;
    private RecyclerView boxOfficeRecycler;
    private Const aConst;
    private Context context;

    private List<WeeklyBoxOfficeList> weeklyBoxOfficeLists = new ArrayList<>();
    private BoxOfficeAdapter boxOfficeAdapter;

    public FragmentOne() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_one, container, false);
        context = container.getContext();
        boxOfficeRecycler = view.findViewById(R.id.boxoffice_recycler);
        initValues();
        showBoxOffice();
        return view;
    }


    void initValues(){
        boxOfficeService = RestClient.buildHTTPClient();
    }

    String getCurrentDate(){
        Date currentTime = Calendar.getInstance().getTime();
        String currentDate = new SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(currentTime);
    return currentDate;
    }

    void showBoxOffice(){
        boxOfficeService.getBoxOffice(aConst.KEY,getCurrentDate()).enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                if(response.isSuccessful()){
                    Result result = response.body();
                    BoxOfficeResult boxOfficeResult = result.getBoxOfficeResult();
                    List<WeeklyBoxOfficeList> weeklyBoxOfficeList2 = boxOfficeResult.getWeeklyBoxOfficeList();
                    for(WeeklyBoxOfficeList weeklyBoxOffice : weeklyBoxOfficeList2){
                        weeklyBoxOfficeLists.add(weeklyBoxOffice);
                    }

                    boxOfficeAdapter = new BoxOfficeAdapter(weeklyBoxOfficeLists, getActivity());
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false);
                    boxOfficeRecycler.setLayoutManager(linearLayoutManager);
                    boxOfficeRecycler.setAdapter(boxOfficeAdapter);
                    Log.i(TAG, "Response: " + response.body());
                }
                if(!response.isSuccessful()){
                    Toast.makeText(context, "데이터를 가져오지 못했습니다.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {

            }
        });
    }
}
