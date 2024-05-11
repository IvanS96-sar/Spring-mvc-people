package ru.maxima.models;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Person {
    private Long id;
    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2,max = 50, message = "Name should be min2 and max 50")

    private String name;
    @Min(value = 0, message = "Age should be 0 years")
    private Long age;
}
