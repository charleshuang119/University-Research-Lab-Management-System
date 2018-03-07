package ecse321.mcgill.ca.urlms;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import ca.mcgill.ecse321.urlms.application.URLMSApplication;
import ca.mcgill.ecse321.urlms.controller.InvalidInputException;
import ca.mcgill.ecse321.urlms.controller.ManagerController;
import ca.mcgill.ecse321.urlms.model.Staff;

/**
 * Created by Andi-Camille Bakti on 15/11/2017.
 */
public class AddProgressActivity extends AppCompatActivity{

    private Button saveReportButton;
    private EditText progressEditText;
    private Spinner dropdownStaffs;
    private String description;

    @Override
    public void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.add_progress_activity);
        getSupportActionBar().setTitle("New Report");
        updateUI();

    }

    private void updateUI() {
        progressEditText = (EditText) findViewById(R.id.editProgress);
        saveReportButton = (Button) findViewById(R.id.buttonAddProgress);

        int size = MainActivity.staffs.size();
        String[] staffList = new String[size];

        for(int i = 0; i<size; i++){
            String temp = MainActivity.staffs.get(i).getFirstName();
            temp = temp + " " + MainActivity.staffs.get(i).getLastName();
            //temp = temp + " (" + MainActivity.staffs.get(i).getRole().toString()+ ")"; //not really relevant
            staffList[i] = temp;
        }

        dropdownStaffs = (Spinner) findViewById(R.id.spinner_progress_selectStaff);
        ArrayAdapter<String> dropdownAdpater = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, staffList);
        dropdownStaffs.setAdapter(dropdownAdpater);

        //set action associated to button
        saveReportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addProgressUpdate();
            }
        });
    }

    private void addProgressUpdate() {
        ManagerController controller = MainActivity.controller;

        //get the input from the views
        description = progressEditText.getText().toString();

        int selection = dropdownStaffs.getSelectedItemPosition();
        Staff selectedStaff = MainActivity.staffs.get(selection);
        //check if input is valid

        if(!validText()){
            progressEditText.setError("Please fill in the text box.");
        }

        //if input is valid
        try{
            controller.createProgressUpdate(description, selectedStaff);
            URLMSApplication.save();
            Log.e("TEST", "here");


            //Toast Feedback
            Toast toast = Toast.makeText(getApplicationContext(), "Report saved", Toast.LENGTH_SHORT);
            toast.show();

            //return to ProgressActivity
            finish();

        }catch (InvalidInputException e){
            Log.e("ERR", e.getMessage());

        }
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

    @Override
    public void onBackPressed() {

        if(description != null ){
            new AlertDialog.Builder(this)
                    .setTitle("Discard draft?")
                    .setNegativeButton(android.R.string.cancel, null)
                    .setPositiveButton(R.string.discard,new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            finish();
                        }
                    }).create().show();
        }else{
            finish();
        }

    }
}
