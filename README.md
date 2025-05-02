# CalcEngine - An Arbitrary Precision Calculator
## Introduction :
CalcEngine is like a Calculator which can compute some basic operations like Addition , Subtraction , Multiplication , Division for both integers and floating point number.It can have arbitrary precision according to your comfort. Here you can do Arithmetic Operations on numbers which standard java can't afford to store.

## Java Number Limits

Java provides several built-in primitive types for storing numeric values, each with fixed maximum and minimum bounds:

- `int`: -2,147,483,648 to 2,147,483,647 (32-bit signed)
- `long`: -9,223,372,036,854,775,808 to 9,223,372,036,854,775,807 (64-bit signed)
- `float`: Approximately ±3.4e38 with ~7 digits of precision (32-bit)
- `double`: Approximately ±1.8e308 with ~15 digits of precision (64-bit).

But our Calculator Engine supports numbers of the order approximately 10^INTMAX. That is  Why you should use this.
# CalcEngine

## What's Inside

This project provides an arbitrary-precision arithmetic engine in Java, having two main classes:
- **AInteger**: For large integer numbers
- **AFloat**: For large floating-point numbers

You will also find:
- `MyInfArith.java`: The main entry point for command-line arithmetic operations
- `CalcEngine.py`: A Python script to run calculations from the command line
- `arbitraryarithmetic/aarithmetic.jar`: The compiled Java library (JAR file)
- `report` : To learn more about this Project.

As an external user, you can only access the public methods of `AInteger` and `AFloat`. These include:
- `addition`
- `subtraction`
- `multiplication`
- `division`
- `getter`
- `setPrecision`
- Constructors and `parse` methods
   **parse** is useful for creating instances of the class AInteger and AFloat. **getter()** for accessing the number which is a private attribute of **AFloat/AInteger** .

## Setup
### AArithmetic Library

It is a java archive from which you can use the library.


### Installation

1.download the `aarithmetic.jar` from the `arbitraryarithmetic` directory.

2.Place `aarithmetic.jar` in your desired location.

##  How to use jar file 

To use `aarithmetic.jar` in your Java project, follow these steps:

### 1. Write a main java file by which you will access classes from the .jar file.
Ensure your code imports and uses classes from the JAR:
 
import arbitraryarithmetic.AInteger;
import arbitraryarithmetic.AFloat;

These 2 statements are required to be written at the top.
### 2. Compile the file 
use **javac -cp .;arbitraryarithmetic/aarithmetic.jar yourmain.java** to compile your main java file.
### 3. To run the code 
use **java -cp .;arbitraryarithmetic/aarithmetic.jar yourmain.java** to run 

## How to write code ?
### AInteger 
You will have the public methods `parse` for creation of instance , methods like addition , subtraction , division , multiplication expect 2 arguments and they do what their names are suggesting .For example if you want to add 15, 25 write **AInteger.addition(parse("15"),parse("25))** it will return you a object of type AInteger , you can use .getter() to see the answer i.e 40.
### AFloat
Apart from the public methods in AInteger you will have a public method `setPrecision` here which expects an integer argument. You can set the Precision arbitrarily from this function. The default precision is set to 30. For example if you want to add 12.25 , 1.5 then you can write **AFloat.addition(parse("12.25"),parse("1.5"))** and use .getter() to see the answer i.e 13.75. Note that you can give integers as well in the argumnets.