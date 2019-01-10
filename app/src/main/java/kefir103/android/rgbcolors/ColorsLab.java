package kefir103.android.rgbcolors;

import android.graphics.Color;
import android.util.Log;

import java.util.Random;

public class ColorsLab {

    private static final String TAG = "myLogs";

    private int targetColor;
    private int btnColor;

    private Random mRandom = new Random();

    ColorsLab(){
        setTargetColor();
    }

    private void setTargetColor() {
        Log.d(TAG, "setTargetColor()...");
        targetColor = Color.rgb(
                mRandom.nextInt(255),
                mRandom.nextInt(255),
                mRandom.nextInt(255));
        Log.d(TAG, "targetColor is: " + targetColor);
    }

    int getTargetColor() {
        return targetColor;
    }

    public int getColor(){
        do{
            btnColor = Color.rgb(
                    mRandom.nextInt(255),
                    mRandom.nextInt(255),
                    mRandom.nextInt(255));
        } while (btnColor == targetColor);
        return btnColor;
    }
}
