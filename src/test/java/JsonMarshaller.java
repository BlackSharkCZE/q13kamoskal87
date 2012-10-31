import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import cz.kamoska.partner.pojo.fakturoid.Invoice;
import cz.kamoska.partner.pojo.fakturoid.Subject;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Created with IntelliJ IDEA.
 * User: blackshark
 * Date: 27.10.12
 * Time: 17:56
 * To change this template use File | Settings | File Templates.
 */
public class JsonMarshaller {

	@Test
	public void testMarshaller() throws Exception {

		ObjectMapper mapper = new ObjectMapper();
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
		mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		mapper.configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);

		final String strInvoice = "{\"subject_id\": \"30625\", \"due\": 14, \"proforma\": true, \"lines\":[{\"name\": \"Sada 5ti reklam Sponzorovaný tip na webu Kámoška.cz v délce trvání 1 roku.\", \"quantity\": \"1.0\", \"unit_name\": \"\", \"unit_price\": \"1008.0\", \"vat_rate\": 20,  \"with_vat\": true } ] }";

		Invoice invoice = mapper.readValue(strInvoice, Invoice.class);
		assertNotNull(invoice);
		assertNotNull(invoice.getLines());


	}
}
