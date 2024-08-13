package com.poker.TexasHoldem.controller;

import com.poker.TexasHoldem.dto.request.PokerHandRequest;
import com.poker.TexasHoldem.dto.response.PokerHandResponse;
import com.poker.TexasHoldem.usecase.PokerUserCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/poker/validation")
public class PokerController {

    private final PokerUserCase pokerCaseUse;

    @PostMapping
    public PokerHandResponse createHands(@RequestBody PokerHandRequest request) {
        return pokerCaseUse.createHands(request);
    }


}
