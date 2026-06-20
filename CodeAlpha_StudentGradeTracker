import java.util.ArrayList;
import java.util.Scanner;

    class Student {
        private int id;
        private String name;
        private double grade;

        public Student(int id, String name, double grade) {
            this.id = id;
            this.name = name;
            this.grade = grade;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public double getGrade() {
            return grade;
        }

        public String getPerformance() {
            if (grade >= 90)
                return "A+";
            else if (grade >= 80)
                return "A";
            else if (grade >= 70)
                return "B";
            else if (grade >= 60)
                return "C";
            else
                return "Fail";
        }
    }

    public class StudentGradeTracker {

        static ArrayList<Student> students = new ArrayList<>();
        static Scanner sc = new Scanner(System.in);

        public static void main(String[] args) {

            while (true) {
                System.out.println("\n===== STUDENT GRADE TRACKER =====");
                System.out.println("1. Add Student");
                System.out.println("2. View Report");
                System.out.println("3. Search Student");
                System.out.println("4. Exit");
                System.out.print("Choose Option: ");

                int choice = sc.nextInt();

                switch (choice) {
                    case 1:
                        addStudent();
                        break;
                    case 2:
                        generateReport();
                        break;
                    case 3:
                        searchStudent();
                        break;
                    case 4:
                        System.out.println("Thank you for using Student Grade Tracker!");
                        return;
                    default:
                        System.out.println("Invalid Choice!");
                }
            }
        }

        private static void addStudent() {

            System.out.print("Enter Student ID: ");
            int id = sc.nextInt();
            sc.nextLine();

            System.out.print("Enter Student Name: ");
            String name = sc.nextLine();

            System.out.print("Enter Grade: ");
            double grade = sc.nextDouble();

            students.add(new Student(id, name, grade));

            System.out.println("Student Added Successfully!");
        }

        private static void generateReport() {

            if (students.isEmpty()) {
                System.out.println("No student records found.");
                return;
            }

            double total = 0;
            double highest = students.get(0).getGrade();
            double lowest = students.get(0).getGrade();

            Student topper = students.get(0);

            System.out.println("\n------------ REPORT ------------");
            System.out.printf("%-10s %-20s %-10s %-10s\n",
                    "ID", "NAME", "GRADE", "STATUS");

            for (Student s : students) {

                System.out.printf("%-10d %-20s %-10.2f %-10s\n",
                        s.getId(),
                        s.getName(),
                        s.getGrade(),
                        s.getPerformance());

                total += s.getGrade();

                if (s.getGrade() > highest) {
                    highest = s.getGrade();
                    topper = s;
                }

                if (s.getGrade() < lowest) {
                    lowest = s.getGrade();
                }
            }

            double average = total / students.size();

            System.out.println("\n========= SUMMARY =========");
            System.out.println("Total Students : " + students.size());
            System.out.printf("Average Grade  : %.2f\n", average);
            System.out.printf("Highest Grade  : %.2f\n", highest);
            System.out.printf("Lowest Grade   : %.2f\n", lowest);

            System.out.println("\nTop Performer:");
            System.out.println(topper.getName() +
                    " (Grade: " + topper.getGrade() + ")");
        }

        private static void searchStudent() {

            System.out.print("Enter Student ID: ");
            int searchId = sc.nextInt();

            for (Student s : students) {

                if (s.getId() == searchId) {

                    System.out.println("\nStudent Found!");
                    System.out.println("ID      : " + s.getId());
                    System.out.println("Name    : " + s.getName());
                    System.out.println("Grade   : " + s.getGrade());
                    System.out.println("Status  : " + s.getPerformance());
                    return;
                }
            }

            System.out.println("Student Not Found!");
        }
    }

