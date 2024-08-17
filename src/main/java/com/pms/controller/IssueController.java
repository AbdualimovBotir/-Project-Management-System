package com.pms.controller;

import com.pms.DTO.IssueDTO;
import com.pms.modal.Issue;
import com.pms.modal.User;
import com.pms.request.IssueRequest;
import com.pms.response.MessageResponse;
import com.pms.service.IssuService;
import com.pms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/issues")
public class IssueController {

    @Autowired
    private IssuService issuService;

    @Autowired
    private UserService userService;

    @GetMapping("/{issueId}")
    public ResponseEntity<Issue> getIssueById(@PathVariable Long issueId) throws Exception {
        return ResponseEntity.ok(issuService.getIssuById(issueId));
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<Issue>> getIssueByProjectId(@PathVariable Long projectId) throws Exception {
        return ResponseEntity.ok(issuService.getIssueByProjectId(projectId));
    }

    @PostMapping
    public ResponseEntity<IssueDTO> createIssue(@RequestBody IssueRequest issue,
                                                @RequestHeader("Authorization") String token) throws Exception {
        User tokenUser = userService.findUserProfileByJwt(token);
        Issue createdIssue = issuService.createIssue(issue, tokenUser);

        IssueDTO issueDTO = new IssueDTO();
        issueDTO.setDescription(createdIssue.getDescription());
        issueDTO.setDueDate(createdIssue.getDueDate());
        issueDTO.setId(createdIssue.getId());
        issueDTO.setPriority(createdIssue.getPriority());
        issueDTO.setProject(createdIssue.getProject());
        ///shu joyda project
        issueDTO.setProjectId(createdIssue.getProjectsId());
        issueDTO.setStatus(createdIssue.getStatus());
        issueDTO.setTitle(createdIssue.getTitle());
        issueDTO.setTags(createdIssue.getTags());
        issueDTO.setAssignee(createdIssue.getAssignee());

        return ResponseEntity.ok(issueDTO);
    }

    @DeleteMapping("/{issueId}")
    public ResponseEntity<MessageResponse> deleteIssue(
            @PathVariable Long issueId,
            @RequestHeader("Authorization") String token) throws Exception {
        User user = userService.findUserProfileByJwt(token);
        issuService.deleteIssue(issueId, user.getId());

        MessageResponse res = new MessageResponse();
        res.setMessage("Issue deleted");

        return ResponseEntity.ok(res);
    }

    @PostMapping("/{issueId}/assignee/{userId}")
    public ResponseEntity<Issue> addUserToIssue(
            @PathVariable Long issueId,
            @PathVariable Long userId) throws Exception {
        Issue issue = issuService.addUserToIssue(issueId, userId);
        return ResponseEntity.ok(issue);
    }

    @PatchMapping("/{issueId}/status/{status}")
    public ResponseEntity<Issue> updateIssueStatus(
            @PathVariable Long issueId,
            @PathVariable String status) throws Exception {
        Issue issue = issuService.updateStatus(issueId, status);
        return ResponseEntity.ok(issue);
    }
}
