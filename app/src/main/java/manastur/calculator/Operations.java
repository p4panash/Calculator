package manastur.calculator;
/**
 * Created by Calin on 05/08/2017.
 */

import java.util.Scanner;

public class Operations {
    public static int ConvertToTen(String number, int base){
        int result = Integer.valueOf(number, base);
        return result;
    }

    public static String ConvertToBase(String number, int base_prime, int base_final){
        String result = "";
        int number_base10 = ConvertToTen(number, base_prime);
        result = Integer.toString(number_base10, base_final);
        return result;
    }

    public static String Operation(String operation_sign, String number_one, String number_two, int base_one, int base_two){
        float result;
        float number_1 = ConvertToTen(number_one, base_one);
        float number_2 = ConvertToTen(number_two, base_two);
        int interm;
        String output_s = "";
        int output_i;
        switch(operation_sign){
            case "+" :  result = number_1 + number_2;
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

    public static boolean Search_Format (String[] opS ,int i){
        boolean sign = false;
        boolean no_paranthesis = true;
        if(i + 4 <= opS.length) {
            if (opS[2] == "+" || opS[2] == "-" || opS[2] == "*" || opS[2] == "/")
                sign = true;
            for (int j = i; j <= i + 4; j++) {
                if (opS[j] == "(" || opS[j] == ")")
                    no_paranthesis = false;
            }
            if (sign && no_paranthesis)
                return true;
            else
                return false;
        }
        else
            return false;
    }

    public static String Calculus(String[] opS){
        int paranthesis = 0, base_1, base_2, number_1, number_2;
        String result = "", S_base_1 = "", S_base_2 = "", S_number_1 = "", S_number_2 = "", op_type = "";
        boolean go = false;
        for (int i = 0; i <= opS.length; i++){
            if(i+4 < opS.length)
                if(Search_Format(opS,i))
                    result = Operation(opS[i+2],opS[i],opS[i+3],Integer.valueOf(opS[i+1]),Integer.valueOf(opS[i+4]));
        }
        return result;
    }

    //"7b", "16", "+", "7b", "12" = da
    //"7b", "16", "+", "7b", "12", "*", "2", "10" = 1b4

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String[] opS = {"7b", "16", "+", "7b", "12", "*", "2", "10"};

    }
}