package com.example.answer.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.answer.Bean.QuestionInfo;
import com.example.answer.fragment.CardFragment;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CardFragmentPagerAdapter extends FragmentStatePagerAdapter {

    private List<QuestionInfo> mList;

    public CardFragmentPagerAdapter(@NonNull @NotNull FragmentManager fm,List<QuestionInfo> list) {
        super(fm);
        this.mList = list;
    }


    @NonNull
    @NotNull
    @Override
    public Fragment getItem(int position) {
        return CardFragment.newInstance(mList.get(position));
    }

    @Override
    public int getCount() {
        return this.mList.size();
    }
}
