package surveyor

import grails.test.*

class SurveyAssignmentTests extends GrailsUnitTestCase {
    
    User nic = new User(name: 'Nic McPhee', email: 'mcphee@gmail.com')
    User sarah = new User(name: 'Sarah Buchanan', email: 'buchanan@gmail.com')

    Survey survey = new Survey(owner: nic)
    
    protected void setUp() {
        super.setUp()
        mockForConstraintsTests(SurveyAssignment)
        mockDomain(SurveyAssignment, [])
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testValidation() {
        SurveyAssignment surveyAssignment = new SurveyAssignment(survey: survey, student: sarah)
        assertTrue surveyAssignment.validate()
    }
}
