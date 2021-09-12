package com.example.answer.api;

import com.example.answer.Bean.QuestionInfo;
import com.example.answer.CardContants;
import com.example.answer.http.api.ApiUtil;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.lang.reflect.Type;

public class GetQuestionInfoApi extends ApiUtil {

    private QuestionInfo info;

    public QuestionInfo getInfo() {
        return info;
    }

    public void setInfo(QuestionInfo info) {
        this.info = info;
    }

    @Override
    protected String getUrl() {
        return CardContants.URL + "/getQuestion";
    }

    @Override
    protected void parseData(JSONObject jsonObject) throws Exception {

        try {

            JSONObject data = jsonObject.optJSONObject("data");
            JSONObject info = jsonObject.optJSONObject("info");
            info = new Gson().fromJson(info.toString(), (Type) QuestionInfo.class);


        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
