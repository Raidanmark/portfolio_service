package portfolioservice.protocol;

public class CommandDispatcher {

    public String dispatch(Command command) {
        if (command instanceof PingCommand) {
            return "PONG";
        }

        throw new IllegalArgumentException("Unsupported command");
    }
}
