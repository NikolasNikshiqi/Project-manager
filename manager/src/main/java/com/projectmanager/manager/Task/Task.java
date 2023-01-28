package com.projectmanager.manager.Task;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.projectmanager.manager.project.Project;
import com.projectmanager.manager.user.User;
import org.springframework.data.annotation.CreatedDate;
import javax.persistence.*;
import java.util.Date;

@Entity
public class Task {

    @Id
    @GeneratedValue
    private Long taskId;

    private String name;

    @JsonIgnore
    @ManyToOne
    private User user;

    @JsonIgnore
    @ManyToOne
    private Project project;

    @Enumerated(value = EnumType.STRING)
    @Column(updatable = true)
    private TaskStatus taskStatus;

    @CreatedDate
    @Column
    private Date startDate = new Date();

    @Column(updatable = true)
    private Date endDate;


    private Long timeSpent = 0L;

    public Long getId() {
        return taskId;
    }

    public void setId(Long id) {
        this.taskId = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public TaskStatus getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(TaskStatus taskStatus) {
        this.taskStatus = taskStatus;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Long getTimeSpent() {
        if (timeSpent > 0) {
            return this.timeSpent;
        }
        this.setTimeSpent((new Date().getTime() - getStartDate().getTime()) / 1000);
        return this.timeSpent;
    }

    public void setTimeSpent(Long timeSpent) {
        this.timeSpent = timeSpent;
    }
}
