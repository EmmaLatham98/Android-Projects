package com.example.tictactoe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


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
        m_turnNumber++;
        if (determineWin(column, row, playerNum)) {
            playerNum += 10;
        }else {
            if (m_turnNumber < COL_COUNT * ROW_COUNT) {
                if (!m_isSinglePlayer) {
                    if (playTokenComputer(playerNum)) {
                        playerNum += 12;
                    }

                } else {
                    if (playerNum == 1) {
                        playerNum = 2;
                    } else if (playerNum == 2) {
                        playerNum = 1;
                    }
                }
            }else{
                playerNum += 13;
            }
        }

        return playerNum;
    }


    // Changes the contents of the game board at a specified row and column
    public boolean playTokenComputer(int playerNum) {

        if (m_turnNumber <=9 ) {
            boolean tilePlaced = false;
            while (!tilePlaced) {
                Random rand = new Random();
                int x = rand.nextInt(COL_COUNT);
                int y = rand.nextInt(ROW_COUNT);
                if (mData[x][y] == 0) {
                    mData[x][y] = playerNum + 1;
                    m_turnNumber ++;
                    if (determineWin(x, y, playerNum + 1)) {
                        return true;
                    } else {
                        return false;
                    }
                }
            }

        } else {
            return false;
        }
        return false;
    }

    public boolean determineWin(int rootCol, int rootRow, int playerNum) {
        if (sortCandidateTiles(getCandidateTiles(rootCol, rootRow, playerNum))) {
            return true;
        } else {
            return false;
        }
    }

    public ArrayList<Direction> getCandidateTiles(int rootCol, int rootRow, int playerNum) {


        ArrayList<Direction> directions = new ArrayList<Direction>();

        int nodeRow = rootRow;
        while (nodeRow + 1 < ROW_COUNT) {
            if (mData[rootCol][nodeRow + 1] == playerNum) {
                directions.add(Direction.VERTICAL);
                nodeRow++;
            } else {
                break;
            }
        }
        nodeRow = rootRow;
        while (nodeRow > 0) {
            if (mData[rootCol][nodeRow - 1] == playerNum) {
                directions.add(Direction.VERTICAL);
                nodeRow--;
            } else {
                break;
            }
        }

        int nodeCol = rootCol;
        while (nodeCol + 1 < COL_COUNT) {
            if (mData[nodeCol + 1][rootRow] == playerNum) {
                directions.add(Direction.HORIZONTAL);
                nodeCol++;
            } else {
                break;
            }
        }
        nodeCol = rootCol;
        while (nodeCol > 0) {
            if (mData[nodeCol - 1][rootRow] == playerNum) {
                directions.add(Direction.HORIZONTAL);
                nodeCol--;
            } else {
                break;
            }
        }

        nodeCol = rootCol;
        nodeRow = rootRow;
        while (nodeCol + 1 < COL_COUNT && nodeRow + 1 < ROW_COUNT) {
            if (mData[nodeCol + 1][nodeRow + 1] == playerNum) {
                directions.add(Direction.NEG_DIAGONAL);
                nodeCol++;
                nodeRow++;
            } else {
                break;
            }
        }
        nodeCol = rootCol;
        nodeRow = rootRow;
        while (nodeCol > 0 && nodeRow > 0) {
            if (mData[nodeCol - 1][nodeRow - 1] == playerNum) {
                directions.add(Direction.NEG_DIAGONAL);
                nodeCol--;
                nodeRow--;
            } else {
                break;
            }
        }

        nodeCol = rootCol;
        nodeRow = rootRow;
        while (nodeCol + 1 < COL_COUNT && nodeRow > 0) {
            if (mData[nodeCol + 1][nodeRow - 1] == playerNum) {
                directions.add(Direction.POS_DIAGONAL);
                nodeCol++;
                nodeRow--;
            } else {
                break;
            }
        }
        nodeCol = rootCol;
        nodeRow = rootRow;
        while (nodeCol > 0 && nodeRow + 1 < ROW_COUNT) {
            if (mData[nodeCol - 1][nodeRow + 1] == playerNum) {
                directions.add(Direction.POS_DIAGONAL);
                nodeCol--;
                nodeRow++;
            } else {
                break;
            }
        }
        return directions;
    }

    public boolean sortCandidateTiles(ArrayList<Direction> directions) {
        List<Direction> directionTypes = Arrays.asList(Direction.VERTICAL, Direction.HORIZONTAL, Direction.NEG_DIAGONAL, Direction.POS_DIAGONAL);
        List<Integer> DirectionTotalList =
                (IntStream.range(0, 4)
                        .map((colorIndex) ->
                                IntStream.range(0, directions.size())
                                        .filter(listIndex -> directions.get(listIndex) == directionTypes.get(colorIndex))
                                        .map(count -> 1)
                                        .reduce(Integer::sum)
                                        .orElse(0)
                        )
                ).boxed()
                        .collect(Collectors.toList());


        if (DirectionTotalList.contains(2)) {
            return true;
        } else {
            return false;
        }

    }

    public enum Direction {
        VERTICAL, HORIZONTAL, NEG_DIAGONAL, POS_DIAGONAL
    }


}



