import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FileBackedTasksManager extends InMemoryTaskManager {

    private final static String PATH = "resources\\data.csv";  // тут надо с адресом поработать

    FileBackedTasksManager manager = Managers.getDefaultFileManager();


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
            List<String> tasks = new ArrayList<>();
            List<String> epics = new ArrayList<>();
            List<String> subtasks = new ArrayList<>();
            String historyLine = "";
            boolean isTitle = true;
            boolean isTask = true;
            int maxId = 0;
            int id;
            for (int i = 0; i < lines.length(); i++) {

            }


        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

//     public void taskToString(){
//        List<Task> tasksList = new ArrayList<>();
//        StringBuilder stringBuilder = new StringBuilder();
//        tasksList.addAll(taskMap.values());
//        tasksList.addAll(epicMap.values());
//        tasksList.addAll(subtaskMap.values());
//
//
//     }

    public String taskToString(Task task) {
        return String.format("%s,%s,%s,%s,%s,%s",
                task.getTaskId(),
                task.getTaskType(),
                task.getTaskName(),
                task.getTaskStatus(),
                task.getTaskDescription(), "");
    }


        public void save () {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(PATH))) {
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

                writer.write("\n");


            } catch (IOException e) {
                throw new ManagerSaveException("Ошибка записи файла");
            }
        }
    }

