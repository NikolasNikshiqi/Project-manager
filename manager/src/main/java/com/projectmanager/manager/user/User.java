package com.projectmanager.manager.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.projectmanager.manager.Task.Task;
import com.projectmanager.manager.project.Project;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

@Entity
public class User implements UserDetails {

    @Id
    @GeneratedValue
    private Long user_id;

    private String name;
    private String password;

    @Enumerated(value = EnumType.STRING)
    private UserRole userRole;

    @JsonIgnore
    @ManyToMany(mappedBy = "usersInProject")
    private Set<Project> projects = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Task> tasks = new HashSet<>();

    public Long getId() {
        return user_id;
    }

    public void setId(Long id) {
        this.user_id = id;
    }

    public Set<Project> getProjects() {
        return projects;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public Set<Task> getTasks() {
        return tasks;
    }

    public void addTasksToUser(Task task) {
        tasks.add(task);
    }

    public void addAllTasksToUser(List<Task> allTasks) {
        tasks.addAll(allTasks);
    }

    public void deleteTaskFromUser(Task task) {
        tasks.remove(task);
    }

    public void deleteAllTasksFromUser(List<Task> allTasks) {
        tasks.removeAll(allTasks);
    }

    public void addProjectToUser(Project project) {
        projects.add(project);
    }


    @PreRemove
    private void removeProjectsFromUsers() {
        for (Project p : projects) {
            p.getUsersInProject().remove(this);
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(userRole.name());
        return Collections.singletonList(authority);
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String pass) {
        this.password = pass;
    }

    @Override
    public String getUsername() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
