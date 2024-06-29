package com.green.glampick.controller;

import com.green.glampick.service.GlampingService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/glamping")
@Tag(name = "글램핑 컨트롤러")
public class GlampingController {
    private final GlampingService service;


}
