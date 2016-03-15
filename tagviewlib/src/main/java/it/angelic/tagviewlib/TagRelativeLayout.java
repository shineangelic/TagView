package it.angelic.tagviewlib;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

@Deprecated
public class TagRelativeLayout extends RelativeLayout {

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
    private List<TagView> mTags = new ArrayList<TagView>();
    /**
     * System Service
     */
    private LayoutInflater mInflater;
    private ViewTreeObserver mViewTreeObserber;
    /**
     * listener
     */
    /**
     * view size param
     */
    private int mWidth;
    /**
     * layout initialize flag
     */
    private boolean mInitialized = false;


    /**
     * constructor
     *
     * @param ctx
     */
    public TagRelativeLayout(Context ctx) {
        super(ctx, null);
        initialize(ctx, null, 0);
    }

    /**
     * constructor
     *
     * @param ctx
     * @param attrs
     */
    public TagRelativeLayout(Context ctx, AttributeSet attrs) {
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
    public TagRelativeLayout(Context ctx, AttributeSet attrs, int defStyle) {
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
        int width = getMeasuredWidth();
        if (width <= 0) return;
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
        TagView tag_pre = null;
        Log.d("TagView TEST", "Drawing Tags: "+mTags.size());
        for (TagView item : mTags) {
            final int position = listIndex - 1;
            final TagView tag = item;

            // inflate tag layout
            TagView tagLayout = (TagView) mInflater.inflate(R.layout.tagview_item, tag);
            //TagView tagLayout = tag;
            tagLayout.setTagId(listIndex);
            tagLayout.setBackgroundDrawable(getSelector(tag));

            //tagLayout.sett
            // tag text
            TextView tagTextView = (TextView) tagLayout.findViewById(R.id.tv_tag_item_contain);
            tagTextView.setText(tag.getText());

            tagTextView.setTextColor(tag.getTagTextColor());
            tagTextView.setPadding(textPaddingLeft, textPaddingTop, textPaddingRight, texPaddingBottom);
           // ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) tagTextView.getLayoutParams();
            TagView.LayoutParams tagParams = new TagView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            //TODO on child
            tagParams.setMargins(textPaddingLeft, textPaddingTop, textPaddingRight, texPaddingBottom);
            tagTextView.setLayoutParams(tagParams);

            tagTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, tag.getTagTextSize());



            // calculate　of tag layout width
            float tagWidth = tagTextView.getPaint().measureText(tag.getText()) + textPaddingLeft + textPaddingRight;
            // tagTextView padding (left & right)

            // deletable text
            TextView deletableView = (TextView) tagLayout.findViewById(R.id.tv_tag_item_delete);
            if (tag.isDeletable()) {
                deletableView.setVisibility(View.VISIBLE);
                deletableView.setText(tag.getDeleteIcon());
                int offset = Utils.dipToPx(getContext(), 2f);
                deletableView.setPadding(offset, textPaddingTop, textPaddingRight + offset, texPaddingBottom);
                /*params = (LinearLayout.LayoutParams) deletableView.getLayoutParams();
				params.setMargins(offset, textPaddingTop, textPaddingRight+offset, texPaddingBottom);
				deletableView.setLayoutParams(params);*/
                deletableView.setTextColor(tag.getDeleteIconColor());
                deletableView.setTextSize(TypedValue.COMPLEX_UNIT_SP, tag.getDeleteIconSize());
                deletableView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //removed
                    }
                });
                tagWidth += deletableView.getPaint().measureText(tag.getDeleteIcon()) + textPaddingLeft + textPaddingRight;
                // deletableView Padding (left & right)
            } else {
                deletableView.setVisibility(View.GONE);
            }

          //  LayoutParams tagParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            tagParams.setMargins(0, 0, 0, 0);

            //add margin of each line
            tagParams.bottomMargin = lineMargin;

            /*if (mWidth <= total + tagWidth + Utils.dipToPx(this.getContext(), Constants.LAYOUT_WIDTH_OFFSET)) {
                //need to add in new line
                tagParams.addRule(RelativeLayout.BELOW, index_bottom);
                // initialize total param (layout padding left & layout padding right)
                total = getPaddingLeft() + getPaddingRight();
                index_bottom = listIndex;
                index_header = listIndex;
            } else {
                //no need to new line
                tagParams.addRule(RelativeLayout.ALIGN_TOP, index_header);
                //not header of the line
                if (listIndex != index_header) {
                    tagParams.addRule(RelativeLayout.RIGHT_OF, listIndex - 1);
                    tagParams.leftMargin = tagMargin;
                    total += tagMargin;
                    if (tag_pre != null && tag_pre.getTagTextSize() < tag.getTagTextSize()) {
                        index_bottom = listIndex;
                    }
                }
            }*/
            total += tagWidth;
            addView(tagLayout, tagParams);
            tag_pre = tag;
            listIndex++;

        }

    }

    private Drawable getSelector(TagView tag) {
        if (tag.getBackground() != null) return tag.getBackground();
        StateListDrawable states = new StateListDrawable();
        GradientDrawable gd_normal = new GradientDrawable();
        gd_normal.setColor(tag.getLayoutColor());
        gd_normal.setCornerRadius(tag.getRadius());
        if (tag.getLayoutBorderSize() > 0) {
            gd_normal.setStroke(Utils.dipToPx(getContext(), tag.getLayoutBorderSize()), tag.getLayoutBorderColor());
        }
        GradientDrawable gd_press = new GradientDrawable();
        gd_press.setColor(tag.getLayoutColorPressed());
        gd_press.setCornerRadius(tag.getRadius());
        states.addState(new int[]{android.R.attr.state_pressed}, gd_press);
        //must add state_pressed first，or state_pressed will not take effect
        states.addState(new int[]{}, gd_normal);
        return states;
    }


    //public methods
    //----------------- separator  -----------------//

    /**
     * @param tag
     */
    public void addTag(TagView tag) {

        mTags.add(tag);
        drawTags();
    }

    public void addTags(ArrayList<TagView> tags) {
        if (tags == null) return;
        mTags = new ArrayList<>();
        if (tags.size() == 0)
            drawTags();
        for (TagView item : tags) {
            addTag(item);
        }
    }


    public void addTags(TagView[] tags) {
        if (tags == null) return;
        for (TagView item : tags) {
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
    public List<TagView> getTags() {
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


}
