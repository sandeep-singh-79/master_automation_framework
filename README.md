# Java Automation Framework

[![CodeQL](https://github.com/sandeep-singh-79/master_automation_framework/actions/workflows/codeql-analysis.yml/badge.svg)](https://github.com/sandeep-singh-79/master_automation_framework/actions/workflows/codeql-analysis.yml)
[![maven docker CI](https://github.com/sandeep-singh-79/master_automation_framework/actions/workflows/DockerCI.yml/badge.svg)](https://github.com/sandeep-singh-79/master_automation_framework/actions/workflows/DockerCI.yml)

The framework is a TestNG based test automation framework. It provides the user with the facility to easily create/write both Selenium & RestAssured driven API tests.

If the user wants to create a new Selenium/UI test then s(he) needs to just inherit from the BaseTestNGTest class.

If the user wants to create a new API test then s(he) needs to inherit from the BaseAPITest class.

The configuration of the above two are picked up from the frameworkConfig.properties file in src/main/resources folder. The config file provides/contains the basic properties. Having said that some of them can be overriden from the commandline. They are as below:

1. driverType - does the user require a local browser initialization or a RemoteWebDriver initialization - local/remote
2. BROWSER - chrome/firefox/internetexplorer
3. host - the host where the grid is being run (preferably in ip address format)
4. port - the port to be connected to - numerical value
5. version - version of the browser - numerical value
6. platform - platform the remote tests need to be run against (please refer to Selenium's [Platform](https://seleniumhq.github.io/selenium/docs/api/java/org/openqa/selenium/Platform.html) enum)
7. headless - whether you want to run the local browser in headless mode or not. 'true' is the default value

if the above are not supplied then they default to the values provided in frameworkConfig.properties file.

The usage is as follows:

`mvn clean test -DdriverType=local -DBROWSER=chrome -Dhost=127.0.0.1 -Dport=4444 -Dversion=71 -Dplatform=WINDOWS -Dheadless=false`

In case the user doesn't want to specify all or any of the above, you may use any of the relevant profiles. For example, to run the process on the dev environment with driver type local and browser chrome, all the user has to do is:
`mvn clean test -P dev_bdd`

for cucumber-bdd, you may specify the cucumber.options as part of maven command line params. For example:
`mvn clean test -Dcucumber.options="-t @XYZ --threads 3" -P dev_bdd`

In case your app requires a username and password to execute the test cases (part of SSO), then the usage is:
`mvn clean test -P dev_bdd -Duser=<username> -Duserpass=<password>`

The various supported profiles are:

1. dev_bdd - for BDD tests on dev environment
2. qa_bdd - for BDD tests on qa environment
3. dev_int - for API integration tests on dev environment
4. qa_int - for API integration tests on qa environment
5. dev_functional - for full suite execution on dev environment
6. qa_functional - for full suite execution on qa environment.

**PS**: You might encounter a TestNG issue related to loading of DTDs from unsecured URLs. To get around this, you will
need to pass the JVM argument *-Dtestng.dtd.http=true* as part of the maven run command.
