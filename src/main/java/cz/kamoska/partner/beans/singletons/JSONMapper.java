package cz.kamoska.partner.beans.singletons;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.log4j.Logger;

import javax.ejb.Singleton;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: blackshark
 * Date: 29.10.12
 * Time: 20:03
 * To change this template use File | Settings | File Templates.
 */

@Singleton
public class JSONMapper {

	private final Logger logger = Logger.getLogger(JSONMapper.class);

	private ObjectMapper mapper;

	public JSONMapper() {
		mapper = new ObjectMapper();
//		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
		mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		mapper.configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);
	}

	public String serialize(final Object object) {
		try {
			final String res = mapper.writeValueAsString(object);
			return res;
		} catch (JsonProcessingException e) {
			logger.error("Can not serialize to JSON object: " + object, e);
		}
		return null;
	}

	public Object deserialize(final String serializedObject, Class clazz) {
		try {
			return mapper.readValue(serializedObject, clazz);
		} catch (IOException e) {
			logger.error("Can not deserialize JSON for data: " + serializedObject + " as class " + clazz, e);
		}
		return null;
	}

}
