package ecse321.mcgill.ca.urlms;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import ca.mcgill.ecse321.urlms.application.URLMSApplication;
import ca.mcgill.ecse321.urlms.controller.InvalidInputException;
import ca.mcgill.ecse321.urlms.model.Staff;

/**
 * Created by Andi-Camille Bakti on 23/11/2017.
 */

public class AddExpenseActivity extends AppCompatActivity{
    private Button addExpenseButton;
    private Spinner dropdownStaffs;
    private EditText editDescription;
    private EditText editAmount;
    private RadioGroup radioGroupType;
    private String description;
    private String selectedType;
    private int quantity;
    private TextInputLayout inputLayoutQuantity;
    private TextInputLayout inputLayoutDescription;

    @Override
    public void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.add_expense_activity);
        getSupportActionBar().setTitle("New Expense");
        updateUI();

    }

    /**
     * This method generates the UI of this activity
     */
    public void updateUI(){

        editDescription = (EditText) findViewById(R.id.editDescription);
        editAmount = (EditText) findViewById(R.id.edit_amount_expense);

        radioGroupType = (RadioGroup) findViewById(R.id.radio_group_expense_type);

        inputLayoutQuantity = (TextInputLayout) findViewById(R.id.layoutQuantity_expense);
        inputLayoutDescription = (TextInputLayout) findViewById(R.id.input_layout_description_expense);


        int size = MainActivity.staffs.size();
        String[] staffList = new String[size];

        for(int i = 0; i<size; i++){
            String temp = MainActivity.staffs.get(i).getFirstName();
            temp = temp + " " + MainActivity.staffs.get(i).getLastName();
            //temp = temp + " (" + MainActivity.staffs.get(i).getRole().toString()+ ")"; //not really relevant
            staffList[i] = temp;
        }

        dropdownStaffs = (Spinner) findViewById(R.id.spinner_expense_selectStaff);
        ArrayAdapter<String> dropdownAdpater = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, staffList);
        dropdownStaffs.setAdapter(dropdownAdpater);

        addExpenseButton = (Button) findViewById(R.id.buttonAddExpense);

        addExpenseButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                addExpense();
            }
        });

    }

    private void addExpense() {
        int selection = dropdownStaffs.getSelectedItemPosition();
        Staff selectedStaff = MainActivity.staffs.get(selection);

        description = editDescription.getText().toString();
        selectedType = "";
        //check if input is valid

        if(!validText()){
            inputLayoutDescription.setError("Please fill in the text box.");
            return;
        }

        if(!validType()){
            Toast.makeText(getApplicationContext(), "Type is required", Toast.LENGTH_LONG);
            return;
        }

        try{
            quantity = Integer.parseInt(editAmount.getText().toString());
            if(!validQuantity()){
                return;
            }
        }catch(NumberFormatException e){
            inputLayoutQuantity.setError(getString(R.string.err_invalid_quantity));
            return;
        }

        //assume the input is valid

        try{
            if(MainActivity.currentLab == null){
                Log.i("ERR", "lab is null");

            }else{
                Log.i("ERR", "lab is not null");
            }
            MainActivity.controller.addExpense(selectedStaff, description, quantity, selectedType);
            Toast.makeText(getApplicationContext(), "Added Expense",Toast.LENGTH_LONG).show();
            URLMSApplication.save();

            radioGroupType.clearCheck();
            editDescription.setText("");
            editAmount.setText("");

        }catch (InvalidInputException e){
            Toast.makeText(getApplicationContext(), e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    private boolean validQuantity() {
        if(quantity == 0){
            return false;
        }
        return true;
    }

    /**
     * This method checks if the text box is empty or not
     * @return false if it is empty
     */
    private boolean validText() {
        if(description.isEmpty()){
            return false;
        }
        return true;
    }

    /**
     * This method checks if the role was checked. If it is then set the role to the selected role.
     * @return true if a role was checked.
     */
    private boolean validType() {

        if(radioGroupType.getCheckedRadioButtonId() == -1){
            return false;
        }else{
            setType();
        }

        return true;
    }

    /**
     * This method sets the type of the expense
     */
    private void setType() {
        switch (radioGroupType.getCheckedRadioButtonId()){
            case R.id.radio_travel:
                selectedType = "Travel";
                break;
            case R.id.radio_salary:
                selectedType = "Salary";
                break;
            case R.id.radio_resource:
                selectedType = "Resource";
                break;
            case R.id.radio_other:
                selectedType = "Other";
        }

    }
}
