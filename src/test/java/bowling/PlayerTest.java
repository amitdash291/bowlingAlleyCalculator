package bowling;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class PlayerTest {
    Player player;

    @Before
    public void setUp() throws Exception {
        player = new Player();
    }

    @Test
    public void testTwelveStrikesScenario() throws Exception {
        twelveStrikes();
        assertThat(player.getTotalScore(), is(300));
    }

    @Test
    public void testUnluckyScenario() throws Exception {
        theunluckyScenario();
        assertThat(player.getTotalScore(), is(90));
    }

    @Test
    public void testRandomScenarios() throws Exception {
        player = new Player();
        elevenStrikesScenario();
        assertThat(player.getTotalScore(), is(299));

        player = new Player();
        tenStrikesBonusSpareScenario1();
        assertThat(player.getTotalScore(), is(289));

        player = new Player();
        tenStrikesBonusSpareScenario2();
        assertThat(player.getTotalScore(), is(285));

        player = new Player();
        nineStrikesLastSpareScenario();
        assertThat(player.getTotalScore(), is(270));

        player = new Player();
        nineStrikesLastNormalScenario();
        assertThat(player.getTotalScore(), is(263));

        player = new Player();
        randomScenario();
        assertThat(player.getTotalScore(), is(173));

    }

    private void playScenario(int... scores) throws Exception {
        for (int s : scores) {
            player.roll(s);
        }
    }

    private void twelveStrikes() throws Exception {
        //Total: 300
        playScenario(10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10);
    }

    private void elevenStrikesScenario() throws Exception {
        //Total: 299
        playScenario(10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 9);
    }

    private void tenStrikesBonusSpareScenario1() throws Exception {
        //Total: 289
        playScenario(10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 9, 1);
    }

    private void tenStrikesBonusSpareScenario2() throws Exception {
        //Total: 285
        playScenario(10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 5, 5);
    }

    private void nineStrikesLastSpareScenario() throws Exception {
        //Total: 270
        playScenario(10, 10, 10, 10, 10, 10, 10, 10, 10, 5, 5, 5);
    }

    private void nineStrikesLastNormalScenario() throws Exception {
        //Total: 263
        playScenario(10, 10, 10, 10, 10, 10, 10, 10, 10, 5, 4);
    }

    private void randomScenario() throws Exception {
        //Total: 173
        playScenario(9, 1, 8, 1, 6, 3, 5, 0, 10, 10, 10, 5, 5, 7, 3, 10, 9, 1);
    }

    private void theunluckyScenario() throws Exception {
        playScenario(1, 8, 1, 8, 1, 8, 1, 8, 1, 8, 1, 8, 1, 8, 1, 8, 1, 8, 1, 8);
    }

}