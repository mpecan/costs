package si.pecan.repository

import org.springframework.data.repository.PagingAndSortingRepository
import si.pecan.domain.CostRecord

interface CostRecordsRepository : PagingAndSortingRepository<CostRecord, Long> {
}