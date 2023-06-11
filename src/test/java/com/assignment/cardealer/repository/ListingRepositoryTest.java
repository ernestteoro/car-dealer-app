package com.assignment.cardealer.repository;

import com.assignment.cardealer.model.Dealer;
import com.assignment.cardealer.model.Listing;
import com.assignment.cardealer.model.ListingState;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;



@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ListingRepositoryTest {

    @Autowired
    DealerRepository dealerRepository;

    @Autowired
    ListingRepository listingRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void should_create_listing() {
        Dealer d = Dealer.builder().name("Dealer").tierLimit(5).build();
        Date createdAt = new Date();
        Listing listingObject = Listing.builder()
                .vehicle("BMW")
                .dealer(d)
                .price(320.0)
                .state(ListingState.DRAFT)
                .createdAt(createdAt)
                .build();

        Listing listing = listingRepository.save(listingObject);
        assertThat(listing).hasFieldOrPropertyWithValue("vehicle", "BMW");
        assertThat(listing).hasFieldOrPropertyWithValue("state", ListingState.DRAFT);
        assertThat(listing).hasFieldOrPropertyWithValue("createdAt", createdAt);
    }

    @Test
    public void should_findAll_listings() {
        List<Listing> listListings = listingRepository.findAll();
        Dealer d = Dealer.builder().name("Dealer").tierLimit(5).build();
        entityManager.persist(d);
        Date createdAt = new Date();
        Listing listing = Listing.builder()
                .vehicle("BMW")
                .dealer(d)
                .price(320.0)
                .state(ListingState.DRAFT)
                .createdAt(createdAt)
                .build();
        entityManager.persist(listing);
        Date createdAt1 = new Date();
        Listing listing1 = Listing.builder()
                .vehicle("JEEP WRAGLER")
                .dealer(d)
                .price(520.0)
                .state(ListingState.DRAFT)
                .createdAt(createdAt1)
                .build();

        entityManager.persist(listing1);
        List<Listing> listings = listingRepository.findAll();

        assertThat(listings).contains(listing, listing1);
        assertEquals(listListings.size()+2, listings.size());
    }

    @Test
    public void find_dealerById() {
        Dealer d = Dealer.builder().name("Dealer").tierLimit(5).build();
        entityManager.persist(d);
        Date createdAt = new Date();
        Listing listing = Listing.builder()
                .vehicle("BMW")
                .dealer(d)
                .price(320.0)
                .state(ListingState.DRAFT)
                .createdAt(createdAt)
                .build();
        entityManager.persist(listing);
        Optional<Listing> listingExist = listingRepository.findById(listing.getId());
        assertThat(listingExist).isPresent();
        assertThat(listingExist.get()).isEqualTo(listing);
    }
}
