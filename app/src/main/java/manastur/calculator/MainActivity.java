package manastur.calculator;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static java.security.AccessController.getContext;
import static manastur.calculator.R.layout.activity_main;

public class MainActivity extends AppCompatActivity {

    EditText number;
    EditText base;
    boolean baseB = false;
    String numberS = "0";
    String baseS = "10";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_main);


        //make the EditText for number and base not editable
        number = (EditText) findViewById(R.id.number);
        number.setKeyListener(null);
        base = (EditText) findViewById(R.id.base);
        base.setKeyListener(null);


        //changing input number and base font
        number = (EditText) findViewById(R.id.number);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/digital.ttf");
        number.setTypeface(typeface);
        number.setText(numberS, TextView.BufferType.EDITABLE);
        base = (EditText) findViewById(R.id.base);
        base.setTypeface(typeface);
        base.setText(baseS, TextView.BufferType.EDITABLE);

        //changing statusbar color
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimaryDark));
        }
    }


    public void onClickBaseChange(View v) {
        if (baseB) {
            baseB = false;
        } else {
            baseB = true;
        }
    }

    public void onClickB0(View v) {
        if (numberS.length() > 0 && !numberS.equals("0") && !baseB) {
            numberS += "0";
            number = (EditText) findViewById(R.id.number);
            number.setText(numberS, TextView.BufferType.EDITABLE);
            number.setSelection(numberS.length());
        } else {
            if (Integer.valueOf(baseS) >= 1) {
                baseS += "0";
                base = (EditText) findViewById(R.id.base);
                base.setText(baseS, TextView.BufferType.EDITABLE);
            }
        }
    }

    public void onClickB1(View v) {
        if (numberS.equals("0")) {
            numberS = "1";
        } else {
            numberS += "1";
        }
        number = (EditText) findViewById(R.id.number);
        number.setText(numberS, TextView.BufferType.EDITABLE);
        number.requestFocus();
        number.setSelection(numberS.length());
    }

    public void onClickB2(View v) {
        if (numberS.equals("0")) {
            numberS = "2";
        } else {
            numberS += "2";
        }
        number = (EditText) findViewById(R.id.number);
        number.setText(numberS, TextView.BufferType.EDITABLE);
        number.setSelection(numberS.length());
    }

    public void onClickB3(View v) {
        if (numberS.equals("0")) {
            numberS = "3";
        } else {
            numberS += "3";
        }
        number = (EditText) findViewById(R.id.number);
        number.setText(numberS, TextView.BufferType.EDITABLE);
        number.setSelection(numberS.length());
    }

    public void onClickB4(View v) {
        if (numberS.equals("0")) {
            numberS = "4";
        } else {
            numberS += "4";
        }
        number = (EditText) findViewById(R.id.number);
        number.setText(numberS, TextView.BufferType.EDITABLE);
        number.setSelection(numberS.length());
    }

    public void onClickB5(View v) {
        if (numberS.equals("0")) {
            numberS = "5";
        } else {
            numberS += "5";
        }
        number = (EditText) findViewById(R.id.number);
        number.setText(numberS, TextView.BufferType.EDITABLE);
        number.setSelection(numberS.length());
    }

    public void onClickB6(View v) {
        if (numberS.equals("0")) {
            numberS = "6";
        } else {
            numberS += "6";
        }
        number = (EditText) findViewById(R.id.number);
        number.setText(numberS, TextView.BufferType.EDITABLE);
        number.setSelection(numberS.length());
    }

    public void onClickB7(View v) {
        if (numberS.equals("0")) {
            numberS = "7";
        } else {
            numberS += "7";
        }
        number = (EditText) findViewById(R.id.number);
        number.setText(numberS, TextView.BufferType.EDITABLE);
        number.setSelection(numberS.length());
    }

    public void onClickB8(View v) {
        if (numberS.equals("0")) {
            numberS = "8";
        } else {
            numberS += "8";
        }
        number = (EditText) findViewById(R.id.number);
        number.setText(numberS, TextView.BufferType.EDITABLE);
        number.setSelection(numberS.length());
    }

    public void onClickB9(View v) {
        if (numberS.equals("0")) {
            numberS = "9";
        } else {
            numberS += "9";
        }
        number = (EditText) findViewById(R.id.number);
        number.setText(numberS, TextView.BufferType.EDITABLE);
        number.setSelection(numberS.length());
    }

    public void onClickBDel(View v) {
        if (!baseB) {
            if (numberS.length() > 1) {
                StringBuilder sb = new StringBuilder(numberS);
                sb.deleteCharAt(numberS.length() - 1);
                numberS = sb.toString();
                number = (EditText) findViewById(R.id.number);
                number.setText(numberS, TextView.BufferType.EDITABLE);
                number.setSelection(numberS.length());
            } else {
                if (numberS.length() == 1) {
                    numberS = "0";
                    number = (EditText) findViewById(R.id.number);
                    number.setText(numberS, TextView.BufferType.EDITABLE);
                    number.setSelection(numberS.length());
                }
            }
        } else {
            if (baseS.length() > 1) {
                StringBuilder sb = new StringBuilder(baseS);
                sb.deleteCharAt(baseS.length() - 1);
                baseS = sb.toString();
                base = (EditText) findViewById(R.id.base);
                base.setText(baseS, TextView.BufferType.EDITABLE);
                base.setSelection(baseS.length());
            } else {
                if (baseS.length() == 1) {
                    baseS = "0";
                    base = (EditText) findViewById(R.id.base);
                    base.setText(baseS, TextView.BufferType.EDITABLE);
                    base.setSelection(baseS.length());
                }
            }
        }
    }
}
