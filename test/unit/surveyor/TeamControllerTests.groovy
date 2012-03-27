package surveyor

import grails.test.*

class TeamControllerTests extends ControllerUnitTestCase {
    User instructor = new User(name: 'Nic Mcphee', email: 'mcphee@morris.umn.edu')
    Course softwareDesign = new Course(abbreviation: 'CSCI 3601', name: 'Software Design', term: 'Spring', year: '2012', owner: instructor)
    User ian = new User(name: 'Ian M', email: 'ian@ian.com')
    User emma = new User(name: 'Emma I', email: 'emma@emma.com')
    Project releaseOne = new Project(name: 'Release One', description: '', course: softwareDesign, teams: [])
    
    protected void setUp() {
        super.setUp()
        mockForConstraintsTests(Team)
		mockDomain User, [instructor, ian, emma]
		mockDomain Course, [softwareDesign]
		mockDomain Team, []
		mockDomain GroupAssignment, []
		mockDomain Project, [releaseOne]
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testGroupCreation() {
		def controller = new TeamController()
		controller.params.groupNumber = '2'
		controller.params.projectInstance = releaseOne
		
		controller.createAndSaveMany()
		
		assertEquals "project", controller.redirectArgs.controller
		assertEquals "show", controller.redirectArgs.action
        
    }
}
