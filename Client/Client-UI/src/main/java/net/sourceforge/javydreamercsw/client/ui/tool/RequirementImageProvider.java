package net.sourceforge.javydreamercsw.client.ui.tool;

import com.validation.manager.core.db.Requirement;
import com.validation.manager.core.tool.ImageProvider;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author Javier A. Ortiz Bultron <javier.ortiz.78@gmail.com>
 */
@ServiceProvider(service = ImageProvider.class)
public class RequirementImageProvider implements ImageProvider<Requirement> {

    private static BufferedImage green, red, orange, yellow;
    private static final Logger LOG
            = Logger.getLogger(RequirementImageProvider.class.getSimpleName());

    @Override
    public BufferedImage getIcon(Requirement req, int coverage) throws IOException {
        BufferedImage image;
        LOG.log(Level.FINE, "Processing coverage: {0} for Requirement: {1}", 
                new Object[]{coverage, req.getUniqueId()});
        if (coverage == 100) {
            if (green == null) {
                green = ImageIO.read(getClass().getResource(
                        "/net/sourceforge/javydreamercsw/vm/client/hierarchy/visualizer/circle_green.png"));
            }
            image = green;
        } else if (coverage < 100 && coverage > 50) {
            if (yellow == null) {
                yellow = ImageIO.read(getClass().getResource(
                        "/net/sourceforge/javydreamercsw/vm/client/hierarchy/visualizer/circle_yellow.png"));
            }
            image = yellow;
        } else if (coverage < 50 && coverage > 25) {
            if (orange == null) {
                orange = ImageIO.read(getClass().getResource(
                        "/net/sourceforge/javydreamercsw/vm/client/hierarchy/visualizer/circle_orange.png"));
            }
            image = orange;
        } else {
            if (red == null) {
                red = ImageIO.read(getClass().getResource(
                        "/net/sourceforge/javydreamercsw/vm/client/hierarchy/visualizer/circle_red.png"));
            }
            image = red;
        }
        return image;
    }

    @Override
    public boolean supported(Object e) {
        return e instanceof Requirement;
    }
}