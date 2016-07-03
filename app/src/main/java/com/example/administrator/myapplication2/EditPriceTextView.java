package com.example.administrator.myapplication2;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
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
    private double maxPrice = 0;
    private double minPrice = 0;
    private int radixPointLength = 3;// 小数点位数

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

    public int getRadixPointLength ()
    {
        return radixPointLength;
    }

    public void setRadixPointLength (int radixPointLength)
    {
        this.radixPointLength = radixPointLength;
    }

    public double getMaxPrice ()
    {
        return maxPrice;
    }

    public void setMaxPrice (double maxPrice)
    {
        this.maxPrice = maxPrice;
    }

    public double getMinPrice ()
    {
        return minPrice;
    }

    public void setMinPrice (double minPrice)
    {
        this.minPrice = minPrice;
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
        radixPointLength = 3;
        minPrice = 0;
        maxPrice = 0;
        contentEt.setText ("");
        doCallBack ("0" + "//" + currentPrice);
    }

    public void setAddStepTv (String addStepText)
    {
        addStepTv.setText (addStepText);
    }

    public void setDecreaseStepTv (String decreaseStepText)
    {
        decreaseStepTv.setText (decreaseStepText);
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
                if (decreaseStep == 0 || currentPrice <= minPrice)
                {
                    decreaseStepTv.setText ("--");
                    currentPrice = minPrice;
                    contentEt.setText (String.valueOf (currentPrice));
                    doCallBack (Util.formatDouble3 (currentPrice) + "//" + currentPrice);
                    return;
                }

//                addStepTv.setText (String.valueOf (addStep));
//                decreaseStepTv.setText (String.valueOf (decreaseStep));
                currentPrice -= decreaseStep;
                currentPrice = Util.getFormatPrice (currentPrice);
                contentEt.setText (Util.formatDouble3 (currentPrice));

                if (currentPrice <= minPrice)
                {
                    addStepTv.setText (String.valueOf (addStep));
                    decreaseStepTv.setText ("--");
                }
                else
                {
                    addStepTv.setText (String.valueOf (addStep));
                    decreaseStepTv.setText (String.valueOf (decreaseStep));
                }

                doCallBack (Util.formatDouble3 (currentPrice) + "//" + currentPrice);
                break;
            case R.id.add_layout:
                if (addStep == 0 || currentPrice >= maxPrice)
                {
                    addStepTv.setText ("--");
                    currentPrice = maxPrice;
                    contentEt.setText (String.valueOf (currentPrice));
                    doCallBack (Util.formatDouble3 (currentPrice) + "//" + currentPrice);
                    return;
                }

//                addStepTv.setText (String.valueOf (addStep));
//                decreaseStepTv.setText (String.valueOf (decreaseStep));
                currentPrice += addStep;
                currentPrice = Util.getFormatPrice (currentPrice);
                contentEt.setText (Util.formatDouble3 (currentPrice));

                if (currentPrice >= maxPrice)
                {
                    addStepTv.setText ("--");
                    decreaseStepTv.setText (String.valueOf (decreaseStep));
                }
                else
                {
                    addStepTv.setText (String.valueOf (addStep));
                    decreaseStepTv.setText (String.valueOf (decreaseStep));
                }
                doCallBack (Util.formatDouble3 (currentPrice)+ "//" + currentPrice);
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

            // 删除所有的文本的情况
            if (TextUtils.isEmpty (resStr))
            {
                currentPrice = 0;
            }

            /*// 如果仅仅输入一个"."，而且字符串长度为1
            if (resStr.startsWith (".") && resStr.length () == 1)
            {
                resStr = "0.";
            }

            // 不止一个"."
            if (resStr.contains (".") && resStr.indexOf (".") != resStr.lastIndexOf ("."))
            {
                // 光标不在最后,删除自后一个输入的"."
                if (contentEt.getSelectionStart () != resStr.length ())
                {
                    StringBuilder builder = new StringBuilder (resStr);
                    builder.delete (contentEt.getSelectionStart () - 1,contentEt.getSelectionStart ());
                    resStr = builder.toString ();
                    builder.setLength (0);
                }
                else
                {
                    // 删除最后一个输入的"."
                    resStr = resStr.substring (0, resStr.length () - 1);
                }
            }*/

            // ".1"这种情况
            if (resStr.startsWith (".") && resStr.length () != 1)
            {
                // 光标不在0的位置,需要补零。".1"-->"0.1"
                int start = contentEt.getSelectionStart ();
                int end = contentEt.getSelectionEnd ();
                if (!(start == end && start == 0))
                {
                    resStr = "0" + resStr;
                }
                else
                {
                    // 光标在最前面，证明用户还想输入，所以不要移动光标
                    isSetSelection = false;
                }
            }

            // 如果输入内容包含"."并且小数部分已经超过1位
            if (resStr.contains (".") && ((resStr.length () - 1) - resStr.indexOf (".") > 0))
            {
                resStr = Util.getPrice (resStr,maxPrice, radixPointLength);
            }

            // 如果不包含"."
            if (!resStr.contains ("."))
            {
                resStr = Util.getPrice (resStr,maxPrice, radixPointLength);
            }

            // 如果是089这样的字符串
            if (!resStr.contains (".") && resStr.startsWith ("0") && resStr.length () > 1)
            {
                resStr = resStr.substring (1,resStr.length ());
            }

            // 判断是否已经超过最大值或者最小值
            if (Util.isDouble (resStr))
            {
                currentPrice = Double.parseDouble (resStr);
                if (currentPrice < minPrice)
                {
                    currentPrice = minPrice;
                    decreaseStepTv.setText ("--");
                    resStr = String.valueOf (currentPrice);
                }
                else if (currentPrice == minPrice)
                {
                    decreaseStepTv.setText ("--");
                }
                else
                {
                    if (currentPrice <= maxPrice && currentPrice != minPrice)
                    {
                        decreaseStepTv.setText (String.valueOf (decreaseStep));
                    }
                }

                if (currentPrice > maxPrice)
                {
                    currentPrice = maxPrice;
                    addStepTv.setText ("--");
                    resStr = String.valueOf (currentPrice);
                }
                else if (currentPrice == maxPrice)
                {
                    addStepTv.setText ("--");
                }
                else
                {
                    if (currentPrice >= minPrice && currentPrice != maxPrice)
                    {
                        addStepTv.setText (String.valueOf (addStep));
                    }
                }
            }

            contentEt.setText (resStr);
            if (isSetSelection)
            {
                contentEt.setSelection (resStr.length ());
            }
            isChange = false;
            contentEt.invalidate ();

//            // 更新当前记录的价格
//            if (Util.isDouble (resStr))
//            {
//                currentPrice = Util.getDouble (resStr);
//            }
            doCallBack ((TextUtils.isEmpty (resStr) ? "0" : resStr) + "//" + currentPrice);
        }
    };

    public interface TextChangeListener
    {
        void getText (String resStr);
    }
}
