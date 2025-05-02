import arbitraryarithmetic.*;


public class MyInfArith {
    public static void main(String[] args){
        String type = args[0];
        String operation = args[1];
        String operand1  = args[2];
        String operand2 = args[3];
        if(type.equals("int")){
            if(operation.equals("add")){
                System.out.println(AInteger.addition(AInteger.parse(operand1),AInteger.parse(operand2)).getter());
            }else if(operation.equals("sub")){
                System.out.println(AInteger.subtraction(AInteger.parse(operand1),AInteger.parse(operand2)).getter());
            }else if(operation.equals("mul")){
                System.out.println(AInteger.multiplication(AInteger.parse(operand1),AInteger.parse(operand2)).getter());
            }else if(operation.equals("div")){
                System.out.println(AInteger.division(AInteger.parse(operand1),AInteger.parse(operand2)).getter());
            }
        }else if(type.equals("float")){
            if(operation.equals("add")){
                System.out.println(AFloat.addition(AFloat.parse(operand1),AFloat.parse(operand2)).getter());
            }else if(operation.equals("sub")){
                System.out.println(AFloat.subtraction(AFloat.parse(operand1),AFloat.parse(operand2)).getter());
            }else if(operation.equals("mul")){
                System.out.println(AFloat.multiplication(AFloat.parse(operand1),AFloat.parse(operand2)).getter());
            }else if(operation.equals("div")){
                System.out.println(AFloat.division(AFloat.parse(operand1),AFloat.parse(operand2)).getter());
            }
        }
    }
}
