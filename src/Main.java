import java.util.Scanner;
import java.util.TreeSet;

public class Main {

    public static void main(String[] args) {
       /* Scanner scanner = new Scanner(System.in);
        printMenu();
          int userInput = scanner.nextInt();
          TaskManager taskManager = new TaskManager();

        // Task mainTask = new Task();
//        TaskManager taskManager = new TaskManager();
//        Task task = new Task("Задача", "В работе", "Описание");
//        System.out.println(task.taskId);
//        // taskManager.generateId(task);
//        taskManager.saveTask(task);
//        System.out.println(taskManager.taskMap);
//        new Task("Задача1", "В работе1", "Описание1");
//        // taskManager.generateId(task);
//        taskManager.saveTask(task);
//        System.out.println(taskManager.taskMap);



        while (userInput != 0) {
            switch (userInput) {
                case 1:
                    scanner.nextLine();
                    System.out.println("введите задачу");
                    String taskName = scanner.nextLine();
                    String taskStatus = scanner.nextLine();
                    String taskDescr = scanner.nextLine();
                    Task task = new Task(taskName, taskStatus, taskDescr);
                    taskManager.saveTask(task);
//                    SaveTask saveTask = new SaveTask(task);
//                    mainTask.saveList(task);
//                    mainTask.saveHashMap(saveTask.hashCode(), mainTask.taskList);


                    break;
                case 2:
                    //int epicId2 = scanner.nextInt();
                   // taskManager.changeSubtask(epicId2);
                    //int epicId2 = scanner.nextInt();
                   // taskManager.printArray(taskManager.getEpicSubtasks(epicId2));
                   break;
                case 3:
                    scanner.nextLine();
                    System.out.println("введите задачу");
                    String taskName1 = scanner.nextLine();
                    String taskStatus1 = scanner.nextLine();
                    String taskDescr1 = scanner.nextLine();
                    Epic epic = new Epic(taskName1, taskStatus1, taskDescr1);
                    taskManager.saveEpic(epic);
                  //  System.out.println(epic.subtasksId);
                   break;
                case 4:
                    int epicId3 = scanner.nextInt();
                   // taskManager.printEpicStatus();
                    taskManager.changeEpicStatus(epicId3);
                    System.out.println(taskManager.finedTaskWithId(epicId3).status);

//                    int id = scanner.nextInt();
//                    System.out.println(taskManager.finedTaskWithId(id));
                    break;
               case 5:
                   scanner.nextLine();
                   System.out.println("введите задачу");
                   String taskName2 = scanner.nextLine();
                   String taskStatus2 = scanner.nextLine();
                   String taskDescr2 = scanner.nextLine();
                   int epicId = scanner.nextInt();
                   Subtask subtask = new Subtask(taskName2, taskStatus2, taskDescr2, epicId);
                   taskManager.saveSubtask(subtask);

//                   taskManager.getEpicId(subtask, epicId);
//                   System.out.println(subtask.epicId);

                    break;
                case 6:
//                    int idEp = scanner.nextInt();
//                    System.out.println(taskManager.finedEpicWithId(idEp).subtasksId);
                 // Task task1 = new Task("Зубы", "губы", "стол");
                  int removeId = scanner.nextInt();
                  //  Epic epic1 = new Epic("Зубы", "DONE", "стол");
                 // taskManager.changeTask(task1, removeId);
                  Subtask subtask1 = new Subtask("нос", "DONE", "стол", 1);
                  taskManager.changeSubtask(subtask1, removeId);
                    break;
                case 7:
                   taskManager.printAllTasks();
                   break;
                case 8:
                    taskManager.printEpicStatus();

                    // int epicId6 = scanner.nextInt();
                  //  taskManager.getEpicSubtasks(epicId6);
                    break;
                case 9:
                    int taskId2 = scanner.nextInt();
                    taskManager.removeWithId(taskId2);
                    break;
                default:
                    System.out.println("Данная команда не поддерживается, введите одну из указанных команд");
                    break;
            }
            printMenu();
            userInput = scanner.nextInt();
        }
        System.out.println("Программа завершена");

    }


        public static void printMenu () {
            System.out.println("1 - Поставить задачу");
            System.out.println("2 - Напечатать все задачи");
            System.out.println("3 - Сохранить эпик");
            System.out.println("4 - Напечатать все эпики");
            System.out.println("5 - Сохранить сабтаск");
            System.out.println("6 - Напечатать сабтаск");
            System.out.println("7 - Напечатать все задачи");
            System.out.println("8 - Удалить все задачи");
            System.out.println("9 - Найти задачу по Id");
*/
        }

}