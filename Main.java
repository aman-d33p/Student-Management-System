import java.sql.*;
import java.util.Scanner;

public class Main {
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n===== Student Management System =====");
            System.out.println("1. Add Student");
            System.out.println("2. View All Students");
            System.out.println("3. Search Student by Roll No");
            System.out.println("4. Update Student");
            System.out.println("5. Delete Student");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            int choice = sc.nextInt();
            sc.nextLine(); // buffer clear

            switch (choice) {
                case 1: addStudent(); break;
                case 2: viewAllStudents(); break;
                case 3: searchStudent(); break;
                case 4: updateStudent(); break;
                case 5: deleteStudent(); break;
                case 6:
                    System.out.println("Thank you!");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    static void addStudent() {
        try {
            System.out.print("Enter Name: ");
            String name = sc.nextLine();
            System.out.print("Enter Roll No: ");
            String rollNo = sc.nextLine();
            System.out.print("Enter Course: ");
            String course = sc.nextLine();
            System.out.print("Enter Marks: ");
            double marks = sc.nextDouble();
            sc.nextLine();

            Connection conn = DatabaseConnection.getConnection();
            String sql = "INSERT INTO students (name, roll_no, course, marks) VALUES (?, ?, ?, ?)";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, name);
            pst.setString(2, rollNo);
            pst.setString(3, course);
            pst.setDouble(4, marks);

            int rows = pst.executeUpdate();
            if (rows > 0) {
                System.out.println("Student added successfully!");
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void viewAllStudents() {
        try {
            Connection conn = DatabaseConnection.getConnection();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM students");

            System.out.println("\n----- All Students -----");
            boolean found = false;
            while (rs.next()) {
                found = true;
                System.out.println("ID: " + rs.getInt("id") +
                        " | Name: " + rs.getString("name") +
                        " | Roll No: " + rs.getString("roll_no") +
                        " | Course: " + rs.getString("course") +
                        " | Marks: " + rs.getDouble("marks"));
            }
            if (!found) {
                System.out.println("No students found!");
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void searchStudent() {
        try {
            System.out.print("Enter Roll No to search: ");
            String rollNo = sc.nextLine();

            Connection conn = DatabaseConnection.getConnection();
            String sql = "SELECT * FROM students WHERE roll_no = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, rollNo);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                System.out.println("\nStudent Found:");
                System.out.println("ID: " + rs.getInt("id") +
                        " | Name: " + rs.getString("name") +
                        " | Roll No: " + rs.getString("roll_no") +
                        " | Course: " + rs.getString("course") +
                        " | Marks: " + rs.getDouble("marks"));
            } else {
                System.out.println("Student not found!");
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void updateStudent() {
        try {
            System.out.print("Enter Roll No to update: ");
            String rollNo = sc.nextLine();
            System.out.print("Enter New Name: ");
            String name = sc.nextLine();
            System.out.print("Enter New Course: ");
            String course = sc.nextLine();
            System.out.print("Enter New Marks: ");
            double marks = sc.nextDouble();
            sc.nextLine();

            Connection conn = DatabaseConnection.getConnection();
            String sql = "UPDATE students SET name=?, course=?, marks=? WHERE roll_no=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, name);
            pst.setString(2, course);
            pst.setDouble(3, marks);
            pst.setString(4, rollNo);

            int rows = pst.executeUpdate();
            if (rows > 0) {
                System.out.println("Student updated successfully!");
            } else {
                System.out.println("Student not found!");
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void deleteStudent() {
        try {
            System.out.print("Enter Roll No to delete: ");
            String rollNo = sc.nextLine();

            Connection conn = DatabaseConnection.getConnection();
            String sql = "DELETE FROM students WHERE roll_no = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, rollNo);

            int rows = pst.executeUpdate();
            if (rows > 0) {
                System.out.println("Student deleted successfully!");
            } else {
                System.out.println("Student not found!");
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}