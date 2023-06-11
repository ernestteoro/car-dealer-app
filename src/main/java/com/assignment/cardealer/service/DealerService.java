package com.assignment.cardealer.service;

import com.assignment.cardealer.exception.DealerNotFoundException;
import com.assignment.cardealer.model.Dealer;
import com.assignment.cardealer.repository.DealerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
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
        Optional<Dealer> dealer = dealerRepository.findDealerById(id);
        if (dealer.isPresent()){
            dealerRepository.deleteById(id);
        }
    }

    public Dealer addDealer(Dealer dealer){
        return dealerRepository.save(dealer);
    }

    public Dealer updateDealer(Long id, Dealer dealer){
        Dealer d = dealerRepository.findDealerById(id).orElseThrow(() -> new DealerNotFoundException(id.toString()));
        d.setName(dealer.getName());
        d.setTierLimit(dealer.getTierLimit());
        return dealerRepository.save(d);
    }

}
