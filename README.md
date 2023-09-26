# SOEN 6441 - WARZONE GAME GROUP PROJECT

The specific project for this semester consists in building a simple “Risk” computer game. The developed program will
have to be compatible with the rules and map files and the command-line play of the “Warzone” version of Risk, which can
be found at: https://www.warzone.com/.

### Documentation

https://soen6441project.github.io/Warzone/

### How do I get set up

Clone down this repository. You will need `maven` and `java` installed on your machine.

Installation:

`mvn clean install`

To Run Test Suite:

`mvn test`

To Start:

`run main class in WarzoneApplication`

To Generate Javadoc:

`mvn javadoc:javadoc`

To Format Code:

`mvn spotless:apply`

To Check Code Formatting:

`mvn spotless:check`

### Contribution guidelines

- Use main branch as development branch
- When creating a branch, create it from Github issue (so we can track it),
- Put your issue number into commit message as GH-<num>: [TAG] <Message> (for traceability)
- TAG should be caps and can be: ADD, REMOVE, CHANGE, FIX, WIP (aka Work In Progress), REFACTOR, TEST (for readability)
- Each feature should be tested
- No direct pushes to the development branch (main), only pull requests(PRs).
- Two approval of PR (can put 1 as well)
- You can just use team's name whenever assigning a PR reviewer, it will add all members

### HexaForce

- Azamat Ochilov
- Deniz Dinchdonmez
- Habeeb Dashti
- Hammad Ali
- Usaib Khan
- Waseem Jan
