package ecse321.mcgill.ca.urlms;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ButtonBarLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Andi-Camille Bakti on 27/11/2017.
 */

public class FundingSettings extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceBundle){
        super.onCreate(savedInstanceBundle);
        setContentView(R.layout.settings_funding);
        getSupportActionBar().setTitle(R.string.funding_account_title);

        updateUI();
    }

    private void updateUI() {


        EditText allocatedEdit = (EditText) findViewById(R.id.edit_allocated);
        int allocated = MainActivity.currentLab.getFundingAccount().getAllocatedAmount();
        allocatedEdit.setText(Integer.toString(allocated));

        Button saveButton = (Button) findViewById(R.id.save_settings_funding);

        saveButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                int amount = Integer.parseInt(allocatedEdit.getText().toString());
                MainActivity.currentLab.getFundingAccount().setAllocatedAmount(amount);
                finish();
            }
        });

    }
}
