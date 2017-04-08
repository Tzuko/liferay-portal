package com.liferay.portal.dao.orm.hibernate.region;

import java.util.Map;
import java.util.Properties;
import java.util.HashMap;

import org.apache.ignite.internal.IgniteKernal;
import org.apache.ignite.internal.processors.cache.IgniteInternalCache;
import org.apache.ignite.Ignition;
import org.apache.ignite.cache.CacheAtomicityMode;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.cache.CacheWriteSynchronizationMode;
import org.apache.ignite.configuration.CacheConfiguration;

import org.hibernate.cache.CacheDataDescription;
import org.hibernate.cache.CacheException;
import org.hibernate.cache.CollectionRegion;
import org.hibernate.cache.EntityRegion;
import org.hibernate.cfg.Settings;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import ru.emdev.bridge.ignite.cache.hibernate.HibernateRegionFactory;
import ru.emdev.bridge.ignite.cache.hibernate.HibernateEntityRegion;
import ru.emdev.bridge.ignite.cache.hibernate.HibernateCollectionRegion;

/**
 * @author Nikita Dubina
 */

public class LiferayIgniteRegionFactory extends HibernateRegionFactory {

	private static final Log log = LogFactoryUtil.getLog(LiferayIgniteRegionFactory.class);

	private IgniteKernal ignite;
	private IgniteInternalCache<Object, Object> cache;
	private final Map<String, String> regionCaches = new HashMap<>();

	@Override
	public void start(Settings settings, Properties properties) throws CacheException {
		super.start(settings, properties);
		initializeVariables(properties);
	}

	public void initializeVariables(Properties props) {
		ignite = (IgniteKernal) Ignition.ignite("liferay-hibernate-grid");
		String dfltCacheName = props.getProperty(DFLT_CACHE_NAME_PROPERTY);

		for (Map.Entry<Object, Object> prop : props.entrySet()) {
			String key = prop.getKey().toString();
			if (key.startsWith(REGION_CACHE_PROPERTY)) {
				String regionName = key.substring(REGION_CACHE_PROPERTY.length());
				String cacheName = prop.getValue().toString();
				regionCaches.put(regionName, cacheName);
			}
		}
		
		if (dfltCacheName != null) {
			cache = ((IgniteKernal) ignite).getCache(dfltCacheName);
		}
	}

	private IgniteInternalCache<Object, Object> regionCache(String regionName) throws CacheException {
		String cacheName = regionCaches.get(regionName);
		if (cacheName == null) {
			if (cache != null)
				return cache;
			cacheName = regionName;
		}
		IgniteInternalCache<Object, Object> cache = ((IgniteKernal) ignite).getCache(cacheName);
		if (cache == null)
			throw new CacheException("Cache '" + cacheName + "' for region '" + regionName + "' is not configured.");
		return cache;
	}

	private CacheConfiguration<Object, Object> cacheConfiguration(String cacheName) {
		CacheConfiguration<Object, Object> cfg = new CacheConfiguration<Object, Object>();
		cfg.setName(cacheName);
		cfg.setCacheMode(CacheMode.PARTITIONED);
		cfg.setAtomicityMode(CacheAtomicityMode.TRANSACTIONAL);
		cfg.setWriteSynchronizationMode(CacheWriteSynchronizationMode.FULL_SYNC);

		return cfg;
	}

	@Override
	public EntityRegion buildEntityRegion(String regionName, Properties props, CacheDataDescription metadata)
			throws CacheException {
		EntityRegion entityRegion = null;
		try {
			entityRegion = new HibernateEntityRegion(this, regionName, ignite, regionCache(regionName), metadata);
		} catch (Exception e) {
		}
		if (entityRegion == null) {
			ignite.createCache(cacheConfiguration(regionName));
			try {
				entityRegion = new HibernateEntityRegion(this, regionName, ignite, regionCache(regionName), metadata);
			} catch (Exception e) {
				log.debug("exception occurred");
			}
		}
		return entityRegion;
	}

	@Override
	public CollectionRegion buildCollectionRegion(String regionName, Properties props, CacheDataDescription metadata)
			throws CacheException {
		CollectionRegion collectionRegion = null;
		try {
			collectionRegion = new HibernateCollectionRegion(this, regionName, ignite, regionCache(regionName),
					metadata);
		} catch (Exception e) {
		}
		if (collectionRegion == null) {
			ignite.createCache(cacheConfiguration(regionName));
			try {
				collectionRegion = new HibernateCollectionRegion(this, regionName, ignite, regionCache(regionName),
						metadata);
			} catch (Exception e) {
				log.debug("exception occurred");
			}
		}
		return collectionRegion;
	}

}
