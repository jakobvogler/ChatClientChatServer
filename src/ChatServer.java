import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class ChatServer {
    List<String> usernames = new LinkedList<>();
    ServerSocket ss;

    public ChatServer() {
        try {
            ss = new ServerSocket(9999);
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (true) {
            try {
                ss.accept();
            } catch (IOException e) {
                e.printStackTrace();
            }

            //
        }
    }
    public static void main(String[] args) {
        new ChatServer();
    }
}
