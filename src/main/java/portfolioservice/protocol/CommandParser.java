package portfolioservice.protocol;

import java.net.ProtocolException;
import java.util.Locale;

public class CommandParser {

    public Command parse(String input) throws ProtocolException {
        String trimmed = input.trim();

        if (trimmed.isEmpty()) {
            throw new ProtocolException("Empty command");
        }

        String[] tokens = input.trim().split("\\s+");
        String commandName = tokens[0].toUpperCase(Locale.ROOT);

        return switch (commandName) {
            case "PING" -> parsePing(tokens);
            default -> throw new ProtocolException("Unknown command");
        };
    }

    private Command parsePing(String[] tokens) throws ProtocolException {
        if (tokens.length != 1) {
            throw new ProtocolException("PING does not  accept arguments");
        }

        return new PingCommand();
    }
}
