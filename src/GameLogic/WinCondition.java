package gamelogic;

import view.SendMessage;

import java.util.ArrayList;

public class WinCondition {
    private ArrayList<Monster> monsters;
    private SendMessage sendMessage;
    private int alive;
    private String aliveMonster;

    public WinCondition(ArrayList<Monster> monsters, SendMessage sendMessage) {
        this.monsters = monsters;
        this.sendMessage = sendMessage;
        this.alive = 0;
        this.aliveMonster = "";
    }

    public void winCondition() {
        if (monstersAlive() || numStars()) {
            System.exit(0);
        }
    }

    private boolean numStars() {
        if (alive == 1) {
            for (int victory = 0; victory < this.monsters.size(); victory++) {
                this.sendMessage.sendMessage(victory,
                        "Victory: " + this.aliveMonster + " has won by being the only one alive\n");
            }
            return true;
        }
        return false;
    }

    private boolean monstersAlive() {
        for (int mon = 0; mon < this.monsters.size(); mon++) {
            if (this.monsters.get(mon).getStars() >= 5) {
                for (int victory = 0; victory < this.monsters.size(); victory++) {
                    this.sendMessage.sendMessage(victory,
                            "Victory: " + monsters.get(mon).getName() + " has won by stars\n");
                }
                return true;
            }
            if (this.monsters.get(mon).getCurrentHealth() > 0) {
                this.alive++;
                this.aliveMonster = this.monsters.get(mon).getName();
            }
        }
        return false;
    }
}