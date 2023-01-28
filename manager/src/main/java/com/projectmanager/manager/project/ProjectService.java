package com.projectmanager.manager.project;

import com.projectmanager.manager.Task.Task;
import com.projectmanager.manager.user.User;
import com.projectmanager.manager.user.UserRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Set;

@Service
public class ProjectService {

    ProjectRepository projectRepository;
    UserRepository userRepository;


    ProjectService(ProjectRepository projectRepository, UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    public Project addProject(Project project) {
        return projectRepository.save(project);
    }

    public List<Project> getAllProjects() {
        return (List<Project>) projectRepository.findAll();
    }

    public Project findProjectById(Long id) {
        return projectRepository.findById(id).get();
    }

    public void deleteProjectById(Long id) {
        Project project = projectRepository.findById(id).orElseThrow(() -> new IllegalStateException("Project does not exist"));
        projectRepository.deleteById(id);
    }

    public Project updateProject(String name, Long id) {
        Project project = projectRepository.findById(id).orElseThrow(() -> new IllegalStateException("Project does not exist"));
        if (name != null && name.length() >= 0 && !name.equalsIgnoreCase(project.getName())) {
            project.setName(name);
        }
        return project;
    }

    public Project userIntoProject(Long pId, Long uId) {
        Project project = projectRepository.findById(pId).orElseThrow(() -> new IllegalStateException("Project does not exist"));
        User user = userRepository.findById(uId).orElseThrow(() -> new IllegalStateException("User does not exist"));
        user.addProjectToUser(project);
        project.addUserToProject(user);
        return projectRepository.save(project);
    }

    public Project deleteUserFromProject(Long pId, Long uId) {
        Project project = projectRepository.findById(pId).orElseThrow(() -> new IllegalStateException("Project does not exist"));
        User user = userRepository.findById(uId).orElseThrow(() -> new IllegalStateException("User does not exist"));
        project.deleteUserFromProject(user);
        return projectRepository.save(project);
    }

    public Set<Task> getTasksInProject(Long pId) {
        Project project = projectRepository.findById(pId).orElseThrow(() -> new IllegalStateException("Project does not exist"));
        return project.getTasks();
    }

    public Project getTimeSpentOnProject(Long id) {
        Project project = projectRepository.findById(id).orElseThrow(() -> new IllegalStateException("Project does not exist"));
        long tempTime = 0;
       for( Task task : project.getTasks()){
            tempTime += task.getTimeSpent();
       }
       project.setTimeSpent(tempTime);
        return projectRepository.save(project);
    }

}
