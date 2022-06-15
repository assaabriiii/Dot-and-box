package com.example.demo;

public class Board {
    Tile[][] board = new Tile[7][7];
    horLine[][] horLines = new horLine[8][7];
    verLine[][] verLines = new verLine[7][8];

    //The three following methods reset the status and color value of the shapes
    public void resetTiles(){
        for (int i = 0; i < 7; i++)
            for (int j = 0; j < 7; j++){
                Tile tile = new Tile(i, j, 0, 0);
                board[i][j] = tile;
            }
    }

    public void resetHorLines(){
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 7; j++){
                horLine hLine = new horLine(i, j, 0, 0);
                horLines[i][j] = hLine;
            }
    }

    public void resetVerLines(){
        for (int i = 0; i < 7; i++)
            for (int j = 0; j < 8; j++){
                verLine vLine = new verLine(i, j, 0, 0);
                verLines[i][j] = vLine;
            }
    }

    public Board(){
        resetTiles();
        resetHorLines();
        resetVerLines();
    }

    public int getTileStatus(int x, int y){ return board[x][y].getStatus(); }

    public int getTileColor(int x, int y){
        return board[x][y].getColor();
    }

    public int getHorLineStatus(int x, int y){
        return horLines[x][y].getStatus();
    }

    public int getVerLineStatus(int x, int y){
        return verLines[x][y].getStatus();
    }

    public void setTileStatus(int x, int y){
        board[x][y].setStatus(1);
    }

    public void setTileColor(int x, int y, int color){
        board[x][y].setColor(color);
    }

    public void setHorLineStatus(int x, int y){
        horLines[x][y].setStatus(1);
    }

    public void setVerLineStatus(int x, int y){
        verLines[x][y].setStatus(1);
    }

    //This method counts the number of Tiles that are filled
    public int filledTilesCounter(){
        int tileCounter = 0;
        for (int i = 0; i < 7; i++)
            for (int j = 0; j < 7; j++)
                if (getTileStatus(i, j) == 1)
                    tileCounter++;
        return tileCounter;
    }

    //This method returns the position of the tile that the current player has scored
    //returns -1 if the condition is not true
    public int checkTileWin(){
        int position = 0;
        for (int i = 0; i < 7; i++)
            for (int j = 0; j < 7; j++)
                if (board[i][j].getStatus() == 0)
                    if ((horLines[i][j].getStatus() == 1) && (horLines[i + 1][j].getStatus() == 1) &&
                            (verLines[i][j].getStatus() == 1) && (verLines[i][j + 1].getStatus() == 1)){
                        position += i * 10;
                        position += j;
                        return position;
                    }
        return -1;

    }
}
