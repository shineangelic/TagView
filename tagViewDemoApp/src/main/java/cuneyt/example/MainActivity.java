package cuneyt.example;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cuneyt.example.model.TagClass;
import it.angelic.tagviewlib.Constants;
import it.angelic.tagviewlib.OnSimpleTagClickListener;
import it.angelic.tagviewlib.OnSimpleTagDeleteListener;
import it.angelic.tagviewlib.SimpleTagRelativeLayout;
import it.angelic.tagviewlib.SimpleTagView;
import it.angelic.tagviewlib.SimpleTagViewUtils;

public class MainActivity extends AppCompatActivity {

    @InjectView(R.id.tag_group)
     SimpleTagRelativeLayout tagGroup;

    @InjectView(R.id.editText)
     EditText editText;

    @InjectView(R.id.test_laoyut)
     LinearLayout testRel;

    @InjectView(R.id.nuovoTag)
    SimpleTagView testRaff;

    @InjectView(R.id.nuovoTagAwesome)
    SimpleTagView testAwe;

    @InjectView(R.id.AwesomeSpinner)
    Spinner testSpinner;

    /**
     * sample country list
     */
    private ArrayList<TagClass> tagList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        prepareTags();


        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d("TagView TEST", "onTextChanged: " + s);
                setTags(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //programmatic Tag examples
        SimpleTagView tagTer = new SimpleTagView(this);
        tagTer.setText("Programmatic");

        SimpleTagView tagTer2 = new SimpleTagView(this);
        tagTer2.setText("Programmatic Deletable");
        tagTer2.setDeletable(true);

        SimpleTagView tagTer3 = new SimpleTagView(this);
        tagTer3.setText("Programmatic Red");
        tagTer3.setFontAwesome("fa-warning");
        tagTer3.setColor(Color.argb(255, 255, 0, 0));

        testRel.addView(tagTer);
        testRel.addView(tagTer2);
        testRel.addView(tagTer3);

        //DEMO listeners
        tagTer2.setOnSimpleTagClickListener(new OnSimpleTagClickListener() {
            @Override
            public void onSimpleTagClick(SimpleTagView tag) {
                Log.d("TagView TEST", "TAG click: " + tag.getText()
                );
            }
        });
        tagTer2.setOnSimpleTagDeleteListener(new OnSimpleTagDeleteListener() {
            @Override
            public void onTagDeleted(SimpleTagView tag) {
                testRel.removeView(tag);
                Log.w("TagView TEST", "TAG delete: " + tag.getText());
                Toast.makeText(MainActivity.this, "Tag Deleted! " + tag.getText(), Toast.LENGTH_SHORT).show();
            }
        });

        tagGroup.setOnSimpleTagClickListener(new OnSimpleTagClickListener() {
            @Override
            public void onSimpleTagClick(SimpleTagView tag) {
                Log.d("TagView TEST", "TAG click: " + tag.getText());
                editText.setText(tag.getText());
            }

        });

        tagGroup.setOnSimpleTagDeleteListener(new OnSimpleTagDeleteListener() {
            @Override
            public void onTagDeleted(final SimpleTagView tag) {
                tagGroup.remove(tag);
                Toast.makeText(MainActivity.this, "Tag Deleted! " + tag.getText(), Toast.LENGTH_SHORT).show();
            }
        });

        //dynamically test font-awesome
        testSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // int idx = testSpinner.getSelectedItemPosition();
                String awSom = SimpleTagViewUtils.getAwesomeNames(MainActivity.this).get(position);
                testAwe.setFontAwesome(awSom);
                testAwe.setText(awSom);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
    }

    private void prepareTags() {
        tagList = new ArrayList<>();
        JSONArray jsonArray = null;
        JSONObject temp;
        try {
            jsonArray = new JSONArray(Constants.COUNTRIES);
            for (int i = 0; i < jsonArray.length(); i++) {
                temp = jsonArray.getJSONObject(i);
                tagList.add(new TagClass(temp.getString("code"), temp.getString("name")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void setTags(CharSequence cs) {
        Random rnd = new Random();
        String text = cs.toString();
        ArrayList<SimpleTagView> tags = new ArrayList<>();
        SimpleTagView tag;
        /**
         * counter for prevent frozen effect
         * if the tags number is greather than 20 some device will a bit frozen
         */
        int counter = 0;

        /**
         * for empty edittext
         */
        if (text.equals("")) {
            tagGroup.setTags(new ArrayList<SimpleTagView>());
            return;
        }

        Log.d("TagView TEST", "setTags size: " + tagList.size());
        for (int i = 0; i < tagList.size(); i++) {
            if (tagList.get(i).getName().toLowerCase().startsWith(text.toLowerCase())) {
                tag = new SimpleTagView(MainActivity.this, tagList.get(i).getName());
                tag.setRadius(8);
                tag.setColor(Color.parseColor(tagList.get(i).getColor()));
                if (i % 2 == 0) // you can set deletable or not
                    tag.setDeletable(true);
                if (i % 3 == 0) {//add random icons
                    int rndIdx = rnd.nextInt(SimpleTagViewUtils.getAwesomeNames(MainActivity.this).size());
                    tag.setFontAwesome(SimpleTagViewUtils.getAwesomeNames(MainActivity.this).get(rndIdx));
                }
                tags.add(tag);
                Log.d("TagView TEST", "match found: " + tag);
                counter++;
                /**
                 * if you don't want show all tags. You can set a limit.
                 if (counter == 10)
                 break;
                 */
            }
        }
        tagGroup.setTags(tags);

    }


}