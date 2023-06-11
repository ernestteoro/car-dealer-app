package com.assignment.cardealer.service;

import com.assignment.cardealer.exception.DealerNotFoundException;
import com.assignment.cardealer.model.Dealer;
import com.assignment.cardealer.repository.DealerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class DealerServiceTest {

    @Mock
    private DealerRepository dealerRepository;
    @InjectMocks
    private DealerService dealerService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void givenDealerObject_createDealer_retrunsDealer() {
        Dealer dealer = new Dealer("Ernest", 5);
        when(dealerRepository.save(any(Dealer.class))).thenReturn(dealer);
        Dealer addedDealer = dealerRepository.save(dealer);
        assertEquals(dealer, addedDealer);
        verify(dealerRepository, times(1)).save(any(Dealer.class));
    }

    @Test
    public void findDealer_byDealerId_returnsDealer() throws DealerNotFoundException {
        Dealer dealer = new Dealer("Ernest", 5);
        Long dealerId = 1L;
        dealer.setId(dealerId);
        when(dealerRepository.findById(dealerId)).thenReturn(Optional.of(dealer));
        Optional<Dealer> dealerExist = this.dealerRepository.findById(dealerId);
        assertTrue(dealerExist.isPresent());
        assertEquals(dealer, dealerExist.get());
        verify(dealerRepository, times(1)).findById(dealerId);
    }


    @Test
    public void findAllDealers_should_returnAllDealers() {
        Dealer dealer = new Dealer("Dealer 1", 10);
        dealer.setId(1L);
        Dealer dealer1 = new Dealer("Dealer 2", 5);
        dealer1.setId(2L);
        Dealer dealer2 = new Dealer("Dealer 3", 5);
        dealer1.setId(3L);
        List<Dealer> dealers = Arrays.asList(dealer, dealer1, dealer2);
        when(dealerRepository.findAll()).thenReturn(dealers);
        List<Dealer> listDealers = dealerRepository.findAll();
        assertThat(listDealers).isNotNull();
        assertEquals(dealers, listDealers);
    }

    @Test
    public void updateDealer_ReturnsNewDealer() {
        Long dealerId = 1L;
        Dealer existingDealer = new Dealer("Old Dealer", 5);
        existingDealer.setId(dealerId);
        Dealer newDealer = new Dealer("New Dealer", 9);
        newDealer.setId(1L);

        when(dealerRepository.findById(dealerId)).thenReturn(Optional.of(existingDealer));
        when(dealerRepository.save(any(Dealer.class))).thenReturn(newDealer);
        when(dealerRepository.save(any(Dealer.class))).thenReturn(existingDealer);
        Dealer addedDealer = dealerRepository.save(existingDealer);
        addedDealer.setName("New Dealer");
        addedDealer.setTierLimit(9);
        Dealer updatedDealer = dealerRepository.save(addedDealer);

        assertEquals("New Dealer", updatedDealer.getName());
        assertEquals(9, updatedDealer.getTierLimit());
    }

    @Test
    void deleteById_should_delete_Dealer() {
        Long dealerId = 1L;
        Dealer dealer = new Dealer("Dealer", 5);
        dealer.setId(dealerId);
        when(dealerRepository.findById(dealerId)).thenReturn(Optional.empty());
        when(dealerRepository.save(dealer)).thenReturn(dealer);
        dealerService.deleteDealer(dealerId);
        Optional<Dealer> dealerExist = dealerRepository.findById(dealerId);
        assertFalse(dealerExist.isPresent());
    }

}
