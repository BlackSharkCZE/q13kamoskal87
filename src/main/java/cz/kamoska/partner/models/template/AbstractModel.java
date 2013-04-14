package cz.kamoska.partner.models.template;

import cz.kamoska.partner.dao.domains.QueryResult;
import cz.kamoska.partner.dao.domains.SaveDomainResult;
import cz.kamoska.partner.dao.template.DaoInterface;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

/**
 * Vylepsena trida, ktera je prevzata z Teleparkingu a slouzi jako zakladni trida pro modely. Poskytuje ulozeni do DB, nacitani z DB. Je to nahrada za
 *
 * @author blackshark
 * @version 0.1
 * @since 0.1
 */
public abstract class AbstractModel<T> {

	protected final Logger logger = LoggerFactory.getLogger(getClass());
	protected abstract DaoInterface<T> getBasicDaoInterface();

	protected T currentItem;

	private Class clazz;

	protected LazyDataModel<T> lazyDataModel;

	protected AbstractModel() {
	}

	protected AbstractModel(final Class clazz, final String defaultSortColumn) {
		this.clazz = clazz;
		try {
			currentItem = (T) clazz.newInstance();
		} catch (Exception e) {
			logger.error("Can not init current for class " + clazz.getName());
		}
		lazyDataModel = createLazyDataModel(clazz, defaultSortColumn);
	}

	protected LazyDataModel<T> createLazyDataModel(final Class clazz, final String defaultSortColumn) {
		return new LazyDataModel<T>() {
			@Override
			public List<T> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters) {
				Field[] filed = clazz.getDeclaredFields();
				for (Field field : filed) {
					if (field.getName().equals(defaultSortColumn) && sortField == null) {
						sortField = defaultSortColumn;
						break;
					}
				}
				QueryResult<T> res = getBasicDaoInterface().findByCriteriaBuilder(clazz, first, pageSize, sortField, sortOrder == SortOrder.DESCENDING, filters);
				setRowCount((int) res.count);
				return res.items;
			}
		};
	}

	protected AbstractModel(final Class clazz) {
		this(clazz, "id");
	}

	public boolean updateCurrentItem() {
		if (currentItem == null) {
			logger.warn("Can not update NULL item " + clazz.getName());
			return false;
		}
		SaveDomainResult<T> res = getBasicDaoInterface().update(currentItem);
		if (res.success) {
			currentItem = res.item;
		}
		return res.success;
	}

	public boolean saveCurrentItem() {
		if (currentItem == null) {
			logger.warn("Can not save NULL item " + clazz.getName());
			return false;
		}

		SaveDomainResult<T> res = getBasicDaoInterface().save(currentItem);
		if (res.success) {
			currentItem = res.item;
		}
		return res.success;
	}

	public void resetCurrentItem() {
		try {
			currentItem = (T) clazz.newInstance();
		} catch (Exception e) {
			logger.error("Can not reset current item: " + clazz.getName());
		}
	}

	public boolean deleteCurrentItem() {
		if (currentItem != null) {
			return getBasicDaoInterface().drop(currentItem);
		} else {
			logger.warn("Can not drop NULL object.");
		}
		return false;
	}

	public T getCurrentItem() {
		return currentItem;
	}

	public void setCurrentItem(T currentItem) {
		this.currentItem = currentItem;
	}

	public LazyDataModel<T> getLazyDataModel() {
		return lazyDataModel;
	}

	public void setLazyDataModel(LazyDataModel<T> lazyDataModel) {
		this.lazyDataModel = lazyDataModel;
	}

	public boolean refresh() {
		if (currentItem == null) {
			logger.warn("Can not save NULL item " + clazz.getName());
			return false;
		} else {
			T res = getBasicDaoInterface().refresh(currentItem);
			return true;
		}
	}
}
