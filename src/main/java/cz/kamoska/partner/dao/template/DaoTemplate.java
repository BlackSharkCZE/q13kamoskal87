package cz.kamoska.partner.dao.template;

//import

import cz.kamoska.partner.commons.Constants;
import cz.kamoska.partner.dao.domains.QueryResult;
import cz.kamoska.partner.dao.domains.SaveDomainResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.*;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author blackshark
 * @version 0.1
 * @since 0.1
 */
public abstract class DaoTemplate<T> implements DaoInterface<T> {

	protected final Logger logger = LoggerFactory.getLogger(DaoTemplate.class);
	protected Object dateFormatLock;
	protected SimpleDateFormat dateFormat;

	@PersistenceContext(unitName = "kamoskaPU")
	protected EntityManager em;

	public DaoTemplate() {
		dateFormatLock = new Object();
		dateFormat = new SimpleDateFormat(Constants.DATE_FORMAT);
	}

	@Override
	public boolean drop(T value) {

		if (value != null) {

			try {
				if (!em.contains(value)) {
					value = em.merge(value);
				}
				em.remove(value);
				em.flush();
				return true;
			} catch (Exception e) {
				logger.error("Can not drop object: " + value, e);
			}
		}
		return false;
	}

	@Override
	public SaveDomainResult<T> save(T value) {
		SaveDomainResult<T> res = new SaveDomainResult<T>();
		res.item = value;
		res.success = false;
		if (value == null) {
			logger.warn("Can not save NULL object to database.");
		} else {
			try {
				em.persist(value);
				em.flush();
				res.success = true;
			} catch (Exception e) {
				logger.error("Can not save object " + value, e);
			}
		}
		return res;
	}

	@Override
	public SaveDomainResult<T> update(T value) {

		SaveDomainResult<T> res = new SaveDomainResult<T>();
		res.item = value;
		res.success = false;

		if (value != null) {
			try {
				value = em.merge(value);
				em.persist(value);
				em.flush();
				res.item = value;
				res.success = true;
			} catch (Exception e) {
				logger.error("Can not update " + value, e);
			}
		} else {
			logger.warn("Can not update NULL object.");
		}
		return res;
	}

	@Override
	@SuppressWarnings("unchecked")
	public T findByPrimaryKey(Class entityClass, Object primaryKey) {
		T res = null;
		if (primaryKey != null) {
			try {
				T t = (T) em.find(entityClass, primaryKey);
				return t;
			} catch (Exception e) {
				logger.error("Can not find " + entityClass.getName() + " for primary key " + primaryKey, e);
			}
		} else {
			logger.warn("Can not find Entity " + entityClass.getName() + " for NULL primary key.");
		}
		return res;
	}

	@Override
	public List<T> findListByNamedQuery(String namedQuery, int offset, int limit, Map<String, Object> params) {
		List<T> res;

 		if (namedQuery != null) {
			Query q = em.createNamedQuery(namedQuery);
			if (params != null) {
				for (String key : params.keySet()) {
					q.setParameter(key, params.get(key));
				}
			}
			q.setFirstResult(offset);
			if (limit > 0) {
				q.setMaxResults(limit);
			}
			try {
				res = q.getResultList();
//				em.flush();
				return res;
			} catch (Exception e) {
				logger.error("Can not find any result for namedQuery: " + namedQuery, e);
			}
		} else {
			logger.warn("Can not find List  for namedQuery " + namedQuery);
		}

		return Collections.emptyList();
	}

	@Override
	public T findSingleByNamedQuery(String namedQuery, Map<String, Object> params) {
		if (namedQuery != null) {
			Query q = em.createNamedQuery(namedQuery);
			if (params != null) {
				for (String key : params.keySet()) {
					q.setParameter(key, params.get(key));
				}
			}
			q.setFirstResult(0).setMaxResults(1);

			try {
				List<T> tmp = q.getResultList();
				em.flush();
				if (tmp != null && !tmp.isEmpty()) {
					return tmp.get(0);
				}
			} catch (Exception e) {
				logger.error("Can not find any Entity for namedQuery: " + namedQuery, e);
			}
		} else {
			logger.warn("Can not find any Entity  for namedQuery " + namedQuery);
		}

		return null;
	}

	private Predicate getPredicate(Root<T> entityRoot, Class rootClass, CriteriaBuilder criteriaBuilder, Map<String, String> filter) {
		Predicate finalPredicate = null;
		List<Predicate> predicates = null;

		if (filter != null && !filter.isEmpty()) {
			predicates = new ArrayList<>(filter.size());
			for (String key : filter.keySet()) {
				Field field;
				try {
					field = rootClass.getDeclaredField(key);
				} catch (NoSuchFieldException e) {
					// takove pole v objektu neni takze pro nej tezko sestavime filter. Vynechame ho.
					continue;
				}

				final String value = filter.get(key);
				if (value != null && !value.isEmpty()) {
					// v hodnote mame neco ulozene, takze to muzeme pouzit jako filter
					if (value.contains("%") && field.getType().isAssignableFrom(String.class)) {
						// hodnota obsahuje procento, takz chceme hledat pres LIKE
						predicates.add(criteriaBuilder.like(entityRoot.<String>get(key), value));
					} else {
						// hodnota neobsahuje procento takze chceme hledat equals

						if (field.getType().isAssignableFrom(String.class)) {
							predicates.add(criteriaBuilder.equal(entityRoot.<String>get(key), value));
						} else {
							if (field.getType().isAssignableFrom(Integer.class)) {
								predicates.add(criteriaBuilder.equal(entityRoot.<Integer>get(key), Integer.parseInt(value)));
							} else {
								if (field.getType().isAssignableFrom(Date.class)) {
									synchronized (dateFormatLock) {
										try {
											predicates.add(criteriaBuilder.equal(entityRoot.<Date>get(key), dateFormat.parse(value)));
										} catch (ParseException e) {
											logger.error("Can not parse " + value + " as Date with pattern: " + Constants.DATE_FORMAT, e);
										}
									}
								}
							}
						}

					}
				}
			}
			for (Predicate p : predicates) {
				if (finalPredicate != null) {
					finalPredicate = criteriaBuilder.and(finalPredicate, p);
				} else {
					finalPredicate = criteriaBuilder.and(p);
				}
			}
		}

		return finalPredicate;

	}

	@Override
	public QueryResult<T> findByCriteriaBuilder(Class rootClass, int offset, int limit, String sortColumn, boolean sortDirection, Map<String, String> filter) {
		QueryResult<T> generalResult = new QueryResult<>();
		generalResult.items = getObjectList(rootClass, offset, limit, sortColumn, sortDirection, filter);
		if (generalResult.items != null && !generalResult.items.isEmpty()) {
			generalResult.count = getObjectListCount(rootClass, offset, limit, sortColumn, sortDirection, filter);
		} else {
			generalResult.count = 0L;
		}
		return generalResult;
	}


	private List<T> getObjectList(Class rootClass, int offset, int limit, String sortColumn, boolean sortDirection, Map<String, String> filter) {
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(rootClass);
		Root<T> entityRoot = criteriaQuery.from(rootClass);

		List<Predicate> predicates;
		Predicate finalPredicate = null;

		if (filter != null && !filter.isEmpty()) {
			predicates = new ArrayList<>(filter.size());
			for (String key : filter.keySet()) {
				Field field;
				try {
					field = rootClass.getDeclaredField(key);
				} catch (NoSuchFieldException e) {
					// takove pole v objektu neni takze pro nej tezko sestavime filter. Vynechame ho.
					continue;
				}

				final String value = filter.get(key);
				if (value != null && !value.isEmpty()) {
					// v hodnote mame neco ulozene, takze to muzeme pouzit jako filter
					if (value.contains("%") && field.getType().isAssignableFrom(String.class)) {
						// hodnota obsahuje procento, takz chceme hledat pres LIKE
						predicates.add(criteriaBuilder.like(entityRoot.<String>get(key), value));
					} else {
						// hodnota neobsahuje procento takze chceme hledat equals

						if (field.getType().isAssignableFrom(String.class)) {
							predicates.add(criteriaBuilder.equal(entityRoot.<String>get(key), value));
						} else {
							if (field.getType().isAssignableFrom(Integer.class)) {
								predicates.add(criteriaBuilder.equal(entityRoot.<Integer>get(key), Integer.parseInt(value)));
							} else {
								if (field.getType().isAssignableFrom(Date.class)) {
									synchronized (dateFormatLock) {
										try {
											predicates.add(criteriaBuilder.equal(entityRoot.<Date>get(key), dateFormat.parse(value)));
										} catch (ParseException e) {
											logger.error("Can not parse " + value + " as Date with pattern: " + Constants.DATE_FORMAT, e);
										}
									}
								}
							}
						}
					}
				}
			}
			for (Predicate p : predicates) {
				if (finalPredicate != null) {
					finalPredicate = criteriaBuilder.and(finalPredicate, p);
				} else {
					finalPredicate = criteriaBuilder.and(p);
				}
			}
		}
		Order order;
		Query findQuery;
		if (sortColumn != null) {
			if (sortDirection) {
				order = criteriaBuilder.asc(entityRoot.<Object>get(sortColumn));
			} else {
				order = criteriaBuilder.desc(entityRoot.<Object>get(sortColumn));
			}
			findQuery = em.createQuery(finalPredicate == null ? criteriaQuery.orderBy(order) : criteriaQuery.where(finalPredicate).orderBy(order));
		} else {
			findQuery = em.createQuery(finalPredicate == null ? criteriaQuery : criteriaQuery.where(finalPredicate));
		}

		List<T> res;
		try {
			res = findQuery.setFirstResult(offset).setMaxResults(limit).getResultList();
			em.flush();
		} catch (Exception e) {
			logger.error("Can not find " + rootClass.getName() + " due to error.", e);
			res = Collections.emptyList();
		}
		return res == null ? (List<T>) Collections.emptyList() : res;
	}

	private Long getObjectListCount(Class rootClass, int offset, int limit, String sortColumn, boolean sortDirection, Map<String, String> filter) {
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<Long> countCq = criteriaBuilder.createQuery(Long.class);
		Root<T> entityRoot = countCq.from(rootClass);
		countCq = countCq.select(criteriaBuilder.countDistinct(entityRoot));
//		countCq = countCq.select(criteriaBuilder.countDistinct(countCq.from(rootClass)));

		List<Predicate> predicates;
		Predicate finalPredicate = null;

		if (filter != null && !filter.isEmpty()) {
			predicates = new ArrayList<>(filter.size());
			for (String key : filter.keySet()) {
				Field field;
				try {
					field = rootClass.getDeclaredField(key);
				} catch (NoSuchFieldException e) {
					// takove pole v objektu neni takze pro nej tezko sestavime filter. Vynechame ho.
					continue;
				}

				final String value = filter.get(key);
				if (value != null && !value.isEmpty()) {
					// v hodnote mame neco ulozene, takze to muzeme pouzit jako filter
					if (value.contains("%") && field.getType().isAssignableFrom(String.class)) {
						// hodnota obsahuje procento, takz chceme hledat pres LIKE
						predicates.add(criteriaBuilder.like(entityRoot.<String>get(key), value));
					} else {
						// hodnota neobsahuje procento takze chceme hledat equals

						if (field.getType().isAssignableFrom(String.class)) {
							predicates.add(criteriaBuilder.equal(entityRoot.<String>get(key), value));
						} else {
							if (field.getType().isAssignableFrom(Integer.class)) {
								predicates.add(criteriaBuilder.equal(entityRoot.<Integer>get(key), Integer.parseInt(value)));
							} else {
								if (field.getType().isAssignableFrom(Date.class)) {
									synchronized (dateFormatLock) {
										try {
											predicates.add(criteriaBuilder.equal(entityRoot.<Date>get(key), dateFormat.parse(value)));
										} catch (ParseException e) {
											logger.error("Can not parse " + value + " as Date with pattern: " + Constants.DATE_FORMAT,e);
										}
									}
								}
							}
						}
					}
				}
			}
			for (Predicate p : predicates) {
				if (finalPredicate != null) {
					finalPredicate = criteriaBuilder.and(finalPredicate, p);
				} else {
					finalPredicate = criteriaBuilder.and(p);
				}
			}
		}
		if (finalPredicate != null) {
			countCq = countCq.where(finalPredicate);
		}
		Long count;
		try {
			count = em.createQuery(countCq).getSingleResult();
			em.flush();
		} catch (Exception e) {
			logger.error("Can not obtain record count for " + rootClass.getName() + " List: " + e.getMessage(),e);
			count = 0L;
		}

		return count == null ? 0L : count;
	}

	@Override
	public Integer executeUpdateNamedQuery(String namedQuery, Map<String, Object> params) {
		try {
			Query q = em.createNamedQuery(namedQuery);
			if (params != null && !params.isEmpty()) {
				for (String k : params.keySet()) {
					q.setParameter(k, params.get(k));
				}
			}
			int res = q.executeUpdate();
			em.flush();
			return res;
		} catch (Exception e) {
			logger.error("Can not execute update by namedQuery " + namedQuery + ": " + e.getMessage(), e);
			return 0;
		}
	}


	@Override
	public T refresh(T value) {
		try {
			value = em.merge(value);
			em.refresh(value);
		} catch (Exception e) {
			logger.error("Can not refresh object " + value, e);
		}
		return value;
	}


}
