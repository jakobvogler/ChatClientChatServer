import java.io.IOException;

public class ChatServerThread extends Thread
{
    Socket socket;
    ChatServer chatServer;
    String nickname = "";

    public ChatServerThread(ChatServer chatServer, Socket socket)
    {
        this.chatServer = chatServer;
        this.socket = socket;
    }

    @Override
    public void run()
    {
        String input;

        while (true)
        {
            input = "";

            try
            {
                if (socket.dataAvailable() > 0)
                {
                    input = socket.readLine();

                    if (!input.isEmpty() && !input.startsWith("/"))
                    {
                        sendMessage(input);
                    }

                    switch (input.split(" ")[0])
                    {
                        case "/lu":
                        case "/users":

                            break;

                        case "/wu":
                        case "/msg":

                            break;

                        case "/cr":

                            break;

                        case "/lr":

                            break;

                        case "/jr":

                            break;

                        case "/er":

                            break;

                        case "/ec":

                            break;

                        case "/help":

                            break;
                    }
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    private void sendMessage(String input)
    {
        //
    }

    public void receiveMessage()
    {
        //
    }
}
