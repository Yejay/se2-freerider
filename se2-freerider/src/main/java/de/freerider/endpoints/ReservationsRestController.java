package de.freerider.endpoints;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.freerider.data_jdbc.DataAccessException;
import de.freerider.data_jdbc.DataAccessReservations;
import de.freerider.datamodel.Reservation;

@RestController
class ReservationsRestController implements ReservationsEP {

    /*
     * Logger instance for this class.
     */
    private static final Logger logger = LoggerFactory.getLogger(CustomersRestController.class);

    /**
     * DataAccess (object) DAO is a component to accesses data in the
     * database through SQL queries.
     */
    @Autowired
    private DataAccessReservations reservations_dao;

    @Override
    public Iterable<Reservation> findAllReservations() {
        return reservations_dao.findAllReservations();
    }

    @Override
    public Reservation findReservationById(@PathVariable long id) {
        //
        logger.info(String.format("--- received request: GET /reservation/%d", id));
        //
        if (id < 0L)
            // throw error 400 (bad request)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    String.format("Reservation id: %d negative", id, HttpStatus.BAD_REQUEST.value()));
        //
        Reservation found = reservations_dao.findReservationById(id)
                .map(c -> c) // return customer{id}, if found
                //
                //              // else throw error 404 (not found)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("Reservation id: %d not found, error %d", id, HttpStatus.NOT_FOUND.value())));
        //
        logger.info(String.format(
                "--- found: Reservation(id: %d, customer_id: %d,vehicle_id: %d,begin: %s, end: %s, pickup: %s, dropoff: %s,status: %s",
                found.getId(), found.getCustomerId(),
                found.getVehicleId(),
                found.getBegin(), found.getEnd(), found.getPickup(), found.getDropoff(),
                found.getStatus()));
        //
        return found;
    }

    @Override
    public ResponseEntity<Reservation> createReservation(@RequestBody Map<String, Object> jsonData) {
        //
        logger.info(String.format("\n--- received POST (create): Reservation JSON data: \n"));
        System.out.println(jsonData);
        //
        try {
            //
            Reservation reservation = reservations_dao.createReservation(jsonData);
            //
            logger.info(String.format(
                    "--- new Reservation object created: [%d, \"%d\", \"%d\", \"%s\", \"%s\", \"%s\", \"%s\", \"%s\"]",
                    reservation.getId(), reservation.getCustomerId(), reservation.getVehicleId(),
                    reservation.getBegin(), reservation.getEnd(), reservation.getPickup(), reservation.getDropoff(),
                    reservation.getStatus()));
            //
            // return Customer object (serialized to JSON)
            return ResponseEntity.status(HttpStatus.CREATED).body(reservation);
            //
        } catch (DataAccessException dax) {
            reThrow(dax, "DataAccessException dax: " + dax.getMessage());
        }
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    @Override
    public ResponseEntity<?> updateReservation(Map<String, Object> jsonData) {
        //
        logger.info(String.format("--- received PUT (update): Reservation JSON data:"));
        System.out.println(jsonData);
        //
        var respCode = HttpStatus.NOT_IMPLEMENTED;
        try {
            reservations_dao.updateReservation(jsonData);
            logger.info(String.format("--- Reservation object updated"));
            respCode = HttpStatus.ACCEPTED;
            //
        } catch (DataAccessException dax) {
            reThrow(dax, "DataAccessException dax: " + dax.getMessage());
        }
        return ResponseEntity.status(respCode).build();
    }

    @Override
    public ResponseEntity<?> deleteReservationById(long id) {
        //
        logger.info(String.format("--- received request: DELETE /reservation/%d", id));
        //
        var respCode = HttpStatus.NOT_IMPLEMENTED;
        try {
            reservations_dao.deleteReservation(id);
            respCode = HttpStatus.ACCEPTED;
            //
        } catch (DataAccessException dax) {
            reThrow(dax, "DataAccessException dax: " + dax.getMessage());
        }
        return ResponseEntity.status(respCode).build();
    }

    /**
     * Map exceptions of type DataAccessException used in the data access layer
     * to HTTP ResponseStatusExceptions used in the Controller layer.
     * 
     * @param dax DataAccessException from the data access layer.
     * @param msg exception message.
     * @throws ResponseStatusException return to HTTP client.
     */
    private void reThrow(DataAccessException dax, String msg) throws ResponseStatusException {
        var respCode = HttpStatus.NOT_IMPLEMENTED;
        switch (dax.code) {
            case BadRequest:
                respCode = HttpStatus.BAD_REQUEST;
                break;
            case NotFound:
                respCode = HttpStatus.NOT_FOUND;
                break;
            case Conflict:
                respCode = HttpStatus.CONFLICT;
                break;
        }
        throw new ResponseStatusException(respCode, msg);
    }

}
