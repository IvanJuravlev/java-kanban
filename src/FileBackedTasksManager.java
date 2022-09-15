import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;

public class FileBackedTasksManager extends InMemoryTaskManager {

    private final static String PATH = "resourses\\data.csv";  // тут надо с адресом поработать

    FileBackedTasksManager manager = Managers.getDefaultFileManager();


    @Override
    public void saveTask(Task task){
        super.saveTask(task);
        save();
       // return id;
    }

    @Override
    public void saveEpic(Epic epic){
        super.saveEpic(epic);
        save();
    }

    @Override
    public void saveSubtask(Subtask subtask){
        super.saveSubtask(subtask);
        save();
    }



    @Override
    public void removeAllTasks(){
        super.removeAllTasks();
        save();
    }

    @Override
    public void removeWithId(int id){
        super.removeWithId(id);
        save();
    }

    @Override
    public Task getTask(int id){
        Task task = super.getTask(id);
        save();
        return task;
    }

    @Override
    public Epic getEpic(int id){
        Epic epic = super.getEpic(id);
        save();
        return epic;
    }

    @Override
    public Subtask getSubtask(int id){
        Subtask subtask = super.getSubtask(id);
        save();
        return subtask;
    }

     public void loadFromFile(){
        try{
            String[] lines = Files.readString(Path.of(PATH));
            String[] separatedLines = lines.split("\n");


        }

     }

     public void toString(){

     }










    public void save(String file){
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(file))){

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
