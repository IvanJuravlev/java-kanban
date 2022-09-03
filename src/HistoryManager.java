import java.util.ArrayList;
import java.util.List;


public interface HistoryManager {

     List<Task> getHistory();

     void remove(int id);

    void addTaskToHistory(Task task);


}
