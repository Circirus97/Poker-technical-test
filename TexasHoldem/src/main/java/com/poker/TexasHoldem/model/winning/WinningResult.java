package com.poker.TexasHoldem.model.winning;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder(toBuilder = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WinningResult {

    private Boolean highCard;
    private String valueHighCard;

    private Boolean pair;
    private String valuePair;

    private Boolean twoPair;
    private String valueTwoPair;

    private Boolean threeOfAKind;
    private String valueThreeOfAKind;

    private Boolean straight;
    private String valueStraight;

    private Boolean flush;
    private String valueFlush;

    private Boolean fullHouse;
    private String valueFullHouse;

    private Boolean fourOfAKind;
    private String valueFourOfAKind;

    private Integer valueWinning;
}
