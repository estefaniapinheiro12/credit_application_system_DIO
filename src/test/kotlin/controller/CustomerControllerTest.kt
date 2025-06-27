package controller

import com.fasterxml.jackson.databind.ObjectMapper
import me.dio.credit.application.system.controller.CustomerController
import me.dio.credit.application.system.entity.Address
import me.dio.credit.application.system.entity.Customer
import me.dio.credit.application.system.service.impl.CustomerService
import org.hamcrest.Matchers.*
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.mockito.Mockito.`when`
import org.mockito.Mockito.any
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post
import java.math.BigDecimal

@WebMvcTest(CustomerController::class)
class CustomerControllerTest(@Autowired val mockMvc: MockMvc) {

    @MockBean
    private lateinit var customerService: CustomerService

    private val objectMapper = ObjectMapper()

    @Test
    fun `should save customer`() {
        val customer = Customer(
            firstName = "Test",
            lastName = "User",
            cpf = "12345678900",
            email = "test@email.com",
            income = BigDecimal(5000),
            password = "1234",
            address = Address(zipCode = "00000-000", street = "Rua Teste")
        )

        `when`(customerService.save(any())).thenReturn(customer.copy(id = 1))

        val json = objectMapper.writeValueAsString(customer)

        mockMvc.post("/api/customers") {
            contentType = MediaType.APPLICATION_JSON
            content = json
        }.andExpect {
            status { isCreated() }
            jsonPath("$.firstName") { value("Test") }
        }
    }
}