package com.example.property.dealfinal;

public class bean {
    private int rid;
    private String seekerscontact;
    private String bname;
    private String traderscontact;
    private String sname;
    private String dateofregistr;
    private String dateofdeal;
    private String totalamount;
    private String advancedpayment;
    private String balancedpayment;
    private String corporationno;
    private String commissionengrossed;

    public bean(int rid, String seekerscontact, String bname,
                String traderscontact, String sname, String dateofregistr,
                String dateofdeal, String totalamount, String advancedpayment,
                String balancedpayment, String corporationno, String commissionengrossed) {
        this.rid = rid;
        this.seekerscontact = seekerscontact;
        this.bname = bname;
        this.traderscontact = traderscontact;
        this.sname = sname;
        this.dateofregistr = dateofregistr;
        this.dateofdeal = dateofdeal;
        this.totalamount = totalamount;
        this.advancedpayment = advancedpayment;
        this.balancedpayment = balancedpayment;
        this.corporationno = corporationno;
        this.commissionengrossed = commissionengrossed;
    }

    public int getRid() { return rid; }
    public String getSeekerscontact() { return seekerscontact; }
    public String getBname() { return bname; }
    public String getTraderscontact() { return traderscontact; }
    public String getSname() { return sname; }
    public String getDateofregistr() { return dateofregistr; }
    public String getDateofdeal() { return dateofdeal; }
    public String getTotalamount() { return totalamount; }
    public String getAdvancedpayment() { return advancedpayment; }
    public String getBalancedpayment() { return balancedpayment; }
    public String getCorporationno() { return corporationno; }
    public String getCommissionengrossed() { return commissionengrossed; }
}
