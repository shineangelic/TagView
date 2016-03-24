package cuneyt.example;

import android.test.AndroidTestCase;
import android.view.View;

import it.angelic.tagviewlib.OnSimpleTagDeleteListener;
import it.angelic.tagviewlib.SimpleTagRelativeLayout;
import it.angelic.tagviewlib.SimpleTagView;
import it.angelic.tagviewlib.SimpleTagViewUtils;


public class TestSimpleTagView extends AndroidTestCase {


    @Override
    public void setUp() throws Exception {
        super.setUp();

    }


    @Override
    public void tearDown() throws Exception {
        super.tearDown();
    }


    
    public void testSimpleTagListeners() {

        final int key = 0;
        SimpleTagView tester = new SimpleTagView(mContext);

        tester.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setTag(Boolean.TRUE);
            }
        });
        tester.setOnSimpleTagDeleteListener(new OnSimpleTagDeleteListener() {
            @Override
            public void onTagDeleted(SimpleTagView tag) {

            }
        });

        tester.performClick();
        assertTrue((Boolean) tester.getTag());
    }
    public void testSimpleTagViewAwesome() {
        SimpleTagView pirelli = new SimpleTagView(mContext);
        pirelli.setFontAwesome("fa-warning");
        pirelli.setDeletable(false);
        pirelli.measure(0, 0);

        assertTrue(pirelli.getMeasuredWidth() > 0);
    }
    public void testSimpleTagViewDeletable() {
        SimpleTagView pirelli = new SimpleTagView(mContext);
        pirelli.measure(0,0);
        assertTrue(pirelli.getWidth() == 0);
        pirelli.setDeletable(true);
        pirelli.measure(0,0);

        assertTrue(pirelli.getMeasuredWidth() > 0);
    }
    public void testSimpleTagRelative() {
        SimpleTagRelativeLayout relTest = new SimpleTagRelativeLayout(mContext);
        relTest.addTag(new SimpleTagView(mContext,"farfallone"));

        assertEquals("farfallone", relTest.getTags().get(0).getText());
        assertTrue(relTest.getTags().get(0).getRadius() > 0);
    }
}