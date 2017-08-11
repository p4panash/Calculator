package manastur.calculator;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewDebug;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import static java.security.AccessController.getContext;
import static manastur.calculator.R.layout.activity_main;

public class MainActivity extends AppCompatActivity {

    EditText number;
    EditText base;
    boolean convert = true;
    boolean baseB = false;
    String numberS = "0";
    String baseS = "10";
    String primeBase = "0";
    String ops = "";
    List<String> op = new ArrayList<String>();
    String sign = "";
    float dp;
    Integer lenght;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DisplayMetrics metrics = this.getResources().getDisplayMetrics();
        setContentView(activity_main);

        //sets buttons dimensions for xxhdpi <-> hdpi
        if (metrics.densityDpi > 160 && metrics.densityDpi < 320) {
            dp = 40f;
            lenght = 13;
            int height = (int) getResources().getDimension(R.dimen.height_hdpi);
            int text = (int) getResources().getDimension(R.dimen.text);
            overrideSize(this, this.findViewById(android.R.id.content).getRootView(), height, text);
        }
        if (metrics.densityDpi > 300 && metrics.densityDpi < 500) {
            dp = 50f;
            lenght = 13;
            int height = (int) getResources().getDimension(R.dimen.height);
            int text = (int) getResources().getDimension(R.dimen.text_xxhdpi);
            overrideSize(this, this.findViewById(android.R.id.content).getRootView(), height, text);
            LinearLayout ly = (LinearLayout)findViewById(R.id.firstRow);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins((int) getResources().getDimension(R.dimen.left_margin), 0, 0, 0);
            ly.setLayoutParams(lp);
            ly = (LinearLayout)findViewById(R.id.secondRow);
            ly.setLayoutParams(lp);
            ly = (LinearLayout)findViewById(R.id.thirdRow);
            ly.setLayoutParams(lp);
            ly = (LinearLayout)findViewById(R.id.forthRow);
            ly.setLayoutParams(lp);
            ly = (LinearLayout)findViewById(R.id.fifthRow);
            ly.setLayoutParams(lp);
        }


        //init EditTexts
        number = (EditText) findViewById(R.id.number);
        base = (EditText) findViewById(R.id.base);
        number.setFocusable(true);
        //number.setCursorVisible(true);
        base.setCursorVisible(false);


        //make the EditText for number and base not editable
        number.setTextIsSelectable(false);

        number.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                onClickEditText();
            }
        });
        number.setOnLongClickListener(new View.OnLongClickListener(){
            @Override
            public boolean onLongClick(View v) {
                onClickEditText();
                return true;
            }
        });

        base.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                onClickEditText();
            }
        });
        base.setOnLongClickListener(new View.OnLongClickListener(){
            @Override
            public boolean onLongClick(View v) {
                onClickEditText();
                return true;
            }
        });


        //changing input number and base font
        number = (EditText) findViewById(R.id.number);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/digital.ttf");
        number.setTypeface(typeface);
        number.setText(numberS, TextView.BufferType.EDITABLE);
        overrideFonts(this, this.findViewById(android.R.id.content).getRootView());
        base = (EditText) findViewById(R.id.base);
        base.setTypeface(typeface);
        base.setText(baseS, TextView.BufferType.EDITABLE);
        float fpixels = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, metrics);
        int pixels = Math.round(fpixels);
        number.setTextSize(TypedValue.COMPLEX_UNIT_PX, pixels);

        //changing statusbar color
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimaryDark));
        }

        //convert on long click
        Button eq = (Button) findViewById(R.id.bEQ);
        eq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (convert) {
                    if (Integer.valueOf(baseS) >= 2 && Integer.valueOf(baseS) <= 16) {
                        convert = false;
                        number.setInputType(InputType.TYPE_CLASS_TEXT);
                        String convertedNumber = Operations.ConvertToBase(numberS, Integer.valueOf(primeBase),
                                Integer.valueOf(baseS));
                        number.setText(convertedNumber, TextView.BufferType.EDITABLE);
                        numberS = convertedNumber;
                        baseB = false;
                        base.setCursorVisible(false);
                        number.setCursorVisible(true);
                    } else {
                        Toast.makeText(MainActivity.this, "Invalid base !", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        eq.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                number.setInputType(InputType.TYPE_NULL);
                number.setText("BASE :", TextView.BufferType.EDITABLE);
                baseB = true;
                convert = true;
                primeBase = baseS;
                base.setCursorVisible(true);
                return true;
            }
        });

    }

    public void onClickEditText() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public void updateTextSize() {
        DisplayMetrics metrics = this.getResources().getDisplayMetrics();
        EditText number = (EditText) findViewById(R.id.number);
        float d;
        if (number.getText().length() > lenght){
            if (dp == 40f) {
                d = 25f;
            } else {
                d = 35f;
            }
            float fpixels = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, d, metrics);
            int pixels = Math.round(fpixels);
            number.setTextSize(TypedValue.COMPLEX_UNIT_PX, pixels);
        }
        if (number.getText().length() <= lenght) {
            if (dp == 40f) {
                d = 40f;
            } else {
                d = 50f;
            }
            float fpixels = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, d, metrics);
            int pixels = Math.round(fpixels);
            number.setTextSize(TypedValue.COMPLEX_UNIT_PX, pixels);
        }
    }

    private void overrideSize(final Context context, final View v, int height, int text) {
        try {
            if (v instanceof ViewGroup) {
                ViewGroup vg = (ViewGroup) v;
                for (int i = 0; i < vg.getChildCount(); i++) {
                    View child = vg.getChildAt(i);
                    overrideSize(context, child, height, text);
                }
            } else if (v instanceof Button) {
                ((Button) v).setTextSize(TypedValue.COMPLEX_UNIT_SP, text);
                ((Button) v).setLayoutParams(new LinearLayout.LayoutParams(0, height, 1f));
            }
        } catch (Exception e) {
        }
    }

    private void overrideFonts(final Context context, final View v) {
        try {
            if (v instanceof ViewGroup) {
                ViewGroup vg = (ViewGroup) v;
                for (int i = 0; i < vg.getChildCount(); i++) {
                    View child = vg.getChildAt(i);
                    overrideFonts(context, child);
                }
            } else if (v instanceof Button) {
                ((Button) v).setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/digital.ttf"));
            }
        } catch (Exception e) {
        }
    }

    public void onClickBaseChange(View v) {
        if (baseB) {
            if (Integer.valueOf(baseS) >= 2 && Integer.valueOf(baseS) <= 16) {
                baseB = false;
                number = (EditText) findViewById(R.id.number);
                base = (EditText) findViewById(R.id.base);
                number.setCursorVisible(true);
                base.setCursorVisible(false);
                number.setSelection(numberS.length());
                Toast.makeText(this, "Number", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Invalid base !", Toast.LENGTH_SHORT).show();
            }
        } else {
            baseB = true;
            base = (EditText) findViewById(R.id.base);
            number = (EditText) findViewById(R.id.number);
            number.setCursorVisible(false);
            base.setCursorVisible(true);
            base.setSelection(baseS.length());
            Toast.makeText(this, "Base", Toast.LENGTH_SHORT).show();
        }
    }

    public void onClickB0(View v) {
        if (numberS.length() > 0 && !numberS.equals("0") && !baseB) {
            numberS += "0";
            number = (EditText) findViewById(R.id.number);
            number.setText(ops + numberS, TextView.BufferType.EDITABLE);
            number.setSelection(numberS.length());
        } else {
            if (Integer.valueOf(baseS) >= 1) {
                baseS += "0";
                base = (EditText) findViewById(R.id.base);
                base.setText(baseS, TextView.BufferType.EDITABLE);
            }
        }
        updateTextSize();
    }

    public void onClickB1(View v) {
        if (numberS.length() > 0 && !numberS.equals("0") && !baseB) {
            numberS += "1";
            number = (EditText) findViewById(R.id.number);
            number.setText(ops + numberS, TextView.BufferType.EDITABLE);
            number.setSelection(numberS.length());
            number.setCursorVisible(true);
        } else {
            if (numberS.equals("0") && !baseB) {
                numberS = "1";
                number = (EditText) findViewById(R.id.number);
                number.setText(ops + numberS, TextView.BufferType.EDITABLE);
                number.setSelection(numberS.length());
            } else {
                if (baseS.length() > 0 && !baseS.equals("0") && baseB) {
                    baseS += "1";
                    base = (EditText) findViewById(R.id.base);
                    base.setText(baseS, TextView.BufferType.EDITABLE);
                    base.setSelection(baseS.length());
                } else {
                    if (baseS.equals("0") && baseB) {
                        baseS = "1";
                        base = (EditText) findViewById(R.id.base);
                        base.setText(baseS, TextView.BufferType.EDITABLE);
                        base.setSelection(baseS.length());
                    }
                }
            }
        }
        updateTextSize();
    }

    public void onClickB2(View v) {
        if (numberS.length() > 0 && !numberS.equals("0") && !baseB) {
            numberS += "2";
            number = (EditText) findViewById(R.id.number);
            number.setText(ops + numberS, TextView.BufferType.EDITABLE);
            number.setSelection(numberS.length());
        } else {
            if (numberS.equals("0") && !baseB) {
                numberS = "2";
                number = (EditText) findViewById(R.id.number);
                number.setText(ops + numberS, TextView.BufferType.EDITABLE);
                number.setSelection(numberS.length());
            } else {
                if (baseS.length() > 0 && !baseS.equals("0") && baseB) {
                    baseS += "2";
                    base = (EditText) findViewById(R.id.base);
                    base.setText(baseS, TextView.BufferType.EDITABLE);
                    base.setSelection(baseS.length());
                } else {
                    if (baseS.equals("0") && baseB) {
                        baseS = "2";
                        base = (EditText) findViewById(R.id.base);
                        base.setText(baseS, TextView.BufferType.EDITABLE);
                        base.setSelection(baseS.length());
                    }
                }
            }
        }
        updateTextSize();
    }

    public void onClickB3(View v) {
        if (numberS.length() > 0 && !numberS.equals("0") && !baseB) {
            numberS += "3";
            number = (EditText) findViewById(R.id.number);
            number.setText(ops + numberS, TextView.BufferType.EDITABLE);
            number.setSelection(numberS.length());
        } else {
            if (numberS.equals("0") && !baseB) {
                numberS = "3";
                number = (EditText) findViewById(R.id.number);
                number.setText(ops + numberS, TextView.BufferType.EDITABLE);
                number.setSelection(numberS.length());
            } else {
                if (baseS.length() > 0 && !baseS.equals("0") && baseB) {
                    baseS += "3";
                    base = (EditText) findViewById(R.id.base);
                    base.setText(baseS, TextView.BufferType.EDITABLE);
                    base.setSelection(baseS.length());
                } else {
                    if (baseS.equals("0") && baseB) {
                        baseS = "3";
                        base = (EditText) findViewById(R.id.base);
                        base.setText(baseS, TextView.BufferType.EDITABLE);
                        base.setSelection(baseS.length());
                    }
                }
            }
        }
        updateTextSize();
    }

    public void onClickB4(View v) {
        if (numberS.length() > 0 && !numberS.equals("0") && !baseB) {
            numberS += "4";
            number = (EditText) findViewById(R.id.number);
            number.setText(ops + numberS, TextView.BufferType.EDITABLE);
            number.setSelection(numberS.length());
        } else {
            if (numberS.equals("0") && !baseB) {
                numberS = "4";
                number = (EditText) findViewById(R.id.number);
                number.setText(ops + numberS, TextView.BufferType.EDITABLE);
            } else {
                if (baseS.length() > 0 && !baseS.equals("0") && baseB) {
                    baseS += "4";
                    base = (EditText) findViewById(R.id.base);
                    base.setText(baseS, TextView.BufferType.EDITABLE);
                    base.setSelection(baseS.length());
                } else {
                    if (baseS.equals("0") && baseB) {
                        baseS = "4";
                        base = (EditText) findViewById(R.id.base);
                        base.setText(baseS, TextView.BufferType.EDITABLE);
                    }
                }
            }
        }
        updateTextSize();
    }

    public void onClickB5(View v) {
        if (numberS.length() > 0 && !numberS.equals("0") && !baseB) {
            numberS += "5";
            number = (EditText) findViewById(R.id.number);
            number.setText(ops + numberS, TextView.BufferType.EDITABLE);
            number.setSelection(numberS.length());
        } else {
            if (numberS.equals("0") && !baseB) {
                numberS = "5";
                number = (EditText) findViewById(R.id.number);
                number.setText(ops + numberS, TextView.BufferType.EDITABLE);
                number.setSelection(numberS.length());
            } else {
                if (baseS.length() > 0 && !baseS.equals("0") && baseB) {
                    baseS += "5";
                    base = (EditText) findViewById(R.id.base);
                    base.setText(baseS, TextView.BufferType.EDITABLE);
                    base.setSelection(baseS.length());
                } else {
                    if (baseS.equals("0") && baseB) {
                        baseS = "5";
                        base = (EditText) findViewById(R.id.base);
                        base.setText(baseS, TextView.BufferType.EDITABLE);
                        base.setSelection(baseS.length());
                    }
                }
            }
        }
    }

    public void onClickB6(View v) {
        if (numberS.length() > 0 && !numberS.equals("0") && !baseB) {
            numberS += "6";
            number = (EditText) findViewById(R.id.number);
            number.setText(ops + numberS, TextView.BufferType.EDITABLE);
            number.setSelection(numberS.length());
        } else {
            if (numberS.equals("0") && !baseB) {
                numberS = "6";
                number = (EditText) findViewById(R.id.number);
                number.setText(ops + numberS, TextView.BufferType.EDITABLE);
                number.setSelection(numberS.length());
            } else {
                if (baseS.length() > 0 && !baseS.equals("0") && baseB) {
                    baseS += "6";
                    base = (EditText) findViewById(R.id.base);
                    base.setText(baseS, TextView.BufferType.EDITABLE);
                    base.setSelection(baseS.length());
                } else {
                    if (baseS.equals("0") && baseB) {
                        baseS = "6";
                        base = (EditText) findViewById(R.id.base);
                        base.setText(baseS, TextView.BufferType.EDITABLE);
                        base.setSelection(baseS.length());
                    }
                }
            }
        }
    }

    public void onClickB7(View v) {
        if (numberS.length() > 0 && !numberS.equals("0") && !baseB) {
            numberS += "7";
            number = (EditText) findViewById(R.id.number);
            number.setText(ops + numberS, TextView.BufferType.EDITABLE);
            number.setSelection(numberS.length());
        } else {
            if (numberS.equals("0") && !baseB) {
                numberS = "7";
                number = (EditText) findViewById(R.id.number);
                number.setText(ops + numberS, TextView.BufferType.EDITABLE);
                number.setSelection(numberS.length());
            } else {
                if (baseS.length() > 0 && !baseS.equals("0") && baseB) {
                    baseS += "7";
                    base = (EditText) findViewById(R.id.base);
                    base.setText(baseS, TextView.BufferType.EDITABLE);
                    base.setSelection(baseS.length());
                } else {
                    if (baseS.equals("0") && baseB) {
                        baseS = "7";
                        base = (EditText) findViewById(R.id.base);
                        base.setText(baseS, TextView.BufferType.EDITABLE);
                        base.setSelection(baseS.length());
                    }
                }
            }
        }
        updateTextSize();
    }

    public void onClickB8(View v) {
        if (numberS.length() > 0 && !numberS.equals("0") && !baseB) {
            numberS += "8";
            number = (EditText) findViewById(R.id.number);
            number.setText(ops + numberS, TextView.BufferType.EDITABLE);
            number.setSelection(numberS.length());
        } else {
            if (numberS.equals("0") && !baseB) {
                numberS = "8";
                number = (EditText) findViewById(R.id.number);
                number.setText(ops + numberS, TextView.BufferType.EDITABLE);
                number.setSelection(numberS.length());
            } else {
                if (baseS.length() > 0 && !baseS.equals("0") && baseB) {
                    baseS += "8";
                    base = (EditText) findViewById(R.id.base);
                    base.setText(baseS, TextView.BufferType.EDITABLE);
                    base.setSelection(baseS.length());
                } else {
                    if (baseS.equals("0") && baseB) {
                        baseS = "8";
                        base = (EditText) findViewById(R.id.base);
                        base.setText(baseS, TextView.BufferType.EDITABLE);
                        base.setSelection(baseS.length());
                    }
                }
            }
        }
        updateTextSize();
    }

    public void onClickB9(View v) {
        if (numberS.length() > 0 && !numberS.equals("0") && !baseB) {
            numberS += "9";
            number = (EditText) findViewById(R.id.number);
            number.setText(ops + numberS, TextView.BufferType.EDITABLE);
            number.setSelection(numberS.length());
        } else {
            if (numberS.equals("0") && !baseB) {
                numberS = "9";
                number = (EditText) findViewById(R.id.number);
                number.setText(ops + numberS, TextView.BufferType.EDITABLE);
                number.setSelection(numberS.length());
            } else {
                if (baseS.length() > 0 && !baseS.equals("0") && baseB) {
                    baseS += "9";
                    base = (EditText) findViewById(R.id.base);
                    base.setText(baseS, TextView.BufferType.EDITABLE);
                    base.setSelection(baseS.length());
                } else {
                    if (baseS.equals("0") && baseB) {
                        baseS = "9";
                        base = (EditText) findViewById(R.id.base);
                        base.setText(baseS, TextView.BufferType.EDITABLE);
                        base.setSelection(baseS.length());
                    }
                }
            }
        }
        updateTextSize();
    }

    public void onClickBDel(View v) {
        if (!baseB) {
            if (numberS.length() > 1) {
                StringBuilder sb = new StringBuilder(numberS);
                sb.deleteCharAt(numberS.length() - 1);
                numberS = sb.toString();
                number = (EditText) findViewById(R.id.number);
                number.setText(ops + numberS, TextView.BufferType.EDITABLE);
                number.setSelection(numberS.length());
            } else {
                if (numberS.length() == 1) {
                    if (numberS.equals("0")) {
                        if (op.size() >= 3) {
                            Integer l = 1;
                            baseS = op.get(op.size() - 2);
                            base = (EditText) findViewById(R.id.base);
                            l = l + baseS.length() + 3;
                            base.setText(baseS, TextView.BufferType.EDITABLE);
                            numberS = op.get(op.size() - 3);
                            l = l + numberS.length();
                            StringBuilder sb = new StringBuilder(ops);
                            for (int i = 1; i <= l; i++)
                                sb.deleteCharAt(sb.length() - 1);
                            op.remove(op.size() - 1);
                            op.remove(op.size() - 1);
                            op.remove(op.size() - 1);
                            if (!sb.toString().equals("")) {
                                ops = sb.toString();
                            } else {
                                ops = "";
                            }
                            number = (EditText) findViewById(R.id.number);
                            number.setText(ops + numberS, TextView.BufferType.EDITABLE);
                        }
                    } else {
                        numberS = "0";
                        number = (EditText) findViewById(R.id.number);
                        number.setText(ops + numberS, TextView.BufferType.EDITABLE);
                        number.setSelection(numberS.length());
                    }
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
        updateTextSize();
    }

    public void onClickADD(View v) {
        sign = "+";
        op.add(numberS);
        op.add(baseS);
        op.add(sign);
        EditText number = (EditText) findViewById(R.id.number);
        number.setText(ops + numberS + "[b" + baseS + "]" + sign, TextView.BufferType.EDITABLE);
        ops = ops + numberS + "[b" + baseS + "]" + sign;
        updateTextSize();
        numberS = "0";
    }
}