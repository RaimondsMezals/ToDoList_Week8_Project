
Coverage: 90.9%

# ToDoList_Week8_Project

The purpose of this project was to create a To Do List Web Application that would be able to create a to do lists with CRUD functionality and have an ability to add or remove items from specific lists.


### Prerequisites

What thing you will need to use this system


Software:

Appropriate java version (At least java 8)

Apache Maven

Integrated Development Environment (IDE) In this case SpringToolSuite4

MySQL or a H2 database

Maven and java can both be downloaded online and extracted as zip files. In addition to this you will need to set paths for both maven and java within system environment variables.


### Installing

How to get a development env running

The first think that you will need to do is to open your SpringToolSuite4 and import the back end of the project.
 
After this the project is ready for development. Here you can edit any code if necessary, run the program or its tests.

To run the program itself you would need to right click the project and run it as a java application.

## Running the tests

In order to run the tests you need to navigate yourself to the src/test/java folder within the project. After this you could select either one or multiple test classes, right click them and run as coverage with JUnit test.

### Unit Tests 

The purpose of unit tests within this project was to create every single individual method within created classes. The classes that were unit tested were BagController, BagService, ItemController and ItemService.

### Integration Tests 

The purpose of integration tests was to test the functionality of the links between the classes. In this case the links that were tested were between Controller and Service classes.

### Selenium Tests 

The purpose of selenium tests was to test the front-end of the web application. The reports created can be found within the documentation folder of the project as a frontendReport HTML file.

## Deployment

To deploy the project without an IDE it is possible to run it using a jar file. Within the root folder of this project there is a jar file that can be run using either a cmd or PowerShell console. In order to run a jar file you would need to navigate to the folder where it is located, open a console of your choice and type "java -jar ToDoList_Week8_Project-0.0.1-SNAPSHOT.jar". This will run the system. In order to open the web application you would need to open a Chrome browser and type "localhost:9090/index.html" as the URL.

## Built With

* [Maven](https://maven.apache.org/) - Dependency Management

## Authors


* **Raimonds Mezals** - *Author of the project* - [raimondsmezals](https://github.com/RaimondsMezalsQA)


## Acknowledgments

* I would like to say thanks to my trainers Ghela Vinesh, Reynolds Edward, Davis Alan and Reece Elder for teaching me the knowledge required in order to complete this project.
* I also would like to acknowledge my teammates Cameron, Henry and Sehun which gave me inspiration on fixing some of the strange errors that appeared during this project.
