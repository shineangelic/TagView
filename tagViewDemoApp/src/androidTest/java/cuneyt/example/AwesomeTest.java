package cuneyt.example;

import android.content.Context;


import android.test.AndroidTestCase;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;


import java.util.ArrayList;

import it.angelic.tagviewlib.OnSimpleTagDeleteListener;
import it.angelic.tagviewlib.SimpleTagRelativeLayout;
import it.angelic.tagviewlib.SimpleTagView;
import it.angelic.tagviewlib.SimpleTagViewUtils;


public class AwesomeTest extends AndroidTestCase {


    @Override
    public void setUp() throws Exception {
        super.setUp();

    }


    @Override
    public void tearDown() throws Exception {
        super.tearDown();
    }

    public void testAwesomeCodesValidator() {

        assertNotNull(mContext.getResources());
        assertTrue(SimpleTagViewUtils.getAwesomeCodes(mContext).size() ==
                SimpleTagViewUtils.getAwesomeCodes(mContext).size());
    }


}