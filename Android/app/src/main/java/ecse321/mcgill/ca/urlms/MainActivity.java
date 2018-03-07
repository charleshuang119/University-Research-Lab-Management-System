package ecse321.mcgill.ca.urlms;

import android.content.Intent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.List;

import ca.mcgill.ecse321.urlms.application.URLMSApplication;
import ca.mcgill.ecse321.urlms.controller.InvalidInputException;
import ca.mcgill.ecse321.urlms.controller.ManagerController;
import ca.mcgill.ecse321.urlms.model.Lab;
import ca.mcgill.ecse321.urlms.model.Staff;
import ca.mcgill.ecse321.urlms.persistence.PersistenceXStream;


public class MainActivity extends AppCompatActivity {

    static ManagerController controller;
    static List<Staff> staffs;
    private AddStaffArrayAdapter adapter;
    ListView listView;
    private boolean checkBoxVisible = false;
    static Lab currentLab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView=(ListView)findViewById(R.id.staffList);


        //Setting the file path to the internal storage of the device
        PersistenceXStream.setFilename(getApplicationContext().getFilesDir().getAbsolutePath() + "/data.xml");

        URLMSApplication urlmsApplication = new URLMSApplication();

        //initializing persistence (creating the file)
        PersistenceXStream.initializeModelManager(PersistenceXStream.getFilename());

        //if there are no labs created check if they were saved by persistence
        controller = new ManagerController();
        if (!URLMSApplication.getURLMS().hasSingleLab()) {
            URLMSApplication.load();
            try {
                controller.createLab();
                URLMSApplication.save();
            } catch (InvalidInputException e1) {
            }
        }

        try{
            controller.loadLab();
        }catch (InvalidInputException e){
            Log.i("TEST", "temp fix");
        }
        currentLab = URLMSApplication.getURLMS().getSingleLab();
        staffs = currentLab.getStaff();

        updateUI();
    }

    /**
     *  This method deals with the display of the activity
     */
    private void updateUI() {

        ImageView staffsMenu = (ImageView) findViewById(R.id.staffMenu);
        ImageView inventoryMenu = (ImageView) findViewById(R.id.inventoryMenu);
        ImageView progressMenu = (ImageView) findViewById(R.id.progressMenu);
        ImageView fundingMenu = (ImageView) findViewById(R.id.fundsMenu);


        staffsMenu.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                callStaffActivity();
            }
        });

        inventoryMenu.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                callInventoryActivity();
            }
        });

        progressMenu.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                callProgressActivity();
            }
        });

        fundingMenu.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                callFundingAcitivity();
            }
        });


    }

    private void callFundingAcitivity() {
        Intent fundingIntent = new Intent(this, FundingActivity.class);
        startActivity(fundingIntent);
    }

    private void callProgressActivity() {
        Intent progressIntent = new Intent(this, ProgressActivity.class);
        startActivity(progressIntent);
    }

    private void callInventoryActivity() {
        Intent inventoryIntent = new Intent(this, InventoryActivity.class);
        startActivity(inventoryIntent);
    }

    private void callStaffActivity() {
        Intent i = new Intent(this, StaffActivity.class);
        startActivity(i);
    }


    @Override
    public void onResume(){
        super.onResume();
        updateUI();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        //getMenuInflater().inflate(R.menu.add_staff, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.action_settings){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
