package cz.kamoska.partner.models.converters;

import cz.kamoska.partner.config.MainConfig;
import cz.kamoska.partner.dao.interfaces.SectionDaoInterface;
import cz.kamoska.partner.entities.SectionEntity;
import java.util.logging.Logger;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * Created with IntelliJ IDEA.
 * User: blackshark
 * Date: 18.10.12
 * Time: 20:50
 * To change this template use File | Settings | File Templates.
 */
@FacesConverter(value = "advertSectionConverter")
public class SectionDaoConverter implements Converter {

	private final Logger logger = Logger.getLogger(MainConfig.LOGGER_NAME);

	private SectionDaoInterface sectionDaoInterface;

	public SectionDaoConverter() {

		try {
			sectionDaoInterface = InitialContext.doLookup("java:app/sectionDaoInterface");
		} catch (NamingException e) {
			logger.severe("Can not lookup for SectionDaoInterface");
			logger.throwing(this.getClass().getSimpleName(), "SectionDaoConverter", e);
		}
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (value != null) {
			Integer id = Integer.parseInt(value);
			if (id != null) {
				SectionEntity s = sectionDaoInterface.findByPrimaryKey(SectionEntity.class, id);
				return s;
			}
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value instanceof SectionEntity && value != null) {
			Integer id = ((SectionEntity) value).getId();
			return id == null ? null : id.toString();
		}
		return null;
	}
}
