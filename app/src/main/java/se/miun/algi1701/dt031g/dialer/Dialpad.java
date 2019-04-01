package se.miun.algi1701.dt031g.dialer;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;

public class Dialpad extends ConstraintLayout {

    public Dialpad(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public Dialpad(Context context) {
        super(context);
        init(context, null);
    }

    private void init(Context context, AttributeSet attrs){
        inflate(context, R.layout.dialpad, this);
    }
}
