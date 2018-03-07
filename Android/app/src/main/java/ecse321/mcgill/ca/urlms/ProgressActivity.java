package ecse321.mcgill.ca.urlms;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import java.util.List;
import ca.mcgill.ecse321.urlms.controller.ManagerController;
import ca.mcgill.ecse321.urlms.model.ProgressUpdate;

/**
 * Created by Andi-Camille Bakti on 14/11/2017.
 */

public class ProgressActivity extends AppCompatActivity {
    public static final String EXTRA_DESCRIPTION = "ca.mcgill.ecse321.urlms.DESCRIPTION";

    private ListView listView;
    private ManagerController controller;
    private boolean checkBoxVisible = false;
    private ProgressArrayAdapter progressAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceBundle){
        super.onCreate(savedInstanceBundle);
        setContentView(R.layout.activity_progress);
        getSupportActionBar().setTitle("Reports");

        listView=(ListView)findViewById(R.id.progressList);

        controller = MainActivity.controller;
        updateUI();



    }

    private void callAddProgressActivity() {
        Intent addProgressIntent = new Intent(this, AddProgressActivity.class);
        startActivity(addProgressIntent);

    }

    @Override
    public void onResume(){
        super.onResume();
        updateUI();
    }

    private void updateUI() {
        //TODO: Set the adapter

        List<ProgressUpdate> progressUpdates = MainActivity.currentLab.getProgressUpdates();

        ProgressArrayAdapter arrayAdapter = new ProgressArrayAdapter(progressUpdates, this);

        listView.setAdapter(arrayAdapter);
        //creating the floating action button and what to do when clicked
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_add_report);
        fab.setOnClickListener(view -> callAddProgressActivity());

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView adapterView, View v, int position, long l){
                ProgressUpdate  progressUpdate = arrayAdapter.getItem(position);
                viewReport(progressUpdate);
            }
        });
    }

    private void viewReport(ProgressUpdate progressUpdate) {
        Intent viewReport = new Intent(this, ViewProgressActivity.class);
        String description = progressUpdate.getDescription();
        viewReport.putExtra(EXTRA_DESCRIPTION,description);
        startActivity(viewReport);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        //TODO: Search or filter settings
        return true;
    }

    /**
     * This method manages what happens when a options item is pressed (top bar)
     * In this case it is the delete staff button
     * @param item or the button that is pressed
     * @return true if a button was pressed
     */
    /*
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
    /*
    private void removeSelectedStaff() {
        int staffDeleted = 0;
        int count;

        count = listView.getChildCount();
        for(int i = 0; i<count; i++){
            View v = listView.getChildAt(i);
            CheckBox removeStaffBox = (CheckBox) v.findViewById(R.id.removeStaffBox);
            if(removeStaffBox.isChecked()) {
                Staff selectedStaff = progressAdapter.getItem(i);

                //remove from the system
                try {
                    controller.removeStaff(selectedStaff);
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
        Toast toast = Toast.makeText(getApplicationContext(), "Removed " + staffDeleted + " staff(s).", Toast.LENGTH_SHORT );
        toast.show();

        //save the change to persistence
        URLMSApplication.save();

        updateUI();
    }

    /**
     * This method sets all of the checkboxes to be visible.
     * @return
     */
    /*
    public boolean setCheckBoxToVisible(){
        boolean wasSet = false;
        int count = listView.getChildCount();
        for(int i = 0; i<count; i++){
            View v = listView.getChildAt(i);

            CheckBox removeStaffBox = (CheckBox) v.findViewById(R.id.removeStaffBox);
            removeStaffBox.setVisibility(View.VISIBLE);

            //state of the visibility of the check boxes
            checkBoxVisible = true;
        }
        wasSet = true;
        return wasSet;
    }*/
}
