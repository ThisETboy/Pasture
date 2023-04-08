package com.example.pasture.service.impl;


import com.example.pasture.service.CardValidationService;
import org.springframework.stereotype.Service;

/**
 * @author aaron
 * @since 2021-02-23
 */
@Service
public class CardValidationServiceImpl implements CardValidationService {

    @Override
    public boolean cardCheck(String deviceIp, String icCardNumber) {
        return false;
    }
}
