package com.poker.TexasHoldem.model;

import lombok.Getter;

@Getter
public enum WinEnum {

    HIGH_CARD("HighCard"),
    PAIR("OnePair"),
    TWO_PAIR("TwoPair"),
    THREE_OF_A_KIND("ThreeOfAKind"),
    STRAIGHT("Straight"),
    FLUSH("Flush"),
    FULL_HOUSE("FullHouse"),
    FOUR_OF_A_KIND("FourOfAKind");

    private final String name;

    WinEnum(String name) {
        this.name = name;
    }
}
