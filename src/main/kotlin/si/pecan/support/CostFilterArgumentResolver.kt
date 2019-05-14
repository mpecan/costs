package si.pecan.support

import org.springframework.core.MethodParameter
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer
import si.pecan.service.CostFilter
import java.lang.NumberFormatException
import java.math.BigDecimal
import java.util.*
import javax.servlet.http.HttpServletRequest


class CostFilterArgumentResolver : HandlerMethodArgumentResolver {
    override fun supportsParameter(parameter: MethodParameter) = parameter.parameterType == CostFilter::class.java

    override fun resolveArgument(parameter: MethodParameter, mavContainer: ModelAndViewContainer?, webRequest: NativeWebRequest, binderFactory: WebDataBinderFactory?): Any? {
        val nativeRequest = webRequest.getNativeRequest(HttpServletRequest::class.java)

        return nativeRequest?.parameterMap.toCostFilter()
    }
}

private fun Map<String, Array<String>>?.toCostFilter() = this?.let {
    val dischargeFilter = parseDischargeFilter(it)
    val averageCoveredCharges = parseFilter(it, "average_covered_charges")
    val averageMedicarePayments = parseFilter(it, "average_medicare_payments")
    val stateFilter = it.get("state")?.first(Objects::nonNull)
    return CostFilter(discharges = dischargeFilter, averageCoveredCharges = averageCoveredCharges, averageMedicarePayments = averageMedicarePayments, state = stateFilter)
} ?: CostFilter()

private fun parseDischargeFilter(it: Map<String, Array<String>>): Pair<Long?, Long?> {
    val minDischarges = it.get("min_discharges")
    val maxDischarges = it.get("max_discharges")
    return minDischarges?.first(Objects::nonNull)?.toLong() to maxDischarges?.first(Objects::nonNull)?.toLong()
}

private fun parseFilter(it: Map<String, Array<String>>, key: String): Pair<BigDecimal?, BigDecimal?> {
    val minValue = it.get("min_$key")
    val maxValue = it.get("max_$key")
    return minValue?.first(Objects::nonNull)?.toBigDecimal() to maxValue?.first(Objects::nonNull)?.toBigDecimal()
}
