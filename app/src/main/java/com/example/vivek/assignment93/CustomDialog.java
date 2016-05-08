package com.example.vivek.assignment93;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by vivek on 09-05-2016.
 */
public class CustomDialog extends Dialog{

    EditText seconds;
    ProgressDialog progressDialog;
    Button okButton,cancelButton;
    Context ctx;

    public CustomDialog(Context context) {
        super(context);
        ctx = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog);
        seconds = (EditText)findViewById(R.id.seconds);
        okButton = (Button)findViewById(R.id.okButton);
        cancelButton = (Button)findViewById(R.id.cancelButton);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = seconds.getText().toString();
                Integer secs = Integer.parseInt(s);
                waitTask w = new waitTask();
                w.execute(secs);
                dismiss();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                Toast.makeText(ctx, "Cancelled", Toast.LENGTH_LONG).show();
            }
        });
    }

    class waitTask extends AsyncTask<Integer, Integer, Integer> {

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(ctx);
            progressDialog.setMessage("Processing...");
            progressDialog.show();
        }

        @Override
        protected Integer doInBackground(Integer... params) {

            long time = Calendar.getInstance().getTimeInMillis();
            Log.d("Waiting:", "time " + time);
            long waitTime = time + params[0] * 1000;
            Log.d("Waiting:", "waitTime " + time);

            while (Calendar.getInstance().getTimeInMillis() <= waitTime) {
                int progress = (int) ((Calendar.getInstance().getTimeInMillis() - time) / (waitTime - time)) * 100;
                publishProgress(progress);
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            progressDialog.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Integer integer) {
            progressDialog.dismiss();
            Toast.makeText(ctx, "Completed!", Toast.LENGTH_SHORT).show();
        }
    }

}
