import java.util.*;
import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager{



    // private final CustomLinkedList<Task> historyList = new CustomLinkedList<>();

   // private List<Task> taskHistoryList = historyList.getTasks();
    private List<Task> taskHistoryList = new ArrayList<>();

    private final HashMap<Integer, Node<Task>> nodeHashMap = new HashMap<>();


    private  Node first;


    private  Node last;



        public List<Task> getTasks() {
            List<Task> taskArrayLinkedList = new ArrayList<>();
            Node<Task> currentNode = first;
            while (currentNode != null) {
                taskArrayLinkedList.add(currentNode.data);
                currentNode = currentNode.next;
            }
            return taskArrayLinkedList;
        }


        public void removeNode(Node node){
         // if (!nodeHashMap.isEmpty()) {
             // Node node = nodeHashMap.remove(id);

                if (node == null) {
                    return;
                }
                if (node.prev != null) {
                    node.prev.next = node.next;
                    if (node.next != null) {
                        last = node.prev;
                    } else {
                        node.next.prev = node.prev;
                    }
                } else {
                    first = node.next;
                    if (first == null) {
                        last = null;
                    } else {
                        first.prev = null;
                    }
                }
         // }
        }


       // @Override
        public void linkLast(Task task) {
            Node<Task> node = new Node<>(last, task, null);
            if (first == null) {
                first = node;
            } else {
                last.next = node;
            }
            last = node;
        }



    @Override
     public void addTaskToHistory(Task task){
        remove(task);
        linkLast(task);
        nodeHashMap.put(task.getTaskId(), last);

    }

    @Override
    public List<Task> getHistory(){
      //  taskHistoryList = historyList.getTasks();
        return getTasks();
    }

    @Override
    public void remove(Task task){
        if(nodeHashMap.containsKey(task.getTaskId())){
            removeNode(nodeHashMap.get(task.getTaskId()));
            nodeHashMap.remove(task.getTaskId());
        }

    }

}
