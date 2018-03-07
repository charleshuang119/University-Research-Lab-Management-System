package ecse321.mcgill.ca.urlms;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import ca.mcgill.ecse321.urlms.application.URLMSApplication;
import ca.mcgill.ecse321.urlms.controller.InvalidInputException;
import ca.mcgill.ecse321.urlms.model.ProgressUpdate;
import ca.mcgill.ecse321.urlms.model.Resource;
import ca.mcgill.ecse321.urlms.model.Staff;

/**
 * Created by Andi-Camille Bakti on 11/11/2017.
 */

public class InventoryActivity extends AppCompatActivity {

    private List<Resource> resources;
    private InventoryArrayAdapter adapter;
    private boolean checkBoxVisible = false;
    private ListView resourceList;
    private String m_Text = "";

    @Override
    protected void onCreate(Bundle savedInstanceBundle){
        super.onCreate(savedInstanceBundle);
        setContentView(R.layout.activity_inventory);
        getSupportActionBar().setTitle("Inventory");

        this.resources = MainActivity.currentLab.getInventory().getResources();
        updateUI();
    }



    @Override
    public void onResume(){
        super.onResume();
        updateUI();
    }

    private void updateUI() {

        resourceList = (ListView) findViewById(R.id.inventory_listView);

        this.resources = MainActivity.currentLab.getInventory().getResources();
        adapter = new InventoryArrayAdapter(resources, this);
        resourceList.setAdapter(adapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_add_inventory);
        fab.setOnClickListener(view -> callAddInventoryActivity());

        resourceList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView adapterView, View v, int position, long l){
                Resource selectedResource = adapter.getItem(position);
                Log.i("TEST", selectedResource.getName());
                setQuantity(selectedResource);

                URLMSApplication.save();
                updateUI();
            }
        });
    }

    private void setQuantity(Resource selectedResource) {
        final EditText text = new EditText(this);
        text.setInputType(InputType.TYPE_CLASS_NUMBER);

        new AlertDialog.Builder(this)
                .setTitle("Edit quantity")
                .setView(text)
                .setPositiveButton("SAVE", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        if(text.getText() != null){
                            String quantity = text.getText().toString();
                            Log.i("TEST", quantity);
                            if(quantity!= null){
                                selectedResource.setQuantity(Integer.parseInt(quantity));
                            }
                        }
                    }
                })
                .setNegativeButton("CANCEL" +
                        "", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                })
                .show();
    }

    private void callAddInventoryActivity() {
        Intent inventoryIntent = new Intent(this, AddInventoryActivity.class);
        startActivity(inventoryIntent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.remove_inventory, menu);
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

        if(id == R.id.removeResource){
            //set check boxes to visible or delete the selected staff
            if(!checkBoxVisible){
                setCheckBoxToVisible();
            }else{
                removeSelectedResource();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * This method loops through all of the items in the list and removes the ones that were selected
     */
    private void removeSelectedResource() {
        int resourcesDeleted = 0;
        int count;

        count = this.resourceList.getChildCount();
        for(int i = 0; i<count; i++){
            View v = this.resourceList.getChildAt(i);
            CheckBox removeResourceBox = (CheckBox) v.findViewById(R.id.box_remove_resource);
            if(removeResourceBox.isChecked()) {
                Resource selectedResource = adapter.getItem(i);

                //remove from the system
                try {
                    MainActivity.controller.removeResource(selectedResource);
                    Log.i("DEBUG", "selected staff: " + selectedResource.getName());
                } catch (InvalidInputException e) {
                    Snackbar.make(findViewById(android.R.id.content), e.getMessage(), Snackbar.LENGTH_LONG).show();
                    Log.e("ERR", e.getMessage());
                }

                //increment number of staff deleted
                resourcesDeleted++;
            }

            //set the check boxes to be invisible again
            removeResourceBox.setVisibility(View.VISIBLE);
            checkBoxVisible = false;
        }


        //UI feedback
        if(resourcesDeleted != 0){
            Toast toast = Toast.makeText(getApplicationContext(), "Removed " + resourcesDeleted + " resources.", Toast.LENGTH_SHORT );
            toast.show();
        }

        //Debug logger
        Log.i("DEBUG", "staffs list" + URLMSApplication.getURLMS().getSingleLab().getStaff().toString());


        //save the change to persistence
        URLMSApplication.save();

        //update the list
        updateUI();
    }

    /**
     * This method sets all of the checkboxes to be visible.
     * @return
     */
    public boolean setCheckBoxToVisible(){
        boolean wasSet = false;
        int count = this.resourceList.getChildCount();
        for(int i = 0; i<count; i++){
            View v = this.resourceList.getChildAt(i);

            CheckBox removeResourceBox = (CheckBox) v.findViewById(R.id.box_remove_resource);
            removeResourceBox.setVisibility(View.VISIBLE);

            //state of the visibility of the check boxes
            checkBoxVisible = true;
        }
        wasSet = true;
        return wasSet;
    }

}
