import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class ChatServer
{
    private ServerSocket ss;
    private ChatSystem chatSystem;

    public ChatServer()
    {
        chatSystem = new ChatSystem();

        try
        {
            ss = new ServerSocket(9999);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public synchronized void runServer()
    {
        while (true)
        {
            try {
                ChatServerThread temp = new ChatServerThread(chatSystem, chatSystem.getMainRoom(), ss.accept());
                temp.start();
                //chatSystem.addClient(new ChatServerThread(chatSystem, chatSystem.getMainRoom(), ss.accept()));
                //chatSystem.getClients().get(chatSystem.getClients().size()-1).start();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args)
    {
        new ChatServer().runServer();
    }
}
