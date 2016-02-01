package com.base.basedialog;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class BaseDialog extends DialogFragment {
    private Button mbtnOk;
    private Button mbtnCancel;
    private LinearLayout linear_background;
    private TextView text_title, text_content;
    private ImageView image_icon;

    private String title, content, ok, cancel;
    private int titleColor, contentColor, icon, LinearBackground, okBackground, okColor, cancelBackground, cancelColor;

    private int type;

    private static BaseDialog baseDialog;
    private BaseDialogListener mBaseDialogListener;
    private boolean hasTitle, hasContent, hasIcon, hasBackground, hasButtonOk, hasButtonCancel;

    public static interface BaseDialogListener {
        public void ok(int type, int resultCode);

        public void cancel(int type, int resultCode);
    }

    public static BaseDialog getInstance(int type, BaseDialogListener listener) {
        if (baseDialog == null) {
            baseDialog = new BaseDialog();
        }
        baseDialog.type = type;
        baseDialog.mBaseDialogListener = listener;
        return baseDialog;
    }

    public BaseDialog() {
    }

    @SuppressLint("InflateParams")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().setCanceledOnTouchOutside(false);
        setCancelable(true);
        // 设置背景透明，否则在对话框的后面会有白色背景
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        final View viewInterface = inflater.inflate(R.layout.base_dialog_1, null);

        initView(viewInterface);
        setUpView();
        setLisenter(viewInterface);

        setEnterAnimation(viewInterface);
        Log.v("xf", "oncreate结果");
        return viewInterface;
    }

    private void setLisenter(final View viewInterface) {
        mbtnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setExitAnimation(viewInterface);
                if (mBaseDialogListener != null) {
                    mBaseDialogListener.ok(type, Activity.RESULT_OK);
                }
            }
        });
        if (mbtnCancel != null) {
            mbtnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mBaseDialogListener != null) {
                        mBaseDialogListener.cancel(type, Activity.RESULT_OK);
                    }
                }
            });
        }
    }

    private void initView(View viewInterface) {
        linear_background = (LinearLayout) viewInterface.findViewById(R.id.linear_background);
        image_icon = (ImageView) viewInterface.findViewById(R.id.image_icon);
        text_title = (TextView) viewInterface.findViewById(R.id.text_title);
        text_content = (TextView) viewInterface.findViewById(R.id.text_content);
        mbtnOk = (Button) viewInterface.findViewById(R.id.button_ok);
        if (type == 2) {
            mbtnCancel = (Button) viewInterface.findViewById(R.id.button_cancel);
        }
    }

    private void setUpView() {
        if (hasTitle) {
            text_title.setText(title);
            text_title.setTextColor(titleColor);
        }
        if (hasContent) {
            text_content.setText(content);
            text_content.setTextColor(contentColor);
        }
        if (hasIcon) {
            image_icon.setImageResource(icon);
        }
        if (hasBackground) {
            GradientDrawable myGrad = (GradientDrawable)linear_background.getBackground();
            myGrad.setColor(LinearBackground);
        }
        if (hasButtonOk) {
            mbtnOk.setBackgroundColor(okBackground);
            mbtnOk.setText(ok);
            mbtnOk.setTextColor(okColor);
        }
        if (hasButtonCancel) {
            if (type == 1) {
                mbtnCancel.setBackgroundColor(cancelBackground);
                mbtnCancel.setText(cancel);
                mbtnCancel.setTextColor(cancelColor);
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        // 修改对话框的大小要在此事件中，不然有些机型会显示异常
        getDialog().getWindow().setLayout(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    }


    private void setEnterAnimation(View view) {
        PropertyValuesHolder propertyValuesHolder1 = PropertyValuesHolder.ofFloat("translationY", -2000, 0);
        PropertyValuesHolder propertyValuesHolder2 = PropertyValuesHolder.ofFloat("rotation", -20, 0);
        ObjectAnimator animator1 = ObjectAnimator.
                ofPropertyValuesHolder(view, propertyValuesHolder1,
                        propertyValuesHolder2);
//        animator1.setInterpolator(new AccelerateInterpolator());
        animator1.setDuration(400);
//        animator1.start();

        PropertyValuesHolder propertyValuesHolder3 = PropertyValuesHolder.ofFloat("translationY", 0, -15);
        ObjectAnimator animator2 = ObjectAnimator.
                ofPropertyValuesHolder(view, propertyValuesHolder3);
        //   animator2.setInterpolator(new DecelerateInterpolator());
        animator2.setDuration(200);


        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(animator2).after(animator1);
        animatorSet.setInterpolator(new DecelerateInterpolator());
//        animatorSet.setDuration(800);
        animatorSet.start();
    }

    private void setExitAnimation(View view) {
        PropertyValuesHolder propertyValuesHolder1 = PropertyValuesHolder.ofFloat("translationY", 0, 2000);
        PropertyValuesHolder propertyValuesHolder2 = PropertyValuesHolder.ofFloat("rotation", 0, 10);
        ObjectAnimator animator1 = ObjectAnimator.
                ofPropertyValuesHolder(view, propertyValuesHolder1,
                        propertyValuesHolder2);
//        animator1.setInterpolator(new AccelerateInterpolator());
        animator1.setDuration(400);
//        animator1.start();

//		PropertyValuesHolder propertyValuesHolder3 = PropertyValuesHolder.ofFloat("translationY",0,-10);
//		ObjectAnimator animator2 = ObjectAnimator.
//				ofPropertyValuesHolder(view,propertyValuesHolder3);
//		//   animator2.setInterpolator(new DecelerateInterpolator());
//		animator2.setDuration(200);


        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(animator1);
//        animatorSet.setDuration(800);
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                dismiss();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animatorSet.start();
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        if (mBaseDialogListener != null) {
            mBaseDialogListener.cancel(type, Activity.RESULT_CANCELED);
        }
        super.onCancel(dialog);
    }

    public BaseDialog bulidTitle(String title, int textColor) {
        this.title = title;
        this.titleColor = textColor;
        hasTitle = true;
        return baseDialog;
    }


    public BaseDialog bulidContent(String content, int textColor) {
        this.content = content;
        this.contentColor = textColor;
        hasContent = true;
        return baseDialog;
    }

    public BaseDialog bulidIcon(int imageRes) {
        icon = imageRes;
        hasIcon = true;
        return baseDialog;
    }

    public BaseDialog bulidBackground(int color) {
        LinearBackground = color;
        hasBackground = true;
        return baseDialog;
    }

    public BaseDialog bulidButtonOk(int backgroundColor, String text, int textColor) {
        ok = text;
        okBackground = backgroundColor;
        okColor = textColor;
        hasButtonOk = true;
        return baseDialog;
    }

    public BaseDialog bulidButtonCancel(int backgroundColor, String text, int textColor) {
        cancel = text;
        cancelBackground = backgroundColor;
        cancelColor = textColor;
        hasButtonCancel = true;
        return baseDialog;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        hasTitle = false;
        hasContent = false;
        hasIcon = false;
        hasBackground = false;
        hasButtonCancel = false;
        hasButtonOk = false;
    }
}
