public class Subtask extends Task {

    protected int subtaskId;

    protected int epicId;

    public Subtask(String taskName, String status, String description, int epicId) {
        super(taskName, status, description);
        this.epicId = epicId;
    }

    public String getSubtaskStatus(){
        return status;
    }

    public String getSubtaskDescription(){
        return description;
    }

    public String getSubtaskName(){
        return taskName;
    }

    public int getSubtaskId() {
        return subtaskId;
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

    public void setSubtaskStatus(String status) {
        this.status = status;
    }

    public void setSubtaskDescription(String description) {
        this.description = description;
    }

    public void setSubtaskId(int taskId) {
        this.subtaskId = taskId;
    }

}
