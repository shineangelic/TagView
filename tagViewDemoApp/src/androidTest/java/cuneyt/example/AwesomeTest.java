package cuneyt.example;

import android.content.Context;


import android.test.AndroidTestCase;



import it.angelic.tagviewlib.SimpleTagViewUtils;


public class AwesomeTest extends AndroidTestCase {


    private Context mActivity;


    @Override
    public void setUp() throws Exception {
        super.setUp();

        mActivity = getContext();
    }


    @Override
    public void tearDown() throws Exception {
        super.tearDown();
    }

    public void testAwesomeCodesValidator() {

        assertNotNull(mActivity.getResources());
        assertTrue(SimpleTagViewUtils.getAwesomeCodes(mActivity).size() ==
                SimpleTagViewUtils.getAwesomeCodes(mActivity).size());
    }

}