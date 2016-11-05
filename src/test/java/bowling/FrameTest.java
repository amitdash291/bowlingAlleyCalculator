package bowling;

import exceptions.InvalidScoreException;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class FrameTest {
    Frame frame;

    @Before
    public void setUp() throws Exception {
        frame = new Frame();
    }

    @Test(expected = InvalidScoreException.class)
    public void invalidScoreShouldThrowException() throws Exception {
        frame.updateScore(100);
    }

    @Test(expected = InvalidScoreException.class)
    public void updatingOnBeingOutOfTurnsShouldThrowException() throws Exception {
        frame.updateScore(1);
        frame.updateScore(1);
        frame.updateScore(1);
    }

    @Test(expected = InvalidScoreException.class)
    public void moreThanAStrikeInAFrameShouldThrowException() throws Exception {
        frame.updateScore(10);
        frame.updateScore(10);
    }

    @Test
    public void shouldBeStrikeWhenTenPinsAreDownOnFirstRoll() throws Exception {
        frame.updateScore(10);
        assertTrue(frame.isStrike());
        assertThat(frame.getTotalScore(), is(10));
        assertTrue(frame.isOutOfTurns());
    }

    @Test
    public void shouldBeSpareWhenTenPinsAreDownInTwoRolls() throws Exception {
        doRolls(frame, 5, 5);
        assertTrue(frame.isSpare());
        assertThat(frame.getTotalScore(), is(10));
        assertTrue(frame.isOutOfTurns());
    }

    @Test
    public void shouldOutOfTurnsAfterTwoRolls() throws Exception {
        doRolls(frame, 1, 2);
        assertThat(frame.getTotalScore(), is(3));
        assertTrue(frame.isOutOfTurns());
    }

    @Test
    public void shouldAllowBonusOnStrike() throws Exception {
        doStrike(frame);
        assertTrue(frame.isEligibleForBonus());
    }

    @Test
    public void shouldAllowBonusOnSpare() throws Exception {
        doRolls(frame, 6, 4);
        assertTrue(frame.isEligibleForBonus());
    }

    @Test
    public void shouldNotAllowBonusIfNeitherStrikeNorSpare() throws Exception {
        doRolls(frame, 6, 3);
        assertFalse(frame.isEligibleForBonus());
    }

    @Test
    public void shouldUpdateFirstRollScoreAsBonusOfPreviousFrameWhenItWasSpare() throws Exception {
        Frame previousFrame = new Frame();
        doRolls(previousFrame, 3, 7);
        Frame currentFrame = new Frame(previousFrame);
        doRolls(currentFrame, 4);
        assertThat(previousFrame.getTotalScore(), is(14));
        assertFalse(previousFrame.isEligibleForBonus());
        doRolls(currentFrame, 5);
        assertThat(previousFrame.getTotalScore(), is(14));
        assertFalse(previousFrame.isEligibleForBonus());
    }

    @Test
    public void shouldUpdateBothRollScoresAsBonusOfPreviousFrameWhenItWasStrike() throws Exception {
        Frame firstFrame = new Frame();
        doStrike(firstFrame);
        Frame currentFrame = new Frame(firstFrame);
        doRolls(currentFrame, 4);
        assertThat(firstFrame.getTotalScore(), is(14));
        assertTrue(firstFrame.isEligibleForBonus());
        doRolls(currentFrame, 5);
        assertThat(firstFrame.getTotalScore(), is(19));
        assertFalse(firstFrame.isEligibleForBonus());
    }

    @Test
    public void shouldUpdateTwoPreviousFramesWhenBothWereStrikes() throws Exception {
        Frame firstFrame = new Frame();
        doStrike(firstFrame);
        Frame secondFrame = new Frame(firstFrame);
        doStrike(secondFrame);
        assertThat(firstFrame.getTotalScore(), is(20));
        assertTrue(firstFrame.isEligibleForBonus());
        Frame thirdFrame = new Frame(secondFrame);
        doRolls(thirdFrame, 5);
        assertThat(firstFrame.getTotalScore(), is(25));
        assertFalse(firstFrame.isEligibleForBonus());
        assertThat(secondFrame.getTotalScore(), is(15));
        assertTrue(secondFrame.isEligibleForBonus());
        doRolls(thirdFrame, 4);
        assertThat(secondFrame.getTotalScore(), is(19));
        assertFalse(secondFrame.isEligibleForBonus());
    }

    @Test
    public void shouldUpdateTwoPreviousFramesOnThreeStrikeScenario() throws Exception {
        Frame firstFrame = new Frame();
        doStrike(firstFrame);
        Frame secondFrame = new Frame(firstFrame);
        doStrike(secondFrame);
        assertThat(firstFrame.getTotalScore(), is(20));
        assertTrue(firstFrame.isEligibleForBonus());
        Frame thirdFrame = new Frame(secondFrame);
        doStrike(thirdFrame);
        assertThat(firstFrame.getTotalScore(), is(30));
        assertFalse(firstFrame.isEligibleForBonus());
        assertThat(secondFrame.getTotalScore(), is(20));
        assertTrue(secondFrame.isEligibleForBonus());
    }

    private void doStrike(Frame frameToDoStrikeFor) throws Exception {
        doRolls(frameToDoStrikeFor, 10);
    }

    private void doRolls(Frame frameToRollFor, int... scores) throws Exception {
        for(int score: scores) {
            frameToRollFor.updateScore(score);
        }
    }
}