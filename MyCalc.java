package MyCalculator;

import java.util.Scanner;

/**
 * @author IlyaNeumann 01.12.2022
 */
public class MyCalc {
    public static void main(String[] args) throws Exception {

        String[] actions = {"+", "-", "/", "*"};
        String[] regexActions = {"\\+", "-", "/", "\\*"};
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите выражение: ");
        String exp = scanner.nextLine();
        String n = "arabic";
        int countActions = countActions(exp, actions);
        if (countActions > 1) {
            throw new Exception("Количество операторов должно быть не больше одного");
        }
        String action=null;
        String regexAction=null;

        for (int i = 0; i < actions.length; i++) {
            if(exp.contains(actions[i])){
                action = actions[i];
                regexAction = regexActions[i];
                break;
            }
        }
        if(action==null){
            System.out.println("Строка не является математической операцией.");
            return;
        }

        String[] data = exp.split(regexAction);
        if (data.length > 2) {
            throw new Exception("т.к. формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *)");
        }
        try {
            RomanNumber number1 = RomanNumber.valueOf(data[0]);
            RomanNumber number2 = RomanNumber.valueOf(data[1]);
            if (number1 != null && number2 != null) {
                n = "roman";
            }
        }catch (IllegalArgumentException e){

        }

        int a,b;

        try {
            if (n == "roman"){
                a = RomanNumber.valueOf(data[0]).getArabic();
                b = RomanNumber.valueOf(data[1]).getArabic();
            }else{
                a = Integer.parseInt(data[0]);
                b = Integer.parseInt(data[1]);
            }
        } catch (NumberFormatException e) {
            System.out.println("Калькулятор может производить операции только с целыми числами.");
            return;
        }


        if (a<1||b<1||a>10||b>10) {
            if(n=="arabic") {
                System.out.println("Калькулятор принимает только значения от 1 до 10.");
                return;
            }
            if(n=="roman") {
                System.out.println("Калькулятор принимает только значения от I до X.");
                return;
            }
        }

        int result;
        switch (action){
            case "+":
                result = a+b;
                break;
            case "-":
                result = a-b;
                break;
            case "*":
                result = a*b;
                break;
            default:
                result = a/b;
                break;
        }
        if(n == "roman" && result<0) {
            throw new Exception("В римской системе нет отрицательных чисел.");
        }
        if(n == "roman"){
            System.out.println(RomanNumber.inToRoman(result));
        }else{
            System.out.println(result);
        }
    }

    private static int countActions(String expression, String[] actions) {
        int count = 0;
        char[] chars = expression.toCharArray();
        for (int i = 0 ; i < chars.length ; i++) {
            char c = chars[i];
            for (int j = 0 ; j < actions.length ; j++) {
                if (String.valueOf(c).equals(actions[j])) {
                    count++;
                }
            }
        }
        return count;
    }
}
