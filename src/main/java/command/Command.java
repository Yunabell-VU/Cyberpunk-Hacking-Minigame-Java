package command;

public interface Command {
    boolean
    executable();

    void
    execute();
}