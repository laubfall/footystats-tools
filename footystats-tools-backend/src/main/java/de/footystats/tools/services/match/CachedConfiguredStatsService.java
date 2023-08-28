package de.footystats.tools.services.match;

import de.footystats.tools.services.footy.dls.ConfiguredCsvDownloadService;
import java.util.HashMap;
import java.util.Map;
import lombok.Builder;
import lombok.Data;
import org.springframework.stereotype.Service;

@Service
public class CachedConfiguredStatsService {

	// Static field that holds the max static cache time of 10 minutes in millisecons as a long.
	private static final long MAX_CACHE_TIME = 10L * 60L * 1000L;

	private final ConfiguredCsvDownloadService configuredCsvDownloadService;

	private final Map<CacheKey, CacheValue> cache = new HashMap<>();

	public CachedConfiguredStatsService(ConfiguredCsvDownloadService configuredCsvDownloadService) {
		this.configuredCsvDownloadService = configuredCsvDownloadService;
	}

	public void configuredStats(String country, String league) {
		synchronized (cache) {
			var cacheKey = CacheKey.builder().country(country).league(league).build();
			if (cache.containsKey(cacheKey)) {
				var cacheValue = cache.get(cacheKey);
				// If the cache value is less than the max cache time, return.
				if (System.currentTimeMillis() - cacheValue.getLastAccessed() > MAX_CACHE_TIME) {
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
