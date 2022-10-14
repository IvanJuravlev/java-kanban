

import servers.HttpTaskServer;
import servers.KVServer;


import java.io.IOException;


public class Main {

    public static void main(String[] args) throws IOException {
        new KVServer().start();
        new HttpTaskServer().start();
    }
}