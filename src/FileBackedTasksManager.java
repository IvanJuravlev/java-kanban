import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class FileBackedTasksManager extends InMemoryTaskManager {

    final FileBackedTasksManager tasksManager = new FileBackedTasksManager();

    private final static String PATH = "resources\\data.csv";  // тут надо с адресом поработать



    @Override
    public void saveTask(Task task) {
        super.saveTask(task);
           save();

    }

    @Override
    public void saveEpic(Epic epic) {
        super.saveEpic(epic);
            save();
    }

    @Override
    public void saveSubtask(Subtask subtask) {
        super.saveSubtask(subtask);
           save();
    }

    private static Task taskFromString(String[] taskString){
        int id = Integer.parseInt(taskString[0]);
        TaskTypes type = Enum.valueOf(TaskTypes.class, taskString[1]);
        String taskName = taskString[2];
        TaskStatus status = Enum.valueOf(TaskStatus.class, taskString[3]);
        String description =taskString[4];

        Task task = new Task(id, taskName, description, status, type);
        return task;
    }

    private static Epic epicFromString(String[] taskString){
        int id = Integer.parseInt(taskString[0]);
        TaskTypes type = Enum.valueOf(TaskTypes.class, taskString[1]);
        String taskName = taskString[2];
        TaskStatus status = Enum.valueOf(TaskStatus.class, taskString[3]);
        String description =taskString[4];


        Epic epic = new Epic(id, taskName, status, description, type);
        return epic;
    }

    private static Subtask subtaskFromString(String[] taskString){
        int id = Integer.parseInt(taskString[0]);
        TaskTypes type = Enum.valueOf(TaskTypes.class, taskString[1]);
        String taskName = taskString[2];
        TaskStatus status = Enum.valueOf(TaskStatus.class, taskString[3]);
        String description =taskString[4];
        int epicId = Integer.parseInt(taskString[5]);

        Subtask subtask = new Subtask(id, taskName, status, description, type, epicId);
        return subtask;
    }


    @Override
    public void removeAllTasks() {
        super.removeAllTasks();
           save();
    }

    @Override
    public void removeWithId(int id) {
        super.removeWithId(id);
          save();
    }

    @Override
    public Task getTask(int id) {
        Task task = super.getTask(id);
           save();
        return task;
    }

    @Override
    public Epic getEpic(int id) {
        Epic epic = super.getEpic(id);
         save();
        return epic;
    }

    @Override
    public Subtask getSubtask(int id) {
        Subtask subtask = super.getSubtask(id);
          save();
        return subtask;
    }


    public void loadFromFile() throws FileNotFoundException {
        List<Integer> historyList = new ArrayList<>();
        Map<Integer, Task> historyMap = new HashMap<>();
        try {
            String lines = Files.readString(Path.of(PATH));
            String[] separatedLines = lines.split("\n");
            int maxId = 0;
            for(int i = 1; i < separatedLines.length; i++){
                String taskLine = separatedLines[i];
                String[] taskContent = taskLine.split(",");
                String typeTask = taskContent[1];

                switch (typeTask){
                    case "TASK" :
                        Task task = taskFromString(taskContent);
                        tasksManager.taskMap.put(task.getTaskId(), task);
                        historyMap.put(task.getTaskId(), task);
                        if(task.getTaskId() > maxId){
                            maxId = task.getTaskId();
                        }
                        break;
                    case "EPIC" :
                        Epic epic = epicFromString(taskContent);
                        tasksManager.epicMap.put(epic.getTaskId(), epic);
                        historyMap.put(epic.getTaskId(), epic);
                        if(epic.getTaskId() > maxId){
                            maxId = epic.getTaskId();
                        }
                        break;
                    case "SUBTASK" :
                        Subtask subtask = subtaskFromString(taskContent);
                        tasksManager.subtaskMap.put(subtask.getTaskId(), subtask);
                        historyMap.put(subtask.getTaskId(), subtask);
                        if(subtask.getTaskId() > maxId){
                            maxId = subtask.getTaskId();
                        }
                }
                if(taskLine.isEmpty()){
                    taskLine = separatedLines[i + 1];
                    historyList = historyFromString(taskLine);
                    for (Integer id : historyList) {
                        for (Task task : historyMap.values()){
                            if(task.getTaskId() == id){
                                historyManager.addTaskToHistory(task);
                            }
                        }
                    }

                }
            } setIdCounter(maxId);

        } catch (IOException e) {
            throw new FileNotFoundException("Невозможно прочитать файл");
        }

    }

    public static List<Integer> historyFromString(String history) {
        List<Integer> historyList = new ArrayList<>();
        String[] historyValue = history.split(",");
        for (String line : historyValue) {
            historyList.add(Integer.parseInt(line));
        }
        return historyList;
    }

    private String taskToString(Task task) {
        String id = String.valueOf(task.getTaskId());
        String type;
        String name = task.getTaskName();
        String status = String.valueOf(task.getTaskStatus());
        String description = task.getTaskDescription();
        String epic;

        if (task instanceof Epic) {
            type = TaskTypes.EPIC.name();
            epic = "";
        } else if (task instanceof Subtask) {
            type = TaskTypes.SUBTASK.name();
            epic = String.valueOf(((Subtask) task).getEpicId());
        } else {
            type = TaskTypes.TASK.name();
            epic = "";
        }

        return String.join(",", id, type, name, status, description, epic) + System.lineSeparator();
    }





        public void save () {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(PATH))) {
                String historyId;
                StringBuilder stringBuilder = new StringBuilder();
                writer.write("id,type,name,status,description,epic\n");

                for (Task task : taskMap.values()){
                    writer.write(taskToString(task));
                }
                for (Task epic : epicMap.values()){
                    writer.write(taskToString(epic));
                }
                for (Task subtask : subtaskMap.values()){
                    writer.write(taskToString(subtask));
                }

                writer.write("\n\n");

                for (Task task : getHistory()){
                    historyId = task.getTaskId() + ",";
                    writer.write(historyId);
                }



            } catch (IOException e) {
                throw new ManagerSaveException("Ошибка записи файла");
            }
        }
    }

