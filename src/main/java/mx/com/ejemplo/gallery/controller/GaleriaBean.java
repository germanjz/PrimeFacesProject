/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.ejemplo.gallery.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import mx.com.ejemplo.gallery.model.Fotos;
import org.primefaces.event.FileUploadEvent;

/**
 *
 * @author Germán
 */
@ManagedBean(name = "galeriaBean")
@ViewScoped
public class GaleriaBean implements Serializable {
    private List<Fotos> fotos = new ArrayList<>();
    private static final int BUFFER_SIZE = 6124;
    
    /**
     * Default Constructor
     */
    public GaleriaBean() {
        super();
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();

        Path path = Paths.get(((ServletContext) externalContext.getContext())
                .getRealPath(File.separator + "resources" + File.separator + "photos"));
        try (DirectoryStream<Path> ds = Files.newDirectoryStream(path)) {
            for (Path file : ds) {
                Fotos foto = new Fotos(file.getFileName().toString(), true);
                fotos.add(foto);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void subirArchivos(FileUploadEvent event) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        
        if (event.getFile() != null) {
        Path path = Paths.get(((ServletContext) externalContext.getContext())
                    .getRealPath(File.separator + "resources" + File.separator + "photos" + File.separator));
            FileOutputStream fileOutputStream;
            InputStream inputStream;
            try {
                String fn = event.getFile().getFileName();
                fileOutputStream = new FileOutputStream(path.toString() + File.separator + fn);

                byte[] buffer = new byte[BUFFER_SIZE];

                int bulk;
                inputStream = event.getFile().getInputstream();
                while (true) {
                    bulk = inputStream.read(buffer);
                    if (bulk < 0) {
                        break;
                    }
                    fileOutputStream.write(buffer, 0, bulk);
                    fileOutputStream.flush();
                }

                fileOutputStream.close();
                inputStream.close();

                Fotos foto = new Fotos(fn, false);
                fotos.add(foto);

                facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Los archivos fueron subidos exitosamente !", null));
            } catch (FileNotFoundException ex) {
                facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "El Archivo " + event.getFile().getFileName() + " No pudo ser encontrado!", null));
            } catch (IOException ex) {
                facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "El Archivo " + event.getFile().getFileName() + " No pudo ser subido!", null));
            }
        } else {
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "File does not exist!", null));
        }
    }

    /**
     * @return the fotos
     */
    public List<Fotos> getFotos() {
        return fotos;
    }

    /**
     * @param fotos the fotos to set
     */
    public void setFotos(List<Fotos> fotos) {
        this.fotos = fotos;
    }

}
