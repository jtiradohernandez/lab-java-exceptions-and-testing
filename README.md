![logo_ironhack_blue 7](https://user-images.githubusercontent.com/23629340/40541063-a07a0a8a-601a-11e8-91b5-2f13e4e6b441.png)

# LAB Java | Exceptions and Testing

## Introduction

We have just learned how to create custom exceptions, how to handle them in controllers and how to use MockMVC to test the controllers so let's practice a bit more.

<br>

## Requirements

1. Fork this repo.
2. Clone this repo.
3. Add your instructor and the class graders as collaborators to your repository. If you are unsure who your class graders are, ask your instructor or refer to the day 1 slide deck.
4. In the repository, create a Java project and add the code for the following prompts.

## Submission

Once you finish the assignment, submit a URL link to your repository or your pull request in the field below.

<br>

## Set Up

Combine the 2 labs Intro to Spring Boot (4.02) and Add and Update (4.04) and then copy the code to your new repository.

<br>

## Instructions

1. Test every route available using `MockMVC`.

<br>

## Tips

To test the routes, you can follow these steps:

1. Start by importing the necessary dependencies for integration testing, such as Spring Boot's `@SpringBootTest`, `MockMvc` and `WebApplicationContext`.
2. In your test class, use the `@SpringBootTest` annotation to configure the test environment and create a `WebApplicationContext` object by autowiring it.
3. Next, create a `MockMvc` object by using `MockMvcBuilders.webAppContextSetup(webApplicationContext)`
4. Use the `MockMvc` object to perform GET, POST and PUT requests to the different routes in your application.
5. For positive cases, use the `.andExpect(status().isOk())` method to ensure that the appropriate status code (200 OK) is returned in the response. You can also use `.andExpect(content().json("expected json"))` to check if the returned json matches what you expect.
6. For negative cases, use the `.andExpect(status().is(400))` method to check that the status code returned is 400 Bad Request. You can also use `.andExpect(status().reason(containsString("Bad Request")))` to check if the response body contains the appropriate error message.
7. To handle path variables and query parameters, you can use the `.param("paramName", "paramValue")` method to add them to the request before performing it.
8. Repeat steps 4-7 for all the GET, POST and PUT routes in your application, including the routes from Labs 4.02 and 4.04.
9. Run your tests and check that all of them pass.

It's important to keep in mind that test should be isolated and should not have any side effect on other test, so make sure to clean up any data that was created during the test.

<br>

## FAQs

<br>

<details>
  <summary style="font-size: 16px; cursor: pointer; outline: none; font-weight: bold;">I am stuck and don't know how to solve the problem or where to start. What should I do?</summary>

  <br> <!-- ✅ -->

  If you are stuck in your code and don't know how to solve the problem or where to start, you should take a step back and try to form a clear, straight forward question about the specific issue you are facing. The process you will go through while trying to define this question, will help you narrow down the problem and come up with potential solutions.

  For example, are you facing a problem because you don't understand the concept or are you receiving an error message that you don't know how to fix? It is usually helpful to try to state the problem as clearly as possible, including any error messages you are receiving. This can help you communicate the issue to others and potentially get help from classmates or online resources.

  Once you have a clear understanding of the problem, you should be able to start working toward the solution.

 </details>

<br>

<details>
  <summary style="font-size: 16px; cursor: pointer; outline: none; font-weight: bold;">How do I create a Spring boot project?</summary>

  <br> <!-- ✅ -->

  Spring boot is a framework for creating stand-alone, production-grade applications that are easy to launch and run. The best way to create a Spring boot project is to use the Spring Initializer website. The website provides a convenient way to generate a basic project structure with all the necessary dependencies and configurations.

  - Step 1: Go to [start.spring.io](https://start.spring.io/)
  - Step 2: Choose the type of project you want to create, such as Maven or Gradle.
  - Step 3: Select the version of Spring Boot you want to use.
  - Step 4: Choose the dependencies you need for your project. Some common dependencies include web, jpa and data-jpa.
  - Step 5: Click the "Generate" button to download the project files.

  Alternatively, you can use an Integrated Development Environment (IDE) such as Eclipse or IntelliJ IDEA. These IDEs have plugins for creating Spring boot projects, making it easy to set up the environment and get started with coding.

 </details>

<br>

<details>
  <summary style="font-size: 16px; cursor: pointer; outline: none; font-weight: bold;">What is "MockMvc" and "WebApplicationContext" and how can we use them in Java?</summary>

  <br> <!-- ✅ -->

  `MockMvc` and `WebApplicationContext` are two important components used in testing Spring MVC applications.

  `MockMvc` is used for testing the behavior of a Spring MVC application, including the handling of HTTP requests and responses. `WebApplicationContext` is used to configure the application context for testing purposes.

  To use `MockMvc` and `WebApplicationContext`, you need to create a test class with the following code:

  ```java
  @SpringBootTest
  public class YourControllerTest {

      @Autowired
      private MockMvc mockMvc;

      @Autowired
      private WebApplicationContext webApplicationContext;

      @Before
      public void setup() {
          mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
      }

      @Test
      public void testExample() throws Exception {
          mockMvc.perform(get("/your-endpoint"))
                  .andExpect(status().isOk())
                  .andExpect(content().string("Hello World!"));
      }
  }
  ```

  In this code, `@SpringBootTest` annotation is used to configure the test class for testing a Spring MVC application. The `MockMvc` and `WebApplicationContext` components are autowired and a `setup` method is used to configure the `MockMvc` instance using the `WebApplicationContext`. Finally, a test method is created to test the behavior of the endpoint using the `mockMvc.perform` method.

  <br>

   

</details>

<br>

<details>
  <summary style="font-size: 16px; cursor: pointer; outline: none; font-weight: bold;">What is "ObjectMapper" in Java and how can it be used?</summary>

  <br> <!-- ✅ -->

  `ObjectMapper` is a class from the Jackson library that is used for reading and writing JSON data in Java. It allows you to convert Java objects to JSON and vice versa. With `ObjectMapper`, you can easily convert objects to and from JSON format, making it a useful tool for working with RESTful web services and APIs.

  Here's a code snippet to demonstrate how to use `ObjectMapper` to convert a Java object to a JSON string:

  ```java
  // Import ObjectMapper class
  import com.fasterxml.jackson.databind.ObjectMapper;

  // Create an instance of ObjectMapper
  ObjectMapper mapper = new ObjectMapper();

  // Convert a Java object to JSON
  User user = new User();
  user.setName("John Doe");
  user.setAge(30);
  String json = mapper.writeValueAsString(user);
  System.out.println(json);
  ```

  And here's a code snippet to demonstrate how to use `ObjectMapper` to convert a JSON string to a Java object:

  ```java
  // Import ObjectMapper class
  import com.fasterxml.jackson.databind.ObjectMapper;

  // Create an instance of ObjectMapper
  ObjectMapper mapper = new ObjectMapper();

  // Convert a JSON string to a Java object
  String json = "{\"name\":\"John Doe\",\"age\":30}";
  User user = mapper.readValue(json, User.class);
  System.out.println(user.getName());
  System.out.println(user.getAge());
  ```

  As you can see, using `ObjectMapper` is simple and straightforward. You can use it to perform a variety of tasks related to reading and writing JSON data in Java, making it an essential tool for any Java developer who needs to work with RESTful web services and APIs.

  <br>

   

</details>

<br>

<details>
  <summary style="font-size: 16px; cursor: pointer; outline: none; font-weight: bold;">What is "SimpleDateFormat" and how can it be used in Java?</summary>

  <br>

  `SimpleDateFormat` is a concrete class in Java that allows you to format and parse dates. It is part of the `java.text package`.

  `SimpleDateFormat` can be used in Java to format a date into a string representation. For example:

  ```java
  Date date = new Date();
  SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
  String formattedDate = sdf.format(date);
  System.out.println("Formatted date: " + formattedDate);
  ```

  `SimpleDateFormat` can be used to parse a string into a date. For example:

  ```java
  String dateString = "15-01-2021";
  SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
  Date parsedDate = sdf.parse(dateString);
  System.out.println("Parsed date: " + parsedDate);
  ```

  The format of the date string in `SimpleDateFormat` is specified as a string parameter when creating the `SimpleDateFormat` object. The format string uses symbols to represent different parts of a date such as day, month, year, etc. For example, the format string "dd-MM-yyyy" represents a date in the format "day-month-year".

 </details>

<br>

<details>
  <summary style="font-size: 16px; cursor: pointer; outline: none; font-weight: bold;">I am unable to push changes to my repository. What should I do?</summary>

  <br> <!-- ✅ -->

  If you are unable to push changes to your repository, here are a few steps that you can follow:

  1. Check your internet connection: Ensure that your internet connection is stable and working.
  1. Verify your repository URL: Make sure that you are using the correct repository URL to push your changes.
  2. Check Git credentials: Ensure that your Git credentials are up-to-date and correct. You can check your credentials using the following command:

  ```bash
  git config --list
  ```

  4. Update your local repository: Before pushing changes, make sure that your local repository is up-to-date with the remote repository. You can update your local repository using the following command:

  ```bash
  git fetch origin
  ```

  5. Check for conflicts: If there are any conflicts between your local repository and the remote repository, resolve them before pushing changes.
  6. Push changes: Once you have resolved any conflicts and updated your local repository, you can try pushing changes again using the following command:

  ```bash
  git push origin <branch_name>
  ```

</details>
