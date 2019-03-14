package com.example.shengtingapi.dto;

public class ImageExtract {
    private String face_selection;
    private Image image;

    public ImageExtract(String face_selection, Image image) {
        this.face_selection = face_selection;
        this.image = image;
    }

    public String getFace_selection() {
        return face_selection;
    }

    public void setFace_selection(String face_selection) {
        this.face_selection = face_selection;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}
