package com.example.administrator.myapplication2;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Administrator on 2016-7-1.
 */
public class EditPriceTextView extends LinearLayout implements View.OnClickListener
{
    private LinearLayout decreaseLayout;// 左边减号布局
    private TextView decreaseStepTv;// 每次减少的步长
    private EditText contentEt;// 编辑区域
    private LinearLayout addLayout;// 右边加号布局
    private TextView addStepTv;// 每次增加的步长

    private double decreaseStep = 0;
    private double addStep = 0;
    private final double MAX_PRICE = 999999;
    private final double MIN_PRICE = 0;

    private boolean isChange = false;// 编辑区域是否正在变化（防止afterTextChanged中递归调用导致的栈溢出）
    private String resStr = "";
    private double currentPrice = 0;
    private boolean isSetSelection = true;
    private TextChangeListener listener;

    public EditPriceTextView (Context context)
    {
        super (context);
        findViews (context);
    }

    public EditPriceTextView (Context context, AttributeSet attrs)
    {
        super (context, attrs);
        findViews (context);
    }

    public EditPriceTextView (Context context, AttributeSet attrs, int defStyleAttr)
    {
        super (context, attrs, defStyleAttr);
        findViews (context);
    }

    public double getDecreaseStep ()
    {
        return decreaseStep;
    }

    public void setDecreaseStep (double decreaseStep)
    {
        this.decreaseStep = decreaseStep;
        decreaseStepTv.setText (String.valueOf (decreaseStep));
    }

    public double getAddStep ()
    {
        return addStep;
    }

    public void setAddStep (double addStep)
    {
        this.addStep = addStep;
        addStepTv.setText (String.valueOf (addStep));
    }

    public TextChangeListener getListener ()
    {
        return listener;
    }

    public void setListener (TextChangeListener listener)
    {
        this.listener = listener;
    }

    private void findViews (Context context)
    {
        View layout = LayoutInflater.from (context).inflate (R.layout.edit_price_layout, this);
        decreaseLayout = (LinearLayout) layout.findViewById (R.id.decrease_layout);
        decreaseStepTv = (TextView) layout.findViewById (R.id.decrease_step_tv);
        contentEt = (EditText) layout.findViewById (R.id.content_et);
        addLayout = (LinearLayout) layout.findViewById (R.id.add_layout);
        addStepTv = (TextView) layout.findViewById (R.id.add_step_tv);

        reset ();

        decreaseLayout.setOnClickListener (this);
        addLayout.setOnClickListener (this);
        contentEt.addTextChangedListener (textWatcher);
    }

    public void reset ()
    {
        decreaseStepTv.setText ("--");
        addStepTv.setText ("--");
        addStep = 0;
        decreaseStep = 0;
        currentPrice = 0;
        contentEt.setText ("");
        doCallBack ("0");
    }

    private void doCallBack (String res)
    {
        if (null != listener)
        {
            listener.getText (res);
        }
    }

    @Override
    public void onClick (View view)
    {
        switch (view.getId ())
        {
            case R.id.decrease_layout:
                listener.getText (contentEt.getSelectionStart () + "//" + contentEt.getSelectionEnd ());
                if (decreaseStep == 0 || currentPrice == MIN_PRICE)
                {
                    return;
                }
                break;
            case R.id.add_layout:
                if (addStep == 0 || currentPrice == MAX_PRICE)
                {
                    return;
                }
                break;
        }
    }

    private TextWatcher textWatcher = new TextWatcher ()
    {
        @Override
        public void beforeTextChanged (CharSequence charSequence, int i, int i1, int i2)
        {
            if (isChange)
            {
                return;
            }
        }

        @Override
        public void onTextChanged (CharSequence charSequence, int i, int i1, int i2)
        {
            if (isChange)
            {
                return;
            }
        }

        @Override
        public void afterTextChanged (Editable editable)
        {

            if (isChange)
            {
                return;
            }

            isSetSelection = true;
            isChange = true;
            resStr = editable.toString ();

            // 如果仅仅输入一个"."
            if (resStr.startsWith (".") && resStr.length () == 1)
            {
                resStr = "0.";
            }

            // 不止一个"."
            if (resStr.contains (".") && resStr.indexOf (".") != resStr.lastIndexOf ("."))
            {
                if (contentEt.getSelectionStart () != resStr.length ())
                {}
                else
                {
                    resStr = resStr.substring (0, resStr.length () - 1);
                }
            }

            // ".1"这种情况，而且光标不在0的位置
            if (resStr.startsWith (".") && resStr.length () != 1)
            {
                int start = contentEt.getSelectionStart ();
                int end = contentEt.getSelectionEnd ();
                if (!(start == end && start == 0))
                {
                    resStr = "0" + resStr;
                }
                else
                {
                    isSetSelection = false;
                }
            }

            // 如果输入内容包含"."并且小数部分已经超过1位
//            if (resStr.contains (".") && ((resStr.length () - 1) - resStr.indexOf (".") > 3))
            if (resStr.contains (".") && ((resStr.length () - 1) - resStr.indexOf (".") > 0))
            {
                resStr = Util.getPrice (resStr);
            }

            // 如果包含"."
            if (!resStr.contains ("."))
            {
                resStr = Util.getPrice (resStr);
            }

            contentEt.setText (resStr);
            if (isSetSelection)
            {
                contentEt.setSelection (resStr.length ());
            }
            contentEt.invalidate ();
            doCallBack (resStr);
            isChange = false;
        }
    };

    public interface TextChangeListener
    {
        void getText (String resStr);
    }
}
