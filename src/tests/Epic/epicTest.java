import managers.Managers;
import managers.TaskManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.Epic;
import tasks.Subtask;
import tasks.TaskStatus;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class epicTest {

    private static TaskManager manager;


    static Epic epic1;



       @BeforeEach
       public void beforeEach() {
        manager = Managers.getDefault();

         epic1 = new Epic("Тренировка1", TaskStatus.IN_PROGRESS, "Тренировка1");
        manager.saveEpic(epic1);

        }


       @Test
       void updateEpicStatusBySubtasksNewTest() {

        Subtask subTask1 = new Subtask("Прийти в зал1", TaskStatus.NEW, "переодеться1", 1,
                LocalDateTime.of(2022, 9, 27, 13, 30, 15), Duration.ofMinutes(60));
        Subtask subTask2 = new Subtask("Прийти в зал2", TaskStatus.NEW, "переодеться2", 1,
                LocalDateTime.of(2023, 9, 27, 14, 30, 15), Duration.ofMinutes(50));
        manager.saveSubtask(subTask1);
        manager.saveSubtask(subTask2);

        TaskStatus status = manager.getEpic(1).getEpicStatus();

        assertEquals(TaskStatus.NEW, status);

        }

        @Test
        void updateEpicStatusBySubtasksDoneTest() {
           Subtask subTask1 = new Subtask("Прийти в зал1", TaskStatus.DONE, "переодеться1", 1,
                    LocalDateTime.of(2022, 9, 27, 13, 30, 15), Duration.ofMinutes(60));
            Subtask subTask2 = new Subtask("Прийти в зал2", TaskStatus.DONE, "переодеться2", 1,
                    LocalDateTime.of(2023, 9, 27, 14, 30, 15), Duration.ofMinutes(50));

            manager.saveSubtask(subTask1);
            manager.saveSubtask(subTask2);

            TaskStatus status1 = manager.getEpic(1).getEpicStatus();
            assertEquals(TaskStatus.DONE, status1);

        }


        @Test
        void updateEpicStatusBySubtasksNewDoneTest() {

            Subtask subTask5 = new Subtask("Прийти в зал2", TaskStatus.DONE, "переодеться2", 1,
                    LocalDateTime.of(2026, 9, 27, 14, 30, 15), Duration.ofMinutes(50));
            Subtask subTask6 = new Subtask("Прийти в зал1", TaskStatus.NEW, "переодеться1", 1,
                    LocalDateTime.of(2027, 9, 27, 13, 30, 15), Duration.ofMinutes(60));
            manager.saveSubtask(subTask5);
            manager.saveSubtask(subTask6);

            TaskStatus status2 = manager.getEpic(1).getEpicStatus();
            assertEquals(TaskStatus.IN_PROGRESS, status2);

        }

        @Test
        void updateEpicStatusWithoutSubtasksTest() {


           Epic epic2 = new Epic("Тренировка1", TaskStatus.NEW, "Тренировка1");
           manager.saveEpic(epic2);

           TaskStatus status3 = manager.getEpic(2).getEpicStatus();
           assertEquals(TaskStatus.NEW, status3);

    }
        @Test
        void updateEpicStatusBySubtasksIN_ProgressTest() {


            Subtask subTask7 = new Subtask("Прийти в зал2", TaskStatus.IN_PROGRESS, "переодеться2", 1,
                    LocalDateTime.of(2028, 9, 27, 14, 30, 15), Duration.ofMinutes(50));
            Subtask subTask8 = new Subtask("Прийти в зал1", TaskStatus.IN_PROGRESS, "переодеться1", 1,
                 LocalDateTime.of(2029, 9, 27, 13, 30, 15), Duration.ofMinutes(60));
            manager.saveSubtask(subTask7);
            manager.saveSubtask(subTask8);

            TaskStatus status4 = manager.getEpic(1).getEpicStatus();
            assertEquals(TaskStatus.IN_PROGRESS, status4);
    }


    }

