import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        printMenu();
        int userInput = scanner.nextInt();
        TaskManager taskManager = Managers.getDefault();



        while (userInput != 0) {
            switch (userInput) {
                case 1:
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



                    taskManager.saveTask(task1);
                   taskManager.saveTask(task2);
                   taskManager.saveTask(task3);
                   taskManager.saveTask(task4);
                   taskManager.saveTask(task5);
                    taskManager.saveTask(task6);

                   taskManager.saveEpic(epic1);
                   taskManager.saveEpic(epic2);

                    taskManager.saveSubtask(subTask1);
                    taskManager.saveSubtask(subTask2);
                    taskManager.saveSubtask(subTask3);





                    taskManager.getTask(1);
                    taskManager.getTask(2);
                    taskManager.getTask(3);
                    taskManager.getTask(4);
                    taskManager.getTask(5);
                    taskManager.getTask(6);

                    taskManager.getEpic(7);
                    taskManager.getEpic(8);


                    taskManager.getSubtask(9);
                    taskManager.getSubtask(10);
                    taskManager.getSubtask(11);


                    for (Task task : taskManager.getHistory()) {
                    System.out.println(task);
                    }

                    System.out.println("Вызываем в новой последовательности");

                    taskManager.getSubtask(9);
                    taskManager.getSubtask(10);
                    taskManager.getEpic(7);
                    taskManager.getEpic(8);
                    taskManager.getSubtask(11);

                    taskManager.getTask(1);
                    taskManager.getTask(2);
                    taskManager.getTask(3);
                    taskManager.getTask(4);
                    taskManager.getTask(5);
                    taskManager.getTask(6);

                    for (Task task : taskManager.getHistory()) {
                        System.out.println(task);
                    }


                    break;
                case 2:
                    scanner.nextLine();
                    System.out.println("введите задачу");
                    String epicName = scanner.nextLine();
                    TaskStatus epicStatus = TaskStatus.valueOf(scanner.nextLine());
                    String epicDescription = scanner.nextLine();
                    Epic epic = new Epic(epicName, epicStatus, epicDescription);
                    taskManager.saveEpic(epic);
                   break;
                case 3:
                    scanner.nextLine();
                   System.out.println("введите задачу");
                    String subtaskName = scanner.nextLine();
                    TaskStatus subtaskStatus = TaskStatus.valueOf(scanner.nextLine());
                    String subtaskDescription = scanner.nextLine();
                    int epicId = scanner.nextInt();
                    Subtask subtask = new Subtask(subtaskName, subtaskStatus, subtaskDescription, epicId);
                    taskManager.saveSubtask(subtask);
                   break;
                case 4:
                   taskManager.removeAllTasks();
                    break;
               case 5:
                   int removeId = scanner.nextInt();
                   taskManager.removeWithId(removeId);
                    break;
                case 6:
                  int subtaskIdChanger = scanner.nextInt();
                  Subtask subtask1 = new Subtask("нос", TaskStatus.DONE, "стол", 1);
                  taskManager.changeSubtask(subtask1, subtaskIdChanger);
                    break;
                case 7:
                   taskManager.printAllTasks();
                   break;
                case 8:
                    int epicIdFinder = scanner.nextInt();
                    System.out.println(taskManager.getTask(epicIdFinder));
                    break;
                case 9:
                    for (Task task : taskManager.getHistory()) {
                        System.out.println(task);
                    }
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


        public static void printMenu() {
            System.out.println("1 - Тест работы HistoryManager");
            System.out.println("2 - Создать Эпик");
            System.out.println("3 - Создать Сабтаск");
            System.out.println("4 - Удалить все задачи");
            System.out.println("5 - Удалить по номеру ID");
            System.out.println("6 - Измеить Subtask");
            System.out.println("7 - Напечатать все задачи");
            System.out.println("8 - Найти Epic по ID");
            System.out.println("9 - Напечатать историю");
        }
}