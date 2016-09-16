package com.example.pawan.getjsondata;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.support.design.widget.Snackbar;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.security.PrivateKey;
import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    JSONParser jsonParser = new JSONParser();
    private static final String SUBMIT_QUERY = Constants.NIIT + "/JsonData/FirstJsonData";
    TextView textView;
    Button button;
    String status;
    ProgressDialog pDialog;
    static ArrayList<Integer> user_id = new ArrayList<Integer>();
    static ArrayList<String> user_name = new ArrayList<String>();
    static ArrayList<String> user_pwd = new ArrayList<String>();
    private ArrayList<Integer> id;
    private ArrayList<String> name;
    private ArrayList<String> password;


    public ArrayList<Integer> getId() {
        return id;

    }

    public void setId(ArrayList<Integer> id) {
        this.id = id;
    }

    public ArrayList<String> getPassword() {
        return password;
    }

    public void setPassword(ArrayList<String> password) {
        this.password = password;
    }

    public ArrayList<String> getName() {
        return name;
    }

    public void setName(ArrayList<String> name) {
        this.name = name;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.textViewJSON);
        button = (Button) findViewById(R.id.buttonGet);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isInternetOn() == true) {
                    new AttemptSubmitQuery().execute();
                    Intent i = new Intent(MainActivity.this, SecondActivity.class);

                    i.putIntegerArrayListExtra("id", user_id);
                    i.putStringArrayListExtra("name", user_name);
                    i.putStringArrayListExtra("password", user_pwd);
                    finish();
                    startActivity(i);

                } else {
                    Snackbar snackbar = Snackbar
                            .make(findViewById(android.R.id.content), "No internet connection !!", Snackbar.LENGTH_LONG)
                            .setAction("RETRY", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                }
                            });
                    snackbar.setActionTextColor(Color.RED);
                    View sbView = snackbar.getView();
                    TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                    textView.setTextColor(Color.YELLOW);
                    snackbar.show();
                }
            }

        });


    }

    public boolean isInternetOn() {
        ConnectivityManager connectivitymanager = (ConnectivityManager) getApplicationContext().getSystemService("connectivity");
        if (connectivitymanager != null) {
            NetworkInfo anetworkinfo[] = connectivitymanager.getAllNetworkInfo();
            if (anetworkinfo != null) {
                for (int i = 0; i < anetworkinfo.length; i++) {
                    if (anetworkinfo[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }

            }
        }
        return false;
    }

    class AttemptSubmitQuery extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Fetching Data!!!!");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected String doInBackground(String... args) {

            int success;
            try {
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("name", "pawan"));
                params.add(new BasicNameValuePair("passowrd", "101"));
                Log.d("request!", "starting");
                JSONObject json = jsonParser.makeHttpRequest(SUBMIT_QUERY, "POST", params);
                Log.e("json", json.toString());

                Log.e("successs", json.getString("success"));
                int a = json.getInt("success");
                Log.e("successa", a + "");

                Gson gson = new Gson();
                List<DataBeans> dataBeans = gson.fromJson(json.getString("USER"), new TypeToken<List<DataBeans>>() {
                }.getType());
                Log.e("dataa", json.getString("USER"));
                Iterator<DataBeans> ii = dataBeans.iterator();
                while (ii.hasNext()) {
                    DataBeans itr = (DataBeans) ii.next();

                    int id = itr.getId();
                    //Log.e("id:",id+"");
                    user_id.add(id);

                    String name = itr.getName();
                    user_name.add(name);

                    String password = itr.getPassword();
                    user_pwd.add(password);
                }

                if (a == 1) {
                    status = "1";
                    setId(user_id);
                    setName(user_name);
                    setPassword(user_pwd);
/*
                    Intent i = new Intent(MainActivity.this, SecondActivity.class);

                    i.putIntegerArrayListExtra("id", user_id);
                    i.putStringArrayListExtra("name", user_name);
                    i.putStringArrayListExtra("password", user_pwd);
                    finish();
                    startActivity(i);
*/

                    return status;
                } else {
                    status = "0";
                    return status;
                }


            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String message) {

            if (message.equals("1")) {
                Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Data Send Successfully", Snackbar.LENGTH_LONG);
                View sbView = snackbar.getView();
                TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                sbView.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark));
                textView.setTextColor(Color.rgb(24, 124, 255));
                snackbar.show();
            } else {

                Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Not able to send.. please try later ", Snackbar.LENGTH_LONG);
                View sbView = snackbar.getView();
                TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                sbView.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark));
                textView.setTextColor(Color.RED);
                snackbar.show();
            }


            dismissProgressDialog();
        }

        private void dismissProgressDialog() {
            if (pDialog != null && pDialog.isShowing()) {
                pDialog.dismiss();
            }
        }

    }
}

