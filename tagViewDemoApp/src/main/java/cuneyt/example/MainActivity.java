package cuneyt.example;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
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
import me.drakeet.materialdialog.MaterialDialog;

public class MainActivity extends AppCompatActivity {

    @InjectView(R.id.tag_group)
    SimpleTagRelativeLayout tagGroup;

    @InjectView(R.id.editText)
    EditText editText;

    @InjectView(R.id.test_laoyut)
    LinearLayout testRel;

    @InjectView(R.id.nuovoTag)
    SimpleTagView testRaff;

    /**
     * sample country list
     */
    ArrayList<TagClass> tagList;


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

        //Tag examples
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

        testRaff.setOnSimpleTagClickListener(new OnSimpleTagClickListener() {
            @Override
            public void onSimpleTagClick(SimpleTagView tag) {
                Log.d("TagView TEST", "TAG click: " + tag.getText()
                );
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

                final MaterialDialog dialog = new MaterialDialog(MainActivity.this);
                dialog.setMessage("\"" + tag.getText() + "\" will be deleted. Are you sure?");
                dialog.setPositiveButton("Yes", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tagGroup.remove(tag);
                        Toast.makeText(MainActivity.this, "\"" + tag.getText() + "\" deleted", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
                dialog.setNegativeButton("No", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();


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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}