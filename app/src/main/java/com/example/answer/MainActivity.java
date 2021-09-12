package com.example.answer;

import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

import com.example.answer.Bean.QuestionInfo;
import com.example.answer.adapter.CardFragmentPagerAdapter;
import com.example.answer.presenter.TestPresenter;

import java.util.List;

import com.example.answer.view.ITestView;

public class MainActivity extends FragmentActivity implements ITestView {

    private ViewPager viewPager;
    private TextView textView;
    private CardFragmentPagerAdapter mAdapter;
    private List<QuestionInfo> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        textView = (TextView) findViewById(R.id.textView);
        TestPresenter presenter = new TestPresenter(this);
        presenter.getData();
//        填充
//        getData()

//        updataUI(null);
    }


    @Override
    public void updataUI(List<QuestionInfo> list) {
        mAdapter = new CardFragmentPagerAdapter(getSupportFragmentManager(), list);
        viewPager.setAdapter(mAdapter);
        viewPager.setOffscreenPageLimit(3);
    }

    @Override
    public void setBottomTipView(String count) {
        textView.setText("恭喜你累计答对"+count+"题!");
    }
}