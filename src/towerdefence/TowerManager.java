/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package towerdefence;

import java.util.ArrayList;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;
import towerdefence.engine.component.ImageRenderComponent;
import towerdefence.engine.entity.*;

/**
 *
 * Handles the list of towers
 * Includes adding to, deleting from the list
 * Stores an updated list of the critters in the map
 * Handles the update and render methods for towers
 *
 * @author Jiv Dhaliwal <jivdhaliwal@gmail.com>
 */
public class TowerManager {

    private ArrayList<Tower> towerList = new ArrayList<Tower>();
    private ArrayList<Critter> critterList;
    private final Image towerSprite;

    public TowerManager() throws SlickException {

        towerSprite = new Image("data/sprites/towers/firetower.png");

    }


    public void addTower(String id, Vector2f position, Image towerType) throws SlickException {
        Tower tower = new Tower(id);
        tower.setPosition(position);
        tower.AddComponent(new ImageRenderComponent("CritterRender", towerType));
        towerList.add(tower);
    }

    /*
     * Default Tower when no type is defined
     */
    public void addTower(String id, Vector2f position) throws SlickException {
        Tower tower = new Tower(id);
        tower.setPosition(position);
        tower.AddComponent(new ImageRenderComponent("CritterRender", towerSprite));
        towerList.add(tower);
    }

    public void deleteTower(Tower tower) {
        towerList.remove(tower);
    }

    public ArrayList<Tower> getTowers() {
        return towerList;
    }

    /*
     * Update the critterList that towers use for finding closest critter
     * Currently called during each update, using a counter to delay it
     * Possible to only update when a critter dies.
     */
    public void updateCritterList(ArrayList<Critter> critterList) {
        this.critterList = critterList;
    }

    public void update(GameContainer gc, StateBasedGame sb, int delta) {
        for(Tower tower : towerList) {
            tower.updateCritterList(critterList);
            tower.update(gc, sb, delta);
        }
    }
    
    public void render(GameContainer gc, StateBasedGame sb, Graphics gr) {
        for(Tower tower : towerList) {
            tower.render(gc, sb, gr);
        }
    }




}