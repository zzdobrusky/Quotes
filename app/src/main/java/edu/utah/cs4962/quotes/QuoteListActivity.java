package edu.utah.cs4962.quotes;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class QuoteListActivity extends Activity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        QuoteCollection.getInstance();

        View testView = new View(this);
        testView.setBackgroundColor(Color.GREEN);
        setContentView(testView);
    }

}
