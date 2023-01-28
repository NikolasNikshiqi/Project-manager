package com.projectmanager.manager.project;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.projectmanager.manager.Task.Task;
import com.projectmanager.manager.user.User;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Project {

    @Id
    @GeneratedValue
    Long projectId;

    @Column(unique = true)
    String name;

    @ManyToMany
    @JoinColumn(
            name = "users_in_project"
    )
    private Set<User> usersInProject = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "project",cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<Task> tasks = new HashSet<>();

    private long timeSpent;

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<User> getUsersInProject() {
        return usersInProject;
    }

    public void addUserToProject(User user) {
        usersInProject.add(user);
    }

    public void addTaskToProject(Task task){
        tasks.add(task);
    }

    public void deleteUserFromProject(User user) {
        usersInProject.remove(user);
    }

    public Set<Task> getTasks() {
        return tasks;
    }

    public long getTimeSpent() {
        return timeSpent;
    }

    public void setTimeSpent(long timeSpent) {
        this.timeSpent = timeSpent;
    }
}
