package com.example.answer.presenter;

import android.content.Context;

import com.example.answer.Bean.QuestionInfo;
import com.example.answer.api.GetQuestionInfoApi;
import com.example.answer.api.HistoryQuestionGetApi;
import com.example.answer.http.api.ApiListener;
import com.example.answer.http.api.ApiUtil;

import java.util.List;

import com.example.answer.view.ITestView;

public class TestPresenter {

    private ITestView mITestView;
    private Context mContext;
    private List<QuestionInfo> mList;
    private List<QuestionInfo> mHistoryList;
    private QuestionInfo mCurrentInfo;


    public TestPresenter(ITestView iTestView) {
        mContext = (Context) iTestView;
        mITestView = iTestView;
    }

    public void getData(){
        //获取data放到list里面
        getHistory();
    }

    private void getHistory(){
        new HistoryQuestionGetApi().get(mContext, new ApiListener() {
            @Override
            public void success(ApiUtil api) {
                HistoryQuestionGetApi apiBase = (HistoryQuestionGetApi) api;
                mHistoryList = apiBase.getList();
                getCurrentQuestionApi();
            }

            @Override
            public void failure(ApiUtil api) {

            }
        });
    }

    private void getCurrentQuestionApi(){
        new GetQuestionInfoApi().get(mContext, new ApiListener() {
            @Override
            public void success(ApiUtil api) {
                GetQuestionInfoApi apiBase = (GetQuestionInfoApi) api;
                mCurrentInfo = apiBase.getInfo();
                mHistoryList.add(0,mCurrentInfo);
                refreshData();
            }

            @Override
            public void failure(ApiUtil api) {

            }
        });
    }

    private void refreshData(){
        if (mITestView != null){
            mITestView.updataUI(mHistoryList);
        }
    }

}
