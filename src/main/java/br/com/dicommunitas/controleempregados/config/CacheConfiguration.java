package br.com.dicommunitas.controleempregados.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import org.hibernate.cache.jcache.ConfigSettings;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, br.com.dicommunitas.controleempregados.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, br.com.dicommunitas.controleempregados.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, br.com.dicommunitas.controleempregados.domain.User.class.getName());
            createCache(cm, br.com.dicommunitas.controleempregados.domain.Authority.class.getName());
            createCache(cm, br.com.dicommunitas.controleempregados.domain.User.class.getName() + ".authorities");
            createCache(cm, br.com.dicommunitas.controleempregados.domain.Regiao.class.getName());
            createCache(cm, br.com.dicommunitas.controleempregados.domain.Pais.class.getName());
            createCache(cm, br.com.dicommunitas.controleempregados.domain.Localizacao.class.getName());
            createCache(cm, br.com.dicommunitas.controleempregados.domain.Departamento.class.getName());
            createCache(cm, br.com.dicommunitas.controleempregados.domain.Departamento.class.getName() + ".empregados");
            createCache(cm, br.com.dicommunitas.controleempregados.domain.Tarefa.class.getName());
            createCache(cm, br.com.dicommunitas.controleempregados.domain.Tarefa.class.getName() + ".trabalhos");
            createCache(cm, br.com.dicommunitas.controleempregados.domain.Empregado.class.getName());
            createCache(cm, br.com.dicommunitas.controleempregados.domain.Empregado.class.getName() + ".trabalhos");
            createCache(cm, br.com.dicommunitas.controleempregados.domain.Trabalho.class.getName());
            createCache(cm, br.com.dicommunitas.controleempregados.domain.Trabalho.class.getName() + ".tarefas");
            createCache(cm, br.com.dicommunitas.controleempregados.domain.HistoricoDeTrabalho.class.getName());
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache != null) {
            cm.destroyCache(cacheName);
        }
        cm.createCache(cacheName, jcacheConfiguration);
    }

}
