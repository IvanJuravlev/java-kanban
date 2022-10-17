import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import managers.TaskManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import servers.HTTPTaskManager;
import servers.HttpTaskServer;
import servers.KVServer;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;
import tasks.TaskStatus;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;



    public class HttpTaskServerTest {





    private KVServer kvServer;
    private HttpTaskServer httpTaskServer;
    private static HTTPTaskManager taskManager;
    private static Gson gson;
    private static HttpClient httpClient;
    private static final URI url = URI.create("http://localhost:8080");




    @BeforeAll
    public static void beforeAll(){
        gson = new Gson();
        httpClient = HttpClient.newBuilder().build();

    }

    @BeforeEach
    void beforeEach() throws IOException {
        kvServer = new KVServer();
        kvServer.start();
        httpTaskServer = new HttpTaskServer();
        httpTaskServer.start();
        taskManager = (HTTPTaskManager)httpTaskServer.getTaskManager();
    }

    @AfterEach
    public void afterEach(){
        kvServer.stop();
        httpTaskServer.stop();

    }


    @Test
    void PostMethodTest() throws IOException, InterruptedException {

        Task task1 = new Task("Спринт2", TaskStatus.NEW, "учу2",
                LocalDateTime.of(2020, 9, 25, 13, 55, 15), Duration.ofMinutes(20));
        httpTaskServer.getTaskManager().saveTask(task1);
        Epic epic1 = new Epic("Тренировка1", TaskStatus.IN_PROGRESS, "Тренировка1");
        httpTaskServer.getTaskManager().saveEpic(epic1);
        Subtask subtask1 = new Subtask("Прийти в зал1", TaskStatus.NEW, "переодеться1", 2,
                LocalDateTime.of(2022, 9, 26, 21, 0), Duration.ofMinutes(30));
        httpTaskServer.getTaskManager().saveSubtask(subtask1);

        Subtask subtask2 = new Subtask("Прийти в зал2", TaskStatus.NEW, "переодеться2", 2,
                LocalDateTime.of(2022, 9, 26, 23, 0), Duration.ofMinutes(30));
        httpTaskServer.getTaskManager().saveSubtask(subtask2);


        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url + "/tasks/task"))
                .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(task1)))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(201, response.statusCode());

        request = HttpRequest.newBuilder()
                .uri(URI.create(url + "/tasks/epic"))
                .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(epic1)))
                .build();

        response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(201, response.statusCode());

        request = HttpRequest.newBuilder()
                .uri(URI.create(url + "/tasks/subtask"))
                .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(subtask1)))
                .build();

        response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(201, response.statusCode());

        request = HttpRequest.newBuilder()
                .uri(URI.create(url + "/wrongUrl"))
                .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(subtask2)))
                .build();

        response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(404, response.statusCode());
    }

    @Test
    void GetMethodTest() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url + "/tasks"))
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());

        request = HttpRequest.newBuilder()
                .uri(URI.create(url + "/tasks/task"))
                .GET()
                .build();

        response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());

        request = HttpRequest.newBuilder()
                .uri(URI.create(url + "/tasks/epic"))
                .GET()
                .build();

        response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());

        request = HttpRequest.newBuilder()
                .uri(URI.create(url + "/tasks/subtask"))
                .GET()
                .build();

        response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());

        request = HttpRequest.newBuilder()
                .uri(URI.create(url + "/wrongUrl"))
                .GET()
                .build();

        response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(404, response.statusCode());
    }

    @Test
    void GetByIdTest() throws IOException, InterruptedException {

        Task task1 = new Task("Спринт2", TaskStatus.NEW, "учу2",
                LocalDateTime.of(2020, 9, 25, 13, 55, 15), Duration.ofMinutes(20));
        httpTaskServer.getTaskManager().saveTask(task1);
        Epic epic1 = new Epic("Тренировка1", TaskStatus.IN_PROGRESS, "Тренировка1");
        httpTaskServer.getTaskManager().saveEpic(epic1);
        Subtask subtask1 = new Subtask("Прийти в зал1", TaskStatus.NEW, "переодеться1", 2,
                LocalDateTime.of(2022, 9, 26, 21, 0), Duration.ofMinutes(30));
        httpTaskServer.getTaskManager().saveSubtask(subtask1);


        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url + "/tasks/task?id=1"))
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());

        request = HttpRequest.newBuilder()
                .uri(URI.create(url + "/tasks/epic?id=2"))
                .GET()
                .build();

        response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());

        request = HttpRequest.newBuilder()
                .uri(URI.create(url + "/tasks/subtask?id=3"))
                .GET()
                .build();

        response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());


        request = HttpRequest.newBuilder()
                .uri(URI.create(url + "/tasks/task?id=" + 222))
                .GET()
                .build();

        response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(400, response.statusCode());
    }

    @Test
    void GetHistoryTest() throws IOException, InterruptedException {

        Task task1 = new Task("Спринт1", TaskStatus.NEW, "учу1",
                LocalDateTime.of(2020, 9, 25, 13, 30, 15), Duration.ofMinutes(20));
        httpTaskServer.getTaskManager().saveTask(task1);

        httpTaskServer.getTaskManager().getTask(task1.getTaskId());



        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url + "/tasks/history"))
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());

    }


    @Test
    void removeWithIdTest() throws IOException, InterruptedException {

        Task task1 = new Task("Спринт2", TaskStatus.NEW, "учу2",
                LocalDateTime.of(2020, 9, 25, 13, 55, 15), Duration.ofMinutes(20));
        httpTaskServer.getTaskManager().saveTask(task1);
        Epic epic1 = new Epic("Тренировка1", TaskStatus.IN_PROGRESS, "Тренировка1");
        httpTaskServer.getTaskManager().saveEpic(epic1);
        Subtask subtask1 = new Subtask("Прийти в зал1", TaskStatus.NEW, "переодеться1", 2,
                LocalDateTime.of(2022, 9, 26, 21, 0), Duration.ofMinutes(30));
        httpTaskServer.getTaskManager().saveSubtask(subtask1);


        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url + "/tasks/task?id=1"))
                .DELETE()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());

        request = HttpRequest.newBuilder()
                .uri(URI.create(url + "/tasks/subtask?id=3"))
                .DELETE()
                .build();


        response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());

        request = HttpRequest.newBuilder()
                .uri(URI.create(url + "/tasks/epic?id=2"))
                .DELETE()
                .build();

        response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());


    }


}
