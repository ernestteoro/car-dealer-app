package com.assignment.cardealer.repository;

import com.assignment.cardealer.model.Dealer;
import com.assignment.cardealer.model.Listing;
import com.assignment.cardealer.model.ListingState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ListingRepository extends JpaRepository<Listing, Long> {
    Optional<Listing> findListingById(Long id);
    List<Listing> findAll();
    List<Listing> findListingByDealer(Dealer dealer);
    List<Listing> findListingByState(ListingState listingState);
    List<Listing> findListingByDealerAndState(Dealer dealer, ListingState listingState);

    List<Listing>  findListingByDealerAndStateOrderByCreatedAtAsc(Dealer dealer, ListingState listingState);
    Listing  findFirstByDealerAndStateOrderByCreatedAtAsc(Dealer dealer, ListingState listingState);
}
