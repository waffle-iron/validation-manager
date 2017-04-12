package net.sourceforge.javydreamercsw.validation.manager.web.file;

import com.vaadin.server.Sizeable;
import com.vaadin.ui.Window;
import java.io.File;
import org.apache.commons.io.FilenameUtils;
import org.openide.util.lookup.ServiceProvider;
import pl.pdfviewer.PdfViewer;

/**
 * Display a PDF file. Based on code from:
 * http://jcraane.blogspot.co.uk/2010/09/printing-in-vaadin.html
 *
 * This class creates a PDF with the iText library. This class implements the
 * StreamSource interface which defines the getStream method.
 *
 * @author Javier A. Ortiz Bultron <javier.ortiz.78@gmail.com>
 */
@ServiceProvider(service = IFileDisplay.class)
public class PDFDisplay extends AbstractFileDisplay {

    @Override
    public boolean supportFile(String name) {
        return FilenameUtils.getExtension(name).equals("pdf");
    }

    @Override
    public Window getViewer(File f) {
        Window w = new Window(f.getName());
        w.center();
        w.setModal(true);
        PdfViewer pdfViewer = new PdfViewer(f);
        pdfViewer.setSizeFull();
        w.setContent(pdfViewer);
        w.setHeight(80, Sizeable.Unit.PERCENTAGE);
        w.setWidth(80, Sizeable.Unit.PERCENTAGE);
        return w;
    }
}
