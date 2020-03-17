package com.gaegul2moja.httptester;

import android.os.AsyncTask;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.net.URL;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ReceiveShortWeatherAsync extends AsyncTask<URL, Integer, Long> {
    public static String url = "https://www.kma.go.kr/wid/queryDFSRSS.jsp?zone=1159068000";
    private TextView textView;
    ArrayList<ShortWeather> shortWeathers = new ArrayList<ShortWeather>();

    ReceiveShortWeatherAsync (TextView textView) {
        this.textView = textView;
    }

    protected Long doInBackground(URL... urls) {

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = null;

        try {
            response = client.newCall(request).execute();
            parseXML(response.body().string());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    protected void onPostExecute(Long result) {
        String data = "";

        for(int i=0; i<shortWeathers.size(); i++) {
            data += shortWeathers.get(i).getHour() + "시 " +
                    shortWeathers.get(i).getDay() + "일 " +
                    shortWeathers.get(i).getTemp() + "도 " +
                    shortWeathers.get(i).getWfKor() + " " +
                    shortWeathers.get(i).getPop() + "\n";
        }

        textView.setText(data);
    }

    void parseXML(String xml) {
        try {
            String tagName = "";
            boolean onHour = false;
            boolean onDay = false;
            boolean onTem = false;
            boolean onWfKor = false;
            boolean onPop = false;
            boolean onEnd = false;
            boolean isItemTag1 = false;
            int i = 0;

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();

            parser.setInput(new StringReader(xml));

            int eventType = parser.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG) {
                    tagName = parser.getName();
                    if (tagName.equals("data")) {
                        shortWeathers.add(new ShortWeather());
                        onEnd = false;
                        isItemTag1 = true;
                    }
                } else if (eventType == XmlPullParser.TEXT && isItemTag1) {
                    if (tagName.equals("hour") && !onHour) {
                        shortWeathers.get(i).setHour(parser.getText());
                        onHour = true;
                    }
                    if (tagName.equals("day") && !onDay) {
                        shortWeathers.get(i).setDay(parser.getText());
                        onDay = true;
                    }
                    if (tagName.equals("temp") && !onTem) {
                        shortWeathers.get(i).setTemp(parser.getText());
                        onTem = true;
                    }
                    if (tagName.equals("wfKor") && !onWfKor) {
                        shortWeathers.get(i).setWfKor(parser.getText());
                        onWfKor = true;
                    }
                    if (tagName.equals("pop") && !onPop) {
                        shortWeathers.get(i).setPop(parser.getText());
                        onPop = true;
                    }
                } else if (eventType == XmlPullParser.END_TAG) {
                    if (tagName.equals("s06") && onEnd == false) {
                        i++;
                        onHour = false;
                        onDay = false;
                        onTem = false;
                        onWfKor = false;
                        onPop = false;
                        isItemTag1 = false;
                        onEnd = true;
                    }
                }

                eventType = parser.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}