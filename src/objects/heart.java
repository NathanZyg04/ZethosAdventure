package objects;

import entity.Entity;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class heart extends Entity {



    public heart(GamePanel gp) {

      super(gp);
        name = "Heart";


        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/heart_full.png")));
            image2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/heart_half.png")));
            image3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/heart_empty.png")));
            image =  uTool.scaledImage(image, gp.tileSize*2,gp.tileSize*2);
            image2 = uTool.scaledImage(image2, gp.tileSize*2,gp.tileSize*2);
            image3 = uTool.scaledImage(image3, gp.tileSize*2,gp.tileSize*2);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

}
