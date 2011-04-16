package towerdefence.engine.entity;

import java.util.ArrayList;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;
import towerdefence.engine.component.Component;

/**
 *
 * Tower - An entity that deals with distance checking against critters
 * and shoots critters
 *
 * @author Jiv Dhaliwal <jivdhaliwal@gmail.com>
 */
public class Tower extends Entity {

    ArrayList<Critter> critterList = null;
    Critter targetCritter = null;

    private final Image yellowLaser;
    private final Image blueLaser;
    private final Image greenLaser;
    private final Image purpleLaser;
    private final Image redLaser;
    private Image laser;

    float range;
    float damagePerSec;

    private int shootingCounter;
    private int colourCounter=0;
    private int laserCounter=0;

    boolean isShooting;


    public Tower(String id) throws SlickException{
        super(id);
        range = 128;
        damagePerSec = 25;

        this.rotation=0;

        yellowLaser = new Image("data/sprites/laser/yellow.png");
        blueLaser = new Image("data/sprites/laser/blue.png");
        greenLaser = new Image("data/sprites/laser/green.png");
        purpleLaser = new Image("data/sprites/laser/purple.png");
        redLaser = new Image("data/sprites/laser/red.png");
        laser = greenLaser;
    }

    /*
     * Update the list of critters the tower iterates through to find a target
     */
    public void updateCritterList(ArrayList<Critter> critterList) {
        this.critterList = critterList;
    }

    private void findClosestCritter() {

        // If critters on the map
        if(critterList!=null) {

            // If no critters is locked on
            if(targetCritter==null) {
                Critter tempTarget = new Critter("test");
                // Set initial position very far away from the map
                tempTarget.setPosition(new Vector2f(-1000f,-1000f));
                for(Critter enemy : critterList) {
                    float critterDistance = this.getPosition().distance(enemy.getPosition());
                    float tempDistance = this.getPosition().distance(tempTarget.getPosition());

                    // First check the critter is in range of the tower
                    // Then check if it is closer than the tempCritter
                    // Do this for all critters to eventually find the closest one
                    if(critterDistance < range) {
                        if(critterDistance < tempDistance) {
                            targetCritter=enemy;
                            tempTarget=enemy;
                        }
                    }
                    // Remnants of the multicolour lazers
                    // colourCounter++;
                }
            } else if (targetCritter!=null) {
                // If the critter is out of range or dead, find a new target
                // Else shoot the
                if(this.getPosition().distance(targetCritter.getPosition()) >= range) {
                    targetCritter = null;
                } else if(targetCritter.isDead()) {
                    targetCritter=null;
                } else {
                    shootCritter(targetCritter);
                }
            }
        }
    }

    private void shootCritter(Critter critter) {
        critter.takeDamage(damagePerSec/10f);
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sb, int delta)
    {
        shootingCounter-=delta;
        laserCounter-=delta;

        if(shootingCounter<=0) {
            findClosestCritter();
            shootingCounter=100;
        }

        for(Component component : components)
        {
            component.update(gc,sb,delta);
        }

    }

    @Override
    public void render(GameContainer gc, StateBasedGame sb, Graphics gr)
    {

        if(renderComponent != null) {
            renderComponent.render(gc, sb, gr);
        }
        // Laser shooting
        // Check tower has a target
        if (targetCritter != null) {

            gr.rotate(this.getPosition().x + 16, this.getPosition().y + 16,
                    (float) (targetCritter.getPosition().sub(this.getPosition())).getTheta()-90);
            // Draw lazer and extend it using the distance from the tower to the
            // target critter
            laser.draw(this.getPosition().x, this.getPosition().y+16, 32,
                    this.getPosition().distance(targetCritter.getPosition()));
            gr.rotate(this.getPosition().x + 16, this.getPosition().y + 16,
                    (float) -(targetCritter.getPosition().sub(this.getPosition())).getTheta()+90);
        }

    }
    
}
