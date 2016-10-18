
package controller;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
 
import entities.Membre;

 
@FacesConverter("themeConverter")
public class ThemeConverter implements Converter {
 
    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
       /* if(value != null && value.trim().length() > 0) {
            try {
                ControllerCanal service = (ControllerCanal) fc.getExternalContext().getApplicationMap().get("themeService");
                return service.getMembres().get(Integer.parseInt(value));
            } catch(NumberFormatException e) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid theme."));
            }
        }
        else {
        }*/
                    return null;

    }
 
    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if(object != null) {
            return String.valueOf(((Membre) object).getId());
        }
        else {
            return null;
        }
    }   
}
