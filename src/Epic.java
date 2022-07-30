import java.util.ArrayList;

public class Epic extends Task{

    protected int epicId;

    ArrayList<Integer> subtasksId;

    public Epic(String taskName, String status, String description) {
        super(taskName, status, description);
        subtasksId = new ArrayList<>();
    }

    public int getEpicId() {
        return epicId;
    }

    public ArrayList<Integer> getSubtasksId() {
        return subtasksId;
    }

    public String getEpicStatus(){
        return status;
    }

    public String getEpicDescription(){
        return description;
    }

    public String getEpicName(){
        return taskName;
    }

    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }
    public void setEpicName(String EpicName) {
        this.taskName = taskName;
    }

    public void setEpicStatus(String status) {
        this.status = status;
    }

    public void setEpicDescription(String description) {
        this.description = description;
    }


    public void setSubtasksId(ArrayList<Integer> subtasksId) {
        this.subtasksId = subtasksId;
    }
}
