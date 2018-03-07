package ecse321.mcgill.ca.urlms;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import ca.mcgill.ecse321.urlms.application.URLMSApplication;
import ca.mcgill.ecse321.urlms.controller.InvalidInputException;
import ca.mcgill.ecse321.urlms.controller.ManagerController;
import ca.mcgill.ecse321.urlms.model.Staff;

/**
 * Created by Andi-Camille Bakti on 11/11/2017.
 */

public class StaffActivity extends AppCompatActivity {

    ManagerController controller;
    List<Staff> staffs;
    private AddStaffArrayAdapter adapter;
    ListView listView;
    private boolean checkBoxVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff);
        getSupportActionBar().setTitle("Staffs");

        listView=(ListView)findViewById(R.id.staffList);

        controller = MainActivity.controller;
        staffs = MainActivity.staffs;

        updateUI();
    }

    /**
     *  This method deals with the display of the activity
     */
    private void updateUI() {

        //initialize and set the adapter
        adapter = new AddStaffArrayAdapter(staffs, this);
        listView.setAdapter(adapter);

        //creating the floating action button and what to do when clicked
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_add_staff);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callAddStaffActivity();
            }
        });
    }

    /**
     * This method creates a new activity to add a new staff
     */
    private void callAddStaffActivity() {
        Intent addStaffIntent = new Intent(this, AddStaffActivity.class);
        startActivity(addStaffIntent);
    }

    @Override
    public void onResume(){
        super.onResume();
        updateUI();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.add_staff, menu);
        return true;
    }

    /**
     * This method manages what happens when a options item is pressed (top bar)
     * In this case it is the delete staff button
     * @param item or the button that is pressed
     * @return true if a button was pressed
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.action_settings){
            return true;
        }

        if(id == R.id.removeStaff){
            //set check boxes to visible or delete the selected staff
            if(!checkBoxVisible){
                setCheckBoxToVisible();
            }else{
                removeSelectedStaff();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * This method loops through all of the items in the list and removes the ones that were selected
     */
    private void removeSelectedStaff() {
        int staffDeleted = 0;
        int count;

        count = listView.getChildCount();
        for(int i = 0; i<count; i++){
            View v = listView.getChildAt(i);
            CheckBox removeStaffBox = (CheckBox) v.findViewById(R.id.box_remove_staff);
            if(removeStaffBox.isChecked()) {
                Staff selectedStaff = adapter.getItem(i);

                //remove from the system
                try {
                    controller.removeStaff(selectedStaff);
                    Log.i("DEBUG", "selected staff: " + selectedStaff.getFirstName());
                } catch (InvalidInputException e) {
                    Snackbar.make(findViewById(android.R.id.content), e.getMessage(), Snackbar.LENGTH_LONG).show();
                    Log.e("ERR", e.getMessage());
                }

                //increment number of staff deleted
                staffDeleted++;
            }

            //set the check boxes to be invisible again
            removeStaffBox.setVisibility(View.VISIBLE);
            checkBoxVisible = false;
        }

        Log.i("DEBUG", "staffs list" + URLMSApplication.getURLMS().getSingleLab().getStaff().toString());

        //UI feedback
        if(staffDeleted != 0){
            Toast toast = Toast.makeText(getApplicationContext(), "Removed " + staffDeleted + " staff(s).", Toast.LENGTH_SHORT );
            toast.show();
        }


        //save the change to persistence
        URLMSApplication.save();

        updateUI();
    }

    /**
     * This method sets all of the checkboxes to be visible.
     * @return
     */
    public boolean setCheckBoxToVisible(){
        boolean wasSet = false;
        int count = listView.getChildCount();
        for(int i = 0; i<count; i++){
            View v = listView.getChildAt(i);

            CheckBox removeStaffBox = (CheckBox) v.findViewById(R.id.box_remove_staff);
            removeStaffBox.setVisibility(View.VISIBLE);

            //state of the visibility of the check boxes
            checkBoxVisible = true;
        }
        wasSet = true;
        return wasSet;
    }

}
