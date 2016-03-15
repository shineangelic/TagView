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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class SimpleTagRelativeLayout extends RelativeLayout {

    /**
     * custom layout param
     */
    int lineMargin;
    int tagMargin;
    int textPaddingLeft;
    int textPaddingRight;
    int textPaddingTop;
    int texPaddingBottom;
    /**
     * tag list
     */
    private List<SimpleTagView> mTags = new ArrayList<SimpleTagView>();
    /**
     * System Service
     */
    private LayoutInflater mInflater;
    private ViewTreeObserver mViewTreeObserber;
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


    /**
     * constructor
     *
     * @param ctx
     */
    public SimpleTagRelativeLayout(Context ctx) {
        super(ctx, null);
        initialize(ctx, null, 0);
    }

    /**
     * constructor
     *
     * @param ctx
     * @param attrs
     */
    public SimpleTagRelativeLayout(Context ctx, AttributeSet attrs) {
        super(ctx, attrs);
        initialize(ctx, attrs, 0);
    }

    /**
     * constructor
     *
     * @param ctx
     * @param attrs
     * @param defStyle
     */
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
        mInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mViewTreeObserber = getViewTreeObserver();
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
        this.lineMargin = (int) typeArray.getDimension(R.styleable.TagRelativeLayout_lineMargin, Utils.dipToPx(this.getContext(), Constants.DEFAULT_LINE_MARGIN));
        this.tagMargin = (int) typeArray.getDimension(R.styleable.TagRelativeLayout_tagMargin, Utils.dipToPx(this.getContext(), Constants.DEFAULT_TAG_MARGIN));
        this.textPaddingLeft = (int) typeArray.getDimension(R.styleable.TagRelativeLayout_textPaddingLeft, Utils.dipToPx(this.getContext(), Constants.DEFAULT_TAG_TEXT_PADDING_LEFT));
        this.textPaddingRight = (int) typeArray.getDimension(R.styleable.TagRelativeLayout_textPaddingRight, Utils.dipToPx(this.getContext(), Constants.DEFAULT_TAG_TEXT_PADDING_RIGHT));
        this.textPaddingTop = (int) typeArray.getDimension(R.styleable.TagRelativeLayout_textPaddingTop, Utils.dipToPx(this.getContext(), Constants.DEFAULT_TAG_TEXT_PADDING_TOP));
        this.texPaddingBottom = (int) typeArray.getDimension(R.styleable.TagRelativeLayout_textPaddingBottom, Utils.dipToPx(this.getContext(), Constants.DEFAULT_TAG_TEXT_PADDING_BOTTOM));
        typeArray.recycle();
    }

    /**
     * onSizeChanged
     *
     * @param w
     * @param h
     * @param oldw
     * @param oldh
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int size = 0;
        mWidth = getMeasuredWidth();
        mHeight= getMeasuredHeight();
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
        // clear all tag
        removeAllViews();

        // layout padding left & layout padding right
        float total = getPaddingLeft() + getPaddingRight();

        int listIndex = 1;// List Index
        int index_bottom = 1;// The Tag to add below
        int index_header = 1;// The header tag of this line
        try {
              index_bottom = mTags.get(0).getId();// The Tag to add below
              index_header = mTags.get(0).getId();// The header tag of this line
        }catch(Exception e){
            Log.w("", "Empty list");
        }

        SimpleTagView tag_pre = null;
        Log.d("TagView TEST", "Drawing Tags: " + mTags.size());
        for (final SimpleTagView item : mTags) {
            final int position = listIndex - 1;
            // inflate tag layout
            //TagView tagLayout = tag;
            //  tagLayout.setTagId(listIndex);
            // tagLayout.setBackgroundDrawable(getSelector(tag));
            Log.d("TagView TEST", "re-adding: " + item.getText()+ " id: "+item.getId());
            //tagLayout.sett
            // tag text
            TextView tagTextView = (TextView) item.findViewById(R.id.tagName);
            item.setText(item.getText());
            item.setId(position);
            //tagTextView.setTextColor(tag.getTagTextColor());
            //  tagTextView.setPadding(textPaddingLeft, textPaddingTop, textPaddingRight, texPaddingBottom);
            // ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) tagTextView.getLayoutParams();

            //tagTextView.setLayoutParams(tagParams);

            // tagTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, tag.getTagTextSize());
            item.setOnTagClickListener(new OnSimpleTagClickListener() {
                @Override
                public void onTagClick(SimpleTagView tag) {
                    if (mClickListener != null) {
                        mClickListener.onTagClick(tag);
                    }
                }

            });
            int sss = (int) tagTextView.getPaint().measureText(item.getText());
            // int sss = (int) tagTextView.getPaint().gett
            item.measure(sss,40);
            // calculate　of tag layout width
            float tagWidth = item.getMeasuredWidth() + textPaddingLeft + textPaddingRight;
            // tagTextView padding (left & right)
            Log.d("TagView TEST", "re-adding "+item.getText()+": tagWidth=" + tagWidth+" mHeight=" + item.getMeasuredHeight());
            // deletable text
            TextView deletableView = (TextView) item.findViewById(R.id.isDeletable);
           /* if (item.isDeletable()) {
                // deletableView.setVisibility(View.VISIBLE);
                //deletableView.setText(tag.getDeleteIcon());
                int offset = Utils.dipToPx(getContext(), 2f);
                deletableView.setPadding(offset, textPaddingTop, textPaddingRight + offset, texPaddingBottom);
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) deletableView.getLayoutParams();
                params.setMargins(offset, textPaddingTop, textPaddingRight + offset, texPaddingBottom);
                deletableView.setLayoutParams(params);
                deletableView.setTextColor(item.getColor());
                //deletableView.setTextSize(TypedValue.COMPLEX_UNIT_SP, tag.getDeleteIconSize());
                deletableView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //TagView.this.remove(position);
                        if (mDeleteListener != null) {
                            SimpleTagView targetTag = item;
                            mDeleteListener.onTagDeleted(SimpleTagRelativeLayout.this, targetTag, position);
                        }
                    }
                });
                tagWidth += deletableView.getPaint().measureText("×") + textPaddingLeft + textPaddingRight;
                // deletableView Padding (left & right)
            } else {
                deletableView.setVisibility(View.GONE);
            }*/

            LayoutParams tagParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            //RelativeLayout.LayoutParams tagParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            //TODO on child
            tagParams.setMargins(textPaddingLeft, textPaddingTop, textPaddingRight, texPaddingBottom);
            // LayoutParams tagParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            tagParams.setMargins(0, 0, 0, 0);

            //add margin of each line
            tagParams.bottomMargin = lineMargin;
            Log.d("TagView TEST", "adding. mWidth:" + mWidth+" total:" + total);
            if (mWidth <= total + tagWidth + Utils.dipToPx(this.getContext(), Constants.LAYOUT_WIDTH_OFFSET)) {
                //need to add in new line
                Log.d("TagView TEST", "new line.BELOW index_bottom:" + index_bottom);
                tagParams.addRule(RelativeLayout.BELOW, index_bottom);
                // initialize total param (layout padding left & layout padding right)
                total = getPaddingLeft() + getPaddingRight();
                index_bottom = item.getId();
                index_header = item.getId();
            } else {
                //no need to new line
                Log.d("TagView TEST", "NO new line. index_header:" + index_header+" me:"+item.getId());
                tagParams.addRule(RelativeLayout.ALIGN_TOP, index_header);
                //not header of the line
                if (tag_pre != null && item.getId() != index_header) {
                    Log.d("TagView TEST", "NO new line NO HEAD line. RIGHT_OF:" + tag_pre.getId());
                    tagParams.addRule(RelativeLayout.RIGHT_OF, tag_pre.getId());
                    tagParams.leftMargin = tagMargin;
                    total += tagMargin;
                    if (tag_pre.getMeasuredWidth() < item.getMeasuredWidth()) {
                        index_bottom = item.getId();
                    }
                }
            }
            total += tagWidth;
            measure(mWidth, mHeight);
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
     * @param tag
     */
    public void addTag(SimpleTagView tag) {
        mTags.add(tag);
        drawTags();
    }

    public void addTags(ArrayList<SimpleTagView> tags) {
        if (tags == null) return;
        mTags = tags;
        drawTags();
    }


    public void addTags(SimpleTagView[] tags) {
        if (tags == null) return;
        for (SimpleTagView item : tags) {
            addTag(item);
        }
    }
    /*public void addTags(ArrayList<String> tags){
		if (tags==null)return;
		for(String item:tags){
			Tag tag = new Tag(item);
			addTag(tag);
		}
	}*/

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
     * @param position
     */
    public void remove(int position) {
        if (position < mTags.size()) {
            mTags.remove(position);
            drawTags();
        }
    }

    /**
     *
     */
    public void removeAll() {
        removeAllViews();
        mTags.clear();
    }

    public int getLineMargin() {
        return lineMargin;
    }

    public void setLineMargin(float lineMargin) {
        this.lineMargin = Utils.dipToPx(getContext(), lineMargin);
    }

    public int getTagMargin() {
        return tagMargin;
    }

    public void setTagMargin(float tagMargin) {
        this.tagMargin = Utils.dipToPx(getContext(), tagMargin);
    }

    public int getTextPaddingLeft() {
        return textPaddingLeft;
    }

    public void setTextPaddingLeft(float textPaddingLeft) {
        this.textPaddingLeft = Utils.dipToPx(getContext(), textPaddingLeft);
    }

    public int getTextPaddingRight() {
        return textPaddingRight;
    }

    public void setTextPaddingRight(float textPaddingRight) {
        this.textPaddingRight = Utils.dipToPx(getContext(), textPaddingRight);
    }

    public int getTextPaddingTop() {
        return textPaddingTop;
    }

    public void setTextPaddingTop(float textPaddingTop) {
        this.textPaddingTop = Utils.dipToPx(getContext(), textPaddingTop);
    }

    public int getTexPaddingBottom() {
        return texPaddingBottom;
    }

    public void setTexPaddingBottom(float texPaddingBottom) {
        this.texPaddingBottom = Utils.dipToPx(getContext(), texPaddingBottom);
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
