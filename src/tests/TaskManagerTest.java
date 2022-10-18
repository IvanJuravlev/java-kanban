
import managers.TaskManager;

import org.junit.jupiter.api.Test;
import tasks.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;


 abstract class TaskManagerTest<T extends TaskManager> {



     protected T manager;

     static Task task1;
     static Task task2;
     static Epic epic1;



     public void createTasks(){

          task1 = new Task("Спринт1", TaskStatus.NEW, "учу1",
                 LocalDateTime.of(2020, 9, 25, 13, 30, 15), Duration.ofMinutes(20));
          task2 = new Task("Спринт2", TaskStatus.NEW, "учу2",
                 LocalDateTime.of(2020, 9, 25, 13, 55, 15), Duration.ofMinutes(20));
          epic1 = new Epic("Тренировка1", TaskStatus.IN_PROGRESS, "Тренировка1");

     }


    @Test
    void saveTaskAndGetTaskByIdTest() {
        createTasks();
        manager.saveTask(task1);
        assertEquals(task1, manager.getTask(1));
    }

     @Test
     void SubtaskAndGetTaskByIdTest() {
         createTasks();
         manager.saveEpic(epic1);
         Subtask subTask1 = new Subtask( "Прийти в зал1", TaskStatus.NEW, "переодеться1", 1,
                 LocalDateTime.of(2022, 10, 27, 13, 30, 15), Duration.ofMinutes(60));
         manager.saveSubtask(subTask1);
         assertEquals(subTask1, manager.getSubtask(2));
     }



    @Test
    void removeAllTasksTest() {
        createTasks();
        manager.saveTask(task1);

        manager.saveTask(task2);
        manager.removeAllTasks();
        assertTrue(manager.getTaskMap().isEmpty());
    }

     @Test
     void getEpicTimesAndDurationTest() {
         createTasks();

         manager.saveEpic(epic1);
         Subtask subTask1 = new Subtask("Прийти в зал1", TaskStatus.NEW, "переодеться1", 1,
                 LocalDateTime.of(2022, 9, 26, 21, 0), Duration.ofMinutes(30));
         Subtask subTask2 = new Subtask("Прийти в зал2", TaskStatus.NEW, "переодеться2", 1,
                 LocalDateTime.of(2022, 9, 26, 22, 0), Duration.ofMinutes(30));
         manager.saveSubtask(subTask1);
         manager.saveSubtask(subTask2);

         assertEquals(manager.getEpicsMap().get(1).getStartTime(), manager.getSubtasksMap().get(2).getStartTime());
         assertEquals(manager.getEpicsMap().get(1).getEndTime(), manager.getSubtasksMap().get(3).getEndTime());
         assertEquals(Duration.ofMinutes(90), manager.getEpicsMap().get(1).getDuration());
     }

     @Test
     void getHistoryTest() {
         createTasks();
         manager.saveEpic(epic1);
         manager.getEpic(1);

         Subtask subTask1 = new Subtask("Прийти в зал1", TaskStatus.NEW, "переодеться1", 1,
                 LocalDateTime.of(2022, 9, 26, 21, 0), Duration.ofMinutes(30));
         Subtask subTask2 = new Subtask("Прийти в зал2", TaskStatus.NEW, "переодеться2", 1,
                 LocalDateTime.of(2022, 9, 26, 23, 0), Duration.ofMinutes(30));
         manager.saveSubtask(subTask1);
         manager.getSubtask(2);
         manager.saveSubtask(subTask2);
         manager.getSubtask(3);

         assertEquals(3, manager.getHistory().size());


     }


     @Test
     void ReturnPrioritizedTasksTest() {
         createTasks();
         manager.saveTask(task1);
         manager.saveTask(task2);
         manager.saveEpic(epic1);

         Subtask subTask1 = new Subtask("Прийти в зал1", TaskStatus.NEW, "переодеться1", 3,
                 LocalDateTime.of(2022, 9, 26, 21, 0), Duration.ofMinutes(30));
         Subtask subTask2 = new Subtask("Прийти в зал2", TaskStatus.NEW, "переодеться2", 3,
                 LocalDateTime.of(2022, 9, 26, 23, 0), Duration.ofMinutes(30));
         manager.saveSubtask(subTask1);
         manager.saveSubtask(subTask2);


         List<Task> prioritizedTasks = new ArrayList<>();

         prioritizedTasks.add(task2);
         prioritizedTasks.add(subTask2);
         prioritizedTasks.add(subTask1);
         prioritizedTasks.add(task1);

         int size = prioritizedTasks.size();

         assertEquals(size, manager.getTasksTreeSet().size());

     }

 }








