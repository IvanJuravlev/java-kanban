
public class Task {

    protected String taskName;
    protected TaskStatus status;
    protected String description;
    protected int taskId;


    public Task(String taskName, TaskStatus status, String description) {
        this.taskName = taskName;
        this.status = status;
        this.description = description;

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
}
