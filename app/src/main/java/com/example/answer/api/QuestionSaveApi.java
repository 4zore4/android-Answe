package com.example.answer.api;

import com.example.answer.Bean.RankInfo;
import com.example.answer.CardContants;
import com.example.answer.http.api.ApiUtil;

import org.json.JSONObject;

public class QuestionSaveApi extends ApiUtil {

    public RankInfo mRankInfo = new RankInfo();

    public QuestionSaveApi(String question_id, String answer){
        addParam("question_id",question_id);
        addParam("answer",answer);
    }


    @Override
    protected String getUrl() {
        return CardContants.URL+"/submit";
    }

    @Override
    protected void parseData(JSONObject jsonObject) throws Exception {
        try {
            JSONObject dataInfo = (JSONObject) jsonObject.get("data");
            JSONObject rankInfo = (JSONObject)dataInfo.get("rank_info");
            mRankInfo.setIs_correct(rankInfo.optString("is_correct"));
            mRankInfo.setTotal_count(rankInfo.optString("total_count"));
            mRankInfo.setCorrect_count(rankInfo.optString("correct_count"));
        }catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void loadLocalData(JSONObject jsonObject) throws Exception {
        try {
            JSONObject dataInfo = (JSONObject) jsonObject.get("data");
            JSONObject rankInfo = (JSONObject)dataInfo.get("rank_info");
            mRankInfo.setIs_correct(rankInfo.optString("is_correct"));
            mRankInfo.setTotal_count(rankInfo.optString("total_count"));
            mRankInfo.setCorrect_count(rankInfo.optString("correct_count"));
        }catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    protected boolean isBackInMainThread() {
        return true;
    }

}
