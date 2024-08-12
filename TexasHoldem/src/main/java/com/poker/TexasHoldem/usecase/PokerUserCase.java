package com.poker.TexasHoldem.usecase;

import com.poker.TexasHoldem.dto.request.PokerHandRequest;
import com.poker.TexasHoldem.dto.response.PokerHandResponse;
import com.poker.TexasHoldem.model.WinningResult;
import lombok.val;
import org.apache.logging.log4j.util.Strings;

import java.util.*;
import java.util.regex.Pattern;

import static org.apache.logging.log4j.util.Strings.isEmpty;


public class PokerUserCase {

    private static final String HAND_REGEX = "^([2-9]|10|A|J|Q|K)[CHSD]( ([2-9]|10|A|J|Q|K)[CHSD]){4}$";
    public static final String HAND_ONE = "hand1";
    public static final String HAND_TWO = "hand2";

    public PokerHandResponse createHands(PokerHandRequest request) {

        return null;
    }

    public Boolean validateHand(String hand) {
        return Pattern.matches(HAND_REGEX, hand);
    }

    private String extractRank(String card) {
        return card.length() == 3 ? card.substring(0, 2) : card.substring(0, 1);
    }


    private char extractSuit(String card) {

        return card.charAt(card.length() - 1);
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

     /*
    ----------------------------
      HIGH CARD
    ----------------------------
     */

    public String winnerHighCard(String handOne, String handTwo) {

        val highestCardHandOne = getHighestCard(handOne);
        val highestCardHandTwo = getHighestCard(handTwo);

        if (getCardValue(highestCardHandOne.getValueHighCard()) > getCardValue(highestCardHandTwo.getValueHighCard())) {
            return HAND_ONE;
        } else {
            return HAND_TWO;
        }
    }


    public List<String> separateCards(String hand) {
        String[] cards = hand.split(" ");

        return new ArrayList<>(Arrays.asList(cards));
    }


    public WinningResult getHighestCard(String hand) {

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
                .build();
    }

    /*
    ----------------------------
     PAIR OR TWO PAIR
    ----------------------------
     */

    public WinningResult getPairOrTwoPair(String hand, WinningResult winningResult) {
        List<String> cards = separateCards(hand);
        Map<String, Integer> rankCount = new HashMap<>();
        String pair = Strings.EMPTY;
        String twoPair = Strings.EMPTY;

        // Contar las apariciones de cada rango
        for (String card : cards) {
            String rank = extractRank(card);
            rankCount.put(rank, rankCount.getOrDefault(rank, 0) + 1);
        }

        // Identificar pares
        for (String card : cards.stream().map(s -> s.length() == 3 ? s.substring(0, 2) : s.substring(0, 1)).distinct().toList()) {
            String rank = extractRank(card);
            if (rankCount.get(rank) == 2) {
                if (pair.isEmpty()) {
                    pair = card;
                } else if (!twoPair.equals(card)) {
                    twoPair = card;
                    break; // Para de buscar si se encuentra un segundo par
                }
            }
        }

        return winningResult.toBuilder()
                .twoPair(!isEmpty(twoPair))
                .valueTwoPair(isEmpty(twoPair) ? Strings.EMPTY : String.format("%s,%s", twoPair, pair))
                .pair(!isEmpty(pair))
                .valuePair(isEmpty(pair) ? Strings.EMPTY : pair)
                .build();
    }

    /*
    ----------------------------
      THREE OF A KIND
    ----------------------------
     */

    public WinningResult validateThreeCardsEquals(String hand, WinningResult winningResult) {

        List<String> cards = separateCards(hand);
        Map<String, Integer> rankCount = new HashMap<>();

        // Contar las apariciones de cada rango
        for (String card : cards) {
            String rank = extractRank(card);
            rankCount.put(rank, rankCount.getOrDefault(rank, 0) + 1);
        }

        // Verificar si hay 3 cartas con el mismo rango y devolver el rango
        for (Map.Entry<String, Integer> entry : rankCount.entrySet()) {
            if (entry.getValue() == 3) {
                return winningResult.toBuilder()
                        .threeOfAKind(Boolean.TRUE)
                        .valueThreeOfAKind(entry.getKey())
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

    /*
    ----------------------------
      ONE PAIR
    ----------------------------
     */

    /*
    ----------------------------
      ONE PAIR
    ----------------------------
     */

    /*
    ----------------------------
      FLUSH
    ----------------------------
     */

    /*
    ----------------------------
      FULL HOUSE
    ----------------------------
     */
    /*
    ----------------------------
      FOUR OF A KIND (POKER)
    ----------------------------
     */
    /*
    ----------------------------
      ROYAL FLUSH
    ----------------------------
     */


}
