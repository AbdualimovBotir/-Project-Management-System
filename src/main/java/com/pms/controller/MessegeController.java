package com.pms.controller;

import com.pms.modal.Chat;
import com.pms.modal.Message;
import com.pms.modal.User;
import com.pms.request.CreateMessegeRequest;
import com.pms.service.MessegeService;
import com.pms.service.ProjectService;
import com.pms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessegeController {
    @Autowired
    private UserService userService;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private MessegeService messegeService;
    @PostMapping("/send")
    public ResponseEntity<Message> sendMessage(@RequestBody CreateMessegeRequest request)
    throws Exception{
        User user=userService.findUserById(request.getSenderId());
//        if(user==null)throw new Exception("user not found with id:"+request.getSenderId());
        Chat chats=projectService.getChatByProjectId(request.getProjectId()).getProject().getChat();
        if(chats==null)throw new Exception("chats not found");
        Message sentMessage=messegeService.sendMessege(
                request.getSenderId(),
                request.getProjectId(),
                request.getContent());
        return ResponseEntity.ok(sentMessage);
    }

    @GetMapping("/chat/{projectId}")
    public ResponseEntity<List<Message>> getMessagesByChatId(@PathVariable Long projectId)
            throws Exception{
        List<Message> messages=messegeService.getMessegeByProjectId(projectId);
        return ResponseEntity.ok(messages);
    }

}
