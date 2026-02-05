package com.example.property.customertable;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class customerbean {
    private String contact, name, emailid, address, adhaarno, refby, picpath, occupation, usertype;
    private ImageView imageView;

    public customerbean(String contact, String name, String emailid, String address,
                        String adhaarno, String refby, String picpath,
                        String occupation, String usertype) {
        this.contact = contact;
        this.name = name;
        this.emailid = emailid;
        this.address = address;
        this.adhaarno = adhaarno;
        this.refby = refby;
        this.picpath = picpath;
        this.occupation = occupation;
        this.usertype = usertype;

        try {
            Image img = new Image("file:" + picpath, 80, 80, true, true);
            this.imageView = new ImageView(img);
        } catch (Exception e) {
            this.imageView = new ImageView(); // fallback empty image
        }
    }

    public String getContact() { return contact; }
    public String getName() { return name; }
    public String getEmailid() { return emailid; }
    public String getAddress() { return address; }
    public String getAdhaarno() { return adhaarno; }
    public String getRefby() { return refby; }
    public String getPicpath() { return picpath; }
    public String getOccupation() { return occupation; }
    public String getUsertype() { return usertype; }
    public ImageView getImageView() { return imageView; }
}
