package towerdefence;

import java.util.ArrayList;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.MouseListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;
import org.newdawn.slick.util.pathfinding.AStarPathFinder;
import org.newdawn.slick.util.pathfinding.Path;
import org.newdawn.slick.util.pathfinding.PathFinder;
import towerdefence.engine.component.MouseMovement;

import towerdefence.engine.entity.Entity;
import towerdefence.engine.pathfinding.PathMap;
import towerdefence.engine.pathfinding.UnitMover;

/**
 *
 * Used for testing path testing.
 * Click and drag to create paths from the cursor to 1,1
 *
 * @author Jiv
 */
public class PathTestState extends BasicGameState {

    int stateID = -1;

    int critterCount;
    int generateCounter=0;

    // Map used to find critter paths
    private PathMap pathmap;
    // Path finder used to search the pathmap
    private PathFinder finder;
    // Gives the last path found for the current unit
    private Path path;


    private TiledMap map;
    private Image pathSprite;

    CritterFactory critterFactory;
    ArrayList<Entity> critters = new ArrayList<Entity>();


    PathTestState(int stateID) {
        this.stateID = stateID;
    }

    @Override
    public int getID() {
        return 2;
    }

    public void init(GameContainer container, StateBasedGame game) throws SlickException {

        pathSprite = new Image("data/sprites/path.png");
        map = new TiledMap("data/maps/path1_4.tmx");

        pathmap = new PathMap(map);
        finder = new AStarPathFinder(pathmap, 500, false);
        path = finder.findPath(new UnitMover(3), map.getWidth()-1, map.getHeight()-1, 1, 1);

        Entity critter = new Entity("test");
        critter.setPosition(new Vector2f(100f,100f));
        critter.AddComponent(new MouseMovement("CritterMouse"));

        critters.add(critter);

    }

    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {

        Input input = container.getInput();

        if(input.isKeyPressed(Input.KEY_F1)) {
            game.enterState(TowerDefence.GAMEPLAYSTATE);
        }

        generateCounter-=delta;
        input.addMouseListener(new MouseListener(){

            public void mouseWheelMoved(int change) {
            }

            public void mouseClicked(int button, int x, int y, int clickCount) {
            }

            public void mousePressed(int button, int x, int y) {
            }

            public void mouseReleased(int button, int x, int y) {
            }

            public void mouseMoved(int oldx, int oldy, int newx, int newy) {
                
            }

            public void mouseDragged(int oldx, int oldy, int newx, int newy) {
                if(generateCounter<=0) {
                    path = finder.findPath(new UnitMover(3),
                            (int) Math.floor((newx / 32)), (int) Math.floor((newy / 32)), 1, 1);
                    generateCounter=10;
                }
            }

            public void setInput(Input input) {
            }

            public boolean isAcceptingInput() {
                return true;
            }

            public void inputEnded() {
            }

            public void inputStarted() {
            }
        });
        for(Entity enemy : critters)
            enemy.update(container, game, delta);

    }

    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {

        map.render(0,0);

        // Render the path for testing
        for (int x = 0; x < map.getWidth(); x++) {
            for (int y = 0; y < map.getHeight(); y++) {
                if(path != null) {
                    if(path.contains(x,y)) {
                        pathSprite.draw(x*32,y*32);
                    }
                }
            }
        }

    }


}