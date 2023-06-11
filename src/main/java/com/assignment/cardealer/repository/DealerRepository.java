package com.assignment.cardealer.repository;

import com.assignment.cardealer.model.Dealer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface DealerRepository extends JpaRepository<Dealer, Long> {
    List<Dealer> findDealerByName(String name);
    Optional<Dealer> findDealerById(Long id);
    List<Dealer> findDealerByTierLimit(int tierLimit);
}
