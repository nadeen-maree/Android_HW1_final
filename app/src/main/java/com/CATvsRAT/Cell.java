package com.CATvsRAT;

public class Cell {
    private boolean isCat;
    private boolean isRat;
    private String catRotation;
    private String ratRotation;


    public Cell() {// build the cell.
        this.isCat = false;
        this.isRat = false;
        this.catRotation = "right";
        this.ratRotation = "right";

    }

    //getters and setters.

    public boolean isCat() {
        return isCat;
    }
    public void setCat(boolean bool) {
        isCat = bool;
    }
    public boolean isRat() {
        return isRat;
    }
    public void setRat(boolean bool) {
        isRat = bool;
    }
    public String getCatRotation() { return catRotation; }
    public void setCatRotation(String catRotation) {
        this.catRotation = catRotation;
    }
    public String getRatRotation() { return ratRotation; }
    public void setRatRotation(String ratRotation) {
        this.ratRotation = ratRotation;
    }

}
