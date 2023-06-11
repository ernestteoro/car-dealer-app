package com.assignment.cardealer.service;

import com.assignment.cardealer.exception.DealerNotFoundException;
import com.assignment.cardealer.model.Dealer;
import com.assignment.cardealer.model.Listing;
import com.assignment.cardealer.model.ListingState;
import com.assignment.cardealer.repository.DealerRepository;
import com.assignment.cardealer.repository.ListingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.empty;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ListingServiceTest {

    @Mock
    private ListingRepository listingRepository;

    @Mock
    private DealerRepository dealerRepository;

    @InjectMocks
    private ListingService listingService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void givenListingObject_createListing_retrunsListing() {
        Dealer dealer  = new Dealer("Dealer",2);
        dealer.setId(1L);
        Listing listing = Listing.builder()
                .state(ListingState.DRAFT)
                .price(120.0)
                .vehicle("BMW").build();
        when(listingRepository.save(any(Listing.class))).thenReturn(listing);
        when(dealerRepository.save(any(Dealer.class))).thenReturn(dealer);
        Dealer savedDealer = dealerRepository.save(dealer);
        listing.setDealer(savedDealer);
        Listing addedListing = listingRepository.save(listing);
        assertEquals(listing, addedListing);
        verify(listingRepository, times(1)).save(any(Listing.class));
    }

    @Test
    public void findListing_byListingId_returnsListing() throws DealerNotFoundException {
        Long dealerId = 1L;
        Dealer dealer  = new Dealer("Dealer",2);
        dealer.setId(dealerId);
        Listing listing = Listing.builder()
                .state(ListingState.DRAFT)
                .price(120.0)
                .vehicle("BMW").id(dealerId).build();
        when(listingRepository.findById(dealerId)).thenReturn(Optional.of(listing));
        Optional<Listing> listingExist = listingRepository.findById(dealerId);
        assertTrue(listingExist.isPresent());
        assertEquals(listing, listingExist.get());
        verify(listingRepository, times(1)).findById(dealerId);
    }


    @Test
    public void findAllListings_should_returnAllListings() {
        Listing listing = Listing.builder()
                .state(ListingState.DRAFT)
                .price(120.0)
                .vehicle("BMW").id(1L).build();
        Listing listing1 = Listing.builder()
                .state(ListingState.DRAFT)
                .price(120.0)
                .vehicle("Mercedes").id(2L).build();
        Listing listing2 = Listing.builder()
                .state(ListingState.DRAFT)
                .price(120.0)
                .vehicle("Jeep").id(3L).build();
        List<Listing> listings = Arrays.asList(listing, listing1, listing2);
        when(listingRepository.findAll()).thenReturn(listings);
        List<Listing> listListings = listingRepository.findAll();
        assertThat(listListings).isNotNull();
        assertEquals(listings, listListings);
    }

    @Test
    public void updateListing_ReturnsNewListing() {
        Long listingId = 1L;
        Listing oldListing = Listing.builder()
                .state(ListingState.DRAFT)
                .price(120.0)
                .vehicle("BMW").id(1L).build();
        Listing newListing = Listing.builder()
                .state(ListingState.DRAFT)
                .price(320.0)
                .vehicle("Mercedes").id(1L).build();

        when(listingRepository.findById(listingId)).thenReturn(Optional.of(oldListing));
        when(listingRepository.save(any(Listing.class))).thenReturn(newListing);
        when(listingRepository.save(any(Listing.class))).thenReturn(oldListing);
        Listing addedListing = listingRepository.save(oldListing);
        addedListing.setVehicle("Mercedes");
        addedListing.setPrice(320.0);
        Listing updatedListing = listingRepository.save(addedListing);

        assertEquals("Mercedes", updatedListing.getVehicle());
        assertEquals(320.0, updatedListing.getPrice());
        assertEquals(ListingState.DRAFT, updatedListing.getState());
    }



    @Test
    void deleteById_should_delete_Listing() {
        Long listingId = 1L;
        Listing listing = Listing.builder()
                .state(ListingState.DRAFT)
                .price(120.0)
                .vehicle("Mercedes").id(1L).build();
        listing.setId(listingId);
        when(listingRepository.findById(listingId)).thenReturn(Optional.empty());
        when(listingRepository.save(listing)).thenReturn(listing);
        listingService.deleteListingById(listingId);
        Optional<Listing> listingExist = listingRepository.findById(listingId);
        assertFalse(listingExist.isPresent());
    }


}
