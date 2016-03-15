package it.angelic.tagviewlib;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.ScaleAnimation;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by shine@angelic.it on 11/03/2016.
 */
@Deprecated
public class TagView extends LinearLayout {


    /**
     * These are used for computing child frames based on their gravity.
     */
    private final Rect mTmpContainerRect = new Rect();
    private final Rect mTmpChildRect = new Rect();
    private final LayoutInflater mInflater;
    private Tag content;
    private int layoutBorderColor;
    /**
     * The amount of space used by children in the left gutter.
     */
    private int mLeftWidth;
    /**
     * The amount of space used by children in the right gutter.
     */
    private int mRightWidth;

    public TagView(Context applicationContext, String name) {
        super(applicationContext);
        content = new Tag(name);
        mInflater = (LayoutInflater) applicationContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    public TagView(Context context) {
        super(context);
        content = new Tag();
        content.setText("UNINIT");
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    public TagView(Context context, AttributeSet attrs) {
        super(context, attrs);
        content = new Tag();
        Log.d("TagView TEST", "TagView() con AttributeSet :"+attrs.getAttributeCount());
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // parametri XML
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.Options, 0, 0);
        String titleText = a.getString(R.styleable.Options_titleTextT);
        Log.d("TagView TEST", "TagView() titleText :"+titleText);
        setOrientation(LinearLayout.HORIZONTAL);
        setGravity(Gravity.CENTER_VERTICAL);
        int valueColor = a.getColor(R.styleable.Options_valueColorT,
                content.getTagTextColor());
        a.recycle();

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.tagview_item, this, true);

        TextView title = (TextView) getChildAt(0);
        title.setText(titleText);
        title.setTextColor(valueColor);
        title.setTextColor(content.getTagTextColor());
        title.setBackgroundColor(content.getTagTextColor());

        TextView deleteCross = (TextView) getChildAt(1);
        deleteCross.setVisibility(VISIBLE);
       // mValue.setBackgroundColor(valueColor);
    }

    @Override
    public Drawable getBackground() {
        return content.getBackground();
    }

  /*  @Override
    protected void onAttachedToWindow() {
        Log.d("TagView TEST", "onAttachedToWindow() :"+content.getText());
        super.onAttachedToWindow();
        ScaleAnimation anim = new ScaleAnimation(0, 1, 0, 1);
        anim.setDuration(1000);
        anim.setFillAfter(true);
        this.startAnimation(anim);

    }*/


    public void setTagId(int tagId) {
        content.setTagId(tagId);
    }

    public String getText() {
        return content.getText();
    }

    public void setText(String intt) {
         content.setText(intt);
    }

    public boolean isDeletable() {
        return content.isDeletable();
    }

    public void setDeletable(boolean deletable) {
        content.setIsDeletable(deletable);
    }

    public String getDeleteIcon() {
        return content.getDeleteIcon();
    }

    public float getRadius() {
        return content.getRadius();
    }

    public void setRadius(float radius) {
        content.setRadius(radius);
    }

    public float getTagTextSize() {
        return content.getTagTextSize();
    }

    public void setTagTextSize(float tagTextSize) {
        content.setTagTextSize(tagTextSize);
    }

    public int getTagTextColor() {
        return content.getTagTextColor();
    }

    public void setTagTextColor(int tagTextColor) {
        content.setTagTextColor(tagTextColor);
    }

    public int getLayoutColor() {
        return content.getLayoutColor();
    }

    public void setLayoutColor(int layoutColor) {
        content.setLayoutColor(layoutColor);
    }

    public float getLayoutBorderSize() {
        return content.getLayoutBorderSize();
    }

    public int getLayoutBorderColor() {
        return layoutBorderColor;
    }

    public int getDeleteIconColor() {
        return content.getDeleteIndicatorColor();
    }

    public float getDeleteIconSize() {
        return content.getDeleteIndicatorSize();
    }

    public int getLayoutColorPressed() {
        return content.getLayoutColorPress();
    }

}
