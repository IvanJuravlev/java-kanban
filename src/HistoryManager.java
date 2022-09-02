import java.util.ArrayList;
import java.util.List;


public interface HistoryManager {

   // List<Task> taskHistoryList = new ArrayList<>();

   // void removeNode(Integer id);

   // void linkLast(Task task);

   // void removeNode(int id);

     List<Task> getHistory();

     void remove(Task task);

    void addTaskToHistory(Task task);


}
