import java.util.ArrayList;
import java.util.List;

public interface HistoryManager {

    void addTaskToHistory(Task task);

     List<Task> getHistory();

}
