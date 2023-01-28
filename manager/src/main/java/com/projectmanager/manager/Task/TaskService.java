package com.projectmanager.manager.Task;

import com.projectmanager.manager.project.Project;
import com.projectmanager.manager.project.ProjectRepository;
import com.projectmanager.manager.user.User;
import com.projectmanager.manager.user.UserRepository;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import java.util.LongSummaryStatistics;
import java.util.stream.Collectors;


@Service
public class TaskService {
    TaskRepository taskRepository;
    UserRepository userRepository;
    ProjectRepository projectRepository;

    public TaskService(TaskRepository taskRepository, UserRepository userRepository, ProjectRepository projectRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
    }

    public List<Task> getAllTasks() {
        return (List<Task>) taskRepository.findAll();
    }

    public Task findTaskById(Long id) {
        return taskRepository.findById(id).get();
    }

    public String getTaskStatistics(){
        List<Task> tasks = (List<Task>) taskRepository.findAll();
        LongSummaryStatistics stats = tasks.stream().collect(Collectors.summarizingLong(Task::getTimeSpent));
        return "Max time spent on a task: "  + stats.getMax() + ",\nMin time spent on a task: " + stats.getMin() +
                ",\nAverage time spent on a task: " + stats.getAverage();
    }

    public void deleteTaskById(Long id) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new IllegalStateException("Task does not exist"));
        taskRepository.deleteById(id);
    }
    public Long timeSpentOnTask(Long id){
        Task task = taskRepository.findById(id).orElseThrow(() -> new IllegalStateException("Task does not exist"));
        Long time = task.getTimeSpent();
        taskRepository.save(task);
        return time;
    }
    public Task updateTask(String name, Long id) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new IllegalStateException("Task does not exist"));
        if (name != null && name.length() >= 0 && !name.equalsIgnoreCase(task.getName())) {
            task.setName(name);
        }
        return task;
    }

    public Task changeTaskStatus(Long id, String status) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new IllegalStateException("Task does not exist"));
        task.setTaskStatus(TaskStatus.valueOf(status));
        if (status.equals("DONE")) {
            task.setEndDate(new Date());
            task.setTimeSpent((task.getEndDate().getTime() - task.getStartDate().getTime()) / 1000);
        }
        return taskRepository.save(task);

    }

    public void removeTaskFromUser(Long tId, Long uId) {
        User user = userRepository.findById(uId).orElseThrow(() -> new IllegalStateException("User does not exist"));
        Task task = taskRepository.findById(tId).orElseThrow(() -> new IllegalStateException("Task does not exist"));
        if (user.getTasks().contains(task)) {
            user.getTasks().remove(task);
            task.setUser(null);
        }
    }

    public Task addTaskToUser(Long pId, Long uId, Task task, String status) {
        Project project = projectRepository.findById(pId).orElseThrow(() -> new IllegalStateException("Project does not exist"));
        User user = userRepository.findById(uId).orElseThrow(() -> new IllegalStateException("User does not exist"));
        if (!project.getUsersInProject().contains(user)) {
            throw new IllegalStateException("The project does not contain user with id " + uId);
        }
        task.setTaskStatus(TaskStatus.valueOf(status));
        task.setUser(user);
        task.setProject(project);
        user.addTasksToUser(task);
        project.addTaskToProject(task);
        return taskRepository.save(task);
    }
}
