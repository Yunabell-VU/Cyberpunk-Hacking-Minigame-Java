package game;

import entity.*;

import java.util.List;

public class PuzzleHandler {
    Puzzle puzzle;

    public
    PuzzleHandler(Puzzle puzzle){
        this.puzzle = puzzle;
    }

    public void
    updatePuzzle(Puzzle puzzle, Coordinate clickedCellPosition){
        this.puzzle = puzzle;
        updateCodeMatrix(clickedCellPosition);
        updateBuffer();
        updateDaemons();
    }

    //overloading
    public void
    updatePuzzle(Puzzle puzzle){
        this.puzzle = puzzle;
    }

    public boolean
    isAllDaemonsChecked() {
        for (Daemon daemon : puzzle.getDaemons()) {
            if (daemon.isNotChecked()) return false;
        }
        return true;
    }

    public void
    markUncheckedDaemonsFailed() {
        List<Daemon> tmpSeq = puzzle.getDaemons();
        for (Daemon sequence : tmpSeq) {
            if (sequence.isNotChecked()) sequence.setDaemonFailed();
        }
    }

    public int
    countUncheckedDaemons(String text) {
        int counter = 0;
        for (Daemon daemon : puzzle.getDaemons()) {
            if (daemon.isNotChecked()) {
                if(text.equals("SUCCEEDED") && daemon.isSucceeded()) {
                    counter++;
                    daemon.setChecked();
                }
                if(text.equals("FAILED") && daemon.isFailed()) {
                    counter++;
                    daemon.setChecked();
                }
            }
        }
        return counter;
    }

    private void
    updateCodeMatrix(Coordinate clickedCellPosition) {
        CodeMatrix codeMatrix = puzzle.getCodeMatrix();
        codeMatrix.updateCellPicked(clickedCellPosition);
        codeMatrix.disableAllCells();

        if (codeMatrix.isRowAvailable()) codeMatrix.setOneColAvailable();
        else codeMatrix.setOneRowAvailable();

        if (puzzle.getBuffer().isBufferFull()) codeMatrix.disableAllCells();

    }

    private void
    updateBuffer() {
        if (!puzzle.getBuffer().isBufferFull())
            puzzle.getBuffer().addCellToBuffer(puzzle.getCodeMatrix().getPickedCharacter());
    }

    private void
    updateDaemons() {
        int bufferCounter = puzzle.getBuffer().getBufferCounter();
        List<Daemon> daemons = puzzle.getDaemons();

        for (Daemon daemon : daemons) {
            if (daemon.isNotChecked()) {
                updateDaemonCells(daemon, bufferCounter);

                if (daemon.isLastCellMatched()) daemon.setDaemonSucceeded();
                if (isDaemonExceedsBuffer(daemon)) daemon.setDaemonFailed();
            }
        }
    }

    private void
    updateDaemonCells(Daemon daemon, int bufferCounter) {
        daemon.setAllDaemonCellsUnSelected();
        DaemonCell cellWaitingToBeCheck = daemon.getDaemonCell(bufferCounter - 1);

        updateStatesOfMatchedDaemonCell(cellWaitingToBeCheck);

        if (!cellWaitingToBeCheck.isMatched()) {
            daemon.setAllDaemonCellsUnMatched();
            alignDaemonCellWithBuffer(daemon, bufferCounter);

            cellWaitingToBeCheck = daemon.getDaemonCell(bufferCounter - 1);//switch waiting cell to first unEmpty cell
            updateStatesOfMatchedDaemonCell(cellWaitingToBeCheck);

            if(!cellWaitingToBeCheck.isMatched()) daemon.addEmptyCell();
        }
    }

    private void
    updateStatesOfMatchedDaemonCell(DaemonCell waitingCell){
        String pickedCharacter = puzzle.getCodeMatrix().getPickedCharacter();
        String lastBufferCharacter = puzzle.getBuffer().getLastCodeInBuffer();
        if (waitingCell.isMatch(pickedCharacter)) waitingCell.setSelected(true);
        if (waitingCell.isMatch(lastBufferCharacter)) waitingCell.setMatched(true);
    }

    private void
    alignDaemonCellWithBuffer(Daemon daemon, int bufferCounter) {
        int positionOfWaitingCell = getWaitingDaemonCellPosition(daemon, bufferCounter);
        for (int i = 0; i < positionOfWaitingCell-1; i++) {
            daemon.addEmptyCell();
        }
    }

    private int
    getWaitingDaemonCellPosition(Daemon daemon,int bufferCounter){
        int emptyCellCounter = 0;
        for (int i = 0; i < bufferCounter - 1; i++) {
            if (daemon.getDaemonCell(i).getCode().equals("")) emptyCellCounter += 1;
        }
        return bufferCounter - emptyCellCounter;
    }

    private boolean
    isDaemonExceedsBuffer(Daemon daemon) {
        return daemon.getDaemonCells().size() > puzzle.getBuffer().getBufferSize();
    }
}
