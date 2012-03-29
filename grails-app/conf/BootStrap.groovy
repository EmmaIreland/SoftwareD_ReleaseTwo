import surveyor.*

class BootStrap {
	
	def noData() {
		return User.count() == 0
	}

    def init = { servletContext ->
        if (noData()) {
            int i= 0
			
			User nic = new User(name: 'Nic McPhee', email: 'mcphee@gmail.com')
            User sarah = new User(name: 'Sarah Buchanan', email: 'buchanan@gmail.com')
            User simon = new User(name: 'Simon Tillier', email: 'tillier@gmail.com')
            User phou = new User(name: 'Phou Lee', email: 'phoulee@gmail.com')
            User chris = new User(name: 'Chris Aga', email: 'chrisaga@gmail.com')
            User kevin = new User(name: 'Kevin Viratyosin', email: 'kevinviratyosin@gmail.com')
            User josh = new User(name: 'Josh Johnson', email: 'joshjohnson@gmail.com')
            User matthew = new User(name: 'Matthew Perrault', email: 'mattperrault@gmail.com')
            User emma = new User(name: 'Emma Ireland', email: 'emmaireland@gmail.com')
            User christopher = new User(name: 'Christopher Thomas', email: 'christhomas@gmail.com')
            User ian = new User(name: 'Ian McGathy', email: 'ianmcgathy@gmail.com')
            User steve = new User(name: 'Steve Jungst', email: 'stevejungst@gmail.com')
            User matt = new User(name: 'Matt Cotter', email: 'mattcotter@gmail.com')
            User annabel = new User(name: 'Annabel Lee', email: 'annabellee@poe.org')
            User bill = new User(name: 'Bill Gates', email: 'gates@microsoft.com')
            User albert = new User(name: 'Albert Einstein', email: 'einstein@gmail.com')
            User[] students = [nic, sarah, simon, phou, chris, kevin, josh, matthew, emma, christopher, ian, steve, matt, annabel, bill, albert]
			for(i = 0; i < students.length; i++){
				students[i].save(failOnError:true)
			}
			
            Course softwareDesign = new Course(abbreviation:'CSCI 3601', name:'Software Design', term:'Spring',year:'2012', owner: nic)
            Course musicTheory = new Course(abbreviation:'MUS 1001', name:'Music Theory', term:'Fall',year:'2013', owner: simon)
            Course frenchI = new Course(abbreviation:'FREN 1001', name:'French I', term:'May',year:'2014', owner: sarah)
			Course[] courses = [softwareDesign, musicTheory, frenchI]
			for(i = 0; i < courses.length; i++){
				courses[i].save(failOnError:true)
			}
			

            Enrollment kevinInSoftwareDesign = new Enrollment(course: softwareDesign, student: kevin)
	            Enrollment phouInSoftwareDesign = new Enrollment(course: softwareDesign, student: phou)
	            Enrollment chrisInSoftwareDesign = new Enrollment(course: softwareDesign, student: chris)
	            Enrollment joshInSoftwareDesign = new Enrollment(course: softwareDesign, student: josh)
           
	            Enrollment matthewInSoftwareDesign = new Enrollment(course: softwareDesign, student: matthew)
	            Enrollment emmaInSoftwareDesign = new Enrollment(course: softwareDesign, student: emma)
	            Enrollment christopherInSoftwareDesign = new Enrollment(course: softwareDesign, student: christopher)
	            Enrollment ianInSoftwareDesign = new Enrollment(course: softwareDesign, student: ian)
	           
	            Enrollment mattInSoftwareDesign = new Enrollment(course: softwareDesign, student: matt)
	            Enrollment phouInMusicTheory = new Enrollment(course: musicTheory, student: phou)
	            Enrollment billInMusicTheory = new Enrollment(course: musicTheory, student: bill)
	            Enrollment mattInMusicTheory = new Enrollment(course: musicTheory, student: matt)
	            Enrollment albertInMusicTheory = new Enrollment(course: musicTheory, student: albert)
	            Enrollment chrisInBeginningFrench = new Enrollment(course: frenchI, student: chris)
	            Enrollment annabelInBeginningFrench = new Enrollment(course: frenchI, student: annabel)
			
			Enrollment[] enrollments = [kevinInSoftwareDesign, phouInSoftwareDesign, chrisInSoftwareDesign,joshInSoftwareDesign, matthewInSoftwareDesign, emmaInSoftwareDesign, christopherInSoftwareDesign, ianInSoftwareDesign, mattInSoftwareDesign, phouInMusicTheory, billInMusicTheory, mattInMusicTheory, albertInMusicTheory, chrisInBeginningFrench, annabelInBeginningFrench]
			for(i = 0; i < enrollments.length; i++){
				enrollments[i].save(failOnError:true)
			}
            
            Project ninthSymphony = new Project(name: 'The 9th Symphony', description: 'Analyze this composition and memorize the score.', course: musicTheory)
            Project imparfait = new Project(name: 'The Imparfait', description: 'Vous apprenderez l\'imparfait', course: frenchI)
            Project releaseOne = new Project(name: 'Release One', description: '', course: softwareDesign)
            Project[] project = [ninthSymphony, imparfait, releaseOne]
			for(i = 0; i < project.length; i++){
				project[i].save(failOnError:true)
			}
			
            Team camelCase = new Team(name: 'CamelCaseShotgunners', project: releaseOne)
            Team amish = new Team(name: 'TeamAmish', project: releaseOne)
            Team prodigies = new Team(name: 'Child Prodigies', project: ninthSymphony)
			Team frenchies = new Team(name: 'We Like France', project: imparfait)
			Team[] team = [camelCase, amish, prodigies, frenchies]
			for(i = 0; i < team.length; i++){
				team[i].save(failOnError:true)
			}
            
            GroupAssignment chrisToCamelCase = new GroupAssignment(student: chris, team: camelCase)
	            GroupAssignment kevinToCamelCase = new GroupAssignment(student: kevin, team: camelCase)
	            GroupAssignment phouToCamelCase = new GroupAssignment(student: phou, team: camelCase)
	            GroupAssignment joshToCamelCase = new GroupAssignment(student: josh, team: camelCase)
	            GroupAssignment matthewToAmish = new GroupAssignment(student: matthew, team: amish)
	            GroupAssignment emmaToAmish = new GroupAssignment(student: emma, team: amish)
	            GroupAssignment christopherToAmish = new GroupAssignment(student: christopher, team: amish)
	            GroupAssignment ianToAmish = new GroupAssignment(student: ian, team: amish)
	            GroupAssignment albertToProdigies = new GroupAssignment(student: albert, team: prodigies)
	            GroupAssignment billToProdigies = new GroupAssignment(student: bill, team: prodigies)
				GroupAssignment[] groupAssignment = [kevinToCamelCase, chrisToCamelCase, phouToCamelCase, joshToCamelCase, matthewToAmish, emmaToAmish,christopherToAmish,ianToAmish,albertToProdigies,billToProdigies]
				for(i = 0; i < groupAssignment.length; i++){
					groupAssignment[i].save(failOnError:true)
				}
        }
    }

    def destroy = {
    }
}
