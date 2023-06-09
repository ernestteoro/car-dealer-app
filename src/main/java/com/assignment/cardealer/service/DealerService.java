package com.assignment.cardealer.service;

import com.assignment.cardealer.model.Dealer;
import com.assignment.cardealer.repository.DealerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class DealerService {
    private final DealerRepository dealerRepository;

    @Autowired
    DealerService(DealerRepository dealerRepository){
        this.dealerRepository = dealerRepository;
    }

    public List<Dealer> findDealerByName(String name){
        return dealerRepository.findDealerByName(name);
    }

    public Dealer findDealerById(Long id){
        return dealerRepository.findDealerById(id).orElse(null);
    }

    public List<Dealer> findAllDealer(){
        return dealerRepository.findAll();
    }
    public List<Dealer> findDealerByLimit(int tierLimit){
        return dealerRepository.findDealerByTierLimit(tierLimit);
    }

    public void deleteDealer(Long id) {
        dealerRepository.findDealerById(id).ifPresent(dealerRepository::delete);
    }

    public Dealer addDealer(Dealer dealer){
        return dealerRepository.save(dealer);
    }
}
