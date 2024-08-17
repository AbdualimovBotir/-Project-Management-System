package com.pms.service;

import com.pms.modal.Issue;
import com.pms.modal.User;
import com.pms.request.IssueRequest;

import java.util.List;
import java.util.Optional;

public interface IssuService {
    Issue getIssuById(Long issueId)throws Exception;
    List<Issue> getIssueByProjectId(Long projectId) throws Exception;
    Issue createIssue(IssueRequest issue, User user)throws Exception;
    void deleteIssue(Long issueId,Long userId)throws Exception;
    Issue addUserToIssue(Long issueId,Long userId)throws Exception;
    Issue updateStatus(Long issueId, String status) throws Exception;
}
