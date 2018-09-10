package alanbiaaturath.apps.turathbooksantuni;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class splash extends AppCompatActivity
{
    private Handler handler = new Handler();
    private int progressStatus = 0;

    Button btn_open_app;
    Button btn_open_app_en;
    ProgressBar pb;
    String stop;
    String message;
    String latest_version;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        btn_open_app = (Button) findViewById(R.id.btn_open_app);
        btn_open_app_en = (Button) findViewById(R.id.btn_open_app_en);
        pb = (ProgressBar) findViewById(R.id.my_progressBar);

        btn_open_app.setVisibility(View.INVISIBLE);
        btn_open_app_en.setVisibility(View.INVISIBLE);
        SharedPreferences prfs = getSharedPreferences("AUTHENTICATION_FILE_NAME", this.MODE_PRIVATE);
        int close = prfs.getInt("close", 0);
        stop = String.valueOf(close);
        btn_open_app.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                switch (stop)
                {
                    case "0":
                        open_main_activity(1);
                        break;
                    case "1":
                        goToUpdate();
                        break;
                    case "2":
                        closeApp();
                        break;
                }
            }
        });
        btn_open_app_en.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                switch (stop)
                {
                    case "0":
                        open_main_activity(2);
                        break;
                    case "1":
                        goToUpdate();
                        break;
                    case "2":
                        closeApp();
                        break;
                }
            }
        });
        // http connection
        final String url = "https://turathalanbiaa.com/api/app-status/thereisagod";
        final HTTP http = new HTTP();
        final Map<String, String> params = new HashMap<>();
        params.put("version", "1");
        try
        {
            new AsyncTask<Void, Void, Boolean>()
            {
                protected Boolean doInBackground(Void... params)
                {
                    doOneThing();
                    return null;
                }

                protected void onPostExecute(Boolean result)
                {
                    stopProgress();
                    btn_open_app.setText("");
                    btn_open_app.setVisibility(View.VISIBLE);
                    btn_open_app_en.setVisibility(View.VISIBLE);
                    switch (stop)
                    {
                        case "0":
                            btn_open_app.setText(R.string.read_book);
                            break;
                        case "1":
                            btn_open_app.setText(R.string.click_update);
                            update();
                            break;
                        case "2":
                            closeApp();
                            break;
                    }
                }

                private void doOneThing()
                {
                    String response = HTTP.post(url, params);
                    try
                    {
                        JSONObject jsonObject = new JSONObject(response);
                        stop = jsonObject.getString("stop");
                        message = jsonObject.getString("message");
                        latest_version = jsonObject.getString("latest_version");
                    }
                    catch (Exception e)
                    {
                    }
                }
            }.execute();
        }

        catch (Exception e)
        {
            if (close == 2)
            {
                btn_open_app.setText(R.string.no_active);
                btn_open_app_en.setText("not active");


                closeApp();
            }
            else if (close == 1)
            {
                btn_open_app.setText(R.string.click_update);
                btn_open_app_en.setText("click to update");
            }
            else
            {
                btn_open_app.setText(R.string.read_book);
                btn_open_app_en.setText("read book");


            }
        }
    }

    private void update()
    {
        SharedPreferences prfs = getSharedPreferences("AUTHENTICATION_FILE_NAME", this.MODE_PRIVATE);
        SharedPreferences.Editor editor = prfs.edit();
        editor.putInt("close", 1);
        editor.apply();
    }

    private void goToUpdate()
    {
        SharedPreferences prfs = getSharedPreferences("AUTHENTICATION_FILE_NAME", this.MODE_PRIVATE);
        SharedPreferences.Editor editor = prfs.edit();
        editor.putInt("close", 1);
        editor.apply();
        final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
        try
        {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        }
        catch (android.content.ActivityNotFoundException anfe)
        {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
        }
    }

    private void closeApp()
    {
        btn_open_app.setText(R.string.no_active);
        btn_open_app_en.setText("not active");

        SharedPreferences prfs = getSharedPreferences("AUTHENTICATION_FILE_NAME", this.MODE_PRIVATE);
        SharedPreferences.Editor editor = prfs.edit();
        editor.putInt("close", 2);
        editor.apply();
    }

    private void stopProgress()
    {
        pb.setVisibility(View.INVISIBLE);

    }

    private void open_main_activity(int lang)
    {

        Intent i = new Intent(this, ScrollingActivity.class);
        if (lang == 1)
        {
            i.putExtra("lang" , 1);


        }else
        {
            i.putExtra("lang" , 2);

        }


        startActivity(i);

    }
}
