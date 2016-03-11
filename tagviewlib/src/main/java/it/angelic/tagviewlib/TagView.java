package it.angelic.tagviewlib;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.ScaleAnimation;

/**
 * Created by shine@angelic.it on 11/03/2016.
 */
public class TagView extends ViewGroup {

    private Tag content;
    private int layoutBorderColor;


    public TagView(Context applicationContext, String name) {
        super(applicationContext);
        content = new Tag(name);
    }


    public TagView(Context context) {
        super(context);
    }

    public TagView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public Drawable getBackground() {
        return content.getBackground();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        ScaleAnimation anim = new ScaleAnimation(0, 1, 0, 1);
        anim.setDuration(1000);
        anim.setFillAfter(true);
        this.startAnimation(anim);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        //TODO
    }

    public void setTagId(int tagId) {
        content.setTagId(tagId);
    }

    public String getText() {
        return content.getText();
    }


    public boolean isDeletable() {
        return content.isDeletable();
    }

    public String getDeleteIcon() {
        return content.getDeleteIcon();
    }

    public float getRadius() {
        return content.getRadius();
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

    public void setDeletable(boolean deletable) {
       content.setIsDeletable(deletable);
    }

    public void setRadius(float radius) {
        content.setRadius(radius);
    }

    public void setLayoutColor(int layoutColor) {
        content.setLayoutColor(layoutColor);
    }
}
