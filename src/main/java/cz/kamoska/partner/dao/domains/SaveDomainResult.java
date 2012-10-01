package cz.kamoska.partner.dao.domains;

/**
 * @author blackshark
 * @version 0.1
 * @since 0.1
 */
public class SaveDomainResult<T> {

	public T item;
	public boolean success;

    @Override
    public String toString() {
        return "SaveDomainResult{" +
                "item=" + item +
                ", success=" + success +
                '}';
    }
}
