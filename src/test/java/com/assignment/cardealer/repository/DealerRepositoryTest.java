package com.assignment.cardealer.repository;

import com.assignment.cardealer.model.Dealer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class DealerRepositoryTest {

    @Autowired
    DealerRepository dealerRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void should_create_a_dealer() {
        Dealer d = Dealer.builder().name("New Dealer").tierLimit(5).build();
        Dealer dealer = dealerRepository.save(d);
        assertThat(dealer).hasFieldOrPropertyWithValue("name", "New Dealer");
        assertThat(dealer).hasFieldOrPropertyWithValue("tierLimit", 5);
    }

    @Test
    public void should_findAll_dealers() {
        List<Dealer> listDealers = dealerRepository.findAll();
        Dealer dealer1 = new Dealer("Dealer 1", 5);
        entityManager.persist(dealer1);
        Dealer dealer2 = new Dealer("Dealer 2", 5);
        entityManager.persist(dealer2);
        List<Dealer> dealers = dealerRepository.findAll();

        assertThat(dealers).contains(dealer1, dealer2);
        assertEquals(listDealers.size()+2, dealers.size());
    }

    @Test
    public void find_dealerById() {
        Dealer dealer = new Dealer("Dealer", 5);
        entityManager.persist(dealer);
        Optional<Dealer> dealerExist = dealerRepository.findById(dealer.getId());
        assertThat(dealerExist).isPresent();
        assertThat(dealerExist.get()).isEqualTo(dealer);
    }
}
