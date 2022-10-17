import managers.Managers;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import servers.HTTPTaskManager;
import servers.KVServer;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;
import tasks.TaskStatus;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HTTPTaskManagerTest {


    private HTTPTaskManager manager;
    private static KVServer kvServer;

    @BeforeAll
    public static void beforeAll() throws IOException {
        kvServer = new KVServer();
        kvServer.start();

    }

    @BeforeEach
    public void beforeEach() {
        manager = (HTTPTaskManager) Managers.getDefault();
    }

    @Test
    public void saveAndRestoreEmptyTaskListTest() {
        Task task1 = new Task("Спринт1", TaskStatus.NEW, "учу1",
                LocalDateTime.of(2020, 9, 25, 13, 30, 15), Duration.ofMinutes(20));
        manager.saveTask(task1);

        assertEquals(1, manager.getTaskList().size());

        manager.removeWithId(task1.getTaskId());

        manager = (HTTPTaskManager) Managers.getDefault();

        assertEquals(List.of(), manager.getTaskList());
    }

    @Test
    public void saveAndRestoreEpicTest() {
        Epic epic1 = new Epic("Тренировка1", TaskStatus.IN_PROGRESS, "Тренировка1");

        manager.saveEpic(epic1);

        manager = (HTTPTaskManager) Managers.getDefault();
        manager.loadFromServer();

        assertEquals(1, manager.getEpicList().size());
        assertEquals(List.of(), manager.getSubtaskList());
    }

    @Test
    public void saveAndRestoreHistoryListTest() {

        Task task1 = new Task("Спринт1", TaskStatus.NEW, "учу1",
                            LocalDateTime.of(2020, 9, 25, 13, 30, 15), Duration.ofMinutes(20));
        Task task2 = new Task("Спринт2", TaskStatus.NEW, "учу2",
                LocalDateTime.of(2020, 9, 25, 13, 55, 15), Duration.ofMinutes(20));

        manager.saveTask(task1);
        manager.saveTask(task2);

        manager.getTask(task1.getTaskId());
        manager.getTask(task2.getTaskId());

        assertEquals(2, manager.getTaskList().size());
        assertEquals(2, manager.getHistory().size());

        manager.removeAllTasks();
        manager = (HTTPTaskManager) Managers.getDefault();

        assertEquals(0, manager.getTaskList().size());
        assertEquals(0, manager.getHistory().size());
    }

    @Test
    public void saveAndLoadTasksTest() {

        Task task2 = new Task("Спринт2", TaskStatus.NEW, "учу2",
                LocalDateTime.of(2020, 9, 25, 13, 55, 15), Duration.ofMinutes(20));
        Epic epic1 = new Epic("Тренировка1", TaskStatus.IN_PROGRESS, "Тренировка1");
        Subtask subTask1 = new Subtask("Прийти в зал1", TaskStatus.NEW, "переодеться1", 2,
                LocalDateTime.of(2022, 9, 26, 21, 0), Duration.ofMinutes(30));
        Subtask subTask2 = new Subtask("Прийти в зал2", TaskStatus.NEW, "переодеться2", 2,
                LocalDateTime.of(2022, 9, 26, 23, 0), Duration.ofMinutes(30));
        manager.saveTask(task2);
        manager.saveEpic(epic1);
        manager.saveSubtask(subTask1);
        manager.saveSubtask(subTask2);

        assertFalse(manager.getTaskList().isEmpty());
        assertFalse(manager.getEpicList().isEmpty());
        assertFalse(manager.getSubtaskList().isEmpty());

        manager = (HTTPTaskManager) Managers.getDefault();

        assertTrue(manager.getTaskList().isEmpty());
        assertTrue(manager.getEpicList().isEmpty());
        assertTrue(manager.getSubtaskList().isEmpty());

        manager.loadFromServer();

        assertFalse(manager.getTaskList().isEmpty());
        assertFalse(manager.getEpicList().isEmpty());
        assertFalse(manager.getSubtaskList().isEmpty());
    }

    @AfterAll
    public static void afterAll() {
        kvServer.stop();
    }
}