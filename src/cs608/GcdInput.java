package cs608;
import java.util.Scanner;

public class GcdInput {
    public static void main(String[] args) {
       Scanner scanner = new Scanner(System.in);
       System.out.println("Enter your first number:");
       int a = scanner.nextInt();
       System.out.println("Enter your second number:");
       int b = scanner.nextInt();
            while (a != b) {
                if (a > b) {
                    a = a-b;
                } else {
                    b = b-a;
                }
            }
            System.out.println("The GCD of the given numbers is " + a);
        }
    }
