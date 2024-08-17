package com.pms.service;

import com.pms.modal.Message;

import java.util.List;

public interface MessegeService {
    Message sendMessege(Long senderId,Long projectId,String content)throws Exception;
    List<Message> getMessegeByProjectId(Long projectId)throws Exception;

    Message sendMessege(Long senderId);
}
