package managers;

import servers.HTTPTaskManager;

public class Managers {

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }

    public static TaskManager getDefault() {
        return new HTTPTaskManager("http://localhost:8078");
    }

    public static FileBackedTasksManager getDefaultFileManager(){
        return new FileBackedTasksManager();
    }
}
