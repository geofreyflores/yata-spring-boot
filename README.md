# YATA spring boot

YATA (yet another todo app) is a TODO app built on top of a MongoDB, Spring-boot API backend with a ReactJS frontend stack and managed through Docker containers

## Installation

**Prerequisite**: [Docker](https://docs.docker.com/get-docker/) (specifically Docker Engine and Docker Compose which already comes with Docker Desktop for Windows/Mac)

Assuming you are already in the main directory, use `docker-compose up -d` to get the stack up and running in the background

```
git clone git@github.com:geofreyflores/yata-spring-boot.git
cd yata-spring-boot
docker-compose up -d
```
Use `docker-compose stop` to stop the containers and `docker-compose down` to completely remove the containers (images still need to be manually removed)

Service URLs:

* front-end: http://localhost:3001
* backend API: http://localhost:8080/api/v1/tasks

## Usage

Go to url http://localhost:3001

* **Add/Create tasks** - type in "add input" field and click `Add` button (or press enter)
* **Delete task** - click on the ❌ remove icon beside the task to delete
* **Edit task** - double click on the task name, edit the task and click on `edit`, or `cancel` to abandon change
* **Search/filter task list** - type in the search filter text field and click 'Search' button. To clear filter, clear filter text and click 'Search'.

## Testing

**Prerequisite**: `maven` (>=3.6) and `npm` (>=v12)

* To run backend unit tests, run the following command: `cd server; mvn test`
* To run front-end unit tests, run the following command: `cd client; npm i && npm test`

## Resources

* Unit test for springboot rest services
  https://www.springboottutorial.com/unit-testing-for-spring-boot-rest-services
  


## License
[MIT](https://choosealicense.com/licenses/mit/)