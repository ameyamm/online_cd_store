online_cd_store
===============

An online CD store : 
A simple web application to showcase MVC Architecture
using JS/HTML - Servlets- SOAP Web Services - JDBC (Connection Pooling) - MySQL.

Project Team : 
Amanpreet Singh Saroya
Mandy Ma
Haifa Alharthy
Vahid Hoss
Ameya Malondkar
		    
Music Zone- CD store web site 

Locations:
---Src
    |--MusicZoneBackend
       |-- src
       |-- WebContent  
       |-- dbschema
    |--Music_Zone_View_Controller
       |-- src
       |-- WebContent
|---Readme.txt

1.    Source code folder which includes two projects: MusicZoneBackend and  Music_Zone_View_Controller.

==============================================================
    
Instructions for Configuration/Installation:
----------------------------------------------------------------
Configuration for Project Environment:

First make sure you have the environment ready. Our groups project environment consisted of:
For a) to d) you can refer to http://www.site.uottawa.ca/~lpeyton/ecomsetup.doc if you havn't setup before.
a)  JDK 7
b)  Tomcat 7.0 (Java web server and servlet container)
    Config SSL for tomcat, refer http://www.site.uottawa.ca/~lpeyton/csi5389ssl.html
c)  MySQL 5.5 
d)  Connector J 5.1 (JDBC driver for MySQL)
e)  IDE: Eclipse Kepler.
    Download from http://www.eclipse.org/downloads/packages/eclipse-ide-java-ee-developers/keplersr1
    Add Tomcat 7 in the runtime envronment:
    Click Windows -> Preferences -> Server -> Runtime Environment,
    Click Add -> Choose 'Apache Tomcat v7.0' -> Put your own tomcat home here ->
    Choose default JRE -> Click Finish.
    [NOTE] eclipse will use its own tomcat configurations, keep configuration files like server.xml and web.xml are the same as tomcat's configuration.
f)  Testing Tools: SOAP UI 4.6.0 for webservices (has described in another testing readme)
g)  Use Google Chrome for frontend testing; 

----------------------------------------------------------------

Installation:

1.    Create two Dynamic Web Projects in Eclipse with the name of Music_Zone_View_Controller and MusicZoneBackend
      Copy the contents of "src" and "WebContent" directories of Music_Zone_View_Controller 
      and MusicZoneBackend in the SourceCode to the respective eclipse projects.
2.    Extract Database schema from the following location in MusicZoneBackend: 
             MusicZoneBackend\dbschema\createschema.sql. This will create a new database named music_zone in MySQL with all the tables.
3.    Download Jar files for 2 projects:
      JAX-WS related to be placed under /MusicZoneBackend/WebContent/WEB-INF/lib: 
        . gmbal-api-only.jar
        . ha-api.jar
        . jaxb-impl.jar
        . jaxws-api.jar
        . jaxws-rt.jar
        . management-api.jar
        . policy.jar
        . stax-ex.jar
        . streambuffer.jar
      Download the zip file for above jars from https://jax-ws.java.net/2.2.8/.

      Log4j related jars to be placed /MusicZoneBackend/WebContent/WEB-INF/lib: 
        . log4j-api-2.0-beta9.jar; 
        . log4j-core-2.0-beta9.jar; 
      Download zip file from http://logging.apache.org/log4j/2.x/download.html, then unzip and copy above to /MusicZoneBackend/WEB-INF/lib;

      JSP Standard Tag Library to be placed under /Music_Zone_View_Controller/WebContent/WEB-INF/lib
        . Jstl-1.2.jar
      Download from http://download.java.net/maven/1/jstl/jars/jstl-1.2.jar

4.    For each project check their Java Build Path -> Libraries and make sure there are no errors.
      Also make sure the system environment variables are set to the following:
	SET CLASSPATH=%CLASSPATH%;C:\jakarta-tomcat-5\dist\common\lib\servlet-api.jar
	SET PATH=%PATH%;C:\MySQL\Bin;
	SET CLASSPATH = %CLASSPATH%;C:\lib\mysql-connector-java-3.0.9-stable-bin.jar
      Configure APACHE Server runtime for both projects.
	
5.    Make sure database username, password and url configuration are all right in /MusicZoneBackend/WebContent/META-INF/context.xml:
      a.    username="root", password="root" (Password should be the mysql root password)
      b.    url="jdbc:mysql://localhost:3306/music_zone"
6.    Right click the project name MusicZoneBackend -> Run as -> Run on server, choose tomcat 7, and click Finish.
7.    The web-service clients are already included in controller project path.
8.    Start Music_Zone_View_Controller in the same way.
9.    To confirm the web services running, type the following url in the browser: http://localhost:8080/MusicZoneBackend/OrderProcessorWebService
      To run the Web Application use the following URL in the browser: https://localhost:8443/Music_Zone_View_Controller

----------------------------------------------------------------------------------------------------------------------------------------------

