import java.util.HashMap;

public class TaskManager {

    int generatorId = 0;

    HashMap<Integer, Task> taskMap = new HashMap<>();
    HashMap<Integer, Epic> epicMap = new HashMap<>();
    HashMap<Integer, Subtask> subtaskMap = new HashMap<>();


    public void saveTask(Task task) {
        task.setTaskId(++generatorId);
        if(task.getTaskStatus().equals("NEW") || task.getTaskStatus().equals("DONE") || task.getTaskStatus().equals("IN PROGRESS")){
            taskMap.put(task.getTaskId(), task);
        } else
            System.out.println("Не корректный формат статуса задачи");
    }

    public void saveEpic(Epic epic) {
        epic.setEpicId(++generatorId);
        if (epic.getEpicStatus().equals("NEW") || epic.getEpicStatus().equals("DONE") || epic.getEpicStatus().equals("IN PROGRESS")){
            epicMap.put(epic.getEpicId(), epic);
        } else {
            System.out.println("Не корректный формат статуса задачи");
        }
    }

    public void saveSubtask(Subtask subtask) {
        subtask.setSubtaskId(++generatorId);
        if (subtask.getSubtaskStatus().equals("NEW") || subtask.getSubtaskStatus().equals("DONE") || subtask.getSubtaskStatus().equals("IN PROGRESS")){
            subtaskMap.put(subtask.getSubtaskId(), subtask);
            finedEpicWithId(subtask.getEpicId()).getSubtasksId().add(subtask.getSubtaskId());
            changeEpicStatus(subtask.getEpicId());

        } else {
            System.out.println("Не корректный формат статуса задачи");
        }
    }



    public void removeAllTasks(){
        taskMap.clear();
        epicMap.clear();
        subtaskMap.clear();
    }


    public void removeWithId(int id){
        if(taskMap.containsKey(id)){
                    taskMap.remove(id);
        } else if (epicMap.containsKey(id)){
                    epicMap.remove(id);
        } else if (subtaskMap.containsKey(id)) {
                    subtaskMap.remove(id);
        } else System.out.println("такого ID не существует");
    }

    public Epic finedEpicWithId(int id){
        Epic epic = null;
        if(epicMap.containsKey(id)){
            for(Integer newId : epicMap.keySet()){
                if(id == newId){
                    epic = epicMap.get(id);
                }
            }
        } return epic;
    }

    public Subtask finedSubtaskWithId(int id){
        Subtask subtask = null;
        if (subtaskMap.containsKey(id)) {
            for(Integer newId : subtaskMap.keySet()){
                if(newId == id){
                    subtask = subtaskMap.get(id);
                }
            }
        } return subtask;
    }


    public Task finedTaskWithId(int id){
        Task task = null;
        if(taskMap.containsKey(id)){
            for (Integer newId : taskMap.keySet()){
                if (newId == id) {
                    task = taskMap.get(id);
                }
            }
        }
        return task;
    }



    public void changeTask(Task task, int id) {
            for (Integer newId : taskMap.keySet()) {
                if (newId == id) {
                    taskMap.put(id, task);
                }
            }
        }

     public void changeEpic(Epic epic, int id) {
        for (Integer newId : epicMap.keySet()) {
            if (newId == id) {
                epicMap.put(id, epic);
            }
        }
     }

    public void changeSubtask(Subtask subtask, int id){
        for(Integer newId : subtaskMap.keySet()){
            if(newId == id){
                subtaskMap.put(id, subtask);
                changeEpicStatus(subtask.getEpicId());
            }
        }
    }


     public void changeEpicStatus(int id){
        int countNew = 0;
        int countDone = 0;

         for(Integer subtasks : subtaskMap.keySet()) {
             if (finedEpicWithId(id).subtasksId.contains(subtasks)){
                 if(subtaskMap.get(subtasks).getSubtaskStatus().equals("NEW")){
                     countNew++;
                 }
             }
         }
         for(Integer subtasks : subtaskMap.keySet()) {
             if (finedEpicWithId(id).subtasksId.contains(subtasks)){
                 if(subtaskMap.get(subtasks).getSubtaskStatus().equals("DONE")){
                     countDone++;
                 }
             }
         }
         if (finedEpicWithId(id).subtasksId.isEmpty() || countNew == finedEpicWithId(id).subtasksId.size()){
             finedEpicWithId(id).setEpicStatus("NEW");
         } else if(countDone == finedEpicWithId(id).subtasksId.size()){
             finedEpicWithId(id).setEpicStatus("DONE");
         } else {
             finedEpicWithId(id).setEpicStatus("IN PROGRESS");
         }
    }



    public void printAllTasks(){
        printTask();
        printEpic();
        printSubtask();
    }

    public void printSubtask(){
        for(Subtask name: subtaskMap.values()){
            System.out.println(name.getSubtaskName());
        }
    }

    public void printTask(){
        for(Task name: taskMap.values()) {
            System.out.println(name.getTaskName());
        }
    }

    public void printEpic(){
        for(Epic name: epicMap.values()){
            System.out.println(name.getEpicName());
        }
    }

}






