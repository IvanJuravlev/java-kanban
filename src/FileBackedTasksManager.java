import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FileBackedTasksManager extends InMemoryTaskManager {

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

    public void loadFromFile() {
        try {
            String lines = Files.readString(Path.of(PATH));
            String[] separatedLines = lines.split("\n");
            int maxId = 0;
            for(int i = 1; i < separatedLines.length; i++){
                String taskLine = separatedLines[i];
                String[] taskContent = taskLine.split(",");
                if (taskContent[1].equals("TASK")){



                }
            }
//            List<String> tasks = new ArrayList<>();
//            List<String> epics = new ArrayList<>();
//            List<String> subtasks = new ArrayList<>();
//            String historyLine = "";
//            boolean isTitle = true;
//            boolean isTask = true;
//            int maxId = 0;
//            int id;
//            for (int i = 0; i < lines.length(); i++) {

//            }


        } catch (IOException e) {
            throw new RuntimeException(e);
        }

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

