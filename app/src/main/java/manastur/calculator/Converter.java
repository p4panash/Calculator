package manastur.calculator; /**
 * Created by Calin on 05/08/2017.
 */

import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Converter {

    public static String Convert(String number,int base_prime,int base_final){
        String result = "";
        int number_base10 = Integer.valueOf(number,base_prime);
        result = Integer.toString(number_base10,base_final);
        return result;
    }
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int base_prime,base_final;
        String number;
        number = in.nextLine();
        base_prime = in.nextInt();
        base_final = in.nextInt();
        String output = Convert(number,base_prime,base_final);
        System.out.print(output);
    }
}