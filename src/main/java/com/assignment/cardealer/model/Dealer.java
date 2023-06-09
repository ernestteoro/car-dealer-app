package com.assignment.cardealer.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "dealers", schema = "car_dealers")
public class Dealer {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    private String name;
    private int tierLimit;

    @JsonIgnore
    @OneToMany(mappedBy = "dealer")
    private List<Listing> listings;

    public Dealer() {}

    public Dealer(String name, int tierLimit) {
        this.name = name;
        this.tierLimit = tierLimit;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTierLimit() {
        return tierLimit;
    }

    public void setTierLimit(int limit) {
        this.tierLimit = limit;
    }

    public List<Listing> getListings() {
        return listings;
    }

    public void setListings(List<Listing> listings) {
        this.listings = listings;
    }
}
