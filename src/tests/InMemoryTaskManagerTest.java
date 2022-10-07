import managers.InMemoryTaskManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.Task;


import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest extends TaskManagerTest<InMemoryTaskManager> {


    private static final File PATH_FILE = new File("resources/data.csv");


    @BeforeEach
    public void beforeEach(){
        manager = new InMemoryTaskManager();
    }


    @Test
    public void shouldSaveAndRestoreAnEmptyTaskList() {
        Task task = new Task("Task", "Description Task");
        manager.createTask(task);

        assertEquals(1, manager.getTaskList().size());

        manager.removeTask(task.getId());

        FileBackedTasksManager loader = FileBackedTasksManager.loadFromFile(PATH_FILE);

        assertEquals(List.of(), loader.getTaskList());
    }

    @Test
    public void shouldSaveAndRestoreEpicWithoutSubtasks() {
        Epic epic = new Epic("Epic", "Description Epic");

        List<Task> epicList = new ArrayList<>(List.of(epic));

        manager.createEpic(epic);

        FileBackedTasksManager loader = FileBackedTasksManager.loadFromFile(PATH_FILE);

        assertEquals(epicList.size(), loader.getEpicList().size());
        assertEquals(List.of(), loader.getSubTaskList());
    }

    @Test
    public void shouldSaveAndRestoreAnEmptyHistoryList() {
        Task task = new Task("Task", "Description Task");
        Task task2 = new Task("Task2", "Description Task2");

        manager.createTask(task);
        manager.createTask(task2);

        manager.getTask(task.getId());
        manager.getTask(task2.getId());

        assertEquals(2, manager.getTaskList().size());
        assertEquals(2, manager.getHistory().size());

        manager.removeAllTask();
        FileBackedTasksManager loader = FileBackedTasksManager.loadFromFile(PATH_FILE);

        assertEquals(0, loader.getTaskList().size());
        assertEquals(0, loader.getHistory().size());
    }



}