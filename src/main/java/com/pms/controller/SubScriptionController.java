package com.pms.controller;

import com.pms.modal.PlanType;
import com.pms.modal.SubScription;
import com.pms.modal.User;
import com.pms.service.SubScriptionService;
import com.pms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/subscription")
public class SubScriptionController {
    @Autowired
    private SubScriptionService subScriptionService;
    @Autowired
    private UserService userService;

    @GetMapping("/user")
    public ResponseEntity<SubScription>getUserSubScription(
            @RequestHeader("Authorization") String jwt)throws Exception{
        User user=userService.findUserProfileByJwt(jwt);
         SubScription subScription=subScriptionService.getUserSubscription(user.getId());
         return new ResponseEntity<>(subScription, HttpStatus.OK);
    }

    @PostMapping("/upgrade")
    public ResponseEntity<SubScription>upgradeUserSubScription(
            @RequestHeader("Authorization") String jwt,
            @RequestParam PlanType planType)throws Exception{
        User user=userService.findUserProfileByJwt(jwt);

        SubScription subScription=subScriptionService.upgradeSubscription(user.getId(),planType);
        return new ResponseEntity<>(subScription, HttpStatus.OK);
    }

}
