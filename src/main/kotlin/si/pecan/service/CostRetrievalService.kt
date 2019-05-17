package si.pecan.service

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Service
import si.pecan.domain.CostRecord
import si.pecan.domain.CostRecord_
import si.pecan.domain.MedicalProvider
import si.pecan.domain.MedicalProvider_
import si.pecan.dto.CostRecordDto
import si.pecan.dto.toDto
import si.pecan.repository.CostRecordsRepository
import javax.persistence.criteria.Join
import javax.persistence.metamodel.SingularAttribute

@Service
class CostRetrievalService(private val costRecordsRepository: CostRecordsRepository) {
    fun getRecordsForFilter(filter: CostFilter?, pageRequest: Pageable): Page<CostRecordDto> {
        val page = costRecordsRepository.findAll(filter.toSpecification(), pageRequest)
        return page.map(CostRecord::toDto)
    }
}


// Using deprecated method as the static function Specification.where does not have the correct @Nullable annotation on its parameter value
@Suppress("DEPRECATION")
fun CostFilter?.toSpecification(): Specification<CostRecord> {
    this ?: return Specification.where<CostRecord>(null)!!
    return Specification.where<CostRecord>(null).letIf(this.discharges != null) {
        val (min, max) = this.discharges!!
        rangeSpecification(min, max, CostRecord_.totalDischarges)?.and(it) ?: it
    }.letIf(this.averageCoveredCharges != null) {
        val (min, max) = this.averageCoveredCharges!!
        rangeSpecification(min, max, CostRecord_.averageCoveredCharges)?.and(it) ?: it
    }.letIf(this.averageMedicarePayments != null) {
        val (min, max) = this.averageMedicarePayments!!
        rangeSpecification(min, max, CostRecord_.averageMedicarePayments)?.and(it) ?: it
    }.letIf(this.state != null) {
        Specification<CostRecord> { record, _, criteriaBuilder ->
            val join: Join<CostRecord, MedicalProvider> = record.join(CostRecord_.medicalProvider)
            criteriaBuilder.equal(join.get<String>(MedicalProvider_.state), state)
        }.and(it)
    }!!
}

private inline fun <reified T> T.letIf(check: Boolean, operation: (T) -> T) = if (check) {
    this.let(operation)
} else {
    this
}


private inline fun <reified T : Number> rangeSpecification(min: T?, max: T?, field: SingularAttribute<CostRecord, T>?): Specification<CostRecord>? {
    return when {
        // Could not use `between` because the reification does not play well with it.
        min != null && max != null -> Specification { record, _, criteriaBuilder -> criteriaBuilder.and(criteriaBuilder.ge(record.get(field), min), criteriaBuilder.le(record.get(field), max)) }
        min != null -> Specification { record, _, criteriaBuilder -> criteriaBuilder.ge(record.get(field), min) }
        max != null -> Specification { record, _, criteriaBuilder -> criteriaBuilder.le(record.get(field), max) }
        else -> null
    }
}