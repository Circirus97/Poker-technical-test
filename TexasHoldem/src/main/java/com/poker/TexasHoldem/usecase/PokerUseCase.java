package com.poker.TexasHoldem.usecase;

import com.poker.TexasHoldem.dto.request.PokerHandRequest;
import com.poker.TexasHoldem.dto.response.PokerHandResponse;

import java.util.regex.Pattern;

public class PokerUseCase {
    private static final String HAND_REGEX = "^([2-9]|10|A|J|Q|K)[CHSD]( ([2-9]|10|A|J|Q|K)[CHSD]){4}$";

    public PokerHandResponse createHands(PokerHandRequest request){

        return null;
    }

    public Boolean validateHand(String hand){

        return Pattern.matches(HAND_REGEX, hand);
    }

    private String extractRank(String card) {
        /*
            Para cartas con una longitud de 3 caracteres: Se extraen los primeros 2 caracteres usando substring(0, 2) para obtener "10" en el caso de "10D".
            Para cartas con una longitud de 2 caracteres: Se extrae solo el primer carácter usando substring(0, 1) para obtener "A" en el caso de "AH".
        */
        return card.length() == 3 ? card.substring(0, 2) : card.substring(0, 1);
    }


    private char extractSuit(String card) {
        /*
            Usando card.charAt(card.length() - 1), se obtiene este último carácter.
            Aquí, card.charAt(card.length() - 1) devuelve el carácter en la última posición de la cadena card, que es el palo.
        */
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

    public String winnerHighCard(String hand1, String hand2) {

        String highestCardHand1 = getHighestCard(hand1);
        String highestCardHand2 = getHighestCard(hand2);

        Integer value1 = getCardValue(highestCardHand1);
        Integer value2 = getCardValue(highestCardHand2);

        if (value1 > value2) {
            return "hand1";
        } else if (value1 < value2) {
            return "hand2";
        } else {
            return "draw";
        }
    }


    public String findCard(String hand){
        String[] cards = hand.split(" ");
        String highestCard = cards[0];

        for (String card : cards) {
            Integer currentRankValue = getCardValue(extractRank(card));
            Integer highestRankValue = getCardValue(extractRank(highestCard));
        }

        return highestCard;
    }

    public String getHighestCard(String hand) {
        String[] cards = hand.split(" ");
        String highestCard = cards[0];

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
        return highestCard;
    }

    /*
    ----------------------------
      ONE PAIR
    ----------------------------
     */

    public String getOnePair(String hand) {
        String[] cards = hand.split(" ");
        String highestCard = cards[0];





    }









    /*
    ----------------------------
      TWO PAIR
    ----------------------------
     */

    /*
    ----------------------------
      THREE OF A KIND
    ----------------------------
     */

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
