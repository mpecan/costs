package si.pecan.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import si.pecan.repository.MedicalProviderRepository
import si.pecan.support.CostFilterArgumentResolver

@Configuration
class WebConfiguration : WebMvcConfigurer {

    @Autowired
    lateinit var medicalProviderRepository: MedicalProviderRepository

    override fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver>) {
        super.addArgumentResolvers(resolvers)
        resolvers.add(CostFilterArgumentResolver(medicalProviderRepository))
    }
}