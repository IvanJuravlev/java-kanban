package servers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import exception.ManagerSaveException;
import managers.FileBackedTasksManager;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;
import java.lang.reflect.Type;

import java.util.HashMap;
import java.util.List;

public class HTTPTaskManager extends FileBackedTasksManager {

    private final KVTaskClient kvTaskClient;
    private final Gson gson = new Gson();

    public HTTPTaskManager(String url) {
        this.kvTaskClient = new KVTaskClient(url);
    }

    @Override
    public void save() {
        try {
            kvTaskClient.put("tasks/task", gson.toJson(taskMap));
            kvTaskClient.put("tasks/epic", gson.toJson(epicMap));
            kvTaskClient.put("tasks/subtask", gson.toJson(subtaskMap));
            kvTaskClient.put("tasks/history", gson.toJson(getHistory()));
        } catch (Exception e) {
            throw new ManagerSaveException("Не удалось сохранить задачи");
        }
    }

    public void loadFromServer() {
        String tasksFromJson = kvTaskClient.load("tasks/task");
        if (tasksFromJson != null) {
            Type typeToken = new TypeToken<HashMap<Integer, Task>>() {
            }.getType();

            taskMap = gson.fromJson(tasksFromJson, typeToken);
            getPrioritizedTasks.addAll(taskMap.values());
        }

        String epicsFromJson = kvTaskClient.load("tasks/epic");
        if (epicsFromJson != null) {
            Type typeToken = new TypeToken<HashMap<Integer, Epic>>() {
            }.getType();

            epicMap = gson.fromJson(epicsFromJson, typeToken);
            getPrioritizedTasks.addAll(epicMap.values());
        }

        String subTasksFromJson = kvTaskClient.load("tasks/subtask");
        if (subTasksFromJson != null) {
            Type typeToken = new TypeToken<HashMap<Integer, Subtask>>() {
            }.getType();

            subtaskMap = gson.fromJson(subTasksFromJson, typeToken);
            getPrioritizedTasks.addAll(subtaskMap.values());
        }

        String historyFromJson = kvTaskClient.load("tasks/history");
        if (historyFromJson != null) {
            Type typeToken = new TypeToken<List<Task>>() {
            }.getType();

            List<Task> historyList = gson.fromJson(historyFromJson, typeToken);
            for (Task task : historyList) {
                historyManager.addTaskToHistory(task);
            }
        }
    }
}

