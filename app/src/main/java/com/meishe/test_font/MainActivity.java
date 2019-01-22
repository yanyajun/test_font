package com.meishe.test_font;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private static final String FONT_MAX_LENGTH = "北京美摄网络科技公司";
    private Context context;
    private EditText edit;
    private EditText edit2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;

        edit = findViewById(R.id.edit);
        edit2 = findViewById(R.id.edit2);



        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String ss = edit.getText().toString();

                Log.e("===>", "onCreate: " + ss);

                ss = manageString1Length(ss, 63);

                edit.setText(ss);

                Log.e("===>", "onCreate: " + ss);



                String ttt = edit2.getText().toString();
                Log.e("===>", "onCreate: ttt: " + ttt);
                ttt = manageString2Length(ttt, 39);
                edit2.setText(ttt);
                Log.e("===>", "onCreate: ttt: " + ttt);
            }
        });

    }

    private String manageString1Length(String source_text, int fontSize) {
        if(source_text == null || source_text.isEmpty())
            return null;
        source_text = source_text.trim();
        Paint textPaint = new Paint();
        textPaint.setTextSize(fontSize);

        float max_length = textPaint.measureText(FONT_MAX_LENGTH);
        float emojiMinLength;
        String emoji = "😀";
        float lenght1 = textPaint.measureText(emoji);
        float lenght2 = textPaint.measureText(emoji.substring(0,1));;
        emojiMinLength = lenght1-lenght2;

        int sub_index = 0;
        for (int i = 0; i < source_text.length(); i++) {
            sub_index = i+ 1;
            String test = source_text.substring(0,sub_index);
            float cur_length = textPaint.measureText(test);
            if(cur_length >= max_length) {
                if(i < source_text.length() - 1) {
                    String next_str = source_text.substring(0,i+2);
                    float end_length = textPaint.measureText(next_str) - cur_length;
                    if(end_length <= emojiMinLength) {
                        sub_index = i+2;
                    }
                    break;

                } else {
                    sub_index = source_text.length();
                    break;
                }
            }
            Log.e("===>", "manageStringLength: "+ test + " " + cur_length);
        }
        if(sub_index != source_text.length()) {
            return source_text.substring(0,sub_index) + "...";
        }
        return source_text.substring(0,sub_index);
    }

    private String manageString2Length(String source_text, int fontSize) {
        if(source_text == null || source_text.isEmpty())
            return null;
        source_text = source_text.trim();
        Paint textPaint = new Paint();
        textPaint.setTextSize(fontSize);

        float max_length = (float) getScreenWidth(context);
        float emojiMinLength;
        String emoji = "😀";
        float lenght1 = textPaint.measureText(emoji);
        float lenght2 = textPaint.measureText(emoji.substring(0,1));;
        emojiMinLength = lenght1-lenght2;


        Log.e("===>", "manageStringLength: "+ max_length + " " + max_length + " emojiMinLength: " + emojiMinLength);

        int sub_index = 0;
        for (int i = 0; i < source_text.length(); i++) {
            sub_index = i+ 1;
            String test = source_text.substring(0,sub_index);
            float cur_length = textPaint.measureText(test);
            if(cur_length >= max_length) {
                sub_index = i;
                if(i <= source_text.length() - 1) {
                    String next_str = source_text.substring(0,i);
                    float end_length = cur_length - textPaint.measureText(next_str);
                    if(end_length <= emojiMinLength) {
                        sub_index = i - 1;
                    }
                }
                break;
            }
            Log.e("===>", "manageStringLength: "+ test + " " + cur_length);
        }
        Log.e("===>", "manageStringLength: " + source_text.substring(0,sub_index));
        return source_text.substring(0,sub_index);
    }


    public int getScreenWidth(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager mWm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        mWm.getDefaultDisplay().getMetrics(dm);
        int screenWidth = dm.widthPixels;
        return screenWidth;
    }
}
