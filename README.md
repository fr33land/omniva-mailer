# omniva-mailer
Omniva mailer task

In this task I created spring boot REST service and React client. In this task I skipped Unit test writing for Spring service. Also I skipped success messages and other modal dialogs in React client. This is the first time when I am working with React so sorry for the unclean code. For the backend Spring service uses postgres DB. For DB schema versioning I am using flyway. You can find schema in src\main\resources\db\migration catalog. For database I used postgres Docker container. It is possible to containerize  Spring application to with adding Dockerfile and maven docker plugin. For API describtion I used Swagger OpenAPI v3 yaml file which is used to generate server code stub during maven plugin execution.   

React application starts with npm start
