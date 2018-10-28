package com.example.advillion.ratemytreater;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import android.os.AsyncTask;
import java.lang.String;
import java.io.BufferedReader;
import android.os.NetworkOnMainThreadException;
import java.util.ArrayList;

public class Data extends AsyncTask<String, String, String>{
    String info = "";
    URL url;
    HttpURLConnection con = null;
    String address = "";
    ArrayList<String> finalCopy = new ArrayList<String>();

    @Override
    protected String doInBackground(String... urls){
        try {
            url = new URL(urls[0]);
            con = (HttpURLConnection) url.openConnection();
            InputStream in = con.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while(line != null) {
                line = br.readLine();
                info = info + line;
            }
        }
        catch(NetworkOnMainThreadException e){
            return "NMTE";
        }
        catch(MalformedURLException a){
            return "MUE";
        }
        catch(IOException i){
            return "IOE";
        }
        int start = info.indexOf("formatted");
        start = start + 12;
        int end = info.indexOf("geometry");
        end = end - 3;
        address = info.substring(start,end);

        String line;
        String display = address;
        String[] part = address.split(" ");
        try {
            int num = Integer.parseInt(part[0]);
            int num2 = Integer.parseInt(part[0]);
            for(int i = 1; i < 5; i++){
                if(num-i > 0) {
                    line = Integer.toString(num - i);
                    for (int j = 1; j < part.length; j++) {
                        line += " " + part[j];
                    }
                    display += "~" + line;
                }else{
                    i = 6;
                }
            }
            for(int i = 0; i < 4; i++){
                num2++;
                line = Integer.toString(num2);
                for(int j = 1; j < part.length; j++){
                    line += " " + part[j];
                }
                display += "~" + line;
            }

        }
        catch(Exception e){

        }

        return display;
    }


    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
    }

}