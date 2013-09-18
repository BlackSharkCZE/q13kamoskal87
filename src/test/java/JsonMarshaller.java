import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import cz.kamoska.partner.pojo.fakturoid.Invoice;
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

		final String strInvoice = "{\"id\":182238,\"proforma\":true,\"partial_proforma\":false,\"number\":\"1-52013-0048\",\"variable_symbol\":\"1520130048\",\"your_name\":\"Glaceo s.r.o.\",\"your_street\":\"Karoliny Světlé 278/28\",\"your_street2\":null,\"your_city\":\"Praha 1\",\"your_zip\":\"11000\",\"your_country\":\"CZ\",\"your_registration_no\":\"29132860\",\"your_vat_no\":\"\",\"client_name\":\"Bezva firma s.r.o.\",\"client_street\":\"Za mlýnem 27\",\"client_street2\":null,\"client_city\":\"Praha\",\"client_zip\":\"14700\",\"client_country\":\"CZ\",\"client_registration_no\":\"24168416\",\"client_vat_no\":\"CZ24168416\",\"subject_id\":60318,\"generator_id\":null,\"related_id\":182651,\"token\":\"PuU6qmzjfP\",\"status\":\"paid\",\"order_number\":null,\"issued_on\":\"2013-08-27\",\"taxable_fulfillment_due\":\"2013-08-27\",\"due\":14,\"due_on\":\"2013-09-10\",\"sent_at\":null,\"paid_at\":\"2013-08-28T00:00:00.000+02:00\",\"reminder_sent_at\":null,\"accepted_at\":null,\"cancelled_at\":null,\"note\":\"Fakturujeme Vám za služby webu partner.kamoska.cz\",\"footer_note\":\"Společnost je zapsána v obchodním rejstříku vedeném Městským soudem v Praze oddíl C, vložka 202707.\",\"bank_account\":\"2600341676/2010\",\"iban\":\"CZ8520100000002600341676\",\"swift_bic\":\"FIOBCZPP\",\"payment_method\":\"bank\",\"currency\":\"CZK\",\"exchange_rate\":\"1.0\",\"language\":\"cz\",\"transferred_tax_liability\":false,\"supply_code\":null,\"subtotal\":\"1825.0\",\"total\":\"1825.0\",\"native_subtotal\":\"1825.0\",\"native_total\":\"1825.0\",\"remaining_amount\":\"1825.0\",\"remaining_native_amount\":\"1825.0\",\"lines\":[{\"name\":\"Sada 5 reklam Sponzorovaný tip na webu Kámoška.cz v délce trvání 1 roku.\",\"quantity\":\"1.0\",\"unit_name\":\"\",\"unit_price\":\"1825.0\",\"vat_rate\":0,\"with_vat\":false}],\"html_url\":\"https://partnerkamoska.fakturoid.cz/invoices/182238\",\"public_html_url\":\"https://partnerkamoska.fakturoid.cz/p/PuU6qmzjfP/1-52013-0048\",\"url\":\"https://partnerkamoska.fakturoid.cz/api/v1/invoices/182238.json\",\"subject_url\":\"https://partnerkamoska.fakturoid.cz/api/v1/subjects/60318.json\",\"updated_at\":\"2013-08-28T10:24:41.518+02:00\"}";

		Invoice invoice = mapper.readValue(strInvoice, Invoice.class);
		assertNotNull(invoice);
		assertNotNull(invoice.getLines());


	}
}
