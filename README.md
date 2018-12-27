# Java Automation Framework

The framework is a TestNG based test automation framework. It provides the user with the facility to easily create/write both Selenium & RestAssured driven API tests.

If the user wants to create a new Selenium/UI test then s(he) needs to just inherit from the BaseTestNGTest class.

If the user wants to create a new API test then s(he) needs to inherit from the BaseAPITest class.

The configuration of the above two are picked up from the frameworkConfig.properties file in src/main/resources folder. The config file provides/contains the basic properties. Having said that some of them can be overriden from the commandline. They are as below:

1. driverType - does the user require a local browser initialization or a RemoteWebDriver initialization
  a. values - local/remote
2. BROWSER - chrome/firefox/internetexplorer
3. host - the host where the grid is being run (preferably in ip address format)
4. port - the port to be connected to - numerical value
5. version - version of the browser - numerical value
6. platform - platform the remote tests need to be run against (please refer to Selenium's [Platform](https://seleniumhq.github.io/selenium/docs/api/java/org/openqa/selenium/Platform.html) enum)

if the above are not supplied then they default to the values provided in frameworkConfig.properties file.

The usage is as follows:

mvn clean test -DdriverType=local -DBROWSER=chrome -Dhost=127.0.0.1 -Dport=4444 -Dversion=71 -Dplatform=WINDOWS
