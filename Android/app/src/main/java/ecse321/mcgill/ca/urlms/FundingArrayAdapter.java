package ecse321.mcgill.ca.urlms;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import ca.mcgill.ecse321.urlms.model.Expense;

/**
 * Created by Andi-Camille Bakti on 26/11/2017.
 */

public class FundingArrayAdapter extends ArrayAdapter<Expense> {
    private final List<Expense> expenses;
    private Expense selectedExpense;

    public FundingArrayAdapter(List<Expense> expenses, Context context){
        super(context, 0, expenses);
        this.expenses = expenses;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){


        selectedExpense = getItem(position);
        //Find the View to be converted if null
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_expense, parent, false);
        }

        TextView editDescription = (TextView) convertView.findViewById(R.id.expenseDescription);
        TextView editAmount = (TextView) convertView.findViewById(R.id.expenseAmount);
        TextView date = (TextView) convertView.findViewById(R.id.expenseDate);


        editDescription.setText(selectedExpense.getDescription());
        editAmount.setText(Integer.toString(selectedExpense.getAmount()));
        date.setText(selectedExpense.getDate().toString());


        return convertView;
    }
}

