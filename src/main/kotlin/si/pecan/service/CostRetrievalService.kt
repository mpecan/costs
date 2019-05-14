package si.pecan.service

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
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
import java.math.BigDecimal
import javax.persistence.criteria.Join
import javax.persistence.metamodel.SingularAttribute

@Service
class CostRetrievalService(private val costRecordsRepository: CostRecordsRepository) {
    fun getRecordsForFilter(filter: CostFilter?, pageRequest: Pageable): Page<CostRecordDto> {
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
        rangeSpecification(min, max, specifications, CostRecord_.totalDischarges)
    }
    if (this?.averageCoveredCharges != null) {
        val (min, max) = this.averageCoveredCharges
        rangeSpecification(min, max, specifications, CostRecord_.averageCoveredCharges)
    }
    if (this?.averageMedicarePayments != null) {
        val (min, max) = this.averageMedicarePayments
        rangeSpecification(min, max, specifications, CostRecord_.averageMedicarePayments)
    }
    if (this?.state != null) {
        specifications.add(Specification { record, _, criteriaBuilder ->
            val join: Join<CostRecord, MedicalProvider> = record.join(CostRecord_.medicalProvider)
            criteriaBuilder.equal(join.get<String>(MedicalProvider_.state), state)
        })
    }
    return specifications.reduce { a, b -> a.and(b) }

}

private inline fun <reified T : Number> rangeSpecification(min: T?, max: T?, specifications: MutableList<Specification<CostRecord>>, field: SingularAttribute<CostRecord, T>?) {
    when {
        min != null && max != null -> specifications.add(Specification { record, _, criteriaBuilder -> criteriaBuilder.and(criteriaBuilder.ge(record.get(field), min), criteriaBuilder.le(record.get(field), min)) })
        min != null -> specifications.add(Specification { record, _, criteriaBuilder -> criteriaBuilder.ge(record.get(field), min) })
        max != null -> specifications.add(Specification { record, _, criteriaBuilder -> criteriaBuilder.le(record.get(field), max) })
    }
}