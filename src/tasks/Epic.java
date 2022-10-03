package tasks;

import java.util.ArrayList;

public class Epic extends Task {

    ArrayList<Integer> subtasksId;

    public Epic(String taskName, TaskStatus status, String description) {
        super(taskName, status, description, null, null);
        subtasksId = new ArrayList<>();
        taskType = taskType.EPIC;
    }

    public Epic(int id, String taskName, String description, TaskStatus status, TaskTypes taskType) {
        super(id, taskName, description, status, taskType, null, null);
        subtasksId = new ArrayList<>();
        taskType = taskType.EPIC;
    }


    public ArrayList<Integer> getSubtasksId() {
        return subtasksId;
    }

    public TaskStatus getEpicStatus() {
        return status;
    }

    public String getEpicDescription() {
        return description;
    }

    public String getEpicName() {
        return taskName;
    }

    public void setEpicName(String EpicName) {
        this.taskName = taskName;
    }

    public void setEpicStatus(TaskStatus status) {
        this.status = status;
    }

    public void setEpicDescription(String description) {
        this.description = description;
    }


    public void setSubtasksId(ArrayList<Integer> subtasksId) {
        this.subtasksId = subtasksId;
    }

    @Override
    public String toString() {
        return "tasks.Epic{" +
                "subtasksId=" + subtasksId +
                ", taskName='" + taskName + '\'' +
                ", status=" + status +
                ", description='" + description + '\'' +
                ", taskId=" + taskId +
                ", startTime=" + startTime +
                ", duration=" + duration +
                '}';
    }
}
