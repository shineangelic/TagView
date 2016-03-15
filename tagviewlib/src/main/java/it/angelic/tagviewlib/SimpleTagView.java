package it.angelic.tagviewlib;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.ScaleAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SimpleTagView extends LinearLayout {

    private TextView tagTextView;
    private TextView tagDeleteTextView;
    private SimpleTag content;
    private OnSimpleTagClickListener mClickListener;

    public SimpleTagView(Context context) {
        this(context, (AttributeSet) null);
    }


    public SimpleTagView(Context context, String tagName) {
        this(context, (AttributeSet) null);
        content.setName(tagName);
    }

    public SimpleTagView(Context context, AttributeSet attrs) {
        super(context, attrs);
        content = new SimpleTag();

        TypedArray arrayTheme = context.getTheme().obtainStyledAttributes(new int[] {
                android.R.attr.colorBackground,
                android.R.attr.textColorPrimaryInverse,
        });
        int backgroundDefaultColor = arrayTheme.getColor(0, 0xFF00FF);
        int textDefaultColor = arrayTheme.getColor(1, 0xFF00FF);
        arrayTheme.recycle();

        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.SimpleTagView, 0, 0);
        String titleText = a.getString(R.styleable.SimpleTagView_titleText);

        int valueColor = a.getColor(R.styleable.SimpleTagView_tagColor,
                backgroundDefaultColor);
        int valueRadius = a.getInt(R.styleable.SimpleTagView_tagRadius, 4);
        boolean valueDelete = a.getBoolean(R.styleable.SimpleTagView_isDeletable, false);

        content.setColor(valueColor);
        content.setName(titleText);
        content.setRadius(valueRadius);
        content.setIsVisible(valueDelete);
        a.recycle();

        setOrientation(LinearLayout.HORIZONTAL);
        setGravity(Gravity.CENTER_VERTICAL);

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View tutto = inflater.inflate(R.layout.view_color_option, this, true);

        tagTextView = (TextView) getChildAt(0);
        tagTextView.setText(titleText);
        tagTextView.setTextColor(textDefaultColor);


        tagDeleteTextView = (TextView) getChildAt(1);
        tagDeleteTextView.setVisibility(valueDelete ? View.VISIBLE : View.GONE);

        tutto.setBackgroundDrawable(getSelector(content));

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mClickListener != null) {
                    mClickListener.onTagClick(SimpleTagView.this);
                }
            }
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
      //  Log.d("---", content.getName());
        //int tagWidth = (int) tagTextView.getPaint().measureText(content.getName());
        //Log.d("tagWidth", Integer.toString(getWidth()));
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
       /* Log.d("widthMeasureSpec", Integer.toString(MeasureSpec.getSize(widthMeasureSpec)));
        Log.d("heightMeasureSpec", Integer.toString(MeasureSpec.getSize(heightMeasureSpec)));
        Log.d("viewWidth", Integer.toString(getMeasuredWidth()));
        Log.d("viewHeight", Integer.toString(getMeasuredHeight()));*/
    }

    @Override
    protected void onAttachedToWindow() {
        Log.d("TagView TEST", "onAttachedToWindow() :" + content.getName());
        super.onAttachedToWindow();
        ScaleAnimation anim = new ScaleAnimation(0, 1, 0, 1);
        anim.setDuration(1000);
        anim.setFillAfter(true);
        this.startAnimation(anim);

    }

    public String getText() {
        return content.getName();
    }

    public void setText(String tee) {
        content.setName(tee);
        tagTextView.setText(tee);
    }

    public int getRadius() {
        return content.getRadius();
    }

    public void setRadius(int tee) {
        content.setRadius(tee);
    }

    public int getColor() {
        return content.getColor();
    }

    public void setColor(int tee) {
        content.setColor(tee);
    }

    public boolean isDeletable() {
        return content.isVisible();
    }

    public void setDeletable(boolean isVisible) {
        content.setIsVisible(isVisible);
    }

    private Drawable getSelector(SimpleTag tag) {
        // if (tag.getBackground() != null) return tag.getBackground();
        StateListDrawable states = new StateListDrawable();
        GradientDrawable gd_normal = new GradientDrawable();
        gd_normal.setColor(getColor());
        gd_normal.setCornerRadius(getRadius());
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
