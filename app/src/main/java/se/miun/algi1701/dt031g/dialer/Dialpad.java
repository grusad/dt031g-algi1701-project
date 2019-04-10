package se.miun.algi1701.dt031g.dialer;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class Dialpad extends ConstraintLayout {

    private TextView display;
    private ImageButton backspaceButton;

    public Dialpad(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public Dialpad(Context context) {
        super(context);
        init(context, null);
    }

    public Dialpad(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs){
        inflate(context, R.layout.dialpad, this);
        initComponents();
        setListeners();


    }

    private void initComponents(){
        display = (TextView) findViewById(R.id.display);
        backspaceButton = (ImageButton) findViewById(R.id.backspace_button);
    }

    private void setListeners(){
        backspaceButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(display.getText().length() > 0){
                    display.setText(display.getText().subSequence(0, display.getText().length() - 1));
                }
            }
        });

        backspaceButton.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                display.setText("");
                return true;
            }
        });

    }

    public void appendChar(CharSequence c){
        display.append(c);
        display.setHeight(display.getLineCount() * display.getLineHeight());
    }

    public String getNumber(){
        return display.getText().toString();
    }

}
