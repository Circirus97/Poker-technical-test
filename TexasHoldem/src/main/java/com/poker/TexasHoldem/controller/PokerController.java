package com.poker.TexasHoldem.controller;


import com.poker.TexasHoldem.dto.request.PokerHandRequest;
import com.poker.TexasHoldem.dto.response.PokerHandResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/poker")
@AllArgsConstructor
public class PokerController {

    //private PokerService pokerService;

    @PostMapping
    public PokerHandResponse createHands(@RequestBody PokerHandRequest cards){
        return pokerService.createHand(cards);
    }


}
