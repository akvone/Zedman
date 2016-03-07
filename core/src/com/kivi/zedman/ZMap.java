package com.kivi.zedman;

/**
 * Created by Kirill on 06.03.2016.
 */
public class ZMap {

    public short[][] mapMatrix;

    ZMap(){
        mapMatrix = new short[20][10];
        for (int j=0;j<9;j++){
            for (int  i=0;i<20;i++){
                mapMatrix[i][j]=1;
            }
        }
        for (int  i=0;i<20;i++){
            mapMatrix[i][9]=0;
        }
    }
}
