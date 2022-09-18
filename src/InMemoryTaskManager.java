import java.util.HashMap;
import java.util.List;


public class InMemoryTaskManager implements TaskManager {



        protected final HistoryManager historyManager = Managers.getDefaultHistory();

        static int idCounter = 0;



        HashMap<Integer, Task> taskMap = new HashMap<>();
        HashMap<Integer, Epic> epicMap = new HashMap<>();
        HashMap<Integer, Subtask> subtaskMap = new HashMap<>();



    public static int getIdCounter() {
        return idCounter;
    }

    public static void setIdCounter(int idCounter) {
        InMemoryTaskManager.idCounter = idCounter;
    }




        @Override
        public List<Task> getHistory() {
           return historyManager.getHistory();
         }



        @Override
        public void saveTask(Task task) {
            task.setTaskId(++idCounter);
            if(task.getTaskStatus().equals(TaskStatus.NEW)
                    || task.getTaskStatus().equals(TaskStatus.DONE)
                    || task.getTaskStatus().equals(TaskStatus.IN_PROGRESS)){
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
                subtaskMap.put(subtask.getTaskId(), subtask);
                getEpic(subtask.getEpicId()).getSubtasksId().add(subtask.getTaskId());
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



