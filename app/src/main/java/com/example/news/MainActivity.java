package com.example.news;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    /**
     * Creates a News app while showing the contents of a Search Fragment with news results
     * using the default keyword "news". The user can type a new keyword to search news articles by
     * and press the search button to show those results.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        SearchFragment.search = "news";
        SearchFragment frag = new SearchFragment();
        frag.setContainerActivity(this);
        transaction.replace(R.id.inner, frag);
        transaction.addToBackStack(null);
        transaction.commit();

    }

    /**
     * This method shows the news results with the given keyword by the user.
     * This method is run when the user presses the search button.
     *
     * @param view is the button view that is pressed.
     */
    public void showSearch(View view) {
        EditText textField = (EditText) this.findViewById(R.id.text_field);
        String search = textField.getText().toString();
        SearchFragment.search = search;
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        SearchFragment frag = new SearchFragment();
        frag.setContainerActivity(this);
        transaction.replace(R.id.inner, frag);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    /**
     * This method shows the article preview of the row the user selected.
     * This method is run when the user presses a row in the listView.
     *
     * @param view is the LinearLayout view that is pressed.
     */
    public void showPreview(View view) {
        TextView websiteView = (TextView)((LinearLayout )view).getChildAt(0);
        TextView authorView = (TextView)((LinearLayout )view).getChildAt(1);
        String website = websiteView.getText().toString();
        String author = authorView.getText().toString();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        PreviewFragment frag = new PreviewFragment(website, author);
        frag.setContainerActivity(this);
        transaction.replace(R.id.inner, frag);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    /**
     * This method shows the full article of the current article preview.
     * This method is run when the user presses the READ ON button.
     *
     * @param view is the button view that is pressed.
     */
    public void showWebArticle(View view) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        WebArticleFragment frag = new WebArticleFragment(PreviewFragment.url);
        frag.setContainerActivity(this);
        transaction.replace(R.id.inner, frag);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}
