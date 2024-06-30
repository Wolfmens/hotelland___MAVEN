package com.study.hotelland.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "hotel")
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String headline;

    private String city;

    private String address;

    @Column(name = "distance_to_center")
    private Long distanceToCenter;

    @Column(name = "hotel_rating")
    private Double hotelRating;

    @Column(name = "number_ratings")
    private Long numberRatings;
}
