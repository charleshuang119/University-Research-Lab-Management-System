package ecse321.mcgill.ca.urlms;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import ca.mcgill.ecse321.urlms.model.Staff;

/**
 * Created by Andi-Camille Bakti on 09/11/2017.
 */

public class AddStaffArrayAdapter extends ArrayAdapter<Staff> {

    private List<Staff> staffs;

    public AddStaffArrayAdapter(List<Staff> staffs, Context context){
        super(context, 0, staffs);
        this.staffs = staffs;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        Staff selectedStaff = getItem(position);

        //Find the View to be converted if null
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_staff, parent, false);
        }

        //Find views by ID
        TextView name = (TextView) convertView.findViewById(R.id.staffName);

        String firstName = selectedStaff.getFirstName();
        String lastName = selectedStaff.getLastName();

        name.setText(firstName + " " + lastName);

    return convertView;
    }

}
