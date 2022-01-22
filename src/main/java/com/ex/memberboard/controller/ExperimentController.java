package com.ex.memberboard.controller;

import com.ex.memberboard.service.ExperimentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/experiment/*")
public class ExperimentController {

    private final ExperimentService es;

    @GetMapping("experiment")
    public String experiment() {
        return "experiment/experiment";
    }

    @GetMapping("kakaoPay")
    public String kakaoPay() {
        return "experiment/kakaoPay";
    }

    @GetMapping("kakaoLogin")
    public String kakaoLogin(@RequestParam(value = "code", required = false) String code, Model model) throws Exception{
        System.out.println("#########" + code);
        return "experiment/experiment";
    }

}
