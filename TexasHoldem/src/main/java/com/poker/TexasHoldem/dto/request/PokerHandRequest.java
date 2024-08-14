package com.poker.TexasHoldem.dto.request;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PokerHandRequest {

    @NotBlank(message = "You are required to enter a hand of cards. Please provide a valid hand.")
    private String hand1;

    @NotBlank(message = "You are required to enter a hand of cards. Please provide a valid hand.")
    private String hand2;
}
