package si.pecan.domain

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "medical_provider")
class MedicalProvider {
    @Id
    var id: Long? = null
    lateinit var name: String
    lateinit var streetAddress: String
    lateinit var city: String
    lateinit var state: String
    var zipCode: Long? = null
    lateinit var referralRegionDescription: String
}