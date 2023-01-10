import java.util.Scanner;
import java.io.IOException;
//import java.lang.String;
public class Main {
    public static void main(String[] args) {
    Scanner s = new Scanner(System.in);
    System.out.println("Введите математическое выражение");
    String stroka = s.nextLine();
    //stroka-->calc-->newStroka
    String newStroka = calc(stroka);
    //if User made a mistake
    if(newStroka.equals("Error1")){
        try{
            throw new IOException();
        }catch (IOException e){
            System.out.println("throws Exception //т.к. формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *)");
        }
    } else if (newStroka.equals("Error2")) {
        try{
            throw new IOException();
        }catch (IOException e){
            System.out.println("throws Exception //т.к. используются одновременно разные системы счисления");
        }
    } else if (newStroka.equals("Error3")) {
        try{
            throw new IOException();
        }catch (IOException e){
            System.out.println("throws Exception //т.к. в римской системе нет отрицательных чисел");
        }
    } else {
        System.out.println("Результат операции: "+newStroka);
    }

    }
    //----------------------------------------
    public static String calc(String input){
        String [] simbs = input.split(" ");
        Statement str = new Statement();
        if(simbs.length==3) {
            str.firstMember = simbs[0];
            str.secondMember = simbs[2];
            str.mathOperator = simbs[1];
        }
        int romanFlag = 0;
        int a=0,b=0;
        int answer=0;
        String[] RomanAlphabet = {"Ⅰ","ⅠⅠ","ⅠⅠⅠ","ⅠⅤ","Ⅴ","ⅤⅠ","ⅤⅠⅠ","ⅤⅠⅠⅠ","ⅠⅩ","Ⅹ"};
        String myAnswer = new String();

        //-------ERROR CASE----------
        int Error1=0;//User made a mistake in the format of the mathematical expression
        int Error2=0;//User try to use different systems
        int romanFlagA=0;
        int romanFlagB=0;
        int arabianFlagA=0;
        int arabianFlagB=0;
        int sum = 2;

        if(simbs.length!=3){
            Error1=1;
        }else{
            sum = 0;
            for(int i=0;i<=9;i++){
                if(RomanAlphabet[i].equals(str.firstMember)){
                    romanFlagA++;
                    sum++;
                }
                if(RomanAlphabet[i].equals(str.secondMember)){
                    romanFlagB++;
                    sum++;
                }
                if(str.firstMember.equals(Integer.toString(i+1))){
                    arabianFlagA++;
                    sum++;
                }
                if(str.secondMember.equals(Integer.toString(i+1))){
                    arabianFlagB++;
                    sum++;
                }
            }
        }


        if(romanFlagA+romanFlagB==1 && arabianFlagA+arabianFlagB==1){
            Error2=1;
        }
        if (sum!=2){
            Error1=1;
        }

        if(Error1==0 && Error2==0) {

            if (str.firstMember.codePointAt(0)==8544 || str.firstMember.codePointAt(0)==8548 || str.firstMember.codePointAt(0)==8553) {
                //-------ROMAN CASE-----------
                romanFlag = 1;
                for (int i = 0; i <= 9; i++) {
                    if (RomanAlphabet[i].equals(str.firstMember)) {
                        a = i + 1;
                        break;
                    }
                }
                for (int i = 0; i <= 9; i++) {
                    if (RomanAlphabet[i].equals(str.secondMember)) {
                        b = i + 1;
                        break;
                    }
                }


            } else {
                //-------ARABIAN CASE-----------
                for (int i = 1; i <= 10; i++) {
                    if (str.firstMember.equals(Integer.toString(i))) {
                        a = i;
                        break;
                    }
                }
                for (int i = 1; i <= 10; i++) {
                    if (str.secondMember.equals(Integer.toString(i))) {
                        b = i;
                        break;
                    }
                }
            }
            //-----performing a mathematical operation-----
            if (str.mathOperator.equals("+")) {
                answer = a + b;
            }
            if (str.mathOperator.equals("-")) {
                answer = a - b;
            }
            if (str.mathOperator.equals("*")) {
                answer = a * b;
            }
            if (str.mathOperator.equals("/")) {
                answer = a / b;
            }

            myAnswer = Integer.toString(answer);

            //------Transform answer in case of ROMAN------
            if (romanFlag == 1) {
              if (answer < 1) {
                  myAnswer = "Error3";
              }else{
                if (answer >= 1 && answer <= 10) {
                    myAnswer = RomanAlphabet[answer - 1];
                }
                if (answer > 10) {
                    String valueBefore1 = myAnswer.substring(0, 1);
                    String valueBefore2 = myAnswer.substring(1, 2);
                    String valueAfter1 = new String();
                    String valueAfter2 = new String();

                    switch (valueBefore1) {
                        case "1":
                            valueAfter1 = "Ⅹ";
                            break;
                        case "2":
                            valueAfter1 = "ⅩⅩ";
                            break;
                        case "3":
                            valueAfter1 = "ⅩⅩⅩ";
                            break;
                        case "4":
                            valueAfter1 = "ⅩⅬ";
                            break;
                        case "5":
                            valueAfter1 = "Ⅼ";
                            break;
                        case "6":
                            valueAfter1 = "ⅬⅩ";
                            break;
                        case "7":
                            valueAfter1 = "ⅬⅩⅩ";
                            break;
                        case "8":
                            valueAfter1 = "ⅬⅩⅩⅩ";
                            break;
                        case "9":
                            valueAfter1 = "ⅩⅭ";
                            break;
                    }
                    for (int i = 0; i < 9; i++) {
                        if (valueBefore2.equals(Integer.toString(i + 1))) {
                            valueAfter2 = RomanAlphabet[i];
                            break;
                        }
                    }
                    myAnswer = valueAfter1.concat(valueAfter2);
                    if (answer == 100) {
                        myAnswer = "Ⅽ";
                    }
                }
              }
            }

        }else{
            if(Error2!=0) {
                myAnswer="Error2";
            }
            if(Error1!=0) {
                myAnswer="Error1";
            }
        }

        return myAnswer;
    }
}
    class Statement{
        String firstMember;
        String secondMember;
        String mathOperator;
    }
