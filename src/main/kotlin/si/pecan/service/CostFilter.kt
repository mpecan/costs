package si.pecan.service

import java.math.BigDecimal

data class CostFilter(
        val discharges: Pair<Long, Long>? = null,
        val averageCoveredCharges: Pair<BigDecimal, BigDecimal>? = null,
        val averageMedicarePayments: Pair<BigDecimal, BigDecimal>? = null,
        val state: String? = null
)