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

		final String strInvoice = "{\"id\":93677,\"proforma\":true,\"partial_proforma\":false,\"number\":\"1-2012-0001\",\"variable_symbol\":\"120120001\",\"your_name\":\"Mgr. Radek \\u0160t\\u00edcha\",\"your_street\":\"M\\u011bstsk\\u00e1 Habrov\\u00e1 814\",\"your_street2\":null,\"your_city\":\"Rychnov nad Kn\\u011b\\u017enou\",\"your_zip\":\"51601\",\"your_country\":\"\\u010cesk\\u00e1 republika\",\"your_registration_no\":\"72790164\",\"your_vat_no\":\"\",\"client_name\":\"Radek \\u0160t\\u00edcha\",\"client_street\":\"Nam. I.P. Pavlova 5\",\"client_street2\":null,\"client_city\":\"Praha\",\"client_zip\":\"12000\",\"client_country\":null,\"client_registration_no\":\"75821489\",\"client_vat_no\":null,\"subject_id\":31334,\"generator_id\":null,\"related_id\":null,\"token\":\"fev3sLab78\",\"status\":\"open\",\"order_number\":null,\"issued_on\":\"2012-11-01\",\"taxable_fulfillment_due\":\"2012-11-01\",\"due\":14,\"due_on\":\"2012-11-15\",\"sent_at\":null,\"paid_at\":null,\"reminder_sent_at\":null,\"accepted_at\":null,\"cancelled_at\":null,\"note\":\"\",\"footer_note\":\"Fyzick\\u00e1 osoba zapsan\\u00e1 v \\u017eivnostensk\\u00e9m rejst\\u0159\\u00edku M\\u00fa Rychnov nad Kn\\u011b\\u017enou.\",\"bank_account\":\"670100-2208786312/6210\",\"iban\":\"\",\"swift_bic\":\"\",\"payment_method\":\"bank\",\"currency\":\"CZK\",\"exchange_rate\":\"1.0\",\"language\":null,\"transferred_tax_liability\":false,\"subtotal\":\"0.0\",\"total\":\"0.0\",\"native_subtotal\":\"0.0\",\"native_total\":\"0.0\",\"remaining_amount\":\"0.0\",\"remaining_native_amount\":\"0.0\",\"lines\":[{\"name\":\"Sada 5ti reklam Sponzorovan\\u00fd tip na webu K\\u00e1mo\\u0161ka.cz v d\\u00e9lce trv\\u00e1n\\u00ed 1 roku.\",\"quantity\":\"1.0\",\"unit_name\":\"\",\"unit_price\":\"1200.0\",\"vat_rate\":20,\"with_vat\":true}],\"html_url\":\"https://partnerkamoska.fakturoid.cz/invoices/93677\",\"public_html_url\":\"https://partnerkamoska.fakturoid.cz/p/fev3sLab78/1-2012-0001\",\"url\":\"https://partnerkamoska.fakturoid.cz/api/v1/invoices/93677.json\",\"subject_url\":\"https://partnerkamoska.fakturoid.cz/api/v1/subjects/31334.json\",\"updated_at\":\"2012-11-01T21:16:31+01:00\"}";

		Invoice invoice = mapper.readValue(strInvoice, Invoice.class);
		assertNotNull(invoice);
		assertNotNull(invoice.getLines());


	}
}
