package com.poker.TexasHoldem.model.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

@Getter
public enum NameSuit {

    H("Heart", 3),
    D("Diamond", 2),
    S("Sword", 4),
    C("Cane", 1);

    private final String value;
    private final Integer valueWinning;


    NameSuit(String value, Integer valueWinning) {
       this.value = value;
       this.valueWinning = valueWinning;
    }

    public static Optional<String> fromSuit(String suit) {
        return Arrays.stream(NameSuit.values())
                .filter(cardValue -> cardValue.name().equals(suit))
                .findFirst()
                .map(NameSuit::getValue);
    }
}
