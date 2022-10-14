package ru.itmo.p3131.student18.client;

import ru.itmo.p3131.student18.client.gui.LoginFrame;
import ru.itmo.p3131.student18.client.tools.CommandSaver;
import ru.itmo.p3131.student18.client.tools.ScriptExecutive;
import ru.itmo.p3131.student18.client.tools.Sender;
import ru.itmo.p3131.student18.client.tools.UserAction;
import ru.itmo.p3131.student18.client.tools.readers.ClientCommandReader;
import ru.itmo.p3131.student18.interim.exeptions.ObjectFieldsValueException;
import ru.itmo.p3131.student18.interim.messages.ClientMessage;
import ru.itmo.p3131.student18.interim.objectclasses.HumanBeing;
import ru.itmo.p3131.student18.interim.exeptions.CommandScannerException;
import ru.itmo.p3131.student18.interim.exeptions.ScriptException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Client {
    private final ClientCommandReader clientCommandReader = new ClientCommandReader();
    private final Sender sender = new Sender(9000);
    private final List<ScriptExecutive> scriptExecutive = new ArrayList<>();
    private int currentScriptExecutive = -1;
    private final UserAction userAction = new UserAction();
    private String user;
    private final CommandSaver commandSaver = new CommandSaver();

    public void extendedCommandFetch(String[] command, HumanBeing human) throws CommandScannerException, ScriptException, ObjectFieldsValueException {
            if (scriptExecutive.size() > 0 && scriptExecutive.get(currentScriptExecutive).isScriptIsRunning()) {
                if (!scriptExecutive.get(currentScriptExecutive).run(commandSaver)) {
                    scriptExecutive.remove(currentScriptExecutive);
                    currentScriptExecutive--;
                }
            } else {
                clientCommandReader.startScanning(command);
                commandSaver.setFoundCommand(clientCommandReader.getFoundCommand());
                commandSaver.setParams(clientCommandReader.getArgs());
            }
            commandSaver.setObject(null);
            switch (commandSaver.getFoundCommand()) {
                case "add", "update" -> commandSaver.setObject(human);
                case "execute_script" -> {
                    currentScriptExecutive++;
                    scriptExecutive.add(new ScriptExecutive());
                    scriptExecutive.get(currentScriptExecutive).initialize(commandSaver.getParams()[0]);
                }
                case "login" -> {
                    userAction.logUser(command[1], command[2]);
                    commandSaver.setParams(new String[]{userAction.getInsertedLogin(), userAction.getInsertedEncryptedPassword()});
                }
                case "register" -> {
                    userAction.registerUser(command[1], command[2], command[3]);
                    commandSaver.setParams(new String[]{userAction.getInsertedLogin(), userAction.getInsertedEncryptedPassword()});
                }
            }
            /*if (userAction.getUser() == null && (!"login".equalsIgnoreCase(commandSaver.getFoundCommand()) && !"register".equalsIgnoreCase(commandSaver.getFoundCommand()))) {
                System.out.println("To get access to all commands you have to login.");
            */

    }

    public void dataTransferring() throws InterruptedException {
        while (true) {
            ClientMessage message = new ClientMessage(commandSaver.getFoundCommand(), commandSaver.getParams(), user, commandSaver.getObject());
            sender.send(message);
            Thread connectionThread = new Thread(() -> {
                try {
                    sender.receive();
                } catch (IOException e) {
                    System.out.println("Failed to receive message.");
                }
            });
            connectionThread.start();
            connectionThread.join(2000);
            if (sender.getServerMessage() == null) {
                System.out.println("No connection to server.");
            } else {
                break;
            }
        }
    }

    public String[] printResults() {
        String[] commandResult = { sender.getCommandResultMessage(), sender.getErrMessage() };
        return commandResult;
    }

    public void authorizationComplete() {
        userAction.completeAuthorization();
        user = userAction.getUser().getLogin();
    }

    public String getUserName() {
        return user;
    }

    public String[] run(String[] command, HumanBeing human) {
        try {
            extendedCommandFetch(command, human);
            dataTransferring();
            return printResults();
        } catch(CommandScannerException | InterruptedException | ScriptException | ObjectFieldsValueException e){
            System.out.println(e.getMessage());
            return new String[] {null, e.getMessage()}; //"",""
        }
    }

    public static void main(String[] args) {
        Client client = new Client();
        LoginFrame loginFrame = new LoginFrame(client);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            client.sender.closeSocket();
            System.out.println("Client stops working. Good bye!");
        }));
        }
    }
