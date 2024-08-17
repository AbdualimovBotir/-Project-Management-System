package com.pms.service;

import com.pms.modal.Invitation;
import jakarta.mail.MessagingException;


public interface InvitationService {
    public void sendInvitation(String email,Long projectId) throws MessagingException, MessagingException;
    public Invitation accseptInvitation(String token,Long userId) throws Exception;
    public String getTokenByUserMail(String userEmail);
    void deleteToken(String token);



}
