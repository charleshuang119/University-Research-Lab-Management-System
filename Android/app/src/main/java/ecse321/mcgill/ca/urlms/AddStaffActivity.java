package ecse321.mcgill.ca.urlms;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import ca.mcgill.ecse321.urlms.application.URLMSApplication;
import ca.mcgill.ecse321.urlms.controller.InvalidInputException;
import ca.mcgill.ecse321.urlms.controller.ManagerController;
import ca.mcgill.ecse321.urlms.persistence.PersistenceXStream;

/**
 * Created by Andi-Camille Bakti on 13/10/2017.
 *
 * This activity shows the fields to create a new staff.
 */

public class AddStaffActivity extends AppCompatActivity {


    private TextView errorView;
    private RadioGroup radioGroup;
    private EditText editFirstName, editLastName;
    private TextInputLayout inputLayoutFirstName, inputLayoutLastName;
    private Button addStaffButton;
    private String role, firstName, lastName;

    @Override
    public void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.add_staff_activity);
        getSupportActionBar().setTitle("New Staff");
        updateUI();

    }

    /**
     * This method generates the UI of this activity
     */
    public void updateUI(){

        //Initialize the Views
        errorView = (TextView) findViewById(R.id.errorMessage);

        radioGroup = (RadioGroup) findViewById(R.id.radio_group_role);

        editFirstName = (EditText) findViewById(R.id.editFirstName);
        editLastName = (EditText) findViewById(R.id.editLastName);

        inputLayoutFirstName = (TextInputLayout) findViewById(R.id.input_layout_first_name);
        inputLayoutLastName = (TextInputLayout) findViewById(R.id.input_layout_last_name);

        addStaffButton = (Button) findViewById(R.id.buttonAddStaff);


        //Set the event listener
        //
        addStaffButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                addStaff();
            }
        });

    }

    /**
     * This method checks if the inputs are valid. If so, it calls the controller to create a new staff.
     */
    private void addStaff() {

        ManagerController controller = MainActivity.controller;
        role = "";
        errorView.setVisibility(View.INVISIBLE);

        firstName = editFirstName.getText().toString();
        Log.i("TEST", "first name:" + firstName);
        lastName = editLastName.getText().toString();
        Log.i("TEST", "last name:" + lastName);



        if(!validFirstName()){
            Log.i("TEST", "invalid first name");
            return;
        }

        if(!validLastName()){
            Log.i("TEST", "invalid last name");
            return;
        }

        if(!validRole()){
            Log.i("TEST", "invalid role");
            return;
        }

        Log.i("TEST", "input is valid");

        //The input is valid, create a new staff
        try {
            controller.addStaff(firstName, lastName, role);

            URLMSApplication.save();

            //UI feedback
            Toast toast = Toast.makeText(getApplicationContext(), "Added \"" +  firstName + "\"", Toast.LENGTH_SHORT);
            toast.show();

            //Clear UI
            errorView.setVisibility(View.INVISIBLE);
            editFirstName.setText("");
            editLastName.setText("");
            radioGroup.clearCheck();
        } catch (InvalidInputException e) {

            //If the lab already has a director
            errorView.setVisibility(View.VISIBLE);
            errorView.setText(e.getMessage());
            Log.e("ERR", e.getMessage());
        }
    }

    /**
     * This method checks if the first name input is valid.
     * @return true if the input is a valid first name.
     */
    private boolean validFirstName() {
        if(firstName.isEmpty()){
            inputLayoutFirstName.setError(getString(R.string.err_first_name));
            errorView.setVisibility(View.VISIBLE);
            return false;
        }else if(firstName.matches(".*\\d+.*")){
            inputLayoutFirstName.setError(getString(R.string.err_invalid_name));
            errorView.setVisibility(View.VISIBLE);
            return false;
        }else{
            inputLayoutFirstName.setErrorEnabled(false);
        }
        return true;
    }

    /**
     * This method checks if the last name input is valid.
     * @return true if the input is a valid last name.
     */
    private boolean validLastName() {
        if(lastName.isEmpty()){
            inputLayoutLastName.setError(getString(R.string.err_last_name));
            errorView.setVisibility(View.VISIBLE);
            return false;
        }else if(lastName.matches(".*\\d+.*")){
            inputLayoutLastName.setError(getString(R.string.err_invalid_name));
            errorView.setVisibility(View.VISIBLE);
            return false;
        }else{
            inputLayoutLastName.setErrorEnabled(false);
        }
        return true;
    }

    /**
     * This method checks if the role was checked. If it is then set the role to the selected role.
     * @return true if a role was checked.
     */
    private boolean validRole() {

        if(radioGroup.getCheckedRadioButtonId() == -1){
            errorView.setVisibility(View.VISIBLE);
            errorView.setText("Role is required");
            return false;
        }else{
            setRole();
        }

        return true;
    }


    /**
     * This method sets the role to the checked role.
     */
    private void setRole() {
        switch (radioGroup.getCheckedRadioButtonId()){
            case R.id.radio_directors:
                role = "Director";
                break;
            case R.id.radio_assistant:
                role = "Assistant";
                break;
            case R.id.radio_associate:
                role = "Associate";
                break;
        }
    }
}
