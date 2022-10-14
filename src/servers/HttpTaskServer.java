package servers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import managers.Managers;
import managers.TaskManager;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;
import tasks.TaskStatus;

import javax.sound.sampled.Port;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public class HttpTaskServer {

    Gson gson;
    HttpServer httpServer;
    static final int PORT = 8080;

    private static final Charset STANDARD_CHARSET = StandardCharsets.UTF_8;

    TaskManager taskManager;

    public HttpTaskServer() throws IOException {
        gson = new Gson()
                .newBuilder()
                .setPrettyPrinting()
                .create();

        taskManager = Managers.getDefault();
        httpServer = HttpServer.create();
        httpServer.bind(new InetSocketAddress(PORT), 0);
        httpServer.createContext("/tasks", new Handler());
        Task task1 = new Task("Спринт1", TaskStatus.NEW, "учу1", LocalDateTime.of(2022, 9, 25, 13, 30, 15), Duration.ofMinutes(20));
        taskManager.saveTask(task1);
    }

    public void start() {
        httpServer.start();
    }

    void stop() {
        httpServer.stop(1);
    }


    class Handler implements HttpHandler {

        @Override
        public void handle(HttpExchange exchange) throws IOException {

            String response = "";

            int statusCode = 404;

            String method = exchange.getRequestMethod();
            String path = exchange.getRequestURI().getPath();
            String query = exchange.getRequestURI().getQuery();

            switch (method) {

                case "GET":

                    if (path.endsWith("/tasks")) {
                        statusCode = 200;
                        response = "Список задач по приоритету: " + gson.toJson(taskManager.getTasksTreeSet());
                    } else if (path.endsWith("tasks/task")) {
                        statusCode = 200;
                        if (query == null) {
                            response = "Список всех задач: " + gson.toJson(taskManager.getTaskList());
                        } else {
                            String[] splitQuery = query.split("=");
                            int taskId = Integer.parseInt(splitQuery[1]);
                            response = gson.toJson(taskManager.getTask(taskId));
                        }

                    } else if (path.endsWith("tasks/history")) {
                        statusCode = 200;
                        response = gson.toJson(taskManager.getHistory());

                    } else if (path.endsWith("tasks/subtask")) {
                        statusCode = 200;
                        if (query == null) {
                            response = "Список сабтасков: " + gson.toJson(taskManager.getSubtaskList());
                        } else {
                            String[] splitQuery = query.split("=");
                            int taskId = Integer.parseInt(splitQuery[1]);
                            response = gson.toJson(taskManager.getSubtask(taskId));
                        }

                    } else if (path.endsWith("/tasks/subtask/epic") && query != null) {
                        statusCode = 200;
                        String[] splitQuery = query.split("=");
                        int taskId = Integer.parseInt(splitQuery[1]);
                        response = "Список сабтасок для эпика " + gson.toJson(taskManager.getEpic(taskId).getSubtasksId());

                    } else if (path.endsWith("tasks/epic")) {
                        statusCode = 200;
                        if (query == null) {
                            response = "Список всех эпиков: " + gson.toJson(taskManager.getEpicList());
                        } else {
                            String[] splitQuery = query.split("=");
                            int taskId = Integer.parseInt(splitQuery[1]);
                            response = gson.toJson(taskManager.getEpic(taskId));
                        }
                    }
                    break;

                case "POST":

                    InputStream inputStream = exchange.getRequestBody();
                    if (inputStream != null) {
                        String body = new String(inputStream.readAllBytes(), STANDARD_CHARSET);
                        if (query == null) {
                            if (path.endsWith("tasks/task")) {
                                statusCode = 201;
                                Task newTask = gson.fromJson(body, Task.class);
                                taskManager.saveTask(newTask);
                                response = "Задача добавлена";

                            } else if (path.endsWith("tasks/epic")) {
                                statusCode = 201;
                                Epic newEpic = gson.fromJson(body, Epic.class);
                                taskManager.saveEpic(newEpic);
                                response = "Задача добавлена";

                            } else if (path.endsWith("tasks/subtask")) {
                                Subtask newSubTask = gson.fromJson(body, Subtask.class);
                                List<Epic> epics = taskManager.getEpicList();
                                Epic epicForSubTask = null;

                                taskManager.saveSubtask(newSubTask);
                                statusCode = 201;
                                response = "Задача добавлена";

                                if (epicForSubTask == null) {
                                    statusCode = 400;
                                    response = "Необходимо указать корректный id задачи типа Epic";
                                }
                            }
                        }
                    }
                    break;

                case "DELETE":

                    if (path.endsWith("/tasks") && query == null) {
                        System.out.println("Обработка запроса DELETE /tasks/");
                        statusCode = 200;
                        taskManager.removeAllTasks();
                        taskManager.getTasksTreeSet().clear();

                        response = "Удалены задачи всех видов";
                    }

                    if (path.endsWith("tasks/task") || path.endsWith("tasks/epic") || path.endsWith("tasks/subtask")) {
                        if (query != null) {

                            String[] splitQuery = query.split("=");
                            int taskId = Integer.parseInt(splitQuery[1]);

                            try {
                                taskManager.removeWithId(taskId);
                                statusCode = 200;
                                response = "Задача типа TASK с ID " + taskId + " удалена";
                            } catch (NullPointerException e) {
                                statusCode = 400;
                                System.out.println("Задачи с таким ID не существует");
                            }

                        }
                    }
                    break;
            }


                        exchange.sendResponseHeaders(statusCode, response.getBytes(STANDARD_CHARSET).length);
                        try (
                                OutputStream outputStream = exchange.getResponseBody()) {
                            outputStream.write(response.getBytes(STANDARD_CHARSET));
                        }
                    }
            }
        }







//                            for (Task task : taskList) {
//                                if (taskId == task.getId()) {
//                                    taskForRemove = taskManager.getTask(taskId);
//                                    statusCode = 200;
//                                    taskManager.removeTask(taskId);
//                                    response = "Задача типа TASK с ID " + taskId + " удалена";
//                                }
//                            }
//
//                            if (taskForRemove == null) {
//                                statusCode = 400;
//                                response = "Необходимо указать корректный id задачи, task c " + taskId + " не существует";
//                            }
//                        } else {
//                            statusCode = 200;
//                            taskManager.removeAllTask();
//                            response = "удалены все задачи типа TASK";
//                        }
//                    }
//
//                    if (path.endsWith("tasks/epic")) {
//                        if (query != null) {
//                            List<Epic> epicList = taskManager.getEpicList();
//                            Epic epicForRemove = null;
//
//                            String[] splitQuery = query.split("=");
//                            int taskId = Integer.parseInt(splitQuery[1]);
//
//                            for (Epic epic : epicList) {
//                                if (taskId == epic.getId()) {
//                                    epicForRemove = taskManager.getEpic(taskId);
//                                    statusCode = 200;
//
//                                    taskManager.getAllSubTaskOfEpic(epic).clear();
//                                    taskManager.removeEpic(taskId);
//
//                                    response = "Задача типа EPIC с ID " + taskId + " удалена";
//                                }
//                            }
//
//                            if (epicForRemove == null) {
//                                statusCode = 400;
//                                response = "Необходимо указать корректный id задачи, epic c " + taskId + " не существует";
//                            }
//                        } else {
//                            statusCode = 200;
//                            taskManager.removeAllEpic();
//                            response = "удалены все задачи типа EPIC";
//                        }
//                    }
//
//                    if (path.endsWith("tasks/subtask")) {
//                        if (query != null) {
//                            List<SubTask> subTaskList = taskManager.getSubTaskList();
//                            SubTask subTaskForRemove = null;
//
//                            String[] splitQuery = query.split("=");
//                            int taskId = Integer.parseInt(splitQuery[1]);
//
//                            for (SubTask subTask : subTaskList) {
//                                if (taskId == subTask.getId()) {
//                                    subTaskForRemove = taskManager.getSubTask(taskId);
//                                    statusCode = 200;
//                                    taskManager.removeSubTask(taskId);
//                                    response = "Задача типа SubTask с ID " + taskId + " удалена";
//                                }
//                            }
//
//                            if (subTaskForRemove == null) {
//                                statusCode = 400;
//                                response = "Необходимо указать корректный id задачи, SubTask c " + taskId + " не существует";
//                            }
//                        } else {
//                            statusCode = 200;
//                            taskManager.removeAllSubTask();
//                            response = "удалены все задачи типа TASK";
//                        }
//                    }








