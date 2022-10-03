import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
//        Scanner scanner = new Scanner(System.in);
//        printMenu();
//        int userInput = scanner.nextInt();
//        managers.TaskManager taskManager = managers.Managers.getDefault();
//
//
//
//        while (userInput != 0) {
//            switch (userInput) {
//                case 1:
//                    tasks.Task task1 = new tasks.Task("Спринт1", tasks.TaskStatus.NEW, "учу1");
//                   tasks.Task task2 = new tasks.Task("Спринт2", tasks.TaskStatus.NEW, "учу2");
//                    tasks.Task task3 = new tasks.Task("Спринт3", tasks.TaskStatus.NEW, "учу3");
//                    tasks.Task task4 = new tasks.Task("Спринт4", tasks.TaskStatus.NEW, "учу4");
//                    tasks.Task task5 = new tasks.Task("Спринт5", tasks.TaskStatus.NEW, "учу5");
//                    tasks.Task task6 = new tasks.Task("Спринт6", tasks.TaskStatus.NEW, "учу6");
//
//                    tasks.Epic epic1 = new tasks.Epic("Тренировка1", tasks.TaskStatus.NEW, "Тренировка1");
//                   tasks.Epic epic2 = new tasks.Epic("Тренировка2", tasks.TaskStatus.NEW, "Тренировка2");
//
//                   tasks.Subtask subTask1 = new tasks.Subtask("Прийти в зал1", tasks.TaskStatus.NEW, "переодеться1", 7);
//                    tasks.Subtask subTask2 = new tasks.Subtask("Прийти в зал2", tasks.TaskStatus.NEW, "переодеться2", 7);
//                    tasks.Subtask subTask3 = new tasks.Subtask("Прийти в зал3", tasks.TaskStatus.NEW, "переодеться3", 7);
//
//
//
//                    taskManager.saveTask(task1);
//                   taskManager.saveTask(task2);
//                   taskManager.saveTask(task3);
//                   taskManager.saveTask(task4);
//                   taskManager.saveTask(task5);
//                    taskManager.saveTask(task6);
//
//                   taskManager.saveEpic(epic1);
//                   taskManager.saveEpic(epic2);
//
//                    taskManager.saveSubtask(subTask1);
//                    taskManager.saveSubtask(subTask2);
//                    taskManager.saveSubtask(subTask3);
//
//
//
//
//
//                    taskManager.getTask(1);
//                    taskManager.getTask(2);
//                    taskManager.getTask(3);
//                    taskManager.getTask(4);
//                    taskManager.getTask(5);
//                    taskManager.getTask(6);
//
//                    taskManager.getEpic(7);
//                    taskManager.getEpic(8);
//
//
//                    taskManager.getSubtask(9);
//                    taskManager.getSubtask(10);
//                    taskManager.getSubtask(11);
//
//
//                    for (tasks.Task task : taskManager.getHistory()) {
//                    System.out.println(task);
//                    }
//
//                    System.out.println("Вызываем в новой последовательности");
//
//                    taskManager.getSubtask(9);
//                    taskManager.getSubtask(10);
//                    taskManager.getEpic(7);
//                    taskManager.getEpic(8);
//                    taskManager.getSubtask(11);
//
//                    taskManager.getTask(1);
//                    taskManager.getTask(2);
//                    taskManager.getTask(3);
//                    taskManager.getTask(4);
//                    taskManager.getTask(5);
//                    taskManager.getTask(6);
//
//                    for (tasks.Task task : taskManager.getHistory()) {
//                        System.out.println(task);
//                    }
//
//
//                    break;
//                case 2:
//                    scanner.nextLine();
//                    System.out.println("введите задачу");
//                    String epicName = scanner.nextLine();
//                    tasks.TaskStatus epicStatus = tasks.TaskStatus.valueOf(scanner.nextLine());
//                    String epicDescription = scanner.nextLine();
//                    tasks.Epic epic = new tasks.Epic(epicName, epicStatus, epicDescription);
//                    taskManager.saveEpic(epic);
//                   break;
//                case 3:
//                    scanner.nextLine();
//                   System.out.println("введите задачу");
//                    String subtaskName = scanner.nextLine();
//                    tasks.TaskStatus subtaskStatus = tasks.TaskStatus.valueOf(scanner.nextLine());
//                    String subtaskDescription = scanner.nextLine();
//                    int epicId = scanner.nextInt();
//                    tasks.Subtask subtask = new tasks.Subtask(subtaskName, subtaskStatus, subtaskDescription, epicId);
//                    taskManager.saveSubtask(subtask);
//                   break;
//                case 4:
//                   taskManager.removeAllTasks();
//                    break;
//               case 5:
//                   int removeId = scanner.nextInt();
//                   taskManager.removeWithId(removeId);
//                    break;
//                case 6:
//                  int subtaskIdChanger = scanner.nextInt();
//                  tasks.Subtask subtask1 = new tasks.Subtask("нос", tasks.TaskStatus.DONE, "стол", 1);
//                  taskManager.changeSubtask(subtask1, subtaskIdChanger);
//                    break;
//                case 7:
//                   taskManager.printAllTasks();
//                   break;
//                case 8:
//                    int epicIdFinder = scanner.nextInt();
//                    System.out.println(taskManager.getTask(epicIdFinder));
//                    break;
//                case 9:
//                    for (tasks.Task task : taskManager.getHistory()) {
//                        System.out.println(task);
//                    }
//                    break;
//                case 10:
//
//                    managers.TaskManager taskManager2 = managers.Managers.getDefaultFileManager();
//                    tasks.Epic epic5 = new tasks.Epic("Тренировка1", tasks.TaskStatus.NEW, "Тренировка1");
//                    tasks.Subtask subTask6 = new tasks.Subtask("Прийти в зал1", tasks.TaskStatus.NEW, "переодеться1", 1);
//                   // tasks.Task task22 = new tasks.Task("Спринт2", tasks.TaskStatus.NEW, "учу1");
//                    taskManager2.saveEpic(epic5);
//                    taskManager2.getEpic(1);
//                    taskManager2.saveSubtask(subTask6);
//                    taskManager2.getSubtask(2);
//                  //  System.out.println(taskManager.taskToString(task22));
//                    break;
//
//                default:
//                    System.out.println("Данная команда не поддерживается, введите одну из указанных команд");
//                    break;
//            }
//            printMenu();
//            userInput = scanner.nextInt();
//        }
//        System.out.println("Программа завершена");
//
//    }
//
//
//        public static void printMenu() {
//            System.out.println("1 - Тест работы managers.HistoryManager");
//            System.out.println("2 - Создать Эпик");
//            System.out.println("3 - Создать Сабтаск");
//            System.out.println("4 - Удалить все задачи");
//            System.out.println("5 - Удалить по номеру ID");
//            System.out.println("6 - Измеить tasks.Subtask");
//            System.out.println("7 - Напечатать все задачи");
//            System.out.println("8 - Найти tasks.Epic по ID");
//            System.out.println("9 - Напечатать историю");
//            System.out.println(("10 - тест сейва"));
//        }
    }
}