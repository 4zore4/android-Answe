package com.example.answer.api;

import com.example.answer.Bean.QuestionInfo;
import com.example.answer.CardContants;
import com.example.answer.http.api.ApiUtil;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class HistoryQuestionGetApi extends ApiUtil {

    private List<QuestionInfo> list = new ArrayList<>();


    public List<QuestionInfo> getList() {
        return list;
    }

    public void setList(List<QuestionInfo> list) {
        this.list = list;
    }

    @Override
    protected String getUrl() {
        return CardContants.URL + "/history";
    }

    @Override
    protected void parseData(JSONObject jsonObject) throws Exception {

        try {

            JSONObject dataInfo = jsonObject.optJSONObject("data");
            JSONObject array = (JSONObject) dataInfo.get("history_list");
            if (list != null){
                list.clear();
            }
            for (int i=0;i<array.length();i++){
                QuestionInfo info = new Gson().fromJson(array.get(String.valueOf(i)).toString(),QuestionInfo.class);
                list.add(info);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //todo
    public void loadlocalData(JSONObject jsonObject) {
        try{
            JSONObject dataInfo = jsonObject.optJSONObject("data");
            JSONArray array = (JSONArray)dataInfo.get("history_list");
            if(list != null) {
                list.clear();
            }

            for (int i=0;i<array.length();i++) {
                QuestionInfo questionInfo = new Gson().fromJson(array.get(i).toString(),QuestionInfo.class);
                list.add(questionInfo);
            }
        }catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
