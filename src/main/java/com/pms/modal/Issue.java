package com.pms.modal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Issue {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "projects_id")
    private Long projectsId;
    private String title;
    private String description;
    private String status;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    private String priority;
    private LocalDate dueDate;

    @ElementCollection
    @CollectionTable(name = "issue_tags", joinColumns = @JoinColumn(name = "issue_id"))
    @Column(name = "tag")
    private List<String> tags = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "assignee_id")
    private User assignee;

    @JsonIgnore
    @OneToMany(mappedBy = "issue", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

}

