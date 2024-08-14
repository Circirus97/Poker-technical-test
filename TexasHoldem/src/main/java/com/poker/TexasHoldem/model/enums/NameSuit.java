package com.poker.TexasHoldem.model.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

@Getter
public enum NameSuit {

    H("Heart"),
    D("Diamond"),
    S("Sword"),
    C("Cane");

    private final String value;


    NameSuit(String value) {
       this.value = value;
    }

    public static Optional<String> fromSuit(String suit) {
        return Arrays.stream(NameSuit.values())
                .filter(cardValue -> cardValue.name().equalsIgnoreCase(suit))
                .findFirst()
                .map(NameSuit::getValue);
    }
}
