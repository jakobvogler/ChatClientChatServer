import java.util.LinkedList;
import java.util.List;

public class Chatroom
{
    private List<ChatServerThread> clients = new LinkedList<>();
    private ChatSystem chatSystem;
    private String roomName = "";

    public Chatroom(ChatSystem chatSystem, String roomName)
    {
        this.chatSystem = chatSystem;
        this.roomName = roomName;
    }

    public synchronized List<String> getNicknames()
    {
        List<String> nicknames = new LinkedList<>();

        for (int i = 0; i < clients.size(); i++)
        {
            nicknames.add(clients.get(i).getNickname());
        }

        return nicknames;
    }

    public synchronized void addClient(ChatServerThread client)
    {
        Chatroom temp = client.getChatroom();
        temp.getClients().remove(client);

        if (temp.getClients().size() == 0 && !temp.equals(chatSystem.getMainRoom()))
        {
            chatSystem.getChatrooms().remove(temp);
        }

        clients.add(client);
        client.setChatroom(this);
    }

    public synchronized String getRoomName()
    {
        return roomName;
    }

    public synchronized List<ChatServerThread> getClients()
    {
        return clients;
    }
}
