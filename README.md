# Android SimpleTagView
Android SimpleTagView derived from Cüneyt Çarıkçi

This lib includes two customViews: **SimpleTagView** to show wrapped TextViews in the
form of tags with icon and delete capability; **SimpleTagRelativeLayout** to dynamically add and remove
tags within a custom RelativeLayout.

Lib was forked from Cüneyt ViewTag, but I tried to expose also single-tagView API,
not forcing the user to pass thru a *containing* RelativeLayout view. SimpleTagView
is simpler, since it uses <merge> layout, but also supports **font-awesome** to put an icon on your TAGs.
You can edit the tag's color and set listener for selecting or deleting. 


#Feature
* SimpleTagView extends GroupView, it represents a single TAG. Can be deletable or not
* SimpleTagRelativeLayout is a group of TAGs, allowing add, removal and listeners.
* Listener of tag selecting and deleting.
* Font-awesome 4.5.0 native support
* Can be created from XML file or Java code.

# Usage
You'll need to add gradle dependency from *jcenter()* as usualadding the following
to your *build.gradle* 

<pre>
   compile 'it.angelic:tagView:1.2.0'
</pre>

## Usage in XML
After having declared the namespace, with something like  <pre>xmlns:tagview="http://schemas.android.com/apk/res-auto"</pre>
you may declare a Tag group like this:

 <pre style='color:#000000;background:#ffffff;'><span style='color:#a65700; '>&lt;</span><span style='color:#5f5035; '>it.angelic.tagviewlib.SimpleTagRelativeLayout</span>
            <span style='color:#007997; '>android</span><span style='color:#800080; '>:</span><span style='color:#274796; '>id</span><span style='color:#808030; '>=</span><span style='color:#800000; '>"</span><span style='color:#0000e6; '>@+id/tag_group</span><span style='color:#800000; '>"</span>
            <span style='color:#007997; '>android</span><span style='color:#800080; '>:</span><span style='color:#274796; '>layout_width</span><span style='color:#808030; '>=</span><span style='color:#800000; '>"</span><span style='color:#0000e6; '>match_parent</span><span style='color:#800000; '>"</span>
            <span style='color:#007997; '>android</span><span style='color:#800080; '>:</span><span style='color:#274796; '>layout_height</span><span style='color:#808030; '>=</span><span style='color:#800000; '>"</span><span style='color:#0000e6; '>match_parent</span><span style='color:#800000; '>"</span>
            <span style='color:#007997; '>android</span><span style='color:#800080; '>:</span><span style='color:#274796; '>layout_margin</span><span style='color:#808030; '>=</span><span style='color:#800000; '>"</span><span style='color:#0000e6; '>10dp</span><span style='color:#800000; '>"</span> <span style='color:#a65700; '>/></span>
</pre>

or a single tag like this:

<pre style='color:#000000;background:#ffffff;'><span style='color:#a65700; '>&lt;</span><span style='color:#5f5035; '>it.angelic.tagviewlib.SimpleTagView</span>
            <span style='color:#007997; '>android</span><span style='color:#800080; '>:</span><span style='color:#274796; '>id</span><span style='color:#808030; '>=</span><span style='color:#800000; '>"</span><span style='color:#0000e6; '>@+id/myTestTag</span><span style='color:#800000; '>"</span>
            <span style='color:#007997; '>android</span><span style='color:#800080; '>:</span><span style='color:#274796; '>layout_width</span><span style='color:#808030; '>=</span><span style='color:#800000; '>"</span><span style='color:#0000e6; '>match_parent</span><span style='color:#800000; '>"</span>
            <span style='color:#007997; '>android</span><span style='color:#800080; '>:</span><span style='color:#274796; '>layout_height</span><span style='color:#808030; '>=</span><span style='color:#800000; '>"</span><span style='color:#0000e6; '>match_parent</span><span style='color:#800000; '>"</span>
            <span style='color:#007997; '>tagview</span><span style='color:#800080; '>:</span><span style='color:#274796; '>tagColor</span><span style='color:#808030; '>=</span><span style='color:#800000; '>"</span><span style='color:#0000e6; '>@android:color/holo_purple</span><span style='color:#800000; '>"</span>
            <span style='color:#007997; '>tagview</span><span style='color:#800080; '>:</span><span style='color:#274796; '>tagAwesome</span><span style='color:#808030; '>=</span><span style='color:#800000; '>"</span><span style='color:#0000e6; '>fa-hand-spock-o</span><span style='color:#800000; '>"</span>
            <span style='color:#007997; '>tagview</span><span style='color:#800080; '>:</span><span style='color:#274796; '>titleText</span><span style='color:#808030; '>=</span><span style='color:#800000; '>"</span><span style='color:#0000e6; '>My HashTag</span><span style='color:#800000; '>"</span> <span style='color:#a65700; '>/></span>
</pre>
 
## Usage in code
You can add one tag inside SimpleTagRelativeLayout:
<pre>
 SimpleTagRelativeLayout tagGroup = (SimpleTagRelativeLayout) findviewById(R.id.tag_view);
 tagGroup.addTag(SimpleTagView tag);
 //You can add multiple tag via ArrayList
 tagGroup.addTags(ArrayList<Tag> tags);
 //Via string array
 addTags(String[] tags);
 //set click listener
 tagGroup.setOnTagClickListener(new OnTagClickListener() {
            @Override
            public void onTagClick(Tag tag, int position) {
            }
 });     
 //set delete listener
 tagGroup.setOnTagDeleteListener(new OnTagDeleteListener() {
   @Override
   public void onTagDeleted(final TagView view, final Tag tag, final int position) {
   }
 });
 </pre>
 
 Or you can add SimpleTagView directly to your Views like this:
 <pre>
 SimpleTagView tagTer2 = new SimpleTagView(getApplicationContext());
 tagTer2.setText("Programmatic Deletable");
 //Set font-awesome icon to be used
 tagTer2.setFontAwesome("fa-warning");
 tagTer2.setDeletable(true);
</pre>

#Libraries Used
<a href="http://jakewharton.github.io/butterknife/">ButterKnife</a> by Jake Wharton
<a href="http://fontawesome.io">Font Awesome</a> by Dave Gandy

#License
Copyright 2016 shine@angelic.it forking Cüneyt Çarıkçi

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
