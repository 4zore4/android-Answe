package com.example.answer.fragment;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.QuickContactBadge;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.answer.Bean.QuestionInfo;
import com.example.answer.Bean.RankInfo;
import com.example.answer.MainActivity;
import com.example.answer.R;
import com.example.answer.api.QuestionSaveApi;
import com.example.answer.http.api.ApiListener;
import com.example.answer.http.api.ApiUtil;
import com.example.answer.view.ButtonSelectView;

import java.io.Serializable;

public class CardFragment extends Fragment {

    private View mRootView;
    private TextView contentTitle;
    private ButtonSelectView buttonSelectView1;
    private ButtonSelectView buttonSelectView2;
    private TextView TipContentTv;
    private LinearLayout tip_layout;
    private QuestionInfo mCurrentInfo;

    private MainActivity mainActivity;


    public static CardFragment newInstance(QuestionInfo questionInfo){
        CardFragment fragment = new CardFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("info", (Serializable) questionInfo);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @org.jetbrains.annotations.NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_layout,container,true);
        return mRootView;
    }

    @Override
    public void onViewCreated(@NonNull @org.jetbrains.annotations.NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        mCurrentInfo = (QuestionInfo) getArguments().getSerializable("info");
        contentTitle.setText(mCurrentInfo.getTitle());
        int type = mCurrentInfo.getType();
        int answer = Integer.valueOf(mCurrentInfo.getAnswer());
        int userOption = 1;
        if (!TextUtils.isEmpty(mCurrentInfo.getOption())){
            userOption = Integer.valueOf(mCurrentInfo.getOption());
        }

        if (type == 1){
            tip_layout.setVisibility(View.VISIBLE);
            TipContentTv.setText(mCurrentInfo.getExplain());
        }else {
            tip_layout.setVisibility(View.GONE);

        }

        buttonSelectView1.setText(mCurrentInfo.getOptions().get(0));
        buttonSelectView2.setText(mCurrentInfo.getOptions().get(1));

        buttonSelectView1.setListener(new ButtonSelectView.onButtonSelectClickListener() {
            @Override
            public void onClick() {
                saveOptionInfo(1);
            }
        });
        buttonSelectView2.setListener(new ButtonSelectView.onButtonSelectClickListener() {
            @Override
            public void onClick() {
                saveOptionInfo(2);
            }
        });

        if (type == 1){
            if (answer == 1){
                buttonSelectView1.setIcon(R.mipmap.ic_launcher);
                buttonSelectView2.setIcon(R.mipmap.ic_launcher_round);
            }else {
                buttonSelectView1.setIcon(R.mipmap.ic_launcher_round);
                buttonSelectView2.setIcon(R.mipmap.ic_launcher);
            }
            if (userOption == 1){
                buttonSelectView1.setSelect(true);
                buttonSelectView1.setSelect(false);
            }else {
                buttonSelectView1.setSelect(false);
                buttonSelectView1.setSelect(true);
            }
        }
    }

    private void saveOptionInfo(final  int option){
        new QuestionSaveApi(mCurrentInfo.getQuestion_id(),
                String.valueOf(option)).post(new ApiListener() {
            @Override
            public void success(ApiUtil api) {
                QuestionSaveApi apiBase = (QuestionSaveApi) api;
                boolean isCorrect = ((QuestionSaveApi) api).mRankInfo.getIs_correct().equals("1");
                handleButtonSelectView(option,isCorrect);

                tip_layout.setVisibility(View.VISIBLE);
                TipContentTv.setText(mCurrentInfo.getExplain());
                mainActivity.setBottomTipView(((QuestionSaveApi) api).mRankInfo.getCorrect_count());
            }

            @Override
            public void failure(ApiUtil api) {

            }
        });
    }

    public void onAttach(Context context) {

        super.onAttach(context);
        mainActivity = (MainActivity) context;
    }



    private void handleButtonSelectView(int option,boolean isCorrect){
        int rightOption = 0;
        if (option == 1){
            if (isCorrect){
                rightOption = 1;
            }else {
                rightOption = 2;
            }
            buttonSelectView1.setSelect(true);
            buttonSelectView2.setSelect(false);
        }else {
            buttonSelectView1.setSelect(false);
            buttonSelectView2.setSelect(true);
        }

        if (rightOption == 1) {
            buttonSelectView1.setIcon(R.mipmap.ic_launcher);
            buttonSelectView2.setIcon(R.mipmap.ic_launcher_round);
        }else {
            buttonSelectView1.setIcon(R.mipmap.ic_launcher_round);
            buttonSelectView2.setIcon(R.mipmap.ic_launcher);
        }

    }


    private void initView(){
        contentTitle = mRootView.findViewById(R.id.contentTitle);
        buttonSelectView1 = mRootView.findViewById(R.id.first_option_layout);
        buttonSelectView2 = mRootView.findViewById(R.id.second_option_layout);
        tip_layout = mRootView.findViewById(R.id.tip_layout);
        TipContentTv = mRootView.findViewById(R.id.tip_text);
    }
}
