package surveyor

import grails.test.*

class GroupAssignmentTests extends GrailsUnitTestCase {
    User dave = new User(name: 'Dave Ericksen', email: 'ericksen@umn.edu')
    Course engl2501 = new Course(abbreviation: 'ENGL 2501', name: 'Literary Studies', term: 'Spring', year: '2012', owner: dave)
    Project findNellie = new Project(name: 'Find Nellie', description: 'Find the Loch Ness monster.', course: engl2501)
    Team hunters = new Team(name: 'MonsterHunters', project: findNellie, groupAssignments: [])
    User phouLee = new User(name: 'Phou Lee', email: 'phoulee@gmail.com')
    Enrollment phouInLit = new Enrollment(student: phouLee, course: engl2501)
    
    protected void setUp() {
        super.setUp()
        mockForConstraintsTests(GroupAssignment)
        mockDomain(Course, [engl2501])
        mockDomain(Project, [findNellie])
        mockDomain(User, [phouLee])
        mockDomain(Enrollment, [phouInLit])
        mockDomain(GroupAssignment, [])
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testStandardGroupAssignment() {
        GroupAssignment phouToMonsterHunters = new GroupAssignment(student: phouLee, team: hunters)
        assertTrue phouToMonsterHunters.validate()
    }
    
    void testEmptyGroupAssignment() {
        GroupAssignment phouToMonsterHunters = new GroupAssignment()
        assertFalse phouToMonsterHunters.validate()
        assertEquals 'nullable', phouToMonsterHunters.errors['student']
        assertEquals 'nullable', phouToMonsterHunters.errors['team']
    }
    
    void testDuplicatedGroupAssignment() {
        GroupAssignment phouToMonsterHunters = new GroupAssignment(student: phouLee, team: hunters)
        assertTrue phouToMonsterHunters.validate()
        
        mockDomain(GroupAssignment, [phouToMonsterHunters])
        
        GroupAssignment otherPhouToMonsterHunters = new GroupAssignment(student: phouLee, team: hunters)
        assertFalse otherPhouToMonsterHunters.validate()
        assertEquals 'validator', otherPhouToMonsterHunters.errors['team']
    }
    
    void testToString() {
        mockDomain(Team, [hunters])
        GroupAssignment phouToMonsterHunters = new GroupAssignment(student: phouLee, team: hunters)
        mockDomain(GroupAssignment, [phouToMonsterHunters])
        assertEquals phouLee.toString() + ' in ' + hunters.toString(), phouToMonsterHunters.toString()
    }
    
    void testAssignUserNotInClass() {
        User joevin = new User( name: 'Joevin', email: 'joevin@umn.edu' )
        GroupAssignment joevinInHunters = new GroupAssignment(student: joevin, team: hunters)
        assertFalse joevinInHunters.validate()
        assertEquals 'validator', joevinInHunters.errors['student']
    }
}
