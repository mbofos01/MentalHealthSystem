# MentalHealthSystem

<h1>EPL441 Homework</h1>
<img src="/Images/kris.png">
<h2>Team members</h2>
  <a href="mailto:mbofos01@ucy.ac.cy">Michail - Panagiotis Bofos</a>
  <br>
  <a href="mailto:dhadji02@ucy.ac.cy">Demetra Hadjicosti</a>
  <br>
  <a href="mailto:itheop02@ucy.ac.cy">Ioanna Theophilou</a>
  <br>
  <a href="mailto:ljimen01@ucy.ac.cy">Lucia Jimenez Garcia</a>
  <br>


<h2>Model Structure <i>(Client-Server)</i></h2>

<img src="/Images/Structure/clientServer.drawio.png">

<h2>Instructions on set up and first run</h2>
  <h3>Installation and set up</h3>
  1) Pull the origin from github ( We recommend github desktop for your convenience)
  <br>
  2) Change files src/main/resources/clientConf.json and src/main/resources/serverConf.json to your IPs
  <br>
  3) Run maven command "mvn package" to run the Junit tests and create the 5 jar files needed to run this system
  <br>
  4) <i>Optional:</i> Run maven command "mvn javadoc:javadoc" to create the javadoc documentation of the system
  <br>
  5) For testing purposes we use the University of Cyprus infrastructure and database, in order for our system to run properly we'll need to be connected with VPN.
  <br>

  <h3>Run Server</h3>
  Move to the target directory and run the Server-jar-with-dependencies jar file
  <br>
  $java -jar Server-jar-with-dependencies.jar

  <h3>Run Clinical Viewpoint</h3>
  Move to the target directory and run the Clinical-jar-with-dependencies jar file
  <br>
  $java -jar Clinical-jar-with-dependencies.jar

  <h3>Run Receptionist Viewpoint</h3>
  Move to the target directory and run the Receptionist-jar-with-dependencies jar file
  <br>
  $java -jar Receptionist-jar-with-dependencies.jar

  <h3>Run Medical Records Viewpoint</h3>
  Move to the target directory and run the MedicalRecords-jar-with-dependencies jar file
  <br>
  $java -jar MedicalRecords-jar-with-dependencies.jar

  <h3>Run Health Service Viewpoint</h3>
  Move to the target directory and run the HealthService-jar-with-dependencies jar file
  <br>
  $java -jar HealthService-jar-with-dependencies.jar

<h2>Database Creation</h2>

  <h3>Recreation of Database</h3>
  If you want to set up your own database and recreate this system, just run the Database/team4.sql script.

  <h3>ER Diagram</h3>
  <img src="/Images/Structure/DB.png">

<h2>Prototypes</h2>

  <h3>Clinical Viewpoint</h3>
  <h3>Receptionist Viewpoint</h3>
  <h3>Medical Records Viewpoint</h3>
  <h3>Health Service Viewpoint</h3>