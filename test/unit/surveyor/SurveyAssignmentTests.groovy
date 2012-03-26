package surveyor

import grails.test.*

class SurveyAssignmentTests extends GrailsUnitTestCase {
    
    User nic = new User(name: 'Nic McPhee', email: 'mcphee@gmail.com')
    User sarah = new User(name: 'Sarah Buchanan', email: 'buchanan@gmail.com')
    User matt = new User(name: 'matt McPhee', email: 'mcphee@gmail.com')
    
    Survey survey = new Survey(owner: nic)
    Survey survey2 = new Survey(owner: nic)
    Survey survey3 = new Survey(owner: nic)
    
    
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
    
    void testMultipleStudentsPerSurvey() {
        SurveyAssignment surveyAssignment = new SurveyAssignment(survey: survey, student: sarah)
        SurveyAssignment surveyAssignment2 = new SurveyAssignment(survey: survey, student: nic)
        SurveyAssignment surveyAssignment3 = new SurveyAssignment(survey: survey, student: matt)
        
        assertTrue surveyAssignment.validate()
        assertTrue surveyAssignment2.validate()
        assertTrue surveyAssignment3.validate()
    }
    
    void testMultipleSurveysPerStudent() {
        SurveyAssignment surveyAssignment = new SurveyAssignment(survey: survey, student: matt)
        SurveyAssignment surveyAssignment2 = new SurveyAssignment(survey: survey2, student: matt)
        SurveyAssignment surveyAssignment3 = new SurveyAssignment(survey: survey3, student: matt)
        
        assertTrue surveyAssignment.validate()
        assertTrue surveyAssignment2.validate()
        assertTrue surveyAssignment3.validate()        
    }
    
    
    void testNoDuplicateStudents() {
        SurveyAssignment surveyAssignment = new SurveyAssignment(survey: survey, student: sarah)
        assertTrue surveyAssignment.validate()
        SurveyAssignment surveyAssignment2 = new SurveyAssignment(survey: survey, student: sarah)
        mockDomain(SurveyAssignment, [surveyAssignment2, surveyAssignment])
        assertFalse surveyAssignment.validate()
        assertFalse surveyAssignment2.validate()
    }
    
}
