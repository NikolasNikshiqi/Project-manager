package com.projectmanager.manager.user;

import com.projectmanager.manager.Task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

@CrossOrigin(origins = "*")
@RequestMapping("/project/user")
@RestController
public class UserController {

    UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAll(){
        return userService.getAllUsers();
    }

    @GetMapping(path = "{user_id}")
    public User getUserById(@PathVariable("user_id") Long id){
        return userService.findUserById(id);
    }

    @GetMapping(path = "/{user_id}/task")
    public Set<Task> getTasksFromUser(@PathVariable("user_id") Long id){
        return userService.getTasksFromUser(id);
    }

    @PostMapping(path = "/add")
    public User addUser(@RequestBody User user,@RequestParam(required = false,name = "userRole") String status){
        return userService.addUser(user,status);
    }

    @DeleteMapping(path = "{user_id}")
    public void deleteUserById(@PathVariable("user_id") Long id){
        userService.deleteUserById(id);
    }

    @Transactional
    @PutMapping(path = "{user_id}")
    public User updateUser(@RequestBody User user,@PathVariable("user_id") Long id){

        return userService.updateUser(user.getUsername(), id);
    }


}
