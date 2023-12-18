public enum CommandWord {
    GO("go"), BACK("back"), HELP("?"), LOOK("look"), EAT("eat"), GRAB("take"), QUIT("quit"), UNKNOWN("");
    private String commandString;

    CommandWord(String word) {
        commandString = word;
    }

    public String getCommandString() {
        return commandString;
    }
}
