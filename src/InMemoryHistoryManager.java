import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager{

     List<Task> taskHistoryList = new ArrayList<>();

      @Override
     public void addTaskToHistory(Task task){
        if(taskHistoryList.size() == 10){
            taskHistoryList.remove(0);
            taskHistoryList.add(task);
        } else{
            taskHistoryList.add(task);
        }
    }


    @Override
    public List<Task> getHistory(){
        return taskHistoryList;
    }


}
