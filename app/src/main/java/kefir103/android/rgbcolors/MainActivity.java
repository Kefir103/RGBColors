package kefir103.android.rgbcolors;

import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "myLogs";

    Button mTargetButton;
    Button mNonTargetButton;

    TextView mTvRetryCounter;
    TextView mTvColor;
    TextView mTvCorrectAnswers;

    int[] btnIDs = { R.id.btn_TL, R.id.btn_TR, R.id.btn_BL, R.id.btn_BR };
    int mAttempts = 5;
    int mCorrect;

    static boolean RGBFlag = true;

    Random mRandom = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setSubtitle(R.string.rgb_scheme_subtitle);
        initViews();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(Menu.NONE, R.id.change_colors_item, Menu.NONE, R.string.to_argb_menu_item);
        menu.getItem(0).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.change_colors_item){
            if (RGBFlag){
                item.setTitle(R.string.to_rgb_menu_item);
                getSupportActionBar().setSubtitle(R.string.argb_scheme_subtitle);
                RGBFlag = false;
                setColors();
                return true;
            } else {
                item.setTitle(R.string.to_argb_menu_item);
                getSupportActionBar().setSubtitle(R.string.rgb_scheme_subtitle);
                RGBFlag = true;
                setColors();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void initViews(){
        Log.d(TAG, "initViews()...");
        mTvRetryCounter = (TextView) findViewById(R.id.tv_retry_counter);
        mTvColor = (TextView) findViewById(R.id.tv_color);
        mTvCorrectAnswers = (TextView) findViewById(R.id.tv_correct_answers);

        mTvRetryCounter.setText(getString(R.string.retry_counter) + " " +  mAttempts);
        mTvCorrectAnswers.setText(getString(R.string.correct_answers) + " " + mCorrect);

        setColors();
    }

    private void setColors(){
        Log.d(TAG, "setColors()...");

        ColorsLab colorsLab = new ColorsLab();
        String hexString;
        if (isRGBFlag()){
            hexString = Integer.toHexString(colorsLab.getTargetColor())
                    .substring(2, 8)
                    .toUpperCase();
        } else {
            hexString = Integer.toHexString(colorsLab.getTargetColor()).toUpperCase();
        }

        mTvColor.setTextColor(colorsLab.getTargetColor());
        mTvColor.setText("#" + hexString);
        int targetColorPosition = mRandom.nextInt(3);

        for (int i = 0; i < 4; i++){
            if (i == targetColorPosition){
                mTargetButton = findViewById(btnIDs[i]);
                mTargetButton.setBackgroundColor(colorsLab.getTargetColor());
            } else {
                mNonTargetButton = findViewById(btnIDs[i]);
                mNonTargetButton.setBackgroundColor(colorsLab.getColor());
            }
        }
    }

    public void checkColor(View view){

        Button checkButton = (Button) findViewById(view.getId());
        ColorDrawable colorDrawable = (ColorDrawable) checkButton.getBackground();

        if (colorDrawable.getColor() == mTvColor.getCurrentTextColor()){
            mCorrect++;
            mTvCorrectAnswers.setText(getString(R.string.correct_answers) + " " + mCorrect);
            showToast(R.string.correct_toast);
        } else {
            mAttempts--;
            mTvRetryCounter.setText(getString(R.string.retry_counter) + " " +  mAttempts);
            showToast(R.string.incorrect_toast);

            if (mAttempts == 0){
                disableButtons();
            }
        }

        setColors();
    }

    private void showToast(int resString){
        Toast.makeText(this, resString, Toast.LENGTH_SHORT)
                .show();
    }

    private void disableButtons(){
        Button button;
        for (int i = 0; i < 4; i++){
            button = (Button) findViewById(btnIDs[i]);
            button.setEnabled(false);
        }

        mTvRetryCounter.setText(R.string.game_over);
    }

    public static boolean isRGBFlag() {
        return RGBFlag;
    }
}
