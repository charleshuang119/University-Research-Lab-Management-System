package ecse321.mcgill.ca.urlms;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import ca.mcgill.ecse321.urlms.model.ProgressUpdate;
import ca.mcgill.ecse321.urlms.model.Staff;

/**
 * Created by Andi-Camille Bakti on 16/11/2017.
 */

public class ProgressArrayAdapter extends ArrayAdapter<ProgressUpdate> {

    private List<ProgressUpdate> reports;
    private ProgressUpdate selectedReport;

    public ProgressArrayAdapter(List<ProgressUpdate> reports, Context context){
        super(context, 0, reports);
        this.reports = reports;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent){


        selectedReport = getItem(position);

        //Find the View to be converted if null
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_progress, parent, false);
        }




        //Find views by ID

        TextView dateView = (TextView) convertView.findViewById(R.id.progressDate);
        dateView.setText(selectedReport.getDate().toString());

        TextView nameView = (TextView) convertView.findViewById(R.id.progressAuthor);

        String search = findReportAuthor();

        if(search == ""){
            nameView.setText("");
        }else{
            nameView.setText(search);
        }


        return convertView;
    }

    /**
     * This method return a pre-formatted string with the name of the author of the corresponding report
     * @return name of author of the report, "" if no author was found.
     */
    private String findReportAuthor() {
        List<ProgressUpdate> progressUpdates;
        String output = "";
        for(int i = 0; i<MainActivity.staffs.size(); i++){
            Staff staff = MainActivity.staffs.get(i);
            progressUpdates = staff.getProgressUpdates();
            for(int j = 0; j<progressUpdates.size(); j++){
                if(selectedReport == progressUpdates.get(j)){
                    output = staff.getFirstName() + " " + staff.getLastName();

                    return output;
                }
            }
        }
        return output;
    }


}
