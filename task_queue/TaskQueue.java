package queueing_system.task_queue;

import queueing_system.status.Status;
import queueing_system.task.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaskQueue {
    private final Map<String, Task> taskList;
    private int order = 0;

    public TaskQueue() {
        this.taskList = new HashMap<>();
    }

    public void addTask(String taskId, int priority, List<String> dependencies) {
        if(this.taskList.containsKey(taskId)) {
            System.out.println("Task with id " + taskId + " already exists. Cannot be added twice");
        }
        Task newTask = new Task(taskId, priority, dependencies, this.order++);
        if(!dependencies.isEmpty()) {
            for(String dependency : dependencies) {
                Task dependencyTask = taskList.get(dependency);
                if(dependencyTask != null) {
                    dependencyTask.addDependent(taskId);
                }
            }
        }
        taskList.put(taskId, newTask);
    }

    public String getNextTask() {
        List<Task> filteredTaskList = new ArrayList<>();
        for(Task task : taskList.values()) {
            if(task.isReady()) {
                filteredTaskList.add(task);
            }
        }
        if(filteredTaskList.isEmpty()) {
            return null;
        }
        filteredTaskList.sort((Task a, Task b) -> {
            if (a.getPriority() > b.getPriority()) {
                return -1;
            } else if (a.getPriority() < b.getPriority()) {
                return 1;
            } else {
                return a.getOrder() > b.getOrder() ? 1 : -1;
            }
        });
        return filteredTaskList.get(0).getTaskId();
    }

    public Status getStatus(String taskId) {
        return taskList.get(taskId).getStatus();
    }

    public List<Task> getReadyTask() {
        List<Task> readyTasks = new ArrayList<>();
        for(Task task : taskList.values()) {
            if(task.isReady()) {
                readyTasks.add(task);
            }
        }
        return readyTasks;
    }

    public Task getTask(String taskId) {
        return taskList.get(taskId);
    }

    public void updateDependencies(String taskId) {
        Task task = this.taskList.get(taskId);
        List<String> dependents = task.getDependents();
        for(String d : dependents) {
            this.taskList.get(d).removeDependency(taskId);
        }
    }
}
