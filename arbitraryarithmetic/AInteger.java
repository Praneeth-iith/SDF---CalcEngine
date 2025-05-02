package arbitraryarithmetic;
import java.util.Arrays;
import java.util.List;
 
public class AInteger{
    // Holds the integer value as a string for arbitrary precision arithmetic
    private String number;

    // Default constructor initializes number to "0"
    public AInteger(){
        this.number="0";
    }

    // Constructor to initialize from a string
    public AInteger(String s){
        this.number=s;
    }

    // Copy constructor to create a new object from another object
    public AInteger(AInteger original){
        this.number=original.getter();
    }

    // Getter method to access the private 'number' attribute
    public String getter(){
        return this.number;
    }

    //method for input validation
    private static void validateIntegerInput(String s) {
        if (s == null || !s.matches("^[+-]?\\d+$")) {
            throw new NumberFormatException("Invalid integer input: " + s);
        }
    }
    
    // Static method to create an AInteger object from a string
    public static AInteger parse(String s){
        return new AInteger(s);
    }

    /* 
     * Removes leading zeroes from a numeric string
     * Example: "002" -> "2"
     */
    protected static String cleanLeadingZeroes(String number){
        String newNumber = "";
        int numberOfLeadingZeroes = 0;
        if(number.charAt(0)!='-'){

        // Count leading zeroes, but leave at least one digit
        for(int i = 0 ; i < number.length()-1; i++){
            if(number.charAt(i) == '0'){
                numberOfLeadingZeroes++;
            }else{
                break;
            }
        }
        for(int i = numberOfLeadingZeroes ; i < number.length();i++){
            newNumber+=number.charAt(i);
        }

        }

        else{

            newNumber="-";
             // Count leading zeroes by leaving minus sign, but leave at least one digit
        for(int i = 1 ; i < number.length()-1; i++){
            if(number.charAt(i) == '0'){
                numberOfLeadingZeroes++;
            }else{
                break;
            }
        }
        for(int i = numberOfLeadingZeroes+1 ; i < number.length();i++){
            newNumber+=number.charAt(i);
        }

        }

        return newNumber;
    }

    /* 
     * Returns the reverse of the input string
     * Example: "abc" -> "cba"
     */
    protected static String reverseString(String number){
        String temp="";
        for(int i = number.length() - 1 ; i >=0; i--){
            temp+=number.charAt(i);
        }
        return temp;
    }
    
    /*
     * Compares two numeric strings
     * Returns true if number1 >= number2, else false
     * Assumes both numbers are positive and have no sign
     */
    protected static boolean stringCompare(String number1,String number2){
        int len1=number1.length();
        int len2=number2.length();
        if(len1>len2){
           return true;
        }else if(len2>len1){
           return false;
        }else{
           for(int i = 0 ; i < len1 ;i++){
               int d1 = number1.charAt(i)-'0';
               int d2 = number2.charAt(i)-'0';
               if(d1 > d2){
                   return true;
               }else if(d1 < d2){
                   return false;
               }
           }
           // Equal numbers case
           return true;
        }
    }
    
    /*
     * Determines the sign ("+" or "-") of each input number
     * Returns a list of two signs corresponding to number1 and number2
     */
    protected static List<String> signDecider(String number1,String number2){
        String sign1="+",sign2="+";
        if(number1.charAt(0)=='-'){
            sign1="-";
        }
        if(number2.charAt(0)=='-'){
            sign2="-";
        }
        return Arrays.asList(sign1,sign2);  
    }
    
    /*
     * Returns the absolute values (magnitudes) of the two input numbers as a list
     * Removes sign characters if present
     */
    protected static List<String> magnitudeMaker(String number1,String number2,String sign1, String sign2){
        String magnitudeof_number2 = number2;
        String magnitudeof_number1 = number1;
        // Remove sign if present
        if(sign1.equals("-") && number1.length() > 0){
            String temp1="";
            for(int i = 1 ; i<number1.length();i++){
                temp1+=number1.charAt(i);
            }
            magnitudeof_number1=temp1;
        }
        if(sign2.equals("-") && number2.length() > 0){
            String temp2="";
            for(int i = 1 ; i<number2.length();i++){
                temp2+=number2.charAt(i);
            }
            magnitudeof_number2=temp2;
        }
        magnitudeof_number1 = cleanLeadingZeroes(magnitudeof_number1);
        magnitudeof_number2 = cleanLeadingZeroes(magnitudeof_number2);
        return Arrays.asList(magnitudeof_number1,magnitudeof_number2);
    }

    /*
     * Adds two positive integer strings and returns their sum as a string
     * Assumes no sign in input
     */
    protected static String additionOfPositiveIntegers(String num1,String num2){
        String reversedSum="";

        // Ensure num1 is the longer string
        if(num1.length()<num2.length()){
            String temp=num1;
            num1=num2;
            num2=temp;
        }
        int len1=num1.length();
        int len2=num2.length();

        // Pad num2 with leading zeros to match length
        String prefixZeroes="";
        for(int i = len2 ; i < len1 ; i++){
            prefixZeroes+='0';
        }
        num2=prefixZeroes.concat(num2);
        len2=num2.length();

        // Perform addition digit by digit from right to left
        int carry=0;
        for(int i = 0 ;i<num1.length();i++){
            int finalDigit;
            int digit1=num1.charAt(len1-1-i)-'0';
            int digit2=num2.charAt(len2-1-i)-'0';
            
            // If sum at this position is >= 10, set carry to 1 
            if(digit1 + digit2 + carry >= 10){
                finalDigit = digit2 + digit1 +carry - 10;
                carry = 1;
            }else{
                finalDigit = digit1 + digit2 + carry;
                carry = 0;
            }
            reversedSum += (char)(finalDigit+'0');
        }
        // If carry remains, add to result
        if(carry==1){
            reversedSum+='1';
        }
        String sum;
        sum = reverseString(reversedSum);
        return sum;
    }

    /*
     * Subtracts two positive integer strings and returns the result as a string
     * Handles which number is larger to determine sign
     * Assumes no sign in input
     */
    protected static String subtractionOfPositiveIntegers(String num1,String num2){
        String signOfResult;

        // Determine which number is larger for correct sign
        if(stringCompare(num1, num2)){
            signOfResult="+";
        }else{
            signOfResult="-";
        }

        // Swap if num2 > num1 to ensure non-negative subtraction
        if(signOfResult.equals("-")){
            String temp =num1;
            num1=num2;
            num2=temp;
        }
        int len1=num1.length();
        int len2=num2.length();

        // Pad num2 with leading zeros to match length
        String prefixZeroes="";
        for(int i = len2 ; i < len1 ; i++){
            prefixZeroes+='0';
        }
        num2=prefixZeroes.concat(num2);
        len2=num2.length();
        int borrow=0;
        String reversedResult="";
        for(int i = len1 -1 ; i >=0 ; i--){
            int digit1 = num1.charAt(i) - '0';
            int digit2 = num2.charAt(i) - '0';
            // If subtraction at this position is negative, borrow from next digit
            if(digit1 - digit2 + borrow>=0){
                int shift=num1.charAt(i)-'0'-(num2.charAt(i)-'0') +borrow;
                borrow=0;
                reversedResult+=(char)(shift+'0');
            }else{
                int shift=num1.charAt(i)-'0'-(num2.charAt(i)-'0') +borrow+10;
                borrow=-1;
                reversedResult+=(char)(shift+'0');
            }
        }

        String result;
        result = reverseString(reversedResult);
        result = cleanLeadingZeroes(result);

        // Add sign if result is negative
        if(signOfResult.equals("-")) result=signOfResult.concat(result);
        return result;
    }

    /*
     * Multiplies two positive integer strings and returns the product as a string
     * Assumes no sign in input
     */
    protected static String multiplicationOfPositiveIntegers(String num1,String num2){
        // Ensure num1 is the longer string
        if(num1.length() < num2.length()){
            String temp = num1;
            num1 = num2;
            num2 = temp;
        }

        int len1 = num1.length();
        int len2 = num2.length();

        String product = "0";
        int carry = 0;
        // Multiply each digit of num2 with num1 and accumulate results
        for(int j = len2-1 ; j >= 0 ; j--){
            String reversedProductWithOneDigit = "";
            carry = 0;
            for(int i = len1-1 ; i >= 0 ; i--){
                // Multiply current digits and add carry
                int digit = carry+(num1.charAt(i)-'0')*(num2.charAt(j)-'0') ;
                carry = (digit/10) ;
                digit -= carry*10;
                reversedProductWithOneDigit += (char)(digit+'0');
            }
            // If carry remains, add to result
            if(carry!=0) reversedProductWithOneDigit += (char)(carry+'0');
            String  productWithOneDigit;
            productWithOneDigit = reverseString(reversedProductWithOneDigit);

            // Add zeros for ensuring place values (shift left)
            for(int k = len2-1 ; k > j ; k--){
                productWithOneDigit += "0";
            }

            product = additionOfPositiveIntegers(product , productWithOneDigit);
        }
        return product;
    }

    /*
     * Divides two positive integer strings and returns the quotient as a string
     * Returns "0" if dividend < divisor
     * Assumes no sign in input
     */
    public static String divisionOfPositiveIntegers(String dividend,String divisor){
        // Handle division by zero (should not occur, checked elsewhere)
        if (divisor.equals("0")) throw new ArithmeticException("Division by zero");

        int len1 = dividend.length();
        String result = "";
        String tempdividend = "";

        // Simulate long division
        for(int i = 0 ;  i < len1 ; i++){
           tempdividend += dividend.charAt(i);
           String nearestTodividend = "0";
           int factor = 0;
           tempdividend = cleanLeadingZeroes(tempdividend);

           // Find how many times divisor fits in tempdividend
           while(!stringCompare(nearestTodividend , tempdividend)){
                factor++;
                nearestTodividend = additionOfPositiveIntegers(nearestTodividend , divisor);
           }

           // If we exceeded, step back one factor
           if(!nearestTodividend.equals(tempdividend)){
                factor--;
                nearestTodividend=subtractionOfPositiveIntegers(nearestTodividend , divisor);
           }

           result += (char)( '0' + factor);
           tempdividend = subtractionOfPositiveIntegers(tempdividend , nearestTodividend);
        }

        return result;
    }

    /*
     * Public method to add two AInteger objects
     * Handles sign and delegates to appropriate helper
     */
    public static AInteger addition(AInteger object1,AInteger object2){
        String number1 = object1.getter();
        String number2 = object2.getter();

        validateIntegerInput(number1);
        validateIntegerInput(number2);


        String sign1,sign2;
        List<String> signs = signDecider(number1,number2);
        sign1=signs.get(0);
        sign2=signs.get(1);

        String magnitude1,magnitude2;
        List<String> magnitudes=magnitudeMaker(number1, number2, sign1, sign2);
        magnitude1=magnitudes.get(0);
        magnitude2=magnitudes.get(1);

        String result="";
        // Decide operation based on sign combination
        if(sign1.equals("-") && sign2.equals("+")){
            result = subtractionOfPositiveIntegers(magnitude2,magnitude1);
        }else if(sign1.equals("+") && sign2.equals("-")){
            result = subtractionOfPositiveIntegers(magnitude1,magnitude2);
        }else if(sign1.equals("+") && sign2.equals("+")){
            result = additionOfPositiveIntegers(magnitude1,magnitude2);
        }else if(sign1.equals("-") && sign2.equals("-")){
            result = additionOfPositiveIntegers(magnitude1,magnitude2);
            if(!result.equals("0")) result="-".concat(result);
        }

        return parse(result);
    }

    /*
     * Public method to subtract object2 from object1
     * Handles sign and delegates to appropriate helper
     */
    public static AInteger subtraction(AInteger object1,AInteger object2){
        String number1=object1.getter();
        String number2=object2.getter();

        validateIntegerInput(number1);
        validateIntegerInput(number2);

        String sign1,sign2;
        List<String> signs = signDecider(number1, number2);
        sign1=signs.get(0);
        sign2=signs.get(1);

        String magnitude1,magnitude2;
        List<String> magnitudes=magnitudeMaker(number1, number2, sign1, sign2);
        magnitude1=magnitudes.get(0);
        magnitude2=magnitudes.get(1);
        

        String result="";
        // Decide operation based on sign combination
        if(sign1.equals("+") && sign2.equals("-")){
           result=additionOfPositiveIntegers(magnitude1,magnitude2);
        }else if(sign1.equals("-") && sign2.equals("+")){
            result=additionOfPositiveIntegers(magnitude1,magnitude2);
            if(!result.equals("0")) result="-".concat(result);
        }else if(sign1.equals("+") && sign2.equals("+")){
            result=subtractionOfPositiveIntegers(magnitude1,magnitude2);
        }else{
            result=subtractionOfPositiveIntegers(magnitude2,magnitude1);
        } 

        return parse(result);
    }
   
    /*
     * Public method to multiply two AInteger objects
     * Handles sign and delegates to multiplication helper
     */
    public static AInteger multiplication(AInteger object1,AInteger object2){
        String number1=object1.getter();
        String number2=object2.getter();    

        validateIntegerInput(number1);
        validateIntegerInput(number2);

        String sign1,sign2;
        List<String>  signs = signDecider(number1, number2);
        sign1=signs.get(0);
        sign2=signs.get(1);

        String result="";
        String magnitude1,magnitude2;
        List<String> magnitudes=magnitudeMaker(number1, number2, sign1, sign2);
        magnitude1 = magnitudes.get(0);
        magnitude2 = magnitudes.get(1);

        // If signs differ, result is negative (unless zero)
        if(!(sign1.equals(sign2))){
            result = multiplicationOfPositiveIntegers(magnitude1 , magnitude2);
            result = cleanLeadingZeroes(result);
            if(!(result.equals("0"))) result = "-".concat(result);
        }else{ 
            result = multiplicationOfPositiveIntegers(magnitude1 , magnitude2);
            result = cleanLeadingZeroes( result );
        }

        return parse(result);
    }
    
    /*
     * Public method to divide object1 by object2
     * Handles sign and delegates to division helper
     * Throws ArithmeticException if divisor is zero
     */
    public static AInteger division(AInteger object1,AInteger object2){
        String number1=object1.getter();
        String number2=object2.getter();

        validateIntegerInput(number1);
        validateIntegerInput(number2);

        String quotient="";
        List<String> signs;
        String sign1,sign2;
        signs=signDecider(number1, number2);
        sign1=signs.get(0);
        sign2=signs.get(1);

        List<String> magnitudes;
        String magnitude1,magnitude2;
        magnitudes=magnitudeMaker(number1, number2, sign1, sign2);
        magnitude1 = magnitudes.get(0);
        magnitude2 = magnitudes.get(1);

        // Check for division by zero
        if(magnitude2.equals("0")){
            throw new ArithmeticException("Division by zero error");
        }

        quotient = divisionOfPositiveIntegers(magnitude1 , magnitude2);
        quotient = cleanLeadingZeroes(quotient);
        // If signs differ and result is not zero, add negative sign
        if(!sign1.equals(sign2) && !quotient.equals("0")){
            quotient="-".concat(quotient);
        }
        
        return parse(quotient);

    }
}