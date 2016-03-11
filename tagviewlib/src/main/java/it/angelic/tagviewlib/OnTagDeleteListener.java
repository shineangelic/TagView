package it.angelic.tagviewlib;

/**
 * listener for tag delete
 */
public interface OnTagDeleteListener {
	void onTagDeleted(TagRelativeLayout view, TagView tag, int position);
}