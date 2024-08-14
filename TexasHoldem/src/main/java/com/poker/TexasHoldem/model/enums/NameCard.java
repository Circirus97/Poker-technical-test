package com.poker.TexasHoldem.model.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

@Getter
public enum NameCard {

    A("As"),
    K("King"),
    Q("Queen"),
    J("Jota");

    private final String value;
    NameCard(String value) {
       this.value = value;
    }

    public static Optional<NameCard> fromRank(String rank) {
        return Arrays.stream(NameCard.values())
                .filter(cardValue -> cardValue.name().equals(rank))
                .findFirst();
    }
}
