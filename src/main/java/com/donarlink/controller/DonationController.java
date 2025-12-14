package com.donarlink.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/donation")
public class DonationController {

    @GetMapping("/donations")
    public String donations() {

        return "donations";
    }
}
