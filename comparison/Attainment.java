import java.util.Comparator;

/*
    Author: Iiro Koskinen H299947
*/

// A class that creates an attainment, and implements a way to compare attainments
// members: String courseCode = the courses course code, String studentNumber = the students student number,
// int grade = the grade the student got from the course at hand.
public class Attainment implements Comparable<Attainment>{
    private String courseCode;
    private String studentNumber;
    private int grade;
    public static final Comparator<Attainment> CODE_STUDENT_CMP = new Comparator<Attainment>() {
        @Override
        public int compare(Attainment a, Attainment b) {
           int cmp = a.courseCode.compareTo(b.courseCode);
           if (cmp == 0) {
            cmp = a.studentNumber.compareTo(b.studentNumber);
           }
           return cmp;
        }
    };
    public static final Comparator<Attainment> CODE_GRADE_CMP = new Comparator<Attainment>() {
        @Override
        public int compare(Attainment a, Attainment b) {
            int cmp = a.courseCode.compareTo(b.courseCode);
            if (cmp == 0) {
                cmp = Integer.compare(b.grade, a.grade);
            }
            return cmp;
        }
    };

    // A constructor
    public Attainment(String courseCode, String studentNumber, int grade) {
        this.courseCode = courseCode;
        this.studentNumber = studentNumber;
        this.grade = grade;
    }

    // obvious getters
    public String getCourseCode() {
        return this.courseCode;
    }

    public String getStudentNumber() {
        return this.studentNumber;
    }

    public int getGrade() {
        return this.grade;
    }

    @Override
    public String toString() {
        return String.format("%s %s %d%n", courseCode, studentNumber, grade);
    }

    @Override
    public int compareTo(Attainment other) {
        int cmp = studentNumber.compareTo(other.studentNumber);
        if (cmp == 0) {
            cmp = courseCode.compareTo(other.courseCode);
        }
        return cmp;
    }


}
