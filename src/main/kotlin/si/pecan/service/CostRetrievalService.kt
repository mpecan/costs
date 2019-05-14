package si.pecan.service

import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.jpa.domain.Specification
import org.springframework.data.jpa.domain.Specifications
import org.springframework.stereotype.Service
import si.pecan.domain.CostRecord
import si.pecan.domain.CostRecord_
import si.pecan.domain.MedicalProvider
import si.pecan.domain.MedicalProvider_
import si.pecan.dto.CostRecordDto
import si.pecan.dto.toDto
import si.pecan.repository.CostRecordsRepository
import java.awt.print.Pageable
import java.math.BigDecimal
import javax.persistence.criteria.*

@Service
class CostRetrievalService(private val costRecordsRepository: CostRecordsRepository) {
    fun getRecordsForFilter(filter: CostFilter?, pageRequest: PageRequest): Page<CostRecordDto> {
        val page = costRecordsRepository.findAll(filter.toSpecification(), pageRequest)
        return page.map(CostRecord::toDto)
    }
}


fun CostFilter?.toSpecification(): Specification<CostRecord> {
    val specifications = mutableListOf<Specification<CostRecord>>()
    // Using deprecated method as the static function Specification.where does not have the correct @Nullable annotation on its parameter value
    @Suppress("DEPRECATION")
    specifications.add(Specifications.where<CostRecord>(null))
    if (this?.discharges != null) {
        val (min, max) = this.discharges
        specifications.add(Specification { record, _, criteriaBuilder -> criteriaBuilder.between(record.get(CostRecord_.totalDischarges), min, max) })
    }
    if (this?.averageCoveredCharges != null) {
        val (min, max) = this.averageCoveredCharges
        specifications.add(Specification { record, _, criteriaBuilder -> criteriaBuilder.between(record.get(CostRecord_.averageCoveredCharges), min, max) })
    }
    if (this?.averageMedicarePayments != null) {
        val (min, max) = this.averageMedicarePayments
        specifications.add(Specification { record, _, criteriaBuilder -> criteriaBuilder.between(record.get<BigDecimal>(CostRecord_.averageMedicarePayments), min, max) })
    }
    if (this?.state != null) {
        specifications.add(Specification { record, _, criteriaBuilder ->
            val join: Join<CostRecord, MedicalProvider> = record.join(CostRecord_.medicalProvider)
            criteriaBuilder.equal(join.get<String>(MedicalProvider_.state), state)
        })
    }
    return specifications.reduce { a, b -> a.and(b) }

}