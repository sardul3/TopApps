package p.topapps;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    //private String address = "http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topfreeapplications/limit=10/xml";
    //private String address = "https://www.apple.com/newsroom/rss-feed.rss";
    private String address = "https://www.wired.com/feed/rss";
    String mData = "";
    //TextView dataDump;
    Button parse;
    ListView listView;
    ArrayAdapter<AppInfo> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.list_view);
        parse = (Button) findViewById(R.id.button);
        parse.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Parser parseProcess = new Parser(mData);
                parseProcess.process();

                 arrayAdapter = new ArrayAdapter<AppInfo>(MainActivity.this, R.layout.list_items, Parser.getAppInfo());
                listView.setAdapter(arrayAdapter);

            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String url = (String) arrayAdapter.getItem(position).getDateCreated();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);

            }
        });


        //dataDump = (TextView) findViewById(R.id.data_dump);
        Downloader downloader = new Downloader();
        downloader.execute(address);


    }

    private class Downloader extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... params) {

            mData = downloadXML(params[0]);
            return mData;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("Downloader", s);
            //dataDump.setText(s);


        }

        private String downloadXML(String urlSource){
            StringBuilder sb = new StringBuilder();
            try {
                URL url = new URL(urlSource);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                InputStream stream = connection.getInputStream();
                InputStreamReader reader = new InputStreamReader(stream);

                int i;
                char[] buffer = new char[500];
                while(true){
                    i = reader.read(buffer);
                    if(i<=0){
                        break;
                    }
                    sb.append(String.copyValueOf(buffer, 0 , i));
                }

           } catch (Exception e) {
                e.printStackTrace();
            }
            return sb.toString();
        }
    }
}
