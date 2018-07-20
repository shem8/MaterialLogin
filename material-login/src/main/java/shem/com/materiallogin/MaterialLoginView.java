package shem.com.materiallogin;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Configuration;
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
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewGroupCompat;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.FrameLayout;
import android.widget.TextView;
import io.codetail.animation.ViewAnimationUtils;

/**
 * Created by shem on 1/15/16.
 */
public class MaterialLoginView extends FrameLayout {

    private static final String TAG = MaterialLoginView.class.getSimpleName();

    private FloatingActionButton registerFab;
    private View registerCancel;
    private ViewGroup loginCard;
    private ViewGroup registerCard;
    private View registerView;
    private View loginView;

    public MaterialLoginView(Context context) {
        this(context, null);
    }

    public MaterialLoginView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MaterialLoginView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MaterialLoginView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.login_view, this, true);

        loginCard = (ViewGroup) findViewById(R.id.login_card);
        registerCard = (ViewGroup) findViewById(R.id.register_card);
        registerFab = (FloatingActionButton) findViewById(R.id.register_fab);

        registerFab.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    animateRegister();
                } else {
                    //There's a bug in support implementation of FAB, so we firing animation with little delay so it won't be override by Android
                    new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            animateRegister();
                        }
                    }, 100);
                }
            }
        });


        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.MaterialLoginView,
                0, 0);

        try {
            int loginViewId = a.getResourceId(R.styleable.MaterialLoginView_loginView, R.layout.default_login_view);
            inflate(getContext(), loginViewId, loginCard);
            loginView = loginCard.getChildAt(0);

            int registerViewId = a.getResourceId(R.styleable.MaterialLoginView_registerView, R.layout.default_register_view);
            inflate(getContext(), registerViewId, registerCard);
            registerView = registerCard.getChildAt(0);
            if (registerView instanceof RegisterView) {
                registerCancel = ((RegisterView) registerView).getCancelRegisterView();
            }else if (registerView.findViewById(R.id.register_cancel) != null) {
                registerCancel = registerView.findViewById(R.id.register_cancel);
            }

            if (registerCancel != null) {
                registerCancel.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        animateLogin();
                    }
                });
            } else {
                Log.d(TAG, "The register view should implement RegisterView interface or set a view with register_cancel id");
            }


            registerFab.setImageResource(
                    a.getResourceId(R.styleable.MaterialLoginView_registerIcon, R.drawable.ic_add));

            boolean enabled = a.getBoolean(R.styleable.MaterialLoginView_registerEnabled, true);
            registerFab.setVisibility(enabled ? View.VISIBLE : View.GONE);

        } finally {
            a.recycle();
        }
    }

    private void animateRegister() {
        Path path = new Path();
        if (isRTL()) {
            RectF rect = new RectF(-41F, -40F, 241F, 242F);
            path.addArc(rect, -135F, -180F);
            path.lineTo(200F, -50F);
        } else {
            RectF rect = new RectF(-241F, -40F, 41F, 242F);
            path.addArc(rect, -45F, 180F);
            path.lineTo(-0F, -50F);
        }
        FabAnimation fabAnimation = new FabAnimation(path);
        fabAnimation.setDuration(400);
        fabAnimation.setInterpolator(new AccelerateInterpolator());

        fabAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                Animator animator = getCircularRevealAnimation(registerCard, isRTL() ? 250 : registerCard.getWidth() - 250, 400, 0f, 2F * registerCard.getHeight());
                animator.setDuration(700);
                animator.setStartDelay(200);
                animator.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        registerCard.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        loginCard.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {
                    }
                });
                animator.start();
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                registerFab.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

        registerFab.startAnimation(fabAnimation);
    }

    private boolean isRTL() {
        return ViewCompat.getLayoutDirection(this) == ViewCompat.LAYOUT_DIRECTION_RTL;
    }

    private Animator getCircularRevealAnimation(View view, int centerX, int centerY, float startRadius, float endRadius) {
        return ViewAnimationUtils.createCircularReveal(
                view, centerX, centerY, startRadius, endRadius);
    }


    private void animateLogin() {
        registerCancel.animate().scaleX(0F).scaleY(0F).alpha(0F).rotation(90F).
                setDuration(200).setInterpolator(new AccelerateInterpolator()).start();
        Animator animator = getCircularRevealAnimation(registerCard, registerCard.getWidth() / 2, registerCard.getHeight() / 2, 1f * registerCard.getHeight(), 0F);
        animator.setDuration(500);
        animator.setStartDelay(100);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                loginCard.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                registerCard.setVisibility(View.GONE);
                registerCancel.setScaleX(1F);
                registerCancel.setScaleY(1F);
                registerCancel.setAlpha(1F);
                registerCancel.setRotation(0F);
                registerFab.setVisibility(View.VISIBLE);

                ObjectAnimator animX = ObjectAnimator.ofFloat(registerFab, "scaleX", 0F, 1F);
                ObjectAnimator animY = ObjectAnimator.ofFloat(registerFab, "scaleY", 0F, 1F);
                ObjectAnimator alpha = ObjectAnimator.ofFloat(registerFab, "alpha", 0F, 1F);
                ObjectAnimator rotation = ObjectAnimator.ofFloat(registerFab, "rotation", 90F, 0F);
                AnimatorSet animator = new AnimatorSet();
                animator.playTogether(animX, animY, alpha, rotation);
                animator.setInterpolator(new AccelerateInterpolator());
                animator.setDuration(200);
                animator.start();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.start();
    }

    public View getLoginView() {
        return loginView;
    }

    public View getRegisterView() {
        return registerView;
    }

    class FabAnimation extends Animation {
        private PathMeasure measure;
        private float[] pos;

        public FabAnimation(Path path) {
            measure = new PathMeasure(path, false);
            pos = new float[]{0, 0};
        }

        protected void applyTransformation(float interpolatedTime, Transformation t) {
            measure.getPosTan(measure.getLength() * interpolatedTime, pos, null);
            Matrix matrix = t.getMatrix();
            matrix.setTranslate(pos[0], pos[1]);
            matrix.preRotate(interpolatedTime * 45);
            t.setAlpha(1 - interpolatedTime);
        }
    }
}
