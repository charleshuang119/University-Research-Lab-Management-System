package ecse321.mcgill.ca.urlms;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

import ca.mcgill.ecse321.urlms.model.Expense;

/**
 * Created by Andi-Camille Bakti on 23/11/2017.
 */

public class ExpensesArrayAdapter extends ArrayAdapter {

    private final List<Expense> expenses;

    public ExpensesArrayAdapter(List<Expense> expenses, Context context){
        super(context, 0, expenses);
        this.expenses = expenses;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){



        return convertView;
    }

}
