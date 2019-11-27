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
import android.webkit.WebView;
import android.widget.TextView;


public class WebArticleFragment extends Fragment {
    public Activity containerActivity = null;
    String url;

    public WebArticleFragment(String url) {
        this.url = url;
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
     * Inflates the view and loads the url into the webView.
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
        View v = inflater.inflate(R.layout.fragment_web_article, container, false);
        WebView webView = v.findViewById(R.id.webview);
        webView.loadUrl(url);
        return v;
    }

}
