package shem.com.materiallogin;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.FrameLayout;
import android.widget.TextView;

import io.codetail.animation.SupportAnimator;
import io.codetail.animation.ViewAnimationUtils;

/**
 * Created by shem on 1/15/16.
 */
public class DefaultLoginView extends FrameLayout {

    public interface DefaultLoginViewListener {
        void onLogin(TextInputLayout loginUser, TextInputLayout loginPass);
    }

    private DefaultLoginViewListener listener;


    public DefaultLoginView(Context context) {
        this(context, null);
    }

    public DefaultLoginView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DefaultLoginView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public DefaultLoginView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.login_layout, this, true);

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.DefaultLoginView,
                0, 0);

        TextView loginTitle = (TextView) findViewById(R.id.login_title);
        final TextInputLayout loginUser = (TextInputLayout) findViewById(R.id.login_user);
        final TextInputLayout loginPass = (TextInputLayout) findViewById(R.id.login_pass);
        TextView loginBtn = (TextView) findViewById(R.id.login_btn);

        findViewById(R.id.login_btn).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onLogin(loginUser, loginPass);
                }
            }
        });
        try {
            String string = a.getString(R.styleable.DefaultLoginView_loginTitle);
            if (string != null) {
                loginTitle.setText(string);
            }

            string = a.getString(R.styleable.DefaultLoginView_loginHint);
            if (string != null) {
                loginUser.setHint(string);
            }

            string = a.getString(R.styleable.DefaultLoginView_loginPasswordHint);
            if (string != null) {
                loginPass.setHint(string);
            }

            string = a.getString(R.styleable.DefaultLoginView_loginActionText);
            if (string != null) {
                loginBtn.setText(string);
            }

            int color = a.getColor(R.styleable.DefaultLoginView_loginTextColor, ContextCompat.getColor(getContext(), R.color.material_login_login_text_color));
            loginUser.getEditText().setTextColor(color);
            loginPass.getEditText().setTextColor(color);

        } finally {
            a.recycle();
        }
    }

    public void setListener(DefaultLoginViewListener listener) {
        this.listener = listener;
    }
}
