package com.example.silvio.sg_test.activity;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.Toast;

import com.example.silvio.sg_test.R;
import com.example.silvio.sg_test.SQLite.DatabaseManager;
import com.example.silvio.sg_test.model.User;
import com.example.silvio.sg_test.singleton.UsersList;
import com.example.silvio.sg_test.validator.DataValidation;

import java.math.BigInteger;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    public static final String EXTRA_MESSAGE = "Registration screen ...";

    private final AppCompatActivity activity = MainActivity.this;
    private NestedScrollView nestedScrollView;
    private TextInputLayout textInputLayoutEmail;
    private TextInputLayout textInputLayoutPassword;

    private TextInputEditText textInputEditTextEmail;
    private TextInputEditText textInputEditTextPassword;

    private AppCompatButton appCompatButtonLogin;
    private AppCompatButton appCompatButtonRegister;

    private DataValidation dataValidation;
    private DatabaseManager databaseHelper;

    private UsersList storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        initViews();
        initListeners();
        initObjects();
    }

    private void initViews() {

        nestedScrollView = (NestedScrollView) findViewById(R.id.nestedScrollView);

        textInputLayoutEmail = (TextInputLayout) findViewById(R.id.textInputLayoutEmail);
        textInputLayoutPassword = (TextInputLayout) findViewById(R.id.textInputLayoutPassword);

        textInputEditTextEmail = (TextInputEditText) findViewById(R.id.textInputEditTextEmail);
        textInputEditTextPassword = (TextInputEditText) findViewById(R.id.textInputEditTextPassword);

        appCompatButtonLogin = (AppCompatButton) findViewById(R.id.appCompatButtonLogin);
        appCompatButtonRegister = (AppCompatButton) findViewById(R.id.appCompatButtonRegister);

    }

    private void initListeners() {
        appCompatButtonLogin.setOnClickListener(this);
        appCompatButtonRegister.setOnClickListener(this);
    }

    private void initObjects() {
        databaseHelper = new DatabaseManager(activity);
        dataValidation = new DataValidation(activity);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.appCompatButtonLogin:
     //           verifyFromSingleton();
                verifyFromSQLite();
                break;
            case R.id.appCompatButtonRegister:
                // Goto SignUpActivity
                Intent intentRegister2 = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(intentRegister2);
                break;
        }
    }

    private void verifyFromSingleton() {
        if (!dataValidation.isInputEditTextFilled(textInputEditTextEmail, textInputLayoutEmail, getString(R.string.error_message_email_low_field))) {
            return;
        }
        if (!dataValidation.isInputEditTextEmail(textInputEditTextEmail, textInputLayoutEmail, getString(R.string.error_message_email))) {
            return;
        }
        if (!dataValidation.isInputEditTextFilled(textInputEditTextPassword, textInputLayoutPassword, getString(R.string.error_message_password_low_field))) {
            return;
        }

        List<User> users = storage.getUsers();
        String str_email = textInputEditTextEmail.getText().toString().trim();
        String pass = storage.md5(textInputEditTextPassword.getText().toString().trim());
        for (User uu : users) {
            if (str_email.equals(uu.getEmail())) {
                if (pass.compareTo(uu.getPassword()) == 0) {
                    Intent accountsIntent = new Intent(getApplicationContext(), UserDetailsActivity.class);
                    accountsIntent.putExtra("EMAIL", (String) textInputEditTextEmail.getText().toString().trim());
                    emptyInputEditText();
                    startActivity(accountsIntent);
                } else
                    Toast.makeText(getApplicationContext(), getString(R.string.error_valid_email_password), Toast.LENGTH_LONG).show();
            }
        }
    }

    private void verifyFromSQLite() {
        if (!dataValidation.isInputEditTextFilled(textInputEditTextEmail, textInputLayoutEmail, getString(R.string.error_message_email_low_field))) {
            return;
        }
        if (!dataValidation.isInputEditTextEmail(textInputEditTextEmail, textInputLayoutEmail, getString(R.string.error_message_email))) {
            return;
        }
        if (!dataValidation.isInputEditTextFilled(textInputEditTextPassword, textInputLayoutPassword, getString(R.string.error_message_password_low_field))) {
            return;
        }

        if (databaseHelper.checkUser(textInputEditTextEmail.getText().toString().trim()
                , textInputEditTextPassword.getText().toString().trim())) {
            Intent accountsIntent = new Intent(getApplicationContext(), UserDetailsActivity.class);
            accountsIntent.putExtra("EMAIL", (String) textInputEditTextEmail.getText().toString().trim());
            emptyInputEditText();
            startActivity(accountsIntent);
        } else {
            Toast.makeText(getApplicationContext(), getString(R.string.error_valid_email_password), Toast.LENGTH_LONG).show();
        }
    }

    private void emptyInputEditText() {
        textInputEditTextEmail.setText(null);
        textInputEditTextPassword.setText(null);
    }
}
