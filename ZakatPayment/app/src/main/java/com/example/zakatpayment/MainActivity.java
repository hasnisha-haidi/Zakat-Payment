package com.example.zakatpayment;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    EditText etWeight, etValue;
    Button btnKeep, btnWear;
    TextView tvTotalValue, tvZakatPayable, tvTotalZakat, hyperlink;
    int keep, wear;
    Double uruf, totalValue, zakatPayable, totalZakat;

    SharedPreferences sharedPref;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        //for action bar
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        //Objects.requireNonNull(getSupportActionBar()).setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_layout);

        //variable input
        etWeight = findViewById(R.id.weight);
        etValue = findViewById(R.id.value);

        btnKeep = findViewById(R.id.btnKeep);
        btnWear = findViewById(R.id.btnWear);

        tvTotalValue = findViewById(R.id.tvTotalValue);
        tvZakatPayable = findViewById(R.id.tvZakatPayable);
        tvTotalZakat = findViewById(R.id.tvTotalZakat);

        //to match to onClick
        btnKeep.setOnClickListener(this);
        btnWear.setOnClickListener(this);

        //for type keep
        //shared pref to save in local storage to reuse when reopen
        sharedPref = this.getSharedPreferences("keep" , Context.MODE_PRIVATE);
        //load the data
        keep = sharedPref.getInt("keep", 0);

        //for type wear
        //shared pref to save in local storage to reuse when reopen
        sharedPref = this.getSharedPreferences("wear" , Context.MODE_PRIVATE);
        //load the data
        wear = sharedPref.getInt("wear", 0);

        //clickable url
        setContentView(R.layout.activity_about);

        hyperlink = findViewById(R.id.hyperlink);
        hyperlink.setMovementMethod(LinkMovementMethod.getInstance());
    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        if (item.getItemId() == R.id.about) {
            //Toast.makeText(this, "This is about page with details and clickable URL", Toast.LENGTH_SHORT).show();

            //to display about page
            Intent intent = new Intent(this, AboutActivity.class);
            startActivity(intent);

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.btnKeep:
                int keep = 85;

                try {
                    String variable_weight = etWeight.getText().toString();
                    String variable_value = etValue.getText().toString();

                    double weight = Double.parseDouble(variable_weight);
                    double value = Double.parseDouble(variable_value);

                    totalValue = weight * value;
                    uruf = weight - keep;
                    zakatPayable = uruf * value;
                    totalZakat = zakatPayable * 0.025;

                        if (uruf <= 0.0) {
                            zakatPayable = 0.0;
                            totalZakat = 0.0;
                        }

                    //to set the output
                    tvTotalValue.setText(totalValue + " is total value");
                    tvZakatPayable.setText(zakatPayable + " is zakat payable");
                    tvTotalZakat.setText(totalZakat + "is total zakat");
                }

                catch (NumberFormatException e) {
                    e.printStackTrace();

                    //use to display at the bottom  of yr mobile device
                    Toast.makeText(this, "Please enter the valid number", Toast.LENGTH_LONG).show();
                }

                //saving the data for type keep
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putInt("keep", keep);
                editor.apply();

                break;

            case R.id.btnWear:
                int wear = 200;

                try {
                    String variable_weight = etWeight.getText().toString();
                    String variable_value = etValue.getText().toString();

                    double weight = Double.parseDouble(variable_weight);
                    double value = Double.parseDouble(variable_value);

                    totalValue = weight * value;
                    uruf = weight - wear;
                    zakatPayable = uruf * value;
                    totalZakat = zakatPayable * 0.025;

                        if (uruf <= 0.0) {
                            zakatPayable = 0.0;
                            totalZakat = 0.0;
                        }

                    //to set the output
                    tvTotalValue.setText(zakatPayable + " is zakat payable");
                    tvZakatPayable.setText(zakatPayable + " is zakat payable");
                    tvTotalZakat.setText(totalZakat + " is total zakat");
                }

                catch (NumberFormatException e) {
                    e.printStackTrace();

                    //use to display at the bottom  of yr mobile device
                    Toast.makeText(this, "Please enter the valid number", Toast.LENGTH_LONG).show();
                }

                //saving the data for type wear
                editor = sharedPref.edit();
                editor.putInt("wear", wear);
                editor.apply();

                break;
        }
    }

}