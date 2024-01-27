package com.driver;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StudentService {

    private final Map<String, Student> students = new HashMap<>();
    private final Map<String, Teacher> teachers = new HashMap<>();
    private final Map<String, String> studentTeacherPairs = new HashMap<>();

    public void addStudent(Student student) {
        students.put(student.getName(), student);
    }

    public void addTeacher(Teacher teacher) {
        teachers.put(teacher.getName(), teacher);
    }

    public void addStudentTeacherPair(String studentName, String teacherName) {
        if (students.containsKey(studentName) && teachers.containsKey(teacherName)) {
            studentTeacherPairs.put(studentName, teacherName);
        } else {
            // Handle error case - student or teacher not found
            throw new IllegalArgumentException("Student or teacher not found");
        }
    }

    public Student getStudentByName(String name) {
        return students.get(name);
    }

    public Teacher getTeacherByName(String name) {
        return teachers.get(name);
    }

    public List<String> getStudentsByTeacherName(String teacherName) {
        List<String> studentsTaughtByTeacher = new ArrayList<>();
        for (Map.Entry<String, String> entry : studentTeacherPairs.entrySet()) {
            if (entry.getValue().equals(teacherName)) {
                studentsTaughtByTeacher.add(entry.getKey());
            }
        }
        return studentsTaughtByTeacher;
    }

    public List<String> getAllStudents() {
        return new ArrayList<>(students.keySet());
    }

    public void deleteTeacherByName(String teacherName) {
        teachers.remove(teacherName);
        // Remove associated students
        List<String> studentsToRemove = new ArrayList<>();
        for (Map.Entry<String, String> entry : studentTeacherPairs.entrySet()) {
            if (entry.getValue().equals(teacherName)) {
                studentsToRemove.add(entry.getKey());
            }
        }
        studentsToRemove.forEach(students::remove);
        // Remove teacher from student-teacher pairs
        studentTeacherPairs.values().removeIf(teacherName::equals);
    }

    public void deleteAllTeachers() {
        teachers.clear();
        students.clear();
        studentTeacherPairs.clear();
    }
}
