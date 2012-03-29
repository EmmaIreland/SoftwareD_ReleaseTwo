package surveyor

import java.security.acl.Group;

import grails.test.*

class UserControllerTests extends ControllerUnitTestCase {
    User instructor = new User(name: 'Nic Mcphee', email: 'mcphee@morris.umn.edu')
    Course softwareD = new Course(abbreviation: 'CSCI 3601', name: 'Software Design', term: 'Spring', year: '2012', owner: instructor)
    User ian = new User(name: 'Ian M', email: 'ian@ian.com')
    User emma = new User(name: 'Emma I', email: 'emma@emma.com')
    Project releaseOne = new Project(name: 'Release One', description: '', course: softwareD, teams: [])
    
    protected void setUp() {
        super.setUp()
        mockForConstraintsTests(User)
        mockDomain User, [instructor, ian, emma]
        mockDomain Course, [softwareD]
        mockDomain Team, []
        mockDomain GroupAssignment, []
        mockDomain Project, [releaseOne]
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testIndex() { 
        controller.index()
        assertEquals 'list', controller.redirectArgs.action      
    }
    
    void testCreateUser(){
        controller.params.name = 'Ed'
        controller.params.email = 'ed@ed.com'
        assertEquals controller.create().userInstance.name, 'Ed' 
        assertEquals controller.create().userInstance.email, 'ed@ed.com'
    }
    void testSaveUser(){
        User newUser = new User(name:'Pam', email:'pam@pam.pam')
        assertEquals User.list().size(), 3
        assertEquals newUser.save(), newUser
        assertEquals User.list().size(), 4
    }
    void testShowUser(){
        controller.params.id = 1
        assertEquals controller.show().userInstance, instructor

        controller.params.id = 100
        assertEquals controller.request.message, controller.redirectArgs.action
    }
    void testEditUser(){
        controller.params.id = 1
        assertEquals controller.edit().userInstance, instructor

        controller.params.id = 100
        assertEquals controller.redirectArgs.action, controller.request.message
    }
}
