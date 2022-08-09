import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager{

     private final List<Task> taskHistoryList = new ArrayList<>();

      @Override
     public void addTaskToHistory(Task task){
              taskHistoryList.add(task);
              if (taskHistoryList.size() > 10) {
                  taskHistoryList.remove(0);
              }
    }

    @Override
    public List<Task> getHistory(){
        return taskHistoryList;
    }

}
