import java.util.LinkedList;
import java.util.List;

public class ChatSystem
{
    private List<ChatServerThread> clients = new LinkedList<>();
    private List<Chatroom> chatrooms = new LinkedList<>();
    private Chatroom mainRoom;

    public ChatSystem()
    {
        mainRoom = new Chatroom(this, "main");
    }

    public synchronized String sendMessageToUser(ChatServerThread origin, String message, String nickname)
    {
        message = "[" + origin.getNickname() + " -> " + nickname + "] " + message;
        for (ChatServerThread client : clients)
        {
            if (client.getNickname().equals(nickname))
            {
                client.receiveMessage(message);
                return message;
            }
        }

        return "\n> no active user found\n";
    }

    public synchronized void sendMessageToRoom(ChatServerThread origin, String message, Chatroom room)
    {
        message = "[" + origin.getNickname() + "] " + message;

        for (ChatServerThread client : room.getClients()) {
            client.receiveMessage(message);
        }
    }

    public synchronized String createRoom(ChatServerThread origin, String roomName)
    {
        if ( !(getRoomNames().contains(roomName) || roomName.equals("main")) )
        {
            Chatroom room = new Chatroom(this, roomName);
            room.addClient(origin);
            chatrooms.add(room);

            return "\n> room created\n";
        }

        return "\n> room name not available\n";
    }

    public synchronized String joinRoom(ChatServerThread origin, String roomName)
    {
        if (origin.getChatroom().getRoomName().equals(roomName))
        {
            return "\n> you are already in this room\n";
        }

        for (Chatroom room : chatrooms)
        {
            if (room.getRoomName().equals(roomName))
            {
                room.addClient(origin);
                return "\n> room joined\n";
            }
        }

        return "\n> couldn't find room\n";
    }

    public synchronized String exitRoom(ChatServerThread origin)
    {
        if (origin.getChatroom().equals(mainRoom))
        {
            return "\n> there is no room you can leave\n";
        }

        mainRoom.addClient(origin);
        return "\n> room left\n";
    }

    public synchronized String exitChat(ChatServerThread origin)
    {
        origin.getChatroom().getClients().remove(origin);
        clients.remove(origin);

        return "\n> chat closed\n";
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

    public synchronized List<String> getRoomNames()
    {
        List<String> roomNames = new LinkedList<>();

        for (int i = 0; i < chatrooms.size(); i++)
        {
            roomNames.add(chatrooms.get(i).getRoomName());
        }

        return roomNames;
    }

    public Chatroom getMainRoom()
    {
        return mainRoom;
    }

    public synchronized List<ChatServerThread> getClients()
    {
        return clients;
    }

    public synchronized List<Chatroom> getChatrooms()
    {
        return chatrooms;
    }

    public synchronized void addClient(ChatServerThread e)
    {
        clients.add(e);
    }
}
