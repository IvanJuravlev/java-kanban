import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        printMenu();
        int userInput = scanner.nextInt();
       // InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();
        TaskManager taskManager = new InMemoryTaskManager();




        while (userInput != 0) {
            switch (userInput) {
                case 1:
                    scanner.nextLine();
                    System.out.println("введите задачу");
                    String taskName = scanner.nextLine();
                    TaskStatus taskStatus = TaskStatus.valueOf(scanner.nextLine());
                    String taskDescription = scanner.nextLine();
                    Task task = new Task(taskName, taskStatus, taskDescription);
                    taskManager.saveTask(task);
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
                    taskManager.getEpic(epicIdFinder);
                    break;
                case 9:
                   // for()
                    System.out.println(taskManager.getHistory());
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
            System.out.println("1 - Сохранить задачу");
            System.out.println("2 - Сохранить эпик");
            System.out.println("3 - Сохранить сабтаск");
            System.out.println("4 - Удалить все задачи");
            System.out.println("5 - Удалить по номеру ID");
            System.out.println("6 - Измеить Subtask");
            System.out.println("7 - Напечатать все задачи");
            System.out.println("8 - Найти Epic по ID");
            System.out.println("9 - Вывести историю просмотров");
        }
}