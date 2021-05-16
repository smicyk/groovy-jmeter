# Examples

The **examples** folder contains several scripts:  

 - [standalone](standalone) - the most basic usage of a test script, it is run from command line with **groovy** command
 - [commandline](commandline) - similar to __standalone__ but demonstrates how to use command line support in the scripts. It makes scripts more flexible and give possibility to run same script with different parameters.  
 - [structure](structure) - demonstrates how to use __insert__ keyword to extract common test elements into separate files (e.g. login)
 - [jsr223](jsr223) - demonstrates how to use JSR233 test element in the script (the Groovy is default script language)
 - [gradle](gradle) - the script can be run as a gradle task (gradle manages all dependencies and there is no need to add Grape annotation to the scripts)
 - [docker](docker) - the script runs in the docker container (if you don't want install runtime environment)
 - [docker-gradle](docker-gradle) - docker and gradle examples combined into one 
 - [application](application) - run test scripts in docker with all components - the application, test server and monitoring. It gives an overview of how the developer can configure all components to work together.
 - [database](database) - run test scripts in docker with all components - the application, test server and monitoring. It gives an overview of how the developer can configure JDBC components to work together.
 
There are two folders [mockserver](mockserver) and [monitoring](monitoring) which contains application mock and monitoring for backed listener.

After you start monitoring, you can access it with [local link](http://localhost:3000). The user and password is __admin/admin__.

> Note, all examples has __run__ file which should be used to run the examples (or you can copy the content to command line and experiment with it)

> It is necessary to run __build_local__ and __build_docker__ in the main folder before running all examples.