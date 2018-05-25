package com.coviam.shopcarro.search.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author sreerajr
 * @package com.coviam.shopcarro.search.configuration
 * @project search
 */
@ConfigurationProperties(prefix = "spring.data.solr")
public class SearchProperties {
    private String host = "http://127.0.0.1:8983/solr";
    private String zkHost;
}
