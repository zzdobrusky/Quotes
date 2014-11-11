package edu.utah.cs4962.quotes;

import android.os.AsyncTask;

import java.util.ArrayList;

/**
 * Created by zbynek on 11/10/2014.
 */
public class QuoteCollection
{
    ArrayList<Quote> quotes = new ArrayList<Quote>();

    private static QuoteCollection _instance = null;
    public static QuoteCollection getInstance()
    {
        if(_instance == null)
            _instance = new QuoteCollection();
        return _instance;
    }

    private QuoteCollection()
    {
        refreshQuoteList();
    }

    public int[] getQuoteIdentifiers()
    {
        return new int[0];
    }

    public Quote getQuote(int quoteIdentifier)
    {
        return null;
    }

    public void addQuote(Quote quote)
    {

    }

    public void removeQuote(Quote quote)
    {

    }

    public void refreshQuoteList()
    {
        AsyncTask<String, Integer, QuoteNetworking.Attribution[]> listQuotesTask =
                new AsyncTask<String, Integer, QuoteNetworking.Attribution[]>()
        {
            @Override
            protected QuoteNetworking.Attribution[] doInBackground(String... params)
            {
                return  QuoteNetworking.listAttributions();
            }

            @Override
            protected void onPostExecute(QuoteNetworking.Attribution[] attributions)
            {
                super.onPostExecute(attributions);

                AsyncTask<QuoteNetworking.Attribution, Integer, QuoteNetworking.Quote[]> retrieveQuotesTask =
                        new AsyncTask<QuoteNetworking.Attribution, Integer, QuoteNetworking.Quote[]>()
                        {
                            @Override
                            protected QuoteNetworking.Quote[] doInBackground(QuoteNetworking.Attribution... attributions)
                            {
                                ArrayList<QuoteNetworking.Quote> quotes = new ArrayList<QuoteNetworking.Quote>();
                                for(QuoteNetworking.Attribution attribution: attributions)
                                {
                                    QuoteNetworking.Quote quote = QuoteNetworking.retreiveQuote(attribution.ID);
                                    if(quote != null)
                                        quotes.add(quote);
                                }

                                return quotes.toArray(new QuoteNetworking.Quote[quotes.size()]);
                            }

                            @Override
                            protected void onPostExecute(QuoteNetworking.Quote[] quotes)
                            {
                                super.onPostExecute(quotes);

                                // TODO: Notify listener that quote list has downloaded
                            }
                        };
                retrieveQuotesTask.execute(attributions);


            }
        };
        listQuotesTask.execute();
    }
}
