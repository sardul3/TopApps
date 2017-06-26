package p.topapps;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.ArrayList;

/**
 * Created by Sardul on 5/22/2017.
 */

public class Parser {
    private String xmlData;
    private static ArrayList<AppInfo> apps;
    public final static String TAG = "Parser";

    public Parser(String xmlData){
        this.xmlData = xmlData;
        apps = new ArrayList<>();
    }

    public static ArrayList<AppInfo> getAppInfo(){
        return apps;
    }

    public boolean process(){
        boolean status = true;
        String eventName = "";
        boolean entry = false;
        AppInfo currentApp = null;
        String output = "";

        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(new StringReader(xmlData));

            int eventType = parser.getEventType();

            while(eventType != XmlPullParser.END_DOCUMENT){
                eventName = parser.getName();
                switch(eventType){

                    case XmlPullParser.START_TAG:
                        if(eventName.equalsIgnoreCase("item"/*"entry"*/)){
                            entry = true;
                            currentApp = new AppInfo();
                        }
                        Log.d(TAG,"Starting the tag" + eventName);
                        break;

                    case XmlPullParser.TEXT:
                        output = parser.getText();
                        break;

                    case XmlPullParser.END_TAG:
                        Log.d(TAG, "Ending the tag" + eventName);
                        if(entry){
                            if(eventName.equalsIgnoreCase(/*"entry"*/"item")) {
                                apps.add(currentApp);
                                entry = false;
                            }
                           else if(eventName.equalsIgnoreCase(/*"name"*/"title")){
                                currentApp.setAppName(output);}
                            else if(eventName.equalsIgnoreCase(/*"artist"*/"description")){
                                currentApp.setAuthor(output);}
                            else if(eventName.equalsIgnoreCase(/*"releaseDate"*/"link")){
                              currentApp.setDateCreated(output);}
                        }

                        break;

                    default:
                }
                eventType = parser.next();
            }

        } catch (Exception e) {
            status = false;
            e.printStackTrace();

        }
        for(AppInfo app : apps) {
            Log.d(TAG, "****************");
            Log.d(TAG, "Name: " + app.getAppName());
            Log.d(TAG, "Artist: " + app.getAuthor());
            Log.d(TAG, "Release Date: " + app.getDateCreated());


        }




        return true;
    }
}
