package surveyor

import grails.test.*

class TeamControllerTests extends ControllerUnitTestCase {
    User instructor = new User(name: 'Nic Mcphee', email: 'mcphee@morris.umn.edu')
    Course softwareDesign = new Course(abbreviation: 'CSCI 3601', name: 'Software Design', term: 'Spring', year: '2012', owner: instructor)
    User ian = new User(name: 'Ian M', email: 'ian@ian.com')
    User emma = new User(name: 'Emma I', email: 'emma@emma.com')
    Project releaseOne = new Project(name: 'Release One', description: '', course: softwareDesign)
    
    protected void setUp() {
        super.setUp()
        mockForConstraintsTests(Team)
    }

    protected void tearDown() {
        super.tearDown()
    }

/*    void testGroupCreation() {
        mockDomain(Project)
        releaseOne.params.groupNumber = 2
        releaseOne.createAndSaveMany()
        assertEquals 0, 'Group 0'.id
    }*/
}
