import managers.InMemoryTaskManager;


import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest extends TaskManagerTest<InMemoryTaskManager> {


    @Override
    protected InMemoryTaskManager createManager() {
        return new InMemoryTaskManager();
    }



}