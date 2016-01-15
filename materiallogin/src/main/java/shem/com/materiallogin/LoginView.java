package shem.com.materiallogin;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * Created by shem on 1/15/16.
 */
public class LoginView extends FrameLayout {

    private LoginViewListener listener;

    private TextView registerBtn;
    private TextView registerTitle;
    private TextInputLayout registerUser;
    private TextInputLayout registerPass;
    private TextInputLayout registerPassRep;
    private TextView loginTitle;
    private TextInputLayout loginUser;
    private TextInputLayout loginPass;
    private TextView loginBtn;
    private FloatingActionButton registerFab;
    private View registerCancel;
    private CardView loginView;
    private CardView registerView;

    public LoginView(Context context) {
        super(context);
        init(context);
    }

    public LoginView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public LoginView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public LoginView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.login_view, this, true);

        loginView = (CardView) findViewById(R.id.login_window);
        loginTitle = (TextView) findViewById(R.id.login_title);
        loginUser = (TextInputLayout) findViewById(R.id.login_user);
        loginPass = (TextInputLayout) findViewById(R.id.login_pass);
        loginBtn = (TextView) findViewById(R.id.login_btn);

        registerView = (CardView) findViewById(R.id.register_window);
        registerTitle = (TextView) findViewById(R.id.register_title);
        registerUser = (TextInputLayout) findViewById(R.id.register_user);
        registerPass = (TextInputLayout) findViewById(R.id.register_pass);
        registerPassRep = (TextInputLayout) findViewById(R.id.register_pass_rep);
        registerBtn = (TextView) findViewById(R.id.register_btn);

        registerFab = (FloatingActionButton) findViewById(R.id.register_fab);
        registerCancel = findViewById(R.id.register_cancel);


        registerBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onRegister(registerUser, registerPass, registerPassRep);
            }
        });

        loginBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onLogin(loginUser, loginPass);
            }
        });

        registerFab.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                animateRegister();
            }
        });

        registerCancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                animateLogin();
            }
        });
    }

    private void animateRegister() {
        Path path = new Path();
        path.addArc(-241F, -40F, 41F, 242F, -45F, 180F);
        path.lineTo(-0F, -50F);
        FabAnimation fabAnimation = new FabAnimation(path);
        fabAnimation.setDuration(400);
        fabAnimation.setInterpolator(new AccelerateInterpolator());

        fabAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                Animator animator = ViewAnimationUtils.createCircularReveal(
                        registerView, registerView.getWidth() - 250, 400, 0f, 2F * registerView.getHeight());
                animator.setDuration(700);
                animator.setStartDelay(200);
                animator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        registerView.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        loginView.setVisibility(View.GONE);
                    }
                });
                animator.start();
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                registerFab.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });

        registerFab.startAnimation(fabAnimation);
    }


    private void animateLogin() {
        registerCancel.animate().scaleX(0F).scaleY(0F).alpha(0F).rotation(90F).
                setDuration(200).setInterpolator(new AccelerateInterpolator()).start();
        Animator animator = ViewAnimationUtils.createCircularReveal(
                registerView, registerView.getWidth() / 2, registerView.getHeight() / 2, 1f * registerView.getHeight(), 0F);
        animator.setDuration(500);
        animator.setStartDelay(100);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                loginView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                registerView.setVisibility(View.GONE);
                registerCancel.setScaleX(1F);
                registerCancel.setScaleY(1F);
                registerCancel.setAlpha(1F);
                registerCancel.setRotation(45F);
                registerFab.setVisibility(View.VISIBLE);

                ObjectAnimator animX = ObjectAnimator.ofFloat(registerFab, "scaleX", 0F, 1F);
                ObjectAnimator animY = ObjectAnimator.ofFloat(registerFab, "scaleY", 0F, 1F);
                ObjectAnimator alpha = ObjectAnimator.ofFloat(registerFab, "alpha", 0F, 1F);
                ObjectAnimator rotation = ObjectAnimator.ofFloat(registerFab, "rotation", 0F, 90F);
                AnimatorSet animator = new AnimatorSet();
                animator.playTogether(animX, animY, alpha, rotation);
                animator.setInterpolator(new AccelerateInterpolator());
                animator.setDuration(200);
                animator.start();
            }
        });
        animator.start();
    }

    public void setListener(LoginViewListener listener) {
        this.listener = listener;
    }

    class FabAnimation extends Animation {
        private PathMeasure measure;
        private float[] pos;

        public FabAnimation(Path path) {
            measure = new PathMeasure(path, false);
            pos = new float[] {0, 0};
        }

        protected void applyTransformation(float interpolatedTime, Transformation t) {
            measure.getPosTan(measure.getLength() * interpolatedTime, pos,null);
            Matrix matrix = t.getMatrix();
            matrix.setTranslate(pos[0], pos[1]);
            matrix.preRotate(interpolatedTime * 45);
            t.setAlpha(1 - interpolatedTime);
        }
    }
}
