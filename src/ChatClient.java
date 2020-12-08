import java.io.IOException;
import java.util.Scanner;

public class ChatClient
{
    private Socket socket;
    private boolean run;

    public ChatClient()
    {
        try
        {
            socket = new Socket("tvjaof.selfhost.bz", 9999);
            socket.connect();

            new ChatClientThread(this, socket).start();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    public void start()
    {
        Scanner sc = new Scanner(System.in);
        String input;

        while (true)
        {
            try {
                socket.write(sc.nextLine() + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void setRun(boolean run)
    {
        this.run = run;
    }

    public static void main(String[] args)
    {
        new ChatClient().start();
    }
}
