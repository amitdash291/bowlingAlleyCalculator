package bowling;

import exceptions.InvalidScoreException;

import java.util.ArrayList;
import java.util.List;

class Frame {
    private List<Integer> scores;
    private Frame previousFrameEligibleForBonus = null;

    public Frame() {
        this.scores = new ArrayList<>();
    }

    public Frame(Frame frameEligibleForBonus) {
        this();

        if (frameEligibleForBonus.isEligibleForBonus()) {
            previousFrameEligibleForBonus = frameEligibleForBonus;
        }
    }

    void updateScore(int score) throws InvalidScoreException {
        if (!isOutOfTurns() && isValidScore(score)) {
            scores.add(score);
            if (previousFrameEligibleForBonus != null && previousFrameEligibleForBonus.isEligibleForBonus()) {
                previousFrameEligibleForBonus.updateBonus(score);
            }
        } else {
            throw new InvalidScoreException();
        }
    }

    private void updateBonus(int bonus) {
        if (isEligibleForBonus()) {
            scores.add(bonus);
            if (previousFrameEligibleForBonus != null && isStrike() && previousFrameEligibleForBonus.isEligibleForBonus()) {
                previousFrameEligibleForBonus.updateBonus(bonus);
            }
        }
    }

    int getTotalScore() {
        return scores.stream().mapToInt(Integer::intValue).sum();
    }

    boolean isOutOfTurns() {
        return isStrike() || isSpare() || isNormallyEnded();
    }

    boolean isEligibleForBonus() {
        return ((isStrike() || isSpare()) && scores.size() < 3);
    }

    boolean isStrike() {
        return isPlayedAtLeastOnce() && scores.get(0) == 10;
    }

    boolean isSpare() {
        return scores.size() == 2 && getTotalScore() == 10;
    }

    private boolean isNormallyEnded() {
        return scores.size() == 2 && getTotalScore() < 10;
    }

    private boolean isPlayedAtLeastOnce() {
        return scores.size() > 0;
    }

    private boolean isValidScore(int score) {
        return score <= (10 - getTotalScore());
    }
}
