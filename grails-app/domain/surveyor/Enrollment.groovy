package surveyor

class Enrollment {

    static belongsTo = [course: Course, student: User]
    
    static constraints = {
        student()
        course validator: { course, enrollment ->
            Enrollment.findByCourseAndStudent(course, enrollment.student) == null
        }
    }
    
    String toString() {
        student.name + " in " + course.abbreviation
    }
}
