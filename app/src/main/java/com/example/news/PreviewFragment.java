package com.example.news;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class PreviewFragment extends Fragment {
    public Activity containerActivity = null;
    public String website;
    public String author;
    public int index;
    public static String url;

    public PreviewFragment(String website, String author) {
        this.website = website;
        this.author = author;
    }

    /**
     * Sets the container activity to the activity passed as a parameter.
     *
     * @param containerActivity the current activity.
     */
    public void setContainerActivity(Activity containerActivity) {
        this.containerActivity = containerActivity;
    }

    /**
     * Inflates the view and runs the GetPreviewTask AsyncTask.
     * Then returns the inflated view.
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return the inflated view.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_preview, container, false);
        new GetPreviewTask().execute();
        return v;
    }

    /**
     * Finds the preview of the article and displays it in a new fragment textView.
     */
    public class GetPreviewTask extends AsyncTask<Object, Void, String> {

        /**
         * Loops through the articles to find the website and the author of the row that the user clicked.
         * Returns the preview from the SearchFragment.articles list.
         *
         * @param objs
         * @return the preview of the article as a string.
         */
        @Override
        protected String doInBackground(Object[] objs) {
            try {
                String[][] articleList = SearchFragment.articles;
                for (int i = 0; i < articleList.length; i++) {
                    if ((articleList[i][0]).equals(website) && (articleList[i][1]).equals(author)) {
                        index = i;
                        url = articleList[index][3];
                        break;
                    }
                }
                return articleList[index][2];


            } catch (Exception e) { e.printStackTrace(); }

            return null;
        }

        /**
         * Sets the texts of the text view as the title and the preview of the article.
         *
         * @param preview is the preview of the article as a string.
         */
        @Override
        protected void onPostExecute(String preview) {
            try {
                TextView titleView = containerActivity.findViewById(R.id.title);
                titleView.setText(SearchFragment.articles[index][0]);
                TextView previewView = containerActivity.findViewById(R.id.preview);
                previewView.setText(preview);

            } catch (Exception e) { e.printStackTrace(); }
        }
    }

}
