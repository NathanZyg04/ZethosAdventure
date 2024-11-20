package objects;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class OBJ_Key extends SuperObject {



    public OBJ_Key(String obj_name) throws IOException {



        switch (obj_name) {
            case "key":
                name = "key";
                image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/key.png")));
                break;
            case "chest":

                name = "chest";
                collision = true;
                image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/chest.png")));
                break;
            case "red_potion":
                name = "red_potion";
                image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/red_potion.png")));
                break;
            case "door":
                name = "door";
                collision = true;
                image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/door.png")));
                break;
            case "door_open":
                name = "door_open";
                collision = false;
                image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/door_open.png")));
                break;
            case "boots":
                name = "boots";
                image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/boots.png")));
                break;
            case "chest_opened":
                name = "chest_opened";
                collision = true;
                image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/chest_opened.png")));
                break;


        }


    }

}
