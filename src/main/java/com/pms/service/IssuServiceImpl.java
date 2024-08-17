package com.pms.service;

import com.pms.modal.Issue;
import com.pms.modal.Project;
import com.pms.modal.User;
import com.pms.repository.IssueRepository;
import com.pms.request.IssueRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IssuServiceImpl implements IssuService{
    @Autowired
    private IssueRepository issueRepository;

    @Autowired
    private ProjectService projectService;
    @Autowired UserService userService;
    @Override
    public Issue getIssuById(Long issueId) throws Exception {
        Optional<Issue>issue=issueRepository.findById(issueId);
        if(issue.isPresent()){
            return issue.get();
        }
        throw new Exception("No issue found with issueid"+issueId);
    }

    @Override
    public List<Issue> getIssueByProjectId(Long projectId) throws Exception {
        return issueRepository.findByProjectId(projectId);
    }

    @Override
    public Issue createIssue(IssueRequest issueRequest, User user) throws Exception {
        Project project=projectService.getProjectById(issueRequest.getProjectId());
        Issue issue1=new Issue();
        issue1.setTitle(issueRequest.getTitle());
        issue1.setDescription(issueRequest.getDescription());
        issue1.setStatus(issueRequest.getStatus());
        issue1.setProjectsId(issueRequest.getProjectId());
        issue1.setPriority(issueRequest.getPriority());
        issue1.setDueDate(issueRequest.getDueDate());

        issue1.setProject(project);

        return issueRepository.save(issue1);
    }

    @Override
    public void deleteIssue(Long issueId, Long userId) throws Exception {
        getIssuById(issueId);
        issueRepository.deleteById(issueId);
    }

    @Override
    public Issue addUserToIssue(Long issueId, Long userId) throws Exception {
        User user=userService.findUserById(userId);
        Issue issue=getIssuById(issueId);
        issue.setAssignee(user);
        return issueRepository.save(issue);
    }



    @Override
    public Issue updateStatus(Long issueId, String status) throws Exception {
        Issue issue=getIssuById(issueId);
        issue.setStatus(status);
        return issueRepository.save(issue);
    }
}
