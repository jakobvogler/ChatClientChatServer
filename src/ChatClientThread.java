import java.io.IOException;

public class ChatClientThread extends Thread
{
    ChatClient chatClient;
    Socket socket;
    boolean run = true;

    public ChatClientThread(ChatClient chatClient, Socket socket)
    {
        this.chatClient = chatClient;
        this.socket = socket;
    }

    public void run()
    {
        while (run)
        {
            try {
                String input = socket.readLine();

                System.out.println(input);

                if (input.equals("> chat closed"))
                {
                    chatClient.setRun(false);
                    run = false;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}