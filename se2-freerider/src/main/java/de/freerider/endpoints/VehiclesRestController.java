package de.freerider.endpoints;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.freerider.data_jdbc.DataAccessVehicles;
import de.freerider.datamodel.Vehicle;


@RestController
class VehiclesRestController implements VehiclesEP {

    /*
     * Logger instance for this class.
     */
    private static final Logger logger =
        LoggerFactory.getLogger(VehiclesRestController.class);

    /**
     * DataAccess (object) DAO is a component to accesses data in the
     * database through SQL queries.
     */
    @Autowired
    private DataAccessVehicles vehicles_dao;


    @Override
    public Iterable<Vehicle> findAllVehicles() {
        return vehicles_dao.findAllVehicles();
    }


    @Override
    public Vehicle findVehicleById(@PathVariable long id) {
        //
        logger.info(String.format("--- received request: GET /vehicle/%d", id));
        //
        if(id < 0L)
            // throw error 400 (bad request)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                String.format("Vehicle id: %d negative", id, HttpStatus.BAD_REQUEST.value())
            );
        //
        Vehicle found = vehicles_dao.findVehicleById(id)
            .map(c -> c)    // return customer{id}, if found
            //
            //              // else throw error 404 (not found)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                String.format("Vehicle id: %d not found, error %d", id, HttpStatus.NOT_FOUND.value())
            ));
        //
        logger.info(String.format("--- found: Vehicle(id: %d, make: %s, model: %s)", found.getId(), found.getMake(), found.getModel()));
        //
        return found;
    }


}
