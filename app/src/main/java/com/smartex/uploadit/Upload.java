package com.smartex.uploadit;

public class Upload {
   private String name;
    private String imageUrl;

    public Upload(){
        //empty Control
    }


    public Upload(String name,String imageUrl){
        if (name.trim().equals(""))
        {
            name = "NO NAME";
        }
        this.name = name;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
