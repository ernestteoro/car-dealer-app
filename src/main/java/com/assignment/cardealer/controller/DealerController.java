package com.assignment.cardealer.controller;

import com.assignment.cardealer.exception.DealerNotFoundException;
import com.assignment.cardealer.model.Dealer;
import com.assignment.cardealer.model.Listing;
import com.assignment.cardealer.service.DealerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/dealers")
@Tag(name = "Dealer operation endpoints")
public class DealerController {
    private final DealerService dealerService;

    @Autowired
    public DealerController(DealerService dealerService){
        this.dealerService = dealerService;
    }

    @Operation(summary = "findDealerByName", description = "Find a dealer using his name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Dealer.class)) }),
            @ApiResponse(responseCode = "404", description = "Dealer not found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DealerNotFoundException.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)) })
    })
    @GetMapping("/public/{name}/name")
    public ResponseEntity<List<Dealer>> findDealerByName(@PathVariable String name){
        List<Dealer> dealers = dealerService.findDealerByName(name);
        if(dealers == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(dealers, HttpStatus.OK);
    }

    @Operation(summary = "findDealerById", description = "Find a dealer using ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Dealer.class)) }),
            @ApiResponse(responseCode = "404", description = "No Dealer found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DealerNotFoundException.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)) })
    })
    @GetMapping("/public/{id}")
    public ResponseEntity<Dealer> findDealerById(@PathVariable  Long id){
        Dealer dealer = dealerService.findDealerById(id);
        if(dealer == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(dealer, HttpStatus.OK);
    }

    @Operation(summary = "findDealerByTierLimit", description = "Find dealers using the tier limit")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Dealer.class)) }),
            @ApiResponse(responseCode = "404", description = "No Dealer found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DealerNotFoundException.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)) })
    })
    @GetMapping("/public/{tierLimit}/tierLimit")
    public ResponseEntity<List<Dealer>> findDealerByTierLimit(@PathVariable int tierLimit){
        List<Dealer> dealers = dealerService.findDealerByLimit(tierLimit);
        if(dealers == null || dealers.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(dealers, HttpStatus.OK);
    }

    @Operation(summary = "deleteDealer", description = "Delete a dealer by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Dealer.class)) }),
            @ApiResponse(responseCode = "404", description = "No Dealer found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DealerNotFoundException.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)) })
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Listing> deleteDealer(@PathVariable Long id) {
        dealerService.deleteDealer(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "addDealer", description = "Add a dealer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successful creation",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Dealer.class)) }),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)) })
    })
    @PostMapping
    public ResponseEntity<Dealer> addDealer(@RequestBody Dealer dealer){
        Dealer addedDealer = dealerService.addDealer(dealer);
        return new ResponseEntity<>(addedDealer, HttpStatus.CREATED);
    }

    @Operation(summary = "getAllDealer", description = "Get all dealers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Dealer.class)) }),
            @ApiResponse(responseCode = "404", description = "No dealer found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DealerNotFoundException.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)) })
    })
    @GetMapping("/public")
    public ResponseEntity<List<Dealer>> getAllDealer(){
        List<Dealer> dealers = dealerService.findAllDealer();
        return new ResponseEntity<>(dealers, HttpStatus.OK);
    }
}
