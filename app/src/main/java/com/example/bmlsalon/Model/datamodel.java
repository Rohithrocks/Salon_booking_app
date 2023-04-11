package com.example.bmlsalon.Model;

public class datamodel {
    int image;
    String userId,userdesc;

    public datamodel(int image, String userId, String userdesc) {
        this.image = image;
        this.userId = userId;
        this.userdesc = userdesc;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserdesc() {
        return userdesc;
    }

    public void setUserdesc(String userdesc) {
        this.userdesc = userdesc;
    }
}
