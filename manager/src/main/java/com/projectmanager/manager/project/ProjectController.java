package com.projectmanager.manager.project;

import com.projectmanager.manager.Task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/project")
public class ProjectController {

    ProjectService projectService;

    @Autowired
    ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping
    public List<Project> getAllProjects() {
        return projectService.getAllProjects();
    }

    @GetMapping(path = "{projectId}")
    public Project getProjectById(@PathVariable("projectIid") Long id) {
        return projectService.findProjectById(id);
    }

    @GetMapping(path = "/task/{projectId}")
    public Set<Task> getTasksInProject(@PathVariable("projectId") Long id ){
        return projectService.getTasksInProject(id);
    }

    @GetMapping(path = "/taskTime/{projectId}")
    public Project getTimeSpentOnProject(@PathVariable("projectId") Long id){
    return projectService.getTimeSpentOnProject(id);
    }

    @PostMapping
    public Project addProject(@RequestBody Project project) {
        return projectService.addProject(project);
    }

    @DeleteMapping(path = "{projectId}")
    public void deleteProjectById(@PathVariable("projectId") Long id) {
        projectService.deleteProjectById(id);
        System.out.println();
    }

    @Transactional
    @PutMapping(path = "{projectId}")
    public Project updateProject(@RequestBody Project project, @PathVariable("projectId") Long id) {

        return projectService.updateProject(project.getName(), id);
    }

    @Transactional
    @PutMapping(path = "/{projectId}/user/{user_id}")
    public Project userIntoProject(@PathVariable("projectId") Long pId,@PathVariable("user_id") Long uId){
        return projectService.userIntoProject(pId,uId);
    }

    @DeleteMapping(path = "/{projectId}/user/{user_id}")
    public Project deleteUserFromProject(@PathVariable("projectId") Long pId,@PathVariable("user_id") Long uId){
        return projectService.deleteUserFromProject(pId,uId);
    }


}
