package surveyor

import grails.test.*

class CourseTests extends GrailsUnitTestCase {
    User instructor = new User(name: 'Nic Mcphee', email: 'mcphee@morris.umn.edu')
    
    protected void setUp() {
        super.setUp()
        mockForConstraintsTests(Course)
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testValidCourse() {
        Course course = new Course(abbreviation: 'CSCI 3601', name: 'Software Design', term: 'Spring', year: '2012', owner: instructor)
        assertTrue course.validate()
    }

    void testBlankAbbreviation() {
        Course course = new Course(abbreviation: '', name: 'Name', term: 'Fall', year: '2012', owner: instructor)
        assertFalse course.validate()
        assertEquals 'blank', course.errors['abbreviation']
    }

    void testBlankName() {
        Course course = new Course(abbreviation: 'ENGL 2501', name: '', term: 'Spring', year: '2012', owner: instructor)
        assertFalse course.validate()
        assertEquals 'blank', course.errors['name']
    }

    void testInvalidTerm() {
        Course course = new Course(abbreviation: 'STAT 2611', name: 'Mathematical Statistics', term: 'Winter', year: '2012', owner: instructor)
        assertFalse course.validate()
        assertEquals 'inList', course.errors['term']
    }

    void testSubTwoThousandYear() {
        Course course = new Course(abbreviation: 'FREN 1001', name: 'Beginning French I', term: 'Fall', year: '1999', owner: instructor)
        assertFalse course.validate()
        assertEquals 'validator', course.errors['year']
    }

    void testNonYear() {
        Course course = new Course(abbreviation: 'MUS 1212', name: 'Individual Performace Study', term: 'Summer I', year: 'never', owner: instructor)
        assertFalse course.validate()
        assertEquals 'validator', course.errors['year']
    }

    void testToString() {
        Course course = new Course(abbreviation: 'FREN 1001', name: 'Beginning French I', term: 'Fall', year: '1999', owner: instructor)
        assertEquals  course.abbreviation + ': ' + course.name, course.toString()
    }
    
    void testOrphan() {
        Course course = new Course(abbreviation: 'CSCI 3601', name: 'Software Design', term: 'Spring', year: '2012')
        assertFalse course.validate()
        assertEquals 'nullable', course.errors['owner']
    }
}
