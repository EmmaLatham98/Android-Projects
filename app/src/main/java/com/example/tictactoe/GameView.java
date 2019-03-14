package com.example.tictactoe;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class GameView extends View {

    public GameLogic mGame;
    private Paint mGridPaint, mPlayer1Paint, mPlayer2Paint,mBGPaint, mEmptyPaint;
    private GestureDetector mGestureDetector;
    private float m_canvasWidth,m_canvasHeight;
    private static final int ROW_COUNT = 3;
    private static final int COL_COUNT = 3;
    private int m_playerNum;

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);

        initialise();
        // Bind the gestureDetector to GestureListener
        mGestureDetector = new GestureDetector(context, new MyGestureListener());
        m_playerNum = 1;

    }

    public GameView(Context context) {
        super(context);

        initialise();
        // Bind the gestureDetector to GestureListener
        mGestureDetector = new GestureDetector(context, new MyGestureListener());
        m_playerNum = 1;
        mGame = new GameLogic();


    }

    private void initialise() {

        mGridPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mGridPaint.setStyle(Paint.Style.FILL);
        mGridPaint.setColor(Color.BLACK);

        mPlayer1Paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPlayer1Paint.setStyle(Paint.Style.FILL);
        mPlayer1Paint.setColor(0xffff0000);

        mPlayer2Paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPlayer2Paint.setStyle(Paint.Style.FILL);
        mPlayer2Paint.setColor(0xffffff00);

        mBGPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBGPaint.setStyle(Paint.Style.FILL);
        mBGPaint.setColor(Color.parseColor("#EBEBEB"));

        mEmptyPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mEmptyPaint.setStyle(Paint.Style.FILL);
        mEmptyPaint.setColor(Color.parseColor("#E9EBF5"));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        double SEPARATOR_RATIO = 0.1;
        float chosenDiameter, separatorSize, canvasWidth,canvasHeight;
        int noOfColSeparators, noOfRowSeparators, tokenAtPos;
        Paint paint;


        noOfColSeparators = COL_COUNT + 1;
        noOfRowSeparators = ROW_COUNT + 1;

        // Given the no of rows and cols, and the screen size, which choice of diameter
        // will best fit everything onto the screen
        chosenDiameter = (float) (getWidth() / (COL_COUNT + noOfColSeparators * SEPARATOR_RATIO));

        separatorSize = (float) (chosenDiameter * SEPARATOR_RATIO);

        // Based on the chosenDiameter, calculate the size of the GameBoard
        canvasWidth = (noOfColSeparators * chosenDiameter * (float) SEPARATOR_RATIO)
                + COL_COUNT * chosenDiameter;
        m_canvasWidth = canvasWidth;

        // Based on the chosenDiameter, calculate the size of the GameBoard
        canvasHeight = (noOfColSeparators * chosenDiameter * (float) SEPARATOR_RATIO)
                + ROW_COUNT * chosenDiameter;
        m_canvasHeight = canvasHeight;

        // Draw the game board
        canvas.drawRect(0, 0, canvasWidth, canvasHeight, mGridPaint);

        for (int col = 0; col < COL_COUNT; col++) {
            for (int row = 0; row < ROW_COUNT; row++) {
                // Does the game array contain a piece at this location?
                tokenAtPos = mGame.tileAt(col, row);
                // Choose the correct colour for the circle
                if (tokenAtPos == 0) {
                    paint = mEmptyPaint;
                } else if (tokenAtPos == 1) {
                    paint = mPlayer1Paint;
                } else if (tokenAtPos == 2) {
                    paint = mPlayer2Paint;
                }else {
                    paint = mEmptyPaint;
                }
                // Calculate the co-ordinates of the circle
                float cx = separatorSize + (chosenDiameter + separatorSize) * col;
                float cy = separatorSize + (chosenDiameter + separatorSize) * row;

                canvas.drawRect(cx, cy, cx + chosenDiameter, cy + chosenDiameter, paint);
            }

        }


    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        boolean r = this.mGestureDetector.onTouchEvent(event);

        return super.onTouchEvent(event) || r;
    }
    class MyGestureListener extends GestureDetector.SimpleOnGestureListener {


        // You should always include onDown() and it should always return true.
        // Otherwise the GestureListener may ignore other events.

        public boolean onDown(MotionEvent ev) {
            return true;
        }

        public void onLongPress(MotionEvent ev) {

        }


        public boolean onSingleTapUp(MotionEvent ev) {

                // Get location of touch, calculate the column that has been clicked.
                double dCol = (ev.getX() / (m_canvasWidth / COL_COUNT));
                double dRow = ((ev.getY()) / (m_canvasHeight / ROW_COUNT));
                int columnNum = (int) (Math.floor(dCol));
                int rowNum = (int) (Math.floor(dRow));

                if(columnNum<COL_COUNT&& rowNum< ROW_COUNT){
                    switch(mGame.playTokenPlayer(columnNum, rowNum, m_playerNum)){
                        case 1: m_playerNum = 1;
                            break;
                        case 2: m_playerNum = 2;
                            break;
                        case 13: m_playerNum =2;
                            Toast.makeText(getContext(), "Computer Wins", Toast.LENGTH_LONG).show();
                            break;
                        case 14: m_playerNum =1;
                            Toast.makeText(getContext(), "No One Wins", Toast.LENGTH_LONG).show();
                            break;
                        case 12: m_playerNum =2;
                            Toast.makeText(getContext(), "Player 2 Wins", Toast.LENGTH_LONG).show();
                            break;
                        case 11: m_playerNum = 1;
                            Toast.makeText(getContext(), "Player 1 Wins", Toast.LENGTH_LONG).show();
                            break;
                        default: m_playerNum =1;
                    }
                    invalidate();
                    return true;
                }

            return false;


        }

    }
}



