package surveyor

import grails.test.*

class SurveyTests extends GrailsUnitTestCase {
    def futureDate = Date.parse("yyyy-MM-dd", "2012-09-15") // TODO deprecated?
    
    User nic = new User(name: 'Nic McPhee', email: 'mcphee@umn.edu')
    Course softwareDesign = new Course(abbreviation: 'CSCI 3601', name: 'Software Design', term: 'Spring', year: '2012', owner: nic)
    
    User dave = new User(name: 'Dave Ericksen', email: 'ericksen@umn.edu')
    Course litStudies = new Course(abbreviation: 'ENGL 2501', name: 'Literary Studies', term: 'Spring', year: '2012', owner: dave)
    
    Project releaseOne = new Project(name: 'Release One', description: 'Release One', course: softwareDesign)
    Team camelCasers = new Team(name: 'CamelCasers', project: releaseOne)
    
    Project estimation = new Project(name: 'LesMeilleursIngenieurs', description: 'Estimation Lab', course: softwareDesign)
    Project poems = new Project(name: 'Poetry Midterm', description: 'Annotate poems', course: litStudies)
    Team englishMajors = new Team(name: 'English majors', project: poems)
    
    protected void setUp() {
        super.setUp()
        mockForConstraintsTests(Survey)
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testValidSurvey() {
        Survey testSurvey = new Survey(name: 'Test', description: 'A test', dueDate: futureDate, course: softwareDesign, project: releaseOne, team: camelCasers)
        assertTrue testSurvey.validate()
    }
    
    void testNoNameSurvey() {
        Survey testSurvey = new Survey(name: '', description: 'no name', dueDate: futureDate, course: softwareDesign, project: releaseOne, team: camelCasers)
        assertFalse testSurvey.validate()
        assertEquals 'blank', testSurvey.errors['name']
    }
    
    void testAllThatCanBeEmpty() {
        Survey testSurvey = new Survey(name: 'So empty', description: '')
        assertTrue testSurvey.validate()
    }
    
    void testOnlyteamGiven() {
        Survey testSurvey = new Survey(name: 'Only team', description: '', team: camelCasers)
        assertTrue testSurvey.validate()
    }
    
    void testOnlyProjectGiven() {
        Survey testSurvey = new Survey(name: 'Only team', description: '', project: releaseOne)
        assertTrue testSurvey.validate()
    }
    
    void testOnlyCourseGiven() {
        Survey testSurvey = new Survey(name: 'Only team', description: '', course: softwareDesign)
        assertTrue testSurvey.validate()
    }
    
    void testInvalidteam() {
        Survey testSurvey = new Survey(name: 'Invalid team', description: '', course: softwareDesign, project: releaseOne, team: englishMajors)
        assertFalse testSurvey.validate()
        assertEquals 'validator', testSurvey.errors['team']
    }
    
    void testInvalidProject() {
        Survey testSurvey = new Survey(name: 'Invalid project', description: '', course: softwareDesign, project: poems, team: camelCasers)
        assertFalse testSurvey.validate()
        assertEquals 'validator', testSurvey.errors['team']
    }
    
    void testInvalidCourse() {
        Survey testSurvey = new Survey(name: 'Invalid project', description: '', course: softwareDesign, project: poems, team: englishMajors)
        assertFalse testSurvey.validate()
        assertEquals 'validator', testSurvey.errors['team']
    }
}
