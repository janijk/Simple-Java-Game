package main;

import main.enums.State;

import java.awt.*;

public class StatusBar {
    private int level, currentHealth, initialHealth, green,red;

    public StatusBar() {
        this.level = 0;
        this.currentHealth = 300;
        this.initialHealth = currentHealth;
        this.green = 255;
        this.red = 0;
    }

    /**
     * Decreases current health by a percentage amount provided as a parameter
     * @param amountPercentage
     */
    public void decreaseHealth(int amountPercentage){
        currentHealth -= (int) (initialHealth * (amountPercentage / 100f));
        float denom = (float) currentHealth / initialHealth;
        green = Math.max((int) (255 * denom), 0);
        red = Math.min((int) (255 * (1 - denom)), 255);
    }

    public void tick(){

        // When health hits zero -> end the game
        if (currentHealth <= 0){
            Game.state = State.END;
        }
    }

    public void render(Graphics graphics){
        graphics.setColor(Color.gray);
        graphics.fillRoundRect(15,15,300,26,10,50);

        graphics.setColor(new Color(red,green,0));
        graphics.fillRoundRect(15,15,currentHealth,26,10,50);

        graphics.setColor(Color.white);
        graphics.drawRoundRect(15,15,300,26,10,50);

        graphics.drawString("Level: " + level, 15, 64);
    }

    public void plusLevel() {
        this.level += 1;
    }

    public int getLevel() {
        return level;
    }

    public void setCurrentHealth(int currentHealth) {
        this.currentHealth = currentHealth;
    }

    /**
     * Reset statusBar to starting values
     */
    public void reset(){
        this.currentHealth = initialHealth;
        this.level = 0;
        this.green = 255;
        this.red = 0;
    }
}
