package ecse321.mcgill.ca.urlms;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import ca.mcgill.ecse321.urlms.application.URLMSApplication;
import ca.mcgill.ecse321.urlms.controller.InvalidInputException;

/**
 * Created by Andi-Camille Bakti on 19/11/2017.
 */

public class AddInventoryActivity extends AppCompatActivity {

    private RadioGroup radioGroup;
    private EditText editResourceName;
    private EditText editQuantity;
    private TextInputLayout inputLayoutName;
    private TextInputLayout inputLayoutQuantity;
    private Button addResourceButton;
    private String resourceName;
    private int quantity;
    private boolean isSupply;

    @Override
    public void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.add_inventory_activity);
        getSupportActionBar().setTitle(R.string.add_inventory_title);
        updateUI();

    }

    private void updateUI() {
        radioGroup = (RadioGroup) findViewById(R.id.radio_group_resourceType);

        editResourceName = (EditText) findViewById(R.id.editResource);
        editQuantity = (EditText) findViewById(R.id.editQuantity_inventory);

        inputLayoutName = (TextInputLayout) findViewById(R.id.layoutResource);
        inputLayoutQuantity = (TextInputLayout) findViewById(R.id.layoutQuantity);

        addResourceButton = (Button) findViewById(R.id.buttonAddInventory);

        addResourceButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                addInventory();
            }
        });
    }

    private void addInventory() {

        resourceName = editResourceName.getText().toString();
        //check if input is valid

        if(!validResourceName()){
            Log.i("TEST", "invalid resource name");
            return;
        }

        try{
            quantity = Integer.parseInt(editQuantity.getText().toString());
            if(!validQuantity()){
                return;
            }
        }catch(NumberFormatException e){
            inputLayoutQuantity.setError(getString(R.string.err_invalid_quantity));
            return;
        }


        if(!validType()){
            Log.i("TEST", "invalid type");
            return;
        }

        try{
            if(isSupply){
                MainActivity.controller.addSupply(quantity, resourceName);
            }else{
                MainActivity.controller.addEquipement(quantity, resourceName);
            }

            Toast.makeText(getApplicationContext(), "Added " + resourceName,Toast.LENGTH_LONG).show();


            URLMSApplication.save();

            //Toast.makeText(this, "Resource Added", Toast.LENGTH_SHORT).show();

            //clear UI
            editResourceName.setText("");
            editQuantity.setText("");
            radioGroup.clearCheck();
        }catch (InvalidInputException e){
            Log.e("ERR", e.getMessage());
        }
    }

    private boolean validQuantity() {
        if(quantity == 0){
            return false;
        }
        return true;
    }

    private boolean validType() {
        if(radioGroup.getCheckedRadioButtonId() == -1){
            Toast.makeText(this, "Type not selected", Toast.LENGTH_LONG).show();
            return false;
        }else{
            setType();
        }

        return true;
    }

    private void setType() {
        switch (radioGroup.getCheckedRadioButtonId()){
            case R.id.radio_equipment:
                isSupply = false;
                break;
            case R.id.radio_supply:
                isSupply = true;
                break;
        }
    }

    /**
     * This method determines if the input resource name is valid
     * @return true if the input is valid
     */
    private boolean validResourceName() {
        if(resourceName.isEmpty() || resourceName.matches(".*\\d+.*") ){
            inputLayoutName.setError(getString(R.string.err_invalid_name));
            return false;
        }
        return true;
    }
}
