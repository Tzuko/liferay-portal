package com.liferay.portal.dao.orm.hibernate.region;

import java.util.Properties;

import org.hibernate.cache.CacheDataDescription;
import org.hibernate.cache.CacheException;
import org.hibernate.cache.CollectionRegion;
import org.hibernate.cache.EntityRegion;
import org.hibernate.cache.QueryResultsRegion;
import org.hibernate.cache.RegionFactory;
import org.hibernate.cache.TimestampsRegion;
import org.hibernate.cache.access.AccessType;
import org.hibernate.cfg.Settings;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsKeys;

/**
 * @author Nikita Dubina
 */
public class SingletonLiferayIgniteRegionFactory implements RegionFactory {

	private static boolean _enabled;
	private static int _instanceCounter;
	private static LiferayIgniteRegionFactory _igniteFactory;
	
	public LiferayIgniteRegionFactory getInstance(){
		return _igniteFactory;
	}
	
	public SingletonLiferayIgniteRegionFactory(Properties properties){
		synchronized (this) {
			boolean useQueryCache = GetterUtil.getBoolean(properties.get(PropsKeys.HIBERNATE_CACHE_USE_QUERY_CACHE));
			boolean useSecondLevelCache = GetterUtil
					.getBoolean(properties.get(PropsKeys.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE));

			if (useQueryCache || useSecondLevelCache) {
				_enabled = true;
			}
			
			if (_igniteFactory == null){
				_igniteFactory = new LiferayIgniteRegionFactory();
			}
		}
	}
	
	@Override
	public CollectionRegion buildCollectionRegion(String regionName, Properties properties, CacheDataDescription cacheDataDescription)
			throws CacheException {
		return _igniteFactory.buildCollectionRegion(regionName, properties, cacheDataDescription);
	}

	@Override
	public EntityRegion buildEntityRegion(String regionName, Properties properties, CacheDataDescription cacheDataDescription)
			throws CacheException {
		return _igniteFactory.buildEntityRegion(regionName, properties, cacheDataDescription);
	}

	@Override
	public QueryResultsRegion buildQueryResultsRegion(String regionName, Properties properties) throws CacheException {
		return _igniteFactory.buildQueryResultsRegion(regionName, properties);
	}

	@Override
	public TimestampsRegion buildTimestampsRegion(String regionName, Properties properties) throws CacheException {
		return _igniteFactory.buildTimestampsRegion(regionName, properties);
	}

	@Override
	public AccessType getDefaultAccessType() {
		return _igniteFactory.getDefaultAccessType();
	}

	@Override
	public boolean isMinimalPutsEnabledByDefault() {
		return _igniteFactory.isMinimalPutsEnabledByDefault();
	}

	@Override
	public long nextTimestamp() {
		return _igniteFactory.nextTimestamp();
	}

	@Override
	public void start(Settings settings, Properties properties) throws CacheException {
		if (_enabled && (_instanceCounter++ == 0)) {
			_igniteFactory.start(settings, properties);
		}
	}

	@Override
	public void stop() {
		if (_enabled && (--_instanceCounter == 0)) {
			_igniteFactory.stop();
		}
	}

}
