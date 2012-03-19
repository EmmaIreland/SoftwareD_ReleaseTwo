package surveyor

import grails.test.*

class ProjectTests extends GrailsUnitTestCase {
    User dave = new User(name: 'Dave Ericksen', email: 'ericksen@umn.edu')
    Course engl2501 = new Course(abbreviation: 'ENGL 2501', name: 'Literary Studies', term: 'Spring', year: '2012', owner: dave)
    
    protected void setUp() {
        super.setUp()
        mockForConstraintsTests(Project)
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testValidProject() {
        Project project = new Project(name:'Test Project', description:'We\'re testing a project', course: engl2501)
        assertTrue project.validate()
    }
    
    void testBlankName() {
        Project project = new Project(name:'', description:'We\'re testing a project', course: engl2501)
        assertFalse project.validate()
        assertEquals project.errors['name'], 'blank'
    }
    
    void testBlankDescription() {
        Project project = new Project(name:'Test Project', description:'', course: engl2501)
        assertTrue project.validate()
    }
    
    void testNoCourse() {
        Project project = new Project(name:'Test Project', description:'We\'re testing a project')
        assertFalse project.validate()
        assertEquals project.errors['course'], 'nullable'
    }
    
    void testToString() {
        Project project = new Project(name:'Test Project', description:'We\'re testing a project', course: engl2501)
        assertEquals project.name, project.toString()
    }
}
