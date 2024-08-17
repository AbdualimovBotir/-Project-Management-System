package com.pms.controller;

import com.pms.modal.Chat;
import com.pms.modal.Invitation;
import com.pms.modal.Project;
import com.pms.modal.User;
import com.pms.request.InviteRequest;
import com.pms.response.MessageResponse;
import com.pms.service.InvitationService;
import com.pms.service.ProjectService;
import com.pms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {
    @Autowired
    private ProjectService projectService;
    @Autowired
    private UserService userService;
    @Autowired
    private InvitationService invitationService;

    @GetMapping
    public ResponseEntity<List<Project>> getProjects(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String tag,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        List<Project> projects = projectService.getProjectByTeam(user, category, tag);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<Project> getProjectById(
            @PathVariable Long projectId,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        Project project = projectService.getProjectById(projectId);
        return new ResponseEntity<>(project, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Project> createProject(
            @RequestBody Project project,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        Project createdProject = projectService.createProject(project, user);
        return new ResponseEntity<>(createdProject, HttpStatus.CREATED);
    }

    @PatchMapping("/{projectId}")
    public ResponseEntity<Project> updateProject(
            @PathVariable Long projectId,
            @RequestHeader("Authorization") String jwt,
            @RequestBody Project project
    ) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        Project updatedProject = projectService.updateProject(project, projectId);
        return new ResponseEntity<>(updatedProject, HttpStatus.OK);
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<MessageResponse> deleteProject(
            @PathVariable Long projectId,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        projectService.deleteProject(projectId, user.getId());
        MessageResponse res = new MessageResponse("Project deleted successfully");
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Project>> searchProjects(
            @RequestParam(required = false) String keyword,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        List<Project> projects = projectService.searchProjects(keyword, user);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @GetMapping("/{projectId}/chat")
    public ResponseEntity<Chat> getChatByProjectId(
            @PathVariable Long projectId,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        Chat chat = projectService.getChatByProjectId(projectId);
        return new ResponseEntity<>(chat, HttpStatus.OK);
    }

    @PostMapping("/invite")
    public ResponseEntity<MessageResponse> inviteProject(
            @RequestBody InviteRequest req,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        invitationService.sendInvitation(req.getEmail(), req.getProjectId());
        MessageResponse res = new MessageResponse("User invitation sent");
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PostMapping("/accept_invitation")
    public ResponseEntity<Invitation> acceptInvitation(
            @RequestParam String token,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        Invitation invitation = invitationService.accseptInvitation(token, user.getId());
        projectService.addUserToProject(invitation.getProjectId(), user.getId());
        return new ResponseEntity<>(invitation, HttpStatus.ACCEPTED);
    }
}


//@RestController
//@RequestMapping("/api/projects")
//public class ProjectController {
//    @Autowired
//    private ProjectService projectService;
//    @Autowired
//    private UserService userService;
//    @Autowired
//    private InvitationService invitationService;
//    @GetMapping
//    public ResponseEntity<List<Project>>getProjects(
//            @RequestParam(required = false)String category,
//            @RequestParam(required = false)String tag,
//            @RequestHeader("Authorization")String jwt
//    ) throws Exception {
//        User user=userService.findUserProfileByJwt(jwt);
//        List<Project>projects=projectService.getProjectByTeam(user,category,tag);
//        return new ResponseEntity<>(projects, HttpStatus.OK);
//    }
//
//    @GetMapping("/{projectId}")
//    public ResponseEntity<Project>getProjectId(
//            @PathVariable Long projectId,
//            @RequestHeader("Authorization")String jwt
//    ) throws Exception {
//        User user=userService.findUserProfileByJwt(jwt);
//        Project projects=projectService.getProjectById(projectId);
//        return new ResponseEntity<>(projects, HttpStatus.OK);
//    }
//
//    @PostMapping
//    public ResponseEntity<Project>createProject(
//            @PathVariable Long projectId,
//            @RequestHeader("Authorization")String jwt,
//            @RequestBody Project project
//    ) throws Exception {
//        User user=userService.findUserProfileByJwt(jwt);
//        Project createdprojects=projectService.createProject(project,user);
//        return new ResponseEntity<>(createdprojects, HttpStatus.OK);
//    }
//
//    @PatchMapping("/{projectId}")
//    public ResponseEntity<Project>updateProject(
//            @PathVariable Long projectId,
//            @RequestHeader("Authorization")String jwt,
//            @RequestBody Project project
//    ) throws Exception {
//        User user=userService.findUserProfileByJwt(jwt);
//        Project updatedprojects=projectService.updateProject(project,projectId);
//        return new ResponseEntity<>(updatedprojects, HttpStatus.OK);
//    }
//
//    @DeleteMapping("/{projectId}")
//    public ResponseEntity<MessageResponse>deleteProject(
//            @PathVariable Long projectId,
//            @RequestHeader("Authorization")String jwt
//    ) throws Exception {
//        User user=userService.findUserProfileByJwt(jwt);
//        projectService.deleteProject(projectId,user.getId());
//        MessageResponse res=new MessageResponse("project deleted successfuly");
//        return new ResponseEntity<>(res, HttpStatus.OK);
//    }
//
//    @GetMapping("/search")
//    public ResponseEntity<List<Project>>searchProjects(
//            @RequestParam(required = false)String keyword,
//            @RequestHeader("Authorization")String jwt
//    ) throws Exception {
//        User user=userService.findUserProfileByJwt(jwt);
//        List<Project>projects=projectService.searchProjects(keyword,user);
//        return new ResponseEntity<>(projects, HttpStatus.OK);
//    }
//
//    @GetMapping("/{projectId}/chat")
//    public ResponseEntity<Chat>getChatByProjectId(
//            @PathVariable Long projectId,
//            @RequestHeader("Authorization")String jwt
//    ) throws Exception {
//        User user=userService.findUserProfileByJwt(jwt);
//        Chat chat=projectService.getChatByProjectId(projectId);
//        return new ResponseEntity<>(chat, HttpStatus.OK);
//    }
//
//
//    @PostMapping("/invite")
//    public ResponseEntity<MessageResponse>invateProject(
//            @RequestBody InviteRequest req,
//            @RequestHeader("Authorization")String jwt,
//            @RequestBody Project project
//    ) throws Exception {
//        User user=userService.findUserProfileByJwt(jwt);
//        Project createdprojects=projectService.createProject(project,user);
//        invitationService.sendInvitation(req.getEmail(), req.getProjectId());
//        MessageResponse res=new MessageResponse("User invitation sent");
//        return new ResponseEntity<>(res, HttpStatus.OK);
//    }
//
//    @PostMapping("/accept_invitation")
//    public ResponseEntity<Invitation>acceptInvateProject(
//            @RequestParam String token,
//            @RequestHeader("Authorization")String jwt,
//            @RequestBody Project project
//    ) throws Exception {
//        User user=userService.findUserProfileByJwt(jwt);
//        Invitation invitation=invitationService.accseptInvitation(token,user.getId());
//        projectService.addUserToProject(invitation.getProjectId(),user.getId());
//        return new ResponseEntity<>(invitation, HttpStatus.ACCEPTED);
//    }
//
//}
