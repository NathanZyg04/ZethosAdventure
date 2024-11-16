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
                image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/chest/png")));
                break;

        }


    }

}
