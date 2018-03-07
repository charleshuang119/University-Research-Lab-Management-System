package ecse321.mcgill.ca.urlms;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import ca.mcgill.ecse321.urlms.model.FundingAccount;

/**
 * Created by Andi-Camille Bakti on 23/11/2017.
 */

public class FundingActivity extends AppCompatActivity{

    private FundingAccount account;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceBundle){
        super.onCreate(savedInstanceBundle);
        setContentView(R.layout.activity_funding);
        getSupportActionBar().setTitle("Funding Account");


        listView = (ListView) findViewById(R.id.inventory_listView);

        this.account = MainActivity.currentLab.getFundingAccount();

        int allocated = MainActivity.currentLab.getFundingAccount().getAllocatedAmount();

        Log.i("DEBUG", "allocated = " + Integer.toString(allocated));
        updateUI();
    }



    @Override
    public void onResume(){
        super.onResume();
        updateUI();
    }



    private void updateUI() {



        FundingArrayAdapter adapter = new FundingArrayAdapter(account.getExpenses(), this);
        listView.setAdapter(adapter);

        TextView allocatedAmount = (TextView)findViewById(R.id.textAllocatedAmount);
        int allocated = account.getAllocatedAmount();
        if(Integer.toString(allocated).isEmpty()){
            //Log.i("DEBUG", "allocated is 0");
            allocatedAmount.setText("$ 0");
        }else{
            String out = "$ " + Integer.toString(allocated);
            if(out == null || out.isEmpty()){
                Log.i("DEBUG", "out is null or empty");
            } else if(allocatedAmount == null) {
                Log.i("DEBUG", "allocated amount null or empty");
            }else{
                Log.i("DEBUG", "out is NOTTT null or empty");

            }
            allocatedAmount.setText(out);
        }


        //MainActivity.currentLab.getFundingAccount()


        TextView remainingAmount = (TextView) findViewById(R.id.textRemainingAmount);
        int remaining = account.getRemainingAmount();
        remainingAmount.setText("$ " + Integer.toString(remaining));


        if(remaining < 0 ){
            remainingAmount.setTextColor(Color.RED);
        }else{
            remainingAmount.setTextColor(Color.GREEN);
        }

        RelativeLayout fundingView = (RelativeLayout) findViewById(R.id.text_funding_account);
        fundingView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //TODO: invoke prompt
                callFundingAccount();
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_add_expense);
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                callAddExpenseActivity();
            }
        });

    }

    private void callFundingAccount() {
        Intent fundingAccountSettingsIntent = new Intent(this, FundingSettings.class);
        startActivity(fundingAccountSettingsIntent);
    }

    private void callAddExpenseActivity() {
        Intent expenseIntent = new Intent(this, AddExpenseActivity.class);
        startActivity(expenseIntent);
    }
}
