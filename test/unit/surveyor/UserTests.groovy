package surveyor

import grails.test.*

class UserTests extends GrailsUnitTestCase {
    protected void setUp() {
        super.setUp()
        mockForConstraintsTests(User)
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testValid() {
        User user = new User(name:'Test Name', email:'TestEmail@Gmail.com')
        assertTrue user.validate()

    }
    
    void testBlankName() {
        User user = new User(name:'', email:'TestEmail@Gmail.com')
        assertFalse user.validate()
        assertEquals user.errors['name'], 'blank'
    }
    
    
    void testInvalidEmail() {
        User user = new User(name:'Test Name', email:'Invalid Email')
        assertFalse user.validate()
        assertEquals user.errors['email'], 'email'
    }
    
    void testToString(){
        User user = new User(name:'Test Name', email:'TestEmail@gmail.com')
        assertEquals user.name , user.toString()
    }
    
}
