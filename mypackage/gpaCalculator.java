package mypackage;

import java.util.Scanner;

class IncorrectDataException extends Exception {

    public IncorrectDataException(String message) {
        super(message);
    }
}

class NumberOfCoursesException extends Exception {

    public NumberOfCoursesException(String message) {
        super(message);
    }
}

public class gpaCalculator {

    static private String[] coursesTaken;
    static private float[] creditUnits;
    static private float[] coursePerformance;
    static private char[] courseGrade;
    static private int[] coursePoint;
    static private float gpa;

    public static void main(String[] args) {

        setCourseInfo();
        setGrade();
        setGpa();
        displayResults();
    }

    private static void setCourseInfo() {

        boolean exitCondition = true;
        Scanner input = new Scanner(System.in);

        while (exitCondition) {

            try {

                System.out.println("How many courses did you take this semester? ");
                int numberOfCourses = input.nextInt();

                if (numberOfCourses <= 0) {
                    throw new NumberOfCoursesException("You can't take less than or a zero number of courses.");
                }

                coursesTaken = new String[numberOfCourses];
                creditUnits = new float[numberOfCourses];
                coursePerformance = new float[numberOfCourses];

                System.out.println("I'll now ask you a few questions about the courses and your performance in them.");
                int index = 0;

                while (index < numberOfCourses) {

                    try {

                        System.out.println("Give me the name of the course: ");
                        coursesTaken[index] = input.next().toString();
                        input.nextLine();

                        System.out.println("How many credit units is it? ");
                        creditUnits[index] = input.nextInt();

                        System.out.println("What was your overall score? ");
                        coursePerformance[index] = input.nextFloat();

                        if (creditUnits[index] <= 0 || coursePerformance[index] < 0 || coursePerformance[index] > 100
                                || coursesTaken[index].length() == 0) {

                            throw new IncorrectDataException(
                                    "You have put in incorrect data. Restart the program and try again.");
                        }
                    } catch (IncorrectDataException e) {

                        System.out.println(e.getMessage());
                        continue;
                    }

                    index++;
                }

                exitCondition = false;

            } catch (NumberOfCoursesException e) {

                System.out.println(e.getMessage());
                continue;
            }
        }
        input.close();

    }

    private static void setGrade() {

        courseGrade = new char[coursePerformance.length];
        coursePoint = new int[coursePerformance.length];

        int index = 0;
        while (index < coursePerformance.length) {

            if (coursePerformance[index] >= 70) {

                courseGrade[index] = 'A';
                coursePoint[index] = 5;

            } else if (coursePerformance[index] < 70 && coursePerformance[index] >= 60) {

                courseGrade[index] = 'B';
                coursePoint[index] = 4;

            } else if (coursePerformance[index] < 60 && coursePerformance[index] >= 50) {

                courseGrade[index] = 'C';
                coursePoint[index] = 3;

            } else if (coursePerformance[index] < 50 && coursePerformance[index] >= 45) {

                courseGrade[index] = 'D';
                coursePoint[index] = 2;

            } else if (coursePerformance[index] < 45 && coursePerformance[index] >= 40) {

                courseGrade[index] = 'E';
                coursePoint[index] = 1;

            } else {

                courseGrade[index] = 'F';
                courseGrade[index] = 0;
            }
            index++;
        }
    }

    private static void setGpa() {

        float totalCreditUnits = 0f;
        float totalCreditPointTimesUnit = 0f;

        for (float credits : creditUnits) {
            totalCreditUnits += credits;
        }
        for (int i = 0; i < creditUnits.length; i++) {
            totalCreditPointTimesUnit += creditUnits[i] * coursePoint[i];
        }
        gpa = totalCreditPointTimesUnit / totalCreditUnits;
    }

    private static void displayResults() {
        System.out.println("|----------------------------|-----------------------|------------|---------------------|");
        System.out.println("| COURSE & CODE              | COURSE UNIT           | GRADE      | GRADE-UNIT          |");
        System.out.println("|----------------------------|-----------------------|------------|---------------------|");

        for (int i = 0; i < coursesTaken.length; i++) {
            System.out.printf("| %-26s | %-21d | %-10c | %-19d |\n", coursesTaken[i], (int)creditUnits[i], courseGrade[i],
                    coursePoint[i]);
        }

        System.out.println("|---------------------------------------------------------------------------------------|");
        System.out.printf("\nYour GPA is = %.2f to 2 decimal places.\n", gpa);
    }
}