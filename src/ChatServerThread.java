import java.io.IOException;

public class ChatServerThread extends Thread
{
    private Socket socket;
    private ChatSystem chatSystem;
    private Chatroom chatroom;
    private String nickname = "";
    private boolean run = true;

    public ChatServerThread(ChatSystem chatSystem, Chatroom chatroom, Socket socket)
    {
        this.chatSystem = chatSystem;
        this.chatroom = chatroom;
        this.socket = socket;
    }

    @Override
    public void run()
    {
        String input;

        chatroom.getClients().add(this);

        while (true)
        {
            input = "";

            try
            {
                input = socket.readLine();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

            if (input.length() > 0 && input.split(" ").length == 1)
            {
                nickname = input;

                try
                {
                    socket.write("\n> connected\n\n");
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }

                break;
            }
            else if (chatSystem.getNicknames().contains(input))
            {
                try
                {
                    socket.write("\n> nickname already taken try again\n\n");
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            else
            {
                try
                {
                    socket.write("\n> wrong format try again\n\n");
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }

        while (run)
        {
            input = "";

            try
            {
                input = socket.readLine();

                if (!input.isEmpty() && !input.startsWith("/"))
                {
                    sendMessage(input);
                }
                else
                {
                    switch (input.split(" ")[0])
                    {
                        case "/lu":
                        case "/users":
                            listUsers();
                            break;

                        case "/wu":
                        case "/msg":
                            if ( !(input.split(" ").length > 2) )
                            {
                                help();
                                break;
                            }

                            input = input.substring(input.indexOf(" ") + 1);
                            whisperUser( input.split(" ")[0], input.substring(input.indexOf(" ") + 1) );
                            break;

                        case "/cr":
                            if ( !(input.split(" ").length == 2) )
                            {
                                help();
                                break;
                            }

                            createRoom(input.split(" ")[1]);
                            break;

                        case "/lr":
                            listRooms();
                            break;

                        case "/jr":
                            if ( !(input.split(" ").length == 2) )
                            {
                                help();
                                break;
                            }

                            joinRoom(input.split(" ")[1]);
                            break;

                        case "/er":
                            exitRoom();
                            break;

                        case "/ec":
                            exitChat();
                            break;

                        case "/help":
                            help();
                            break;

                        default:
                            receiveMessage("\n> type /help for help\n");
                    }
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    private void sendMessage(String message)
    {
        chatSystem.sendMessageToRoom(this, message, chatroom);
    }

    private void listUsers()
    {
        String output = "\n### Active Users ###\n\n";

        for (String nickname : chatSystem.getNicknames()) {
            output += "- " + nickname + "\n";
        }

        output += "\n";

        try
        {
            socket.write(output);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private void whisperUser(String nickname, String message)
    {
        receiveMessage(chatSystem.sendMessageToUser(this, message, nickname));
    }

    private void createRoom(String roomName)
    {
        receiveMessage(chatSystem.createRoom(this, roomName));
    }

    private void listRooms()
    {
        String output = "\n### Open Rooms ###\n\n";

        for (String chatroom : chatSystem.getRoomNames())
        {
            output += "- " + chatroom + "\n";
        }

        output += "\n";

        try
        {
            socket.write(output);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private void joinRoom(String roomName)
    {
        receiveMessage(chatSystem.joinRoom(this, roomName));
    }

    private void exitRoom()
    {
        receiveMessage(chatSystem.exitRoom(this));
    }

    private void exitChat()
    {
        receiveMessage(chatSystem.exitChat(this));

        run = false;
        try
        {
            socket.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private void help()
    {
        try {
            socket.write("\n### Help ###\n\n" +
                    "/lu - lists all available users\n" +
                    "/wu [nickname] [message] - whispers a private message to a specific user\n" +
                    "/cr [roomname] - creates a private room for others to join\n" +
                    "/lr - lists all available rooms\n" +
                    "/jr [roomname] - joins a specific room\n" +
                    "/er - exits the room you are in\n" +
                    "/ec - exits the chat and the program\n" +
                    "/help - shows this message\n\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void receiveMessage(String message)
    {
        try {
            socket.write(message + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getNickname()
    {
        return nickname;
    }

    public Chatroom getChatroom()
    {
        return chatroom;
    }

    public void setChatroom(Chatroom chatroom)
    {
        this.chatroom = chatroom;
    }
}
