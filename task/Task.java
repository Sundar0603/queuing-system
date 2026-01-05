package queueing_system.task;

import queueing_system.status.Status;

import java.util.ArrayList;
import java.util.List;

public class Task {
    private final List<String> dependents;
    private final String taskId;
    private int priority;
    private final List<String> dependencies;
    private Status status;
    private final int order;

    public Task(String task_id, int priority, List<String> dependencies, int order) {
        this.dependents =  new ArrayList<>();
        this.taskId = task_id;
        this.priority = priority;
        this.dependencies = dependencies;
        this.status = Status.READY;
        this.order = order;
    }

    public String getTaskId() {
        return this.taskId;
    }

    public int getPriority() {
        return this.priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public Status getStatus() {
        return status;
    }

    public List<String> getDependencies() {
        return dependencies;
    }

    public List<String> getDependents() {
        return dependents;
    }

    public void addDependent(String dependent) {
        if(this.dependents.contains(dependent)) {
            System.out.println(this.taskId +  "already has " + dependent + "as a dependent");
        }
        this.dependents.add(dependent);
    }

    public boolean completeTask() {
        if(!this.isReady()) {
            System.out.println(this.taskId + " cannot be completed as it has dependents");
            return false;
        }
        this.status = Status.COMPLETED;
        return true;
    }

    public boolean isReady() {
        return this.dependencies.isEmpty() && this.status == Status.READY;
    }

    public void removeDependency(String dependency) {
        this.dependencies.remove(dependency);
    }

    public int getOrder() {
        return order;
    }
}
