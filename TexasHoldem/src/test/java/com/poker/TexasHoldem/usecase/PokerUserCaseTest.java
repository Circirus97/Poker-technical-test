package com.poker.TexasHoldem.usecase;

import com.poker.TexasHoldem.dto.request.PokerHandRequest;
import com.poker.TexasHoldem.dto.response.PokerHandResponse;
import com.poker.TexasHoldem.model.enums.WinEnum;
import com.poker.TexasHoldem.model.winning.WinningResult;
import lombok.val;
import org.apache.coyote.BadRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PokerUserCaseTest {

    @InjectMocks
    private PokerUserCase pokerUserCase;
    private String hand;
    private WinningResult result;

    @BeforeEach
    void setUp() {
        hand = "2H 3H 5S 5C 3D";
        result = WinningResult.builder()
                .twoPair(Boolean.TRUE)
                .pair(Boolean.TRUE)
                .valueTwoPair("5,3")
                .valuePair("3")
                .valueWinning(2)
                .build();
    }

    @Test
    void getHighestCard() {
        val highestCardHandOne = pokerUserCase.validateHighestCard(hand);
        assertEquals("5S", highestCardHandOne.getValueHighCard());

    }

    @Test
    void getHighestCardInvalid() {
        hand = "2H 3H 5S 5C 3D";

        val highestCardHandOne = pokerUserCase.validateHighestCard(hand);
        assertNotEquals("3D", highestCardHandOne.getValueHighCard());

    }

    @Test
    void getPair() {

        hand = "3H 8H 5S 5C 6D";

        val winningResult = WinningResult.builder()
                .valuePair("5")
                .pair(Boolean.TRUE)
                .valueWinning(2)
                .build();

        val result = pokerUserCase.validatePairOrTwoPair(hand, WinningResult.builder().build());
        assertEquals(winningResult, result);


    }

    @Test
    void getPairInvalid() {
        hand = "2H 3H 5S 6C 3D";

        val value = pokerUserCase.validatePairOrTwoPair(hand, WinningResult.builder().build());
        assertNotEquals(result, value);

    }

    @Test
    void getTwoPair() {

        hand = "3H 3H 5S 5C 6D";

        val winningResult = WinningResult.builder()
                .valueTwoPair("5 3")
                .twoPair(Boolean.TRUE)
                .valueWinning(3)
                .build();

        val result = pokerUserCase.validatePairOrTwoPair(hand, WinningResult.builder().build());
        assertEquals(winningResult, result);


    }

    @Test
    void validateThreeCardsEquals() {
        hand = "3H 3H 5S 5C 3D";

        val winningResult = WinningResult.builder()
                .valueThreeOfAKind("3")
                .threeOfAKind(Boolean.TRUE)
                .valueWinning(4)
                .build();

        val result = pokerUserCase.validateThreeCardsEquals(hand, WinningResult.builder().build());
        assertEquals(winningResult, result);
    }

    @Test
    void ThreeCardsInvalid() {
        hand = "2H 3H 5S 6C 3D";
        val expectedResult = WinningResult.builder().build();

        val result = pokerUserCase.validateThreeCardsEquals(hand, WinningResult.builder().build());
        assertEquals(expectedResult, result);

    }

    @Test
    void validateStraight() {
        hand = "2H 3H 4S 5C 6D";

        val winningResult = WinningResult.builder()
                .valueStraight("2H 3H 4S 5C 6D")
                .straight(Boolean.TRUE)
                .valueWinning(5)
                .build();

        val result = pokerUserCase.validateStraight(hand, WinningResult.builder().build());
        assertEquals(winningResult, result);

    }

    @Test
    void invalidateStraight() {
        hand = "AH 3H 7S 5C JD";

        val winningResult = WinningResult.builder()
                .valueStraight("2H 3H 4S 5C 6D")
                .straight(Boolean.TRUE)
                .build();

        val result = pokerUserCase.validateStraight(hand, WinningResult.builder().build());
        assertNotEquals(winningResult, result);

    }

    @Test
    void validateFlush() {
        hand = "2H 3H 5H 5H 3H";

        val winningResult = WinningResult.builder()
                .valueFlush("2H 3H 5H 5H 3H")
                .flush(Boolean.TRUE)
                .valueWinning(6)
                .build();

        val result = pokerUserCase.validateFlush(hand, WinningResult.builder().build());
        assertEquals(winningResult, result);
    }

    @Test
    void invalidateFlush() {
        hand = "2H 3H 5C 5H 3H";

        val winningResult = WinningResult.builder()
                .valueFlush("2H 3H 5H 5H 3H")
                .flush(Boolean.TRUE)
                .valueWinning(6)
                .build();

        val result = pokerUserCase.validateFlush(hand, WinningResult.builder().build());
        assertNotEquals(winningResult, result);
    }

    @Test
    void validateFullHouse() {
        hand = "3H 3H 3S 5C 5D";

        val winningResult = WinningResult.builder()
                .valueFullHouse("3 5")
                .fullHouse(Boolean.TRUE)
                .valueWinning(7)
                .build();

        val result = pokerUserCase.validateFullHouse(hand, WinningResult.builder().build());
        assertEquals(winningResult, result);

    }

    @Test
    void invalidateFullHouse() {
        hand = "3H 3D 7S 7C 5D";

        val winningResult = WinningResult.builder()
                .valueFullHouse("3 5")
                .fullHouse(Boolean.TRUE)
                .valueWinning(7)
                .build();

        val result = pokerUserCase.validateFullHouse(hand, WinningResult.builder().build());
        assertNotEquals(winningResult, result);

    }

    @Test
    void validateFourCardsEquals() {
        hand = "3H 3H 3S 5C 3D";

        val winningResult = WinningResult.builder()
                .valueFourOfAKind("3")
                .fourOfAKind(Boolean.TRUE)
                .valueWinning(8)
                .build();

        val result = pokerUserCase.validateFourCardsEquals(hand, WinningResult.builder().build());
        assertEquals(winningResult, result);
    }

    @Test
    void invalidateFourCardsEquals() {
        hand = "3H 3H 3S 5C 5D";

        val winningResult = WinningResult.builder()
                .valueFourOfAKind("3")
                .fourOfAKind(Boolean.TRUE)
                .valueWinning(8)
                .build();

        val result = pokerUserCase.validateFourCardsEquals(hand, WinningResult.builder().build());
        assertNotEquals(winningResult, result);
    }

    @Test
    void createHands() throws BadRequestException {

        val request = PokerHandRequest.builder()
                .hand1("2H 3D 5S 9C KD")
                .hand2("AH KD 9C 8C 7D")
                .build();


        val expectedResponse = PokerHandResponse.builder()
                .winnerHand("hand2")
                .winnerHandType("HighCard")
                .compositionWinnerHand(List.of("AH", "KD", "9C", "8C", "7D"))
                .build();


        val actualResponse = pokerUserCase.createHands(request);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void createHandsInvalidate() {

        val request = PokerHandRequest.builder()
                .hand1("2H 3D 5S 9C")
                .hand2("AH KD 9C 8C 7T")
                .build();


        assertThrows(BadRequestException.class, () ->
                pokerUserCase.createHands(request));

    }

    @Test
    void handValidationEnum() {

      val winningResult = WinningResult.builder()
              .build();

      assertEquals(WinEnum.HIGH_CARD, pokerUserCase.handValidationEnum(winningResult.toBuilder().valueWinning(1).build()));
      assertEquals(WinEnum.PAIR, pokerUserCase.handValidationEnum(winningResult.toBuilder().valueWinning(2).build()));
      assertEquals(WinEnum.TWO_PAIR, pokerUserCase.handValidationEnum(winningResult.toBuilder().valueWinning(3).build()));
      assertEquals(WinEnum.THREE_OF_A_KIND, pokerUserCase.handValidationEnum(winningResult.toBuilder().valueWinning(4).build()));
      assertEquals(WinEnum.STRAIGHT, pokerUserCase.handValidationEnum(winningResult.toBuilder().valueWinning(5).build()));
      assertEquals(WinEnum.FLUSH, pokerUserCase.handValidationEnum(winningResult.toBuilder().valueWinning(6).build()));
      assertEquals(WinEnum.FULL_HOUSE, pokerUserCase.handValidationEnum(winningResult.toBuilder().valueWinning(7).build()));
      assertEquals(WinEnum.FOUR_OF_A_KIND, pokerUserCase.handValidationEnum(winningResult.toBuilder().valueWinning(8).build()));
      assertEquals(WinEnum.HIGH_CARD, pokerUserCase.handValidationEnum(winningResult.toBuilder().valueWinning(10).build()));

    }

}