
import managers.FileBackedTasksManager;
import managers.HistoryManager;
import managers.InMemoryHistoryManager;
import managers.TaskManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.Epic;
import tasks.Task;
import tasks.TaskStatus;
import tasks.TaskTypes;


import java.io.File;
import java.io.FileNotFoundException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FileBackedTasksManagerTest extends TaskManagerTest {

    private final static String PATH = "resources\\data.csv";


    @BeforeEach
    public void beforeEach() {
        manager = new FileBackedTasksManager();

    }


    @Test
    public void SaveAndLoadAnEmptyTaskListTest() throws FileNotFoundException {
        Task task1 = new Task("Спринт1", TaskStatus.NEW, "учу1",
                LocalDateTime.of(2020, 9, 25, 13, 30, 15), Duration.ofMinutes(20));
        manager.saveTask(task1);

        assertEquals(1, manager.getTaskList().size());

        manager.removeWithId(task1.getTaskId());

        FileBackedTasksManager load = FileBackedTasksManager.loadFromFile(PATH);

        assertEquals(List.of(), load.getTaskList());
    }

    @Test
    public void SaveAndLoadEpicWithoutSubtasksTest() throws FileNotFoundException {
        Epic epic1 = new Epic(1, "Тренировка1", "Тренировка1", TaskStatus.DONE, TaskTypes.EPIC);


        List<Task> epicList = new ArrayList<>(List.of(epic1));

        manager.saveEpic(epic1);

        FileBackedTasksManager loader = FileBackedTasksManager.loadFromFile(PATH);

        assertEquals(epicList.size(), loader.getEpicList().size());
        assertEquals(List.of(), loader.getSubtaskList());
    }

    @Test
    public void SaveAndLoadEmptyHistoryListTest() throws FileNotFoundException {
        Task task1 = new Task("Спринт1", TaskStatus.NEW, "учу1", LocalDateTime.of(2022, 9, 25, 13, 30, 15), Duration.ofMinutes(20));
        Task task2 = new Task("Спринт2", TaskStatus.NEW, "учу2", LocalDateTime.of(2022, 9, 26, 13, 30, 15), Duration.ofMinutes(20));

        manager.saveTask(task1);
        manager.saveTask(task2);

        manager.getTask(task1.getTaskId());
        manager.getTask(task2.getTaskId());

        assertEquals(2, manager.getTaskList().size());
        assertEquals(2, manager.getHistory().size());

        manager.removeAllTasks();
        FileBackedTasksManager loader = FileBackedTasksManager.loadFromFile(PATH);

        assertEquals(0, loader.getTaskList().size());
        assertEquals(0, loader.getHistory().size());
    }
}