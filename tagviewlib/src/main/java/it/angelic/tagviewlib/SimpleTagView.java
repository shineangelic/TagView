package it.angelic.tagviewlib;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SimpleTagView extends LinearLayout {


    private static Typeface mFont;
    private final View tagRootView;
    private final int backgroundDefaultColor;
    private final int textDefaultColor;
    private final int textDefaultColorInverse;
    private final int textDefaultColorAccent;
    private TextView tagAwesomeText;
    private TextView tagTextView;
    private TextView tagDeleteTextView;
    private SimpleTag content;
    private OnSimpleTagClickListener mClickListener;
    private OnSimpleTagDeleteListener mDeleteListener;

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

        //load defaults
        TypedArray arrayTheme = context.getTheme().obtainStyledAttributes(new int[]{
                android.R.attr.colorBackground,
                android.R.attr.textColorPrimary,
                android.R.attr.textColorPrimaryInverse,
                android.R.attr.colorAccent
        });
        backgroundDefaultColor = arrayTheme.getColor(0, 0xFF00FF);
        textDefaultColor = arrayTheme.getColor(1, Color.GRAY);
        textDefaultColorInverse = arrayTheme.getColor(2, Color.WHITE);
        textDefaultColorAccent = arrayTheme.getColor(3, Color.GREEN);
        arrayTheme.recycle();

        //styleable attrs in XML
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.SimpleTagView, 0, 0);
        String titleText = a.getString(R.styleable.SimpleTagView_tagText);

        int valueColor = a.getColor(R.styleable.SimpleTagView_tagColor,
                Color.DKGRAY);
        int valueRadius = a.getInt(R.styleable.SimpleTagView_tagRadius, 4);
        boolean valueDelete = a.getBoolean(R.styleable.SimpleTagView_isDeletable, false);
        float textSize = a.getDimension(R.styleable.SimpleTagView_textSize, 0);
        String tagAwesome = a.getString(R.styleable.SimpleTagView_tagAwesome);

        content.setColor(valueColor);
        content.setName(titleText);
        content.setRadius(valueRadius);
        content.setDeletable(valueDelete);
        a.recycle();

        setOrientation(LinearLayout.HORIZONTAL);
        setGravity(Gravity.CENTER_VERTICAL);

        //INFLATION
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        tagRootView = inflater.inflate(R.layout.simple_tag_view, this, true);
        tagAwesomeText = (TextView) getChildAt(0);

        tagTextView = (TextView) getChildAt(1);
        tagTextView.setText(titleText);


        //tagTextView.setTextColor(textDefaultColor);
        tagDeleteTextView = (TextView) getChildAt(2);
        if (textSize > 0) {
            tagTextView.setTextSize(textSize);
            tagDeleteTextView.setTextSize(textSize);
            tagAwesomeText.setTextSize(textSize);
        }

            tagAwesomeText.setTypeface(SimpleTagViewUtils.getTypeface(context, Constants.FONT));
            setFontAwesome(tagAwesome);
        tagDeleteTextView.setVisibility(valueDelete ? View.VISIBLE : View.GONE);

        //richiama selector + color
        setRadius(content.getRadius());
        //resetTextColor();
        setColor(content.getColor());


        tagTextView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mClickListener != null) {
                    mClickListener.onSimpleTagClick(SimpleTagView.this);
                }
            }
        });
        tagDeleteTextView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDeleteListener != null) {
                    mDeleteListener.onTagDeleted(SimpleTagView.this);
                }
            }
        });
    }

    private void resetTextColor() {
        //compute text color
        int computed;
        if (SimpleTagViewUtils.getColorLuminosity(content.getColor()) > Constants.TAG_TEXT_WHITE_THOLD) {
            //Log.d("TagView TEST", "BRIG selected for: " + content.getName() + " -" + Integer.toHexString(content.getColor()) + " -" + Integer.toHexString(textDefaultColor));
            //testo scuro
            computed = Color.argb(Constants.TAG_TEXT_ALPHA, Color.red(textDefaultColor), Color.green(textDefaultColor), Color.blue(textDefaultColor));
        } else { //use #ffffff
            //Log.d("TagView TEST", "DARK selected for: " + content.getName() + " -" + Integer.toHexString(content.getColor()) + " -" + Integer.toHexString(textDefaultColorInverse));
            computed = Color.argb(Constants.TAG_TEXT_ALPHA, Color.red(textDefaultColorInverse), Color.green(textDefaultColorInverse), Color.blue(textDefaultColorInverse));
        }
        tagDeleteTextView.setTextColor(computed);
        tagTextView.setTextColor(computed);
        tagAwesomeText.setTextColor(computed);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //  Log.d("---", content.getName());
        int tagWidth = (int) tagTextView.getPaint().measureText(content.getName());
        //Log.d("tagWidth", Integer.toString(getWidth()));
        super.onMeasure(tagWidth, heightMeasureSpec);
    }
/*
    @Override
    protected void onAttachedToWindow() {
        Log.d("TagView TEST", "onAttachedToWindow() :" + content.getName());
        super.onAttachedToWindow();
        ScaleAnimation anim = new ScaleAnimation(0, 1, 0, 1);
        anim.setDuration(1000);
        anim.setFillAfter(true);
        this.startAnimation(anim);
    }
*/

    /**
     * Get main TextView's text
     *
     * @see TextView#getText()
     */
    public String getText() {
        return content.getName();
    }

    /**
     * Set main TextView's text
     *
     * @param tee
     * @see TextView#setText(CharSequence)
     */
    public void setText(CharSequence tee) {
        content.setName(tee.toString());
        tagTextView.setText(tee);
        invalidate();
    }

    public void setFontAwesome(String fontName) throws FontNotFoundException {
        try {
            String code = translateAwesomeCode(fontName);
            content.setFontAwesomeCode(code);
            tagAwesomeText.setText(code);
            tagAwesomeText.setVisibility(View.VISIBLE);
        }catch (FontNotFoundException fg){
            Log.e("TagView TEST", "tagAwesome not found: " + fontName);
            tagAwesomeText.setVisibility(View.GONE);
        }

        invalidate();
    }

    private String translateAwesomeCode(String fontName) throws FontNotFoundException {
        int codeidx;
        Log.d("TagView TEST", "translateAwesomeCode set for: " + fontName      );
        try {
            if (fontName.startsWith("&")) {
                codeidx = SimpleTagViewUtils.getAwesomeCodes(getContext()).indexOf(fontName);
            } else {//try translate
                //codes according to http://fortawesome.github.io/Font-Awesome/cheatsheet/
                codeidx = SimpleTagViewUtils.getAwesomeNames(getContext()).indexOf(fontName);
            }
            return SimpleTagViewUtils.getAwesomeCodes(getContext()).get(codeidx);
        }catch (FontNotFoundException | ArrayIndexOutOfBoundsException | NullPointerException fr){
            throw new FontNotFoundException("Font with code not found: " + fontName);
        }
    }


    /**
     * {@link GradientDrawable }
     *
     * @return tag background corner radius in pixels
     */
    public int getRadius() {
        return content.getRadius();
    }

    /**
     * set corner radius by GradientDrawable.setCornerRadius
     *
     * @param tee
     * @see GradientDrawable#setCornerRadius(float)
     */
    public void setRadius(int tee) {
        content.setRadius(tee);
        tagRootView.setBackgroundDrawable(getSelector(content));
    }

    /**
     * Text color will be determined automatically based on
     * threshold value {@link Constants}
     *
     * @return
     */
    public int getColor() {
        return content.getColor();
    }

    public void setColor(int tee) {
        content.setColor(tee);
        resetTextColor();
        tagRootView.setBackgroundDrawable(getSelector(content));
    }

    /**
     * Deletable tags automatically adds the symbol '×', but you still need to attach a listener to make it work
     *
     * @return whether TAG is deletable or not
     */
    public boolean isDeletable() {
        return content.isDeletable();
    }

    public void setDeletable(boolean isVisible) {
        content.setDeletable(isVisible);
        tagDeleteTextView.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

    private Drawable getSelector(SimpleTag tag) {
        // if (tag.getBackground() != null) return tag.getBackground();
        StateListDrawable states = new StateListDrawable();
        GradientDrawable gd_normal = new GradientDrawable();
        gd_normal.setColor(getColor());
        gd_normal.setCornerRadius(getRadius());
        //transparent border grant
        gd_normal.setStroke(SimpleTagViewUtils.dipToPx(getContext(), 2f), Color.TRANSPARENT);
        GradientDrawable gd_press = new GradientDrawable();
        gd_press.setColor(textDefaultColorAccent);
        gd_press.setCornerRadius(tag.getRadius());
        states.addState(new int[]{android.R.attr.state_pressed}, gd_press);
        //must add state_pressed first，or state_pressed will not take effect
        states.addState(new int[]{}, gd_normal);
        return states;
    }

    /**
     * setter for OnTagSelectListener
     *
     * @param clickListener
     */
    public void setOnSimpleTagClickListener(OnSimpleTagClickListener clickListener) {
        mClickListener = clickListener;
    }
} 
