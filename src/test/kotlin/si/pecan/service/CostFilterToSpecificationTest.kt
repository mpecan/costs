package si.pecan.service

import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.winterbe.expekt.expect
import org.junit.Before
import org.junit.Test
import si.pecan.domain.CostRecord
import si.pecan.domain.CostRecord_
import java.math.BigDecimal
import javax.persistence.criteria.*


class CostFilterToSpecificationTest {
    lateinit var criteriaBuilderMock: CriteriaBuilder
    lateinit var criteriaQueryMock: CriteriaQuery<Any>
    lateinit var costRecordMock: Root<CostRecord>

    @Before
    fun init() {
        criteriaBuilderMock = mock()
        criteriaQueryMock = mock()
        costRecordMock = mock()
    }

    @Test
    fun `should create an empty specifcation when no filters passed`() {
        val specification = null.toSpecification()

        val predicate: Predicate? = specification.toPredicate(costRecordMock, criteriaQueryMock, criteriaBuilderMock)
        expect(predicate).to.be.`null`
    }

    @Test
    fun `should create an a specifcation with discharges between when discharges filter is present`() {
        val minimum = 30L
        val maximum = 40L
        val specification = CostFilter(discharges = minimum to maximum).toSpecification()

        val dischargePathMock = mock<Path<Long>>()
        val predicateMock = mock<Predicate>()

        whenever(costRecordMock.get(CostRecord_.totalDischarges)).thenReturn(dischargePathMock)
        whenever(criteriaBuilderMock.between(eq(dischargePathMock), eq(minimum), eq(maximum))).thenReturn(predicateMock)

        val predicate: Predicate? = specification.toPredicate(costRecordMock, criteriaQueryMock, criteriaBuilderMock)
        expect(predicate).to.be.equal(predicateMock)
        verify(costRecordMock).get(CostRecord_.totalDischarges)
        verify(criteriaBuilderMock).between(eq(dischargePathMock), eq(minimum), eq(maximum))
    }

    @Test
    fun `should create an a specifcation with averageCoveredCharges between when averageCoveredCharges filter is present`() {
        val minimum = BigDecimal.ONE
        val maximum = BigDecimal.TEN
        val specification = CostFilter(averageCoveredCharges = minimum to maximum).toSpecification()

        val dischargePathMock = mock<Path<BigDecimal>>()
        val predicateMock = mock<Predicate>()

        whenever(costRecordMock.get(CostRecord_.averageCoveredCharges)).thenReturn(dischargePathMock)
        whenever(criteriaBuilderMock.between(eq(dischargePathMock), eq(minimum), eq(maximum))).thenReturn(predicateMock)

        val predicate: Predicate? = specification.toPredicate(costRecordMock, criteriaQueryMock, criteriaBuilderMock)
        expect(predicate).to.be.equal(predicateMock)
        verify(costRecordMock).get(CostRecord_.totalDischarges)
        verify(criteriaBuilderMock).between(eq(dischargePathMock), eq(minimum), eq(maximum))
    }

    @Test
    fun `should create an a specifcation with averageMedicarePayments between when averageMedicarePayments filter is present`() {
        val minimum = BigDecimal.ONE
        val maximum = BigDecimal.TEN
        val specification = CostFilter(averageMedicarePayments = minimum to maximum).toSpecification()

        val dischargePathMock = mock<Path<BigDecimal>>()
        val predicateMock = mock<Predicate>()

        whenever(costRecordMock.get(CostRecord_.averageMedicarePayments)).thenReturn(dischargePathMock)
        whenever(criteriaBuilderMock.between(eq(dischargePathMock), eq(minimum), eq(maximum))).thenReturn(predicateMock)

        val predicate: Predicate? = specification.toPredicate(costRecordMock, criteriaQueryMock, criteriaBuilderMock)
        expect(predicate).to.be.equal(predicateMock)
        verify(costRecordMock).get(CostRecord_.totalDischarges)
        verify(criteriaBuilderMock).between(eq(dischargePathMock), eq(minimum), eq(maximum))
    }
}