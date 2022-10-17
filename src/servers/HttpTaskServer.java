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

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;


public class HttpTaskServer {

    Gson gson;
    HttpServer httpServer;
    static final int PORT = 8080;

    private static final Charset STANDARD_CHARSET = StandardCharsets.UTF_8;

    TaskManager taskManager;

    public TaskManager getTaskManager(){
        return this.taskManager;
    }

    public HttpTaskServer() throws IOException {
        gson = new Gson()
                .newBuilder()
                .setPrettyPrinting()
                .create();

        taskManager = Managers.getDefault();
        httpServer = HttpServer.create();
        httpServer.bind(new InetSocketAddress(PORT), 0);
        httpServer.createContext("/tasks", new Handler());

    }

    public void start() {
        httpServer.start();
    }

    public void stop() {
        httpServer.stop(1);
    }


    class Handler implements HttpHandler {

        public int getIdFromQuery(String query){
            String[] splitQuery = query.split("=");
            return Integer.parseInt(splitQuery[1]);
        }

        @Override
        public void handle(HttpExchange exchange) throws IOException {

            String response = "";

            int statusCode = 404;

            String method = exchange.getRequestMethod();
            String path = exchange.getRequestURI().getPath();
            String query = exchange.getRequestURI().getQuery();

            switch (method) {

                case "GET":

                    if (path.endsWith("/tasks") && query == null) {
                        statusCode = 200;
                        response = gson.toJson(taskManager.getTasksTreeSet());
                    }
                    if (path.endsWith("tasks/task")) {
                        if (query != null) {
                            Task taskForReturn = null;
                            int taskId = getIdFromQuery(query);

                            for (Task task : taskManager.getTaskList()) {
                                if (taskId == task.getTaskId()) {
                                    taskForReturn = taskManager.getTask(taskId);
                                    statusCode = 200;
                                    response = gson.toJson(taskForReturn);
                                }
                            }

                            if (taskForReturn == null) {
                                statusCode = 400;
                                response = "Необходимо указать корректный id задачи, task c " + taskId + " не существует";
                            }

                        } else {
                            statusCode = 200;
                            response = gson.toJson(taskManager.getTaskList());
                        }
                    }

                    if (path.endsWith("tasks/history") && query == null) {
                        statusCode = 200;
                        response = gson.toJson(taskManager.getHistory());
                    }

                    if (path.endsWith("tasks/epic")) {
                        if (query != null) {
                            Epic epicForReturn = null;
                            int epicId = getIdFromQuery(query);

                            for (Epic epic : taskManager.getEpicList()) {
                                if (epicId == epic.getTaskId()) {
                                    epicForReturn = taskManager.getEpic(epicId);
                                    statusCode = 200;
                                    response = gson.toJson(epicForReturn);
                                }
                            }

                            if (epicForReturn == null) {
                                statusCode = 400;
                                response = "Необходимо указать корректный id задачи, epic c " + epicId + " не существует";
                            }

                        } else {
                            statusCode = 200;
                            response = gson.toJson(taskManager.getEpicList());
                        }
                    }

                    if (path.endsWith("tasks/subtask")) {
                        if (query != null) {
                            Subtask subTaskForReturn = null;
                            int subTaskId = getIdFromQuery(query);

                            for (Subtask subTask : taskManager.getSubtaskList()) {
                                if (subTaskId == subTask.getTaskId()) {
                                    subTaskForReturn = taskManager.getSubtask(subTaskId);
                                    statusCode = 200;
                                    response = gson.toJson(subTaskForReturn);
                                }
                            }

                            if (subTaskForReturn == null) {
                                statusCode = 400;
                                response = "Необходимо указать корректный id задачи, SubTask c " + subTaskId + " не существует";
                            }

                        } else {
                            statusCode = 200;
                            response = gson.toJson(taskManager.getSubtaskList());
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

                                taskManager.saveSubtask(newSubTask);
                                statusCode = 201;
                                response = "Задача добавлена";

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









