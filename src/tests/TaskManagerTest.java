
import managers.HistoryManager;
import managers.InMemoryHistoryManager;
import managers.TaskManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
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


        @Test
        void updateEpicStatusBySubtasksTest() {

            //Test NEW

            Epic epic1 = new Epic("Тренировка1", TaskStatus.IN_PROGRESS, "Тренировка1");
            manager.saveEpic(epic1);
            Subtask subTask1 = new Subtask("Прийти в зал1", TaskStatus.NEW, "переодеться1", 1,
                    LocalDateTime.of(2022, 9, 27, 13, 30, 15), Duration.ofMinutes(60));
            Subtask subTask2 = new Subtask("Прийти в зал2", TaskStatus.NEW, "переодеться2", 1,
                    LocalDateTime.of(2023, 9, 27, 14, 30, 15), Duration.ofMinutes(50));
            manager.saveSubtask(subTask1);
            manager.saveSubtask(subTask2);

            TaskStatus status = manager.getEpic(1).getEpicStatus();
            assertEquals(TaskStatus.NEW, status);

            //Test DONE

            Epic epic2 = new Epic("Тренировка1", TaskStatus.NEW, "Тренировка1");
            manager.saveEpic(epic2);
            Subtask subTask4 = new Subtask("Прийти в зал2", TaskStatus.DONE, "переодеться2", 4,
                    LocalDateTime.of(2024, 9, 27, 14, 30, 15), Duration.ofMinutes(50));
            Subtask subTask3 = new Subtask("Прийти в зал1", TaskStatus.DONE, "переодеться1", 4,
                    LocalDateTime.of(2025, 9, 27, 13, 30, 15), Duration.ofMinutes(60));
            manager.saveSubtask(subTask3);
            manager.saveSubtask(subTask4);

            TaskStatus status1 = manager.getEpic(4).getEpicStatus();
            assertEquals(TaskStatus.DONE, status1);

            //Test NEW/DONE


            Epic epic3 = new Epic("Тренировка1", TaskStatus.NEW, "Тренировка1");
            manager.saveEpic(epic3);
            Subtask subTask5 = new Subtask("Прийти в зал2", TaskStatus.DONE, "переодеться2", 7,
                    LocalDateTime.of(2026, 9, 27, 14, 30, 15), Duration.ofMinutes(50));
            Subtask subTask6 = new Subtask("Прийти в зал1", TaskStatus.NEW, "переодеться1", 7,
                    LocalDateTime.of(2027, 9, 27, 13, 30, 15), Duration.ofMinutes(60));
            manager.saveSubtask(subTask5);
            manager.saveSubtask(subTask6);

            TaskStatus status2 = manager.getEpic(7).getEpicStatus();
            assertEquals(TaskStatus.IN_PROGRESS, status2);

            //Test without Subtasks

            Epic epic4 = new Epic("Тренировка1", TaskStatus.NEW, "Тренировка1");
            manager.saveEpic(epic1);

            TaskStatus status3 = manager.getEpic(10).getEpicStatus();
            assertEquals(TaskStatus.NEW, status3);

            //Test IN_PROGRESS

            Epic epic5 = new Epic("Тренировка1", TaskStatus.NEW, "Тренировка1");
            manager.saveEpic(epic3);
            Subtask subTask7 = new Subtask("Прийти в зал2", TaskStatus.IN_PROGRESS, "переодеться2", 11,
                    LocalDateTime.of(2028, 9, 27, 14, 30, 15), Duration.ofMinutes(50));
            Subtask subTask8 = new Subtask("Прийти в зал1", TaskStatus.IN_PROGRESS, "переодеться1", 11,
                    LocalDateTime.of(2029, 9, 27, 13, 30, 15), Duration.ofMinutes(60));
            manager.saveSubtask(subTask5);
            manager.saveSubtask(subTask6);

            TaskStatus status4 = manager.getEpic(11).getEpicStatus();
            assertEquals(TaskStatus.IN_PROGRESS, status4);

        }


    @Test
    void saveTaskAndGetTaskByIdTest() {
        Task task1 = new Task("Спринт1", TaskStatus.NEW, "учу1",
                LocalDateTime.of(2020, 9, 25, 13, 30, 15), Duration.ofMinutes(20));
        manager.saveTask(task1);
        assertEquals(task1, manager.getTask(1));
    }

     @Test
     void SubtaskAndGetTaskByIdTest() {
         Epic epic1 = new Epic(1, "Тренировка1", "Тренировка1", TaskStatus.DONE, TaskTypes.EPIC);
         manager.saveEpic(epic1);
         Subtask subTask1 = new Subtask( "Прийти в зал1", TaskStatus.NEW, "переодеться1", 1,
                 LocalDateTime.of(2022, 10, 27, 13, 30, 15), Duration.ofMinutes(60));
         manager.saveSubtask(subTask1);
         assertEquals(subTask1, manager.getSubtask(2));
     }



    @Test
    void removeAllTasksTest() {
        Task task1 = new Task("Спринт1", TaskStatus.NEW, "учу1",
                LocalDateTime.of(2020, 9, 25, 13, 30, 15), Duration.ofMinutes(20));
        manager.saveTask(task1);
        Task task2 = new Task("Спринт2", TaskStatus.NEW, "учу2",
                LocalDateTime.of(2020, 9, 25, 13, 55, 15), Duration.ofMinutes(20));
        manager.saveTask(task2);
        manager.removeAllTasks();
        assertTrue(manager.getTaskMap().isEmpty());
    }

     @Test
     void getEpicTimesAndDurationTest() {

         Epic epic1 = new Epic("Тренировка1", TaskStatus.IN_PROGRESS, "Тренировка1");
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
         int somth7 = manager.getHistory().size();
         Epic epic1 = new Epic("Тренировка1", TaskStatus.IN_PROGRESS, "Тренировка1");
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

         int somth3 = manager.getHistory().size();


         int somth4 = manager.getHistory().size();

         assertEquals(3, manager.getHistory().size());

         int somth5 = manager.getHistory().size();

     }


     @Test
     void ReturnPrioritizedTasksTest() {
         Task task1 = new Task("Спринт1", TaskStatus.NEW, "учу1",
                 LocalDateTime.of(2020, 9, 25, 13, 30, 15), Duration.ofMinutes(20));
         manager.saveTask(task1);
         Task task2 = new Task("Спринт2", TaskStatus.NEW, "учу2",
                 LocalDateTime.of(2020, 9, 25, 13, 55, 15), Duration.ofMinutes(20));
         manager.saveTask(task2);

         Epic epic1 = new Epic("Тренировка1", TaskStatus.IN_PROGRESS, "Тренировка1");
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








