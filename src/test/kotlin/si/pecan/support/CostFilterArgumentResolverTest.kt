package si.pecan.support

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.winterbe.expekt.expect
import org.junit.Before
import org.junit.Test
import org.springframework.core.MethodParameter
import org.springframework.web.context.request.NativeWebRequest
import si.pecan.repository.MedicalProviderRepository
import si.pecan.service.CostFilter
import javax.servlet.http.HttpServletRequest
import javax.validation.ValidationException
import kotlin.math.cos

class CostFilterArgumentResolverTest {

    lateinit var subject: CostFilterArgumentResolver
    private val methodParameter = mock<MethodParameter> {
        on(it.parameterType).thenReturn(CostFilter::class.java)
    }

    private val httpServletRequest = mock<HttpServletRequest>()
    private val mockedNativeWebRequest = mock<NativeWebRequest> {
        on(it.getNativeRequest(HttpServletRequest::class.java)).thenReturn(httpServletRequest)
    }

    @Before
    fun init() {
        subject = CostFilterArgumentResolver(mock {
            on(mock.findDistinctStates()).thenReturn(listOf("AZ"))
        })
    }

    @Test
    fun `should support CostFilter as an argument`() {
        val supportsParameter = subject.supportsParameter(methodParameter)
        expect(supportsParameter).to.be.`true`
    }

    @Test
    fun `parses filter from request passed in with no HttpServletRequest`() {
        val resolvedArgument = subject.resolveArgument(methodParameter, null, mock(), null)
        expect(resolvedArgument).`is`.instanceof(CostFilter::class.java)
    }

    @Test
    fun `parses filter from request passed in with no parameters`() {
        val resolvedArgument = subject.resolveArgument(methodParameter, null, mockedNativeWebRequest, null)
        expect(resolvedArgument).`is`.instanceof(CostFilter::class.java)
    }

    @Test
    fun `parses filter from request passed in with max_discharges`() {
        whenever(httpServletRequest.parameterMap).thenReturn(mapOf("max_discharges" to arrayOf("1000")))
        val resolvedArgument = subject.resolveArgument(methodParameter, null, mockedNativeWebRequest, null)
        expect(resolvedArgument).`is`.instanceof(CostFilter::class.java)
        val costFilter = resolvedArgument as CostFilter
        expect(costFilter.discharges).equal(null to 1000L)
    }

    @Test(expected = ValidationException::class)
    fun `throws validation exception in case of invalid input for max_discharges`() {
        whenever(httpServletRequest.parameterMap).thenReturn(mapOf("max_discharges" to arrayOf("afasafassd")))
        val resolvedArgument = subject.resolveArgument(methodParameter, null, mockedNativeWebRequest, null)
        expect(resolvedArgument).`is`.instanceof(CostFilter::class.java)
        val costFilter = resolvedArgument as CostFilter
        expect(costFilter.discharges).equal(null to 1000L)
    }

    @Test
    fun `parses filter from request passed in with max_average_medicare_payments`() {
        whenever(httpServletRequest.parameterMap).thenReturn(mapOf("max_average_medicare_payments" to arrayOf("1000")))
        val resolvedArgument = subject.resolveArgument(methodParameter, null, mockedNativeWebRequest, null)
        expect(resolvedArgument).`is`.instanceof(CostFilter::class.java)
        val costFilter = resolvedArgument as CostFilter
        expect(costFilter.averageMedicarePayments).equal(null to "1000".toBigDecimal())
    }

    @Test
    fun `parses filter from request passed in with state`() {
        whenever(httpServletRequest.parameterMap).thenReturn(mapOf("state" to arrayOf("AZ")))
        val resolvedArgument = subject.resolveArgument(methodParameter, null, mockedNativeWebRequest, null)
        expect(resolvedArgument).`is`.instanceof(CostFilter::class.java)
        val costFilter = resolvedArgument as CostFilter
        expect(costFilter.state).equal("AZ")
    }

    @Test(expected = ValidationException::class)
    fun `throws validation exception in case of invalid input for state`() {
        whenever(httpServletRequest.parameterMap).thenReturn(mapOf("state" to arrayOf("NY")))
        val resolvedArgument = subject.resolveArgument(methodParameter, null, mockedNativeWebRequest, null)
        expect(resolvedArgument).`is`.instanceof(CostFilter::class.java)
        val costFilter = resolvedArgument as CostFilter
        expect(costFilter.discharges).equal(null to 1000L)
    }

}