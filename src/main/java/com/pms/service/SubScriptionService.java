package com.pms.service;

import com.pms.modal.PlanType;
import com.pms.modal.SubScription;
import com.pms.modal.User;

public interface SubScriptionService {
    SubScription createSubscription(User user);
    SubScription getUserSubscription(Long userId)throws Exception;

    SubScription upgradeSubscription(Long userId, PlanType planType);

    boolean isValid(SubScription subScription);
}
