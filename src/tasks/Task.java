package tasks;

import java.time.Duration;
import java.time.LocalDateTime;

public class Task {

    protected String taskName;
    protected TaskStatus status;
    protected String description;
    protected int taskId;

    protected TaskTypes taskType;

    protected Duration duration;

    protected LocalDateTime startTime;
    protected LocalDateTime endTime;


    public Task(int taskId, String taskName, String description, TaskStatus status, TaskTypes taskType, LocalDateTime startTime, Duration duration){
        this.taskId = taskId;
        this.taskName = taskName;
        this.description = description;
        this.status = status;
        this.taskType = taskType.TASK;
        this.duration = duration;
        this.startTime = startTime;
    }


    public Task(String taskName, TaskStatus status, String description, LocalDateTime startTime, Duration duration) {
        this.taskName = taskName;
        this.status = status;
        this.description = description;
        this.taskType = taskType.TASK;
        this.duration = duration;
        this.startTime = startTime;

    }

    public String getTaskName() {
        return taskName;
    }

    public TaskStatus getTaskStatus() {
        return status;
    }

    public String getTaskDescription() {
        return description;
    }

    public int getTaskId() {
        return taskId;
    }

    public TaskTypes getTaskType() {
        return taskType;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public void setTaskStatus(TaskStatus status) {
        this.status = status;
    }

    public void setTaskDescription(String description) {
        this.description = description;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "tasks.Task{" +
                "taskName='" + taskName + '\'' +
                ", status=" + status +
                ", description='" + description + '\'' +
                ", taskId=" + taskId +
                '}';
    }
}
