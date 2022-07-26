package managers;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.ArrayList;
import exception.ManagerSaveException;
import tasks.*;



public class FileBackedTasksManager extends InMemoryTaskManager {


    public static void main(String[] args) throws FileNotFoundException {
        FileBackedTasksManager manager = Managers.getDefaultFileManager();


        Task task1 = new Task("Спринт1", TaskStatus.NEW, "учу1", LocalDateTime.of(2022, 9, 25, 13, 30, 15), Duration.ofMinutes(20));
        Task task2 = new Task("Спринт2", TaskStatus.NEW, "учу2", LocalDateTime.of(2022, 9, 26, 13, 30, 15), Duration.ofMinutes(20));

        Epic epic1 = new Epic("Тренировка1", TaskStatus.DONE, "Тренировка1");
        Epic epic2 = new Epic("Тренировка2", TaskStatus.NEW, "Тренировка2");

        Subtask subTask1 = new Subtask("Прийти в зал1", TaskStatus.DONE, "переодеться1", 3, LocalDateTime.of(2022, 9, 27, 13, 30, 15), Duration.ofMinutes(60));
        Subtask subTask2 = new Subtask("Прийти в зал2", TaskStatus.DONE, "переодеться2", 3, LocalDateTime.of(2022, 9, 27, 14, 30, 15), Duration.ofMinutes(50));


        manager.saveTask(task1);
        manager.saveTask(task2);


        manager.saveEpic(epic1);
        manager.saveEpic(epic2);

        manager.saveSubtask(subTask1);
        manager.saveSubtask(subTask2);




        manager.getTask(1);
        manager.getTask(2);


        manager.getEpic(3);
        manager.getEpic(4);

        manager.getSubtask(5);
        manager.getSubtask(6);


        FileBackedTasksManager fileBackedTasksManager2 = FileBackedTasksManager.loadFromFile("resources\\data.csv");
        for(Task task : fileBackedTasksManager2.taskMap.values()) {
              System.out.println(task);
              }
        for(Task task : fileBackedTasksManager2.epicMap.values()) {
            System.out.println(task);
        }
        for(Task task : fileBackedTasksManager2.subtaskMap.values()) {
            System.out.println(task);
        }

        System.out.println("Печатаем историю");


                for (Task task : fileBackedTasksManager2.getHistory()) {
            System.out.println(task);
        }
    }


    private final static String PATH = "resources\\data.csv";



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

    public static int maxIdCounter(int id){
        int maxId = 0;
        if (maxId < id){
            maxId = id;
        }
        return maxId;
    }




    public static FileBackedTasksManager loadFromFile(String file) throws FileNotFoundException {

        FileBackedTasksManager tasksManager = new FileBackedTasksManager();
        List<Integer> historyList;
        Map<Integer, Task> historyMap = new HashMap<>();
        try {
            String lines = Files.readString(Path.of(file));
            String[] separatedLines = lines.split("\n");
            int maxId = 0;
            for(int i = 1; i < separatedLines.length - 2; i++){
                String taskLine = separatedLines[i];
                String[] taskContent = taskLine.split(",");
                String typeTask = taskContent[1];

                switch (typeTask){
                    case "TASK" :
                        Task task = taskFromString(taskContent);
                        tasksManager.taskMap.put(task.getTaskId(), task);
                        historyMap.put(task.getTaskId(), task);
                        maxId = maxIdCounter(task.getTaskId());

                        break;
                    case "EPIC" :
                        Epic epic = (Epic) taskFromString(taskContent);
                        tasksManager.epicMap.put(epic.getTaskId(), epic);
                        historyMap.put(epic.getTaskId(), epic);
                        maxId = maxIdCounter(epic.getTaskId());

                        break;
                    case "SUBTASK" :
                        Subtask subtask = (Subtask) taskFromString(taskContent);
                        tasksManager.subtaskMap.put(subtask.getTaskId(), subtask);
                        historyMap.put(subtask.getTaskId(), subtask);
                        maxId = maxIdCounter(subtask.getTaskId());

                        break;
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
            } tasksManager.idCounter = maxId;

        } catch (IOException e) {
            throw new FileNotFoundException("Невозможно прочитать файл");
        }
        return tasksManager;
    }


    public static Task taskFromString(String[] taskString) {
        int taskId = Integer.parseInt(taskString[0]);
        TaskTypes taskType = TaskTypes.valueOf(taskString[1]);
        String taskName = taskString[2];
        TaskStatus taskStatus = TaskStatus.valueOf(taskString[3]);
        String taskDescription = taskString[4];
        LocalDateTime startTime = null;
      if (!taskString[5].equals("null")) {
            startTime = LocalDateTime.parse(taskString[5]);
        }
        Duration duration = null;
        if (!taskString[6].equals("null")) {
            duration = Duration.parse(taskString[6]);
        }

        switch (taskType) {
            case TASK:
                Task task = new Task(taskId, taskName, taskDescription, taskStatus, taskType, startTime, duration);
                return task;
            case EPIC:
                Epic epic = new Epic(taskId, taskName, taskDescription, taskStatus, taskType);
                return epic;
            case SUBTASK:
                int taskEpicId = Integer.parseInt(taskString[7]);
                Subtask subTask = new Subtask(taskId, taskName, taskStatus, taskDescription, taskType, startTime, duration, taskEpicId);
                return subTask;
        }
        return null;
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
        String startTime = String.valueOf(task.getStartTime());
        String duration = String.valueOf(task.getDuration());


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
        return String.format("%s,%s,%s,%s,%s,%s,%s,%s\n", id, type, name, status, description, startTime, duration, epic);
    }



        public void save () {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(PATH))) {
                String historyId;
                writer.write("id,type,name,status,description,epic,startTime,duration,entTime\n");

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

                for (Task task : getHistory()){
                    historyId = task.getTaskId() + ",";
                    writer.write(historyId);
                }

            } catch (IOException e) {
                throw new ManagerSaveException("Ошибка записи файла");
            }
        }

    }

