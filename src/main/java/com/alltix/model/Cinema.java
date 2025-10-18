package com.alltix.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cinemas")
public class Cinema {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    private String location;
    private String city;
    private String contactNumber;
    private Integer totalScreens;

    @ElementCollection
    @CollectionTable(name = "cinema_facilities", joinColumns = @JoinColumn(name = "cinema_id"))
    @Column(name = "facility")
    private List<String> facilities = new ArrayList<>();

    // Constructors
    public Cinema() {}

    public Cinema(String name, String location, String city, String contactNumber, Integer totalScreens) {
        this.name = name;
        this.location = location;
        this.city = city;
        this.contactNumber = contactNumber;
        this.totalScreens = totalScreens;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getContactNumber() { return contactNumber; }
    public void setContactNumber(String contactNumber) { this.contactNumber = contactNumber; }

    public Integer getTotalScreens() { return totalScreens; }
    public void setTotalScreens(Integer totalScreens) { this.totalScreens = totalScreens; }

    public List<String> getFacilities() { return facilities; }
    public void setFacilities(List<String> facilities) { this.facilities = facilities; }

    // Helper method
    public void addFacility(String facility) {
        this.facilities.add(facility);
    }
}