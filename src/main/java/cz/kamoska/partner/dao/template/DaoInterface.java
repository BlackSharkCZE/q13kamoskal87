package cz.kamoska.partner.dao.template;


import cz.kamoska.partner.dao.domains.QueryResult;
import cz.kamoska.partner.dao.domains.SaveDomainResult;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author blackshark
 * @version 0.1
 * @since 0.1
 */
public interface DaoInterface<T> extends Serializable {


	/**
	 * Odstrani predany objekt z databaze
	 * @param value
	 * @return
	 */
	boolean drop(T value);

	/**
	 * Aktualizuje objekt proti DB
	 * @param value
	 * @return true, pokud byl objekt odstraneny jinak false
	 */
	T refresh(T value);

	/**
	 * Ulozi pozadovany objekt do databaze
	 * @param value objekt k ulozeni
	 * @return ulozeny objekt
	 */
	SaveDomainResult<T> save(T value);

	/**
	 * Aktualizuje objekt v databazi
	 * @param value objekt k aktualizace
	 * @return true, pokud uspech, jinak false
	 */
	SaveDomainResult<T> update(T value);

	/**
	 * Hleda entity podle predaneho klice
	 * @param entityClass trida hledane entity
	 * @param primaryKey primarni klic
	 * @return nalezeny objekt, nebo null, pokud neexistuje
	 */
	T findByPrimaryKey(Class entityClass, Object primaryKey);


	/**
	 * Vraci seznam nalezenych entity
	 * @param namedQuery pojmenovany dotaz, ktery pouzit pro hledani
	 * @param offset od ktereho zaznamu hledat
	 * @param limit kolik zaznamu maximalne vratit
	 * @param params seznam parametru pro omezeni vysledku
	 * @return seznam nalezenych zaznamu
	 */
	List<T> findListByNamedQuery(final String namedQuery, int offset, int limit, Map<String, Object> params);

	/**
	 * Vraci nalezenou entity
	 * @param namedQuery pouzity dotaz pro vyheldavani
	 * @param params seznam parametru pro omezeni vysledku
	 * @return nalezena entity nebo null
	 */
	T findSingleByNamedQuery(final String namedQuery, Map<String, Object> params);

	/**
	 * Hleda list entit zadaneho typu s omezenim dle zadanych pravidel
	 * @param rootClass class dane entity, kterou hledat
	 * @param offset od ktere entity hledat (index)
	 * @param limit kolik maximalne vratit vysledku
	 * @param sortColumn podle ktereho sloupce radit
	 * @param sortDirection smysl razeni asc/desc
	 * @param filter nastaveny filter
	 * @return seznam nalezenych zaznamu dle kriterii a pocet vsech zaznamu, ktere odpovidaji
	 */
	QueryResult<T> findByCriteriaBuilder(Class rootClass, int offset, int limit, String sortColumn, boolean sortDirection, Map<String, String> filter);

	Integer executeUpdateNamedQuery(final String namedQuery, Map<String, Object> params);
}
