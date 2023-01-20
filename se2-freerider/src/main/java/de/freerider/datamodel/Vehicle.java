package de.freerider.datamodel;


/**
 * Entity class for Vehicle.
 * 
 * Schema in database:
 * +----------+----------------------------------------+------+-----+---------+
 * | Field    | Type                                   | Null | Key | Default |
 * +----------+----------------------------------------+------+-----+---------+
 * | ID       | int                                    | NO   | PRI | NULL    |
 * | MAKE     | varchar(60)                            | YES  |     | NULL    |
 * | MODEL    | varchar(60)                            | YES  |     | NULL    |
 * | SEATS    | int                                    | YES  |     | 4       |
 * | CATEGORY | enum('Sedan','SUV','Convertible',      | YES  |     | NULL    |
 * |          |   'Van', 'Bike')                       |      |     |         |
 * | POWER    | enum('Gasoline','Diesel','Electric',   | YES  |     | NULL    |
 * |          |   'Hybrid','Hydrogen')                 | YES  |     | NULL    |
 * | STATUS   | enum('Active','Serviced','Terminated') | YES  |     | NULL    |
 * +----------+----------------------------------------+------+-----+---------+
 * 
 * @author sgra64
 */
public final class Vehicle {

    /*
     * Vehicle attribute: id.
     */
    private final long id;

    /*
     * Vehicle attribute: make, brand of a manufacturer, e.g. "VW".
     */
    private final String make;

    /*
     * Vehicle attribute: model, vehicle model, e.g. "Golf".
     */
    private final String model;

    /*
     * Vehicle attribute: seats, number of seats.
     */
    private final int seats;

    /*
     * Vehicle attribute: category (Sedan, SUV, Convertible, Van, Bike).
     */
    private final Category category;

    /*
     * Vehicle attribute: power, source of power (Gasoline, Diesel, Electric, Hybrid, Hydrogen).
     */
    private final Power power;

    /*
     * Vehicle attribute: status (Active, Serviced, Terminated).
     */
    private Status status;


    /**
     * Vehicle category.
     */
    public enum Category {
        Sedan, SUV, Convertible, Van, Bike
    };

    /**
     * Main power source of a vehicle.
     */
    public enum Power {
        Gasoline, Diesel, Electric, Hybrid, Hydrogen
    };

    /**
     * Lifecycle and Status information of a Vehicle.
     */
    public enum Status {
        Active, Serviced, Terminated
    };


    /**
     * Non-public constructor that prevents creating instances outside
     * this package. Instances can only be created through DataFactory.
     * 
     * @param id unique identifier, PRIMARY KEY in database.
     * @param make brand name of Vehicle, e.g. "VW" or "Tesla"
     * @param model model name of Vehicle, e.g. "ID.4"
     * @param seats number of seats in Vehicle.
     * @param category category of Vehicle.
     * @param power power source of Vehicle.
     * @param status status of Vehicle.
     * @throws IllegalArgumentException for illegal parameters.
     */
    Vehicle(long id, String make, String model, int seats, String category, String power, String status) {
        if(id < 0)
            throw new IllegalArgumentException(String.format("id: %d, id < 0", id));
        if(make==null || make.length()==0)
            throw new IllegalArgumentException("make is null or empty");
        if(model==null || model.length()==0)
            throw new IllegalArgumentException("model is null or empty");
        if(seats <= 0 || seats > 100)
            throw new IllegalArgumentException(String.format("seats: %d, seats <= 0 || seats > 100", seats));
        if(category==null)
            throw new IllegalArgumentException("category is null");
        if(power==null)
            throw new IllegalArgumentException("power is null");
        //
        this.id = id;
        this.make = make;
        this.model = model;
        this.seats = seats;
        this.category = strToCategoryEnum(category);
        this.power = strToPowerEnum(power);
        setStatus(strToStatusEnum(status));
    }


    /**
     * Public id attribute getter.
     * 
     * @return unique Vehicle identifier, PRIMARY KEY in database.
     */
    public long getId() {
        return id;
    }


    /**
     * Public make attribute getter.
     * 
     * @return brand name of Vehicle, e.g. "VW" or "Tesla".
     */
    public String getMake() {
        return make;
    }


    /**
     * Public model attribute getter.
     * 
     * @return model name of Vehicle, e.g. "ID.4"
     */
    public String getModel() {
        return model;
    }


    /**
     * Public seats attribute getter.
     * 
     * @return number of seats in Vehicle.
     */
    public int getSeats() {
        return seats;
    }


    /**
     * Public category attribute getter.
     * 
     * @return category of Vehicle.
     */
    public Category getCategory() {
        return this.category;
    }


    /**
     * Public power attribute getter.
     * 
     * @return power source of Vehicle.
     */
    public Power getPower() {
        return this.power;
    }


    /**
     * Public status attribute getter.
     * 
     * @return status of Vehicle.
     */
    public Status getStatus() {
        return status;
    }


    /**
     * Public status attribute setter.
     * 
     * @param status status of Vehicle.
     * @return chainable self-reference.
     * @throws IllegalArgumentException for illegal status parameter.
     */
    public Vehicle setStatus(Status status) {
        if(status==null)
            throw new IllegalArgumentException("status is null");
        //
        this.status = status;
        return this;
    }


    /**
     * Convert category from String to enum value.
     * Example: {@code "Active"} to {@code Status.Active}.
     * 
     * @param category status as String.
     * @return category as enum value.
     * @throws IllegalArgumentException for unparsable or not matching status String.
     */
    private Category strToCategoryEnum(String category) {
        if(category==null)
            throw new IllegalArgumentException("category is null");
        //
        for(var e : Category.values()) {   // ignore cases
            if(e.name().compareToIgnoreCase(category) == 0) {
                return e;
            }
        }
        throw new IllegalArgumentException(String.format("can't parse Category from: \"%s\"", category));
    }


    /**
     * Convert power from String to enum value.
     * Example: {@code "Active"} to {@code Status.Active}.
     * 
     * @param power status as String.
     * @return power as enum value.
     * @throws IllegalArgumentException for unparsable or not matching status String.
     */
    private Power strToPowerEnum(String power) {
        if(power==null)
            throw new IllegalArgumentException("status is null");
        //
        for(var e : Power.values()) {   // ignore cases
            if(e.name().compareToIgnoreCase(power) == 0) {
                return e;
            }
        }
        throw new IllegalArgumentException(String.format("can't parse Power from: \"%s\"", power));
    }


    /**
     * Convert status from String to enum value.
     * Example: {@code "Active"} to {@code Status.Active}.
     * 
     * @param status status as String.
     * @return status as enum value.
     * @throws IllegalArgumentException for unparsable or not matching status String.
     */
    private Status strToStatusEnum(String status) {
        if(status==null)
            throw new IllegalArgumentException("status is null");
        //
        for(var s : Status.values()) {   // ignore cases
            if(s.name().compareToIgnoreCase(status) == 0) {
                return s;
            }
        }
        throw new IllegalArgumentException(String.format("can't parse Status from: \"%s\"", status));
    }
}
