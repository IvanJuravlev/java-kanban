import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryTaskManager implements TaskManager {


        int generatorId = 0;

        HashMap<Integer, Task> taskMap = new HashMap<>();
        HashMap<Integer, Epic> epicMap = new HashMap<>();
        HashMap<Integer, Subtask> subtaskMap = new HashMap<>();

        List<Task> taskHistoryList = new ArrayList<>();



       @Override
        public void addTaskToHistory(Task task){
            if(taskHistoryList.size() == 10){
                taskHistoryList.remove(0);
                taskHistoryList.add(task);
                } else{
                taskHistoryList.add(task);
            }
        }


        @Override
        public List<Task> getHistory(){
            return taskHistoryList;
        }

        @Override
        public void saveTask(Task task) {
            task.setTaskId(++generatorId);
            if(task.getTaskStatus().equals("NEW") || task.getTaskStatus().equals("DONE") || task.getTaskStatus().equals("IN PROGRESS")){
                taskMap.put(task.getTaskId(), task);
            } else
                System.out.println("Не корректный формат статуса задачи");
        }
        @Override
        public void saveEpic(Epic epic) {
            epic.setEpicId(++generatorId);
            if (epic.getEpicStatus().equals("NEW") || epic.getEpicStatus().equals("DONE") || epic.getEpicStatus().equals("IN PROGRESS")){
                epicMap.put(epic.getEpicId(), epic);
            } else {
                System.out.println("Не корректный формат статуса задачи");
            }
        }

        @Override
        public void saveSubtask(Subtask subtask) {
            subtask.setSubtaskId(++generatorId);
            if (subtask.getSubtaskStatus().equals("NEW") || subtask.getSubtaskStatus().equals("DONE") || subtask.getSubtaskStatus().equals("IN PROGRESS")){
                subtaskMap.put(subtask.getSubtaskId(), subtask);
                getEpic(subtask.getEpicId()).getSubtasksId().add(subtask.getSubtaskId());
                changeEpicStatus(subtask.getEpicId());

            } else {
                System.out.println("Не корректный формат статуса задачи");
            }
        }


    @Override
        public void removeAllTasks(){
            taskMap.clear();
            epicMap.clear();
            subtaskMap.clear();
        }

        @Override
        public void removeWithId(int id){
            if(taskMap.containsKey(id)){
                taskMap.remove(id);
            } else if (epicMap.containsKey(id)){
                epicMap.remove(id);
            } else if (subtaskMap.containsKey(id)) {
                subtaskMap.remove(id);
            } else System.out.println("такого ID не существует");
        }
        @Override
        public Epic getEpic(int id){
            Epic epic = null;
            if(epicMap.containsKey(id)){
                for(Integer newId : epicMap.keySet()){
                    if(id == newId){
                        epic = epicMap.get(id);
                        addTaskToHistory(epicMap.get(id));
                    }
                }
            } return epic;
        }

        @Override
        public Subtask getSubtask(int id){
            Subtask subtask = null;
            if (subtaskMap.containsKey(id)) {
                for(Integer newId : subtaskMap.keySet()){
                    if(newId == id){
                        subtask = subtaskMap.get(id);
                        addTaskToHistory(subtaskMap.get(id));
                    }
                }
            } return subtask;
        }


        @Override
        public Task getTask(int id){
            Task task = null;
            if(taskMap.containsKey(id)){
                for (Integer newId : taskMap.keySet()){
                    if (newId == id) {
                        task = taskMap.get(id);
                        addTaskToHistory(taskMap.get(id));
                    }
                }
            }
            return task;
        }



        @Override
        public void changeTask(Task task, int id) {
            for (Integer newId : taskMap.keySet()) {
                if (newId == id) {
                    taskMap.put(id, task);
                }
            }
        }

        @Override
        public void changeEpic(Epic epic, int id) {
            for (Integer newId : epicMap.keySet()) {
                if (newId == id) {
                    epicMap.put(id, epic);
                }
            }
        }

        @Override
        public void changeSubtask(Subtask subtask, int id){
            for(Integer newId : subtaskMap.keySet()){
                if(newId == id){
                    subtaskMap.put(id, subtask);
                    changeEpicStatus(subtask.getEpicId());
                }
            }
        }


        @Override
        public void changeEpicStatus(int id){
            int countNew = 0;
            int countDone = 0;

            for(Integer subtasks : subtaskMap.keySet()) {
                if (getEpic(id).subtasksId.contains(subtasks)){
                    if(subtaskMap.get(subtasks).getSubtaskStatus().equals("NEW")){
                        countNew++;
                    }
                }
            }
            for(Integer subtasks : subtaskMap.keySet()) {
                if (getEpic(id).subtasksId.contains(subtasks)){
                    if(subtaskMap.get(subtasks).getSubtaskStatus().equals("DONE")){
                        countDone++;
                    }
                }
            }
            if (getEpic(id).subtasksId.isEmpty() || countNew == getEpic(id).subtasksId.size()){
                getEpic(id).setEpicStatus(TaskStatus.NEW);
            } else if(countDone == getEpic(id).subtasksId.size()){
                getEpic(id).setEpicStatus(TaskStatus.DONE);
            } else {
                getEpic(id).setEpicStatus(TaskStatus.IN_PROGRESS);
            }
        }


        @Override
        public void printAllTasks(){
            printTask();
            printEpic();
            printSubtask();
        }

        @Override
        public void printSubtask(){
            for(Subtask name: subtaskMap.values()){
                System.out.println(name.getSubtaskName());
            }
        }
        @Override
        public void printTask(){
            for(Task name: taskMap.values()) {
                System.out.println(name.getTaskName());
            }
        }
        @Override
        public void printEpic(){
            for(Epic name: epicMap.values()){
                System.out.println(name.getEpicName());
            }
        }

    }



