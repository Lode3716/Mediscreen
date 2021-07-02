# MEDISCREEN 
Mediscreen is a Spring Boot application allowing the detection of disease risk factors (such as diabetes),
by predictive analysis of patient populations. 

## Getting Started

The information will allow you to install the elements necessary for the functioning of the mediscreen environment. 

### Technical

#### Prerequisites
![Java Version](https://img.shields.io/badge/Java-15.x-red)
![Maven Version](https://img.shields.io/badge/Maven-3.6.3-blue)
![SpringBoot Version](https://img.shields.io/badge/Spring%20Boot-2.4.4-brightgreen)
![Angular Version](https://img.shields.io/badge/Angular-11.2-red)
![MySQL Version](https://img.shields.io/badge/MySQL-8.x-cyan)
![MongoDB Version](https://img.shields.io/badge/MongoDB-4.x-green)
![J-Unit Version](https://img.shields.io/badge/JUnit-5.7.0-orange)
![Docker Version](https://img.shields.io/badge/Docker-20.10.2-cyan)

Please setup the following tools: 

* Intellij IDE : (https://www.jetbrains.com/idea/) 
* Java 15 : (https://adoptopenjdk.net/?variant=openjdk15&jvmVariant=hotspot) 
* Maven 3.6.3 : (https://maven.apache.org/install.html) 
* MySql : (https://dev.mysql.com/downloads/mysql/)
* MongoDB : (https://www.mongodb.com/try/download/community)
* Docker  : (https://www.docker.com/products/docker-desktop)

## Installation

### Download others workspace :
 
* Front-end : (https://github.com/Lode3716/medicreen-front/tree/main)
* Service-note : (https://github.com/Lode3716/note)
* Service-patient : (https://github.com/Lode3716/Mediscreen)
* Service-probability : (https://github.com/Lode3716/mediscreen-probability-diabete)

### Build services with maven
~~~
mvn clean intall
~~~
### For Angular
~~~
ng build
~~~

### Docker construction in project directory :

~~~
docker-compose up -d
~~~
## Rapport test

![alt text](test_patient.png)
![alt text](rapport_patient.png)


