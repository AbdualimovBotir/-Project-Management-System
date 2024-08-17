package com.pms.service;

import com.pms.modal.PlanType;
import com.pms.modal.SubScription;
import com.pms.modal.User;
import com.pms.repository.SubScriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class SubScriptionServiceImpl implements SubScriptionService {

    @Autowired
    private UserService userService;
    @Autowired
    private SubScriptionRepository subScriptionRepository;


    @Override
    public SubScription createSubscription(User user) {
        SubScription subScription=new SubScription();
        subScription.setUser(user);
        subScription.setSubscriptionStartDate(LocalDate.now());
        subScription.setGetSubscriptionEndDate(LocalDate.now().plusMonths(12));
        subScription.setValid(true);
        subScription.setPlanType(PlanType.FREE);
        return subScriptionRepository.save(subScription);
    }

    @Override
    public SubScription getUserSubscription(Long userId) throws Exception {
        SubScription subScription = subScriptionRepository.findByUserId(userId);
        if(!isValid(subScription)){
            subScription.setPlanType(PlanType.FREE);
            subScription.setGetSubscriptionEndDate(LocalDate.now().plusMonths(12));
            subScription.setSubscriptionStartDate(LocalDate.now());
        }
        return subScriptionRepository.save(subScription);
    }

    @Override
    public SubScription upgradeSubscription(Long userId, PlanType planType) {
        SubScription subScription=subScriptionRepository.findByUserId(userId);
        subScription.setPlanType(planType);
        subScription.setSubscriptionStartDate(LocalDate.now());
        if(planType.equals(PlanType.ANNULLY))
        {
            subScription.setGetSubscriptionEndDate(LocalDate.now().plusMonths(12));
        }
        else {
            subScription.setGetSubscriptionEndDate(LocalDate.now().plusMonths(1));
        }

        return subScriptionRepository.save(subScription);
    }

    @Override
    public boolean isValid(SubScription subScription) {
        if(subScription.getPlanType().equals(PlanType.FREE)){
            return true;
        }
        LocalDate endDate=subScription.getGetSubscriptionEndDate();
        LocalDate currentDate=LocalDate.now();

        return endDate.isAfter(currentDate)||endDate.isEqual(currentDate);
    }
}
