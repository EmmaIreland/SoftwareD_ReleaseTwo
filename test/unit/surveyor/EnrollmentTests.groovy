package surveyor

import grails.test.*

class EnrollmentTests extends GrailsUnitTestCase {
    User dave = new User(name: 'Dave Ericksen', email: 'ericksen@umn.edu')
    Course engl2501 = new Course(abbreviation: 'ENGL 2501', name: 'Literary Studies', term: 'Spring', year: '2012', owner: dave)
    User kevin = new User(name: 'Kevin', email: 'virat001@umn.edu')
    
    protected void setUp() {
        super.setUp()
        mockForConstraintsTests(Enrollment)
        mockForConstraintsTests(User)
        mockForConstraintsTests(Course)
        
        mockDomain(Enrollment, [])
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testEnrollment() {
        Enrollment enrollment = new Enrollment(course: engl2501, student: kevin )
        assertTrue enrollment.validate()
    }
    
    void testNullEnrollment() {
        Enrollment enrollment = new Enrollment()
        assertFalse enrollment.validate()
        assertEquals 'nullable', enrollment.errors['course']
        assertEquals 'nullable', enrollment.errors['student']
    }
    
    void testToString() {
        Enrollment enrollment = new Enrollment(course: engl2501, student: kevin )
        assertEquals enrollment.toString(), kevin.name + ' in ' + engl2501.abbreviation
    }
    
    void testNoDuplicates() {
        Enrollment firstEnrollment = new Enrollment(course: engl2501, student: kevin )
        assertTrue firstEnrollment.validate()
        
        Enrollment secondEnrollment = new Enrollment(course: engl2501, student: kevin )
        mockDomain(Enrollment, [firstEnrollment])
        assertFalse secondEnrollment.validate()
        assertEquals 'validator', secondEnrollment.errors['course']
    }
}
