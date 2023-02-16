package com.example.fasszo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final String TAG = RegisterActivity.class.getName();
    private static final int SECRET_KEY = 99;
    private static final String PREF_KEY = RegisterActivity.class.getPackage().toString();
    private SharedPreferences preferences;
    private FirebaseAuth mAuth;
    EditText userNameEditText;
    EditText userEmailEditText;
    EditText userPasswordEditText;
    EditText userPasswordConfirmEditText;
    EditText phoneEditText;
    EditText addressEditText;
    Spinner phoneSpinner;
    RadioGroup accountTypeGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.i(TAG, TAG + "onCreate");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //az activityt hivo intent elerese
        //1. módszer ha sok adat van
        /*
        Bundle bundle = getIntent().getExtras();
        int secret_key = bundle.getInt("SECRET_KEY");
        */
        //2.módszer ha csak pár adat van:
        int secret_key = getIntent().getIntExtra("SECRET_KEY", 0);

        if (secret_key != 99){
            finish();
        }

        userNameEditText = findViewById(R.id.userNameEditText);
        userEmailEditText = findViewById(R.id.userEmailEditText);
        userPasswordEditText = findViewById(R.id.userPasswordEditText);
        userPasswordConfirmEditText = findViewById(R.id.userConfirmPasswordEditText);
        phoneSpinner = findViewById(R.id.phoneSpinner);
        phoneEditText = findViewById(R.id.editTextPhone);
        addressEditText = findViewById(R.id.editTextTextPostalAddress);
        accountTypeGroup = findViewById(R.id.accountTypeGroup);
        accountTypeGroup.check(R.id.costumerRadioButton); //alapból becsekkeli az elemet
        preferences = getSharedPreferences(PREF_KEY, MODE_PRIVATE);
        //kinyerjük azt amit a mainben eltároltunk
        String userName = preferences.getString("userName", "");
        String passWord = preferences.getString("passWord", "");

        userNameEditText.setText(userName);
        userPasswordEditText.setText(passWord);
        userPasswordConfirmEditText.setText(passWord);

        phoneSpinner.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.phones, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        phoneSpinner.setAdapter(adapter);

        mAuth = FirebaseAuth.getInstance();
        //ezzel fogjuk elérni a firebase auth cuccokat

        Log.i(TAG, "onCreate");
    }

    public void register(View view) {

        if (!(userPasswordConfirmEditText.getText().toString().equals(userPasswordEditText.getText().toString()))){
            Log.e(TAG, "A két jelszó nem egyezik");
            return;
        }

        //radiogroup cuccok
        int checkedId = accountTypeGroup.getCheckedRadioButtonId();
        RadioButton radioButton = accountTypeGroup.findViewById(checkedId);
        String accountType = radioButton.getText().toString();

        Log.i(TAG, "Regisztrált: " + userNameEditText.getText().toString()
                + ", jelszava: " + userPasswordEditText.getText().toString()
                + ", telefonja: " + phoneEditText.getText().toString()
                + ", típusa: " + phoneSpinner.getSelectedItem().toString()
                + ", címe:\n" + addressEditText.getText().toString()
                + ", fiók: " + accountType);

        //startShopping();

        mAuth.createUserWithEmailAndPassword(userEmailEditText.getText().toString(), userPasswordEditText.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Log.i(TAG, "User created successfully");
                            startShopping();
                        } else {
                            Log.d(TAG, "User creation failed");
                            Toast.makeText(RegisterActivity.this, "User creation failed" + task.getException()
                                    .getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void cancel(View view) {
        finish();
    }

    private void startShopping(){
        Intent intent = new Intent(this, ShopListActivity.class);
        intent.putExtra("SECRET_KEY", SECRET_KEY);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, TAG + "onStart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, TAG + "onDestroy");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, TAG + "onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, TAG + "onResume");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, TAG + "onStop");
    }

    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, TAG + "onRestart");
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
        // melyik item lett kiválasztva a spinnerben?
        //String selectedItem = parent.getItemAtPosition(position).toString();
        //Log.i(TAG, TAG + " " + selectedItem);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        // TODO
    }
}