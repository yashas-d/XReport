package edu.osu.settyblue.xreport;

/**
 * Created by rakshith on 11/20/2015.
 */
public class Report {
    private int id;
    private int expenseid;
    private String reportedby;
    private String reportedto;
    private String approvalstatus;
    private String comments;
    private String date;
    private String pdflocation;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getExpenseid() {
        return expenseid;
    }

    public void setExpenseid(int expenseid) {
        this.expenseid = expenseid;
    }

    public String getReportedby() {
        return reportedby;
    }

    public void setReportedby(String reportedby) {
        this.reportedby = reportedby;
    }

    public String getReportedto() {
        return reportedto;
    }

    public void setReportedto(String reportedto) {
        this.reportedto = reportedto;
    }

    public String getApprovalstatus() {
        return approvalstatus;
    }

    public void setApprovalstatus(String approvalstatus) {
        this.approvalstatus = approvalstatus;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPdflocation() {
        return pdflocation;
    }

    public void setPdflocation(String pdflocation) {
        this.pdflocation = pdflocation;
    }

    @Override
    public String toString(){
        return getExpenseid()+" "+getComments()+" "+getReportedto()+" "+getApprovalstatus();
    }
}
