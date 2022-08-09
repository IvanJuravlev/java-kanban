import java.util.ArrayList;
import java.util.List;


public interface HistoryManager {

   // List<Task> taskHistoryList = new ArrayList<>();

    void addTaskToHistory(Task task);

     List<Task> getHistory();


}
