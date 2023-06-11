package com.assignment.cardealer.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "listings", schema = "car_dealers")
public class Listing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Dealer dealer;
    private String vehicle;
    private double price;
    private Date createdAt;

    @Enumerated(EnumType.STRING)
    private ListingState state ;

}
