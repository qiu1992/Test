package com.example.administrator.myapplication2;

import android.content.Context;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
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

    private int decreaseStep = 0;
    private int addStep = 0;
    private int maxAmount = 0;
    private int minAmount = 0;

    private boolean isChange = false;// 编辑区域是否正在变化（防止afterTextChanged中递归调用导致的栈溢出）
    private String resStr = "";
    private int currentAmount = 0;
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

    public int getAddStep ()
    {
        return addStep;
    }

    public void setAddStep (int addStep)
    {
        this.addStep = addStep;
        addStepTv.setText (Util.getAmountWithComma (addStep));
    }

    public int getMaxAmount ()
    {
        return maxAmount;
    }

    public void setMaxAmount (int maxAmount)
    {
        this.maxAmount = maxAmount;
    }

    public int getMinAmount ()
    {
        return minAmount;
    }

    public void setMinAmount (int minAmount)
    {
        this.minAmount = minAmount;
    }

    public TextChangeListener getListener ()
    {
        return listener;
    }

    public void setListener (TextChangeListener listener)
    {
        this.listener = listener;
    }

    public int getDecreaseStep ()
    {
        return decreaseStep;
    }

    public void setDecreaseStep (int decreaseStep)
    {
        this.decreaseStep = decreaseStep;
        decreaseStepTv.setText (Util.getAmountWithComma (decreaseStep));
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
                if (decreaseStep == 0 || currentAmount <= minAmount)
                {
                    decreaseStepTv.setText ("--");
                    currentAmount = minAmount;
                    contentEt.setText (Util.getAmountWithComma (currentAmount));
                    contentEt.setSelection (contentEt.getText ().toString ().length ());
                    doCallBack (Util.getAmountWithComma (currentAmount) + "//" + currentAmount);
                    return;
                }

                currentAmount -= decreaseStep;
                contentEt.setText (Util.getAmountWithComma (currentAmount));
                contentEt.setSelection (contentEt.getText ().toString ().length ());

                if (currentAmount <= minAmount)
                {
                    addStepTv.setText (Util.getAmountWithComma (addStep));
                    decreaseStepTv.setText ("--");
                }
                else
                {
                    addStepTv.setText (Util.getAmountWithComma (addStep));
                    decreaseStepTv.setText (Util.getAmountWithComma (decreaseStep));
                }
                break;
            case R.id.add_layout:
                if (addStep == 0 || currentAmount >= maxAmount)
                {
                    addStepTv.setText ("--");
                    currentAmount = maxAmount;
                    contentEt.setText (Util.getAmountWithComma (currentAmount));
                    contentEt.setSelection (contentEt.getText ().toString ().length ());
                    doCallBack (Util.getAmountWithComma (currentAmount) + "//" + currentAmount);
                    return;
                }

                currentAmount += addStep;
                contentEt.setText (Util.getAmountWithComma (currentAmount));
                contentEt.setSelection (contentEt.getText ().toString ().length ());

                if (currentAmount >= maxAmount)
                {
                    addStepTv.setText ("--");
                    decreaseStepTv.setText (Util.getAmountWithComma (decreaseStep));
                }
                else
                {
                    addStepTv.setText (Util.getAmountWithComma (addStep));
                    decreaseStepTv.setText (Util.getAmountWithComma (decreaseStep));
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

            isChange = true;
            resStr = editable.toString ();
//            int lastSelection = contentEt.getSelectionStart ();
//            int lastLength = resStr.length ();

            if (TextUtils.isEmpty (resStr))
            {
                currentAmount = 0;
            }

            if (resStr.startsWith ("0") && resStr.length () > 1)
            {
                resStr = resStr.substring (1, resStr.length ());
            }

            if (!TextUtils.isEmpty (resStr))
            {
                resStr = Util.getFormatAmountStr (resStr, maxAmount);
            }

            if (Util.isInteger (resStr.replace (",","")))
            {
                currentAmount = Integer.parseInt (resStr.replace (",",""));

                if (currentAmount < minAmount)
                {
                    currentAmount = minAmount;
                    decreaseStepTv.setText ("--");
                    resStr = Util.getAmountWithComma (currentAmount);
                }
                else if (currentAmount == minAmount)
                {
                    decreaseStepTv.setText ("--");
                }
                else
                {
                    if (currentAmount <= maxAmount && currentAmount != minAmount)
                    {
                        decreaseStepTv.setText (Util.getAmountWithComma (decreaseStep));
                    }
                }

                if (currentAmount > maxAmount)
                {
                    currentAmount = maxAmount;
                    addStepTv.setText ("--");
                    resStr = Util.getAmountWithComma (currentAmount);
                }
                else if (currentAmount == maxAmount)
                {
                    addStepTv.setText ("--");
                }
                else
                {
                    if (currentAmount >= minAmount && currentAmount != maxAmount)
                    {
                        addStepTv.setText (Util.getAmountWithComma (addStep));
                    }
                }
            }

            contentEt.setText (resStr);
//            if (lastSelection >= 0 && lastSelection <= resStr.length ())
//            {
//                if (lastLength < resStr.length ())
//                {
//                    contentEt.setSelection (lastSelection + 1);
//                }
//                else
//                {
//                    contentEt.setSelection (lastSelection > resStr.length () ? resStr.length () : lastSelection);
//                }
//            }
//            else
//            {
                contentEt.setSelection (resStr.length ());
//            }
            isChange = false;
            contentEt.invalidate ();
            doCallBack ((TextUtils.isEmpty (resStr) ? "0" : resStr) + "//" + currentAmount);
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
