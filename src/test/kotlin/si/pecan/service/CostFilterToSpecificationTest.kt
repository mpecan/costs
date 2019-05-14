package si.pecan.service

import com.nhaarman.mockitokotlin2.*
import com.winterbe.expekt.expect
import org.junit.Before
import org.junit.Test
import si.pecan.domain.CostRecord
import si.pecan.domain.CostRecord_
import si.pecan.domain.MedicalProvider
import si.pecan.domain.MedicalProvider_
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
        whenever(criteriaBuilderMock.and(any(),any())).thenReturn(predicateMock)
        whenever(criteriaBuilderMock.ge(any(),any<Long>())).thenReturn(predicateMock)
        whenever(criteriaBuilderMock.le(any(),any<Long>())).thenReturn(predicateMock)

        val predicate: Predicate? = specification.toPredicate(costRecordMock, criteriaQueryMock, criteriaBuilderMock)
        expect(predicate).to.be.equal(predicateMock)
        verify(costRecordMock, times(2)).get(CostRecord_.totalDischarges)
        verify(criteriaBuilderMock).and(any(), any())
        verify(criteriaBuilderMock).ge(any(),any<Long>())
        verify(criteriaBuilderMock).le(any(),any<Long>())
    }

    @Test
    fun `should create an a specifcation with averageCoveredCharges between when averageCoveredCharges filter is present`() {
        val minimum = BigDecimal.ONE
        val maximum = BigDecimal.TEN
        val specification = CostFilter(averageCoveredCharges = minimum to maximum).toSpecification()

        val averageCoveredChargePathMock = mock<Path<BigDecimal>>()
        val predicateMock = mock<Predicate>()

        whenever(costRecordMock.get(CostRecord_.averageCoveredCharges)).thenReturn(averageCoveredChargePathMock)
        whenever(criteriaBuilderMock.and(any(),any())).thenReturn(predicateMock)
        whenever(criteriaBuilderMock.ge(any(),any<BigDecimal>())).thenReturn(predicateMock)
        whenever(criteriaBuilderMock.le(any(),any<BigDecimal>())).thenReturn(predicateMock)

        val predicate: Predicate? = specification.toPredicate(costRecordMock, criteriaQueryMock, criteriaBuilderMock)
        expect(predicate).to.be.equal(predicateMock)
        verify(costRecordMock, times(2)).get(CostRecord_.averageCoveredCharges)
        verify(criteriaBuilderMock).and(any(), any())
        verify(criteriaBuilderMock).ge(any(),any<BigDecimal>())
        verify(criteriaBuilderMock).le(any(),any<BigDecimal>())
    }

    @Test
    fun `should create an a specifcation with averageMedicarePayments between when averageMedicarePayments filter is present`() {
        val minimum = BigDecimal.ONE
        val maximum = BigDecimal.TEN
        val specification = CostFilter(averageMedicarePayments = minimum to maximum).toSpecification()

        val averageMedicarePaymentsPath = mock<Path<BigDecimal>>()
        val predicateMock = mock<Predicate>()
        whenever(costRecordMock.get(CostRecord_.averageMedicarePayments)).thenReturn(averageMedicarePaymentsPath)
        whenever(criteriaBuilderMock.and(any(),any())).thenReturn(predicateMock)
        whenever(criteriaBuilderMock.ge(any(),any<BigDecimal>())).thenReturn(predicateMock)
        whenever(criteriaBuilderMock.le(any(),any<BigDecimal>())).thenReturn(predicateMock)

        val predicate: Predicate? = specification.toPredicate(costRecordMock, criteriaQueryMock, criteriaBuilderMock)
        expect(predicate).to.be.equal(predicateMock)
        verify(costRecordMock, times(2)).get(CostRecord_.averageMedicarePayments)
        verify(criteriaBuilderMock).and(any(), any())
        verify(criteriaBuilderMock).ge(any(),any<BigDecimal>())
        verify(criteriaBuilderMock).le(any(),any<BigDecimal>())
    }

    @Test
    fun `should create an a specifcation with state between when state filter is present`() {
        val state = "AZ"
        val specification = CostFilter(state = state).toSpecification()

        val statePathMock = mock<Path<String>>()
        val medicalProviderJoinMock = mock<Join<CostRecord, MedicalProvider>>() {
            on(it.get(MedicalProvider_.state)).thenReturn(statePathMock)
        }
        val predicateMock = mock<Predicate>()

        whenever(costRecordMock.join(CostRecord_.medicalProvider)).thenReturn(medicalProviderJoinMock)
        whenever(criteriaBuilderMock.equal(eq(statePathMock), eq(state))).thenReturn(predicateMock)

        val predicate: Predicate? = specification.toPredicate(costRecordMock, criteriaQueryMock, criteriaBuilderMock)
        expect(predicate).to.be.equal(predicateMock)
        verify(costRecordMock).join(CostRecord_.medicalProvider)
        verify(medicalProviderJoinMock).get(MedicalProvider_.state)
        verify(criteriaBuilderMock).equal(eq(statePathMock), eq(state))
    }

    @Test
    fun `should create an a specifcation with multiple filters present`() {
        val state = "AZ"
        val minimum = BigDecimal.ONE
        val maximum = BigDecimal.TEN
        val specification = CostFilter(state = state, averageMedicarePayments = minimum to maximum).toSpecification()

        val statePathMock = mock<Path<String>>()
        val medicalProviderJoinMock = mock<Join<CostRecord, MedicalProvider>>() {
            on(it.get(MedicalProvider_.state)).thenReturn(statePathMock)
        }

        val combinedPredicateMock = mock<Predicate>()
        val statePredicateMock = mock<Predicate>()

        val averageCoveredChargePathMock = mock<Path<BigDecimal>>()
        val averageCoveredChargePredicateMock = mock<Predicate>()

        whenever(costRecordMock.get(CostRecord_.averageCoveredCharges)).thenReturn(averageCoveredChargePathMock)
        whenever(criteriaBuilderMock.ge(any(),any<BigDecimal>())).thenReturn(averageCoveredChargePredicateMock)
        whenever(criteriaBuilderMock.le(any(),any<BigDecimal>())).thenReturn(averageCoveredChargePredicateMock)

        whenever(costRecordMock.join(CostRecord_.medicalProvider)).thenReturn(medicalProviderJoinMock)
        whenever(criteriaBuilderMock.equal(eq(statePathMock), eq(state))).thenReturn(statePredicateMock)
        whenever(criteriaBuilderMock.and(any<Predicate>(),any<Predicate>())).thenReturn(averageCoveredChargePredicateMock)
        whenever(criteriaBuilderMock.and(any<Expression<Boolean>>(), any<Expression<Boolean>>()))
                .thenReturn(combinedPredicateMock)

        val predicate: Predicate? = specification.toPredicate(costRecordMock, criteriaQueryMock, criteriaBuilderMock)
        expect(predicate).to.be.equal(combinedPredicateMock)
        verify(costRecordMock).join(CostRecord_.medicalProvider)
        verify(medicalProviderJoinMock).get(MedicalProvider_.state)
        verify(criteriaBuilderMock).equal(eq(statePathMock), eq(state))
        verify(costRecordMock, times(2)).get(CostRecord_.averageCoveredCharges)
        verify(criteriaBuilderMock).and(any(), any())
        verify(criteriaBuilderMock).ge(any(),any<BigDecimal>())
        verify(criteriaBuilderMock).le(any(),any<BigDecimal>())
    }
}