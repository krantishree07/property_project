package com.example.property.amoumtstatustable;

public class DealBean {
    private int rid;
    private String seekersContact, bname, tradersContact, sname,
            dateOfRegister, dateOfDeal, totalAmount, advancedPayment,
            balancedPayment, corporationNo, commissionEngrossed;

    // Constructor
    public DealBean(int rid, String seekersContact, String bname, String tradersContact, String sname,
                    String dateOfRegister, String dateOfDeal, String totalAmount, String advancedPayment,
                    String balancedPayment, String corporationNo, String commissionEngrossed) {
        this.rid = rid;
        this.seekersContact = seekersContact;
        this.bname = bname;
        this.tradersContact = tradersContact;
        this.sname = sname;
        this.dateOfRegister = dateOfRegister;
        this.dateOfDeal = dateOfDeal;
        this.totalAmount = totalAmount;
        this.advancedPayment = advancedPayment;
        this.balancedPayment = balancedPayment;
        this.corporationNo = corporationNo;
        this.commissionEngrossed = commissionEngrossed;
    }

    public int getRid() { return rid; }
    public String getSeekersContact() { return seekersContact; }
    public String getBname() { return bname; }
    public String getTradersContact() { return tradersContact; }
    public String getSname() { return sname; }
    public String getDateOfRegister() { return dateOfRegister; }
    public String getDateOfDeal() { return dateOfDeal; }
    public String getTotalAmount() { return totalAmount; }
    public String getAdvancedPayment() { return advancedPayment; }
    public String getBalancedPayment() { return balancedPayment; }
    public String getCorporationNo() { return corporationNo; }
    public String getCommissionEngrossed() { return commissionEngrossed; }
}
