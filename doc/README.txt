courier-tracking

1.PROJECT_DESCRIPTION
2.GENERAL_NOTES
3.LOCAL_DEVELOPMENT_SETUP

1.PROJECT_DESCRIPTION

courier-tracking
Used technologies: maven, Spring Boot, Hibernate, H2 (for local development)


2.GENERAL_NOTES

package structure:
    md.ct.controller: rest endpoint classes are stored here
    md.ct.dto: DTO classes are here, controller classes receive DTO return DTO
    md.ct.domain: @Entity annotated classes are stored here, ORM mapped classes
    md.ct.repository: JPA/Hibernate query methods are stored here, each @Entity class has its own Repo such as Courier and Store
    md.ct.service: service classes are stored here, actual operations are done inside these classes

src/main/resources/application.properties
    project configuration file, important features:
        spring.jpa.hibernate.ddl-auto:
            create: drop existing tables create new tables
            none: don't change existing db tables

        spring.jpa.properties.hibernate.dialect:
            set db dialect

doc/ : sample postman requests and this readme.txt file stored here

3.LOCAL_DEVELOPMENT_SETUP

no external db required
    src/main/resources/application.properties set "spring.jpa.hibernate.ddl-auto=" to "create"
    start application from IDE:
        open Application.java, click play icon
        empty db tables will be created
        in console you should see:
            "2020-02-02 21:58:05.233  INFO 7235 --- [ main] md.ct.Application : Started Application in 3.264 seconds (JVM running for 3.568)"
        program ready to use

    sample postman requests are stored in doc/ folder, import from postman and use


    1- streamGeolocationOfCourier
    2- getTotalDistanceByCourier

