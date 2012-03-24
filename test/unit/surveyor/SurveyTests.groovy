package surveyor

import grails.test.*

class SurveyTests extends GrailsUnitTestCase {
	def futureDate = Date.parse("yyyy-MM-dd", "2012-09-15") // TODO deprecated?
	def pastDate = Date.parse("yyyy-MM-dd", "2002-09-15") // TODO deprecated?
	
	User nic = new User(name: 'Nic McPhee', email: 'mcphee@umn.edu')
	User dave = new User(name: 'Dave Ericksen', email: 'ericksen@umn.edu')
	User chris = new User(name: 'Chris Aga', email: 'chrisaga@gmail.com')
	User matthew = new User(name: 'Matthew Perrault', email: 'mattperrault@gmail.com')
	User emma = new User(name: 'Emma Ireland', email: 'emmaireland@gmail.com')

	protected void setUp() {
		super.setUp()
		mockForConstraintsTests(Survey)		
	}

	protected void tearDown() {
		super.tearDown()
	}

	void testValidSurvey() {
		Survey testSurvey = new Survey(name: 'Test', description: 'A test', owner: nic, dueDate: futureDate) 		
		assertTrue testSurvey.validate()
	}

	void testNoNameSurvey() {
		Survey testSurvey = new Survey(name: '', description: 'no name', dueDate: futureDate)
		assertFalse testSurvey.validate()
		assertEquals 'blank', testSurvey.errors['name']
	}

	void testAllThatCanBeEmpty() {
		Survey testSurvey = new Survey(name: 'So empty', description: '', owner: nic , dueDate: futureDate)
		assertTrue testSurvey.validate()
	}

	void testOnlyStudentGiven() {
		Survey testSurvey = new Survey(name: 'Only student', description: '', owner: nic , dueDate: futureDate)
		assertTrue testSurvey.validate()
	}
	
	void testNoDateGiven() {
		Survey testSurvey = new Survey(name: 'No Date', description: '', owner: nic)
		assertTrue testSurvey.validate()
	}
	
	void testDateInPastGiven() {
		Survey testSurvey = new Survey(name: 'No Date', description: '', owner: nic, dueDate: pastDate)
		assertFalse testSurvey.validate()
		assertEquals 'validator', testSurvey.errors['dueDate']
	}
	
	void testNoOwnerGiven() {
		Survey testSurvey = new Survey(name: 'No Owner', description: '')
		assertFalse testSurvey.validate()
	}

}
