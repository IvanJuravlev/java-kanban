package managers;

import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface TaskManager {

     public HashMap<Integer, Task> getTaskMap();


     void saveTask(Task task);

     Map<Integer, Subtask> getSubtasks();

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

     void getSubtaskEndTime(Subtask subtask);

     void getTaskEndTime(Task task);

     void getEpicTimesAndDuration(Epic epic);

     void timeIntersection();



}






