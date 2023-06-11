package com.assignment.cardealer.service;

import com.assignment.cardealer.exception.TierLimitException;
import com.assignment.cardealer.model.Dealer;
import com.assignment.cardealer.model.Listing;
import com.assignment.cardealer.model.ListingState;
import com.assignment.cardealer.repository.DealerRepository;
import com.assignment.cardealer.repository.ListingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ListingService {

    private final ListingRepository listingRepository;
    private final DealerRepository dealerRepository;

    @Autowired
    public ListingService(ListingRepository listingRepository, DealerRepository dealerRepository){
        this.listingRepository = listingRepository;
        this.dealerRepository = dealerRepository;
    }

    public Listing addListing(Listing listing) {
        listing.setState(ListingState.DRAFT);
        listing.setCreatedAt(new Date());
        return listingRepository.save(listing);
    }

    public Listing findListingById(Long id){
        return this.listingRepository.findListingById(id).orElse(null);
    }

    public List<Listing> findAllListing(){
        return this.listingRepository.findAll();
    }

    public List<Listing> findListingByDealer(Dealer dealer){
        return this.listingRepository.findListingByDealer(dealer);
    }

    public List<Listing> findListingByState(ListingState listingState){
        return this.listingRepository.findListingByState(listingState);
    }

    public List<Listing> findListingByDealerAndState(Dealer dealer, ListingState listingState){
        return this.listingRepository.findListingByDealerAndState(dealer, listingState);
    }

    public Listing updateListing(Long id, Listing listing){
        Listing listingExist = findListingById(id);
        if(listingExist ==null){
            return null;
        }
        listingExist.setPrice(listing.getPrice());
        listingExist.setVehicle(listing.getVehicle());
        return this.listingRepository.save(listingExist);

    }

    public Listing publishListing(Long id, boolean inTierLimit) {
        Listing listing = findListingById(id);
        Dealer dealer = listing.getDealer();
        int listingCount = totalListingPublished(dealer.getId());
        int tierLimit = dealer.getTierLimit();
        if(inTierLimit){
            if (listingCount == tierLimit) {
                Listing oldestListing = listingRepository.findFirstByDealerAndStateOrderByCreatedAtAsc(dealer, ListingState.PUBLISHED);
                oldestListing.setState(ListingState.DRAFT);
                listingRepository.save(oldestListing);
            }
        }else if(listingCount >= tierLimit){
            throw new TierLimitException(dealer.getName());
        }

        listing.setState(ListingState.PUBLISHED);
        return listingRepository.save(listing);
    }

    public Listing unpublishListing(Long id) {
        Listing listing = findListingById(id);
        listing.setState(ListingState.DRAFT);
        return listingRepository.save(listing);
    }

    private int totalListingPublished(Long dealerId) {
        Dealer dealer =dealerRepository.findDealerById(dealerId).orElse(null);
        return listingRepository.findListingByDealerAndState(dealer, ListingState.PUBLISHED).size();
    }

    public void deleteListingById(Long id){
        Listing listing = findListingById(id);
        listingRepository.delete(listing);
    }

}
