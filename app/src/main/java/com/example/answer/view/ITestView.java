package com.example.answer.view;

import com.example.answer.Bean.QuestionInfo;

import java.util.List;

public interface ITestView {
    void updataUI(List<QuestionInfo> list);

    void setBottomTipView(String count);
}
