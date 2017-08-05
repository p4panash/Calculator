package manastur.calculator;
/**
 * Created by Calin on 05/08/2017.
 */

import android.widget.Switch;

import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Operations {
    public static int ConvertToTen(String number,int base){
        int result = Integer.valueOf(number,base);
        return result;
    }

    public static String ConvertToBase(String number,int base_prime,int base_final){
        String result = "";
        int number_base10 = ConvertToTen(number,base_prime);
        result = Integer.toString(number_base10,base_final);
        return result;
    }


    public static String Operation(String operation_sign,String number_one,String number_two,int base_one,int base_two){
        float result;
        float number_1 = ConvertToTen(number_one,base_one);
        float number_2 = ConvertToTen(number_two,base_two);
        int interm;
        String output_s = "";
        int output_i;
        switch(operation_sign){
            case "+":  result = number_1 + number_2;
                output_i = Math.round(result);
                output_s = Float.toString(output_i);
                output_s = output_s.substring(0,output_s.length()-2);
                break;
            case "-":  result = number_1 - number_2;
                output_i = Math.round(result);
                output_s = Integer.toString(output_i);
                break;
            case "*":  result = number_1 * number_2;
                output_i = Math.round(result);
                output_s = Integer.toString(output_i);
                break;
            case "/":  result = number_1 / number_2 + 1/2;
                output_i = Math.round(result);
                output_s = Integer.toString(output_i);
                break;
        }
        interm = Integer.valueOf(output_s);
        output_s = Integer.toString(interm,base_one);
        return output_s;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int base_one,base_two;
        String number_one,number_two,operaion_sign;
        number_one = in.nextLine();
        number_two = in.nextLine();
        operaion_sign = in.nextLine();
        base_one = in.nextInt();
        base_two = in.nextInt();
        String result = Operation(operaion_sign,number_one,number_two,base_one,base_two);
        System.out.print(result);
    }
}