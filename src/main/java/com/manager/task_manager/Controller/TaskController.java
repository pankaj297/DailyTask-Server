package com.manager.task_manager.Controller;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.manager.task_manager.Model.Task;
import com.manager.task_manager.Model.User;
import com.manager.task_manager.Services.TaskServices;
import com.manager.task_manager.Services.UserServices;

@RestController
@RequestMapping("/tasks")
@CrossOrigin(origins = "*") // Allow React frontend
public class TaskController {

    @Autowired
    private TaskServices taskServices;

    @Autowired
    private UserServices userServices;

    //* // Get all tasks (not by user — consider removing if not needed)*/
    @GetMapping("/all")
    public ResponseEntity<List<Task>> getAllTask() {
        List<Task> task = this.taskServices.getAllTask();
        return task.isEmpty() ? ResponseEntity.status(HttpStatus.NOT_FOUND).build() : ResponseEntity.ok(task);
    }

    //*Get Single Task */
    @GetMapping("/task/{tId}")
    public ResponseEntity<Task> getTask(@PathVariable("tId") Long tId, Principal principal) {
        try {
            String email = principal.getName();
            User user = userServices.getUserByEmail(email);

            Task task = taskServices.getTask(tId);
            if (task != null && task.getUser().getId().equals(user.getId())) {
                return ResponseEntity.ok(task);
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    //* Update Task */
    @PutMapping("/update/{taskId}")
    public ResponseEntity<Task> updateTask(@RequestBody Task task, @PathVariable("taskId") Long taskId) {

        try {
            this.taskServices.updateTask(task, taskId);
            return ResponseEntity.ok().body(task);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

        }
    }

    @DeleteMapping("/delete/{taskId}")
    public ResponseEntity<?> deleteTask(@PathVariable("taskId") Long taskId) {
        try {
            taskServices.deleteTask(taskId);
            return ResponseEntity.ok(Map.of("message", "Task deleted successfully", "taskId", taskId));
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task Not Found With Id : " + taskId);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something Went To Wrong ");
        }

    }

    //*  Get tasks by user
    @GetMapping("/my-tasks")
    public ResponseEntity<List<Task>> getTasksForLoggedInUser(Principal principal) {
        try {
            String email = principal.getName(); // logged-in user's email
            User user = userServices.getUserByEmail(email); // now it works
            List<Task> tasks = taskServices.getTasksByUser(user.getId());
            return ResponseEntity.ok(tasks);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    //* http://localhost:8080/user/3/tasks/by-category?categoryName=Fitness */ Get tasks by user and category
    @GetMapping("/by-category")
    public ResponseEntity<List<Task>> getTasksByUserAndCategory(
            @RequestParam String categoryName, Principal principal) {

        try {
            String email = principal.getName();
            User user = userServices.getUserByEmail(email);
            List<Task> tasks = taskServices.getTasksByUserAndCategory(user.getId(), categoryName);
            return ResponseEntity.ok(tasks);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

        }
    }

    //* Add Task
    @PostMapping("/add")
    public ResponseEntity<?> addTaskWithCategory(
            @RequestBody Task task, Principal principal) {

        try {
            // 1️⃣ Get logged-in user's email
            String email = principal.getName();

            // 2️⃣ Fetch user by email
            User user = userServices.getUserByEmail(email);

            // 3️⃣ Set user to task
            task.setUser(user);

            // 4️⃣ Get category name from request body
            String categoryName = task.getCategory().getName();

            // 5️⃣ Save task with category
            Task savedTask = taskServices.addTaskWithCategory(task, categoryName);

            return ResponseEntity.status(HttpStatus.CREATED).body(savedTask);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}



