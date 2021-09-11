package com.github.SergeyVasilev87.first.service;

import com.github.SergeyVasilev87.first.entity.User;
import org.springframework.stereotype.Service;

@Service
public class ValidationService {

    public boolean passwordSimilarityCheck(User user) {
        if (!user.getPassword().equals(user.getPasswordConfirm())) {
            return false;
        }
        return true;
    }
}
