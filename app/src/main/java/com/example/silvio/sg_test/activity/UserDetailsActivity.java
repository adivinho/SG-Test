package com.example.silvio.sg_test.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.TextView;

import com.example.silvio.sg_test.R;

public class UserDetailsActivity extends AppCompatActivity implements View.OnClickListener{

    private AppCompatButton appCompatButtonExit;
    public TextView UserEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userdetails);

        initViews();
        initListeners();
    }

    private void initViews() {
        UserEmail = (TextView) findViewById(R.id.textViewEmail);
        UserEmail.setText(getIntent().getStringExtra("EMAIL"));
        appCompatButtonExit = (AppCompatButton) findViewById(R.id.appCompatButtonExit);
    }

    private void initListeners() {
        appCompatButtonExit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.appCompatButtonExit:
                finish(); // get back to main activity
                break;
        }
    }
}
