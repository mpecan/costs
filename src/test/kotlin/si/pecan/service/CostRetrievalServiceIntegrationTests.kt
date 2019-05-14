package si.pecan.service

import com.winterbe.expekt.expect
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.PageRequest
import org.springframework.test.context.junit4.SpringRunner
import java.math.BigDecimal


@SpringBootTest
@RunWith(value = SpringRunner::class)
class CostRetrievalServiceIntegrationTests {

    companion object {
        val PAGE_REQUEST = PageRequest.of(0, 10)
    }

    @Autowired
    lateinit var costRetrievalService: CostRetrievalService

    @Test
    fun `should retrieve all records when null filter is supplied`() {
        val page = costRetrievalService.getRecordsForFilter(null, PAGE_REQUEST)
        expect(page.size).to.equal(10)
    }

    @Test
    fun `should retrieve records with totalDischarges between 50 and 100 inclusive`() {
        val list = costRetrievalService.getRecordsForFilter(CostFilter(discharges = 50L to 100L), PAGE_REQUEST)
        list.forEach {
            expect(it.totalDischarges).to.be.within(50L, 100L)
        }
    }

    @Test
    fun `should retrieve records with averageMedicarePayments between 20000 and 30000 inclusive`() {
        val min = BigDecimal.valueOf(20000.0)
        val max = BigDecimal.valueOf(30000.0)
        val list = costRetrievalService.getRecordsForFilter(CostFilter(averageMedicarePayments = min to max), PAGE_REQUEST)
        list.forEach {
            expect(it.averageMedicarePayments).to.be.within(min, max)
        }
    }

    @Test
    fun `should retrieve records with averageMedicarePayments above 20000 inclusive`() {
        val min = BigDecimal.valueOf(20000.0)
        val list = costRetrievalService.getRecordsForFilter(CostFilter(averageMedicarePayments = min to null), PAGE_REQUEST)
        list.forEach {
            expect(it.averageMedicarePayments).to.be.least(min)
        }
    }

    @Test
    fun `should retrieve records with averageMedicarePayments below 20000 inclusive`() {
        val max = BigDecimal.valueOf(20000.0)
        val list = costRetrievalService.getRecordsForFilter(CostFilter(averageMedicarePayments = null to max), PAGE_REQUEST)
        list.forEach {
            expect(it.averageMedicarePayments).to.be.most(max)
        }
    }

    @Test
    fun `should retrieve records with averageCoveredCharges between 20000 and 30000 inclusive`() {
        val min = BigDecimal.valueOf(20000.0)
        val max = BigDecimal.valueOf(30000.0)

        val list = costRetrievalService.getRecordsForFilter(CostFilter(averageCoveredCharges = min to max), PAGE_REQUEST)
        list.forEach {
            expect(it.averageCoveredCharges).to.be.within(min, max)
        }
    }

    @Test
    fun `should retrieve records with from the state of Arizona`() {
        val state = "AZ"
        val list = costRetrievalService.getRecordsForFilter(CostFilter(state = state), PAGE_REQUEST)
        list.forEach {
            expect(it.state).to.equal(state)
        }
    }

    @Test
    fun `should retrieve records with from the state of Arizona with averageCoveredCharges between 20000 and 30000 inclusive`() {
        val state = "AZ"
        val min = BigDecimal.valueOf(20000.0)
        val max = BigDecimal.valueOf(30000.0)
        val list = costRetrievalService.getRecordsForFilter(CostFilter(state = state, averageCoveredCharges = min to max), PAGE_REQUEST)
        list.forEach {
            expect(it.state).to.equal(state)
            expect(it.averageCoveredCharges).to.be.within(min, max)
        }
    }
}