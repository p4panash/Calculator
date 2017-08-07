package manastur.calculator;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_main);

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
        if (numberS.length() > 0 && !numberS.equals("0") && !baseB) {
            numberS += "1";
            number = (EditText) findViewById(R.id.number);
            number.setText(numberS, TextView.BufferType.EDITABLE);
            number.setSelection(numberS.length());
            number.setCursorVisible(true);
        } else {
            if (numberS.equals("0") && !baseB) {
                numberS = "1";
                number = (EditText) findViewById(R.id.number);
                number.setText(numberS, TextView.BufferType.EDITABLE);
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
    }

    public void onClickB2(View v) {
        if (numberS.length() > 0 && !numberS.equals("0") && !baseB) {
            numberS += "2";
            number = (EditText) findViewById(R.id.number);
            number.setText(numberS, TextView.BufferType.EDITABLE);
            number.setSelection(numberS.length());
        } else {
            if (numberS.equals("0") && !baseB) {
                numberS = "2";
                number = (EditText) findViewById(R.id.number);
                number.setText(numberS, TextView.BufferType.EDITABLE);
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
    }

    public void onClickB3(View v) {
        if (numberS.length() > 0 && !numberS.equals("0") && !baseB) {
            numberS += "3";
            number = (EditText) findViewById(R.id.number);
            number.setText(numberS, TextView.BufferType.EDITABLE);
            number.setSelection(numberS.length());
        } else {
            if (numberS.equals("0") && !baseB) {
                numberS = "3";
                number = (EditText) findViewById(R.id.number);
                number.setText(numberS, TextView.BufferType.EDITABLE);
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
    }

    public void onClickB4(View v) {
        if (numberS.length() > 0 && !numberS.equals("0") && !baseB) {
            numberS += "4";
            number = (EditText) findViewById(R.id.number);
            number.setText(numberS, TextView.BufferType.EDITABLE);
            number.setSelection(numberS.length());
        } else {
            if (numberS.equals("0") && !baseB) {
                numberS = "4";
                number = (EditText) findViewById(R.id.number);
                number.setText(numberS, TextView.BufferType.EDITABLE);
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
    }

    public void onClickB5(View v) {
        if (numberS.length() > 0 && !numberS.equals("0") && !baseB) {
            numberS += "5";
            number = (EditText) findViewById(R.id.number);
            number.setText(numberS, TextView.BufferType.EDITABLE);
            number.setSelection(numberS.length());
        } else {
            if (numberS.equals("0") && !baseB) {
                numberS = "5";
                number = (EditText) findViewById(R.id.number);
                number.setText(numberS, TextView.BufferType.EDITABLE);
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
            number.setText(numberS, TextView.BufferType.EDITABLE);
            number.setSelection(numberS.length());
        } else {
            if (numberS.equals("0") && !baseB) {
                numberS = "6";
                number = (EditText) findViewById(R.id.number);
                number.setText(numberS, TextView.BufferType.EDITABLE);
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
            number.setText(numberS, TextView.BufferType.EDITABLE);
            number.setSelection(numberS.length());
        } else {
            if (numberS.equals("0") && !baseB) {
                numberS = "7";
                number = (EditText) findViewById(R.id.number);
                number.setText(numberS, TextView.BufferType.EDITABLE);
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
    }

    public void onClickB8(View v) {
        if (numberS.length() > 0 && !numberS.equals("0") && !baseB) {
            numberS += "8";
            number = (EditText) findViewById(R.id.number);
            number.setText(numberS, TextView.BufferType.EDITABLE);
            number.setSelection(numberS.length());
        } else {
            if (numberS.equals("0") && !baseB) {
                numberS = "8";
                number = (EditText) findViewById(R.id.number);
                number.setText(numberS, TextView.BufferType.EDITABLE);
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
    }

    public void onClickB9(View v) {
        if (numberS.length() > 0 && !numberS.equals("0") && !baseB) {
            numberS += "9";
            number = (EditText) findViewById(R.id.number);
            number.setText(numberS, TextView.BufferType.EDITABLE);
            number.setSelection(numberS.length());
        } else {
            if (numberS.equals("0") && !baseB) {
                numberS = "9";
                number = (EditText) findViewById(R.id.number);
                number.setText(numberS, TextView.BufferType.EDITABLE);
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