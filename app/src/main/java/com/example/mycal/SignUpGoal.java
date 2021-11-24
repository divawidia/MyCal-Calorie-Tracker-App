package com.example.mycal;

import android.content.Intent;
import android.database.Cursor;
import android.icu.util.Calendar;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class SignUpGoal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.submit_goals);


        /* Listener submit */
        Button buttonSubmit = (Button)findViewById(R.id.buttonGoalSubmit1);
        buttonSubmit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                signUpGoalSubmit();
            }
        });

        /* Mesurment used? */
        mesurmentUsed();

    } // onCreate

    /* signUpGoalSubmit ----------------------------------------------------- */
    public void signUpGoalSubmit(){
        /* Open database */
        DBAdapter db = new DBAdapter(this);
        db.open();

        /* Get target weight */
        EditText editTextTargetWeight = (EditText)findViewById(R.id.targetBerat);
        String stringTargetWeight = editTextTargetWeight.getText().toString();
        double doubleTargetWeight = 0;
        try{
            doubleTargetWeight = Double.parseDouble(stringTargetWeight);
        }
        catch(NumberFormatException nfe) {
            Toast.makeText(SignUpGoal.this,"Target weight has to be a number.", Toast.LENGTH_LONG).show();
        }

        /* Spinner IWantTo */
        // 0 - Loose weight
        // 1 - Gain weight
        Spinner spinnerIWantTo = (Spinner)findViewById(R.id.spinnerTarget);
        int intIWantTo = spinnerIWantTo.getSelectedItemPosition();

        /* Spinner spinnerWeeklyGoal */
        Spinner spinnerWeeklyGoal = (Spinner)findViewById(R.id.spinnerTargetPerMinggu);
        String stringWeeklyGoal = spinnerWeeklyGoal.getSelectedItem().toString();


        /* Update fields */
        long goalID = 1;

        double doubleTargetWeightSQL = db.quoteSmart(doubleTargetWeight);
        db.update("goal", "_id", goalID, "goal_target_weight", doubleTargetWeightSQL);

        int intIWantToSQL = db.quoteSmart(intIWantTo);
        db.update("goal", "_id", goalID, "goal_i_want_to", intIWantToSQL);

        String stringWeeklyGoalSQL = db.quoteSmart(stringWeeklyGoal);
        db.update("goal", "_id", goalID, "goal_weekly_goal", stringWeeklyGoalSQL);

        /* Calculate energy */

        // Get row number one from users
        long rowID = 1;
        String fields[] = new String[] {
                "_id",
                "user_age",
                "user_gender",
                "user_height"
        };
        Cursor c = db.select("users", fields, "_id", rowID);
//            String stringUserDob = c.getString(1);
        String stringUserUmur = c.getString(1);
        String stringUserGender  = c.getString(2);
        String stringUserHeight = c.getString(3);

        // Get weight actvity level
        rowID = 1;
        String fieldsGoal[] = new String[] {
                "_id",
                "goal_current_weight",
                "goal_activity_level"
        };
        Cursor cGoal = db.select("goal", fieldsGoal, "_id", rowID);
        String stringUserCurrentWeight = cGoal.getString(1);
        String stringUserActivityLevel = cGoal.getString(2);

        // Get weight
        double doubleUserCurrentWeight = 0;
        try{
            doubleUserCurrentWeight = Double.parseDouble(stringUserCurrentWeight);
        }
        catch(NumberFormatException nfe) {
            System.out.println("Could not parse " + nfe);
        }

        int intUserAge = 0;
        try {
            intUserAge = Integer.parseInt(stringUserUmur);
        }
        catch(NumberFormatException nfe) {
            System.out.println("Could not parse " + nfe);
        }

        // Height
        double doubleUserHeight = 0;
        try {
            doubleUserHeight = Double.parseDouble(stringUserHeight);
        }
        catch(NumberFormatException nfe) {
            System.out.println("Could not parse " + nfe);
        }

        /* 1: BRM */
        // memulai perhitungan
        double goalEnergyBMR = 0;
        if(stringUserGender.startsWith("m")){
            // cowok
            // BMR = 66.5 + (13.75 x kg berat) + (5.003 x tinggi) - (6.755 x umur)
            goalEnergyBMR = 66.5+(13.75*doubleUserCurrentWeight)+(5.003*doubleUserHeight)-(6.755*intUserAge);

        }
        else{
            // cewek
            // BMR = 55.1 + (9.563 x kg berat) + (1.850 x tinggi) - (4.676 x umur)
            goalEnergyBMR = 655+(9.563*doubleUserCurrentWeight)+(1.850*doubleUserHeight)-(4.676*intUserAge);
        }
        goalEnergyBMR = Math.round(goalEnergyBMR);
        double energyBmrSQL = db.quoteSmart(goalEnergyBMR);
        db.update("goal", "_id", goalID, "goal_energy_bmr", energyBmrSQL);

        // Proteins, karbo dan lemak dengan BMR
        // 20-25 % protein
        // 40-50 % carbs
        // 25-35 % fat
        double proteinsBmr = Math.round(goalEnergyBMR*25/100);
        double carbsBmr = Math.round(goalEnergyBMR*50/100);
        double fatBmr = Math.round(goalEnergyBMR*25/100);

        double proteinsBmrSQL = db.quoteSmart(proteinsBmr);
        double carbsBmrSQL = db.quoteSmart(carbsBmr);
        double fatBmrQL = db.quoteSmart(fatBmr);
        db.update("goal", "_id", goalID, "goal_proteins_bmr", proteinsBmrSQL);
        db.update("goal", "_id", goalID, "goal_carbs_bmr", carbsBmrSQL);
        db.update("goal", "_id", goalID, "goal_fat_bmr", fatBmrQL);

        /* 2: Diet */
        // jika ingin megurangi berat badan
        // without activity (Little to no exercise)
        // mengurangi atau menambah berat badan?
        double doubleWeeklyGoal = 0;
        try {
            doubleWeeklyGoal = Double.parseDouble(stringWeeklyGoal);
        }
        catch(NumberFormatException nfe) {
            System.out.println("Could not parse " + nfe);
        }

        // 1 kg fat = 7700 kcal
        double kcal = 0;
        double energyDiet = 0;
        kcal = 7700*doubleWeeklyGoal;
        if(intIWantTo == 0){
            // Loose weight
            energyDiet = Math.round((goalEnergyBMR - (kcal/7)) * 1.2);

        }
        else{
            // Gain weight
            energyDiet = Math.round((goalEnergyBMR + (kcal/7)) * 1.2);
        }

        // Update database
        double energyDietSQL = db.quoteSmart(energyDiet);
        db.update("goal", "_id", goalID, "goal_energy_diet", energyDietSQL);

        // Proteins, karbo dan lemak dengan BMR
        // 20-25 % protein
        // 40-50 % carbs
        // 25-35 % fat
        double proteinsDiet = Math.round(energyDiet*25/100);
        double carbsDiet = Math.round(energyDiet*50/100);
        double fatDiet = Math.round(energyDiet*25/100);

        double proteinsDietSQL = db.quoteSmart(proteinsDiet);
        double carbsDietSQL = db.quoteSmart(carbsDiet);
        double fatDietQL = db.quoteSmart(fatDiet);
        db.update("goal", "_id", goalID, "goal_proteins_diet", proteinsDietSQL);
        db.update("goal", "_id", goalID, "goal_carbs_diet", carbsDietSQL);
        db.update("goal", "_id", goalID, "goal_fat_diet", fatDietQL);


        /* menghitung kalori sesuai dengan tingkat aktivitas */
        double energyWithActivity = 0;
        if(stringUserActivityLevel.equals("0")) {
            energyWithActivity = goalEnergyBMR * 1.2;
        }
        else if(stringUserActivityLevel.equals("1")) {
            energyWithActivity = goalEnergyBMR * 1.375;
        }
        else if(stringUserActivityLevel.equals("2")) {
            energyWithActivity = goalEnergyBMR*1.55;
        }
        else if(stringUserActivityLevel.equals("3")) {
            energyWithActivity = goalEnergyBMR*1.725;
        }
        else if(stringUserActivityLevel.equals("4")) {
            energyWithActivity = goalEnergyBMR * 1.9;
        }
        energyWithActivity = Math.round(energyWithActivity);
        double energyWithActivitySQL = db.quoteSmart(energyWithActivity);
        db.update("goal", "_id", goalID, "goal_energy_with_activity", energyWithActivitySQL);

        // Proteins, karbo dan lemak dengan BMR
        // 20-25 % protein
        // 40-50 % carbs
        // 25-35 % fat
        double proteinsWithActivity = Math.round(energyWithActivity*25/100);
        double carbsWithActivity = Math.round(energyWithActivity*50/100);
        double fatWithActivity = Math.round(energyWithActivity*25/100);

        double proteinsWithActivitySQL = db.quoteSmart(proteinsWithActivity);
        double carbsWithActivitySQL = db.quoteSmart(carbsWithActivity);
        double fatWithActivityQL = db.quoteSmart(fatWithActivity);
        db.update("goal", "_id", goalID, "goal_proteins_with_activity", proteinsWithActivitySQL);
        db.update("goal", "_id", goalID, "goal_carbs_with_activity", carbsWithActivitySQL);
        db.update("goal", "_id", goalID, "goal_fat_with_activity", fatWithActivityQL);



        /* 4: dengan aktivitas dan diet */
        // jika ingin mengurangi berat badan
        // dengan aktivitas
        // 1 kg fat = 7700 kcal
        kcal = 0;
        double energyWithActivityAndDiet = 0;
        kcal = 7700*doubleWeeklyGoal;
        if(intIWantTo == 0){
            // Loose weight
            energyWithActivityAndDiet = goalEnergyBMR - (kcal/7);

        }
        else{
            // Gain weight
            energyWithActivityAndDiet = goalEnergyBMR + (kcal/7);
        }

        if(stringUserActivityLevel.equals("0")) {
            energyWithActivityAndDiet= energyWithActivityAndDiet* 1.2;
        }
        else if(stringUserActivityLevel.equals("1")) {
            energyWithActivityAndDiet= energyWithActivityAndDiet* 1.375;
        }
        else if(stringUserActivityLevel.equals("2")) {
            energyWithActivityAndDiet= energyWithActivityAndDiet*1.55;
        }
        else if(stringUserActivityLevel.equals("3")) {
            energyWithActivityAndDiet= energyWithActivityAndDiet*1.725;
        }
        else if(stringUserActivityLevel.equals("4")) {
            energyWithActivityAndDiet = energyWithActivityAndDiet* 1.9;
        }
        energyWithActivityAndDiet = Math.round(energyWithActivityAndDiet);

        // Update database
        double energyWithActivityAndDietSQL = db.quoteSmart(energyWithActivityAndDiet);
        db.update("goal", "_id", goalID, "goal_energy_with_activity_and_diet", energyWithActivityAndDietSQL);


        // menghitung protein
        // 20-25 % protein
        // 40-50 % carbs
        // 25-35 % fat
        double proteins = Math.round(energyWithActivityAndDiet*25/100);
        double carbs = Math.round(energyWithActivityAndDiet*50/100);
        double fat = Math.round(energyWithActivityAndDiet*25/100);

        double proteinsSQL = db.quoteSmart(proteins);
        double carbsSQL = db.quoteSmart(carbs);
        double fatSQL = db.quoteSmart(fat);
        db.update("goal", "_id", goalID, "goal_proteins_with_activity_and_diet", proteinsSQL);
        db.update("goal", "_id", goalID, "goal_carbs_with_activity_and_diet", carbsSQL);
        db.update("goal", "_id", goalID, "goal_fat_with_activity_and_diet", fatSQL);

        /* Close db */
        db.close();

        // pindah ke main activity
        Intent i = new Intent(SignUpGoal.this, MainActivity.class);
        startActivity(i);
    }

    /* mesurmentUsed ------------------------------------------------------- */
    public void mesurmentUsed(){
        /* buka database */
        DBAdapter db = new DBAdapter(this);
        db.open();

        /* mengambil user id 1 dari tabel user */
        long rowID = 1;
        String fields[] = new String[] {
                "_id",
                "user_mesurment"
        };
        Cursor c = db.select("users", fields, "_id", rowID);
        String mesurment;
        mesurment = c.getString(1);

        //
        if(mesurment.startsWith("m")){
            // Metric
        }
        else{
            // Imperial

            // konvert Kg ke punds
            TextView textViewTargetMesurmentType = (TextView)findViewById(R.id.textViewKg);
            textViewTargetMesurmentType.setText("pounds");

            TextView textViewKgEachWeek = (TextView)findViewById(R.id.textViewKgPerMinggu);
            textViewKgEachWeek.setText("pounds each week");
        }
        /* Close database */
        db.close();
    }
}