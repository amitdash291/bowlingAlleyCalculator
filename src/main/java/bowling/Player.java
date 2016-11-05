package bowling;

import exceptions.GameOverException;
import exceptions.InvalidScoreException;

import java.util.ArrayList;
import java.util.List;

public class Player {
    Frame currentFrame;
    List<Frame> frames;

    public Player() {
        this.frames = new ArrayList<>();
        this.currentFrame = new Frame();
    }

    private void resetCurrentFrame() {
        if (currentFrame.isEligibleForBonus()) {
            Frame frameEligibleForBonus = currentFrame;
            currentFrame = new Frame(frameEligibleForBonus);
        } else {
            currentFrame = new Frame();
        }
    }

    private boolean isLastFrame() {
        return frames.size() >= 10;
    }

    private boolean isCurrentFrameOutOfTurns() {
        return currentFrame.isOutOfTurns();
    }

    public void roll(int score) throws GameOverException, InvalidScoreException {
        if (isLastFrame() && !frames.get(9).isEligibleForBonus()) {
            //Game should continue till last frame is eligible for bonus
            throw new GameOverException();
        }

        if (!isCurrentFrameOutOfTurns()) {
            currentFrame.updateScore(score);
        }

        if (isCurrentFrameOutOfTurns()) {
            if (!isLastFrame()) {
                frames.add(currentFrame);
            }
            resetCurrentFrame();
        }
    }

    public int getTotalScore() {
        if (frames == null) {
            return 0;
        }

        return frames.stream()
                .map(Frame::getTotalScore)
                .mapToInt(Integer::intValue)
                .sum();
    }
}
