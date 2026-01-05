package queueing_system;

import queueing_system.task_queue.TaskQueue;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        TaskQueue taskQueue = new TaskQueue();
        Scanner scanner = new Scanner(System.in);
        int noOfLines = scanner.nextInt();
        scanner.nextLine();
        List<String> input = new ArrayList<>();
        for (int i = 0; i < noOfLines; i++) {
            input.add(scanner.nextLine());
        }
        scanner.close();
        for (String s : input) {
            String[] instructions = s.split(" ");
            String action = instructions[0];
            String taskId;
            switch (action) {
                case "add_task":
                    taskId = instructions[1];
                    int priority = Integer.parseInt(instructions[2]);
                    String dependenciesString = instructions[3];
                    String[] dependenciesList = dependenciesString.split(",");
                    List<String> dependencies = new ArrayList<>(Arrays.asList(dependenciesList));
                    taskQueue.addTask(taskId, priority, dependencies);
                    break;
                case "is_ready":
                    taskId = instructions[1];
                    System.out.println(taskQueue.getTask(taskId).isReady());
                    break;
                case "get_next":
                    System.out.println(taskQueue.getNextTask());
                    break;
                case "complete_task":
                    taskId = instructions[1];
                    boolean isTaskCompleted = taskQueue.getTask(taskId).completeTask();
                    if (isTaskCompleted) {
                        taskQueue.updateDependencies(taskId);
                        System.out.println("Task " + taskId + " completed successfully");
                    } else {
                        System.out.println("Task " + taskId + " cannot be completed as it has dependents");
                    }
                    break;
            }
        }
    }
}
