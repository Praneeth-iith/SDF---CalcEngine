package arbitraryarithmetic;
import java.util.Arrays;
import java.util.List;

/**
 * Arbitrary-precision floating point number class.
 * Supports addition, subtraction, multiplication, and division.
 * All numbers are represented as strings.
 */
public class AFloat{
    // Stores the float value as a string
    private String number;
    public static int precision = 30;
    public static void setPrecision(int precision){
        AFloat.precision = precision ;
    }
    /**
     * Validates that the input string is a valid float representation.
     * Accepts: "123", "-123", "123.45", "-0.5"
     * Rejects: ".5", "0.", "-0.", ".", "abc"
     */
    private static void validateInput(String s) {
        if (s.matches("^-?\\d+(\\.\\d+)?$")) {
            throw new NumberFormatException("Invalid float input: " + s);
        }
    }

    // Returns the current number as string
    public String getter(){
        return number;
    }

    // Returns the minimum of two integers
    private int min(int a , int b){
        return (a >= b) ? b : a;
    }

    // Returns the maximum of two integers
    private static int max(int a , int b){
        return (a >= b) ? a : b;
    }

    /**
     * Truncates the decimal part to the given precision (no rounding).
     */
    private void truncate(int precision){
        String trunctedDecimal = "";
        String fullDecimal = this.decimalPart();
        // Copy up to 'precision' decimal digits
        for(int i = 0 ; i < min(this.decimalPlaces(), precision); i++){
            trunctedDecimal += fullDecimal.charAt(i);
        }
        String truncatedNumber = this.integralPart().concat(".").concat(trunctedDecimal);
        this.setter(truncatedNumber);
    }

    // Sets the internal number string
    private void setter(String s){
        this.number = s;
    }

    // Default constructor, initializes to "0"
    public AFloat(){
        this.number = "0";
    }

    // Constructs from string (assumes valid input)
    public AFloat(String s){
        this.number = s;
    }

    // Copy constructor
    public AFloat(AFloat original){
        this.number = original.getter();
    }

    // Parses a string into an AFloat
    public static AFloat parse(String s){
        return new AFloat(s);
    }

    // Checks if the number is an integer (no decimal point)
    private boolean isInt(){
        int check = 1;
        // Scan for decimal point
        for(int i = 0 ; i < this.number.length() ; i++){
             if(number.charAt(i) == '.'){
                check = 0;
                break;
             }
        }
        return check > 0;
    }

    /**
     * Removes the sign from both numbers and returns their magnitudes as a list.
     */
    protected static List<String> magnitudeMaker(String number1, String number2, String sign1, String sign2){
        String magnitudeof_number2 = number2;
        String magnitudeof_number1 = number1;
        // Remove sign if present for number1
        if(sign1.equals("-") && number1.length() > 0){
            String temp1 = "";
            for(int i = 1 ; i < number1.length(); i++){
                temp1 += number1.charAt(i);
            }
            magnitudeof_number1 = temp1;
        }
        // Remove sign if present for number2
        if(sign2.equals("-") && number2.length() > 0){
            String temp2 = "";
            for(int i = 1 ; i < number2.length(); i++){
                temp2 += number2.charAt(i);
            }
            magnitudeof_number2 = temp2;
        }
        return Arrays.asList(magnitudeof_number1, magnitudeof_number2);
    }
    
    /**
     * Cleans leading zeroes from the integral part of the number.
     */
    protected static void cleanLeadingZeroesInIntegralPart(AFloat object){
        String newIntegralPart = AInteger.cleanLeadingZeroes(object.integralPart());
        String newNumber = newIntegralPart.concat(".").concat(object.decimalPart());
        object.setter(newNumber);
    }

    // Returns the integral part (before decimal point)
    public String integralPart(){
        String s = this.number;
        String temp = "";
        int i = 0;
        // Collect characters until decimal point
        while(i < s.length() && s.charAt(i) != '.'){
            temp += s.charAt(i);
            i++;
        }
        return temp;
    }

    // Returns the number of decimal places
    protected int decimalPlaces(){
        int count = 0;
        int i = this.number.length() - 1;
        // Count digits after decimal
        while(number.charAt(i) != '.'){
            count++;
            i--;
        }
        return count;
    }

    // Returns the decimal part (after decimal point)
    protected String decimalPart(){
        String s = this.number;
        String temp = "";
        int i = s.length() - 1;
        // Collect characters after decimal, in reverse order
        while(s.charAt(i) != '.'){
            temp += s.charAt(i);
            i--;
        }
        // Reverse to restore original order
        return AInteger.reverseString(temp);
    }

    // Removes the decimal point by concatenating integral and decimal parts
    protected String removeDecimal(){
        return this.integralPart().concat(this.decimalPart());
    }

    protected static String makeStandard(AFloat object){
        String integralPart = AInteger.cleanLeadingZeroes(object.integralPart());
        String decimalPart  = AInteger.reverseString(AInteger.cleanLeadingZeroes(AInteger.reverseString(object.decimalPart())));
        return integralPart.concat(".").concat(decimalPart);
    }
    /**
     * Adds two positive AFloat numbers (ignoring sign).
     */
    protected static String additionOfPositiveFloat(AFloat object1, AFloat object2){
        // Get integral and decimal parts
        String integralPart1 = object1.integralPart();
        String decimalPart1  = object1.decimalPart();
        String integralPart2 = object2.integralPart();
        String decimalPart2  = object2.decimalPart();
      
        int len1 = decimalPart1.length();
        int len2 = decimalPart2.length();

        // Pad decimal parts with zeros to equal length
        if(len1 < len2){
            for(int i = len1; i < len2; i++){
                decimalPart1 += "0";
            }
        }else{
            for(int i = len2; i < len1; i++){
                decimalPart2 += "0";
            }
        }

        int numDecimalPlaces = decimalPart1.length();
        // Add as integers (integral+decimal)
        String sum = AInteger.additionOfPositiveIntegers( integralPart1.concat(decimalPart1), integralPart2.concat(decimalPart2));

        int count = 0;
        String finalDecimalPart = "", finalIntegralPart = "";
        // Split sum into decimal and integral parts
        for(int i = sum.length() - 1; i >= 0; i--){
            count++;
            if(count <= numDecimalPlaces){
                finalDecimalPart += sum.charAt(i);
            }else{
                finalIntegralPart += sum.charAt(i);
            }
        }

        // Reverse and clean
        finalDecimalPart  = AInteger.reverseString(finalDecimalPart);
        finalIntegralPart = AInteger.reverseString(finalIntegralPart);
        finalIntegralPart = AInteger.cleanLeadingZeroes(finalIntegralPart);
        finalDecimalPart  = AInteger.reverseString(AInteger.cleanLeadingZeroes(AInteger.reverseString(finalDecimalPart)));

        return finalIntegralPart.concat(".").concat(finalDecimalPart);
    }
    
    /**
     * Subtracts two positive integer strings (num1 - num2).
     * Returns result as a string (may be negative).
     */
    private static String subtractionOfPositiveIntegers(String num1, String num2){
        String signOfResult = "";

        // Determine which is larger for sign
        if(!AInteger.stringCompare(num1, num2)){
            signOfResult = "-";
        }
        // Swap if needed to ensure num1 >= num2
        if(signOfResult.equals("-")){
            String temp = num1;
            num1 = num2;
            num2 = temp;
        }
        int len1 = num1.length();
        int len2 = num2.length();

        // Pad num2 with leading zeros
        String prefixZeroes = "";
        for(int i = len2; i < len1; i++){
            prefixZeroes += '0';
        }

        num2 = prefixZeroes.concat(num2);
        len2 = num2.length();
        int borrow = 0;
        String reversedResult = "";
        // Perform digit-wise subtraction from right to left
        for(int i = len1 - 1; i >= 0; i--){
            int digit1 = num1.charAt(i) - '0';
            int digit2 = num2.charAt(i) - '0'; 
            if(digit1 - digit2 + borrow >= 0){
               int shift = digit1 - digit2 + borrow;
               borrow = 0;
               reversedResult += (char)(shift + '0');
            }else{
               int shift = digit1 - digit2 + borrow + 10;
               borrow = -1;
               reversedResult += (char)(shift + '0');
            }
        }
        // Reverse to get final result
        String result = AInteger.reverseString(reversedResult);
        if(signOfResult.equals("-") && !result.equals("0.0") ) result = signOfResult.concat(result);
        return result;
    }

    /**
     * Subtracts two positive AFloat numbers (ignoring sign).
     */
    private static String SubtractionOfPosFloat(AFloat object1, AFloat object2){
        // Clean leading zeroes
        cleanLeadingZeroesInIntegralPart(object1);
        cleanLeadingZeroesInIntegralPart(object2);

        // Get integral and decimal parts
        String integralPart1 = object1.integralPart();
        String decimalPart1  = object1.decimalPart();
        String integralPart2 = object2.integralPart();
        String decimalPart2  = object2.decimalPart();

        // Use max decimal places for alignment
        int numDecimalPlaces = max(decimalPart1.length(), decimalPart2.length());
        // Pad decimal parts with zeros to equal length
        for(int i = decimalPart1.length() ; i < numDecimalPlaces ; i++){
            decimalPart1 += "0";
        }
        for(int i = decimalPart2.length() ; i < numDecimalPlaces ; i++){
            decimalPart2 += "0";
        }
        // Subtract as integers
        String diff = AFloat.subtractionOfPositiveIntegers(integralPart1.concat(decimalPart1), integralPart2.concat(decimalPart2));
        int count = 0;
        String finalDecimalPart = "", finalIntegralPart = "";
        // Split difference into decimal and integral parts
        for(int i = diff.length() - 1; i >= 0; i--){
            count++;
            if(count <= numDecimalPlaces){
                finalDecimalPart += diff.charAt(i);
            }else{
                finalIntegralPart += diff.charAt(i);
            }
        }

        // Reverse and clean
        finalDecimalPart  = AInteger.reverseString(finalDecimalPart);
        finalIntegralPart = AInteger.reverseString(finalIntegralPart);
        finalIntegralPart = AInteger.cleanLeadingZeroes(finalIntegralPart);
        finalDecimalPart  = AInteger.reverseString(AInteger.cleanLeadingZeroes(AInteger.reverseString(finalDecimalPart)));

        return finalIntegralPart.concat(".").concat(finalDecimalPart);
    }
     
    /**
     * Adds two AFloat numbers, handling signs.
     */
    public static AFloat addition(AFloat object1, AFloat object2){
        // Validate input
        validateInput(object1.getter());
        validateInput(object2.getter());

        // Normalize to float format if integer
        if(object1.isInt()){
            object1.setter(object1.getter().concat(".0"));
        }
        if(object2.isInt()){
            object2.setter(object2.getter().concat(".0"));
        }   
        //store originals
        AFloat ogObject1 = new AFloat(object1);
        AFloat ogObject2 = new AFloat(object2);

 
        // Clean leading zeroes
        cleanLeadingZeroesInIntegralPart(object1);
        cleanLeadingZeroesInIntegralPart(object2);

        String number1 = object1.getter();
        String number2 = object2.getter();

        // Determine signs
        List<String> signs = AInteger.signDecider(number1, number2);
        String sign1 = signs.get(0);
        String sign2 = signs.get(1);

        // Get magnitudes (absolute values)
        List<String> magnitudes = magnitudeMaker(number1, number2, sign1, sign2);
        String magnitude1 = magnitudes.get(0);
        String magnitude2 = magnitudes.get(1);
        object1.setter(magnitude1);
        object2.setter(magnitude2);

        String result = "";
        // Handle sign combinations
        if(sign1.equals("+") && sign2.equals("+")){
            result = additionOfPositiveFloat(object1, object2);
        }else if(sign1.equals("-") && sign2.equals("+")){
            result = SubtractionOfPosFloat(object2, object1);
        }else if(sign1.equals("+") && sign2.equals("-")){
            result = SubtractionOfPosFloat(object1, object2);
        }else{
            result = additionOfPositiveFloat(object1, object2);
            if(!result.equals("0.0")){
                result = "-".concat(result);
            }
        }

        // Parse and truncate to precision decimals
        AFloat answer = parse(result);
        answer.truncate(precision);

        object1.setter(ogObject1.getter());
        object2.setter(ogObject2.getter());

        return answer;
    }

    /**
     * Subtracts two AFloat numbers, handling signs.
     */
    public static AFloat subtraction(AFloat object1, AFloat object2){
        // Validate input
        validateInput(object1.getter());
        validateInput(object2.getter());

        // Normalize to float format if integer
        if(object1.isInt()){
            object1.setter(object1.getter().concat(".0"));
        }
        if(object2.isInt()){
            object2.setter(object2.getter().concat(".0"));
        }
        //store originals
        AFloat ogObject1 = new AFloat(object1);
        AFloat ogObject2 = new AFloat(object2);

        // Clean leading zeroes
        cleanLeadingZeroesInIntegralPart(object1);
        cleanLeadingZeroesInIntegralPart(object2);

        String number1 = object1.getter();
        String number2 = object2.getter();
        // Determine signs
        List<String> signs = AInteger.signDecider(number1, number2);
        String sign1 = signs.get(0);
        String sign2 = signs.get(1);

        // Get magnitudes (absolute values)
        List<String> magnitudes = magnitudeMaker(number1, number2, sign1, sign2);
        String magnitude1 = magnitudes.get(0);
        String magnitude2 = magnitudes.get(1);
        object1.setter(magnitude1);
        object2.setter(magnitude2);
        String result = "";
        // Handle sign combinations
        if(sign1.equals("+") && sign2.equals("+")){
           result = SubtractionOfPosFloat(object1, object2);
        }else if(sign1.equals("+") && sign2.equals("-")){
            result = additionOfPositiveFloat(object1, object2);
        }else if(sign1.equals("-") && sign2.equals("+")){
            result = additionOfPositiveFloat(object1, object2);
            if(!result.equals("0.0")){
                result = "-".concat(result);
            }    
        }else{
            result = SubtractionOfPosFloat(object2, object1);
        }

        // Parse and truncate to precision decimals
        AFloat answer = parse(result);
        answer.truncate(precision);

        object1.setter(ogObject1.getter());
        object2.setter(ogObject2.getter());

        return answer ;
    }
    
    /**
     * Multiplies two AFloat numbers, handling signs.
     */
    public static AFloat multiplication(AFloat object1, AFloat object2){
        // Validate input
        validateInput(object1.getter());
        validateInput(object2.getter());
        // Normalize to float format if integer
        if(object1.isInt()){
            object1.setter(object1.getter().concat(".0"));
        }
        if(object2.isInt()){
            object2.setter(object2.getter().concat(".0"));
        }
        //store originals
        AFloat ogObject1 = new AFloat(object1);
        AFloat ogObject2 = new AFloat(object2);

        // Determine signs
        List<String> signs = AInteger.signDecider(object1.getter(), object2.getter());
        String sign1 = signs.get(0);
        String sign2 = signs.get(1);

        // Clean leading zeroes
        cleanLeadingZeroesInIntegralPart(object1);
        cleanLeadingZeroesInIntegralPart(object2);

        // Get magnitudes (absolute values)
        List<String> magnitudes = magnitudeMaker(object1.getter(), object2.getter(), sign1, sign2);
        object1.setter(magnitudes.get(0));
        object2.setter(magnitudes.get(1));

        String resultSign = "";
        if(!sign1.equals(sign2)){
            resultSign = "-";
        }


        // Total decimal places in result
        int numDecimalPlaces = object1.decimalPlaces() + object2.decimalPlaces();
      
          
        // Multiply as integers (remove decimal)
        String num1 = object1.removeDecimal();
        String num2 = object2.removeDecimal();
       
        String product = AInteger.multiplicationOfPositiveIntegers(num1, num2);
        String finalDecimalPart = "", finalIntegralPart = "";
        int count = 0;
        // Split product into decimal and integral parts
        for(int i = product.length() - 1; i >= 0; i--){
            count++;
            if(count <= numDecimalPlaces){
                finalDecimalPart += product.charAt(i);
            }else{
                finalIntegralPart += product.charAt(i);
            } 
        }
          
        // Reverse and clean
        finalDecimalPart  = AInteger.reverseString(finalDecimalPart);
        finalIntegralPart = AInteger.reverseString(finalIntegralPart);
        finalIntegralPart = AInteger.cleanLeadingZeroes(finalIntegralPart);
        finalDecimalPart  = AInteger.reverseString(AInteger.cleanLeadingZeroes(AInteger.reverseString(finalDecimalPart)));

        // If result is zero, sign should be positive
        if(finalDecimalPart.equals("0") && finalIntegralPart.equals("0")){
            resultSign = "";
        }
        
        // Parse and truncate to precision decimals
        AFloat answer = parse(resultSign.concat(finalIntegralPart.concat(".").concat(finalDecimalPart)));
        answer.truncate(precision);

        object1.setter(ogObject1.getter());
        object2.setter(ogObject2.getter());
        return answer;
    }

    /**
     * Divides two AFloat numbers, handling signs and precision.
     */
    public static AFloat division(AFloat object1, AFloat object2){
        // Validate input
        validateInput(object1.getter());
        validateInput(object2.getter());
        // Normalize to float format if integer
        if(object1.isInt()){
            object1.setter(object1.getter().concat(".0"));
        }
        if(object2.isInt()){
            object2.setter(object2.getter().concat(".0"));
        }
        //store originals
        AFloat divisor = parse(makeStandard(object2));
        AFloat dividend = parse(makeStandard(object1));
         // Check for division by zero
         if(divisor.getter().equals("0.0")){
            throw new ArithmeticException("Division by zero error");
        }

        if(dividend.getter().equals("0.0")){
            return parse("0.0");
        }

        String decimalPart1  = object1.decimalPart();
        String decimalPart2  = object2.decimalPart();
        String integralPart1 = object1.integralPart();
        String integralPart2 = object2.integralPart(); 
        
         // Use max decimal places for alignment
        int numDecimalPlaces = max(decimalPart1.length(), decimalPart2.length());
         // Pad decimal parts with zeros to equal length
        for(int i = decimalPart1.length() ; i < numDecimalPlaces ; i++){
             decimalPart1 += "0";
         }
        for(int i = decimalPart2.length() ; i < numDecimalPlaces ; i++){
             decimalPart2 += "0";
        }
        object1.setter(integralPart1.concat(".").concat(decimalPart1));
        object2.setter(integralPart2.concat(".").concat(decimalPart2));

        // Determine signs
        List<String> signs = AInteger.signDecider(object1.getter(), object2.getter());
        String sign1 = signs.get(0);
        String sign2 = signs.get(1);
        String resultSign = "";
        if(!sign1.equals(sign2)){
            resultSign = "-";
        }
        
        
        // Remove decimals for division
        String num1 = object1.removeDecimal();
        String num2 = object2.removeDecimal();

        // Get magnitudes (absolute values)
        List<String> magnitudes = magnitudeMaker(num1, num2, sign1, sign2);
        String magnitude1 = magnitudes.get(0);
        String magnitude2 = magnitudes.get(1);
        object1.setter(magnitude1);
        object2.setter(magnitude2);
       
       

        // Scale numerator for decimal precision
        for(int i = 0; i < precision ; i++){
            magnitude1 += "0";
        }
        object1.setter(magnitude1);
        // Perform division as integers
       // System.out.println(magnitude1 + " " + magnitude2);
        String quotient =  AInteger.divisionOfPositiveIntegers(AInteger.cleanLeadingZeroes(object1.getter()) ,AInteger.cleanLeadingZeroes(object2.getter()));
        String finalDecimalPart = "", finalIntegralPart = "";
       // System.out.println(quotient);
        int count = 0;
        numDecimalPlaces = precision ;
        
            for(int i = quotient.length() - 1 ; i >= 0; i--){
                count++;
                if(count <= numDecimalPlaces){
                     finalDecimalPart += quotient.charAt(i);
                }else{
                     finalIntegralPart += quotient.charAt(i);
                }
            }
       
        // Reverse and clean
        finalDecimalPart   =   AInteger.reverseString(finalDecimalPart);
        finalIntegralPart  =   AInteger.reverseString(finalIntegralPart);
        finalIntegralPart  =   AInteger.cleanLeadingZeroes(finalIntegralPart);
        finalDecimalPart   =   AInteger.reverseString(AInteger.cleanLeadingZeroes(AInteger.reverseString(finalDecimalPart)));
        
        // If result is zero, sign should be positive
        if(finalDecimalPart.equals("0") && finalIntegralPart.equals("0")){
            resultSign = "";
        }
       
        // Parse and truncate to precision decimals
        AFloat answer = parse(resultSign.concat(finalIntegralPart.concat(".").concat(finalDecimalPart)));
       
        if(!multiplication(answer,divisor).getter().equals(dividend.getter())){
            int finalcount = answer.decimalPlaces();
            String ans = answer.getter();
            for(int i = finalcount ; i < 30 ; i++){
                ans += "0";
            }
            answer.setter(ans);
        }
        object1.setter(dividend.getter());
        object2.setter(divisor.getter());
        answer.truncate(precision);
        return answer;
    }
}