package surveyor

import grails.test.*

class TeamTests extends GrailsUnitTestCase {
    User dave = new User(name: 'Dave Ericksen', email: 'ericksen@umn.edu')
    Course engl2501 = new Course(abbreviation: 'ENGL 2501', name: 'Literary Studies', term: 'Spring', year: '2012', owner: dave)
    Project findNellie = new Project(name: 'Find Nellie', description: 'Find the Loch Ness monster.', course: engl2501)
    
    protected void setUp() {
        super.setUp()
        mockForConstraintsTests(Team)
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testValidGroup() {
        Team validGroup = new Team(name: 'CamelCaseShotgunners', project: findNellie)
        assertTrue validGroup.validate()
    }
    
    void testBlankName() {
        Team blankNameGroup = new Team(name: '', project: findNellie)
        assertFalse blankNameGroup.validate()
        assertEquals 'blank', blankNameGroup.errors['name']
    }
    
    void testToString() {
        Team validGroup = new Team(name: 'Group name', project: findNellie)
        assertEquals validGroup.name, validGroup.toString()
    }
}
