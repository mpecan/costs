package si.pecan.repository

import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.PagingAndSortingRepository
import si.pecan.domain.CostRecord

interface CostRecordsRepository : PagingAndSortingRepository<CostRecord, Long>, JpaSpecificationExecutor<CostRecord> {
}