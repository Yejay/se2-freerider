package de.freerider.data_jdbc;

import java.util.Optional;

import de.freerider.datamodel.Vehicle;


/**
 * Public data Vehicle DataAccess interface.
 * 
 * @author sgra64
 */
public interface DataAccessVehicles {

    /**
     * Run query that returns the number of Vehicles in the database:
     * - query: SELECT COUNT(ID) FROM VEHICLE;
     * - returns number extracted from ResultSet.
     * 
     * @return number of Vehicle records in the database.
     */
    long countVehicles();


    /**
     * Run query that returns all Vehicles in the database.
     * - query: SELECT * FROM VEHICLE;
     * - returns Vehicle objects created from ResultSet rows.
     * 
     * @return all Vehicles in the database.
     */
    Iterable<Vehicle> findAllVehicles();


    /**
     * Run query that returns one Vehicles with a given id.
     * - query: SELECT * FROM VEHICLE WHERE ID = 10;
     * - returns Vehicle object created from ResultSet row.
     * 
     * @param id Vehicle id (WHERE ID = ?id)
     * @return Optional with Vehicle or empty if not found.
     */
    Optional<Vehicle> findVehicleById(long id);


    /**
     * Run query that returns all Vehicles with matching id in ids.
     * - query: SELECT * FROM VEHICLE WHERE ID IN (10, 20, 30000, 40);
     * - returns Vehicle objects created from ResultSet rows.
     * 
     * @param ids Vehicle ids (WHERE IN (?ids))
     * @return Vehicles with matching ids.
     */
    Iterable<Vehicle> findAllVehiclesById(Iterable<Long> ids);

}
