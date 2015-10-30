package edu.osu.settyblue.xreport;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by rakshith on 10/27/2015.
 */
public class ExpenseAdapter extends BaseAdapter{
    TextView expense;
    Context mContext;
    List<String> expenseList;

    public ExpenseAdapter(Context c, List<String> list) {
        this.mContext = c;
        this.expenseList = list;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rootView;
       // if(convertView == null){
            rootView = inflater.inflate(R.layout.expense_list_item, parent, false);
            expense = (TextView)rootView.findViewById(R.id.expenseID);
            expense.setText(expenseList.get(position));
       /* }else{
            rootView = convertView;
        }*/

        return rootView;
    }



}
