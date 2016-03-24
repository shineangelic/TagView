package it.angelic.tagviewlib;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class SimpleTagRelativeLayout extends RelativeLayout {

    /**
     * custom layout param
     */
    private int lineMargin;
    private int tagMargin;
    private int textPaddingLeft;
    private int textPaddingRight;
    private int textPaddingTop;
    private int texPaddingBottom;
    /**
     * tag list
     */
    private List<SimpleTagView> mTags = new ArrayList<SimpleTagView>();
    /**
     * listener
     */
    private OnSimpleTagClickListener mClickListener;
    private OnSimpleTagDeleteListener mDeleteListener;
    /**
     * view size param
     */
    private int mWidth;
    private int mHeight;
    /**
     * layout initialize flag
     */
    private boolean mInitialized = false;

    public SimpleTagRelativeLayout(Context ctx) {
        super(ctx, null);
        initialize(ctx, null, 0);
    }

    public SimpleTagRelativeLayout(Context ctx, AttributeSet attrs) {
        super(ctx, attrs);
        initialize(ctx, attrs, 0);
    }

    public SimpleTagRelativeLayout(Context ctx, AttributeSet attrs, int defStyle) {
        super(ctx, attrs, defStyle);
        initialize(ctx, attrs, defStyle);
    }

    /**
     * initalize instance
     *
     * @param ctx
     * @param attrs
     * @param defStyle
     */
    private void initialize(Context ctx, AttributeSet attrs, int defStyle) {
        /*
      System Service
     */
        LayoutInflater mInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewTreeObserver mViewTreeObserber = getViewTreeObserver();
        mViewTreeObserber.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (!mInitialized) {
                    mInitialized = true;
                    drawTags();
                }
            }
        });
        // get AttributeSet
        TypedArray typeArray = ctx.obtainStyledAttributes(attrs, R.styleable.TagRelativeLayout, defStyle, defStyle);
        this.lineMargin = (int) typeArray.getDimension(R.styleable.TagRelativeLayout_lineMargin, SimpleTagViewUtils.dipToPx(this.getContext(), Constants.DEFAULT_LINE_MARGIN));
        this.tagMargin = (int) typeArray.getDimension(R.styleable.TagRelativeLayout_tagMargin, SimpleTagViewUtils.dipToPx(this.getContext(), Constants.DEFAULT_TAG_MARGIN));
        this.textPaddingLeft = (int) typeArray.getDimension(R.styleable.TagRelativeLayout_textPaddingLeft, SimpleTagViewUtils.dipToPx(this.getContext(), Constants.DEFAULT_TAG_TEXT_PADDING_LEFT));
        this.textPaddingRight = (int) typeArray.getDimension(R.styleable.TagRelativeLayout_textPaddingRight, SimpleTagViewUtils.dipToPx(this.getContext(), Constants.DEFAULT_TAG_TEXT_PADDING_RIGHT));
        this.textPaddingTop = (int) typeArray.getDimension(R.styleable.TagRelativeLayout_textPaddingTop, SimpleTagViewUtils.dipToPx(this.getContext(), Constants.DEFAULT_TAG_TEXT_PADDING_TOP));
        this.texPaddingBottom = (int) typeArray.getDimension(R.styleable.TagRelativeLayout_textPaddingBottom, SimpleTagViewUtils.dipToPx(this.getContext(), Constants.DEFAULT_TAG_TEXT_PADDING_BOTTOM));
        typeArray.recycle();

        mWidth = getWidth();
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int parentWidth = MeasureSpec.getSize(widthMeasureSpec);
        int parentHeight = MeasureSpec.getSize(heightMeasureSpec);
        this.setMeasuredDimension(parentWidth, parentHeight);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = parentWidth;
        mHeight = parentHeight;
        mWidth = getMeasuredWidth();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawTags();
    }

    /**
     * tag draw
     */
    private void drawTags() {

        if (!mInitialized) {
            Log.e("TagView TEST", "INIT MISS");
            return;
        }
        // clear all tag, discutibile
        removeAllViews();

        // layout padding left & layout padding right
        float total = getPaddingLeft() + getPaddingRight();

        int listIndex = 0;// List Index
        int indexBottom = 1;// The Tag to add below
        int indexHeader = 1;// The header tag of this line

        SimpleTagView tag_pre = null;
        Log.d("TagView TEST", "Drawing Tags: " + mTags.size());
        for (final SimpleTagView item : mTags) {
            final int position = listIndex;
            // inflate tag layout
            //TagView tagLayout = tag;
            //  tagLayout.setTagId(listIndex);
            // tagLayout.setBackgroundDrawable(getSelector(tag));

            //tagLayout.sett
            // tag text
            TextView tagTextView = (TextView) item.findViewById(R.id.tagName);
            item.setText(item.getText());
            item.setId(position + 1);//inside Rel Layout, id are rewritten

            item.setOnSimpleTagClickListener(new OnSimpleTagClickListener() {
                @Override
                public void onSimpleTagClick(SimpleTagView tag) {
                    if (mClickListener != null) {
                        mClickListener.onSimpleTagClick(tag);
                    }
                }

            });

            int sHint = (int) tagTextView.getPaint().measureText(item.getText());
            item.measure(sHint, 40);
            // calculate　of tag layout width
            float tagWidth = item.getMeasuredWidth() ;
            // tagTextView padding (left & right)
            Log.d("TagView TEST", "re-adding " + item.getText() + " id: " + item.getId() + "- tagWidth=" + tagWidth + " mHeight=" + item.getMeasuredHeight());
            // deletable text
            TextView deletableView = (TextView) item.findViewById(R.id.isDeletable);
            if (item.isDeletable()) {
                // attach delete Listener
                deletableView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //TagView.this.remove(position);
                        if (mDeleteListener != null) {
                            mDeleteListener.onTagDeleted( item   );
                        }
                    }
                });
                tagWidth += deletableView.getPaint().measureText("×")  ;
                // deletableView Padding (left & right)
            } else {
                deletableView.setVisibility(View.GONE);
            }

            LayoutParams tagParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            tagParams.setMargins(0, 0, 0, 0);

            //add margin of each line
            tagParams.bottomMargin = lineMargin;
            Log.d("TagView TEST", "adding. mWidth:" + mWidth + " total:" + total);
            if (mWidth <= total + tagWidth + SimpleTagViewUtils.dipToPx(this.getContext(), Constants.LAYOUT_WIDTH_OFFSET)) {
                //need to add in new line
                Log.d("TagView TEST", "new line.BELOW indexBottom:" + indexBottom);
                tagParams.addRule(RelativeLayout.BELOW, indexBottom);
                // initialize total param (layout padding left & layout padding right)
                total = getPaddingLeft() + getPaddingRight();
                indexBottom = item.getId();
                indexHeader = item.getId();
            } else {
                //no need to new line
                Log.d("TagView TEST", "NO new line. indexHeader:" + indexHeader + " me:" + item.getId());
                tagParams.addRule(RelativeLayout.ALIGN_TOP, indexHeader);
                //not header of the line
                if (tag_pre != null && item.getId() != indexHeader) {
                    Log.d("TagView TEST", "NO new line NO HEAD line. RIGHT_OF:" + tag_pre.getId());
                    tagParams.addRule(RelativeLayout.RIGHT_OF, tag_pre.getId());
                    tagParams.leftMargin = tagMargin;
                    total += tagMargin;
                    if (tag_pre.getMeasuredWidth() < item.getMeasuredWidth()) {
                        Log.d("TagView TEST", "NEXT will wrap, indexBottom:" + item.getId());
                        indexBottom = item.getId();
                    }
                }
            }
            total += tagWidth;
            //measure(mWidth, mHeight);
            item.setLayoutParams(tagParams);
            addView(item);
            Log.d("TagView TEST", "getMeasuredWidth after add:" + item.getMeasuredWidth());
            tag_pre = item;
            listIndex++;
        }
    }


    //public methods
    //----------------- separator  -----------------//

    /**
     * Add a single tag, the draw them all
     * @param tag
     */
    public void addTag(SimpleTagView tag) {
        mTags.add(tag);
        drawTags();
    }

    public void setTags(ArrayList<SimpleTagView> tags) {
        if (tags == null) return;
        mTags = tags;
        drawTags();
    }

    /**
     * Add a tag array, then draw them all
     * @param tags to be added to existing one
     */
    public void setTags(SimpleTagView[] tags) {
        if (tags == null) return;
        for (SimpleTagView item : tags) {
            addTag(item);
        }
        drawTags();
    }

    /**
     * get tag list
     *
     * @return mTags TagObject List
     */
    public List<SimpleTagView> getTags() {
        return mTags;
    }

    /**
     * remove tag
     *
     * @param position index of element to remove
     */
    public void remove(int position) {
        if (position < mTags.size()) {
            mTags.remove(position);
            drawTags();
        }
    }

    /**
     * remove tag
     *
     * @param position
     */
    public void remove(SimpleTagView position) {
            mTags.remove(position);
            drawTags();
        }

    public void removeAll() {
        removeAllViews();
        mTags.clear();
        drawTags();
    }

    public int getLineMargin() {
        return lineMargin;
    }

    public void setLineMargin(float lineMargin) {
        this.lineMargin = SimpleTagViewUtils.dipToPx(getContext(), lineMargin);
    }

    public int getTagMargin() {
        return tagMargin;
    }

    public void setTagMargin(float tagMargin) {
        this.tagMargin = SimpleTagViewUtils.dipToPx(getContext(), tagMargin);
    }

    public int getTextPaddingLeft() {
        return textPaddingLeft;
    }

    public void setTextPaddingLeft(float textPaddingLeft) {
        this.textPaddingLeft = SimpleTagViewUtils.dipToPx(getContext(), textPaddingLeft);
    }

    public int getTextPaddingRight() {
        return textPaddingRight;
    }

    public void setTextPaddingRight(float textPaddingRight) {
        this.textPaddingRight = SimpleTagViewUtils.dipToPx(getContext(), textPaddingRight);
    }

    public int getTextPaddingTop() {
        return textPaddingTop;
    }

    public void setTextPaddingTop(float textPaddingTop) {
        this.textPaddingTop = SimpleTagViewUtils.dipToPx(getContext(), textPaddingTop);
    }

    public int getTexPaddingBottom() {
        return texPaddingBottom;
    }

    public void setTexPaddingBottom(float texPaddingBottom) {
        this.texPaddingBottom = SimpleTagViewUtils.dipToPx(getContext(), texPaddingBottom);
    }

    /**
     * setter for OnTagSelectListener
     *
     * @param clickListener
     */
    public void setOnSimpleTagClickListener(OnSimpleTagClickListener clickListener) {
        mClickListener = clickListener;
    }

    /**
     * setter for OnTagDeleteListener
     *
     * @param deleteListener
     */
    public void setOnSimpleTagDeleteListener(OnSimpleTagDeleteListener deleteListener) {
        mDeleteListener = deleteListener;
    }

}
