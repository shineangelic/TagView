package it.angelic.tagviewlib;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SimpleTagView extends LinearLayout {

    private TextView mValue;
    private TextView mImage;
    private SimpleTag content;
    private OnSimpleTagClickListener mClickListener;

    public SimpleTagView(Context context, AttributeSet attrs) {
        super(context, attrs);

        content = new SimpleTag();
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.SimpleTagView, 0, 0);
        String titleText = a.getString(R.styleable.SimpleTagView_titleText);
        int valueColor = a.getColor(R.styleable.SimpleTagView_valueColor,
                Color.CYAN);
        int valueRadius = a.getColor(R.styleable.SimpleTagView_valueRadius,
                2);
        boolean valueDelete = a.getBoolean(R.styleable.SimpleTagView_valueDeletable,
                false);
        content.setColor(valueColor);
        a.recycle();

        setOrientation(LinearLayout.HORIZONTAL);
        setGravity(Gravity.CENTER_VERTICAL);

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View tutto = inflater.inflate(R.layout.view_color_option, this, true);

        mValue = (TextView) getChildAt(0);
        mValue.setText(titleText);
        content.setName(titleText);
        content.setRadius(valueRadius);
        content.setIsVisible(valueDelete);

        mImage = (TextView) getChildAt(1);
        mImage.setVisibility(valueDelete?View.VISIBLE:View.GONE);

        tutto.setBackgroundDrawable(getSelector(content));

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mClickListener != null) {
                    mClickListener.onTagClick(content);
                }
            }
        });
    }

    public SimpleTagView(Context context) {
        this(context, null);
    }

    public void setText(String tee) {
        content.setName(tee);
        mValue.setText(tee);
    }

    public void setImageVisible(boolean visible) {
        mImage.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    private Drawable getSelector(SimpleTag tag) {
        // if (tag.getBackground() != null) return tag.getBackground();
        StateListDrawable states = new StateListDrawable();
        GradientDrawable gd_normal = new GradientDrawable();
        gd_normal.setColor(tag.getColor());
        gd_normal.setCornerRadius(content.getRadius());
        //if (tag.getLayoutBorderSize() > 0) {
        //  gd_normal.setStroke(Utils.dipToPx(getContext(), tag.getLayoutBorderSize()), tag.getLayoutBorderColor());
        //}
    /*GradientDrawable gd_press = new GradientDrawable();
    gd_press.setColor(tag.getLayoutColorPressed());
    gd_press.setCornerRadius(tag.getRadius());
    states.addState(new int[]{android.R.attr.state_pressed}, gd_press);*/
        //must add state_pressed first，or state_pressed will not take effect
        states.addState(new int[]{}, gd_normal);
        return states;
    }

    /**
     * setter for OnTagSelectListener
     *
     * @param clickListener
     */
    public void setOnTagClickListener(OnSimpleTagClickListener clickListener) {
        mClickListener = clickListener;
    }
} 
