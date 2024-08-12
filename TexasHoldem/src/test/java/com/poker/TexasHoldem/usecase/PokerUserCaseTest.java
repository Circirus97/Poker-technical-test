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
    void getPair() {

        val value = pokerUserCase.getPairOrTwoPair(hand, WinningResult.builder().build());
        assertEquals(result, value);

    }

    @Test
    void winnerHighCard() {
        val highestCard = pokerUserCase.winnerHighCard(hand, handTwo);
        assertEquals("hand2", highestCard);
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
}