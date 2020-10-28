# Examples

The **examples** folder contains several scripts:  

 - [standalone](standalone) - the most basic usage of a test script, it is run from command line with **groovy** command
 - [commandline](commandline) - similar to __standalone__ but demonstrates how to use command line support in the scripts. It makes scripts more flexible and give possibility to run same script with different parameters.  
 - [structure](structure) - demonstrates how to use __insert__ keyword to extract common test elements to separate files (e.g. login)
 - [gradle](gradle) - the script can be run as a gradle task (all dependencies are managed by gradle and there is no need to add Grape annotation to scripts)
 - [docker](docker) - the script is run in docker container (if you don't want install runtime environment)
 - [docker-gradle](docker-gradle) - docker and gradle examples combined into one 
 - [application](application) - run test scripts in docker with all components, the application, test server and monitoring. Gives overview how all component can be configured to work together.
 
Besides examples with scripts there are two folders [mockserver](mockserver) and [monitoring](monitoring) which contains simple application mock and monitoring for all examples.

> Note, all examples has __run.sh__ file which should be used to run the example (or you can copy the content to command line and experiment with it)

> It is necessary to run __build_local.sh__ and __build_docker.sh__ in the main folder before running all examples.