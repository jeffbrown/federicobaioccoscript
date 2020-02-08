includeTargets << grailsScript('_GrailsBootstrap')

target(populateDb: "Target demonstrates one approach to using domain classes in a script") {
    depends classpath, bootstrap

    // load the Person class
    def personClass = classLoader.loadClass('federicobaioccoscript.Person')

    // the question is about using domain classes in a script, not
    // about parsing JSON files so here the peopleData is hardcoded instead
    // of complicating the example with file i/o.
    def peopleData = []
    peopleData << [firstName: 'Geddy', lastName: 'Lee']
    peopleData << [firstName: 'Neil', lastName: 'Peart']
    peopleData << [firstName: 'Alex', lastName: 'Lifeson']

    // create and persist instances of the Person class
    personClass.withNewSession {
        peopleData.each { personData ->
            // create an instance of the Person class
            def person = personClass.newInstance personData

            // save the instance to the database
            person.save()
        }
    }

    // this is only here to demonstrate that the data
    // really is in the database...
    personClass.withNewSession {
        List people = personClass.list()

        println people
    }
}

setDefaultTarget(populateDb)
