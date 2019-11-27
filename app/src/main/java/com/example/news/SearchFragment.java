package com.example.news;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class SearchFragment extends Fragment {
    public Activity containerActivity = null;
    public static String search;
    public static String[][] articles;
    public Calendar rightNow = Calendar.getInstance();
    public String startDate = "" + rightNow.YEAR + "-" + rightNow.MONTH + "-" + (rightNow.DAY_OF_MONTH - 7);
    public String urlBeginning = "https://newsapi.org/v2/everything?sortBy=publishedAt&q=";
    public String urlMid = "&from=" + startDate;
    public String urlEnd = "&apiKey=82442cf3906b4d1fa883e1f7e7452600";

    public SearchFragment() { }

    /**
     * Sets the container activity to the activity passed as a parameter.
     *
     * @param containerActivity the current activity.
     */
    public void setContainerActivity(Activity containerActivity) {
        this.containerActivity = containerActivity;
    }

    /**
     * Inflates the view and runs the GetNewsTask AsyncTask.
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
        View v = inflater.inflate(R.layout.fragment_search, container, false);
        new GetNewsTask().execute();
        return v;
    }

    /**
     * Fills the ListView with the websites and authors of certain articles with the given search term.
     */
    public class GetNewsTask extends AsyncTask<Object, Void, List<HashMap<String, String>>> {

        /**
         * Creates a new JSON object with the current url with the given search term.
         * Fills the contents of a String[][] with the articles information for later use.
         * Uses a simpleAdapter for conversion into the ListView.
         *
         * @param objs
         * @return the List of HashMaps for the simpleAdapter.
         */
        @Override
        protected List<HashMap<String, String>> doInBackground(Object[] objs) {
            try {

                String json = "";
                String line;

                URL url = new URL(urlBeginning + search + urlMid + urlEnd);

                BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
                while ((line = in.readLine()) != null) {
                    json += line;
                }
                in.close();

                JSONObject jsonObject = new JSONObject(json);
                JSONArray articleList = jsonObject.getJSONArray("articles");

                String[] websites = new String[articleList.length()];
                String[] authors = new String[articleList.length()];
                articles = new String[articleList.length()][4];
                List<HashMap<String, String>> newsList = new ArrayList<HashMap<String, String>>();

                for (int i = 0; i < articleList.length(); i++) {
                    websites[i] = articleList.getJSONObject(i).getJSONObject("source").getString("name");
                    authors[i] = "(" + articleList.getJSONObject(i).getString("author") + ")";
                    articles[i][0] = articleList.getJSONObject(i).getJSONObject("source").getString("name");
                    articles[i][1] = "(" + articleList.getJSONObject(i).getString("author") + ")";
                    articles[i][2] = articleList.getJSONObject(i).getString("content");
                    articles[i][3] = articleList.getJSONObject(i).getString("url");
                    HashMap<String, String> hm = new HashMap<String, String>();
                    hm.put("news_row_website", websites[i]);
                    hm.put("news_row_author", authors[i]);
                    newsList.add(hm);
                }
                return newsList;

            } catch (Exception e) { e.printStackTrace(); }

            return null;
        }

        /**
         * Uses a simpeAdapter to fill the list view with article websites and authors.
         *
         * @param aList a List of HashMaps for the simpleAdapter.
         */
        @Override
        protected void onPostExecute(List<HashMap<String, String>> aList) {
            try {
                String[] from = {"news_row_website", "news_row_author"};
                int[] to = {R.id.news_row_website, R.id.news_row_author};

                SimpleAdapter simpleAdapter =
                        new SimpleAdapter(containerActivity, aList,
                                R.layout.news_row, from, to);

                ListView tierListView = (ListView) containerActivity.findViewById(R.id.news_list);
                tierListView.setAdapter(simpleAdapter);

            } catch (Exception e) { e.printStackTrace(); }
        }
    }
}
