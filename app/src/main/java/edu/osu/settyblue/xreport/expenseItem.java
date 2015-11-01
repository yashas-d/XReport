package edu.osu.settyblue.xreport;

/**
 * Created by rakshith on 10/30/2015.
 */
public class ExpenseItem {
    private int expenseItemId;
    private int expenseId;
    private String itemName;

    public int getExpenseItemId() {
        return expenseItemId;
    }

    public void setExpenseItemId(int expenseItemId) {
        this.expenseItemId = expenseItemId;
    }

    public int getExpenseId() {
        return expenseId;
    }

    public void setExpenseId(int expenseId) {
        this.expenseId = expenseId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String toString(){
        return expenseItemId+" " + itemName + " ";
    }
}
