package com.pms.service;

import com.pms.modal.Chat;
import com.pms.modal.Message;
import com.pms.modal.User;
import com.pms.repository.MessegeRepository;
import com.pms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessegeServiceImpl implements MessegeService{

    @Autowired
    private MessegeRepository messegeRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProjectService projectService;

    @Override
    public Message sendMessege(Long senderId, Long projectId, String content) throws Exception {
        User sender=userRepository.findById(senderId)
                .orElseThrow(()->new Exception("User not found with id:"+senderId));
        Chat chat =projectService.getProjectById(projectId).getChat();
        Message message=new Message();
        message.setContent(content);
        message.setSender(sender);
        message.setCreatedAt(LocalDateTime.now());
        message.setChat(chat);
        Message savedMassage=messegeRepository.save(message);
        chat.getMessages().add(savedMassage);
        return savedMassage;
    }

    @Override
    public List<Message> getMessegeByProjectId(Long projectId) throws Exception {
        Chat chat=projectService.getChatByProjectId(projectId);
        List<Message> findByChatIdOOrderByCreatedAtAsc=messegeRepository.findByChatIdOrderByCreatedAtAsc(chat.getId());
        return findByChatIdOOrderByCreatedAtAsc;
    }

    @Override
    public Message sendMessege(Long senderId) {
        return null;
    }
}
