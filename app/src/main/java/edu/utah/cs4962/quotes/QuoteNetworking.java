package edu.utah.cs4962.quotes;

import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

/**
 * Created by zbynek on 11/10/2014.
 */
public class QuoteNetworking
{
    public static final String BASE_URL = "http://192.168.0.3:8888/quotes";

    public class Attribution
    {
        public int ID;
        public String TimeStamp;
        public String Attribution;
    }

    public class Quote
    {
        public int ID;
        public String TimeStamp;
        public String Attribution;
        public String Text;
    }

    public static Attribution[] listAttributions()
    {
        try
        {
            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet(BASE_URL);
            HttpResponse response = client.execute(request);

            InputStream responseContent = response.getEntity().getContent();
            Scanner responseScanner = new Scanner(responseContent).useDelimiter("\\A");

            String responseString = responseScanner.hasNext() ? responseScanner.next() : null;

            if (responseString == null)
                return null;

            Gson gson = new Gson();
            Attribution[] attributions = gson.fromJson(responseString, Attribution[].class);

            return attributions;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public static Quote retreiveQuote(int quoteIdentifier)
    {
        try
        {
            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet(BASE_URL + "/" + quoteIdentifier);
            HttpResponse response = client.execute(request);

            InputStream responseContent = response.getEntity().getContent();
            Scanner responseScanner = new Scanner(responseContent).useDelimiter("\\A");

            String responseString = responseScanner.hasNext() ? responseScanner.next() : null;

            if (responseString == null)
                return null;

            Gson gson = new Gson();
            Quote quote = gson.fromJson(responseString, Quote.class);

            return quote;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public static void publishQuote(String attribution, String text)
    {
        try
        {
            String payload = "{\"Attribution\":\"" + attribution + "\",\"Text\":\"" + text + "\"}";
            HttpClient client = new DefaultHttpClient();
            HttpPost request = new HttpPost(BASE_URL);
            request.setEntity(new StringEntity(payload));
            HttpResponse response = client.execute(request);

            InputStream responseContent = response.getEntity().getContent();
            Scanner responseScanner = new Scanner(responseContent).useDelimiter("\\A");

            String responseString = responseScanner.hasNext() ? responseScanner.next() : null;

            if (responseString == null)
                return null;

            Gson gson = new Gson();
            Quote quote = gson.fromJson(responseString, Quote.class);

            return quote;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }
    }
}
