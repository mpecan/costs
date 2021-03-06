package si.pecan.domain

import java.math.BigDecimal
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "cost_data")
class CostRecord {
    @Id
    var id: Long? = null

    @ManyToOne
    var medicalProvider: MedicalProvider? = null

    lateinit var drgDefinition: String
    var totalDischarges: Long? = 0
    lateinit var averageCoveredCharges: BigDecimal
    lateinit var averageTotalPayments: BigDecimal
    lateinit var averageMedicarePayments: BigDecimal
}