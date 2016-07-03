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
public class EditPriceTextViewBk extends LinearLayout implements View.OnClickListener
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
    private TextChangeListener listener;

    public EditPriceTextViewBk (Context context)
    {
        super (context);
        findViews (context);
    }

    public EditPriceTextViewBk (Context context, AttributeSet attrs)
    {
        super (context, attrs);
        findViews (context);
    }

    public EditPriceTextViewBk (Context context, AttributeSet attrs, int defStyleAttr)
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
        contentEt.setText ("");
    }

    @Override
    public void onClick (View view)
    {
        switch (view.getId ())
        {
            case R.id.decrease_layout:
                if (decreaseStep == 0 || currentPrice == MIN_PRICE)
                {
                    return;
                }
                currentPrice -= decreaseStep;
                currentPrice = Util.getFormatPrice (currentPrice);
                contentEt.setText (Util.formatDouble3 (currentPrice));
                if (null != listener)
                {
                    listener.getText (Util.formatDouble3 (currentPrice)+ "/" + currentPrice);
                }
                break;
            case R.id.add_layout:
                if (addStep == 0 || currentPrice == MAX_PRICE)
                {
                    return;
                }
                currentPrice += addStep;
                currentPrice = Util.getFormatPrice (currentPrice);
                contentEt.setText (Util.formatDouble3 (currentPrice));
                if (null != listener)
                {
                    listener.getText (Util.formatDouble3 (currentPrice)+ "/" + currentPrice);
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
            if (resStr.startsWith (".") && resStr.length () == 1)
            {
                contentEt.setText ("0.");
                if (null != listener)
                {
                    listener.getText ("0."+ "/" + currentPrice);
                }
                contentEt.setSelection (2);
                if (Util.isDouble (resStr))
                {
                    currentPrice = Util.getFormatPrice (Double.parseDouble (resStr));
                    //TODO
                    listener.getText ("0."+ "/" + currentPrice);
                }
                contentEt.invalidate ();
                isChange = false;
                return;
            }

            if (resStr.startsWith (".") && resStr.length () != 1)
            {
                String temp = "0" + resStr;
                contentEt.setText (temp);
                if (null != listener)
                {
                    listener.getText (temp+ "/" + currentPrice);
                }
                contentEt.setSelection (temp.length ());
                if (Util.isDouble (resStr))
                {
                    currentPrice = Util.getFormatPrice (Double.parseDouble (resStr));
                }
                contentEt.invalidate ();
                isChange = false;
                return;
            }

            if (resStr.indexOf (".") != resStr.lastIndexOf ("."))
            {
                resStr = resStr.substring (0, resStr.length () - 1);
                contentEt.setText (resStr);
                contentEt.setSelection (resStr.length ());
                contentEt.invalidate ();
                isChange = false;
                return;
            }

            if (Util.getDouble (resStr) < 0)
            {
                contentEt.setText ("0");
                contentEt.setSelection (1);
                contentEt.invalidate ();
                isChange = false;
                currentPrice = 0;
                if (null != listener)
                {
                    listener.getText ("0"+ "/" + currentPrice);
                }
                return;
            }

            if (resStr.length () - resStr.indexOf (".") > 4 || Util.getDouble (resStr) > MAX_PRICE)
            {
                resStr = Util.getPrice (resStr,MAX_PRICE,3);
                contentEt.setText (resStr);
                contentEt.setSelection (resStr.length ());
                currentPrice = Util.getDouble (resStr);
                contentEt.invalidate ();
                isChange = false;
                if (null != listener)
                {
                    listener.getText (resStr+ "/" + currentPrice);
                }
                return;
            }
            if (null != listener)
            {
                listener.getText (resStr + "/" + currentPrice);
            }
            if (Util.isDouble (resStr))
            {
                currentPrice = Util.getFormatPrice (Double.parseDouble (resStr));
            }
            contentEt.setSelection (resStr.length ());
            isChange = false;
        }
    };

    public interface TextChangeListener
    {
        void getText (String resStr);
    }
}
