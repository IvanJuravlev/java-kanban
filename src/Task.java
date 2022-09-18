
public class Task {

    protected String taskName;
    protected TaskStatus status;
    protected String description;
    protected int taskId;

    protected TaskTypes taskType;


    public Task(int taskId, String taskName, String description, TaskStatus status, TaskTypes taskType){
        this.taskId = taskId;
        this.taskName = taskName;
        this.description = description;
        this.status = status;
        this.taskType = taskType.TASK;
    }


    public Task(String taskName, TaskStatus status, String description) {
        this.taskName = taskName;
        this.status = status;
        this.description = description;
        this.taskType = taskType.TASK;

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

    @Override
    public String toString() {
        return "Task{" +
                "taskName='" + taskName + '\'' +
                ", status=" + status +
                ", description='" + description + '\'' +
                ", taskId=" + taskId +
                '}';
    }
}
