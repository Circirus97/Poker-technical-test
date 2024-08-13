package com.poker.TexasHoldem.usecase;

import com.poker.TexasHoldem.model.WinningResult;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class PokerUserCaseTest {

    @InjectMocks
    private PokerUserCase pokerUserCase;
    private String hand;
    private WinningResult result;
    private String handTwo;

    @BeforeEach
    void setUp() {
        hand = "2H 3H 5S 5C 3D";
        handTwo = "2H 3H 8S AC JD";
        result = WinningResult.builder()
                .twoPair(Boolean.TRUE)
                .pair(Boolean.TRUE)
                .valueTwoPair("5,3")
                .valuePair("3")
                .build();
    }

    @Test
    void winnerHighCard() {
        val highestCard = pokerUserCase.winnerHighCard(hand, handTwo);
        assertEquals("hand2", highestCard);
    }

    @Test
    void getHighestCard() {
        val highestCardHandOne = pokerUserCase.validateHighestCard(hand);
        assertEquals("5S", highestCardHandOne.getValueHighCard());

    }

    @Test
    void getPair() {

        val value = pokerUserCase.validatePairOrTwoPair(hand, WinningResult.builder().build());
        assertEquals(result, value);

    }

    @Test
    void validateThreeCardsEquals(){
        hand = "3H 3H 5S 5C 3D";

        val winningResult = WinningResult.builder()
                .valueThreeOfAKind("3")
                .threeOfAKind(Boolean.TRUE)
                .build();

        val result = pokerUserCase.validateThreeCardsEquals(hand, WinningResult.builder().build());
        assertEquals(winningResult, result);
    }

    @Test
    void validateFlush(){
        hand = "2H 3H 5H 5H 3H";

        val winningResult = WinningResult.builder()
                .valueFlush("2H 3H 5H 5H 3H")
               .flush(Boolean.TRUE)
               .build();

        val result = pokerUserCase.validateFlush(hand, WinningResult.builder().build());
        assertEquals(winningResult, result);
    }

    @Test
    void validateFourCardsEquals(){
        hand = "3H 3H 3S 5C 3D";

        val winningResult = WinningResult.builder()
                .valueFourOfAKind("3")
                .fourOfAKind(Boolean.TRUE)
                .build();

        val result = pokerUserCase.validateFourCardsEquals(hand, WinningResult.builder().build());
        assertEquals(winningResult, result);
    }

    @Test
    void validateFullHouse(){
        hand = "3H 3H 3S 5C 5D";

        val winningResult = WinningResult.builder()
                .valueFullHouse("3 5")
                .fullHouse(Boolean.TRUE)
                .build();

        val result = pokerUserCase.validateFullHouse(hand, WinningResult.builder().build());
        assertEquals(winningResult, result);

    }

    @Test
    void validateStraight(){
        hand = "2H 3H 4S 5C 6D";

        val winningResult = WinningResult.builder()
                .valueStraight("2H 3H 4S 5C 6D")
                .straight(Boolean.TRUE)
                .build();

        val result = pokerUserCase.validateStraight(hand, WinningResult.builder().build());
        assertEquals(winningResult, result);

    }
}