package se.miun.algi1701.dt031g.dialer;

import android.content.Context;
import android.content.res.TypedArray;
import android.media.SoundPool;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DialpadButton extends LinearLayout {

    TextView title;
    TextView message;


    public DialpadButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(final Context context, AttributeSet attrs){
        inflate(context, R.layout.dialpad_button_view, this);


        TypedArray typedArray = context.obtainStyledAttributes(attrs,  R.styleable.DialpadButton);

        CharSequence title = typedArray.getString(R.styleable.DialpadButton_title);
        CharSequence message = typedArray.getString(R.styleable.DialpadButton_message);

        typedArray.recycle();

        initComponents();

        setTitleText(title);
        setMessageText(message);

        final AlphaAnimation fadeOutAnimation = new AlphaAnimation(1, 0.5f);
        fadeOutAnimation.setFillAfter(true);
        fadeOutAnimation.setFillEnabled(true);
        fadeOutAnimation.setDuration(50);

        final AlphaAnimation fadeInAnimation = new AlphaAnimation(0.5f, 1);
        fadeOutAnimation.setFillAfter(true);
        fadeOutAnimation.setFillEnabled(true);
        fadeOutAnimation.setDuration(50);


        this.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                Log.d("Debug", "PRESSED");

                if(event.getAction() == MotionEvent.ACTION_DOWN){

                    v.startAnimation(fadeOutAnimation);

                    return true;
                }

                if(event.getAction() == MotionEvent.ACTION_UP){
                    v.startAnimation(fadeInAnimation);
                    SoundPlayer.getInstance(context).playSound(DialpadButton.this);
                }

                return false;
            }
        });

    }



    private void initComponents() {
        title = (TextView) findViewById(R.id.title);

        message = (TextView) findViewById(R.id.message);


    }

    public CharSequence getTitleText() {

        return title.getText();
    }

    public void setTitleText(CharSequence value) {
        if(value.length() > 1){
            value = value.subSequence(0, 1);
        }
        title.setText(value);
    }

    public CharSequence getMessageText() {
        return message.getText();
    }

    public void setMessageText(CharSequence value)
    {
        if(value.length() > 4){
            value = value.subSequence(0, 3);
        }
        message.setText(value);
    }

    @Override
    public void setOnTouchListener(OnTouchListener l) {
        super.setOnTouchListener(l);

    }


}
