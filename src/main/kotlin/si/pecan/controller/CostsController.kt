package si.pecan.controller

import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import si.pecan.service.CostFilter
import si.pecan.service.CostRetrievalService

@RestController
@RequestMapping("/api")
class CostsController(private val costRetrievalService: CostRetrievalService) {

    @GetMapping("/providers")
    fun getCosts(costFilter: CostFilter, pageable: Pageable) = costRetrievalService.getRecordsForFilter(costFilter, pageable)
}