package com.rflwn.app.dto;

import com.rflwn.app.utils.ValidationPatterns;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import javax.validation.constraints.Pattern;


@Data
public class TransportDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Pattern(regexp = ValidationPatterns.carNumberPattern, message = "Invalid state number format")
    private String stateNumber;
    private String brand;
    private int capacity;
}
