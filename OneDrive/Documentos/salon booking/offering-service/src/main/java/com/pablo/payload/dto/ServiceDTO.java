package com.pablo.payload.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
public class ServiceDTO {
    private Long id;

    private String nome;

    private String description;

    private int price;

    private int duration;

    private Long salonId;

    private Long categoryId;

    private String image;
}
