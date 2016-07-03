package com.example.administrator.myapplication2;

import android.content.Context;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by qiu on 2016/7/3 0003.
 */
public class EditAmountTextView extends LinearLayout implements View.OnClickListener
{
    private LinearLayout decreaseLayout;// 左边减号布局
    private TextView decreaseStepTv;// 每次减少的步长
    private EditText contentEt;// 编辑区域
    private LinearLayout addLayout;// 右边加号布局
    private TextView addStepTv;// 每次增加的步长

    private double decreaseStep = 0;
    private double addStep = 0;
    private double maxAmount = 0;
    private double minAmount = 0;

    private boolean isChange = false;// 编辑区域是否正在变化（防止afterTextChanged中递归调用导致的栈溢出）
    private String resStr = "";
    private double currentAmount = 0;
    private boolean isSetSelection = true;
    private TextChangeListener listener;

    public EditAmountTextView (Context context)
    {
        super (context);
        findViews (context);
    }

    public EditAmountTextView (Context context, AttributeSet attrs)
    {
        super (context, attrs);
        findViews (context);
    }

    public EditAmountTextView (Context context, AttributeSet attrs, int defStyleAttr)
    {
        super (context, attrs, defStyleAttr);
        findViews (context);
    }

    private void findViews (Context context)
    {
        View layout = LayoutInflater.from (context).inflate (R.layout.edit_price_layout, this);
        decreaseLayout = (LinearLayout) layout.findViewById (R.id.decrease_layout);
        decreaseStepTv = (TextView) layout.findViewById (R.id.decrease_step_tv);
        contentEt = (EditText) layout.findViewById (R.id.content_et);
        contentEt.setInputType (InputType.TYPE_CLASS_NUMBER);
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
        currentAmount = 0;
        minAmount = 0;
        maxAmount = 0;
        contentEt.setText ("");
        doCallBack ("0" + "//" + currentAmount);
    }

    @Override
    public void onClick (View view)
    {
        switch (view.getId ())
        {
            case R.id.decrease_layout:
                break;
            case R.id.add_layout:
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
            // TODO
            // 处理过程
            isChange = false;
            contentEt.invalidate ();
            doCallBack ("");
        }
    };

    private void doCallBack (String res)
    {
        if (null != listener)
        {
            listener.getText (res);
        }
    }

    public interface TextChangeListener
    {
        void getText (String resStr);
    }
}
