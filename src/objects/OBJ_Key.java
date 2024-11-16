package objects;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class OBJ_Key extends SuperObject {



    public OBJ_Key(int ID) throws IOException {



        switch (ID) {
            case 1:
                name = "key";
                image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/key.png")));
                break;
            case 2:

                name = "chest";
                image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/chest.png")));
                break;
            case 3:
                name = "red_potion";
                image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/red_potion.png")));
                break;
            case 4:
                name = "door";
                collision = true;
                image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/door.png")));
                break;
            case 5:
                name = "door_open";
                collision = false;
                image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/door_open.png")));
                break;

        }


    }

}
