package com.example.mycal;


import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Calendar;

public class ProfileFragment extends Fragment {
    // variabel class
    private View mainView;

    // Action buttons pada toolbar
    private MenuItem menuItemEdit;
    private MenuItem menuItemDelete;

    /*- variabel Fragment ----------------------------------------------------------- */
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    /*- 03 Constructur ------------------------------------------------------------------ */
    public ProfileFragment() {

    }

    /*- 04 membuat Fragment ------------------------------------------------------------- */
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    /*- 05 on Activity Created ---------------------------------------------------------- */
    // menjalankan method saat dimulai
    // Set toolbar menu items
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        /* set label pada action bar */
        ((FragmentActivity)getActivity()).getSupportActionBar().setTitle("Profil");

        // mengambil data dari db
        initalizeGetDataFromDbAndDisplay();

    }


    /*- 06 On create view ---------------------------------------------------------------- */
    // membuat variabel main view ke view segingga bisa mengubah view pada fragment
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.fragment_profile, container, false);
        return mainView;
    }


    /*- 07 set main view ----------------------------------------------------------------- */
    // Changing view method in fragmetn
    private void setMainView(int id){
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mainView = inflater.inflate(id, null);
        ViewGroup rootView = (ViewGroup) getView();
        rootView.removeAllViews();
        rootView.addView(mainView);
    }

    /*- 08 on Create Options Menu -------------------------------------------------------- */
    // Creating action icon on toolbar
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        // Inflate menu
        MenuInflater menuInflater = ((FragmentActivity)getActivity()).getMenuInflater();
        inflater.inflate(R.menu.menu_goal, menu);

        // Assign menu items to variables
        menuItemEdit = menu.findItem(R.id.menu_action_food_edit);
    }

    /*- 09 on Options Item Selected ------------------------------------------------------ */
    // Action icon clicked on
    // Menu
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {

        int id = menuItem.getItemId();
        //if (id == R.id.menu_action_goal_edit) {

        //}
        return super.onOptionsItemSelected(menuItem);
    }
    /*- Our own methods -*/

    /*- Get data from db and display --------------------------------------------- */
    public void initalizeGetDataFromDbAndDisplay(){

        /*  Get data from database */
        // Database
        DBAdapter db = new DBAdapter(getActivity());
        db.open();

        /* Get row number one from users */
        long rowID = 1;
        String fields[] = new String[] {
                "_id",
                "user_alias",
                "user_age",
                "user_gender",
                "user_height",
                "user_mesurment"
        };
        Cursor c = db.select("users", fields, "_id", rowID);
        String stringUsername = c.getString(1);
        String stringUserAge = c.getString(2);
        String stringUserGender  = c.getString(3);
        String stringUserHeight = c.getString(4);
        String stringUserMesurment = c.getString(5);

        /* Umur */
        EditText editTextEditProfileUmur = (EditText)getActivity().findViewById(R.id.editTextEditProfileUmur);
        editTextEditProfileUmur.setText(stringUserAge);

        /* Gender */
        RadioButton radioButtonGenderMale = (RadioButton)getActivity().findViewById(R.id.radioButtonGenderMale);
        RadioButton radioButtonGenderFemale = (RadioButton)getActivity().findViewById(R.id.radioButtonGenderFemale);
        if(stringUserGender.startsWith("m")){
            radioButtonGenderMale.setChecked(true);
            radioButtonGenderFemale.setChecked(false);
        }
        else{
            radioButtonGenderMale.setChecked(false);
            radioButtonGenderFemale.setChecked(true);
        }

        /* Height */
        EditText editTextEditProfileHeightCm = (EditText)getActivity().findViewById(R.id.editTextEditProfileHeightCm);
        EditText editTextEditProfileHeightInches = (EditText)getActivity().findViewById(R.id.editTextEditProfileHeightInches);
        TextView textViewEditProfileCm = (TextView)getActivity().findViewById(R.id.textViewEditProfileCm);
        if(stringUserMesurment.startsWith("m")) {
            editTextEditProfileHeightInches.setVisibility(View.GONE);
            editTextEditProfileHeightCm.setText(stringUserHeight);
        } else{
            textViewEditProfileCm.setText("feet and inches");
            double heightCm = 0;
            double heightFeet = 0;
            double heightInches = 0;

            // Find feet
            try {
                heightCm = Double.parseDouble(stringUserHeight);
            }
            catch(NumberFormatException nfe) {
            }
            if(heightCm != 0){
                // Convert CM into feet
                // feet = cm * 0.3937008)/12
                heightFeet = (heightCm * 0.3937008)/12;
                // heightFeet = Math.round(heightFeet);
                int intHeightFeet = (int) heightFeet;

                editTextEditProfileHeightCm.setText("" + intHeightFeet);
            }
        }
        /* Mesurment */
        Spinner spinnerEditProfileMesurment = (Spinner)getActivity().findViewById(R.id.spinnerEditProfileMesurment);
        if(stringUserMesurment.startsWith("m")) {
            spinnerEditProfileMesurment.setSelection(0); // Select index

        }
        else{
            spinnerEditProfileMesurment.setSelection(1); // Select index
        }
        /* Listener Mesurment spinner */
        spinnerEditProfileMesurment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                mesurmentChanged();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // mesurmentChanged();
            }
        });

        /* Listener buttonSignUp */
        Button buttonEditProfileSubmit = (Button)getActivity().findViewById(R.id.buttonEditProfileSubmit);
        buttonEditProfileSubmit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                editProfileSubmit();
            }
        });
        // Close db
        db.close();

    }


    /*- Mesurment changed ----------------------------------------------------------------- */
    public void mesurmentChanged() {

        // Mesurment spinner
        Spinner spinnerMesurment = (Spinner)getActivity().findViewById(R.id.spinnerEditProfileMesurment);
        String stringMesurment = spinnerMesurment.getSelectedItem().toString();


        EditText editTextEditProfileHeightCm = (EditText)getActivity().findViewById(R.id.editTextEditProfileHeightCm);
        EditText editTextEditProfileHeightInches = (EditText)getActivity().findViewById(R.id.editTextEditProfileHeightInches);

        TextView textViewEditProfileCm = (TextView)getActivity().findViewById(R.id.textViewEditProfileCm);



        if(stringMesurment.startsWith("M")) {
            // Metric
            editTextEditProfileHeightInches.setVisibility(View.GONE);
            textViewEditProfileCm.setText("cm");
        }
        else{
            // Imperial
            editTextEditProfileHeightInches.setVisibility(View.VISIBLE);
            textViewEditProfileCm.setText("feet and inches");

        }


    } // public voild messuredChanged


    /*- edit profile submit --------------------------------------------------------------- */
    private void editProfileSubmit(){
        /*  Get data from database */
        // Database
        DBAdapter db = new DBAdapter(getActivity());
        db.open();

        /* Error? */
        int error = 0;

        /* Umur */
        EditText editTextEditProfileUmur = (EditText)getActivity().findViewById(R.id.editTextEditProfileUmur);
        String stringUmur = editTextEditProfileUmur.getText().toString();
        double umur = 0;
        try {
            umur = Double.parseDouble(stringUmur);
        }
        catch(NumberFormatException nfe) {
            error = 1;
            Toast.makeText(getActivity(), "Umur harus berupa nomor.", Toast.LENGTH_SHORT).show();
        }
        stringUmur = "" + umur;
        String umurSQL = db.quoteSmart(stringUmur);

        // Gender
        RadioGroup radioGroupGender = (RadioGroup)getActivity().findViewById(R.id.radioGroupGender);
        int radioButtonID = radioGroupGender.getCheckedRadioButtonId(); // get selected radio button from radioGroup
        View radioButtonGender = radioGroupGender.findViewById(radioButtonID);
        int position = radioGroupGender.indexOfChild(radioButtonGender); // If you want position of Radiobutton

        String stringGender = "";
        if(position == 0){
            stringGender = "male";
        }
        else{
            stringGender = "female";
        }
        String genderSQL = db.quoteSmart(stringGender);

        /* Height */
        EditText editTextHeightCm = (EditText)getActivity().findViewById(R.id.editTextEditProfileHeightCm);
        EditText editTextHeightInches = (EditText)getActivity().findViewById(R.id.editTextEditProfileHeightInches);
        String stringHeightCm = editTextHeightCm.getText().toString();
        String stringHeightInches = editTextHeightInches.getText().toString();

        double heightCm = 0;
        double heightFeet = 0;
        double heightInches = 0;
        boolean metric = true;

        // Metric or imperial?
        Spinner spinnerMesurment = (Spinner)getActivity().findViewById(R.id.spinnerEditProfileMesurment);
        String stringMesurment = spinnerMesurment.getSelectedItem().toString();

        int intMesurment = spinnerMesurment.getSelectedItemPosition();
        if(intMesurment == 0){
            stringMesurment = "metric";
        }
        else{
            stringMesurment = "imperial";
            metric = false;
        }
        String mesurmentSQL = db.quoteSmart(stringMesurment);

        if(metric == true) {
            // Convert CM
            try {
                heightCm = Double.parseDouble(stringHeightCm);
                heightCm = Math.round(heightCm);
            }
            catch(NumberFormatException nfe) {
                error = 1;
                Toast.makeText(getActivity(), "Height (cm) has to be a number.", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            // Convert Feet
            try {
                heightFeet = Double.parseDouble(stringHeightCm);
            }
            catch(NumberFormatException nfe) {
                error = 1;
                Toast.makeText(getActivity(), "Height (feet) has to be a number.", Toast.LENGTH_SHORT).show();
            }

            // Convert inches
            try {
                heightInches = Double.parseDouble(stringHeightInches);
            }
            catch(NumberFormatException nfe) {
                error = 1;
                Toast.makeText(getActivity(), "Height (inches) has to be a number.", Toast.LENGTH_SHORT).show();
            }

            // Need to convert, we want to save the number in cm
            // cm = ((foot * 12) + inches) * 2.54
            heightCm = ((heightFeet * 12) + heightInches) * 2.54;
            heightCm = Math.round(heightCm);
        }
        stringHeightCm = "" + heightCm;
        String heightCmSQL = db.quoteSmart(stringHeightCm);

        if(error == 0){

            long id = 1;

            String fields[] = new String[] {
                    "user_age",
                    "user_gender",
                    "user_height",
                    "user_mesurment"
            };
            String values[] = new String[] {
                    umurSQL,
                    genderSQL,
                    heightCmSQL,
                    mesurmentSQL
            };

            db.update("users", "_id", id, fields, values);

            Toast.makeText(getActivity(), "Changes saved", Toast.LENGTH_SHORT).show();
        } // error == 0
        // tutup db
        db.close();
    }
    /*- On create ----------------------------------------------------------------- */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}