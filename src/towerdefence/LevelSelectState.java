/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package towerdefence;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.gui.MouseOverArea;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;
import towerdefence.engine.levelLoader.LevelLoader;

/**
 *
 * @author Jiv Dhaliwal <jivdhaliwal@gmail.com>
 */
public class LevelSelectState extends BasicGameState implements ComponentListener {
    
    int stateID = 0;
    
    private RenderWater waterAnimation;
    
    private Image guiBackground;
    private int guiLeftX = ((21*32) + 16);
    private int guiTopY = 48;
    private int guiBottomY = 656;
    
    private int easyLevelTopY = 140;
    private int mediumLevelTopY = 280;
    private int hardLevelTopY = 425;
    
    private int TILESIZE = 32;
    
    private MouseOverArea snakeArea,forkArea,zigzagArea,
            squareArea,leftArea,haichArea,loadArea,quitArea;
    
    private Image snake;
    private Image fork;
    private Image zigzag;
    private Image square;
    private Image left;
    private Image haich;
    private Image load;
    private Image quit;
    
    private LevelLoader level=null;
    private TiledMap map=null;
    private StateBasedGame game;
    private UnicodeFont unicodeFont;
    
    TextField helpText;
    private boolean showHelp=true;
    private GameContainer container;
    
    
    
    LevelSelectState() {
    }

    @Override
    public int getID() {
        return 0;
    }

    @Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
        
        guiBackground = new Image("gui/level_select_overlay.png");
        
        waterAnimation = new RenderWater(container.getWidth()/32,
                container.getHeight()/32);
        
        zigzag = new Image("gui/levels/zigzag.png");
        snake = new Image("gui/levels/snake.png");
        fork = new Image("gui/levels/fork.png");
        square = new Image("gui/levels/square.png");
        left = new Image("gui/levels/left.png");
        haich = new Image("gui/levels/haich.png");
        load = new Image("gui/levels/load.png");
        quit = new Image("gui/levels/quit.png");
        
        zigzagArea = new MouseOverArea(container, zigzag, guiLeftX, easyLevelTopY, 140, 40, this);
        squareArea = new MouseOverArea(container, square, guiLeftX, easyLevelTopY+40, 140, 40, this);
        snakeArea = new MouseOverArea(container, snake, guiLeftX, mediumLevelTopY, 140, 40, this);
        forkArea = new MouseOverArea(container, fork, guiLeftX, mediumLevelTopY+40,140, 40, this);
        leftArea = new MouseOverArea(container, left, guiLeftX, hardLevelTopY,140, 40, this);
        haichArea = new MouseOverArea(container, haich, guiLeftX, hardLevelTopY+40,140, 40, this);
        loadArea = new MouseOverArea(container, load, guiLeftX-5, 540,140, 40, this);
        quitArea = new MouseOverArea(container, quit, guiLeftX-5, 600,140, 40, this);
        
        
        snakeArea.setMouseOverColor(new Color(1, 1f, 0.7f, 0.8f));
        forkArea.setMouseOverColor(new Color(1, 1f, 0.7f, 0.8f));
        zigzagArea.setMouseOverColor(new Color(1, 1f, 0.7f, 0.8f));
        leftArea.setMouseOverColor(new Color(1, 1f, 0.7f, 0.8f));
        haichArea.setMouseOverColor(new Color(1, 1f, 0.7f, 0.8f));
        squareArea.setMouseOverColor(new Color(1, 1f, 0.7f, 0.8f));
        loadArea.setMouseOverColor(new Color(1, 1f, 0.7f, 0.8f));
        quitArea.setMouseOverColor(new Color(1, 1f, 0.7f, 0.8f));
        
        unicodeFont = new UnicodeFont("fonts/Jellyka_Estrya_Handwriting.ttf", 100, false, false);
        unicodeFont.getEffects().add(new ColorEffect(java.awt.Color.BLACK));
        
        
        helpText = new TextField(container, container.getGraphics().getFont(), 
                (container.getWidth()/2-300), (container.getHeight()/2-80), 475, 200);
        helpText.setText("Game Help - F1 to toggle\n\nPress 1, 2 or 3 to select a tower\n"
                + "Left click to place a selected tower\n"
                + "Right click to cancel current selection\n"
                + "Mouse over a tower to see its info\n"
                + "Mouse over a tower and press delete to sell a tower\n"
                + "R : Restart\n"
                + "Esc : Level select screen\n"
                + "Manage your money and keep the critters at bay!");
        
        
        this.game = game;
        this.container = container;
        
        level = new LevelLoader("levels/square.xml");
        map = new TiledMap(level.getMapPath());
    }

    @Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        g.setClip(TILESIZE, TILESIZE, TILESIZE * (25), TILESIZE * (20));

        waterAnimation.render(container, game, g);
        
        if(map!=null) {
            map.render(0, 0, 1);
            map.render(0, 0, 2);
        }
        
        guiBackground.draw(21*32, 0);
        
        snakeArea.render(container, g);
        forkArea.render(container, g);
        zigzagArea.render(container, g);
        squareArea.render(container, g);
        leftArea.render(container, g); 
        haichArea.render(container, g);
        loadArea.render(container, g);
        quitArea.render(container, g);
        
        
        if(showHelp){
            helpText.render(container, g);
        }
                
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        unicodeFont.loadGlyphs(1);
        
        Input input = container.getInput();
        
        if(input.isKeyPressed(Input.KEY_F1)){showHelp = !showHelp;}
        
    }

    @Override
    public void componentActivated(AbstractComponent source)  {
        try {
        if (source == zigzagArea) {
            
                level = new LevelLoader("levels/zigzag.xml");
                map = new TiledMap(level.getMapPath());
            
        }
        if (source == forkArea) {
            
                level = new LevelLoader("levels/fork.xml");
                map = new TiledMap(level.getMapPath());
            
        }
        if (source == snakeArea) {
            
                level = new LevelLoader("levels/snake.xml");
                map = new TiledMap(level.getMapPath());
            
        }
        if (source == leftArea) {
            
                level = new LevelLoader("levels/left.xml");
                map = new TiledMap(level.getMapPath());
            
        }
        if (source == haichArea) {
            
                level = new LevelLoader("levels/haich.xml");
                map = new TiledMap(level.getMapPath());
            
        }
        if (source == squareArea) {
            
                level = new LevelLoader("levels/square.xml");
                map = new TiledMap(level.getMapPath());
            
        }
        if (source == loadArea) {
            
            if (level != null) {
                GameplayState gameplaystate = new GameplayState();
                gameplaystate.setLevel(level);
                game.addState(gameplaystate);
                game.enterState(TowerDefence.GAMEPLAYSTATE);
            }
            
        }
        if (source == quitArea) {
            container.exit();
        }
        } catch (SlickException ex) {
                Logger.getLogger(LevelSelectState.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
}
