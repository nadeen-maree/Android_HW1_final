package com.CATvsRAT;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameGrid {

    private int catPos;
    private int ratPos;
    private List<Cell> cells;//list of cells.
    private int size_x;
    private int size_y;
    private int hearts;


    public GameGrid(int size_x, int size_y) {
        this.size_x = size_x;
        this.size_y = size_y;
        this.hearts = 3;

        this.cells = new ArrayList<>();
        for (int i = 0; i < size_x * size_y; i++) {//init all the cells in the list to be blank.
            cells.add(new Cell());
        }
        cells.get(0).setCat(true);
        catPos =0;
        cells.get(cells.size()-1).setRat(true);
        ratPos = cells.size()-1;
    }
    public List<Cell> getCells() {
        return cells;
    }

    public void right(){
        if((ratPos +1)%size_y!=0) {
            cells.get(ratPos).setRat(false);
            ratPos++;
            cells.get(ratPos).setRat(true);
            cells.get(ratPos).setRatRotation("right");
            checkAndRestore();
        }
    }
    public void down(){
        if(ratPos +size_y< cells.size()) {
            cells.get(ratPos).setRat(false);
            ratPos +=size_y;
            cells.get(ratPos).setRat(true);
            cells.get(ratPos).setRatRotation("down");
            checkAndRestore();
        }

    }
    public void left(){
        if((ratPos)%size_y!=0) {
            cells.get(ratPos).setRat(false);
            ratPos--;
            cells.get(ratPos).setRat(true);
            cells.get(ratPos).setRatRotation("left");
            checkAndRestore();
        }

    }
    public void up(){
        if(ratPos -size_y >=0) {
            cells.get(ratPos).setRat(false);
            ratPos -=size_y;
            cells.get(ratPos).setRat(true);
            cells.get(ratPos).setRatRotation("up");
            checkAndRestore();
        }
    }

    public void catDown(){
        if(catPos +size_y< cells.size()) {
            cells.get(catPos).setCat(false);
            catPos +=size_y;
            cells.get(catPos).setCat(true);
            cells.get(ratPos).setCatRotation("down");
            checkAndRestore();
        }else{
            cells.get(catPos).setCat(false);
            catPos = catPos -(size_x*(size_y-1));
            cells.get(catPos).setCat(true);
            cells.get(ratPos).setCatRotation("down");
            checkAndRestore();
        }
    }
    public void randomCatMove(){
        int random = new Random().nextInt(2); // [0, 1]

        switch(random){

            case 0://left

                if((catPos)%size_y!=0) {
                    cells.get(catPos).setCat(false);
                    catPos--;
                    cells.get(catPos).setCat(true);
                    cells.get(ratPos).setCatRotation("left");
                    checkAndRestore();
                }

                break;
            case 1://right

                if((catPos +1)%size_y!=0) {
                    cells.get(catPos).setCat(false);
                    catPos++;
                    cells.get(catPos).setCat(true);
                    cells.get(ratPos).setCatRotation("right");
                    checkAndRestore();
                }
                break;
            default:
                break;

        }

    }
    public void restoreRound(){

        for (int i = 0; i < size_x * size_y; i++) {//init all the cells in the list to be blank.
            cells.get(i).setCat(false);
            cells.get(i).setRat(false);
            cells.get(i).setCatRotation("right");
        }

        cells.get(0).setCat(true);
        catPos =0;
        cells.get(cells.size()-1).setRat(true);
        ratPos = cells.size()-1;
        hearts--;

    }

    public void checkAndRestore(){
        if(ratPos == catPos){
            restoreRound();
            Log.e("hearts",""+hearts);

        }
    }

    public int getHearts(){
        return hearts;
    }
}
