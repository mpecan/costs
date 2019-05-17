package si.pecan.repository

import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.PagingAndSortingRepository
import si.pecan.domain.CostRecord
import si.pecan.domain.MedicalProvider

interface CostRecordsRepository : PagingAndSortingRepository<CostRecord, Long>, JpaSpecificationExecutor<CostRecord> {
}

interface MedicalProviderRepository : PagingAndSortingRepository<MedicalProvider, Long> {
    @Query("SELECT DISTINCT state FROM MedicalProvider")
    fun findDistinctStates(): List<String>
}