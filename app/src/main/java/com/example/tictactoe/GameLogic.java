package com.example.tictactoe;

import android.os.Handler;

import java.util.ArrayList;
import java.util.Random;


public class GameLogic {

    private static final int ROW_COUNT = 3;
    private static final int COL_COUNT = 3;
    private boolean m_isSinglePlayer;
    private int[][] mData;
    private int m_turnNumber;

    public GameLogic() {
        super();
        mData = new int[3][3];
        m_turnNumber = 1;
    }

    public void setGameType(boolean isSinglePlayer) {
        m_isSinglePlayer = isSinglePlayer;
    }



    public int tileAt(int column, int row) {
        return mData[column][row];
    }


    // Changes the contents of the game board at a specified row and column
    public int playTokenPlayer(int column, int row, int playerNum) {
        mData[column][row] = playerNum;
        if (!m_isSinglePlayer) {
           playTokenComputer(playerNum);
        }else{
            if(playerNum == 1){
                playerNum = 2;
            }else{
                playerNum = 1;
            }
        }

        return playerNum;
    }


    // Changes the contents of the game board at a specified row and column
    public void playTokenComputer(int playerNum) {
        m_turnNumber ++;
        if(m_turnNumber<10){
            boolean tilePlaced = false;
            while(!tilePlaced) {
                Random rand = new Random();
                int x = rand.nextInt(COL_COUNT);
                int y = rand.nextInt(ROW_COUNT);

                if (mData[x][y] == 0) {
                    mData[x][y] = playerNum + 1;
                    tilePlaced = true;
                }
            }

        }
    }


}



