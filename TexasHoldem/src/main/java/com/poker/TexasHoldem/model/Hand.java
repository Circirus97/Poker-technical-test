package com.poker.TexasHoldem.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Hand {

    @NotBlank(message = "You are required to enter a hand of cards. Please provide a valid hand")
    private String cards;

}
