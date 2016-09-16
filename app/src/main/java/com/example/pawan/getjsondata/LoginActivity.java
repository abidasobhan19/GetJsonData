package com.example.pawan.getjsondata;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pawan on 9/16/2016.
 */
public class LoginActivity extends AppCompatActivity {

    EditText etname, etpassword;
    Button btLogin;
    ProgressBar progressBar;
    String status = "1";
    private static final String TAG_SUCCESS = "success";
    JSONObject jsonobject=new JSONObject();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etname = (EditText) findViewById(R.id.etname);
        etpassword = (EditText) findViewById(R.id.etpassword);
        btLogin = (Button) findViewById(R.id.btnlogin);

        progressBar=(ProgressBar) findViewById(R.id.progressBar1);
        progressBar.setVisibility(View.GONE);

        btLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                progressBar.setVisibility(View.VISIBLE);

                String s1 = etname.getText().toString();
                String s2 = etpassword.getText().toString();
                new ExecuteTask().execute(s1, s2);
                Intent i=new Intent(LoginActivity.this,MainActivity.class);
                startActivity(i);


            }
        });


    }


    class ExecuteTask extends AsyncTask<String, Integer, String> {




        @Override
        protected String doInBackground(String... params) {
            int success;
            String res = PostData(params);

            return res;
            //JSONParser jsonparser=new JSONParser();


           /* if (success == 1) {
                status = "1";
                Intent i=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(i);
                return status;
            } else {
                status = "0";
                return status;
            }*/
        }

        @Override
        protected void onPostExecute(String result) {
            progressBar.setVisibility(View.GONE);
            //progess_msz.setVisibility(View.GONE);
            Toast.makeText(getApplicationContext(), result, 3000).show();









        }



    }

    public String PostData(String[] valuse) {
        String s = "";
        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost("http://10.0.3.2:8080/Loginuser/Login");

            List<NameValuePair> list = new ArrayList<NameValuePair>();
            list.add(new BasicNameValuePair("name", valuse[0]));
            list.add(new BasicNameValuePair("pass", valuse[1]));
            httpPost.setEntity(new UrlEncodedFormEntity(list));
            HttpResponse httpResponse = httpClient.execute(httpPost);

            HttpEntity httpEntity = httpResponse.getEntity();
            s = readResponse(httpResponse);
            Log.e("values",s);

        } catch (Exception exception) {
        }
        return s;


    }

    public String readResponse(HttpResponse res) {
        InputStream is = null;
        String return_text = "";
        try {
            is = res.getEntity().getContent();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
            String line = "";
            StringBuffer sb = new StringBuffer();
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
            return_text = sb.toString();
        } catch (Exception e) {

        }
        return return_text;


    }
}
