package si.pecan.repository

import com.winterbe.expekt.expect
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.PageRequest
import org.springframework.test.context.junit4.SpringRunner

@RunWith(value = SpringRunner::class)
@SpringBootTest
class CostRecordRepositoryTest {

    @Autowired
    lateinit var costRecordsRepository: CostRecordsRepository

    @Test
    fun `should retrieve the first 100 entries from the database`() {
        val records = costRecordsRepository.findAll(PageRequest.of(0, 100))
        expect(records.size).to.equal(100)
    }
    @Test
    fun `entries retrieved from the database should have the medical provider entity populated`() {
        val records = costRecordsRepository.findAll(PageRequest.of(0, 5))

        records.forEach {
            expect(it.medicalProvider).to.not.be.`null`
        }
    }

}