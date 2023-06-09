package com.assignment.cardealer.controller;

import com.assignment.cardealer.exception.ListingNotFoundException;
import com.assignment.cardealer.model.Dealer;
import com.assignment.cardealer.model.Listing;
import com.assignment.cardealer.model.ListingState;
import com.assignment.cardealer.service.DealerService;
import com.assignment.cardealer.service.ListingService;
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
@RequestMapping("/api/v1/listings")
@Tag(name = "Listing operation endpoints")
public class ListingController {

    private final ListingService listingService;
    private final DealerService dealerService;

    @Autowired
    public ListingController(ListingService  listingService, DealerService dealerService){
        this.listingService = listingService;
        this.dealerService = dealerService;
    }

    @GetMapping("/public/{id}")
    @Operation(summary = "findListingById", description = "Find a listing using its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Listing.class)) }),
            @ApiResponse(responseCode = "404", description = "Listing not found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)) }),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)) })
    })
    public ResponseEntity<Listing> findListingById(@PathVariable Long id) {
        Listing listing = listingService.findListingById(id);
        if (listing != null) {
            return new ResponseEntity<>(listing, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "addListing", description = "Add a listing")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successful creation",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Listing.class)) }),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)) })
    })
    @PostMapping("/{dealerId}/add")
    public ResponseEntity<Listing> addListing(@PathVariable Long dealerId, @RequestBody Listing listing) {
        Dealer dealer = dealerService.findDealerById(dealerId);
        listing.setDealer(dealer);
        Listing addedListing = listingService.addListing(listing);
        return new ResponseEntity<>(addedListing, HttpStatus.CREATED);
    }

    @Operation(summary = "getAllListings", description = "Get all listings")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Listing.class)) }),
            @ApiResponse(responseCode = "404", description = "No Listing found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ListingNotFoundException.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)) })
    })
    @GetMapping("/public")
    public ResponseEntity<List<Listing>> getAllListings() {
        List<Listing> listings = listingService.findAllListing();
        return new ResponseEntity<>(listings, HttpStatus.OK);
    }

    @Operation(summary = "updateListing", description = "updating a listing")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Listing.class)) }),
            @ApiResponse(responseCode = "404", description = "No Listing found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)) })
    })
    @PutMapping("/{id}")
    public ResponseEntity<Listing> updateListing(@PathVariable Long id, @RequestBody Listing listing) {
        Listing listingUpdated = listingService.updateListing(id, listing);
        if (listingUpdated == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(listingUpdated, HttpStatus.OK);
    }

    @Operation(summary = "deleteListing", description = "Delete a listing")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Listing.class)) }),
            @ApiResponse(responseCode = "404", description = "No Listing found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ListingNotFoundException.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)) })
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Listing> deleteListing(@PathVariable Long id) {
        listingService.deleteListingById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "publishListing", description = "Publish a listing")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Listing.class)) }),
            @ApiResponse(responseCode = "404", description = "No Listing found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ListingNotFoundException.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)) })
    })
    @PostMapping("/{id}/published")
    public ResponseEntity<Listing> publishListing(@PathVariable Long id, @RequestParam(defaultValue = "false") boolean inTierLimit) {
        Listing publishedListing = listingService.publishListing(id, inTierLimit);
        if (publishedListing == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(publishedListing, HttpStatus.OK);
    }

    @Operation(summary = "unpublishListing", description = "UnPublish a listing")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Listing.class)) }),
            @ApiResponse(responseCode = "404", description = "No Listing found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ListingNotFoundException.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)) })
    })
    @PostMapping("/{id}/unpublished")
    public ResponseEntity<Listing> unpublishListing(@PathVariable Long id) {
        Listing unpublishedListing = listingService.unpublishListing(id);
        return new ResponseEntity<>(unpublishedListing, HttpStatus.OK);
    }

    @Operation(summary = "findListingsByDealer", description = "Find a listing by dealer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Listing.class)) }),
            @ApiResponse(responseCode = "404", description = "No Listing found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ListingNotFoundException.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)) })
    })
    @GetMapping("/public/{id}/dealer")
    public ResponseEntity<List<Listing>> findListingsByDealer(@PathVariable Long id) {
        Dealer dealer = dealerService.findDealerById(id);
        List<Listing> listings = listingService.findListingByDealer(dealer);
        if (listings == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(listings, HttpStatus.OK);
    }

    @Operation(summary = "findListingsByState", description = "Find a listing by state")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Listing.class)) }),
            @ApiResponse(responseCode = "404", description = "Listing not found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ListingNotFoundException.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)) })
    })
    @GetMapping("/public/state")
    public ResponseEntity<List<Listing>> findListingsByState(@RequestParam ListingState state) {
        List<Listing> listings = listingService.findListingByState(state);
        if (listings == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(listings, HttpStatus.OK);
    }

    @Operation(summary = "findListingsByDealerAndState", description = "Find a listing by Dealer and state")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Listing.class)) }),
            @ApiResponse(responseCode = "404", description = "No Listing found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ListingNotFoundException.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)) })
    })
    @GetMapping("/public/dealer/{id}/state")
    public ResponseEntity<List<Listing>> findListingsByDealerAndState(@PathVariable Long id, @RequestParam ListingState state) {
        Dealer dealer = dealerService.findDealerById(id);
        List<Listing> listings = listingService.findListingByDealerAndState(dealer, state);
        if (listings == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(listings, HttpStatus.OK);
    }

}
