package com.assignment.cardealer.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "dealers", schema = "car_dealers")
public class Dealer {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    private String name;
    private int tierLimit;

    @JsonIgnore
    @OneToMany(mappedBy = "dealer", fetch = FetchType.LAZY)
    private List<Listing> listings;

    public Dealer(Long id, String name, int tierLimit) {
        this.name = name;
        this.tierLimit = tierLimit;
    }

    public Dealer(String name, int tierLimit) {
        this.name = name;
        this.tierLimit = tierLimit;
    }

}
