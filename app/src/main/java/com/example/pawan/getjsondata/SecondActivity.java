package com.example.pawan.getjsondata;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Pawan on 9/14/2016.
 */
public class SecondActivity extends AppCompatActivity {
    TextView tv1;
    TextView tv2;
    TextView tv3;
    TextView tv4;
    TextView tv5;
    TextView tv6;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);


        //TextView tv=new TextView(this);
        //tv.setText("second activity");
        setContentView(R.layout.activity_second);
        Intent extras = getIntent();
        ArrayList<Integer> value1 = new ArrayList<Integer>(extras.getIntegerArrayListExtra("id"));
        ArrayList<String> value2 = new ArrayList<String>(extras.getStringArrayListExtra("name"));
        ArrayList<String> value3 = new ArrayList<String>(extras.getStringArrayListExtra("password"));
        //String value2 = extras.getString("name");
        //String value3 = extras.getString("password");
        //value1 = i.getStringArrayListExtra("stock_list");
        /*Toast.makeText(getApplicationContext(),"Values are:\n First value: "+value1+
                "\n Second Value: "+value2+"\n Third Value: "+value3,Toast.LENGTH_LONG).show();*/
      /*  LinearLayout ll = (LinearLayout) findViewById(R.id.Linear1);
        int j=5;
        for (int i = 0; i < value1.size(); i++)
        {
            TextView tv = new TextView(this);
            tv.setText(value2.get(i));
            j=j+i;
            tv.setId(j);
            j++;

        }*/

       /* tv1 = (TextView) findViewById(R.id.textView5);
        tv1.setText(value1.get(0).toString());
        tv2 = (TextView) findViewById(R.id.textView6);
        tv2.setText( value2.get(0));
        tv3 = (TextView) findViewById(R.id.textView4);
        tv3.setText(value3.get(0).toString());

        tv4 = (TextView) findViewById(R.id.textView7);
        tv4.setText(value1.get(1).toString());
        tv5 = (TextView) findViewById(R.id.textView8);
        tv5.setText( value2.get(1));
        tv6 = (TextView) findViewById(R.id.textView9);
        tv6.setText(value3.get(1).toString());

*/
        LinearLayout ll = (LinearLayout) findViewById(R.id.Linear1);

        final int N = 10; // total number of textviews to add

        final TextView[] myTextViews = new TextView[N]; // create an empty array;

        for (int i = 0; i < value1.size(); i++) {
            // create a new textview
            final TextView rowTextView = new TextView(this);

            // set some properties of rowTextView or something
            rowTextView.setText("ID    " + value1.get(i));


            // add the textview to the linearlayout
            ll.addView(rowTextView);

            // save a reference to the textview for later
            myTextViews[i] = rowTextView;
        }
        for (int i = 0; i < value1.size(); i++) {
            // create a new textview
            final TextView rowTextView = new TextView(this);

            // set some properties of rowTextView or something
            rowTextView.setText("NAME    " + value2.get(i));

            // add the textview to the linearlayout
            ll.addView(rowTextView);

            // save a reference to the textview for later
            myTextViews[i] = rowTextView;
        }
        for (int i = 0; i < value1.size(); i++) {
            // create a new textview
            final TextView rowTextView = new TextView(this);

            // set some properties of rowTextView or something
            rowTextView.setText("PASSWORD   " + value3.get(i));

            // add the textview to the linearlayout
            ll.addView(rowTextView);

            // save a reference to the textview for later
            myTextViews[i] = rowTextView;
        }


    }
    @Override
    public void onBackPressed() {
        finish();

        Intent intent = new Intent(SecondActivity.this, MainActivity.class);
        startActivity(intent);
        goBack();

    }
    public void goBack() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                SecondActivity.this);

        alertDialog.setTitle("");
        alertDialog.setMessage("Are you sure you want to exit?");

        alertDialog.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        System.exit(0);
                    }
                });

        alertDialog.setNegativeButton("NO",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        alertDialog.show();
    }


}
