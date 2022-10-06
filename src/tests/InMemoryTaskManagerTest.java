import managers.InMemoryTaskManager;
import org.junit.jupiter.api.BeforeEach;


import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest extends TaskManagerTest<InMemoryTaskManager> {


    @BeforeEach
    public void beforeEach(){
        manager = new InMemoryTaskManager();
    }



}