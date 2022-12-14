package main.util;

import main.enums.ID;
import main.objects.GameObject;

import java.awt.*;
import java.util.LinkedList;

public class Handler {
    private LinkedList<GameObject> gameObj = new LinkedList<>();

    // Loop through all GameObjects and call their tick() method
    public void tick(){
        try {
            for (int i = 0 ; i < gameObj.size(); i++){
                GameObject t = gameObj.get(i);
                t.tick();
            }
        }catch (IndexOutOfBoundsException e){
            // Ignore index out of bounds
            System.out.println("ignored at Handler.tick(): " + e);
        } catch (NullPointerException npe){
            // Ignore null pointer
            System.out.println("ignored at Handler.tick(): " + npe);
        }
    }

    // Loop through all GameObjects and call their render() method
    public void render(Graphics graphs){
        try {
            for (int i = 0 ; i < gameObj.size(); i++){
                GameObject t = gameObj.get(i);
                t.render(graphs);
            }
        }catch (IndexOutOfBoundsException iobe){
            // Ignore index out of bounds
            System.out.println("ignored at Handler.render(Graphics graphs): " + iobe);
        } catch (NullPointerException npe){
            // Ignore null pointer
            System.out.println("ignored at Handler.render(Graphics graphs): " + npe);
        }
    }

    public GameObject getGameObject(ID id){
        for (GameObject g : gameObj){
            if (g.getId() == id) return g;
        }
        return null;
    }

    public GameObject getGameObject(int indx){
        return gameObj.get(indx);
    }

    public LinkedList<GameObject> getGameObjects(){
        return gameObj;
    }

    public void addGameObject(GameObject go){
        gameObj.add(go);
    }

    public void removeGameObject(GameObject go){
        gameObj.remove(go);
    }
    public void removeAllGameObjects(){
        gameObj.clear();
    }

    public void removeGameObjectsById(ID id){
        for (int i = 0 ; i < gameObj.size(); i++){
            GameObject go = gameObj.get(i);
            if (go.getId() == id) gameObj.remove(go);
        }
    }
}
