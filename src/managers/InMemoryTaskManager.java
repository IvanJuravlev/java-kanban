package managers;

import tasks.Epic;
import tasks.Subtask;
import tasks.Task;
import tasks.TaskStatus;
import exception.ManagerSaveException;

import java.util.*;
import java.time.LocalDateTime;
import java.time.Duration;


public class InMemoryTaskManager implements TaskManager {



        protected static final HistoryManager historyManager = Managers.getDefaultHistory();

        protected int idCounter = 0;



        HashMap<Integer, Task> taskMap = new HashMap<>();
        HashMap<Integer, Epic> epicMap = new HashMap<>();
        HashMap<Integer, Subtask> subtaskMap = new HashMap<>();

        @Override
        public HashMap<Integer, Task> getTaskMap(){
            return taskMap;
        }

    protected Set<Task> getPrioritizedTasks = new TreeSet<>((o1, o2) -> {
        if (o1.getStartTime() == null && o2.getStartTime() == null) {
            return o1.getTaskId() - o2.getTaskId();
        } if (o1.getStartTime() == null){
            return 1;
        } if (o2.getStartTime() == null){
            return -1;
        } if (o1.getStartTime().isAfter(o2.getStartTime())) {
            return 1;
        } if (o1.getStartTime().isBefore(o2.getStartTime())){
            return -1;
        } if (o1.getStartTime().isEqual(o2.getStartTime())){
            return o1.getTaskId() - o2.getTaskId();
        }
        return 0;
    });



    public  int getIdCounter() {
        return idCounter;
    }

//    public static void setIdCounter(int idCounter) {
//        this.idCounter = idCounter;
//    }




        @Override
        public List<Task> getHistory() {
           return historyManager.getHistory();
         }



    public Map<Integer, Subtask> getSubtasks(){
        return subtaskMap;
    }




        @Override
        public void saveTask(Task task) {
            task.setTaskId(++idCounter);
            if(task.getTaskStatus().equals(TaskStatus.NEW)
                    || task.getTaskStatus().equals(TaskStatus.DONE)
                    || task.getTaskStatus().equals(TaskStatus.IN_PROGRESS)){
                getTaskEndTime(task);
                getPrioritizedTasks.add(task);
                taskMap.put(task.getTaskId(), task);
            } else
                System.out.println("Не корректный формат статуса задачи");
        }
        @Override
        public void saveEpic(Epic epic) {
            epic.setTaskId(++idCounter);
            if (epic.getEpicStatus().equals(TaskStatus.NEW)
                    || epic.getEpicStatus().equals(TaskStatus.DONE)
                    || epic.getEpicStatus().equals(TaskStatus.IN_PROGRESS)){
                getEpicTimesAndDuration(epic);
                epicMap.put(epic.getTaskId(), epic);
            } else {
                System.out.println("Не корректный формат статуса задачи");
            }
        }

        @Override
        public void saveSubtask(Subtask subtask) {
            subtask.setTaskId(++idCounter);
            if (subtask.getSubtaskStatus().equals(TaskStatus.NEW)
                    || subtask.getSubtaskStatus().equals(TaskStatus.DONE)
                    || subtask.getSubtaskStatus().equals(TaskStatus.IN_PROGRESS)){
                getSubtaskEndTime(subtask);
                getPrioritizedTasks.add(subtask);
                subtaskMap.put(subtask.getTaskId(), subtask);
                getEpic(subtask.getEpicId()).getSubtasksId().add(subtask.getTaskId());
                changeEpicStatus(subtask.getEpicId());

            } else {
                System.out.println("Не корректный формат статуса задачи");
            }
        }

    @Override
    public void getSubtaskEndTime(Subtask subtask) {
        if (subtask.getStartTime() == null || subtask.getDuration() == null) return;
        LocalDateTime endTime = subtask.getStartTime().plus(subtask.getDuration());
        subtask.setEndTime(endTime);
        if (epicMap.containsKey(subtask.getEpicId())) {
            getEpicTimesAndDuration(epicMap.get(subtask.getEpicId()));
        }
    }

    @Override
    public void getTaskEndTime(Task task) {
        if (task.getStartTime() == null || task.getDuration() == null) return;
        LocalDateTime endTime = task.getStartTime().plus(task.getDuration());
        task.setEndTime(endTime);
    }

    @Override
    public void getEpicTimesAndDuration(Epic epic) {
        if (epic.getSubtasksId().isEmpty()) {
            return;
        }
        LocalDateTime startTime;
        LocalDateTime endTime;
        startTime = LocalDateTime.of(2000, 1, 1, 1, 1, 1);
        endTime = LocalDateTime.of(3000, 1, 1, 1, 1, 1);
        epic.setStartTime(startTime);
        epic.setEndTime(endTime);
        for (Integer id : epic.getSubtasksId()) {
            if (subtaskMap.get(id).getStartTime() != null && subtaskMap.get(id).getStartTime().isBefore(startTime)) {
                startTime = subtaskMap.get(id).getStartTime();
            }
            if (subtaskMap.get(id).getStartTime() != null && subtaskMap.get(id).getEndTime().isAfter(endTime)) {
                endTime = subtaskMap.get(id).getEndTime();
            }
        }
        epic.setStartTime(startTime);
        epic.setEndTime(endTime);
        epic.setDuration(Duration.between(epic.getStartTime(), epic.getEndTime()));
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
                historyManager.remove(id);
            } else if (epicMap.containsKey(id)){
                List<Integer> epicSubtasks = epicMap.get(id).getSubtasksId();
                for(int subtaskId  : epicSubtasks){
                       subtaskMap.remove(subtaskId);
                       historyManager.remove(subtaskId);
                }
                epicMap.remove(id);
                historyManager.remove(id);

            } else if (subtaskMap.containsKey(id)) {
                subtaskMap.remove(id);
                historyManager.remove(id);
            } else System.out.println("такого ID не существует");
        }
        @Override
        public Epic getEpic(int id){
            Epic epic = null;
            if(epicMap.containsKey(id)){
                for(Integer newId : epicMap.keySet()){
                    if(id == newId){
                        epic = epicMap.get(id);
                        historyManager.addTaskToHistory(epicMap.get(id));

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
                        historyManager.addTaskToHistory(subtaskMap.get(id));
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
                        historyManager.addTaskToHistory(taskMap.get(id));
                    }
                }
            }
            return task;
        }

    @Override
    public void timeIntersection() {
        LocalDateTime checkTime = null;
        boolean flagCheckTimeIsEmpty = true;
        for (Task task : getPrioritizedTasks) {
            if (flagCheckTimeIsEmpty) {
                checkTime = task.getEndTime();
                flagCheckTimeIsEmpty = false;
            } else if (task.getStartTime() != null) {
                if (task.getStartTime().isBefore(checkTime)) {
                    throw new ManagerSaveException("Найдено пересечение времени задач, проверьте корректность данных");
                }
                if (task.getStartTime().isAfter(checkTime) || task.getStartTime().isEqual(checkTime)) {
                    checkTime = task.getEndTime();
                }
            }
        }
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
                if (getEpic(id).getSubtasksId().contains(subtasks)){
                    if(subtaskMap.get(subtasks).getSubtaskStatus().equals("NEW")){
                        countNew++;
                    }
                }
            }
            for(Integer subtasks : subtaskMap.keySet()) {
                if (getEpic(id).getSubtasksId().contains(subtasks)){
                    if(subtaskMap.get(subtasks).getSubtaskStatus().equals("DONE")){
                        countDone++;
                    }
                }
            }
            if (getEpic(id).getSubtasksId().isEmpty() || countNew == getEpic(id).getSubtasksId().size()){
                getEpic(id).setEpicStatus(TaskStatus.NEW);
            } else if(countDone == getEpic(id).getSubtasksId().size()){
                getEpic(id).setEpicStatus(TaskStatus.DONE);
            } else if(countNew != getEpic(id).getSubtasksId().size() && countDone != getEpic(id).getSubtasksId().size()){
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



