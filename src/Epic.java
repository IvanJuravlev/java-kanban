import java.util.ArrayList;

public class Epic extends Task {

    ArrayList<Integer> subtasksId;

    public Epic(String taskName, TaskStatus status, String description) {
        super(taskName, status, description);
        subtasksId = new ArrayList<>();
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
        return "Epic{" +
                "subtasksId=" + subtasksId +
                ", taskName='" + taskName + '\'' +
                ", status=" + status +
                ", description='" + description + '\'' +
                ", taskId=" + taskId +
                '}';
    }
}
