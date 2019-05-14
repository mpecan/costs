package si.pecan.support

import com.nhaarman.mockitokotlin2.mock
import com.winterbe.expekt.expect
import org.junit.Before
import org.junit.Test
import org.springframework.core.MethodParameter
import org.springframework.web.context.request.NativeWebRequest
import si.pecan.service.CostFilter
import javax.servlet.http.HttpServletRequest

class CostFilterArgumentResolverTest{

    lateinit var subject: CostFilterArgumentResolver
    private val methodParameter = mock<MethodParameter>{
        on(it.parameterType).thenReturn(CostFilter::class.java)
    }

    private val httpServletRequest = mock<HttpServletRequest>()
    private val mockedNativeWebRequest = mock<NativeWebRequest>{
        on(it.getNativeRequest(HttpServletRequest::class.java)).thenReturn(httpServletRequest)
    }
    @Before
    fun init() {
        subject = CostFilterArgumentResolver()
    }

    @Test
    fun `should support CostFilter as an argument`() {
        val supportsParameter = subject.supportsParameter(methodParameter)
        expect(supportsParameter).to.be.`true`
    }

    @Test
    fun `parses filter from request passed in`() {
        val resolvedArgument = subject.resolveArgument(methodParameter, null, mockedNativeWebRequest, null)
        expect(resolvedArgument).`is`.instanceof(CostFilter::class.java)
    }

}