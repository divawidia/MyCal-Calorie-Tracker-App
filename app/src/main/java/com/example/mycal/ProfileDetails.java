package com.example.mycal;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ProfileDetails extends AppCompatActivity {


    private String[] arraySpinnerDOBDay = new String[31];
    private String[] arraySpinnerDOBYear = new String[100];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_details);

        /* menyembunyikan kolom icnhes */
        EditText editTextHeightInches = (EditText)findViewById(R.id.editTextTinggiInches);
        editTextHeightInches.setVisibility(View.GONE);

        Spinner spinnerMesurment = (Spinner)findViewById(R.id.spinnerPengukuran);
        spinnerMesurment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                ubahPengukuran();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // mesurmentChanged();
            }
        });

        /* Listener buttonSignUp */
        Button buttonSignUp = (Button)findViewById(R.id.buttonSetProfil);
        buttonSignUp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                profileSubmit();
            }
        });
    }
    /*- mengubah pengukuran ------------------------------------------ */
    public void ubahPengukuran() {

        // Mesurment spinner
        Spinner spinnerPengukuran = (Spinner)findViewById(R.id.spinnerPengukuran);
        String stringPengukuran = spinnerPengukuran.getSelectedItem().toString();


        EditText editTextTinggitCm = (EditText)findViewById(R.id.editTextTinggitCm);
        EditText editTextTinggiInches = (EditText)findViewById(R.id.editTextTinggiInches);
        String stringHeightCm = editTextTinggitCm.getText().toString();
        String stringHeightInches = editTextTinggiInches.getText().toString();

        double tinggiCm = 0;
        double tinggiFeet = 0;
        double tinggiInches = 0;

        TextView textViewCm = (TextView)findViewById(R.id.textViewCm);
        TextView textViewKg = (TextView)findViewById(R.id.textViewKg);

        if(stringPengukuran.startsWith("I")){
            // Imperial
            editTextTinggiInches.setVisibility(View.VISIBLE);
            textViewCm.setText("feet and inches");
            textViewKg.setText("pound");
            try {
                tinggiCm = Double.parseDouble(stringHeightCm);
            }
            catch(NumberFormatException nfe) {
            }
            if(tinggiCm != 0){
                // mengubah CM ke feet
                // feet = cm * 0.3937008)/12
                tinggiFeet = (tinggiCm * 0.3937008)/12;
                int intHeightFeet = (int) tinggiFeet;

                editTextTinggitCm.setText("" + intHeightFeet);
            }
        }
        else{
            // Metric
            editTextTinggiInches.setVisibility(View.GONE);
            textViewCm.setText("cm");
            textViewKg.setText("kg");
            // mengubah feet dan inch ke cm
            try {
                tinggiFeet = Double.parseDouble(stringHeightCm);
            }
            catch(NumberFormatException nfe) {

            }
            // konvert inch
            try {
                tinggiInches = Double.parseDouble(stringHeightInches);
            }
            catch(NumberFormatException nfe) {

            }
            // cm = ((foot * 12) + inches) * 2.54
            if(tinggiFeet != 0 && tinggiInches != 0) {
                tinggiCm = ((tinggiFeet * 12) + tinggiInches) * 2.54;
                tinggiCm = Math.round(tinggiCm);
                editTextTinggitCm.setText("" + tinggiCm);
            }
        }
        // berat badan
        EditText editTextBerat = (EditText)findViewById(R.id.editTextBerat);
        String stringBerat = editTextBerat.getText().toString();
        double doubleWeight = 0;
        try {
            doubleWeight = Double.parseDouble(stringBerat);
        }
        catch(NumberFormatException nfe) {
        }
        if(doubleWeight != 0) {
            if (stringPengukuran.startsWith("I")) {
                // mengubah kg ke pounds
                doubleWeight = Math.round(doubleWeight / 0.45359237);
            } else {
                // mengubah pounds ke kg
                doubleWeight = Math.round(doubleWeight * 0.45359237);
            }
            editTextBerat.setText("" + doubleWeight);
        }
    }
    /*- Sign up Submit ---------------------------------------------- */
    public void profileSubmit() {
         // umur
        EditText editTextUmur = (EditText)findViewById(R.id.editTextUmur);
        String stringUmur = editTextUmur.getText().toString();
        double doubleUmur = 0;
        try {
            doubleUmur = Double.parseDouble(stringUmur);
        }
        catch(NumberFormatException nfe) {
            Toast.makeText(ProfileDetails.this,"Umur harus berupa nomor.", Toast.LENGTH_LONG).show();
        }

        // jenis kelamin
        RadioGroup radioGroupJenisKelamin = (RadioGroup)findViewById(R.id.radioGroupJenisKelamin);
        int radioButtonID = radioGroupJenisKelamin.getCheckedRadioButtonId();
        View radioButtonJenisKelamin = radioGroupJenisKelamin.findViewById(radioButtonID);
        int position = radioGroupJenisKelamin.indexOfChild(radioButtonJenisKelamin);

        String stringJenisKelamin = "";
        if(position == 0){
            stringJenisKelamin = "male";
        }
        else{
            stringJenisKelamin = "female";
        }

        // tinggi
        EditText editTextTinggitCm = (EditText)findViewById(R.id.editTextTinggitCm);
        EditText editTextTinggiInches = (EditText)findViewById(R.id.editTextTinggiInches);
        String stringTinggiCm = editTextTinggitCm.getText().toString();
        String stringTinggiInches = editTextTinggiInches.getText().toString();

        double tinggiCm = 0;
        double tinggiFeet = 0;
        double tinggiInches = 0;
        boolean metric = true;

        // apakah Metric atau imperial?
        Spinner spinnerPengukuran = (Spinner)findViewById(R.id.spinnerPengukuran);
        String stringPengukuran = spinnerPengukuran.getSelectedItem().toString();

        int intPengukuran = spinnerPengukuran.getSelectedItemPosition();
        if(intPengukuran == 0){
            stringPengukuran = "metric";
        }
        else{
            stringPengukuran = "imperial";
            metric = false;
        }

        if(metric == true) {
            // ubah ke CM
            try {
                tinggiCm = Double.parseDouble(stringTinggiCm);
                tinggiCm = Math.round(tinggiCm);
            }
            catch(NumberFormatException nfe) {
                Toast.makeText(ProfileDetails.this,"Tinggi (cm) harus berupa nomor.", Toast.LENGTH_LONG).show();
            }
        }
        else {
            // ubah ke Feet
            try {
                tinggiFeet = Double.parseDouble(stringTinggiCm);
            }
            catch(NumberFormatException nfe) {
                Toast.makeText(ProfileDetails.this,"Tinggi (feet) harus berupa nomor.", Toast.LENGTH_LONG).show();
            }

            // konvert inches
            try {
                tinggiFeet = Double.parseDouble(stringTinggiInches);
            }
            catch(NumberFormatException nfe) {
                Toast.makeText(ProfileDetails.this,"Tinggi (inches) harus berupa nomor.", Toast.LENGTH_LONG).show();
            }
            // cm = ((foot * 12) + inch) * 2.54
            tinggiCm = ((tinggiFeet * 12) + tinggiInches) * 2.54;
            tinggiCm = Math.round(tinggiCm);
        }

        // Berat
        EditText editTextBerat = (EditText)findViewById(R.id.editTextBerat);
        String stringBerat = editTextBerat.getText().toString();
        double doubleBerat = 0;
        try {
            doubleBerat = Double.parseDouble(stringBerat);
        }
        catch(NumberFormatException nfe) {
            Toast.makeText(ProfileDetails.this,"berat harus berupa nomor.", Toast.LENGTH_LONG).show();
        }
        if(metric == true) {
        }
        else{
            // Imperial
            // Pound ke kg
            doubleBerat = Math.round(doubleBerat*0.45359237);
        }

        // tingkat aktivitas
        Spinner spinnerTingkatAktivitas = (Spinner)findViewById(R.id.spinnerTingkatAktivitas);
        //  0: sedikit/tidak pernah olaharaga
        // 1: olahraga ringan (1–3 hari/minggu)
        // 2: olahraga sedang (3–5 hari/minggu)
        // 3: olahraga berat (6–7 hari/minggu)
        // 4: olahraga sangat sedang (2x sehari)
        int intTingkatAktivitas = spinnerTingkatAktivitas.getSelectedItemPosition();

        // memasukan data ke database
        DBAdapter db = new DBAdapter(this);
        db.open();

        double tinggiCmSQL = db.quoteSmart(tinggiCm);
        int intTingkatAktivitasSQL = db.quoteSmart(intTingkatAktivitas);
        double doubleBeratSQL = db.quoteSmart(doubleBerat);
        String stringPengukuranSQL = db.quoteSmart(stringPengukuran);
        double doubleUmurSQL = db.quoteSmart(doubleUmur);
        String stringJenisKelaminSQL = db.quoteSmart(stringJenisKelamin);
        long userID = 1;

        // Input untuk tabel users
        db.update("users", "_id", userID, "user_mesurment", stringPengukuranSQL);
        db.update("users", "_id", userID, "user_height", tinggiCmSQL);
        db.update("users", "_id", userID, "user_age", doubleUmurSQL);
        db.update("users", "_id", userID, "user_gender", stringJenisKelaminSQL);

        // Input untuk tabel goal
        SimpleDateFormat tf = new SimpleDateFormat("yyyy-MM-dd");
        String goalDate = tf.format(Calendar.getInstance().getTime());

        String goalDateSQL = db.quoteSmart(goalDate);

        String stringInput = "NULL, " + doubleBeratSQL + "," + goalDateSQL + "," + intTingkatAktivitasSQL;
        db.insert("goal",
                "_id, goal_current_weight, goal_date, goal_activity_level",
                stringInput);
        db.close();

        Intent i = new Intent(ProfileDetails.this, SignUpGoal.class);
        startActivity(i);
    }
}