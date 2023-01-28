package com.projectmanager.manager.Task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/task")
public class TaskController {

    TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public List<Task> getAllTasks(){
        return taskService.getAllTasks();
    }

    @GetMapping(path = "/time/{taskId}")
    public Long getTimeSpent(@PathVariable("taskId") Long id ){
        return taskService.timeSpentOnTask(id);
    }
    @GetMapping(path = "/time")
    public String getTaskStats(){
        return taskService.getTaskStatistics();
    }

    @PostMapping(path = "project/{projectId}/user/{user_id}")
    public Task addTaskToUser(@PathVariable("projectId") Long pId,@PathVariable("user_id") Long uId,@RequestBody Task task,@RequestParam(required = false,name ="taskStatus") String status ){
        System.out.println("Here");
        return taskService.addTaskToUser(pId, uId, task,status);
    }

    @GetMapping(path = "{taskId}")
    public Task findTaskById(@PathVariable("taskId") Long id){
        return taskService.findTaskById(id);
    }

    @DeleteMapping(path = "{taskId}")
    public void deleteTaskById(@PathVariable("taskId") Long id){
        taskService.deleteTaskById(id);
    }

//    @PutMapping(path = "{taskId}")
//    public Task updateTask(@RequestBody Task task,@PathVariable("taskId") Long id){
//        return taskService.updateTask(task.getName(),id);
//    }

    @PutMapping(path = "{taskId}")
    public Task changeTaskStatus(@PathVariable("taskId") Long id,@RequestParam(required = false,name = "taskStatus") String status){
        return taskService.changeTaskStatus(id,status);
    }

    @DeleteMapping(path = "{taskId}/project/user/{user_id}")
    public void removeUserFromTask(@PathVariable("taskId") Long tId,@PathVariable("user_id") Long uId){
        taskService.removeTaskFromUser(tId,uId);
    }

}
