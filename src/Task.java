
public class Task {

    protected String taskName;
    protected String status;
    protected String description;
    protected int taskId;


    public Task(String taskName, String status, String description) {
        this.taskName = taskName;
        this.status = status;
        this.description = description;

    }

    public String getTaskName() {
        return taskName;
    }

    public String getTaskStatus() {
        return status;
    }

    public String getTaskDescription() {
        return description;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public void setTaskStatus(String status) {
        this.status = status;
    }

    public void setTaskDescription(String description) {
        this.description = description;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }
}
