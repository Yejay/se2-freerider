1. Understand the DAO DataAccess interface

    - An empty Optional is returned in the findCustomerById(id) method if the customer does not exist for the given id.
    - Iterable<T> is used as the return type for some methods because it allows for lazy loading of data and can be more efficient in terms of memory usage.
    - SQL-Query that supports a method numberOfReservationsByCustomer(long customer_id): SELECT COUNT(\*) FROM RESERVATION WHERE CUSTOMER_ID = 2;
    - SQL-Query that supports a method findUsableElectricCars(): SELECT \* FROM VEHICLE WHERE POWER = 'Electric' AND STATUS = 'Active';
    - SQL-Query that supports a method findReservations(long from, long to): SELECT \* FROM RESERVATION WHERE BEGIN >= '2022-12-04 20:00:00' AND END <= '2022-12-20 10:00:00';

2. Understand DAO Queries

    - The JDBC_QueryRunner.java class executes because it is annotated with the @Component annotation and the @EventListener(ApplicationReadyEvent.class) annotation tells Spring to call the runQueries() method when the ApplicationReadyEvent is fired.
    - The JDBC_QueryRunner class has one object of the DataAccess interface, which is the dao variable, initialized by Spring using the @Autowired annotation, and another variable vehicle_dao, which is not annotated with @Autowired.
    - The findAllCustomersById(ids) method returns an Iterable of Customer objects.

3. Understand JDBC Code

    - The @Component annotation is used to mark a Java class as a component and indicates that the class is a bean managed by Spring and is eligible for dependency injection.
    - DataAccessImpl is the implementation class of the DataAccess interface and contains the findAllCustomersById(Iterable<Long> ids) method.
    - The SQL statement "SELECT \* FROM CUSTOMER WHERE ID IN (10, 20, 30000, 40);" is prepared by using the String.format method and the ResultSet is received by using the jdbcTemplate.queryForStream method.

4. Verstehen Sie die DAO DataAccess-Schnittstelle

    - Ein leeres Optional wird im findCustomerById(id) -Methoden zurückgegeben, wenn der Kunde für die gegebene ID nicht vorhanden ist.
    - Iterable<T> wird als Rückgabetyp für einige Methoden verwendet, da es das Nachladen von Daten ermöglicht und in Bezug auf den Speicherverbrauch effizienter sein kann.
    - SQL-Abfrage, die eine Methode numberOfReservationsByCustomer(long customer_id) unterstützt: SELECT COUNT(\*) FROM RESERVATION WHERE CUSTOMER_ID = 2;
    - SQL-Abfrage, die eine Methode findUsableElectricCars() unterstützt: SELECT \* FROM VEHICLE WHERE POWER = 'Electric' AND STATUS = 'Active';
    - SQL-Abfrage, die eine Methode findReservations(long from, long to) unterstützt: SELECT \* FROM RESERVATION WHERE BEGIN >= '2022-12-04 20:00:00' AND END <= '2022-12-20 10:00:00';

5. Verstehen Sie DAO-Abfragen

    - Die JDBC_QueryRunner.java-Klasse wird ausgeführt, da sie mit dem @Component-Annotation versehen ist und die @EventListener(ApplicationReadyEvent.class)-Annotation Spring dazu auffordert, die runQueries()-Methode aufzurufen, wenn das ApplicationReadyEvent ausgelöst wird.
    - Die JDBC_QueryRunner-Klasse hat ein Objekt der DataAccess-Schnittstelle, das dao-Variable ist, die von Spring mit der @Autowired-Annotation initialisiert wird, und eine andere Variable vehicle_dao, die nicht mit @Autowired annotiert ist.
    - Die findAllCustomersById(ids)-Methode gibt ein Iterable von Customer-Objekten zurück.

6. Verstehen Sie JDBC-Code

    - Die @Component-Annotation wird verwendet, um eine Java-Klasse als Komponente zu kennzeichnen und gibt an, dass die Klasse ein Bean von Spring ist und für die Abhängigkeitsinjektion berechtigt ist.
    - DataAccessImpl ist die Implementierungsklasse der DataAccess-Schnittstelle und enthält die findAllCustomersById(Iterable<Long> ids)-Methode.
    - Die SQL-Anweisung "SELECT \* FROM CUSTOMER WHERE ID IN (10, 20, 30000, 40);" wird mit der String.format-Methode vorbereitet und das ResultSet wird mit der jdbcTemplate.queryForStream-Methode empfangen.
