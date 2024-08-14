package com.poker.TexasHoldem.usecase;

import com.poker.TexasHoldem.dto.request.PokerHandRequest;
import com.poker.TexasHoldem.dto.response.PokerHandResponse;
import com.poker.TexasHoldem.model.enums.WinEnum;
import com.poker.TexasHoldem.model.winning.WinningResult;
import org.apache.coyote.BadRequestException;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.regex.Pattern;

import static org.apache.logging.log4j.util.Strings.isEmpty;

@Component
public class PokerUserCase {

    private static final String HAND_REGEX = "^([2-9]|10|A|J|Q|K)[CHSD]( ([2-9]|10|A|J|Q|K)[CHSD]){4}$";
    public static final String HAND_ONE = "hand1";
    public static final String HAND_TWO = "hand2";

    public PokerHandResponse createHands(PokerHandRequest request) throws BadRequestException {

        validateRequest(request);

        WinningResult winningResultHandOne = evaluateHand(request.getHand1());
        WinningResult winningResultHandTwo = evaluateHand(request.getHand2());

        return determineWinner(winningResultHandOne, winningResultHandTwo, request);
    }

    private void validateRequest(PokerHandRequest request) throws BadRequestException {
        if (isEmpty(request.getHand1()) || isEmpty(request.getHand2()) ||
                !validateHand(request.getHand1()) || !validateHand(request.getHand2())) {
            throw new BadRequestException("Invalid hands provided");
        }
    }

    public Boolean validateHand(String hand) {
        return Pattern.matches(HAND_REGEX, hand);
    }

    private WinningResult evaluateHand(String hand) {
        WinningResult winningResult = validateHighestCard(hand);
        winningResult = validatePairOrTwoPair(hand, winningResult);
        winningResult = validateThreeCardsEquals(hand, winningResult);
        winningResult = validateStraight(hand, winningResult);
        winningResult = validateFlush(hand, winningResult);
        winningResult = validateFullHouse(hand, winningResult);
        winningResult = validateFourCardsEquals(hand, winningResult);
        return winningResult;
    }

    private PokerHandResponse determineWinner(WinningResult handOneResult, WinningResult handTwoResult, PokerHandRequest request) {
        return handOneResult.getValueWinning() > handTwoResult.getValueWinning() ?
                buildResponse(HAND_ONE, handOneResult, request.getHand1()) :
                buildResponse(HAND_TWO, handTwoResult, request.getHand2());
    }

    private PokerHandResponse buildResponse(String winnerHand, WinningResult winningResult, String hand) {
        return PokerHandResponse.builder()
                .winnerHand(winnerHand)
                .winnerHandType(handValidationEnum(winningResult).getName())
                .compositionWinnerHand(separateCards(hand))
                .build();
    }

    private String extractRank(String card) {
        return card.length() == 3 ? card.substring(0, 2) : card.substring(0, 1);
    }

    private Integer getCardValue(String card) {
        String rank = extractRank(card);
        return switch (rank) {
            case "A" -> 14;
            case "K" -> 13;
            case "Q" -> 12;
            case "J" -> 11;
            case "10" -> 10;
            case "9" -> 9;
            case "8" -> 8;
            case "7" -> 7;
            case "6" -> 6;
            case "5" -> 5;
            case "4" -> 4;
            case "3" -> 3;
            case "2" -> 2;
            default -> 0;
        };
    }

    public int getSuitValue(char suit) {
        return switch (suit) {
            case 'S' -> 4;
            case 'H' -> 3;
            case 'D' -> 2;
            case 'C' -> 1;
            default -> 0;
        };
    }

    public List<String> separateCards(String hand) {
        String[] cards = hand.split(" ");

        return new ArrayList<>(Arrays.asList(cards));
    }

    private void countAppearancesOfEachRank(List<String> cards, Map<String, Integer> rankCount) {
        for (String card : cards) {
            String rank = extractRank(card);
            rankCount.put(rank, rankCount.getOrDefault(rank, 0) + 1);
        }
    }

     /*
    ----------------------------
      HIGH CARD
    ----------------------------
     */


    public WinningResult validateHighestCard(String hand) {

        List<String> cards = separateCards(hand);
        String highestCard = cards.getFirst();

        for (String card : cards) {
            Integer currentRankValue = getCardValue(extractRank(card));
            Integer highestRankValue = getCardValue(extractRank(highestCard));

            if (currentRankValue > highestRankValue) {
                highestCard = card;
            } else if (currentRankValue.equals(highestRankValue)) {

                char currentSuit = card.charAt(card.length() - 1);
                char highestSuit = highestCard.charAt(highestCard.length() - 1);
                if (getSuitValue(currentSuit) > getSuitValue(highestSuit)) {
                    highestCard = card;
                }
            }
        }
        return WinningResult.builder()
                .highCard(Boolean.TRUE)
                .valueHighCard(highestCard)
                .valueWinning(1)
                .build();
    }

    /*
    ----------------------------
     PAIR OR TWO PAIR
    ----------------------------
     */

    public WinningResult validatePairOrTwoPair(String hand, WinningResult winningResult) {
        List<String> cards = separateCards(hand);
        Map<String, Integer> rankCount = new HashMap<>();
        String pair = Strings.EMPTY;
        String twoPair = Strings.EMPTY;

        countAppearancesOfEachRank(cards, rankCount);

        for (String card : cards.stream().map(s -> s.length() == 3 ? s.substring(0, 2) : s.substring(0, 1)).distinct().toList()) {
            String rank = extractRank(card);
            if (rankCount.get(rank) == 2) {
                if (pair.isEmpty()) {
                    pair = card;
                } else if (!twoPair.equals(card)) {
                    twoPair = card;
                    break;
                }
            }
        }

        return !isEmpty(twoPair) ?
                winningResult.toBuilder()
                        .twoPair(Boolean.TRUE)
                        .valueTwoPair(String.format("%s %s", twoPair, pair))
                        .valueWinning(3)
                        .build() : !isEmpty(pair) ?
                winningResult.toBuilder()
                        .pair(Boolean.TRUE)
                        .valuePair(pair)
                        .valueWinning(2)
                        .build() : winningResult;
    }

    /*
    ----------------------------
      THREE OF A KIND
    ----------------------------
     */

    public WinningResult validateThreeCardsEquals(String hand, WinningResult winningResult) {

        List<String> cards = separateCards(hand);
        Map<String, Integer> rankCount = new HashMap<>();

        countAppearancesOfEachRank(cards, rankCount);

        for (Map.Entry<String, Integer> entry : rankCount.entrySet()) {
            if (entry.getValue() == 3) {
                return winningResult.toBuilder()
                        .threeOfAKind(Boolean.TRUE)
                        .valueThreeOfAKind(entry.getKey())
                        .valueWinning(4)
                        .build();
            }
        }
        return winningResult;
    }

    /*
    ----------------------------
      STRAIGHT
    ----------------------------
     */

    public WinningResult validateStraight(String hand, WinningResult winningResult) {

        List<String> cards = separateCards(hand);

        List<Integer> cardValues = cards.stream()
                .map(card -> getCardValue(extractRank(card)))
                .distinct()
                .sorted()
                .toList();

        for (int i = 0; i <= cardValues.size() - 5; i++) {
            boolean isStraight = true;
            for (int j = 0; j < 4; j++) {
                if (cardValues.get(i + j) + 1 != cardValues.get(i + j + 1)) {
                    isStraight = false;
                    break;
                }
            }
            if (isStraight) {
                return winningResult.toBuilder()
                        .straight(Boolean.TRUE)
                        .valueWinning(5)
                        .valueStraight(hand)
                        .build();
            }
        }

        if (cardValues.contains(14) && cardValues.get(0) == 2 && cardValues.get(1) == 3
                && cardValues.get(2) == 4 && cardValues.get(3) == 5) {
            return winningResult.toBuilder()
                    .straight(Boolean.TRUE)
                    .valueStraight(hand)
                    .valueWinning(5)
                    .build();
        }

        return winningResult;
    }

    /*
    ----------------------------
      FLUSH
    ----------------------------
     */

    public WinningResult validateFlush(String hand, WinningResult winningResult) {
        List<String> cards = separateCards(hand);
        Map<Character, Integer> suitCount = new HashMap<>();


        for (String card : cards) {
            char suit = card.charAt(card.length() - 1);
            suitCount.put(suit, suitCount.getOrDefault(suit, 0) + 1);
        }

        return suitCount.values().stream().allMatch(i -> i == 5)
                ? winningResult.toBuilder()
                .flush(Boolean.TRUE)
                .valueFlush(hand)
                .valueWinning(6)
                .build()
                : winningResult;
    }

    /*
    ----------------------------
      FULL HOUSE
    ----------------------------
     */

    public WinningResult validateFullHouse(String hand, WinningResult winningResult) {
        List<String> cards = separateCards(hand);
        Map<String, Integer> rankCount = new HashMap<>();
        String threeOfAKind = Strings.EMPTY;
        String pair = Strings.EMPTY;

        countAppearancesOfEachRank(cards, rankCount);

        for (Map.Entry<String, Integer> entry : rankCount.entrySet()) {
            if (entry.getValue() == 3) {
                threeOfAKind = entry.getKey();
            } else if (entry.getValue() == 2 && !Objects.equals(threeOfAKind, entry.getKey())) {
                pair = entry.getKey();
            }
        }

        if (!threeOfAKind.isEmpty() && !pair.isEmpty()) {
            return winningResult.toBuilder()
                    .fullHouse(Boolean.TRUE)
                    .valueFullHouse(String.format("%s %s", threeOfAKind, pair))
                    .valueWinning(7)
                    .build();
        }

        return winningResult;
    }

    /*
    ----------------------------
      FOUR OF A KIND (POKER)
    ----------------------------
     */
    public WinningResult validateFourCardsEquals(String hand, WinningResult winningResult) {

        List<String> cards = separateCards(hand);
        Map<String, Integer> rankCount = new HashMap<>();

        countAppearancesOfEachRank(cards, rankCount);

        for (Map.Entry<String, Integer> entry : rankCount.entrySet()) {
            if (entry.getValue() == 4) {
                return winningResult.toBuilder()
                        .fourOfAKind(Boolean.TRUE)
                        .valueFourOfAKind(entry.getKey())
                        .valueWinning(8)
                        .build();
            }
        }
        return winningResult;
    }

    /*
    ----------------------------
     VALIDATE THE WINNING HAND
    ----------------------------
     */

    public WinEnum handValidationEnum(WinningResult winningResult) {

        return switch (winningResult.getValueWinning()) {
            case 1 -> WinEnum.HIGH_CARD;
            case 2 -> WinEnum.PAIR;
            case 3 -> WinEnum.TWO_PAIR;
            case 4 -> WinEnum.THREE_OF_A_KIND;
            case 5 -> WinEnum.STRAIGHT;
            case 6 -> WinEnum.FLUSH;
            case 7 -> WinEnum.FULL_HOUSE;
            case 8 -> WinEnum.FOUR_OF_A_KIND;
            default -> WinEnum.HIGH_CARD;
        };
    }

}
