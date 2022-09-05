# Examples

The **examples** folder contains several scripts with plugins support:  

 - [datadog](datadog) - the configuration of [datadog](https://www.datadoghq.com/) plugin for JMeter. When running script the datadog plugin must be provided to the classpath and the API Key must be provided (check the ./run.* scripts)
 - [dummy](dummy) - full project how to create own plugin to groovy-jmeter based on existing plugin from JMeter Plugin page. The project has the script folder with example how to use the plugin  
 - [include](include) - alternative way of including JMeter plugins. The simple solution is to use __include__ keyword and include .jmx file (doesn't work for all plugins e.g. listeners) 
 
There are two folders [mockserver](mockserver) and [monitoring](monitoring) which contains application mock and monitoring for backed listener.

After you start monitoring, you can access it with [local link](http://localhost:3000). The user and password is __admin/admin__.

> Note, all examples has __run__ file which should be used to run the examples (or you can copy the content to command line and experiment with it)

> It is necessary to run __build_local__ and __build_docker__ in the main folder before running all examples.