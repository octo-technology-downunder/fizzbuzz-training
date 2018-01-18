package MockAndStub;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
            Scanner input = new Scanner(System.in);
            Calculator calculator = new Calculator();
            //ask for user input
            System.out.print("Please enter a letter: ");
            String inputString = input.nextLine();
            System.out.print(calculator.calculate(inputString));

    }
}
