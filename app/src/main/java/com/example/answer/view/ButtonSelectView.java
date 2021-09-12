package com.example.answer.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.answer.R;
import com.google.android.material.button.MaterialButtonToggleGroup;

public class ButtonSelectView extends RelativeLayout {

    private String mText;
    private Drawable mDrawable;
    private TextView textView;
    private ImageView imageView;

    private onButtonSelectClickListener mListener;

    public ButtonSelectView(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.button_select_layout,this);
        TypedArray typedArray = context.obtainStyledAttributes(attrs,R.styleable.ButtonSelectView);
        mText = typedArray.getString(R.styleable.ButtonSelectView_text);
        mDrawable = typedArray.getDrawable(R.styleable.ButtonSelectView_icon_left);

        textView = findViewById(R.id.tv_option1);
        imageView = findViewById(R.id.img_option1);
        textView.setText(mText);
        imageView.setImageDrawable(mDrawable);
        textView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if (mListener != null){
                    mListener.onClick();
                }
            }
        });
    }

    public void setIcon(int resource){
        imageView.setImageResource(resource);
    }

    public void setText(String text){
        textView.setText(text);
    }

    public void setButtonState(String text,int resource){
        setText(text);
        setIcon(resource);
    }

    public void enabled(boolean enable){
        textView.setEnabled(enable);
        setEnabled(false);
    }

    public void setSelect(boolean select){
        textView.setSelected(select);
    }

    public interface onButtonSelectClickListener{
        void onClick();
    }

    public void setListener(onButtonSelectClickListener clickListener){
        this.mListener = clickListener;

    }


}
