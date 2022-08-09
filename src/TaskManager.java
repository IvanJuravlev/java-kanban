import java.util.List;

public interface TaskManager {


     void saveTask(Task task);

     void saveEpic(Epic epic);

     void saveSubtask(Subtask subtask);

     List<Task> getHistory();


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






