package com.example.edisonoffice.bargraph;

import android.app.Activity;
import android.graphics.Color;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Main1 extends Activity implements View.OnClickListener {

    private static String TAG = "MainActivity";

    Button day, week, month, year;
    private String buttontext;
    private float[] yData;
    BarChart chart;
    int sum = 0;
    ArrayList<String> data = new ArrayList<>();
    ArrayList<Integer> data1 = new ArrayList<>();
    ArrayList<Float> data_sum_ind = new ArrayList<>();
    private JSONArray rooms;
    JSONObject jsonobject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        chart = (BarChart) findViewById(R.id.chart);
        day = (Button) findViewById(R.id.btn_day);
        week = (Button) findViewById(R.id.btn_week);
        month = (Button) findViewById(R.id.btn_month);
        year = (Button) findViewById(R.id.btn_year);

        day.setOnClickListener(this);
        week.setOnClickListener(this);
        month.setOnClickListener(this);
        year.setOnClickListener(this);

        chart.setDescription("");
        //setting the zoom horizontally when opening application and verticalli 0f
        chart.setScaleMinima(2f, 0f);

    }


    public void post() throws JSONException, IOException {

        // Creating HTTP client
        HttpClient httpClient = new DefaultHttpClient();
        // Creating HTTP Post
        HttpPost httpPost = new HttpPost("http://edisonbro.in/createfolder/logs2.php");
        List<NameValuePair> nameValuePair = new ArrayList<>(2);
        nameValuePair.add(new BasicNameValuePair("cid", "1111"));
        nameValuePair.add(new BasicNameValuePair("type", buttontext));
        // Url Encoding the POST parameters
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
        } catch (UnsupportedEncodingException e) {

            e.printStackTrace();
        }
        // Making HTTP Request
        try {
            final HttpResponse response = httpClient.execute(httpPost);
            String responseBody = null;

            HttpEntity entity = response.getEntity();
            if (entity != null) {
                responseBody = EntityUtils.toString(entity);
                JSONObject jsonObj = new JSONObject(responseBody);

                rooms = jsonObj.getJSONArray("Rooms");

                for (int i = 0; i < rooms.length(); i++) {
                    jsonobject = rooms.getJSONObject(i);

                    String id = jsonobject.getString("roomname");
                    int per = Integer.parseInt(jsonobject.getString("oper"));
                    Log.d("TAG", "rooms :" + id);
                    data.add(id);
                    data1.add(per);

                }
                sum=0;
                for (int i1 : data1)

                    sum += i1;
                Log.d("TAG", "sum_array :" + sum);


                for(int j =0;j<rooms.length();j++){

                    long ind_sum = 100L * data1.get(j) / sum;
                    Log.d("TAG", "sum :" + ind_sum);
                    //   int ind_sum= sum/data1.get(j)*100;
                    data_sum_ind.add((float) ind_sum);
                    Log.d("TAG", "sum_data_after_arry :" + data_sum_ind);


                }
               }

            Log.d("TAG", "sum :" + sum);
            Log.d("TAG", "Http post Response: " + responseBody);

            Log.d("TAG", responseBody);

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    BarData data = new BarData(getXAxisValues(), getDataSet());
                    chart.setData(data);
                    chart.animateXY(1000, 1000);
                    chart.invalidate();


                }
            });
        } catch (IOException e) {
            // writing exception to log
            e.printStackTrace();

        }
    }

    private ArrayList<BarDataSet> getDataSet() {
        ArrayList<BarDataSet> dataSets = null;

        ArrayList<BarEntry> valueSet2 = new ArrayList<>();

        for (int i = 0; i < rooms.length(); i++) {
            BarEntry v2e1 = new BarEntry(((data_sum_ind.get(i))), i);
            valueSet2.add(v2e1);

        }

        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.RED);
        colors.add(Color.BLUE);
        colors.add(Color.YELLOW);
        colors.add(Color.GREEN);
        colors.add(Color.DKGRAY);
        colors.add(Color.MAGENTA);


        BarDataSet barDataSet2 = new BarDataSet(valueSet2, "");
        barDataSet2.setColors(colors);
        dataSets = new ArrayList<>();
        dataSets.add(barDataSet2);
        return dataSets;
    }

    private ArrayList<String> getXAxisValues() {
        ArrayList<String> xAxis = new ArrayList<>();
        for (int i = 0; i < rooms.length(); i++) {
            xAxis.add(data.get(i));
        }

        return xAxis;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_day:

                data.clear();
                data1.clear();
                data_sum_ind.clear();
                buttontext = "day";

                Thread t = new Thread() {
                    public void run() {
                        try {
                            post();

                        } catch (JSONException | IOException e) {
                            e.printStackTrace();
                        }
                    }


                };
                t.start();
                break;
            case R.id.btn_week:
                data.clear();
                data_sum_ind.clear();
                data1.clear();
                buttontext = "week";

                Thread t1 = new Thread() {
                    public void run() {
                        try {
                            post();
                        } catch (JSONException | IOException e) {
                            e.printStackTrace();
                        }
                    }
                };
                t1.start();
                break;

            case R.id.btn_month:
                buttontext = "month";
                data.clear();
                data_sum_ind.clear();
                data1.clear();
                t = new Thread() {
                    public void run() {
                        try {
                            post();
                        } catch (JSONException | IOException e) {
                            e.printStackTrace();
                        }
                    }
                };
                t.start();
                break;

            case R.id.btn_year:
                data.clear();
                data1.clear();
                data_sum_ind.clear();
                buttontext = "year";

                t = new Thread() {
                    public void run() {
                        try {
                            post();
                        } catch (JSONException | IOException e) {
                            e.printStackTrace();
                        }
                    }
                };
                t.start();
                break;

        }

    }
}
