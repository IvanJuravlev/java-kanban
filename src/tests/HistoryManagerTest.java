import managers.HistoryManager;
import managers.InMemoryHistoryManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;
import tasks.TaskStatus;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class HistoryManagerTest extends InMemoryHistoryManager {

    HistoryManager manager;

    @BeforeEach
    public void beforeEach() {
        manager = new InMemoryHistoryManager();
    }

    @Test
    void AddTaskInHistoryTest() {
        Task task1 = new Task("Спринт2", TaskStatus.NEW, "учу2",
                LocalDateTime.of(2022, 9, 26, 13, 30, 15), Duration.ofMinutes(20));

        task1.setTaskId(1);
        manager.addTaskToHistory(task1);

        Epic epic1 = new Epic("Тренировка1", TaskStatus.IN_PROGRESS, "Тренировка1");

        epic1.setTaskId(2);
        manager.addTaskToHistory(epic1);


        Subtask subTask1 = new Subtask("Прийти в зал1", TaskStatus.NEW, "переодеться1", 2,
                LocalDateTime.of(2022, 9, 27, 13, 30, 15), Duration.ofMinutes(60));

        subTask1.setTaskId(3);
        manager.addTaskToHistory(subTask1);


        assertEquals(3, manager.getHistory().size());
    }

    @Test
    void removeFromHistoryTest() {
        Task task1 = new Task("Спринт2", TaskStatus.NEW, "учу2",
                LocalDateTime.of(2022, 9, 26, 13, 30, 15), Duration.ofMinutes(20));

        task1.setTaskId(1);
        manager.addTaskToHistory(task1);

        Epic epic1 = new Epic("Тренировка1", TaskStatus.IN_PROGRESS, "Тренировка1");

        epic1.setTaskId(2);
        manager.addTaskToHistory(epic1);


        Subtask subTask1 = new Subtask("Прийти в зал1", TaskStatus.NEW, "переодеться1", 2,
                LocalDateTime.of(2022, 9, 27, 13, 30, 15), Duration.ofMinutes(60));

        subTask1.setTaskId(3);
        manager.addTaskToHistory(subTask1);

        manager.remove(1);

        assertEquals(2, manager.getHistory().size());

        manager.remove(2);
        manager.remove(3);

        assertTrue(manager.getHistory().isEmpty());
    }

    @Test
    void getHistoryListTest() {
        Task task1 = new Task("Спринт2", TaskStatus.NEW, "учу2",
                LocalDateTime.of(2022, 9, 26, 13, 30, 15), Duration.ofMinutes(20));

        task1.setTaskId(1);
        manager.addTaskToHistory(task1);

        Epic epic1 = new Epic("Тренировка1", TaskStatus.IN_PROGRESS, "Тренировка1");

        epic1.setTaskId(2);
        manager.addTaskToHistory(epic1);


        Subtask subTask1 = new Subtask("Прийти в зал1", TaskStatus.NEW, "переодеться1", 2,
                LocalDateTime.of(2022, 9, 27, 13, 30, 15), Duration.ofMinutes(60));

        subTask1.setTaskId(3);
        manager.addTaskToHistory(subTask1);

        assertEquals(3, manager.getHistory().size());
    }

    @Test
    void getEmptyHistoryListTest() {
        assertEquals(0, manager.getHistory().size());
    }

    @Test
    void returnDuplicateHistoryListTest() {
        Task task1 = new Task("Спринт2", TaskStatus.NEW, "учу2",
                LocalDateTime.of(2022, 9, 26, 13, 30, 15), Duration.ofMinutes(20));

        task1.setTaskId(1);
        manager.addTaskToHistory(task1);

        Epic epic1 = new Epic("Тренировка1", TaskStatus.IN_PROGRESS, "Тренировка1");

        epic1.setTaskId(1);
        manager.addTaskToHistory(epic1);


        Subtask subTask1 = new Subtask("Прийти в зал1", TaskStatus.NEW, "переодеться1", 2,
                LocalDateTime.of(2022, 9, 27, 13, 30, 15), Duration.ofMinutes(60));

        subTask1.setTaskId(1);
        manager.addTaskToHistory(subTask1);

        assertEquals(1, manager.getHistory().size());
    }
}