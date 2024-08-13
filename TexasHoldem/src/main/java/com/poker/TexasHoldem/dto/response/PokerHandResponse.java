package com.poker.TexasHoldem.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PokerHandResponse {

    private String winnerHand;
    private String winnerHandType;
    private List<String> compositionWinnerHand;
}
