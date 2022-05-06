# MentalHealthSystem

<h1>EPL441 Homework</h1>

<h2>Team members</h2>
  Michail - Panagiotis Bofos
  <br>
  Demetra Hadjicosti
  <br>
  Ioanna Theophilou
  <br>
  Lucia Jimenez Garcia
  <br>


<h2>Model Structure <i>(Client-Server)</i></h2>

<img src="/Images/Structure/clientServer.drawio.png">

<h2>Instructions on set up and first run</h2>
  <h3>Installation and set up</h3>
  1) Pull the origin from github ( We recommend github desktop for your convenience)
  2) Change files src/main/resources/clientConf.json and src/main/resources/serverConf.json to your IPs
  3) Run maven command "mvn package" to run the Junit tests and create the 5 jar files needed to run this system
  4) <i>Optional:</i> Run maven command "mvn javadoc:javadoc" to create the javadoc documentation of the system
  5) For testing purposes we use the University of Cyprus infrastructure and database, in order for our system to run properly we'll need to be connected with VPN.

  <h3>Run Server</h3>
  Move to the target directory and run the Server-jar-with-dependencies jar file
  $java -jar Server-jar-with-dependencies.jar

  <h3>Run Clinical Viewpoint</h3>
  Move to the target directory and run the Clinical-jar-with-dependencies jar file
  $java -jar Clinical-jar-with-dependencies.jar

  <h3>Run Receptionist Viewpoint</h3>
  Move to the target directory and run the Receptionist-jar-with-dependencies jar file
  $java -jar Receptionist-jar-with-dependencies.jar

  <h3>Run Medical Records Viewpoint</h3>
  Move to the target directory and run the MedicalRecords-jar-with-dependencies jar file
  $java -jar MedicalRecords-jar-with-dependencies.jar

  <h3>Run Health Service Viewpoint</h3>
  Move to the target directory and run the HealthService-jar-with-dependencies jar file
  $java -jar HealthService-jar-with-dependencies.jar

<h2>Database Creation</h2>

  <h3>Recreation of Database</h3>
  If you want to set up your own database and recreate this system, just run the Database/team4.sql script.

  <h3>ER Diagram</h3>
  <img src="/Images/Structure/DB.png">