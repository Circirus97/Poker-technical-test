package com.poker.TexasHoldem.controller;

import com.poker.TexasHoldem.dto.request.PokerHandRequest;
import com.poker.TexasHoldem.dto.response.PokerHandResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/poker")
public class PokerController {

    private PokerCaseUse pokerCaseUse;

    @PostMapping
    public PokerHandResponse createHands(@RequestBody PokerHandRequest request) {
        return pokerCaseUse.createHands(request);
    }
}
