package de.freerider.endpoints;

import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.media.ExampleObject;

import de.freerider.datamodel.Reservation;

public interface ReservationsEPDoc {

    static final String api_group_reservations = "Reservations endpoint";

    /**
     * Return all reservations.
     * 
     * - GET /reservations
     * 
     * @return iterable with all Reservations.
     */
    @Operation(
            // group name where this operation appears and defines swagger tag
            // http://localhost:8080/swagger-ui/index.html#/customers-controller
            tags = { api_group_reservations },

            // summary: single-line description in API short-list
            summary = "Return all reservations.",

            // detailed description inside API
            description = "Return all reservations (no limit, no pagination).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json")),
    })
    //
    Iterable<Reservation> findAllReservations();

    /**
     * Return Customer by id.
     * 
     * - GET /customers/{id}
     * 
     * @param id of Customer, id must not be negative.
     * @return Customer with id.
     * @throws ResponseStatusException 400 bad request, 404 not found.
     */
    @Operation(tags = {
            api_group_reservations }, summary = "Return Reservation by id, id must not be negative.", description = "Return Reservation if id exists, else return error 404 (not found)."
    // parameters = {@Parameter(name="id", in=ParameterIn.QUERY, schema=@Schema(implementation=Long.class)) }
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
    })
    //
    Reservation findReservationById(@PathVariable long id);

    /**
     * Create new Customer in database from JSON data received in Request-Body.
     * 
     * - POST /customers
     * 
     * Method receives JSON data as Map<key,attr> as parameter. Atributes are
     * validated, in particular the id-value, which MUST be missing or empty
     * since it is assigned by the database.
     * 
     * If all attributes are valid, the Customer object is created and added to
     * the database.
     * 
     * @param jsonData serialized JSON received with the Request.
     * @return ResponseEntity with serialized Customer object and status code.
     * @throws ResponseStatusException 400 bad request, 409 conflict (customer present).
     */
    @Operation(tags = {
            api_group_reservations }, summary = "Create new Reservation, Reservation id must not exist.", description = "Create new Reservation from JSON data received in the Request-Body.")
    @RequestBody(description = "JSON data from which Reservation object to create is deserialized.", required = true, content = @Content(
            // https://stackoverflow.com/questions/63465763/springdoc-openapi-how-to-add-example-of-post-request
            schema = @Schema(implementation = Reservation.class), mediaType = MediaType.APPLICATION_JSON_VALUE, examples = {
                    @ExampleObject(name = "An example request with the minimum required fields to create.", value = "min", summary = "Minimal request"),
                    @ExampleObject(name = "An example request with all fields provided with example values.", value = "full", summary = "Full request")
            }))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "409", description = "Conflict"),
    })
    //
    ResponseEntity<Reservation> createReservation(@RequestBody Map<String, Object> jsonData);

    /**
     * Update existing Customer in database from JSON data received in Request-Body.
     * 
     * - PUT /customers
     * 
     * Method receives JSON data as Map<key,attr> as parameter. Atributes are
     * validated, in particular the id-value, which MUST must exist in the
     * database in order to update the corresponding database object.
     * 
     * @param jsonData serialized JSON received with the Request.
     * @return empty ResponseEntity with status code.
     * @throws ResponseStatusException 400 bad request, 404 conflict (not found).
     */
    @Operation(tags = {
            api_group_reservations }, summary = "Create new Reservation, Reservation id must not exist.", description = "Create new Reservation from JSON data received in the Request-Body.")
    @RequestBody(description = "JSON data from which Reservation object to update is deserialized.", required = true)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Accepted"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
    })
    //
    ResponseEntity<?> updateReservation(@RequestBody Map<String, Object> jsonData);

    /**
     * Delete Customer with id from database.
     * 
     * - DELETE /customers/{id}
     * 
     * @param id of Customer, id must exist.
     * @return empty ResponseEntity with status code.
     * @throws ResponseStatusException 400 bad request, 404 not found, 409 conflict.
     */
    @Operation(tags = {
            api_group_reservations }, summary = "Delete Reservation with id from database, id must exist.", description = "Delete Reservation with id from database. If id is not found, "
                    +
                    "return error 404 (not found). If Reservation cannot be deleted due to " +
                    "foreign key dependencies, return error 409 (conflict).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Accepted"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "409", description = "Conflict"),
    })
    //
    ResponseEntity<?> deleteReservationById(@PathVariable long id);

}
