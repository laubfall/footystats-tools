package de.footystats.tools.services.match;

import de.footystats.tools.FootystatsProperties;
import de.footystats.tools.services.footy.dls.ConfiguredCsvDownloadService;
import java.util.HashMap;
import java.util.Map;
import lombok.Builder;
import lombok.Data;
import org.springframework.stereotype.Service;

@Service
public class CachedConfiguredStatsService {

	private final ConfiguredCsvDownloadService configuredCsvDownloadService;
	private final Map<CacheKey, CacheValue> cache = new HashMap<>();
	private final FootystatsProperties properties;

	public CachedConfiguredStatsService(ConfiguredCsvDownloadService configuredCsvDownloadService, FootystatsProperties properties) {
		this.configuredCsvDownloadService = configuredCsvDownloadService;
		this.properties = properties;
	}

	/**
	 * This method is used to update the cache and trigger the download of the configured stats if necessary.
	 *
	 * @param country Mandatory. The country for which the stats should be downloaded.
	 * @param league  Mandatory. The league for which the stats should be downloaded.
	 */
	public void updateConfiguredStats(String country, String league) {
		synchronized (cache) {
			var cacheKey = CacheKey.builder().country(country).league(league).build();
			if (cache.containsKey(cacheKey)) {
				var cacheValue = cache.get(cacheKey);
				// If the cache value is less than the max cache time, return.
				if (System.currentTimeMillis() - cacheValue.getLastAccessed() > properties.getMaxCacheTimeConfiguredStatsCache()) {
					// Then trigger the download. The download service take care of the necessity of the download.
					configuredCsvDownloadService.downloadConfiguredStats(country, league);
					// Update the cache value.
					cacheValue.setLastAccessed(System.currentTimeMillis());
				}
			} else { // No cache entry, so we have the build the key, trigger the download and add the cache entry.
				configuredCsvDownloadService.downloadConfiguredStats(country, league);
				cache.put(cacheKey, CacheValue.builder().lastAccessed(System.currentTimeMillis()).build());
			}
		}
	}

	@Builder
	@Data
	static class CacheKey {

		private String country;
		private String league;
	}

	@Builder
	@Data
	static class CacheValue {

		private long lastAccessed;
	}
}
