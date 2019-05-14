package si.pecan.dto

import org.hamcrest.core.Is.`is`
import org.junit.Assert.assertThat
import org.junit.Test
import si.pecan.domain.CostRecord
import si.pecan.domain.MedicalProvider
import java.math.BigDecimal

class CostRecordToDtoTest {

    @Test
    fun `should correctly transform cost record to DTO`() {

        val expectedCoveredCharges = BigDecimal(11.34)
        val expectedMedicarePayments = BigDecimal(11.35)
        val expectedTotalPayments = BigDecimal(11.36)
        val expectedDischarges = 12L
        val expectedDrgDescription = "1244"
        val expectedId = 1L
        val expectedProviderCity = "Izola"
        val expectedProviderAddress = "Some street 4"
        val expectedProviderId = 2L
        val expectedProviderName = "Provider name"
        val expectedProviderRegionDescription = "region in world"
        val expectedProviderState = "AZ"
        val expectedProviderZipCode = 23552L

        val record = CostRecord().apply {
            averageCoveredCharges = expectedCoveredCharges
            averageMedicarePayments = expectedMedicarePayments
            averageTotalPayments = expectedTotalPayments
            totalDischarges = expectedDischarges
            drgDefinition = expectedDrgDescription
            id = expectedId
            medicalProvider = MedicalProvider().apply {
                city = expectedProviderCity
                name = expectedProviderName
                referralRegionDescription = expectedProviderRegionDescription
                state = expectedProviderState
                id = expectedProviderId
                streetAddress = expectedProviderAddress
                zipCode = expectedProviderZipCode
            }
        }
        val (id, drgDefinition, totalDischarges, averageCoveredCharges, averageTotalPayments, averageMedicarePayments, providerId, name, streetAddress, city, state, zipCode, referralRegionDescription) = record.toDto();
        assertThat(id, `is`(expectedId))
        assertThat(drgDefinition, `is`(expectedDrgDescription))
        assertThat(totalDischarges, `is`(expectedDischarges))
        assertThat(averageCoveredCharges, `is`(expectedCoveredCharges))
        assertThat(averageTotalPayments, `is`(expectedTotalPayments))
        assertThat(averageMedicarePayments, `is`(expectedMedicarePayments))
        assertThat(providerId, `is`(expectedProviderId))
        assertThat(name, `is`(expectedProviderName))
        assertThat(streetAddress, `is`(expectedProviderAddress))
        assertThat(state, `is`(expectedProviderState))
        assertThat(city, `is`(expectedProviderCity))
        assertThat(zipCode, `is`(expectedProviderZipCode))
        assertThat(referralRegionDescription, `is`(expectedProviderRegionDescription))
    }

}