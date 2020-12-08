import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class ChatServer
{
    List<String> nicknames = new LinkedList<>();
    List<ChatServerThread> clients = new LinkedList<>();
    ServerSocket ss;

    public ChatServer()
    {
        try
        {
            ss = new ServerSocket(9999);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public synchronized void start()
    {
        while (true)
        {
            try
            {
                clients.add(new ChatServerThread(this, ss.accept()));
                clients.get(clients.size()-1).start();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

            //
        }
    }
    public static void main(String[] args)
    {
        new ChatServer().start();
    }
}
