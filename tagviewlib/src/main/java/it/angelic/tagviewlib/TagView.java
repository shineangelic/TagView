package it.angelic.tagviewlib;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by shine@angelic.it on 11/03/2016.
 */
public class TagView extends ViewGroup {


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
        // TagView tagLayout = (TagView) inflate(R.layout.tagview_item, null);
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
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public Drawable getBackground() {
        return content.getBackground();
    }

    @Override
    protected void onAttachedToWindow() {
        Log.d("TagView TEST", "onAttachedToWindow() :"+content.getText());
        super.onAttachedToWindow();
        ScaleAnimation anim = new ScaleAnimation(0, 1, 0, 1);
        anim.setDuration(1000);
        anim.setFillAfter(true);
        this.startAnimation(anim);
    }

    /**
     * Ask all children to measure themselves and compute the measurement of this
     * layout based on the children.
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int count = getChildCount();

        // These keep track of the space we are using on the left and right for
        // views positioned there; we need member variables so we can also use
        // these for layout later.
        mLeftWidth = 0;
        mRightWidth = 0;

        // Measurement will ultimately be computing these values.
        int maxHeight = 0;
        int maxWidth = 0;
        int childState = 0;

        // Iterate through all children, measuring them and computing our dimensions
        // from their size.
        for (int i = 0; i < count; i++) {
            final View child = getChildAt(i);
            if (child.getVisibility() != GONE) {
                // Measure the child.
                measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, 0);

                // Update our size information based on the layout params.  Children
                // that asked to be positioned on the left or right go in those gutters.
                final LayoutParams lp = (LayoutParams) child.getLayoutParams();
                if (lp.position == LayoutParams.POSITION_LEFT) {
                    mLeftWidth += Math.max(maxWidth,
                            child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin);
                } else if (lp.position == LayoutParams.POSITION_RIGHT) {
                    mRightWidth += Math.max(maxWidth,
                            child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin);
                } else {
                    maxWidth = Math.max(maxWidth,
                            child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin);
                }
                maxHeight = Math.max(maxHeight,
                        child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin);
                // childState = combineMeasuredStates(childState, child.getMeasuredState());
            }
        }

        // Total width is the maximum width of all inner children plus the gutters.
        maxWidth += mLeftWidth + mRightWidth;

        // Check against our minimum height and width
        maxHeight = Math.max(maxHeight, getSuggestedMinimumHeight());
        maxWidth = Math.max(maxWidth, getSuggestedMinimumWidth());

        // Report our final dimensions.
        setMeasuredDimension(resolveSizeAndState(maxWidth, widthMeasureSpec, childState),
                resolveSizeAndState(maxHeight, heightMeasureSpec,
                        childState << MEASURED_HEIGHT_STATE_SHIFT));
    }

    /**
     * Position all children within this layout.
     */
    // ----------------------------------------------------------------------
    // The rest of the implementation is for custom per-child layout parameters.
    // If you do not need these (for example you are writing a layout manager
    // that does fixed positioning of its children), you can drop all of this.
    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new TagView.LayoutParams(getContext(), attrs);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    }

    @Override
    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        return new LayoutParams(p);
    }

    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return p instanceof LayoutParams;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        final int count = getChildCount();
        // inflate tag layout
        Log.d("TagView onLayout", "Drawing getChildCount: "+getChildCount());
        // These are the far left and right edges in which we are performing layout.
        int leftPos = getPaddingLeft();
        int rightPos = right - left - getPaddingRight();

        // This is the middle region inside of the gutter.
        final int middleLeft = leftPos + mLeftWidth;
        final int middleRight = rightPos - mRightWidth;

        // These are the top and bottom edges in which we are performing layout.
        final int parentTop = getPaddingTop();
        final int parentBottom = bottom - top - getPaddingBottom();

        for (int i = 0; i < count; i++) {
            final View child = getChildAt(i);
            if (child.getVisibility() != GONE) {
                final LayoutParams lp = (LayoutParams) child.getLayoutParams();

                final int width = child.getMeasuredWidth();
                final int height = child.getMeasuredHeight();

                // Compute the frame in which we are placing this child.
                if (lp.position == LayoutParams.POSITION_LEFT) {
                    mTmpContainerRect.left = leftPos + lp.leftMargin;
                    mTmpContainerRect.right = leftPos + width + lp.rightMargin;
                    leftPos = mTmpContainerRect.right;
                } else if (lp.position == LayoutParams.POSITION_RIGHT) {
                    mTmpContainerRect.right = rightPos - lp.rightMargin;
                    mTmpContainerRect.left = rightPos - width - lp.leftMargin;
                    rightPos = mTmpContainerRect.left;
                } else {
                    mTmpContainerRect.left = middleLeft + lp.leftMargin;
                    mTmpContainerRect.right = middleRight - lp.rightMargin;
                }
                mTmpContainerRect.top = parentTop + lp.topMargin;
                mTmpContainerRect.bottom = parentBottom - lp.bottomMargin;

                // Use the child's gravity and size to determine its final
                // frame within its container.
                Gravity.apply(lp.gravity, width, height, mTmpContainerRect, mTmpChildRect);

                // Place the child.
                child.layout(mTmpChildRect.left, mTmpChildRect.top,
                        mTmpChildRect.right, mTmpChildRect.bottom);
            }
        }
    }

    // ----------------------------------------------------------------------
    // The rest of the implementation is for custom per-child layout parameters.
    // If you do not need these (for example you are writing a layout manager
    // that does fixed positioning of its children), you can drop all of this.

   /* @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.d("TagView TEST", "onLayout() " );
        getChildAt(0);
        TextView tagTextView = (TextView) findViewById(R.id.tv_tag_item_contain);
        tagTextView.setText(content.getText());
        tagTextView.setTextColor(content.getTagTextColor());
        tagTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, content.getTagTextSize());
        tagTextView.layout(l,t,r,b);
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

    /**
     * Custom per-child layout information.
     */
    public static class LayoutParams extends RelativeLayout.LayoutParams {
        public static int POSITION_MIDDLE = 0;
        public static int POSITION_LEFT = 1;
        public static int POSITION_RIGHT = 2;
        /**
         * The gravity to apply with the View to which these layout parameters
         * are associated.
         */
        public int gravity = Gravity.TOP | Gravity.START;
        public int position = POSITION_MIDDLE;

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
            // Pull the layout param values from the layout XML during
            // inflation.  This is not needed if you don't care about
            // changing the layout behavior in XML.
            //TypedArray a = c.obtainStyledAttributes(attrs, R.styleable.CustomLayoutLP);
            // gravity = a.getInt(R.styleable.CustomLayoutLP_android_layout_gravity, gravity);
            // position = a.getInt(R.styleable.CustomLayoutLP_layout_position, position);
            // a.recycle();
        }


        public LayoutParams(int width, int height) {
            super(width, height);
        }

        public LayoutParams(ViewGroup.LayoutParams source) {
            super(source);
        }
    }
}
