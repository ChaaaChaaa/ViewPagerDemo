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

      public static FragmentOne newInstance() {
        Bundle args = new Bundle();

        FragmentOne fragment = new FragmentOne();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_one, container, false);
        context = container.getContext();
        boxOfficeRecycler = view.findViewById(R.id.boxoffice_recycler);
        initValues();
        //뺴액!!!!!!!!!!!!!!!!!!!!!!!!!!!!! OncreateView에서 이런 작업 하지말라했는데!!!!!!!!!ㅋㅋㅋㅋㅋㅋㅋ
        showBoxOffice();
        return view;
    }


    void initValues() {
        boxOfficeService = RestClient.buildHTTPClient();
    }

    //메서드 접근차 빼먹지 말기
    public String getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -10);
        //데이터 못 받아 오는 이유는 날짜.. 이번주 박스오피스는 아직 안 나옴니다. 10일 전쯤으로 하면 아마 지난주 박스오피스가 나오겠죠?
        Date currentTime = calendar.getTime();
        String currentDate = new SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(currentTime);
        return currentDate;
    }

    void showBoxOffice() {
        // 네트워크 통신은 워커스래드에서 하기로 했~~
        boxOfficeService.getBoxOffice(aConst.KEY, getCurrentDate()).enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                if (response.isSuccessful()) {
                    Result result = response.body();
                    BoxOfficeResult boxOfficeResult = result.getBoxOfficeResult();
                    List<WeeklyBoxOfficeList> weeklyBoxOfficeList2 = boxOfficeResult.getWeeklyBoxOfficeList();
                    for (WeeklyBoxOfficeList weeklyBoxOffice : weeklyBoxOfficeList2) {
                        weeklyBoxOfficeLists.add(weeklyBoxOffice);
                    }

                    boxOfficeAdapter = new BoxOfficeAdapter(weeklyBoxOfficeLists, getActivity());
                    //굳이 프래그먼트에선 getActivity 컨택스트 안 끌고오기 , 꼭 필요한 상황 아니면
                    // LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
                    boxOfficeRecycler.setLayoutManager(linearLayoutManager);
                    boxOfficeRecycler.setAdapter(boxOfficeAdapter);
                    Log.i(TAG, "Response: " + response.body());
                }
                //야레야레 이프문 이렇게 if if if if no no
                else {
                    Toast.makeText(context, "데이터를 가져오지 못했습니다.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                //여기는 왜 비워두셨을까욥~ 아예 실패했을때도 분키처리 해줘야하지요~
                t.printStackTrace();
            }
        });
    }
}
