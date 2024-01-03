public enum CommandWord {
    GO("go"), BACK("back"), HELP("help"), LOOK("look"), EAT("eat"), GRAB("grab"), QUIT("quit"), UNKNOWN("");
    private String commandString;

    CommandWord(String word) {
        commandString = word;
    }

    public String getCommandString() {
        return commandString;
    }
}
