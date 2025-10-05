package om.healthmate.javaproject.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "doctors")
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double rating;


    @Column (columnDefinition = "NVARCHAR(255)")
    private String name;

    @Column (columnDefinition = "NVARCHAR(255)")
    private String specialty;

    @Column (columnDefinition = "NVARCHAR(255)")
    private String email;

    @Column (columnDefinition = "NVARCHAR(500)")
    private String imgUrl;

    @Column
    private Double price;

    @Column (columnDefinition = "NVARCHAR(255)")
    private String location; 

    @Column (columnDefinition = "NVARCHAR(255)")
    private String appointmentType; 

    //Getters - Setters
    public Long getId() {return id;}
    public void setId(Long id) {this.id=id;}

    public String getName() {return name;}
    public void setName(String name) {this.name=name;}

    public String getSpecialty() {return specialty;}
    public void setSpecialty(String specialty) {this.specialty=specialty;}

    public String getEmail() {return email;}
    public void setEmail(String email) {this.email=email;}

    public double getRating() {return rating;}
    public void setRating(double rating) {this.rating=rating;}

    public String getImgUrl() {return imgUrl;}
    public void setImgUrl(String imgUrl) {this.imgUrl = imgUrl;}

    public Double getPrice() {return price;}
    public void setPrice(Double price) {this.price=price;}

    public String getLocation() {return location;}
    public void setLocation(String location) {this.location = location;}

    public String getAppointmentType() {return appointmentType;}
    public void setAppointmentType(String appointmentType) {this.appointmentType = appointmentType;}
}


