import java.util.LinkedList;
import java.util.List;

public class Chatroom
{
    List<String> nicknames = new LinkedList<>();
    List<ChatServerThread> clients = new LinkedList<>();
}
