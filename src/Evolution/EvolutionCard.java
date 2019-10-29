package evolution;

import view.SendMessage;
import gamelogic.Monster;

import java.util.ArrayList;

public class EvolutionCard {
    private KongEvolution kongEvolution;
    private AlienoidEvolution alienoidEvolution;
    private GigazaurEvolution gigazaurEvolution;

    public EvolutionCard(ArrayList<Monster> monsters, SendMessage sendMessage) {
        kongEvolution = new KongEvolution(monsters, sendMessage);
        alienoidEvolution = new AlienoidEvolution(monsters, sendMessage);
        gigazaurEvolution = new GigazaurEvolution(monsters, sendMessage);

    }

    public void powerUp(int i, Monster currentMonster) {
        if (currentMonster.getName().equals("Kong")) {
            kongEvolution.kongPowerUp(i, currentMonster);
        }

        if (currentMonster.getName().equals("Gigazaur")) {
            gigazaurEvolution.gigazaurPowerUp(i, currentMonster);
        }

        if (currentMonster.getName().equals("Alienoid")) {
            alienoidEvolution.alienoidPowerUp(i, currentMonster);
        }
    }



}