package manastur.calculator;
/**
 * Created by Calin on 05/08/2017.
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Operations {

    public static void Delete (List<String> opS, int index_prime, int index_final){
        for(int index = index_final; index >= index_prime; index --)
            opS.remove(index);
    }

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

    public static boolean Search_Format (List<String> opS ,int i){
        boolean sign = false;
        boolean no_paranthesis = true;
        if(i + 4 <= opS.size()) {
            if (opS.get(2) == "+" || opS.get(2) == "-" || opS.get(2) == "*" || opS.get(2) == "/")
                sign = true;
            for (int j = i; j <= i + 4; j++) {
                if (opS.get(j) == "(" || opS.get(j) == ")")
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

    public static boolean Search_Format2 (List<String> opS ,int i){
        boolean sign = false;
        boolean no_paranthesis = true;
        if(Search_Format(opS,i))
            return true;
        return false;
    }


    public static List<String> Calculus(List<String> opS){
        int paranthesis = 0, base_1, base_2, number_1, number_2;
        String result = "";String S_base_1 = "", S_base_2 = "", S_number_1 = "", S_number_2 = "", op_type = "";
        if(opS.size() >= 4) {
            while(opS.size() != 2)
            for (int i = 0; i <= opS.size(); i++) {
                if (i + 4 < opS.size())
                    if (Search_Format(opS, i)) {
                        S_base_1 = opS.get(i+1);
                        String aux = Operation(opS.get(i+2), opS.get(i), opS.get(i+3), Integer.valueOf(opS.get(i+1)), Integer.valueOf(opS.get(i+4)));
                        result = aux;
                        Delete(opS,i,i+4);
                        opS.add(i,result);
                        opS.add(i+1,S_base_1);

                    }
                }
            return opS;
        }
        else
            return null;
    }

    public static String Calculus2(List<String> opS){
        int paranthesis = 0, base_1, base_2, number_1, number_2;
        String result = "";String S_base_1 = "", S_base_2 = "", S_number_1 = "", S_number_2 = "", op_type = "";
        if(opS.size() >= 4) {
            for (int i = 0; i <= opS.size(); i++) {
                if (i + 4 < opS.size())
                    if (Search_Format(opS, i)) {
                        S_base_1 = opS.get(i+1);
                        String aux = Operation(opS.get(i+2), opS.get(i), opS.get(i+3), Integer.valueOf(opS.get(i+1)), Integer.valueOf(opS.get(i+4)));
                        result = aux;
                    }
            }
            return result + S_base_1;
        }
        else
            return null;
    }
    //"7b", "16", "+", "7b", "12" = da
    //"7b", "16", "+", "7b", "12", "-", "2", "10" = d8

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        List<String> opS = new ArrayList<>();
        opS.add("7b");
        opS.add("16");
        opS.add("+");
        opS.add("7b");
        opS.add("12");
        opS.add("-");
        opS.add("2");
        opS.add("10");
        System.out.println(Calculus(opS));


    }
}