import surveyor.*

class BootStrap {
	
	def noData() {
		return User.count() == 0
	}

    def init = { servletContext ->
        if (noData()) {
            
			
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
			for(int i = 0; i < students.length; i++){
				students[i].save(failOnError:true)
			}
			
            Course softwareDesign = new Course(abbreviation:'CSCI 3601', name:'Software Design', term:'Spring',year:'2012', owner: nic).save(failOnError:true)
            Course musicTheory = new Course(abbreviation:'MUS 1001', name:'Music Theory', term:'Fall',year:'2013', owner: simon).save(failOnError:true)
            Course frenchI = new Course(abbreviation:'FREN 1001', name:'French I', term:'May',year:'2014', owner: sarah).save(failOnError:true)
			
			

            new Enrollment(course: softwareDesign, student: kevin).save(failOnError:true)
            new Enrollment(course: softwareDesign, student: phou).save(failOnError:true)
            new Enrollment(course: softwareDesign, student: chris).save(failOnError:true)
            new Enrollment(course: softwareDesign, student: josh).save(failOnError:true)
            new Enrollment(course: softwareDesign, student: matthew).save(failOnError:true)
            new Enrollment(course: softwareDesign, student: emma).save(failOnError:true)
            new Enrollment(course: softwareDesign, student: christopher).save(failOnError:true)
            new Enrollment(course: softwareDesign, student: ian).save(failOnError:true)
            new Enrollment(course: softwareDesign, student: matt).save(failOnError:true)
			
			new Enrollment(course: musicTheory, student: phou).save(failOnError:true)
            new Enrollment(course: musicTheory, student: bill).save(failOnError:true)
            new Enrollment(course: musicTheory, student: matt).save(failOnError:true)
            new Enrollment(course: musicTheory, student: albert).save(failOnError:true)
            
			new Enrollment(course: frenchI, student: chris).save(failOnError:true)
            new Enrollment(course: frenchI, student: annabel).save(failOnError:true)
			new Enrollment(course: frenchI, student: steve).save(failOnError:true)
            
            Project ninthSymphony = new Project(name: 'The 9th Symphony', description: 'Analyze this composition and memorize the score.', course: musicTheory).save(failOnError:true)
            Project imparfait = new Project(name: 'The Imparfait', description: 'Vous apprenderez l\'imparfait', course: frenchI).save(failOnError:true)
            Project releaseOne = new Project(name: 'Release One', description: '', course: softwareDesign).save(failOnError:true)
            
            Team camelCase = new Team(name: 'CamelCaseShotgunners', project: releaseOne).save(failOnError:true)
            Team amish = new Team(name: 'TeamAmish', project: releaseOne).save(failOnError:true)
            Team prodigies = new Team(name: 'Child Prodigies', project: ninthSymphony).save(failOnError:true)
			Team frenchies = new Team(name: 'We Like France', project: imparfait).save(failOnError:true)
            
            new GroupAssignment(student: annabel, team: frenchies).save(failOnError:true)
			new GroupAssignment(student: chris, team: camelCase).save(failOnError:true)
            new GroupAssignment(student: kevin, team: camelCase).save(failOnError:true)
            new GroupAssignment(student: phou, team: camelCase).save(failOnError:true)
            new GroupAssignment(student: josh, team: camelCase).save(failOnError:true)
            new GroupAssignment(student: matthew, team: amish).save(failOnError:true)
            new GroupAssignment(student: emma, team: amish).save(failOnError:true)
            new GroupAssignment(student: christopher, team: amish).save(failOnError:true)
            new GroupAssignment(student: ian, team: amish).save(failOnError:true)
            new GroupAssignment(student: albert, team: prodigies).save(failOnError:true)
            new GroupAssignment(student: bill, team: prodigies).save(failOnError:true)
        }
    }

    def destroy = {
    }
}
