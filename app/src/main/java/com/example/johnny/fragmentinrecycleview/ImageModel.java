package com.example.johnny.fragmentinrecycleview;

public class ImageModel {

    private int image_drawable;

    private String source,discription;


    public ImageModel(int image_drawable, String source, String discription) {
        this.image_drawable = image_drawable;
        this.source = source;
        this.discription = discription;
    }


    public int getImage_drawable() {
        return image_drawable;
    }

    public void setImage_drawable(int image_drawable) {
        this.image_drawable = image_drawable;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }
}
