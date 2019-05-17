package si.pecan.support

import org.springframework.core.MethodParameter
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer
import si.pecan.repository.MedicalProviderRepository
import si.pecan.service.CostFilter
import java.lang.NumberFormatException
import java.math.BigDecimal
import java.util.*
import javax.servlet.http.HttpServletRequest
import javax.validation.ValidationException
import kotlin.math.max


class CostFilterArgumentResolver(private val medicalProviderRepository: MedicalProviderRepository) : HandlerMethodArgumentResolver {
    companion object {
        val NUMBER_REGEXP = Regex("\\d+(\\.\\d+)?")
    }

    override fun supportsParameter(parameter: MethodParameter) = parameter.parameterType == CostFilter::class.java

    override fun resolveArgument(parameter: MethodParameter, mavContainer: ModelAndViewContainer?, webRequest: NativeWebRequest, binderFactory: WebDataBinderFactory?): Any? {
        val nativeRequest = webRequest.getNativeRequest(HttpServletRequest::class.java)

        return nativeRequest?.parameterMap.toCostFilter()
    }

    private fun Map<String, Array<String>>?.toCostFilter() = this?.let {
        val dischargeFilter = parseDischargeFilter(it)
        val averageCoveredCharges = parseFilter(it, "average_covered_charges")
        val averageMedicarePayments = parseFilter(it, "average_medicare_payments")
        val stateFilter = it["state"]?.first(Objects::nonNull)
        if(stateFilter != null && !medicalProviderRepository.findDistinctStates().contains(stateFilter)){
            throw ValidationException("Invalid value $stateFilter for field state")
        }
        return CostFilter(discharges = dischargeFilter, averageCoveredCharges = averageCoveredCharges, averageMedicarePayments = averageMedicarePayments, state = stateFilter)
    } ?: CostFilter()

    private fun parseDischargeFilter(it: Map<String, Array<String>>): Pair<Long?, Long?> {
        val minKey = "min_discharges"
        val minDischarges = it[minKey]?.first(Objects::nonNull)
        minDischarges?.validateNumber(minKey)
        val maxKey = "max_discharges"
        val maxDischarges = it[maxKey]?.first(Objects::nonNull)
        maxDischarges?.validateNumber(maxKey)
        return minDischarges?.toLong() to maxDischarges?.toLong()
    }

    private fun parseFilter(it: Map<String, Array<String>>, key: String): Pair<BigDecimal?, BigDecimal?> {
        val minKey = "min_$key"
        val minValue = it[minKey]?.first(Objects::nonNull)
        minValue?.validateNumber(minKey)
        val maxKey = "max_$key"
        val maxValue = it[maxKey]?.first(Objects::nonNull)
        maxValue?.validateNumber(maxKey)
        return minValue?.toBigDecimal() to maxValue?.toBigDecimal()
    }

    fun String.validateNumber(field: String) {
        if (!NUMBER_REGEXP.matches(this)) {
            throw ValidationException("Invalid number value ${this} for field $field")
        }
    }
}
