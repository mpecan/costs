package si.pecan.dto

import si.pecan.domain.CostRecord
import java.math.BigDecimal

data class CostRecordDto(
        val id: Long,
        val drgDefinition: String,
        val totalDischarges: Long,
        val averageCoveredCharges: BigDecimal,
        val averageTotalPayments: BigDecimal,
        val averageMedicarePayments: BigDecimal,
        val providerId: Long,
        val name: String,
        val streetAddress: String,
        val city: String,
        val state: String,
        val zipCode: Long,
        val referralRegionDescription: String
)

fun CostRecord.toDto() = CostRecordDto(this.id ?: -1,
        this.drgDefinition,
        this.totalDischarges ?: -1,
        this.averageCoveredCharges,
        this.averageTotalPayments,
        this.averageMedicarePayments,
        this.medicalProvider?.id ?: -1,
        this.medicalProvider?.name ?: "",
        this.medicalProvider?.streetAddress ?: "",
        this.medicalProvider?.city ?: "",
        this.medicalProvider?.state ?: "",
        this.medicalProvider?.zipCode ?: -1,
        this.medicalProvider?.referralRegionDescription ?: ""
)

