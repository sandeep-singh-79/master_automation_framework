# Java Automation Framework

[![CodeQL](https://github.com/sandeep-singh-79/master_automation_framework/actions/workflows/codeql-analysis.yml/badge.svg)](https://github.com/sandeep-singh-79/master_automation_framework/actions/workflows/codeql-analysis.yml)  
[![maven docker CI](https://github.com/sandeep-singh-79/master_automation_framework/actions/workflows/DockerCI.yml/badge.svg)](https://github.com/sandeep-singh-79/master_automation_framework/actions/workflows/DockerCI.yml)

The framework is a **TestNG**-based test automation framework. It provides the user with the facility to easily create/write both **Selenium** and **RestAssured** driven API tests.

- If you want to create a new Selenium/UI test, you can simply inherit from the **BaseTestNGTest** class.
- If you want to create a new API test, you can inherit from the **BaseAPITest** class.

The configuration for the above two is picked up from the **frameworkConfig.properties** file in the `src/main/resources` folder. This configuration file contains the basic properties, some of which can be overridden from the command line.

## Configuration Options (Overrideable via Command Line)

The following properties can be configured via the `frameworkConfig.properties` file or overridden using Maven command-line arguments:

1. **driverType**: Choose between `local` or `remote` for initializing the browser driver.
2. **BROWSER**: Browser to use for tests (`chrome`, `firefox`, `internetexplorer`).
3. **host**: Host where the Selenium Grid is running (preferably an IP address).
4. **port**: The port number for Selenium Grid.
5. **version**: Version of the browser (numerical value).
6. **platform**: Platform for remote tests (refer to Selenium's [Platform Enum](https://seleniumhq.github.io/selenium/docs/api/java/org/openqa/selenium/Platform.html)).
7. **headless**: Whether to run the browser in headless mode (default: `true`).

These values can be set via the command line or left to default to the `frameworkConfig.properties` values.

## Running Tests with Maven

To run the tests with custom configurations, use the following command:

```bash
mvn clean test -DdriverType=local -DBROWSER=chrome -Dhost=127.0.0.1 -Dport=4444 -Dversion=71 -Dplatform=WINDOWS -Dheadless=false
```

Alternatively, if you prefer to use pre-configured profiles, you can specify one of the following:

### **Profiles**

- **dev_bdd**: For BDD tests on the development environment.
- **qa_bdd**: For BDD tests on the QA environment.
- **dev_int**: For API integration tests on the development environment.
- **qa_int**: For API integration tests on the QA environment.
- **dev_functional**: For full suite execution on the development environment.
- **qa_functional**: For full suite execution on the QA environment.

For example, to run BDD tests for the dev environment using Chrome and local driver type, you can use:

```bash
mvn clean test -P dev_bdd
```

To specify **Cucumber options**:

```bash
mvn clean test -Dcucumber.options="-t @XYZ --threads 3" -P dev_bdd
```

If your application requires **SSO authentication** to execute test cases, provide the username and password like this:

```bash
mvn clean test -P dev_bdd -Duser=<username> -Duserpass=<password>
```

### **Special Considerations**

- **TestNG DTD Issue**: You may encounter a TestNG issue related to loading DTDs from unsecured URLs. To fix this, pass the following JVM argument when running Maven:

  ```bash
  -Dtestng.dtd.http=true
  ```

---

## Docker Configuration

The framework also supports running the tests in a **Dockerized environment**. Below is the guide on how to build and run the Docker image.

### **Build the Docker Image**

You can customize the browser versions and whether Xvfb should be used by passing build arguments:

```bash
docker build \
  --build-arg CHROME_VERSION=123.0.1234.0 \
  --build-arg FIREFOX_VERSION=119.0 \
  --build-arg USE_XVFB=true \
  -t my-test-image .
```

### **Run the Docker Container**

#### **With Xvfb Enabled (Default)**

To run the Docker container with **Xvfb** enabled (for headless browser testing):

```bash
docker run --rm -e USE_XVFB=true my-test-image
```

#### **Without Xvfb (Headless Mode)**

To run the Docker container without **Xvfb** (headless mode):

```bash
docker run --rm -e USE_XVFB=false my-test-image
```

The above configuration will use the environment variable `USE_XVFB` to start or skip **Xvfb** as needed.

---

### **Running with Maven in Docker**

You can also pass Maven commands directly from the Docker container. For example, to run tests with specific parameters:

```bash
docker run --rm -e USE_XVFB=true my-test-image mvn clean test -DdriverType=local -DBROWSER=chrome -Dhost=127.0.0.1 -Dport=4444 -Dversion=71 -Dplatform=WINDOWS -Dheadless=false
```

### **Entry Point Script**

The container uses an **entrypoint script** (`/entrypoint.sh`) to start **Xvfb** (if required) and run the Maven tests. The script checks for the `USE_XVFB` environment variable and starts **Xvfb** if set to `true`. After that, it runs the Maven command passed to the container.

---

## Dockerfile Changes

In the Dockerfile, the `BROWSER` command-line argument will work as follows:

- If no `BROWSER` argument is provided in the Docker command or Maven command, the value from the `frameworkConfig.properties` file will be used.
- You can also override the `BROWSER` argument by passing it directly to the Maven or Docker command. The `Dockerfile` has been set up to handle this override via the `mvn` command in the entry point.

For example, to run the tests using Firefox:

```bash
docker run --rm -e USE_XVFB=true my-test-image mvn clean test -DBROWSER=firefox -DdriverType=local
```

This will use the Firefox browser for the test execution.

---
