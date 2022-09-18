public class Subtask extends Task {

  //  protected int subtaskId;

    protected int epicId;

    public Subtask(String taskName, TaskStatus status, String description, int epicId) {
        super(taskName, status, description);
        this.epicId = epicId;
        taskType = taskType.SUBTASK;
    }

    public Subtask(int id, String taskName, TaskStatus status, String description, TaskTypes taskType, int epicId) {
        super(id, taskName,description, status, taskType);
        this.epicId = epicId;
       // this.taskType = taskType.SUBTASK;
    }

    public TaskStatus getSubtaskStatus(){
        return status;
    }

    public String getSubtaskDescription(){
        return description;
    }

    public String getSubtaskName(){
        return taskName;
    }



    public int getEpicId() {
        return epicId;
    }


    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }

    public void setSubtaskName(String taskName) {
        this.taskName = taskName;
    }

    public void setSubtaskStatus(TaskStatus status) {
        this.status = status;
    }

    public void setSubtaskDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Subtask{" +
                "epicId=" + epicId +
                ", taskName='" + taskName + '\'' +
                ", status=" + status +
                ", description='" + description + '\'' +
                ", taskId=" + taskId +
                '}';
    }

}
