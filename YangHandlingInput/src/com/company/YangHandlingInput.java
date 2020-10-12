// Tyler Yang 9/4/22 Homework 1
// Some extra credit questions attempted (1 + 2)
//
// Notes:
// Ran on Windows 10, worked in IntelliJ, could not get Java to work in command prompt on Windows. This is most
// likely due to how my drives are set up; however, I was able to run the java command in the Ubunto VM though a shared
// folder oddly enough.
//
// Also: I had to change the Switch statements from what was recommended in order to run on the Ubunto VM.
// It couldn't be "case -> { "code"}

package com.company;
import java.util.Scanner;

public class YangHandlingInput {

    public static void main(String[] args) {         //main simply calls each function/method
        Menu_Default();
        Menu_Choices();
    }

    public static void Menu_Default () {             // function/method for displaying menu options
        System.out.println("1: Enter Student Name");
        System.out.println("2: Enter Student's Academic Year");
        System.out.println("3: Enter Student's GPA");
        System.out.println("4: Display Student's Info");
        System.out.println("5: Exit");
        System.out.println("Enter a number from the corresponding options above:");
    }

    public static void Menu_Choices(){               // Function/method for choosing menu options and for information
        int UserInput = 0;                           // regarding the student
        String StName = " ";
        String StYear = " ";                         // Initialization to allow for some fault checking for option 4
        double StGPA = -1;
        boolean StCheck = false;

        Scanner myScanner = new Scanner(System.in);

        do {
            try {                                           // Attempt at error handling the UserInput if they do not
                UserInput = myScanner.nextInt();            // enter a valid integer
            } catch (Exception ex) {
                UserInput = 0;
                myScanner.next();                          // Clears out buffer to prevent infinite loop issue
            }                                              // if an invalid choice was entered

            switch (UserInput) {
                case 1:
                    System.out.println("Enter Student's Name");
                    StName = myScanner.next();              // Could not get it to allow first and last name with
                    BudgetClear();                          // nextLine()
                    System.out.println("***Name recorded***");
                    Menu_Default();
                    break;
                case 2:
                    System.out.println("Enter an Academic Year (uppercase)");
                    System.out.println("Example: Senior");
                    while (!StCheck) {                      //Error checking with ValidYear(String); to see if they
                        StYear = myScanner.next();          //entered a Freshman/Sophomore/Junior/Senior
                        StCheck = ValidYear(StYear);
                    }
                    BudgetClear();
                    System.out.println("***Academic Year recorded***");
                    Menu_Default();
                    break;
                case 3:
                    System.out.println("Enter Student's GPA (between 0.0 - 4.0)");
                    StGPA = ValidGPA();                    // Uses ValidGPA() for error checking for a double
                    BudgetClear();
                    System.out.println("***GPA recorded***");
                    Menu_Default();
                    break;
                case 4 :
                    if (StName.equals(" ") || StYear.equals(" ") || StGPA < 0.0) {
                        BudgetClear();                    // Error checking if enough information has been given
                        Menu_Default();
                        System.out.println("Please answer all questions before attempting to display student info");
                    } else {
                        System.out.println("Student name: " + StName);
                        System.out.println("Student academic year: " + StYear);
                        System.out.println("Student GPA: " + StGPA);
                        System.out.println("Input anything to continue");
                        myScanner.next();

                        UserInput = 5;                 // Exits the loop as this function is recursive
                        BudgetClear();                 // The user needs to type anything in order to continue
                        Menu_Default();
                        Menu_Choices();                // ^^ The function is called once more to repeat after
                    }                                  // it's completed
                    break;
                case 5:
                    BudgetClear();
                    System.out.println("Have a nice day!");
                    break;
                default:
                    BudgetClear();                     // Default case if anything anything is incorrectly entered
                    Menu_Default();
                    System.out.println("Please enter a valid menu option");
                    break;
            }
        } while (UserInput!=5);
    }

    public static boolean ValidYear(String year){
        boolean Byear;

        switch (year){                                 // Error handling for a valid academic year
            case "Freshman":                           // automatically suggested to join cases
            case "Sophomore":
            case "Junior":
            case "Senior" :
                Byear = true;
                    break;
            default :
                Byear = false;                       // Since it's a string, it doesn't need error checking as much
                System.out.println("Please enter a valid Academic year (Uppercase) ");
                System.out.println("Example: Freshman");
                break;
        }
        return Byear;
    }

    public static double ValidGPA(){
        double gpa =-1;
        Scanner myScanner = new Scanner(System.in);

        while (gpa < 0.0 || gpa > 4.0) {                // This allows it to loop if an incorrect data type is entered
            try {
                gpa = myScanner.nextDouble();
            } catch (Exception ex) {
                System.out.println("Please enter a correct GPA score format");
                System.out.println("Example: 3.65 or 2.93");
                myScanner.next();
            }

            if (gpa < 0.0 || gpa > 4.0) {             // This is applied to double values that are out of range
                System.out.println("Please enter a valid GPA between 0.0 and 4.0");
            }                                         // Does not check for extremely long doubles
        }
        return gpa;
    }

    public static void BudgetClear(){   //quick attempt at clearing the screen in a make-shift manner
        for (int x = 0; x <50; x++){
            System.out.println(" ");
        }
    }
}
