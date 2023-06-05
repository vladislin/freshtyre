package com.freshtyre.application.configuration

import com.freshtyre.business.adapter.KitRepositoryAdapter
import com.freshtyre.business.facade.KitFacade
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableAsync


@Configuration
@EnableAsync
class ApplicationConfiguration {

    @Bean
    fun kitFacade(kitRepositoryAdapter: KitRepositoryAdapter) = KitFacade(kitRepositoryAdapter)

}