package de.freerider.endpoints;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;

import de.freerider.datamodel.Vehicle;


/** 
 * TODO - comments with correct status codes
 * Spring Controller interface for /customers REST endpoint to access the
 * collection of customer resources maintained in a CustomerRepository.
 * 
 * Operations provided by the endpoint:
 * 
 * - GET /vehicles         - return JSON data for all customer in the repository,
 *                            status: 200 OK.
 * 
 * - GET /vehicles/{id}    - return JSON data for customer with id,
 *                            status: 200 OK, 400 bad request (id), 404 not found.
 *
 */

@RequestMapping("/v1/vehicles")
public interface VehiclesEP extends VehiclesEPDoc {

    @GetMapping("")
    @Override
    Iterable<Vehicle> findAllVehicles();


    @GetMapping("/{id}")
    @Override
    Vehicle findVehicleById(@PathVariable long id);


}
