package com.green.glampick.service.implement;

import com.green.glampick.mapper.ManagerMapper;
import com.green.glampick.service.ManagerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ManagerServiceImpl implements ManagerService {
    private final ManagerMapper mapper;


}
