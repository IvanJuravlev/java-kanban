import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface TaskManager {

    int generatorId = 0;

  //  HashMap<Integer, Task> taskMap = new HashMap<>();
  //  HashMap<Integer, Epic> epicMap = new HashMap<>();
   // HashMap<Integer, Subtask> subtaskMap = new HashMap<>();
 //   List<Task> taskHistoryList = new ArrayList<>();


     void saveTask(Task task);

     void saveEpic(Epic epic);

     void saveSubtask(Subtask subtask);

   //  List<Task> getHistory();

   //  void addTaskToHistory(Task task);

     HistoryManager getHistoryManager();






     void removeAllTasks();


     void removeWithId(int id);

     Epic getEpic(int id);

     Subtask getSubtask(int id);


     Task getTask(int id);


     void changeTask(Task task, int id);

      void changeEpic(Epic epic, int id);

     void changeSubtask(Subtask subtask, int id);

      void changeEpicStatus(int id);


     void printAllTasks();

     void printSubtask();

     void printTask();

     void printEpic();

}






