import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class FileBackedTasksManager extends InMemoryTaskManager {

   // FileBackedTasksManager manager = new FileBackedTasksManager();

    public static void main(String[] args) throws FileNotFoundException {
        FileBackedTasksManager manager = Managers.getDefaultFileManager();

//================================================================================
        Task task1 = new Task("Спринт1", TaskStatus.NEW, "учу1");
        Task task2 = new Task("Спринт2", TaskStatus.NEW, "учу2");
        Task task3 = new Task("Спринт3", TaskStatus.NEW, "учу3");
        Task task4 = new Task("Спринт4", TaskStatus.NEW, "учу4");
        Task task5 = new Task("Спринт5", TaskStatus.NEW, "учу5");
        Task task6 = new Task("Спринт6", TaskStatus.NEW, "учу6");

        Epic epic1 = new Epic("Тренировка1", TaskStatus.NEW, "Тренировка1");
        Epic epic2 = new Epic("Тренировка2", TaskStatus.NEW, "Тренировка2");

        Subtask subTask1 = new Subtask("Прийти в зал1", TaskStatus.NEW, "переодеться1", 7);
        Subtask subTask2 = new Subtask("Прийти в зал2", TaskStatus.NEW, "переодеться2", 7);
        Subtask subTask3 = new Subtask("Прийти в зал3", TaskStatus.NEW, "переодеться3", 7);



        manager.saveTask(task1);
        manager.saveTask(task2);
        manager.saveTask(task3);
        manager.saveTask(task4);
        manager.saveTask(task5);
        manager.saveTask(task6);

        manager.saveEpic(epic1);
        manager.saveEpic(epic2);

        manager.saveSubtask(subTask1);
        manager.saveSubtask(subTask2);
        manager.saveSubtask(subTask3);





        manager.getTask(1);
        manager.getTask(2);
        manager.getTask(3);
        manager.getTask(4);
        manager.getTask(5);
        manager.getTask(6);

        manager.getEpic(7);
        manager.getEpic(8);


        manager.getSubtask(9);
        manager.getSubtask(10);
        manager.getSubtask(11);


      //  manager.saveTask(new Task("Погладить кота",TaskStatus.NEW,  "поймать его"));
//        manager.removeWithId(7);
//        manager.removeWithId(3);
//        manager.removeWithId(4);
//        manager.removeWithId(5);
//        manager.removeWithId(6);
        System.out.println(manager);
        //manager.loadFromFile();

        FileBackedTasksManager fileBackedTasksManager2 = new FileBackedTasksManager();
        fileBackedTasksManager2.loadFromFile("resources\\data.csv");
        System.out.println(fileBackedTasksManager2.getTask(1));
       // fileBackedTasksManager2.printAllTasks();
    }



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
       // int epicId;
     //   if(taskString[5].isEmpty()){

       // }
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




    public FileBackedTasksManager loadFromFile(String file) throws FileNotFoundException {

        FileBackedTasksManager tasksManager = new FileBackedTasksManager();
        List<Integer> historyList = new ArrayList<>();
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

        return tasksManager;



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
        return String.format("%s,%s,%s,%s,%s,%s\n", id, type, name, status, description, epic);


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

