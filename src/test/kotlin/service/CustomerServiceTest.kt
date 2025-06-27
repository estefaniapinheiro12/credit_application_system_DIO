package service

import me.dio.credit.application.system.entity.Address
import me.dio.credit.application.system.entity.Customer
import me.dio.credit.application.system.repository.CustomerRepository
import me.dio.credit.application.system.service.impl.CustomerService
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.math.BigDecimal

@SpringBootTest
class CustomerServiceTest {

    @Autowired
    private lateinit var customerService: CustomerService

    @Autowired
    private lateinit var repository: CustomerRepository

    private fun buildCustomer(): Customer {
        return Customer(
            firstName = "John",
            lastName = "Doe",
            cpf = "12345678900",
            email = "john@example.com",
            income = BigDecimal(3000.0),
            password = "1234",
            address = Address(
                zipCode = "12345-678",
                street = "Rua das Flores"
            )
        )
    }

    @Test
    fun `should save customer`() {
        val customer = buildCustomer()
        val saved = customerService.save(customer)

        assertNotNull(saved.id)
        assertEquals("John", saved.firstName)
    }

    @Test
    fun `should find customer by id`() {
        val saved = repository.save(buildCustomer())
        val found = customerService.findById(saved.id!!)

        assertEquals(saved.email, found.email)
        assertEquals(saved.firstName, found.firstName)
    }

    @Test
    fun `should delete customer`() {
        val saved = repository.save(buildCustomer())
        customerService.delete(saved.id!!)
        val result = repository.findById(saved.id!!)

        assertTrue(result.isEmpty)
    }
}