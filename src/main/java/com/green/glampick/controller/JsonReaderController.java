package com.green.glampick.controller;

import com.green.glampick.common.jsonFileReader.JsonFileReader;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/json")
@RequiredArgsConstructor
public class JsonReaderController {
    private final JsonFileReader jsonFileReader;

    @GetMapping
    public void jsonDataInsert() {
        jsonFileReader.insert();
    }
}
