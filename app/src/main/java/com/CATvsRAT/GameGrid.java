package com.CATvsRAT;

import android.media.MediaPlayer;
import android.util.Log;

import com.learntodroid.androidminesweeper.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameGrid {

    private int catPos;
    private int ratPos;
    private int cheesePos;
    private List<Cell> cells;//list of cells.
    private int size_x;
    private int size_y;
    private int hearts;
    private int cheeseScore;


    public GameGrid(int size_x, int size_y) {
        this.size_x = size_x;
        this.size_y = size_y;
        this.hearts = 3;
        this.cheeseScore = 0;

        this.cells = new ArrayList<>();
        for (int i = 0; i < size_x * size_y; i++) {
            cells.add(new Cell());
        }
        cells.get(0).setCat(true);
        catPos =0;
        ratPos = cells.size()-1;
        cheesePos = 15;
        cells.get(catPos).setCat(true);
        cells.get(cheesePos).setCheese(true);
        cells.get(ratPos).setRat(true);

    }
    public List<Cell> getCells() {
        return cells;
    }

    public String right(){
        if((ratPos +1)%size_y!=0) {
            cells.get(ratPos).setRat(false);
            ratPos++;
            cells.get(ratPos).setRat(true);
            cells.get(ratPos).setRatRotation("right");
            if(checkAndRestore())
                return "CAT";

            if(checkEat())
                return "RAT";
        }
        return "";
    }
    public String down(){
        if(ratPos +size_y< cells.size()) {
            cells.get(ratPos).setRat(false);
            ratPos +=size_y;
            cells.get(ratPos).setRat(true);
            cells.get(ratPos).setRatRotation("down");
            if(checkAndRestore())
                return "CAT";

            if(checkEat())
                return "RAT";
        }
        return "";
    }
    public String left(){
        if((ratPos)%size_y!=0) {
            cells.get(ratPos).setRat(false);
            ratPos--;
            cells.get(ratPos).setRat(true);
            cells.get(ratPos).setRatRotation("left");
            if(checkAndRestore())
                return "CAT";

            if(checkEat())
                return "RAT";
        }
        return "";
    }
    public String up(){
        if(ratPos -size_y >=0) {
            cells.get(ratPos).setRat(false);
            ratPos -=size_y;
            cells.get(ratPos).setRat(true);
            cells.get(ratPos).setRatRotation("up");
            if(checkAndRestore())
                return "CAT";

            if(checkEat())
                return "RAT";
        }
        return "";
    }

    public String catDown(){
        if(catPos +size_y< cells.size()) {
            cells.get(catPos).setCat(false);
            catPos +=size_y;
            cells.get(catPos).setCat(true);
            cells.get(ratPos).setCatRotation("down");
            if(checkAndRestore())
                return "CAT";

            if(checkEat())
                return "RAT";
        }else{
            cells.get(catPos).setCat(false);
            catPos = catPos -(size_x*(size_y-1));
            cells.get(catPos).setCat(true);
            cells.get(ratPos).setCatRotation("down");
            if(checkAndRestore())
                return "CAT";

            if(checkEat())
                return "RAT";
        }
        return "";
    }
    public String randomCatMove(){
        int random = new Random().nextInt(2); // [0, 1]

        switch(random){

            case 0://left

                if((catPos)%size_y!=0) {
                    cells.get(catPos).setCat(false);
                    catPos--;
                    cells.get(catPos).setCat(true);
                    cells.get(ratPos).setCatRotation("left");
                    if(checkAndRestore())
                        return "CAT";

                    if(checkEat())
                        return "RAT";
                }

                break;
            case 1://right

                if((catPos +1)%size_y!=0) {
                    cells.get(catPos).setCat(false);
                    catPos++;
                    cells.get(catPos).setCat(true);
                    cells.get(ratPos).setCatRotation("right");
                    if(checkAndRestore())
                        return "CAT";

                    if(checkEat())
                        return "RAT";
                }
                break;
            default:
                break;

        }
        return "";
    }



    public void randomCheeseMove(){
        int random = new Random().nextInt(size_x*size_y); // [0, 1]

        cells.get(cheesePos).setCheese(false);
        cheesePos = random;
        cells.get(cheesePos).setCheese(true);
    }





    public void restoreRound(){

        for (int i = 0; i < size_x * size_y; i++) {//init all the cells in the list to be blank.
            cells.get(i).setCat(false);
            cells.get(i).setRat(false);
            cells.get(i).setCheese(false);
            cells.get(i).setCatRotation("right");
        }

        catPos =0;
        cheesePos = 15;
        ratPos = cells.size()-1;
        cells.get(cheesePos).setCheese(true);
        cells.get(ratPos).setRat(true);
        cells.get(catPos).setCat(true);
        hearts--;

    }

    public boolean checkAndRestore(){
        if(ratPos == catPos){
            restoreRound();
            return true;
        }
        return false;
    }

    public boolean checkEat(){
        if(ratPos == cheesePos) {
            cheeseScore += 10;
            randomCheeseMove();
            return true;
        }
        return false;
    }

    public int getHearts(){
        return hearts;
    }

    public int getCheeseScore() {
        return cheeseScore;
    }
}
