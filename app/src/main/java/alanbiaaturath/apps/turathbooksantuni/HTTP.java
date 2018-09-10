package alanbiaaturath.apps.turathbooksantuni;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Map;

/**
 * Created by hp
 * on 2016-08-09.
 */
public class HTTP
{
    private static final int timeout = 20000;

    public static String post(String url , Map<String , String> params)
    {
        try
        {
            URLConnection conn = new URL(url).openConnection();
            conn.setConnectTimeout(timeout);

            conn.setDoOutput(true);
            OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());

            String parameter = "";
            Object[] keys = params.keySet().toArray();
            for (int i=0;i<keys.length;i++)
            {
                String key = keys[i].toString();
                parameter = parameter + URLEncoder.encode(key , "UTF-8") + "=" + URLEncoder.encode(params.get(key) , "UTF-8") + (i==keys.length-1 ? "" : "&");
            }

            writer.write(parameter);
            writer.flush();

            String response = "";
            String line;
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            while ((line = reader.readLine()) != null)
            {
                response = response + line;
            }

            writer.close();
            reader.close();

            return response;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }

    }

}
