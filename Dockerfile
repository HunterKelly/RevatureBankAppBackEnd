FROM tomcat:9.0.65-jre8

ADD /target/curated-banking-spring-1.0-SNAPSHOT.jar curated-banking-spring-1.0-SNAPSHOT.jar

ENTRYPOINT ["java","-jar","/curated-banking-spring-1.0-SNAPSHOT.jar"]