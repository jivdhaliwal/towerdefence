package towerdefence.engine.component;

/**
 *
 * @author Jiv Dhaliwal <jivdhaliwal@gmail.com>
 */

import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.state.StateBasedGame;

public class TopDownMovement extends Component {

    float direction;
    float speed;

    public TopDownMovement( String id )
    {
        this.id = id;
    }

    public float getSpeed()
    {
        return speed;
    }

    public float getDireciton()
    {
        return direction;
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sb, int delta) {

        float rotation = entity.getRotation();
        float scale = entity.getScale();

        Vector2f position = entity.getPosition();

        Input input = gc.getInput();

        if(input.isKeyDown(Input.KEY_DOWN))
        {
            position.y += 0.1f*delta;
        }
        if(input.isKeyDown(Input.KEY_UP))
        {
            position.y -= 0.1f*delta;
        }
        if(input.isKeyDown(Input.KEY_LEFT))
        {
            position.x -= 0.1f*delta;
        }
        if(input.isKeyDown(Input.KEY_RIGHT))
        {
            position.x += 0.1f*delta;
        }

        if(input.isKeyDown(Input.KEY_U))
        {
            scale += 0.001f*delta;
        }

        if(input.isKeyDown(Input.KEY_I))
        {
            scale -= 0.001f*delta;
        }
        
        if(input.isKeyDown(Input.KEY_J))
        {
            rotation += 3;
        }

        if(input.isKeyDown(Input.KEY_K))
        {
            rotation -= 3;
        }



        entity.setPosition(position);

        entity.setRotation(rotation);

        entity.setScale(scale);

    }

}
