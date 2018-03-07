package ecse321.mcgill.ca.urlms;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import ca.mcgill.ecse321.urlms.controller.InvalidInputException;
import ca.mcgill.ecse321.urlms.model.Resource;

/**
 * Created by Andi-Camille Bakti on 21/11/2017.
 */

public class InventoryArrayAdapter extends ArrayAdapter<Resource> {

    private final List<Resource> resources;
    private Resource selectedResource;

    public InventoryArrayAdapter(List<Resource> resources, Context context){
        super(context, 0, resources);
        this.resources = resources;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        selectedResource = getItem(position);

        //Find the View to be converted if null
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_resource, parent, false);
        }

        TextView nameView = (TextView) convertView.findViewById(R.id.resourceName);
        nameView.setText(selectedResource.getName());

        TextView quantityView = (TextView) convertView.findViewById(R.id.resourceQuantity);
        quantityView.setText(Integer.toString(selectedResource.getQuantity()));

        return convertView;
    }
}

