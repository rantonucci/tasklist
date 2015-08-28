Task List 
=========

An example of a full stack web application.  It implements a simple (and rather bare-bones looking) to-do list.

It has two parts. The back end system serves REST calls.  The back end system uses an embedded HSQL database, Hibernate, Jersey and Jackson.  The front end system runs on a separate web server and serves up the javascipt and html pages.  The front end is a single page Angular app.

You will need Maven and NPM installed to run this.

How to Start
============

1. Clone the tasklist project
2. Go into the backend/ directory.
2. Build the backend with "mvn package"
2. Populate the database with "java -jar target\tasklist-backend-0.0.1-SNAPSHOT.jar **bootstrap** tasklist.yml"
3. Run the backend server with "java -jar target\tasklist-backend-0.0.1-SNAPSHOT.jar **server** tasklist.yml"
4. Go into the frontend/ directory
4. Setup webserver to host the javascript with "npm install"
5. Run the webserver with "npm start"
6. Hit http://localhost:8000/app
