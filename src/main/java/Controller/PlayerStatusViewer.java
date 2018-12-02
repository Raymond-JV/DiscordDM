package Controller;

import Game.Battle.DuelStatus;
import net.dv8tion.jda.core.entities.MessageChannel;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PlayerStatusViewer {

    private final List<File> specStatus;
    private final List<File> healthStatus;
    private final DiscordHelper helper = new DiscordHelper();
    private final Font font;

    public PlayerStatusViewer(StatusPictureTransformer pictureTransformer)
    {
        font = new Font("RuneScape UF Regular", Font.PLAIN,30);
        try {
            this.specStatus = pictureTransformer.createSpecBarImages();
            this.healthStatus = pictureTransformer.createHealthBarImages();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Status images could not load. Program cannot continue.");
        }
    }
    public File createStatus(MessageChannel channel, List<DuelStatus> state) throws IOException {

        List<File> players = new ArrayList<>(state.size());

        try{
        for (int i = 0; i < state.size(); i++)
        {
            List<File> playerAttributes = new ArrayList<>();
            DuelStatus status = state.get(i);

            playerAttributes.add(createText("name.png", status.getName() + " " + status.getHealth() + "hp"));
            playerAttributes.add(healthStatus.get(Math.max(99 - status.getHealth(), 0)));
            playerAttributes.add(specStatus.get(100 - status.getSpec()));
            players.add(mergeImageVertical(playerAttributes, "status" + i + ".png", 20));
        }
            return mergeImageHorizontal(players, "status.png", 60);

        } catch (IOException e) {
            throw new IOException(e);
        }

        finally {
            for (File f : players)
                f.delete();
        }
    }
    
    private File mergeImageHorizontal(List<File> files, String fileName, int gap) throws IOException {
        List<BufferedImage> images = new ArrayList<>(files.size());
        for (File f : files)
            images.add(ImageIO.read(f));
        int width = 0;
        int max_height = 0;

        for (BufferedImage image : images) {
            width += image.getWidth();
            max_height = Math.max(image.getHeight(), max_height);
        }
        width += images.size() * gap;

        BufferedImage combined = new BufferedImage(width, max_height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D merged = combined.createGraphics();

        int currentWidth = 0;
        for (BufferedImage image : images) {
            merged.drawImage(image, currentWidth, 0, null);
            currentWidth += image.getWidth() + gap;
        }
        File toReturn = new File(fileName);
        ImageIO.write(combined, "png", toReturn);
        merged.dispose();
        return toReturn;
    }

        private File mergeImageVertical (List < File > files, String fileName,int gap) throws IOException {

            List<BufferedImage> images = new ArrayList<>(files.size());
            for (File f : files)
                images.add(ImageIO.read(f));
            int height = 0;
            int max_width = 0;

            for (BufferedImage image : images) {
                height += image.getHeight();
                max_width = Math.max(image.getWidth(), max_width);
            }
            height += images.size() * gap;

            BufferedImage combined = new BufferedImage(max_width, height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D merged = combined.createGraphics();

            int currentHeight = 0;
            for (BufferedImage image : images) {
                merged.drawImage(image, 0, currentHeight, null);
                currentHeight += image.getHeight() + gap;
            }
            File toReturn = new File(fileName);
            ImageIO.write(combined, "png", toReturn);
            merged.dispose();
            return toReturn;
        }

        private File createText (String fileName, String userName) throws IOException {
            BufferedImage field = new BufferedImage(250, 40, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = field.createGraphics();
            g.setFont(font);
            g.setPaint(Color.BLACK);
            g.drawString(userName, 10, 30);

            File f = new File(fileName);
            ImageIO.write(field, "png", f);
            f.deleteOnExit();
            return f;
        }
    }
