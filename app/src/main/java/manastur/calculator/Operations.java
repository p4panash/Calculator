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

    public static boolean Search_Format_Superior (List<String> opS ,int index_prime,int index_final){
        boolean sign = false;
        boolean no_paranthesis = true;
        if(index_prime + 4 <= index_final) {
            if (opS.get(index_prime+3) == "*" || opS.get(index_prime+3) == "/")
                sign = true;
            for (int j = index_prime + 1; j <= index_prime + 5; j++) {
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

    public static boolean Search_Format_Inferior (List<String> opS ,int index_prime,int index_final){
        boolean sign = false;
        boolean no_paranthesis = true;
        if(index_prime + 4 <= index_final) {
            if (opS.get(index_prime+3) == "+" || opS.get(index_prime+3) == "-")
                sign = true;
            for (int j = index_prime + 1; j <= index_prime + 5; j++) {
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

    public static boolean Search_Superior (List<String> opS, int index_prime,int index_final ) {
        int index = index_prime;
        for(index = 0; index <index_final; index++)
            if((opS.get(index) == "*" || opS.get(index) == "/") && (opS.get(index + 1) != "("))
                return true;
        return false;
    }

    public static int Search_paranthesis_prime (List<String> opS, int paranthesis_index_prime){
        for(int index = opS.size() - 1; index >= 0; index --)
            if(opS.get(index).equals("(")){
                paranthesis_index_prime = index;
                index = -1;
            }
        return paranthesis_index_prime;
    }

    public static int Search_paranthesis_final (List<String> opS, int paranthesis_index_final){
        for(int index = 0; index < opS.size(); index ++)
            if(opS.get(index).equals(")")){
                paranthesis_index_final = index;
                index = opS.size() + 1;
            }
        return paranthesis_index_final;
    }

    public static List<String> Calculus2(List<String> opS){
        String S_base_1 = "", result = "";
        int paranthesis_prime = 0, paranthesis_final = 0, index,superior = 0;
        if(opS.size() >= 4) {
            while(opS.size() != 2) {
                for (index = 0; index <= opS.size(); index++) {
                    paranthesis_prime = Search_paranthesis_prime(opS, 0);
                    paranthesis_final = Search_paranthesis_final(opS,0);
                    if(paranthesis_final - paranthesis_prime == 3){
                        opS.remove(paranthesis_final);
                        opS.remove(paranthesis_prime);
                        paranthesis_prime = 0;
                        paranthesis_final = 0;
                    }
                    if (paranthesis_prime != 0 && paranthesis_final != 0) {
                        if (Search_Superior(opS, paranthesis_prime, paranthesis_final)) {
                            int index_prime = paranthesis_prime;
                            while (!Search_Format_Superior(opS, index_prime, paranthesis_final))
                                index_prime++;
                            S_base_1 = opS.get(index_prime + 2);
                            result = Operation(opS.get(index_prime + 3), opS.get(index_prime + 1), opS.get(index_prime + 4), Integer.valueOf(opS.get(index_prime + 2)), Integer.valueOf(opS.get(index_prime + 5)));
                            Delete(opS, index_prime + 1, index_prime + 5);
                            opS.add(index_prime + 1, result);
                            opS.add(index_prime + 2, S_base_1);
                            System.out.println(opS);
                            index = opS.size() + 1;
                        }
                        else {
                            int index_prime = paranthesis_prime;
                            S_base_1 = opS.get(index_prime + 2);
                            result = Operation(opS.get(index_prime + 3), opS.get(index_prime + 1), opS.get(index_prime + 4), Integer.valueOf(opS.get(index_prime + 2)), Integer.valueOf(opS.get(index_prime + 5)));
                            Delete(opS, index_prime + 1, index_prime + 5);
                            opS.add(index_prime + 1, result);
                            opS.add(index_prime + 2, S_base_1);
                            System.out.println(opS);
                            index = opS.size() + 1;
                        }
                    }
                        else {
                        if (Search_Superior(opS, index, opS.size())) {
                            while(!Search_Format_Superior(opS,index, opS.size()))
                                index++;
                            S_base_1 = opS.get(index + 1);
                            result = Operation(opS.get(index + 2), opS.get(index), opS.get(index + 3), Integer.valueOf(opS.get(index + 1)), Integer.valueOf(opS.get(index + 4)));
                            Delete(opS, index, index + 4);
                            opS.add(index, result);
                            opS.add(index + 1, S_base_1);
                            System.out.println(opS);
                            index = opS.size() + 1;
                        }
                        else{
                            S_base_1 = opS.get(index + 1);
                            result = Operation(opS.get(index + 2), opS.get(index), opS.get(index + 3), Integer.valueOf(opS.get(index + 1)), Integer.valueOf(opS.get(index + 4)));
                            Delete(opS, index, index + 4);
                            opS.add(index, result);
                            opS.add(index + 1, S_base_1);
                            System.out.println(opS);
                            index = opS.size() + 1;
                        }
                    }
                }
            }
        return opS;
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
        opS.add("(");
        opS.add("7b");
        opS.add("12");
        opS.add("-");
        opS.add("2");
        opS.add("10");
        opS.add("+");
        opS.add("2");
        opS.add("10");
        opS.add("*");
        opS.add("3");
        opS.add("10");
        opS.add(")");
        System.out.println(opS.size());
        System.out.println(opS);
        Calculus2(opS);
    }
}