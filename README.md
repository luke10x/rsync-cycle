# RSync Cycle

RSync Cycle is a chore scheduler that allows you to schedule 
and perform various tasks on a regular basis. 
With RSync Cycle, you can create, manage, and schedule different types of *chores*, 
such as:

- fetching backups from SSH or Docker;
- or ensuring that a working copy of a Git repository has pulled the latest changes.

## Features

- Web dashboard based on JSP
- Schedule and manage different types of chores
- Export and import chores as YAML files
- View chore run results (change notice, clean bill, or failure)
- Direct access to H2 Console (for power users)


## Chores

A *chore* is a task that is executed periodically at a configured interval.
Each individual run of a chore is called a *provision*.
When a provision runs, it can either:

- make actual changes to the system,
 and the result of such provision is called a *change notice*.
- detect that there is no job actually needed to be done
  (because the system is already in the desired state), 
  in this case, it will be considered that the provision result is a *clean bill*.
- Lastly, in some unlucky circumstances, the provision can end up with a *failure*.

Each chore type will have different parameters,
but some parameters will be common for all chores, such as interval or title.


## Getting Started

To get started with RSync Cycle, follow these steps:

1. Clone this repository to your local machine
2. Configure your YAML files for your chores
3. Build and run the application using Maven


### Configuration

To configure your chores, use the YAML files in the src/main/resources/chores directory.
You can create, edit, or delete YAML files to manage your chores. 
Each YAML file represents a single chore.

The YAML file structure should look something like this:

    ---
    title: My Chore
    interval: 1m
    type: ssh
    ssh_host: example.com
    ssh_username: username
    ssh_password: password
    ssh_path: /path/to/backup

This is an example of a YAML file for an SSH backup chore.
Each chore type will have its own set of parameters.


### Building and Running

To build and run the application, use Maven.
Navigate to the project root directory and run the following command:

    mvn spring-boot:run

This will build and run the application on your local machine.
You can access the web dashboard by navigating to http://localhost:8080 in your web browser.

For more specific development scenarions:

    export PORT=8082 
    mvn spring-boot:run -Dspring-boot.run.arguments=--importFile=/path/to/exported.yaml


### Contributing

If you want to contribute to RSync Cycle, fork this repository and make your changes.
Then, submit a pull request to this repository.


### FAQ

> Why is it using a DB, if it is memory-only and is loaded on startup

To make programming features easy
the state of application is managed using relational DB.
It is accessed through standard JPA interfaces,
and data in H2 is considered as a *Reference Data*.
(the source of truth).
Web application managing DB is idiomatic Java,
and YAML import/export and scheduling in Quartz is
less common.

The core of the application is CRUD operations on Reference Data,
other state like Quartz schedules will be derived out of it
as side effects including:

- loading from YAML to DB;
- export from DB as YAML;
- Quartz scheduler is observing 
  and reacting to the changes in the DB.


### License

This project is licensed under the MIT License - see the LICENSE file for details.
