@echo off

@rem script requires jmeter-datadog-backend-listener-0.3.0.jar in script folder to work, go to download page (https://jmeter-plugins.org/)
@rem the execution requires datadog api key as an argument

groovy -cp jmeter-datadog-backend-listener-0.3.0.jar script.groovy -Jvar_apikey=%1
